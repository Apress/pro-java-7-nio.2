package fv_04;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Notes;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.record.TextHeaderAtom;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Apress
 */
class Search implements FileVisitor {

    ArrayList<String> wordsarray = new ArrayList<>();
    ArrayList<String> documents = new ArrayList<>();
    boolean found = false;

    public Search(String words) {
        wordsarray.clear();
        documents.clear();

        StringTokenizer st = new StringTokenizer(words, ",");
        while (st.hasMoreTokens()) {
            wordsarray.add(st.nextToken().trim());
        }
    }

    void search(Path file) throws IOException {

        found = false;

        String name = file.getFileName().toString();
        int mid = name.lastIndexOf(".");
        String ext = name.substring(mid + 1, name.length());

        if (ext.equalsIgnoreCase("pdf")) {
            found = searchInPDF_iText(file.toString());
            if (!found) {
                found = searchInPDF_PDFBox(file.toString());
            }
        }

        if (ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")) {
            found = searchInWord(file.toString());
        }

        if (ext.equalsIgnoreCase("ppt")) {
            searchInPPT(file.toString());
        }

        if (ext.equalsIgnoreCase("xls")) {
            searchInExcel(file.toString());
        }

        if ((ext.equalsIgnoreCase("txt")) || (ext.equalsIgnoreCase("xml") || ext.equalsIgnoreCase("html"))
                || ext.equalsIgnoreCase("htm") || ext.equalsIgnoreCase("xhtml") || ext.equalsIgnoreCase("rtf")) {
            searchInText(file);
        }

        if (found) {
            documents.add(file.toString());
        }
    }

    //search in text files
    boolean searchInText(Path file) {

        boolean flag = false;
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;

            OUTERMOST:
            while ((line = reader.readLine()) != null) {
                flag = searchText(line);
                if (flag) {
                    break OUTERMOST;
                }
            }

        } catch (IOException e) {
        } finally {
            return flag;
        }
    }

    //search in Excel
    boolean searchInExcel(String file) {

        Row row;
        Cell cell;
        String text;
        boolean flag = false;
        InputStream xls = null;

        try {
            xls = new FileInputStream(file);
            HSSFWorkbook wb = new HSSFWorkbook(xls);

            int sheets = wb.getNumberOfSheets();

            OUTERMOST:
            for (int i = 0; i < sheets; i++) {
                HSSFSheet sheet = wb.getSheetAt(i);

                Iterator<Row> row_iterator = sheet.rowIterator();
                while (row_iterator.hasNext()) {
                    row = (Row) row_iterator.next();
                    Iterator<Cell> cell_iterator = row.cellIterator();
                    while (cell_iterator.hasNext()) {
                        cell = cell_iterator.next();
                        int type = cell.getCellType();
                        if (type == HSSFCell.CELL_TYPE_STRING) {
                            text = cell.getStringCellValue();
                            flag = searchText(text);
                            if (flag) {
                                break OUTERMOST;
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
        } finally {
            try {
                if (xls != null) {
                    xls.close();
                }
            } catch (IOException e) {
            }
            return flag;
        }
    }

    //search in PowerPoint files
    boolean searchInPPT(String file) {

        boolean flag = false;
        InputStream fis = null;
        String text;

        try {
            fis = new FileInputStream(new File(file));
            POIFSFileSystem fs = new POIFSFileSystem(fis);
            HSLFSlideShow show = new HSLFSlideShow(fs);

            SlideShow ss = new SlideShow(show);
            Slide[] slides = ss.getSlides();

            OUTERMOST:
            for (int i = 0; i < slides.length; i++) {

                TextRun[] runs = slides[i].getTextRuns();
                for (int j = 0; j < runs.length; j++) {
                    TextRun run = runs[j];
                    if (run.getRunType() == TextHeaderAtom.TITLE_TYPE) {
                        text = run.getText();
                    } else {
                        text = run.getRunType() + " " + run.getText();
                    }

                    flag = searchText(text);
                    if (flag) {
                        break OUTERMOST;
                    }

                }

                Notes notes = slides[i].getNotesSheet();
                if (notes != null) {
                    runs = notes.getTextRuns();
                    for (int j = 0; j < runs.length; j++) {
                        text = runs[j].getText();
                        flag = searchText(text);
                        if (flag) {
                            break OUTERMOST;
                        }
                    }
                }
            }

        } catch (IOException e) {
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
            }
            return flag;
        }

    }

    //search in Word files
    boolean searchInWord(String file) {

        POIFSFileSystem fs = null;
        boolean flag = false;

        try {
            fs = new POIFSFileSystem(new FileInputStream(file));

            HWPFDocument doc = new HWPFDocument(fs);
            WordExtractor we = new WordExtractor(doc);
            String[] paragraphs = we.getParagraphText();

            OUTERMOST:
            for (int i = 0; i < paragraphs.length; i++) {

                flag = searchText(paragraphs[i]);
                if (flag) {
                    break OUTERMOST;
                }
            }

        } catch (Exception e) {
        } finally {
            return flag;
        }
    }

    //search in PDF files using PDFBox library
    boolean searchInPDF_PDFBox(String file) {

        PDFParser parser = null;
        String parsedText = null;
        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        boolean flag = false;
        int page = 0;

        File pdf = new File(file);

        try {
            parser = new PDFParser(new FileInputStream(pdf));
            parser.parse();

            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);

            OUTERMOST:
            while (page < pdDoc.getNumberOfPages()) {
                page++;
                pdfStripper.setStartPage(page);
                pdfStripper.setEndPage(page + 1);
                parsedText = pdfStripper.getText(pdDoc);

                flag = searchText(parsedText);
                if (flag) {
                    break OUTERMOST;
                }
            }

        } catch (Exception e) {
        } finally {
            try {
                if (cosDoc != null) {
                    cosDoc.close();
                }
                if (pdDoc != null) {
                    pdDoc.close();
                }
            } catch (Exception e) {
            }
            return flag;
        }
    }

    //search in PDF files using iText library
    boolean searchInPDF_iText(String file) {

        PdfReader reader = null;
        boolean flag = false;

        try {
            reader = new PdfReader(file);
            int n = reader.getNumberOfPages();

            OUTERMOST:
            for (int i = 1; i <= n; i++) {
                String str = PdfTextExtractor.getTextFromPage(reader, i);

                flag = searchText(str);
                if (flag) {
                    break OUTERMOST;
                }
            }

        } catch (Exception e) {
        } finally {
            if (reader != null) {
                reader.close();
            }
            return flag;
        }

    }

    //search text
    private boolean searchText(String text) {

        boolean flag = false;
        for (int j = 0; j < wordsarray.size(); j++) {
            if ((text.toLowerCase()).contains(wordsarray.get(j).toLowerCase())) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        System.out.println("Visited: " + (Path) dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        search((Path) file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}

class Main {

    public static void main(String[] args) throws IOException {

        String words = "Rafael Nadal,tennis,winner of Roland Garros,BNP Paribas tournament draws";
        Search walk = new Search(words);
        EnumSet opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        for (Path root : dirs) {
            Files.walkFileTree(root, opts, Integer.MAX_VALUE, walk);
        }
        
        System.out.println("____________________________________________________________");
        for(String path_string: walk.documents){
            System.out.println(path_string);
        }
        System.out.println("____________________________________________________________");
               
    }
}
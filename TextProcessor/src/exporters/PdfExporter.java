package exporters;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;

import java.io.FileOutputStream;
import java.util.EnumMap;
import java.util.stream.Collectors;

public class PdfExporter {
    private Document pdfDocument;
    private datamodel.buildingblocks.Document document;
    private String outputFileName;
    private int exportedParagraphsCount = 0;

    private static final EnumMap<FormatEnum, Integer> FORMAT_ENUM_MAP = new EnumMap<>(FormatEnum.class);
    private static final EnumMap<StyleEnum, Integer> STYLE_ENUM_MAP = new EnumMap<>(StyleEnum.class);


    static {
        FORMAT_ENUM_MAP.put(FormatEnum.BOLD, Font.BOLD);
        FORMAT_ENUM_MAP.put(FormatEnum.REGULAR, Font.NORMAL);
        FORMAT_ENUM_MAP.put(FormatEnum.ITALICS, Font.ITALIC);
    }

    static {
        STYLE_ENUM_MAP.put(StyleEnum.NORMAL, 10);
        STYLE_ENUM_MAP.put(StyleEnum.H1, 20);
        STYLE_ENUM_MAP.put(StyleEnum.H2, 30);
    }


    public PdfExporter(datamodel.buildingblocks.Document document, String outputFileName) {
        this.document = document;
        this.outputFileName = outputFileName;
    }

    private void appendParagraphs() throws DocumentException {
        for (LineBlock lineBlock : document.getLineBlocks()) {
            if (!StyleEnum.OMITTED.equals(lineBlock.getStyle())) {
                String paragraphText = lineBlock.getLines().stream().map(Object::toString).collect(Collectors.joining(" "));
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, STYLE_ENUM_MAP.get(lineBlock.getStyle()), FORMAT_ENUM_MAP.get(lineBlock.getFormat()));
                Paragraph paragraph = new Paragraph(paragraphText + "\n\n", font);
                pdfDocument.add(paragraph);
                exportedParagraphsCount++;
            }
        }
    }

    public int export() {
        exportedParagraphsCount = 0;
        try {
            pdfDocument = new Document();
            PdfWriter.getInstance(pdfDocument, new FileOutputStream(outputFileName, false));
            pdfDocument.open();
            appendParagraphs();
            pdfDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exportedParagraphsCount;
    }
}

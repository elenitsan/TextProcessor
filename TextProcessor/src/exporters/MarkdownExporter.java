package exporters;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.EnumMap;
import java.util.stream.Collectors;

public class MarkdownExporter {
    private Document document;
    private String outputFileName;
    private int exportedParagraphsCount = 0;

    private static final EnumMap<FormatEnum, String> FORMAT_ENUM_MAP = new EnumMap<>(FormatEnum.class);
    private static final EnumMap<StyleEnum, String> STYLE_ENUM_MAP = new EnumMap<>(StyleEnum.class);


    static {
        FORMAT_ENUM_MAP.put(FormatEnum.BOLD, "**");
        FORMAT_ENUM_MAP.put(FormatEnum.REGULAR, "");
        FORMAT_ENUM_MAP.put(FormatEnum.ITALICS, "_");
    }

    static {
        STYLE_ENUM_MAP.put(StyleEnum.NORMAL, "");
        STYLE_ENUM_MAP.put(StyleEnum.H1, "#");
        STYLE_ENUM_MAP.put(StyleEnum.H2, "##");
    }

    public MarkdownExporter(Document document, String outputFileName) {
        this.document = document;
        this.outputFileName = outputFileName;
    }

    public void writeFile() throws IOException {
        File outputFile = new File(outputFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        for (LineBlock lineBlock : document.getLineBlocks()) {
            if (!StyleEnum.OMITTED.equals(lineBlock.getStyle())) {
                String paragraphText = lineBlock.getLines().stream().map(Object::toString).collect(Collectors.joining(" "));
                paragraphText = paragraphText.replace("\n", "").replace("\r", "") + " ";
                paragraphText = applyFormat(paragraphText, lineBlock.getFormat(), lineBlock.getStyle());
                bufferedWriter.write(STYLE_ENUM_MAP.get(lineBlock.getStyle()) + paragraphText);
                bufferedWriter.write("\n\n");
                exportedParagraphsCount++;
            }
        }
        bufferedWriter.close();
    }

    private String applyFormat(String paragraphText, FormatEnum format, StyleEnum style) {
        return style == StyleEnum.NORMAL
                ? FORMAT_ENUM_MAP.get(format) + paragraphText + FORMAT_ENUM_MAP.get(format)
                : paragraphText;
    }

    public int export() {
        exportedParagraphsCount = 0;
        try {
            writeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exportedParagraphsCount;
    }
}

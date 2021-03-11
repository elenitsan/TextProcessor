package datamodel.buildingblocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineBlock {

    List<String> lines;
    StyleEnum style = StyleEnum.NORMAL;
    FormatEnum format = FormatEnum.REGULAR;

    public LineBlock(List<String> lines) {
        this.lines = lines;
    }

    public String getStatsAsString() {
        return String.format("Lines: %s\t\tWords: %s", getLines().size(), getNumWords());
    }

    public int getNumWords() {
        int words = 0;
        for (String line : lines) {
            words += line.split("\\s+").length;
        }
        return words;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    public void setFormat(FormatEnum format) {
        this.format = format;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public StyleEnum getStyle() {
        return this.style;
    }

    public FormatEnum getFormat() {
        return this.format;
    }
}

package dataload;

import datamodel.buildingblocks.LineBlock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RawFileLineLoader {
    private BufferedReader fileReader;
    private String previousLine = "-1";
    private String currentLine;

    public void load(String filePath, List<LineBlock> lineblocks) {
        try {
            fileReader = getFileReader(filePath);
            currentLine = fileReader.readLine();
            List<String> lines = new ArrayList<>();
            lines = constructLineBlocks(lineblocks, lines);
            appendLastLineBlock(lineblocks, lines);
            fileReader.close();
        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

    private List<String> constructLineBlocks(List<LineBlock> lineblocks, List<String> lines) throws IOException {
        while (currentLine != null) {
            if (isLineEmpty(currentLine) && !isLineEmpty(previousLine)) {
                lineblocks.add(new LineBlock(lines));
                lines = new ArrayList<>();
            } else {
                if (!isLineEmpty(currentLine)) {
                    lines.add(currentLine);
                }
            }
            previousLine = currentLine; // keep the previous line
            currentLine = fileReader.readLine(); // read the next line
        }
        return lines;
    }

    private boolean isLineEmpty(String line) {
        return line.trim().isEmpty();
    }

    private void appendLastLineBlock(List<LineBlock> lineblocks, List<String> lines) {
        if (!previousLine.trim().isEmpty()) {
            lineblocks.add(new LineBlock(lines));
        }
    }

    private BufferedReader getFileReader(String filePath) {
        try {
            return new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

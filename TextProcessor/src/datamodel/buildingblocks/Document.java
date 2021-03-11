package datamodel.buildingblocks;

import java.util.ArrayList;
import java.util.List;

public class Document {

    public enum DocumentTypeEnum {
        RAW,
        ANNOTATED
    }

    private String filePath;
    private DocumentTypeEnum documentType;
    private List<LineBlock> lineBlockList;

    public Document(String pFilePath, DocumentTypeEnum docType) {
        this.filePath = pFilePath;
        this.documentType = docType;
        this.lineBlockList = new ArrayList<>();
    }

    public List<LineBlock> getLineBlocks() {
        return lineBlockList;
    }

    public DocumentTypeEnum getInputFileType() {
        return documentType;
    }


}

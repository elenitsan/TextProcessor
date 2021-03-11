package datamodel.buildingblocks;

import java.util.HashMap;
import java.util.Map;

public enum FormatEnum {
    BOLD(5),
    ITALICS(6),
    REGULAR(7);

    private int value;
    private static Map<Integer, FormatEnum> map = new HashMap<>();

    FormatEnum(int value) {
        this.value = value;
    }

    static {
        for (FormatEnum formatType : FormatEnum.values()) {
            map.put(formatType.value, formatType);
        }
    }

    public static FormatEnum valueOf(int formatType) {
        return map.get(formatType);
    }

    public int getValue() {
        return value;
    }
}

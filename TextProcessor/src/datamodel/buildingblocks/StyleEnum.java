package datamodel.buildingblocks;

import java.util.HashMap;
import java.util.Map;

public enum StyleEnum {
    OMITTED(1),
    H1(2),
    H2(3),
    NORMAL(4);

    private int value;
    private static Map<Integer, StyleEnum> map = new HashMap<>();

    StyleEnum(int value) {
        this.value = value;
    }

    static {
        for (StyleEnum styleEnum : StyleEnum.values()) {
            map.put(styleEnum.value, styleEnum);
        }
    }

    public static StyleEnum valueOf(int styleType) {
        return map.get(styleType);
    }

    public int getValue() {
        return value;
    }
}

package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

import java.util.List;

public class RuleAllCaps extends AbstractRule {

    public RuleAllCaps() {
        super();
    }

    @Override
    public boolean isValid(LineBlock paragraph) {
        for (String line : paragraph.getLines()) {
            if (!isStringUpperCase(line)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStringUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "This rule is activated when all paragraph's words are CAPS ";
    }
}

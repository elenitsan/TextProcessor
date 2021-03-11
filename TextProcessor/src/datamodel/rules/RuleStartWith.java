package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleStartWith extends AbstractRule {

    String startsWithRule;
    public RuleStartWith(String prefix) {
        super();
        startsWithRule = prefix;
    }

    @Override
    public boolean isValid(LineBlock paragraph) {
        return paragraph.getLines().size() > 0 && paragraph.getLines().get(0).startsWith(startsWithRule);
    }

    @Override
    public String toString() {
        return "This rule is activated when a paragraph starts with " + startsWithRule;
    }
}

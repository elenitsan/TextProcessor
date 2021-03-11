package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RuleInPosition extends AbstractRule {

    private List<LineBlock> pLineblocks;
    private Set<Integer> pPositions;

    public RuleInPosition(List<LineBlock> pLineblocks, List<Integer> pPositions) {
        super();
        this.pLineblocks = pLineblocks;
        this.pPositions = new HashSet<>(pPositions);
    }

    @Override
    public boolean isValid(LineBlock paragraph) {
        int index = -1;
        for (int i = 0; i < pLineblocks.size(); i++) {
            if (pLineblocks.get(i) == paragraph) {
                index = i;
                break;
            }
        }
        return index != -1 && pPositions.contains(index);
    }

    @Override
    public String toString() {
        return "This rule is activated when a paragraph is in these positions " + pPositions;
    }
}

package test;

import datamodel.ruleset.RuleSet;
import datamodel.ruleset.RuleSetCreator;
import engine.Engine;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class RuleSetCreatorTest {
    private static List<String> omList;
    private static List<String> h1List;
    private static List<String> h2List;
    private static List<String> italicsList;
    private static List<List<String>> inputSpec;


    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @Test //testaroume ti methodo pou dimiourgei to ruleset, RuleSetCreator
    public final void testRuleSetCreator() {
        String inputFileName = "Resources/SampleDocs/hippocratesOath.txt";
        Engine engine = new Engine(inputFileName, "RAW", "happyhippo");
        setupProfileHippo();
        RuleSetCreator ruleSetCreator = new RuleSetCreator(engine.getLineblocks(), inputSpec, "test");

        RuleSet ruleSet = ruleSetCreator.createRuleSet();
        String expectedText = "test\n" +
                "OMIT: This rule is activated when a paragraph is in these positions [0, 3]\n" +
                "H1: This rule is activated when a paragraph starts with OATH AND\n" +
                "H2: This rule is activated when all paragraph's words are CAPS \n" +
                "BOLD: UNDEFINED\n" +
                "ITALICS: This rule is activated when a paragraph is in these positions [16, 4]\n";
        assertEquals(expectedText, ruleSet.toString());
    }

    private static String setupProfileHippo() {
        inputSpec = new ArrayList<List<String>>();
        h1List = new ArrayList<String>();
        inputSpec.add(h1List);
        h1List.add("H1");
        h1List.add("STARTS_WITH");
        h1List.add("OATH AND");
        omList = new ArrayList<String>();
        inputSpec.add(omList);
        omList.add("OMIT");
        omList.add("POSITIONS");
        omList.add("0,3");
        h2List = new ArrayList<String>();
        inputSpec.add(h2List);
        h2List.add("H2");
        h2List.add("ALL_CAPS");
        italicsList = new ArrayList<String>();
        inputSpec.add(italicsList);
        italicsList.add("<I>");
        italicsList.add("POSITIONS");
        italicsList.add("4,16");

        String referenceResult = "inputRuleSet2" + "\n" +
                "OMIT:  IN_POS " + "\n" +
                "H1:  STARTS_WITH (OATH AND) " + "\n" +
                "H2:  ALL_CAPS " + "\n" +
                "BOLD:  UNDEFINED " + "\n" +
                "ITALICS:  IN_POS ";

        return referenceResult;
    }

}

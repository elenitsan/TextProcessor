package client;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.StyleEnum;
import engine.Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Client {

    public static final int EXIT_OPTION = 3;
    private final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> TWO_OPTIONS = new ArrayList<>(Arrays.asList("1", "2"));
    private static final ArrayList<String> THREE_OPTIONS = new ArrayList<>(Arrays.asList("1", "2", "3"));
    private static final ArrayList<String> FOUR_OPTIONS = new ArrayList<>(Arrays.asList("1", "2", "3", "4"));
    private static final ArrayList<String> SEVEN_OPTIONS = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
    private static final String STYLE_RULES = "1--Omit Paragraph\n2--Heading 1\n3--Heading 2\n4--Regular";
    private static final String STYLE_RULES_MESSAGE = "The system supports the follow style rules";
    private static final String FORMAT_RULES_MESSAGE = "The system supports the following format rules";
    private static final String FORMAT_RULES = "5--Bold\n6--Italics\n7--Regular";

    private ArrayList<List<String>> usersInputSpec;
    private String filePath;
    private String outputFilePath;
    private String alias;
    private Document.DocumentTypeEnum documentType;

    public static Map<Integer, String> RULE_ENABLE_OPTIONS;
    public static Map<Integer, String> RULE_MAPPING;


    static {
        RULE_ENABLE_OPTIONS = new HashMap<>();
        RULE_ENABLE_OPTIONS.put(1, "POSITIONS");
        RULE_ENABLE_OPTIONS.put(2, "ALL_CAPS");
        RULE_ENABLE_OPTIONS.put(3, "STARTS_WITH");
    }

    static {
        RULE_MAPPING = new HashMap<>();
        RULE_MAPPING.put(1, "OMIT");
        RULE_MAPPING.put(2, "H1");
        RULE_MAPPING.put(3, "H2");
        RULE_MAPPING.put(5, "<B>");
        RULE_MAPPING.put(6, "<I>");
    }

    public Client() {
        print("Welcome to the text editor software");
        print("Do you want to add Format and Style rules?");
        print("Type 1 for YES or 2 for NO\nEnter your option: ");
        int option = getUserInput(TWO_OPTIONS); //  Yes to continue or no
        if (option == 1) {
            usersInputSpec = readRulesFromUser(); //diabase kanones 7 style+format, 3 activation, when to activate
        }
        printMenu();
        option = getUserInput(THREE_OPTIONS);
        if (option != EXIT_OPTION) {
            filePath = getFilePathFromUser();
            alias = getAliasFromUser();
            documentType = option == 1 ? Document.DocumentTypeEnum.RAW : Document.DocumentTypeEnum.ANNOTATED;
            Engine engine = new Engine(filePath, documentType.toString(), alias);
            registerUsersRules(engine, documentType);
            option = -1;
            do {
                printTextEditMenu();
                option = getUserInput(FOUR_OPTIONS);
                handleExportFileOptions(option, engine);
                handleReportStatsOption(option, engine);
            } while (option < 4);
        } else {
            print("I am exiting! Bye bye");
        }
    }

    private void handleReportStatsOption(int option, Engine engine) {
        if (option == 3) {
            for (String row : engine.reportWithStats()) {
                print(row);
            }
        }
    }

    private void handleExportFileOptions(int option, Engine engine) {
        if (option <= 2) {
            outputFilePath = getOutputPathFromUser();
            int outputParagraphs = (option == 1)
                    ? engine.exportPdf(outputFilePath)
                    : engine.exportMarkDown(outputFilePath);
            print(String.format("Exported %s paragraphs", outputParagraphs));
        }
    }

    private void registerUsersRules(Engine engine, Document.DocumentTypeEnum documentType) {
        if (usersInputSpec != null && !usersInputSpec.isEmpty()) {
            if (documentType.equals(Document.DocumentTypeEnum.RAW)) {
                engine.registerInputRuleSetForPlainFiles(usersInputSpec);
            } else {
                engine.registerInputRuleSetForAnnotatedFiles(usersInputSpec, getPrefixesFromUser());
            }
        } else if (documentType.equals(Document.DocumentTypeEnum.ANNOTATED)) {
            engine.registerInputRuleSetForAnnotatedFiles(usersInputSpec, getPrefixesFromUser());
        }
    }

    private List<String> getPrefixesFromUser() {
        List<String> prefixes = new ArrayList<>();
        String prefix;
        boolean addMore;
        do {
            print("Insert a prefix");
            prefix = getSingleTextFromUser();
            prefixes.add(prefix.trim());
            print("Do you want to add an another prefix?\n1 -- Add another one\n2 -- Stop");
            Integer userOption = getUserInput(TWO_OPTIONS);
            addMore = userOption.toString().equals("1");
        } while (addMore);
        return prefixes;
    }

    private String getAliasFromUser() {
        print("Type an alias from the file: ");
        return getSingleTextFromUser();
    }

    private String getFilePathFromUser() {
        print("Give me the filepath: ");
        return getSingleTextFromUser();
    }

    private String getOutputPathFromUser() {
        print("Give me the filepath to save the exported text: ");
        return getSingleTextFromUser();
    }

    private ArrayList<List<String>> readRulesFromUser() {
        print(STYLE_RULES_MESSAGE);
        print(FORMAT_RULES_MESSAGE);
        ArrayList<List<String>> inputSpec = new ArrayList<>();
        String partOne;
        String partTwo;
        String partThree;
        boolean addMore;
        ArrayList<String> rule;
        do {
            partOne = getRuleFirstPart();
            print("part one: " + partOne);
            partTwo = getRuleSecondPart();
            print("part two: " + partTwo);
            partThree = getRuleThirdPart(partTwo);
            print("part 3: " + partThree);
            rule = new ArrayList<>();
            rule.add(partOne.equals(StyleEnum.OMITTED) ? "OMIT" : partOne);
            rule.add(partTwo);
            rule.add(partThree);
            inputSpec.add(rule);
            print("Do you want to add an another rule?\n1 -- Add another one\n2 -- Stop");
            Integer userOption = getUserInput(TWO_OPTIONS);
            addMore = userOption.toString().equals("1");
        } while (addMore);
        return inputSpec;
    }

    private String getRuleFirstPart() {
        print(STYLE_RULES);
        print(FORMAT_RULES);
        print("Insert ");
        int styleFormatOption = getUserInput(SEVEN_OPTIONS);
        return RULE_MAPPING.get(styleFormatOption);
    }

    private String getRuleSecondPart() {
        print("Select when your rule will be activated");
        print(RULE_ENABLE_OPTIONS.keySet().stream().map(s -> s + " -- " + RULE_ENABLE_OPTIONS.get(s) + "\n").collect(Collectors.joining()));
        int activateRuleOption = getUserInput(THREE_OPTIONS);
        return RULE_ENABLE_OPTIONS.get(activateRuleOption);
    }

    private String getRuleThirdPart(String ruleActivationKey) {
        if (ruleActivationKey.equals("POSITIONS")) {
            Set<Integer> paragraphs = readNumsFromCommandLine();
            return paragraphs.stream().map(Object::toString).collect(Collectors.joining(","));
        }
        if (ruleActivationKey.equals("ALL_CAPS")) {
            return "";
        }
        if (ruleActivationKey.equals("STARTS_WITH")) {
            print("Insert the paragraph prefix which enables the rule");
            return scanner.nextLine();
        }
        return "";
    }

    public Set<Integer> readNumsFromCommandLine() {
        print("Please insert paragraph positions separated with spaces and when you want to stop press enter");
        Set<Integer> numbers = new HashSet<>();
        scanner.nextLine();
        Scanner numScanner = new Scanner(scanner.nextLine());
        while (true) {
            if (numScanner.hasNextInt()) {
                int number = numScanner.nextInt();
                numbers.add(number);
            } else {
                System.out.println("You inserted the following numbers : " + numbers.toString());
                break;
            }
        }
        return numbers;
    }

    private Integer getUserInput(ArrayList<String> allowedOptions) {
        try {
            Integer option;
            do {
                while (!scanner.hasNextInt()) {
                    System.out.println("That's not a number!");
                    scanner.next();
                }
                option = scanner.nextInt();
                if (!allowedOptions.contains(option.toString())) {
                    System.out.println("Please try again with a number in range: " + allowedOptions.toString());
                }
            } while (!allowedOptions.contains(option.toString()));
            System.out.println("Got " + option);
            return option;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getSingleTextFromUser() {
        scanner.nextLine();
        return scanner.next();
    }


    private void print(String message) {
        System.out.println(message);
    }

    private void printMenu() {
        print("Menu");
        print("1: Load a RAW text");
        print("2: Load an ANNOTATED text");
        print("3: Exit");
    }

    private void printTextEditMenu() {
        print("Menu");
        print("1: Export to PDF");
        print("2: Export to Markdown");
        print("3: Print file Stats");
        print("4: Exit");

    }

    public static void main(String[] args) {
        new Client();
    }
}

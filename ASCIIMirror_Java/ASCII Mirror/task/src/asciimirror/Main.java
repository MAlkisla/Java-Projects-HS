package asciimirror;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the file path:");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        if (file.exists()) {
            try {
                printFileContent(file);
            } catch (FileNotFoundException e) {
                System.out.println("File not found!");
            }
        } else {
            System.out.println("File not found!");
        }
    }

    public static void printFileContent(File file) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        int maxLength = 0;

        // Read lines from file and find the longest line
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
                maxLength = Math.max(maxLength, line.length());
            }
        }

        // Print modified lines
        for (String line : lines) {
            String modifiedLine = String.format("%-" + maxLength + "s", line);
            String reversedModifiedLine = reverseAndMirror(modifiedLine);
            System.out.println(modifiedLine + " | " + reversedModifiedLine);
        }
    }

    public static String reverseAndMirror(String line) {
        StringBuilder reversed = new StringBuilder();
        for (int i = line.length() - 1; i >= 0; i--) {
            char c = line.charAt(i);
            char reversedChar;
            switch (c) {
                case '<':
                    reversedChar = '>';
                    break;
                case '>':
                    reversedChar = '<';
                    break;
                case '[':
                    reversedChar = ']';
                    break;
                case ']':
                    reversedChar = '[';
                    break;
                case '{':
                    reversedChar = '}';
                    break;
                case '}':
                    reversedChar = '{';
                    break;
                case '(':
                    reversedChar = ')';
                    break;
                case ')':
                    reversedChar = '(';
                    break;
                case '/':
                    reversedChar = '\\';
                    break;
                case '\\':
                    reversedChar = '/';
                    break;
                default:
                    reversedChar = c;
            }
            reversed.append(reversedChar);
        }
        return reversed.toString();
    }
}

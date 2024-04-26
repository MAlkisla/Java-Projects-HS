package bullscows;

import java.util.Scanner;
import java.util.Random;
public class Main {
    private static int turn = 0;

    public static void main(String[] args) {
        System.out.println("Input the length of the secret code:");
        var sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.out.println("Error: \"" + sc.nextLine() + "\" isn't a valid number.");
            System.exit(0);
        }
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Error: the length of the secret code must be greater than 0.");
            System.exit(0);
        }

        System.out.println("Input the number of possible symbols in the code:");
        if (!sc.hasNextInt()) {
            System.out.println("Error: \"" + sc.nextLine() + "\" isn't a valid number.");
            System.exit(0);
        }
        int range = sc.nextInt();

        if (n > range || range > 36) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n", n, range);
            System.exit(0);
        }

        System.out.println("Okay, let's start a game!");
        extractRandom(n, range);
    }

    private static void extractRandom(int n, int range) {
        Random random = new Random();
        StringBuilder str = new StringBuilder();

        while (str.length() < n) {
            int symbol = random.nextInt(range);
            char ch = symbol < 10 ? (char) ('0' + symbol) : (char) ('a' + symbol - 10);
            if (str.indexOf(Character.toString(ch)) == -1) {
                str.append(ch);
            }
        }

        String usedCharacters = range <= 10 ? String.format("(0-%d)", range - 1) :
                range <= 11 ? String.format("(0-9, a)") :
                        String.format("(0-9, a-%c)", 'a' + range - 11);

        System.out.printf("The secret is prepared: %s %s.%n", "*".repeat(n), usedCharacters);
        grade(str.toString(), n);
    }

    private static void grade(String code, int n) {
        System.out.printf("Turn %d:",++turn);
        var sc = new Scanner(System.in);
        String userCode = sc.nextLine();
        int cow = 0;
        int bull = 0;
        for(int i = 0; i < userCode.length(); i++){
            if(code.charAt(i) == userCode.charAt(i)){
                bull++;
            }else if(code.contains(""+ userCode.charAt(i))){
                cow++;
            }
        }
        if(bull == n){
            System.out.println("Grade: "+bull+(bull>1?" bulls":" bull"));
            System.out.println("Congratulations! You guessed the secret code.");
        }else{
            String c = cow == 0?"":cow==1?cow+" cow":cow+" cows";
            String b = bull == 0?"":bull==1?bull+" bull":bull+" bulls";
            if(bull == 0 && cow == 0){
                System.out.println("None");
            }else{
                System.out.println("Grade: "+b+(cow>0 && bull>0?" and ":"")+c);
            }
            grade(code, n);
        }
    }
}
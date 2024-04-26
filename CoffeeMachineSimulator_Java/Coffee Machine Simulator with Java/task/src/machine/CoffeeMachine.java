import java.util.Scanner;

class CoffeeMachine {
    private int money = 550;
    private int water = 400;
    private int milk = 540;
    private int beans = 120;
    private int cups = 9;

    private enum State {
        CHOOSING_ACTION, BUYING_COFFEE, FILLING_WATER, FILLING_MILK, FILLING_BEANS, FILLING_CUPS
    }

    private State currentState = State.CHOOSING_ACTION;

    public String processInput(String input) {
        switch (currentState) {
            case CHOOSING_ACTION:
                return handleAction(input);
            case BUYING_COFFEE:
                return handleCoffeeChoice(input);
            case FILLING_WATER:
                return handleWater(input);
            case FILLING_MILK:
                return handleMilk(input);
            case FILLING_BEANS:
                return handleBeans(input);
            case FILLING_CUPS:
                return handleCups(input);
            default:
                return "Invalid state!";
        }
    }

    private String handleAction(String action) {
        switch (action) {
            case "buy":
                currentState = State.BUYING_COFFEE;
                return "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:";
            case "fill":
                currentState = State.FILLING_WATER;
                return "Write how many ml of water you want to add:";
            case "take":
                return take();
            case "remaining":
                return displayMachineState();
            case "exit":
                System.exit(0);
            default:
                return "Invalid action!";
        }
    }

    private String handleCoffeeChoice(String choice) {
        switch (choice) {
            case "1":
                currentState = State.CHOOSING_ACTION;
                return makeCoffee(250, 0, 16, 4);
            case "2":
                currentState = State.CHOOSING_ACTION;
                return makeCoffee(350, 75, 20, 7);
            case "3":
                currentState = State.CHOOSING_ACTION;
                return makeCoffee(200, 100, 12, 6);
            case "back":
                currentState = State.CHOOSING_ACTION;
                return "";
            default:
                return "Invalid choice!";
        }
    }

    private String handleWater(String input) {
        water += Integer.parseInt(input);
        currentState = State.FILLING_MILK;
        return "Write how many ml of milk you want to add:";
    }

    private String handleMilk(String input) {
        milk += Integer.parseInt(input);
        currentState = State.FILLING_BEANS;
        return "Write how many grams of coffee beans you want to add:";
    }

    private String handleBeans(String input) {
        beans += Integer.parseInt(input);
        currentState = State.FILLING_CUPS;
        return "Write how many disposable cups you want to add:";
    }

    private String handleCups(String input) {
        cups += Integer.parseInt(input);
        currentState = State.CHOOSING_ACTION;
        return "";
    }

    private String displayMachineState() {
        return "The coffee machine has:\n" + water + " ml of water\n" + milk + " ml of milk\n" + beans + " g of coffee beans\n" + cups + " disposable cups\n" + "$" + money + " of money\n";
    }

    private String take() {
        String result = "I gave you $" + money;
        money = 0;
        currentState = State.CHOOSING_ACTION;
        return result;
    }
    private String makeCoffee(int waterNeeded, int milkNeeded, int beansNeeded, int cost) {
        if (water < waterNeeded) {
            return "Sorry, not enough water!";
        } else if (milk < milkNeeded) {
            return "Sorry, not enough milk!";
        } else if (beans < beansNeeded) {
            return "Sorry, not enough coffee beans!";
        } else if (cups < 1) {
            return "Sorry, not enough disposable cups!";
        } else {
            water -= waterNeeded;
            milk -= milkNeeded;
            beans -= beansNeeded;
            cups--;
            money += cost;
            return "I have enough resources, making you a coffee!";
        }
    }
}
class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String input = scanner.nextLine();
            String output = coffeeMachine.processInput(input);
            if (!output.isEmpty()) {
                System.out.println(output);
            }
        }
    }
}
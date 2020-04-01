package easy.coffee_machine;

import java.util.Scanner;

public class Main {

    private static int milk;
    private static int coffee;
    private static int water;
    private static int cups;
    private static int money;
    private static int drinkType;
    private static int fillStep;
    private static MachineState state;
    private static boolean machinePower;

    public enum MachineState {
        ACTION,
        BUY,
        FILL,
        TAKE,
        REMAINING,
        EXIT
    }

    public static void main(String[] args) {
        water = 400;
        milk = 540;
        coffee = 120;
        cups = 9;
        money = 550;
        drinkType = 0;
        fillStep = 0;
        state = MachineState.ACTION;
        machinePower = true;

        Scanner scanner = new Scanner(System.in);

        while (machinePower) {
            if (state == MachineState.ACTION) {
                printLine("Write action (buy, fill, take, remaining, exit):");
            }

            if (state == MachineState.FILL) {
                switch (fillStep) {
                    case 0:
                        printLine("How many ml of water do you want to add:");
                        break;
                    case 1:
                        printLine("How many ml of milk do you want to add:");
                        break;
                    case 2:
                        printLine("How many grams of coffee beans do you want to add:");
                        break;
                    case 3:
                        printLine("How many disposable cups do you want to add:");
                        break;
                    default:
                }
            }

            processUserInput(scanner.nextLine());
        }
    }

    private static void printLine(String text, Object ...args) {
        System.out.printf(text + "%n", args);
    }

    private static void resetAction() {
        state = MachineState.ACTION;
    }

    private static void processUserInput(String input) {

        if (input.matches("(buy)|(fill)|(take)|(remaining)|(exit)")) {
            state = MachineState.valueOf(input.toUpperCase());
        }

        switch (state) {
            case BUY:
                if (input.equals("buy")) {
                    printLine("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                } else {
                    if (input.equals("back")) {
                        break;
                    } else {
                        drinkType = Integer.parseInt(input);
                    }

                    invokeBuyAction();
                    resetAction();
                }

                break;
            case FILL:
                if (!input.equals("fill")) {
                    switch (fillStep) {
                        case 0:
                            water += Integer.parseInt(input);
                            fillStep++;
                            break;
                        case 1:
                            milk += Integer.parseInt(input);
                            fillStep++;
                            break;
                        case 2:
                            coffee += Integer.parseInt(input);
                            fillStep++;
                            break;
                        case 3:
                            cups += Integer.parseInt(input);
                            fillStep = 0;
                            break;
                        default:
                    }
                }

                break;
            case TAKE:
                printLine("I will give you $%d", money);
                money = 0;
                resetAction();

                break;
            case REMAINING:
                printMachineState();
                resetAction();

                break;
            case EXIT:
                machinePower = false;
                System.exit(0);

                break;
            default:
        }
    }

    private static void printMachineState() {
        printLine("The coffee machine has:");
        printLine("%d of water", water);
        printLine("%d of milk", milk);
        printLine("%d of coffee beans", coffee);
        printLine("%d of disposable cups", cups);
        printLine("%d of money", money);
    }

    private static void calculateBuyAction(int w, int m, int c, int cost) {
        if (w <= water && m == 0 && c <= coffee) {
            water -= w;
            coffee -= c;
            money += cost;
            cups--;
            printLine("I have enough resources, making you a coffee!");
        } else if (w <= water && m <= milk && c <= coffee) {
            water -= w;
            milk -= m;
            coffee -= c;
            money += cost;
            cups--;
            printLine("I have enough resources, making you a coffee!");
        } else if (w > water) {
            printLine("Sorry, not enough water!");
        } else if (m > milk) {
            printLine("Sorry, not enough milk!");
        } else {
            printLine("Sorry, not enough coffee!");
        }
    }

    private static void invokeBuyAction() {
        switch (drinkType) {
            case 1:
                calculateBuyAction(250, 0, 16, 4);
                break;
            case 2:
                calculateBuyAction(350, 75, 20, 7);
                break;
            case 3:
                calculateBuyAction(200, 100, 12, 6);
                break;
            default:
        }
    }
}


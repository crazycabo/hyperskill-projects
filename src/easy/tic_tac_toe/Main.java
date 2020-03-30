package easy.tic_tac_toe;

import java.util.Scanner;

public class Main {

    private static int emptyCells;
    private static int xScore;
    private static int oScore;
    private static int xCount;
    private static int oCount;
    private static char[][] gameBoard;

    public static void main(String[] args) {
        emptyCells = 0;
        xScore = 0;
        oScore = 0;
        xCount = 0;
        oCount = 0;
        gameBoard = new char[][] {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        playGame();
    }

    private static void printOutput() {
        if (emptyCells > 0 && xScore == 0 && oScore == 0) {
            if (xCount - oCount >= 2 || oCount - xCount >= 2) {
                System.out.println("Impossible");
            } else {
                System.out.println("Game not finished");
            }
        } else {
            if (xScore == oScore) {
                if (xScore == 1) {
                    System.out.println("Impossible");
                } else {
                    System.out.println("Draw");
                }
            } else if (xScore > oScore){
                System.out.println("X wins");
            } else {
                System.out.println("O wins");
            }
        }
    }

    private static void printBoardState() {
        System.out.println("---------");

        for (char[] row : gameBoard) {
            System.out.printf("| %s %s %s |%n", row[0], row[1], row[2]);
        }

        System.out.println("---------");
    }

    private static void checkScore() {
        // Check score rows
        for (char[] row : gameBoard) {
            String scoreRow = String.format("%c%c%c", row[0], row[1], row[2]);
            if (scoreRow.matches("XXX")) {
                xScore++;
            }
            if (scoreRow.matches("OOO")) {
                oScore++;
            }
        }

        // Check score columns
        for (int i = 0; i < 3; i++) {
            String scoreColumn = String.format("%c%c%c", gameBoard[0][i], gameBoard[1][i], gameBoard[2][i]);
            if (scoreColumn.matches("XXX")) {
                xScore++;
            }
            if (scoreColumn.matches("OOO")) {
                oScore++;
            }
        }

        // Check score diagonals
        String scoreDiagonal1 = String.format("%c%c%c", gameBoard[0][0], gameBoard[1][1], gameBoard[2][2]);
        String scoreDiagonal2 = String.format("%c%c%c", gameBoard[0][2], gameBoard[1][1], gameBoard[2][0]);
        if (scoreDiagonal1.matches("XXX") || scoreDiagonal2.matches("XXX")) { xScore++; }
        if (scoreDiagonal1.matches("OOO") || scoreDiagonal2.matches("OOO")) { oScore++; }
    }

    private static void playGame() {
        boolean getCoordinates = true;

        while (getCoordinates) {
            printBoardState();
            System.out.println("Enter the coordinates: ");

            try {
                Scanner scanner = new Scanner(System.in);
                int[] inputs = new int[]{scanner.nextInt(), scanner.nextInt()};

                if (inputs[0] <= 3 && inputs[1] <= 3) {
                    if (inputs[1] == 3) {
                        inputs[1] = 1;
                    } else if (inputs[1] == 1) {
                        inputs[1] = 3;
                    }

                    char currentBox = gameBoard[inputs[1] - 1][inputs[0] - 1];

                    if (currentBox == ' ') {
                        gameBoard[inputs[1] - 1][inputs[0] - 1] = 'X';
                    } else {
                        System.out.println("This cell is occupied! Choose another one!");
                    }

                    checkScore();
                } else {
                    System.out.println("Coordinates should be from 1 to 3!");
                }
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                getCoordinates = false;
            }
        }

        printBoardState();
        printOutput();
    }
}

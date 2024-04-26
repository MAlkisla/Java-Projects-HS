import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] grid = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }

        printGrid(grid);

        int moves = 0;
        while (true) {
            System.out.print("Enter the coordinates: ");
            try {
                int x = scanner.nextInt() - 1;
                int y = scanner.nextInt() - 1;

                if (x >= 0 && x < 3 && y >= 0 && y < 3) {
                    if (grid[x][y] == ' ') {
                        grid[x][y] = moves % 2 == 0 ? 'X' : 'O';
                        moves++;
                        printGrid(grid);

                        if (hasWon(grid, 'X')) {
                            System.out.println("X wins");
                            break;
                        } else if (hasWon(grid, 'O')) {
                            System.out.println("O wins");
                            break;
                        } else if (moves == 9) {
                            System.out.println("Draw");
                            break;
                        }
                    } else {
                        System.out.println("This cell is occupied! Choose another one!");
                    }
                } else {
                    System.out.println("Coordinates should be from 1 to 3!");
                }
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
            }
        }
    }

    private static void printGrid(char[][] grid) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static boolean hasWon(char[][] grid, char player) {
        for (int i = 0; i < 3; i++) {
            if ((grid[i][0] == player && grid[i][1] == player && grid[i][2] == player) ||
                (grid[0][i] == player && grid[1][i] == player && grid[2][i] == player)) {
                return true;
            }
        }

        return (grid[0][0] == player && grid[1][1] == player && grid[2][2] == player) ||
               (grid[0][2] == player && grid[1][1] == player && grid[2][0] == player);
    }
}

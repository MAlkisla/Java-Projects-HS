package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        char[][] seats = new char[rows][seatsPerRow];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                seats[i][j] = 'S';
            }
        }

        int choice;
        int purchasedTickets = 0;
        int currentIncome = 0;

        do {
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    printCinema(rows, seatsPerRow, seats);
                    break;
                case 2:
                    int price = buyTicket(rows, seatsPerRow, seats, scanner);
                    if (price > 0) {
                        purchasedTickets++;
                        currentIncome += price;
                    }
                    break;
                case 3:
                    printStatistics(purchasedTickets, rows, seatsPerRow, currentIncome);
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void printCinema(int rows, int seatsPerRow, char[][] seats) {
        System.out.println("Cinema:");

        System.out.print("  ");
        for (int i = 1; i <= seatsPerRow; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < seatsPerRow; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int buyTicket(int rows, int seatsPerRow, char[][] seats, Scanner scanner) {
        int chosenRow;
        int chosenSeat;

        while (true) {
            System.out.println("Enter a row number:");
            chosenRow = scanner.nextInt() - 1;
            System.out.println("Enter a seat number in that row:");
            chosenSeat = scanner.nextInt() - 1;

            if (chosenRow < 0 || chosenRow >= rows || chosenSeat < 0 || chosenSeat >= seatsPerRow) {
                System.out.println("Wrong input!");
            } else if (seats[chosenRow][chosenSeat] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                break;
            }
        }

        int totalSeats = rows * seatsPerRow;
        int ticketPrice;

        if (totalSeats <= 60) {
            ticketPrice = 10;
        } else {
            int frontHalfRows = rows / 2;
            ticketPrice = (chosenRow < frontHalfRows) ? 10 : 8;
        }

        System.out.println("Ticket price: $" + ticketPrice);
        seats[chosenRow][chosenSeat] = 'B';

        return ticketPrice;
    }

    private static void printStatistics(int purchasedTickets, int rows, int seatsPerRow, int currentIncome) {
        int totalSeats = rows * seatsPerRow;
        double percentage = 100.0 * purchasedTickets / totalSeats;
        int totalIncome;
        if (totalSeats <= 60) {
            totalIncome = totalSeats * 10;
        } else {
            int frontHalfRows = rows / 2;
            int backHalfRows = rows - frontHalfRows;
            totalIncome = frontHalfRows * seatsPerRow * 10 + backHalfRows * seatsPerRow * 8;
        }
    
        System.out.printf("Number of purchased tickets: %d%n", purchasedTickets);
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.printf("Current income: $%d%n", currentIncome);
        System.out.printf("Total income: $%d%n", totalIncome);
    }
}
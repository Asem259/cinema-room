import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static int[][] cinemaHall;
    private static int bookedSeats;
    private static int income;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        int rowsNumber = input.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int colsNumber = input.nextInt();

        createCinemaHall(rowsNumber, colsNumber);
        boolean isExit = false;

        while (!isExit) {
            showMenu();
            int option = input.nextInt();
            if (option < 0 || option > 3) {
                System.out.println("Please enter correct number:");
            } else {
                switch (option) {
                    case 1 -> printCinemaHall();
                    case 2 -> buyTicket();
                    case 3 -> showStatistics();
                    case 0 -> isExit = true;

                }
            }
        }


    }


    static void createCinemaHall(int rowsNumber, int colsNumber) {
        cinemaHall = new int[rowsNumber][colsNumber];
        bookedSeats = 0;
        income = 0;
        for (int[] row : cinemaHall) {
            Arrays.fill(row, 'S');
        }
    }

    static void showMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    static void printCinemaHall() {
        System.out.println("Cinema:");
        for (int rowNumber = 0; rowNumber < cinemaHall.length + 1; rowNumber++) {
            for (int col = 0; col < cinemaHall[0].length + 1; col++) {
                if (rowNumber == 0 && col == 0) {
                    System.out.print("  ");
                } else if (rowNumber == 0) {
                    System.out.print(col + " ");
                } else if (col == 0) {
                    System.out.print(rowNumber + " ");
                } else {
                    System.out.print((char) cinemaHall[rowNumber - 1][col - 1] + " ");
                }
            }
            System.out.println();
        }
    }

    static void buyTicket() {
        boolean isCompleted = false;

        while (!isCompleted) {
            System.out.println("Enter a row number:");
            int row = input.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat = input.nextInt();
            if (row > cinemaHall.length || seat > cinemaHall[0].length) {
                System.out.println("Wrong input!");
                continue;
            }
            boolean isAvailable = checkAvailable(row - 1, seat - 1);
            if (!isAvailable) {
                System.out.println("That ticket has already been purchased!");
                continue;
            }
            updateCinemaHall(row - 1, seat - 1);
            calculatePrice(row);
            isCompleted = true;
        }
    }

    static void showStatistics() {
        System.out.println("Number of purchased tickets: " + bookedSeats);
        System.out.printf("Percentage: %.2f%%%n", getPercentage());
        System.out.println("Current income: $" + income);
        System.out.println("Total income: $" + calculateTotalIncome());
        System.out.println();
    }


    static void updateCinemaHall(int row, int col) {
        cinemaHall[row][col] = 'B';
        bookedSeats += 1;
    }

    static boolean checkAvailable(int row, int col) {
        return cinemaHall[row][col] != 'B';
    }


    static double getPercentage() {
        int totalSeats = cinemaHall[0].length * cinemaHall.length;
        return ((double) bookedSeats / totalSeats) * 100;
    }

    static int calculateTotalIncome() {
        int colsNumber = cinemaHall[0].length;
        int rowsNumber = cinemaHall.length;

        if (rowsNumber * colsNumber <= 60) return colsNumber * rowsNumber * 10;

        int frontRowsIncome = colsNumber / 2 * colsNumber * 10;
        int backRowsIncome = (colsNumber - colsNumber / 2) * colsNumber * 8;

        return frontRowsIncome + backRowsIncome;
    }


    static void calculatePrice(int row) {
        int totalSeats = cinemaHall.length * cinemaHall[0].length;

        boolean isMoreThan60 = totalSeats > 60;
        boolean isSeatInFront = isMoreThan60 && row <= cinemaHall.length / 2;

        int price = isSeatInFront || !isMoreThan60 ? 10 : 8;
        income += price;
        System.out.println("Ticket price: $" + price);
    }

}
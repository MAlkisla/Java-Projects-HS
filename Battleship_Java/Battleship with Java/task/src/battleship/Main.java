package battleship;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Board board = new Board(10);
        Ship aircraftCarrier = new Ship("Aircraft Carrier", 5);
        Ship battleship = new Ship("Battleship", 4);
        Ship submarine = new Ship("Submarine", 3);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship destroyer = new Ship("Destroyer", 2);
        List<Ship> ships = new ArrayList<>();
        ships.add(aircraftCarrier);
        ships.add(battleship);
        ships.add(submarine);
        ships.add(cruiser);
        ships.add(destroyer);

        BattleshipGame game = new BattleshipGame(board, ships);

        game.placeShips();

        game.start();

        while (!game.areAllBootsSunken()) {
            game.shoot();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }
}

class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

class Board {
    private final int size;
    private final String[][] gameBoard;

    public Board(int size) {
        this.size = size;
        this.gameBoard = new String[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(gameBoard[i], "~");
        }
    }

    public void showBoard(boolean fogOfWar) {
        StringBuilder sb = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < this.gameBoard.length; i++) {
            sb.append(Character.toString((char) i + 65));
            for (int j = 0; j < this.gameBoard[0].length; j++) {
                sb.append(" ");
                if (fogOfWar && Objects.equals(this.gameBoard[i][j], "O")) {
                    sb.append("~");
                } else {
                    sb.append(this.gameBoard[i][j]);
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public Position convertFieldToPosition(String field) {
        int x = ((int) field.charAt(0)) - 65;
        int y = Integer.parseInt(field.substring(1)) - 1;
        return new Position(x, y);
    }

    public void occupyField(Position position, String symbol) {
        gameBoard[position.getX()][position.getY()] = symbol;
    }

    public boolean isPositionOnBoard(Position position) {
        return position.getX() < this.size && position.getY() < this.size && position.getX() >= 0 && position.getY() >= 0;
    }

    public boolean arePositionsHorizontalOrVertical(Position position1, Position position2) {
        return position1.getX() == position2.getX() || position1.getY() == position2.getY();
    }

    public boolean isPositionOccupied(Position position, String symbol) {
        return Objects.equals(gameBoard[position.getX()][position.getY()], symbol);
    }
}

class Ship {
    private final String name;
    private final int length;
    private List<Position> positions;

    private int hits;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.positions = new ArrayList<>();
        this.hits = 0;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public boolean isSunken() {
        return hits == length;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public void incrementsHits() {
        hits += 1;
    }


}

class BattleshipGame {

    private final Board board;

    private final List<Ship> ships;

    public BattleshipGame(Board board, List<Ship> ships) {
        this.board = board;
        this.ships = ships;
    }

    public void start() {
        System.out.println("The game starts!");
        board.showBoard(true);
    }

    public void placeShips() {
        board.showBoard(false);
        Scanner scanner = new Scanner(System.in);
        for (Ship ship : ships) {
            System.out.println("Enter the coordinates of the " + ship.getName() + " " + "(" + ship.getLength() + " cells):");
            while (true) {
                String input = scanner.nextLine();
                String firstField = input.split(" ")[0];
                String lastField = input.split(" ")[1];

                Position firstPosition = board.convertFieldToPosition(firstField);
                Position lastPosition = board.convertFieldToPosition(lastField);

                if (!arePositionsValid(firstPosition, lastPosition)) {
                    System.out.println("Error! Wrong ship location! Try again:");
                    continue;
                }

                List<Position> positions = calculateFullShipPosition(firstPosition, lastPosition);

                if (!arePositionsAccordingToShipLength(positions, ship)) {
                    System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
                    continue;
                }

                if (!arePositionsDistantFromOtherShips(positions)) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    continue;
                }

                ship.setPositions(positions);
                for (Position position : ship.getPositions()) {
                    this.board.occupyField(position, "O");
                }

                board.showBoard(false);
                break;
            }
        }
        scanner.close();
    }

    public void shoot() {
        System.out.println("Take a shot!");
        Scanner scanner = new Scanner(System.in);
        Position position;
        while (true) {
            String field = scanner.nextLine();
            position = board.convertFieldToPosition(field);
            if (!board.isPositionOnBoard(position)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            break;
        }
        scanner.close();

        if (board.isPositionOccupied(position, "~")) {
            board.occupyField(position, "M");
            board.showBoard(true);
            System.out.println("You missed. Try again:");

        } else if (board.isPositionOccupied(position, "X") || board.isPositionOccupied(position, "M")) {
            board.showBoard(true);
            System.out.println("You hit a ship! Try again:");

        } else if (board.isPositionOccupied(position, "O")) {
            board.occupyField(position, "X");
            board.showBoard(true);
            Ship hitShip = retrieveHitShip(position);
            assert hitShip != null;
            hitShip.incrementsHits();
            if (hitShip.isSunken()) {
                System.out.println("You sank a ship! Specify a new target:");
            } else {
                System.out.println("You hit a ship! Try again:");
            }
        }
    }

    public List<Position> calculateFullShipPosition(Position position1, Position position2) {
        if (position1.getY() > position2.getY()) {
            int temp = position1.getY();
            position1.setY(position2.getY());
            position2.setY(temp);
        } else if (position1.getX() > position2.getX()) {
            int temp = position1.getX();
            position1.setX(position2.getX());
            position2.setX(temp);
        }

        List<Position> positions = new ArrayList<>();
        if (position1.getX() == position2.getX()) {
            for (int i = position1.getY(); i <= position2.getY(); i++) {
                Position position = new Position(position1.getX(), i);
                positions.add(position);
            }
        } else {
            for (int i = position1.getX(); i <= position2.getX(); i++) {
                Position position = new Position(i, position1.getY());
                positions.add(position);
            }
        }
        return positions;
    }

    public Ship retrieveHitShip(Position position) {
        for (Ship ship : ships) {
            for (Position shipPosition : ship.getPositions()) {
                if (position.getX() == shipPosition.getX() && position.getY() == shipPosition.getY()) {
                    return ship;
                }
            }
        }
        return null;
    }

    public boolean arePositionsValid(Position position1, Position position2) {
        return board.isPositionOnBoard(position1) && board.isPositionOnBoard(position2) && board.arePositionsHorizontalOrVertical(position1, position2);
    }

    public boolean arePositionsAccordingToShipLength(List<Position> positions, Ship ship) {
        return positions.size() == ship.getLength();
    }

    public boolean arePositionsDistantFromOtherShips(List<Position> positions) {
        List<Position> directions = new ArrayList<>();
        directions.add(new Position(0, 1));
        directions.add(new Position(0, -1));
        directions.add(new Position(1, 0));
        directions.add(new Position(-1, 0));

        for (Position direction : directions) {
            for (Position position : positions) {
                Position neighbourPosition = new Position(position.getX() + direction.getX(), position.getY() + direction.getY());
                if (board.isPositionOnBoard(neighbourPosition)) {
                    if (board.isPositionOccupied(neighbourPosition, "O")) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    public boolean areAllBootsSunken() {
        for (Ship ship : ships) {
            if (!ship.isSunken()) {
                return false;
            }
        }
        return true;
    }
}

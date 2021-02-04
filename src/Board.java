import java.util.ArrayList;
import java.util.List;

public class Board {

    public int[][] board;
    public Board parentBoard;

    private int[] courserCoordinateXY;
    private int[] elementCoordinateXY;
    private boolean notEqual;

    public int totalHeuristic;
    public int heuristic;
    public int uniformCostValue;
    private int[][] finalBoardState;

    Board (int[][] initValue) {
        this.uniformCostValue = 0;
        this.board = new int[3][3];
        finalBoardState = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = initValue[i][j];
            }
        }

        this.calculateHeuristic();
    }

    Board (int[][] initValue, Board parent) {
        this.board = new int[3][3];
        this.parentBoard = parent;
        this.uniformCostValue = parent.uniformCostValue + 1;

        finalBoardState = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = initValue[i][j];
            }
        }

        this.calculateHeuristic();
    }

    Board (int[][] initValue, Board parent, int heuristic, int uniformCostValue) {
        this.board = initValue;
        this.parentBoard = parent;
        this.totalHeuristic = heuristic;
        this.uniformCostValue = uniformCostValue;
        this.heuristic = this.totalHeuristic - this.uniformCostValue;
    }



    public List<Board> returnPotentialMove() {

        List<Board> possibleOptionsToMove= new ArrayList<>();

        findCourser();
        int yCoordinate = this.courserCoordinateXY[0];
        int xCoordinate = this.courserCoordinateXY[1];

        int valueYmin = yCoordinate - 1;
        int valueYmax = yCoordinate + 1;

        int valueXmin = xCoordinate - 1;
        int valueXmax = xCoordinate + 1;

        if (valueYmin >= 0) {
            this.swapValue(xCoordinate, yCoordinate, xCoordinate, valueYmin);
            Board possibleMove = new Board(this.getBoard(), this);
            possibleOptionsToMove.add(possibleMove);

            this.swapValue(xCoordinate, valueYmin, xCoordinate, yCoordinate);
        }

        if (valueYmax != 3) {
            this.swapValue(xCoordinate, yCoordinate, xCoordinate, valueYmax);
            Board possibleMove = new Board(this.getBoard(), this);
            possibleOptionsToMove.add(possibleMove);
            this.swapValue(xCoordinate, valueYmax, xCoordinate, yCoordinate);
        }


        if (valueXmin >= 0) {
            this.swapValue(xCoordinate, yCoordinate, valueXmin, yCoordinate);
            Board possibleMove = new Board(this.getBoard(), this);
            possibleOptionsToMove.add(possibleMove);
            this.swapValue(valueXmin, yCoordinate, xCoordinate, yCoordinate);
        }


        if (valueXmax != 3) {
            this.swapValue(xCoordinate, yCoordinate, valueXmax, yCoordinate);
            Board possibleMove = new Board(this.getBoard(), this);
            possibleOptionsToMove.add(possibleMove);
            this.swapValue(valueXmax, yCoordinate, xCoordinate, yCoordinate);
        }


        return possibleOptionsToMove;
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println("");
            for (int j = 0; j < 3; j++) {
                System.out.print("|" + this.board[i][j]);
                if(j == 2) {
                    System.out.print("|");
                }
            }
        }
    }

    public void printBoardParentTree() {
        System.out.println(" ");
        System.out.println("Path => ");
        this.printBoard();
        System.out.println(" ");
        if (this.parentBoard == null) {
            System.out.println("Start Point");
        } else {
            this.parentBoard.printBoardParentTree();
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public Board copyBoardReference() {
        return new Board(this.board, this.parentBoard, this.totalHeuristic,this.uniformCostValue);
    }

    public boolean checkEquality(Board board) {
        notEqual = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(this.board[i][j] != board.board[i][j]) {
                    notEqual = false;
                }
            }
        }
        return notEqual;
    }


    private void calculateHeuristic() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int valueInit = this.board[i][j];
                int yInit = i;
                int xInit = j;
                int[] yxFinal = this.findPosition(valueInit);
                int corWeight = Math.abs((yxFinal[0]- yInit)) + Math.abs((yxFinal[1] - xInit));
                this.totalHeuristic += corWeight;
            }
        }
        this.totalHeuristic += this.uniformCostValue;
        this.heuristic = this.totalHeuristic - this.uniformCostValue;
    }

    private void swapValue(int x1Value, int y1Value, int x2Value, int y2Value) {
        int aValue;
        aValue = this.board[x1Value][y1Value];
        this.board[x1Value][y1Value] = this.board[x2Value][y2Value];
        this.board[x2Value][y2Value] = aValue;
    }

    private int[] findPosition(int valueToFind) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.finalBoardState[i][j] == valueToFind) {
                    this.elementCoordinateXY = new int[]{i, j};
                    // Stop finding
                    i = 4;
                    break;
                }
            }
        }
        return this.elementCoordinateXY;
    }

    private void findCourser() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] == 0 ) {
                    this.courserCoordinateXY = new int[]{j, i};
                    // Stop finding
                    i = 4;
                    break;
                }
            }
        }
    }


}

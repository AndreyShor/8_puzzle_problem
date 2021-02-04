import java.util.Arrays;
import java.util.List;

public class start {
    public static void main(String[] args) {

        // Change value here to start program
//        int[] startState = {5, 1, 3, 0, 2, 6, 4, 7, 8};
////        test.A_algorithm(startState);
//        test_2.a_algorithm(startState);

//        int[][] startState = { {1, 2, 5}, {0, 8, 3}, {7, 4, 6}};
        int[][] startState = { {6, 4, 2}, {5, 7, 3}, {0, 1, 8}};
        Board board = new Board(startState);

//        for(Board move: moves) {
//            System.out.println(" ");
//            System.out.println("Possible move => ");
//            move.printBoard();
//            System.out.println("Move heuristic: " + move.totalHeuristic);
//            System.out.println(" ");
//        }

        puzzleGameFinder puzzle = new puzzleGameFinder();
        puzzle.aStarFinder(startState);
        puzzle.printExpandedListSize();
        puzzle.printUnexpandedListSize();

//        puzzle.printUnexpandedList();
//        puzzle.printExpandedList();


    }
}

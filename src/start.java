import java.util.List;

public class start {
    public static void main(String[] args) {
        AI test = new AI();
        System.out.println("Start state");
        int[] startState = {8, 3, 5, 4, 1, 6, 2, 7, 0};
//        test.start_state(startState);
//        System.out.println("Goal State");
//        test.setGoal_state();
//        List<Integer> reultSearech = test.findXYCourser(test.start_state);
//        System.out.println("Y value is " + reultSearech.get(0));
//        System.out.println("X value is " + reultSearech.get(1));
//        test.genPossibleXY(reultSearech);
//
//        test.generateMoveMatrix(reultSearech);
//        System.out.println("Possible move:");
//        System.out.println(test.generateMoveMatrix(reultSearech));
//        test.calcHeuristicValue(test.start_state);
//
//        Object[] moveCost= new Object[2];
//
//        System.out.println(moveCost[0]);
        test.A_algorithm(startState);
    }
}

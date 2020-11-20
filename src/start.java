import java.util.List;

public class start {
    public static void main(String[] args) {
        table_gen_tool test = new table_gen_tool();
        System.out.println("Start state");
        int[] startState = {7, 6, 5, 1, 0, 2, 3, 4, 8};
        test.seStart_state(startState);
        System.out.println("Goal State");
        test.setGoal_state();
        List<Integer> reultSearech = test.findXYCourser(test.start_state);
        System.out.println("Y value is " + reultSearech.get(0));
        System.out.println("X value is " + reultSearech.get(1));
        test.genPossibleXY(reultSearech);
    }
}

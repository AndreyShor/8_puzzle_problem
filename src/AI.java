import java.util.List;

public class AI extends table_gen_tool {
    private Integer heuristicValue;
    public AI() {
        super();
    }

    public void calcHeuristicValue(List<List<Integer>> matrix) {
        this.heuristicValue = 0;

        for (int i= 0; i <= 2; i++) {
            List<Integer> startRow = matrix.get(i);
            List<Integer> goalRow = this.goal_state.get(i);
            for (int j= 0; j <= 2; j++) {
                int valueStartCompare = startRow.get(j);
                int valueGoalCompare = goalRow.get(j);

                if(valueStartCompare == valueGoalCompare) {
                    this.heuristicValue += 1;
                }
            }
        }
        System.out.println("heuristic value: "+this.heuristicValue);
    }

}

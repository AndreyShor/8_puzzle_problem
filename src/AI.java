import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AI extends table_gen_tool {
    private Integer heuristicValue;
    private Integer uniformCost;
    private Integer finishState;
    private Object[] moveCost= new Object[2];

    private List<List<List<Integer>>> past_steps = new ArrayList<>();
    private List<List<List<Integer>>> selected_steps = new ArrayList<>();

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

                if(valueStartCompare != valueGoalCompare) {
                    this.heuristicValue += 1;
                }
            }
        }
    }

    public boolean goalAchieved() {
        this.finishState = 0;

        for (int i = 0; i <= 2; i++) {
            List<Integer> startRow = this.start_state.get(i);
            List<Integer> goalRow = this.goal_state.get(i);
            for (int j = 0; j <= 2; j++) {
                int valueStartCompare = startRow.get(j);
                int valueGoalCompare = goalRow.get(j);

                if (valueStartCompare == valueGoalCompare) {
                    finishState += 1;
                }
            }
        }
        if (this.finishState == 9) {
            return true;
        } else {
            return false;
        }
    }

    public void update_start_state( List<List<Integer>> selectedMove){
        past_steps.add(this.start_state);
        selected_steps.add(selectedMove);
        this.copyState(selectedMove, this.start_state);
    }


    public void A_algorithm(int[] listNumbers) {
        this.start_state(listNumbers);
        this.setGoal_state();
        this.heuristicValue = 0;
        this.uniformCost = 0;
        boolean success = false;
        while (success) {
            uniformCost =+ 1;
            List<Integer> courserPosition = this.findXYCourser(this.start_state);
            this.genPossibleXY(courserPosition);
            List<List<List<Integer>>> possibleMoves = this.generateMoveMatrix(courserPosition);

            for(List<List<Integer>> move: possibleMoves) {
                calcHeuristicValue(move);
                this.moveCost[0] = this.heuristicValue;
                this.moveCost[1] = move;

            }

            success = goalAchieved();
        }

    }
}

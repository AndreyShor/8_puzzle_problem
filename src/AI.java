import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AI extends table_gen_tool {
    private Integer heuristicValue;
    private Integer uniformCost;
    private Object[] moveCost= new Object[2]; // 0 - is f(n) + g(n), 1 - selected move by algorithm

    private List<List<List<Integer>>> past_steps = new ArrayList<>();
    private List<List<List<Integer>>> selected_steps = new ArrayList<>();

    private boolean checkPastStep;

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
        return this.checkTableEquality(this.start_state, this.goal_state);
    }



    public void update_start_state( List<List<Integer>> selectedMove){
        past_steps.add(this.start_state);
        selected_steps.add(selectedMove);
        this.copyState(selectedMove, this.start_state);
    }

    public boolean checkPast_steps(List<List<Integer>> currentPotentialMove) {
        checkPastStep = false;
        for (List<List<Integer>> pastMove: past_steps) {
            boolean equal = this.checkTableEquality(currentPotentialMove, pastMove);
            if(equal) {
                checkPastStep = true;
                break;
            }
        }
        return checkPastStep;
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
            List<List<List<Integer>>> possibleMovesMatrix = this.generateMoveMatrix(courserPosition);

            for(List<List<Integer>> move: possibleMovesMatrix) {
                if (this.moveCost[0] == null) {
                    // Add if past_steps is empty then return true
                    // if past_steps is not empty check if current move is in array,
                    // if current move in past_steps go to alternative move
                    // if current move in not in past_steps go to process forward


                    calcHeuristicValue(move); // calculate heuristic value for first move
                    this.moveCost[0] = heuristicValue + uniformCost;
                    this.moveCost[1] = move;
                } else {
                    // Add if past_steps is empty then return true
                    // if past_steps is not empty check if current move is in array,
                    // if current move in past_steps go to alternative move
                    // if current move in not in past_steps go to process forward


                    int pastAlternativeMove = (int) moveCost[0];
                    calcHeuristicValue(move); // calculate heuristic value for second till n move

                    if (pastAlternativeMove > (heuristicValue + uniformCost)) {
                        this.moveCost[0] = heuristicValue + uniformCost;
                        this.moveCost[1] = move;
                    }
                }
            }

            List<List<Integer>> selectedStep = (List<List<Integer>>) this.moveCost[1];
            update_start_state(selectedStep);
            success = goalAchieved();
        }

    }
}

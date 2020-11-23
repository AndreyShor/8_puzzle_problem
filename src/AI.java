import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AI extends table_gen_tool {
    private Integer FIX_DEPTH_LIMIT = 3;
    private Integer variable_depth_limit;

    private Integer branchCost;
    private Object[] selectedPath = new Object[2];
    private ArrayList<Object> LayerMap = new ArrayList<>(FIX_DEPTH_LIMIT);

    private Integer heuristicValue;
    private Integer uniformCost;
    private Object[] moveCost= new Object[2]; // 0 - is f(n) + g(n), 1 - selected move by algorithm

    private List<List<List<Integer>>> past_steps = new ArrayList<>();
    private List<List<List<Integer>>> selected_steps = new ArrayList<>();

    private boolean checkPastStep;
    private boolean checkLayer;
    private List<List<List<Integer>>> checkLayerArray;

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
        List<List<Integer>> savePastState = new ArrayList<>();
        this.copyState(this.start_state, savePastState);
        past_steps.add(savePastState);
        selected_steps.add(selectedMove);

        this.rewriteStart_state(selectedMove);
        this.possible_options.clear();
        System.out.println(this.start_state);
        this.moveCost[0] = null;
        this.moveCost[1] = null;
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

    private boolean checkLayerExist(int LayerNumberToCheck) {
        checkLayer = false;
        if (this.LayerMap.size() == 0) {
            return false;
        } else {
            for (int i = 0; i < LayerMap.size(); i++) {
                Object[] layerObject = (Object[]) LayerMap.get(i);
                int layerNumber = (int) layerObject[0];
                if(layerNumber == LayerNumberToCheck) {
                    checkLayer = true;
                    break;
                }
            }
            return checkLayer;
        }
    }

    private List<List<List<Integer>>> getLayerStateArray(int LayerNumberToGet) {
        checkLayerArray = null;
        for (int i = 0; i < LayerMap.size(); i++) {
            Object[] layerObject = (Object[]) LayerMap.get(i);
            int layerNumber = (int) layerObject[0];
            if(layerNumber == LayerNumberToGet) {
                checkLayerArray = (List<List<List<Integer>>>) layerObject[i];
                LayerMap.remove(i);
            }
        }
        return checkLayerArray;
    }


    public void A_algorithm(int[] listNumbers) {
        this.start_state(listNumbers);
        this.setGoal_state();
        this.heuristicValue = 0;
        this.uniformCost = 0;
        Integer layer = 1;
        while (layer <= variable_depth_limit) {
            uniformCost =+ 1;
            List<Integer> courserPosition = this.findXYCourser(this.start_state);
            this.genPossibleXY(courserPosition);

            List<List<List<Integer>>> possibleMovesMatrix = this.generateMoveMatrix(courserPosition);

            for(List<List<Integer>> move: possibleMovesMatrix) {
                //  Check first move
                if (this.moveCost[0] == null) {
                    // Add if past_steps is empty then return true
                    // if past_steps is not empty check if current move is in array,
                    // if current move in past_steps go to alternative move
                    // if current move in not in past_steps go to process forward
                    boolean wasThere = checkPast_steps(move);
                    if (wasThere == false) {
                        calcHeuristicValue(move); // calculate heuristic value for first move
                        this.moveCost[0] = heuristicValue + uniformCost;
                        this.moveCost[1] = move;
                        this.branchCost =+ (Integer) this.moveCost[0];
                    }


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
                        this.branchCost =+ (Integer) this.moveCost[0];

                        if(layer == variable_depth_limit) {

                            int LayerNumber = variable_depth_limit;
                            boolean layerExist = checkLayerExist(LayerNumber);

                            if (layerExist) {
                                // update layer object with past state
                                List<List<List<Integer>>> LayerArrayUpdated = getLayerStateArray(LayerNumber);
                                LayerArrayUpdated.add(move);
                                Object[] layerWas = new Object[2];
                                layerWas[0] = LayerNumber;
                                layerWas[1] = LayerArrayUpdated;
                                LayerMap.add(layerWas);
                            } else {
                                // setup new layer object
                                Object[] layerWas = new Object[2];
                                List<List<List<Integer>>> moveWas = new ArrayList<>();
                                moveWas.add(move);
                                layerWas[0] = LayerNumber;
                                layerWas[1] = moveWas;
                                LayerMap.add(layerWas);
                            }
                        }


                    }
                }
            }

            List<List<Integer>> selectedStep = (List<List<Integer>>) this.moveCost[1];
            update_start_state(selectedStep);
            layer++;
        }

       for (List<List<Integer>> el: past_steps){
           System.out.println("Step");
           System.out.println("=>");
           for (List<Integer> row: el) {
               System.out.println(row);
           }
       }
        System.out.println("Final Step:");
        for (List<Integer> row: this.start_state) {
            System.out.println(row);
        }


    }
}

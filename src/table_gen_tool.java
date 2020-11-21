import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class table_gen_tool {

    protected List<List<Integer>> goal_state = new ArrayList<>();
    protected List<List<Integer>> start_state = new ArrayList<>();
    protected List<List<Integer>> possible_options = new ArrayList<>();

    public void start_state(int[] listNumbers) {
        int rowSizeAdd = 0;
        for(int j = 0; j < 3; j++) {
            List<Integer> row = new ArrayList<Integer>();
            for(int i = 0 + rowSizeAdd; i < (3 + rowSizeAdd); i++) {
                row.add(listNumbers[i]);
            }
            rowSizeAdd = rowSizeAdd + 3;
            this.start_state.add(row);
            System.out.println(row);
        }
    }

    public void setGoal_state(){
        int rowSizeAdd = 0;
        for(int j = 0; j < 3; j++) {
            List<Integer> row = new ArrayList<Integer>();
            for (int i = 1 + rowSizeAdd; i < (4 + rowSizeAdd); i++) {
                if(i == 9){
                    row.add(0); // 0 is position of puzzle
                } else {
                    row.add(i);
                }
            }
            rowSizeAdd = rowSizeAdd + 3;
            this.goal_state.add(row);
            System.out.println(row);
        }
    }

    public void printState(List<List<Integer>> state) {
        for(List<Integer> row : state) {
            System.out.println(row);
        }
    }

    public List<Integer> findXYCourser(List<List<Integer>> state) {
        List<Integer> coordiateResulte =  new ArrayList<>();
        for(int j = 0; j < 3; j++) {
            List<Integer> row = state.get(j);
            for (int i = 0; i < 3; i++) {
                int valueTofind = row.get(i);
                if(valueTofind == 0) {
                    System.out.println("Y value is " + j + " X value is " + i);
                    coordiateResulte.add(j);
                    coordiateResulte.add(i);
                    break;
                }
            }
        }
        return coordiateResulte;
    }

    public void genPossibleXY(List<Integer> position) {
        System.out.println(position);
        int valueY = position.get(0);
        int valueYmin = valueY - 1;
        int valueYmax = valueY + 1;

        int valueX = position.get(1);
        int valueXmin = valueX - 1;
        int valueXmax = valueX + 1;



        if (valueYmin >= 0) {
            List<Integer> possible_option = new ArrayList<>();
            possible_option.add(valueYmin);
            possible_option.add(position.get(1));
            this.possible_options.add(possible_option);
        }

        if (valueYmax != 3) {
            List<Integer> possible_option = new ArrayList<>();
            possible_option.add(valueYmax);
            possible_option.add(position.get(1));
            this.possible_options.add(possible_option);
        }

        if (valueXmin >= 0) {
            List<Integer> possible_option = new ArrayList<>();
            possible_option.add(position.get(0));
            possible_option.add(valueXmin);
            this.possible_options.add(possible_option);
        }

        if (valueXmax != 3) {
            List<Integer> possible_option = new ArrayList<>();
            possible_option.add(position.get(0));
            possible_option.add(valueXmax);
            this.possible_options.add(possible_option);
        }

        System.out.println(this.possible_options);
    }

    public List<List<List<Integer>>> generateMoveMatrix(List<Integer> initCoordinate) {
        List<List<List<Integer>>> possible_options_matrix = new ArrayList<>();
        Integer initCoordinateY = initCoordinate.get(0);
        Integer initCoordinateX = initCoordinate.get(1);
        Integer initialValue = this.start_state.get(initCoordinateY).get(initCoordinateX);


        for(List<Integer> possibleOption: this.possible_options) {
            List<List<Integer>> matrix = new ArrayList<>();

            this.copyState(this.start_state, matrix);
            int possibleCoordinateY = possibleOption.get(0);
            int possibleCoordinateX = possibleOption.get(1);
            int possibleValue = this.start_state.get(possibleCoordinateY).get(possibleCoordinateX);

            System.out.println(possibleValue);
            matrix.get(possibleCoordinateY).set(possibleCoordinateX, initialValue);
            matrix.get(initCoordinateY).set(initCoordinateX, possibleValue);

            possible_options_matrix.add(matrix);
            System.out.println("Size " + possible_options_matrix.size());
        }
        return possible_options_matrix;
    }


    public List<List<Integer>> copyState(List<List<Integer>> source, List<List<Integer>> destination) {
        for (int i= 0; i <= 2; i++) {
            List<Integer> sourceRow = source.get(i);
            List<Integer> destinationRow = new ArrayList<>();
            for(int j = 0; j <= 2; j++) {
                int value = sourceRow.get(j);
                destinationRow.add(value);
            }
            destination.add(destinationRow);
        }

        return destination;
    }



}

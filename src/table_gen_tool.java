import java.util.ArrayList;
import java.util.List;

public class table_gen_tool {

    private List<List<Integer>> goal_state = new ArrayList<>();
    public List<List<Integer>> start_state = new ArrayList<>();
    private List<List<Integer>> possible_options = new ArrayList<>();
    public void seStart_state(int[] listNumbers) {
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

    // List<List<Integer>>



}

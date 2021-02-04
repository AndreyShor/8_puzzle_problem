import java.util.ArrayList;
import java.util.List;

public class puzzleGameFinder {

    private List<Board> unexpandedList;
    private List<Board> expandedList; // to check how may nodes was expanded

    private boolean checkPastArrayValue;

    puzzleGameFinder() {
        this.unexpandedList = new ArrayList<>();
        this.expandedList = new ArrayList<>();
    }

    public void aStarFinder(int[][] startState) {
        boolean process  = true;
        Board boardStart = new Board(startState);
        
        while (process) {
            expandedList.add(boardStart.copyBoardReference());

            List<Board> moves = boardStart.returnPotentialMove();

            moves.removeIf(move -> checkExpandedArray(move));
            unexpandedList.removeIf(move -> checkExpandedArray(move));

            Board selectedMove = null;

            for (Board move: moves) {
                if (selectedMove == null) {
                    selectedMove = move;
                } else {
                    if (move.totalHeuristic < selectedMove.totalHeuristic) {
                        selectedMove = move;

                    } else {
                        // If move was expanded don't add in selected list
                        unexpandedList.add(move);
                    }
                }
            }


            for(Board boardPast: unexpandedList) {

                if (selectedMove == null) {
                    selectedMove = boardPast;
                } else {
                    if (boardPast.totalHeuristic <= selectedMove.totalHeuristic) {
                        if(boardPast.uniformCostValue < selectedMove.uniformCostValue) {
                            if(!checkExpandedArray(selectedMove) && !checkUnexpandedArray(selectedMove)){
                                unexpandedList.add(selectedMove.copyBoardReference());
                                selectedMove = boardPast;
                                break;
                            }
                        }
                    }
                }
            }

            assert selectedMove != null;
//            System.out.println("Uniform " + selectedMove.uniformCostValue);
//            System.out.println("Heurisic " + selectedMove.heuristic);
//            System.out.println("////////////// ");

            if(selectedMove.heuristic < 1 ) {
                selectedMove.printBoardParentTree();
                process = false;
            } else {
                boardStart = selectedMove;
            }




        }
    }

    public void printUnexpandedList() {
        System.out.println("Unexpanded List");
        for (Board board: this.unexpandedList) {
            board.printBoard();
        }
    }

    public void printExpandedList() {
        System.out.println("Expanded List");
        for (Board board: this.expandedList) {
            board.printBoard();
        }
    }

    public void printExpandedListSize() {
        System.out.println("Expanded Nodes " + this.expandedList.size());
    }

    public void printUnexpandedListSize() {
        System.out.println("Unexpanded Nodes " + this.unexpandedList.size());
    }

    private boolean checkExpandedArray(Board selected) {
       this.checkPastArrayValue = false;

        for (Board pastBoard: expandedList) {
            if(pastBoard.totalHeuristic == selected.totalHeuristic) {
                if (pastBoard.uniformCostValue == selected.uniformCostValue) {
                    if(pastBoard.checkEquality(selected)) {
                        checkPastArrayValue = true;
                        break;
                    }
                }
            }
        }
        return this.checkPastArrayValue;
    }

    private boolean checkUnexpandedArray(Board selected) {
        this.checkPastArrayValue = false;

        for (Board pastBoard: unexpandedList) {
            if(pastBoard.totalHeuristic == selected.totalHeuristic) {
                if (pastBoard.uniformCostValue == selected.uniformCostValue) {
                    if(pastBoard.checkEquality(selected)) {
                        checkPastArrayValue = true;
                        break;
                    }
                }
            }
        }
        return this.checkPastArrayValue;
    }



}

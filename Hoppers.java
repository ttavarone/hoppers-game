import java.util.ArrayList;

/**
 * A text-based version of the game Hoppers
 * 
 * @author (Tucker Tavarone)
 * @version (3/7/18)
 */
public class Hoppers 
{
    private boolean[][][] testTheMove = new boolean[13][13][13]; //positions array
    private boolean[] board = new boolean[13]; //board containing the current frog positions
    private int redFrogPos; // where the red frog is
    private ArrayList<Integer> jumps = new ArrayList<Integer>();//a list of the jumps made
    private int remainingFrogs;//remaining frogs on the board

    /**
     * Constructor for objects of type Hoppers.
     */
    public Hoppers()
    {
        
        for(int i = 0; i<13; i++){//initialize the board to all false
            board[i] = false;
        }

        testTheMove = new boolean[13][13][13]; //initialize the array containing all the
        //possible moves

        testTheMove[0][5][10] = true;
        testTheMove[0][1][2] = true;
        testTheMove[0][3][6] = true;
        testTheMove[1][3][5] = true;
        testTheMove[1][6][11] = true;
        testTheMove[1][4][7] = true;
        testTheMove[2][1][0] = true;
        testTheMove[2][4][6] = true;
        testTheMove[2][7][12] = true;
        testTheMove[3][6][9] = true;
        testTheMove[4][6][8] = true;
        testTheMove[5][3][1] = true;
        testTheMove[5][6][7] = true;
        testTheMove[5][8][11] = true;
        testTheMove[6][3][0] = true;
        testTheMove[6][8][10] = true;
        testTheMove[6][9][12] = true;
        testTheMove[6][4][2] = true;
        testTheMove[7][4][1] = true;
        testTheMove[7][9][11] = true;
        testTheMove[8][6][4] = true;
        testTheMove[9][6][3] = true;
        testTheMove[10][5][0] = true;
        testTheMove[10][8][6] = true;
        testTheMove[10][11][12] = true;
        testTheMove[11][6][1] = true;
        testTheMove[11][8][5] = true;
        testTheMove[11][9][7] = true;
        testTheMove[12][11][10] = true;
        testTheMove[12][7][2] = true;;
    }

    /**
     * A quick method to print the jumps from the array list for recording jumps
     * 
     * @param jumpArrayIn the array list that contains all the jumps, note: it does not
     * print straight from the jump array list instance variable, because it could be changed
     * multiple times in the instance
     */
    public void printJumps(ArrayList<Integer> jumpArrayIn)
    {
        for(int i = 0; i < jumpArrayIn.size()-1; i+=2){
            System.out.println(jumpArrayIn.get(i)+"-"+jumpArrayIn.get(i+1));
        }
    }

    /**
     * Sets the board to it's initial state, where the frogs locations are determined
     * by the input array
     * 
     * @param frogsIn an array that contains the locations of all the frogs
     */
    public void setBoard(int[] frogsIn)
    {
        for(int zero = 0; zero < board.length; zero++){//initialize everything to 0, to be safe
            board[zero] = false;
        }

        remainingFrogs = frogsIn.length;
        for(int i = 0; i < frogsIn.length; i++){ 
            int frogPos = frogsIn[i];
            board[frogPos] = true;
            if(i == frogsIn.length-1){ //last frog is the red frog, sets the pos the red frog
                redFrogPos = frogsIn[i];
            }
        }
    }
    

    /**
     * Prints out the final solution, if none found, prints out No Solution Found,
     * if there is a solution, it prints the jumps and then "Done!"
     */
    public void getSolution()
    {
        if(solve(board, jumps, remainingFrogs, false) == false){//runs the recursive call
            System.out.println("No Solution Found!"); //if its false
        }
        else{
            printJumps(jumps); //if the recursive call ends up being true
            System.out.println("Done!");
        }
    }

    /**
     * The method to solve the actual problem
     * 
     * @param boardIn a boolean array that becomes a duplicate of the board array
     * @param jumpsIn an array list that contains integer representing the jumps
     * @param remainingFrogsIn the amount of frogs remaining on the board
     * @param solutionFound if a solution is found for the jump, return true, false otherwise
     * 
     * @return boolean represents the solution found or not, if it is finally true,
     *  it means the method found it all to be true
     */
    public boolean solve(boolean[] boardIn, ArrayList<Integer> jumpsIn, 
    int remainingFrogsIn, boolean solutionFound)
    {
        boolean[] newBoard = boardIn; // duplicate the board to avoid overwriting

        if(remainingFrogsIn < 2){//base case if there are already less than or there are 
            //two frogs on the board
            for(int i = 0; i < newBoard.length; i++){
                if((newBoard[i] == true)&&(i==redFrogPos))
                {
                    jumps = jumpsIn;
                    solutionFound = true;
                    return solutionFound;
                }
            }
            return solutionFound;
        }
        else 
        {
            for(int cur = 0; cur < 13; cur++){
                for(int mid = 0; mid < 13; mid++){
                    for(int land = 0; land < 13; land++){
                        if(testTheMove[cur][mid][land] == true){
                            if((newBoard[cur] == true)&&(newBoard[mid] == true)
                            &&(newBoard[land] == false)){
                                if(mid == redFrogPos){
                                    continue;
                                }
                                if(cur==redFrogPos){
                                    redFrogPos = land;
                                }
                                newBoard[cur]=false;
                                newBoard[mid]=false;
                                newBoard[land]=true;
                                jumpsIn.add(cur);
                                jumpsIn.add(land);
                                solutionFound = solve(newBoard, jumpsIn, remainingFrogsIn-1, solutionFound);
                                if(solutionFound == false){
                                    newBoard[cur]=true;
                                    newBoard[mid]=true;
                                    newBoard[land]=false;
                                    jumpsIn.remove(jumpsIn.size()-1);
                                    jumpsIn.remove(jumpsIn.size()-1);
                                    if(land==redFrogPos){
                                        redFrogPos = cur;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return solutionFound;
    }
}
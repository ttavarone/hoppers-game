import java.util.ArrayList;
/**
 * A text based version of the game Hoppers
 *
 * @author (Tucker Tavarone)
 * @version (2/28/18)
 */
public class Hoppers
{
    private class lilyPad
    {
        private int padNum;
        private boolean hasFrog;
        private boolean isRedFrog;
        private boolean jumped;
        private boolean isMid;
        private Connection head;

        private lilyPad(int padNumIn, boolean hasFrogIn, boolean isRedFrogIn)
        {
            padNum = padNumIn;
            hasFrog = hasFrogIn;
            isRedFrog = isRedFrogIn;
            isMid = false;
        }

        private boolean isMidPoint()
        {
            return isMid;
        }

        private boolean hasFrog()
        {
            return hasFrog;
        }

        private boolean isRedFrog()
        {
            return isRedFrog;
        }

        private int getPadNum()
        {
            return padNum;
        }

        private void placeFrog()
        {
            hasFrog = true;
        }

        private void setToRedFrog()
        {
            isRedFrog = true;
        }

        private void setToMid()
        {
            isMid = true;
        }
        
        private void removeFrog()
        {
            hasFrog = false;
        }
    }

    private class Connection
    {
        private int dest;
        private Connection next;

        public Connection(int dst, Connection n)
        {
            dest = dst;
            next = n;
        }
    }

    private lilyPad[] board;
    private int remainingFrogs;
    private int firstFrogPos;
    boolean[][][] testTheMove;

    /**
     * For adding connections from lilyPad object to lilyPad object
     * Helps to create the adjaceny list of connections
     * 
     * @param
     * @param
     */
    public void addConnect(int source, int dest)
    {
        board[source].head = new Connection(dest, board[source].head);
    }

    public Hoppers()
    {
        board = new lilyPad[13]; // initializes the board to a specific board size
        remainingFrogs = 0;

        for(int i = 0; i < board.length; i++){
            board[i] = new lilyPad(i, true, false);
        }

        // this space contains initilization for the entire board and its connections
        //as well as the other instance variables

        addConnect(0, 1);
        addConnect(0, 3);
        addConnect(0, 5);

        addConnect(1, 0);
        addConnect(1, 3);
        addConnect(1, 6);
        addConnect(1, 4);
        addConnect(1, 2);

        addConnect(2, 1);
        addConnect(2, 4);
        addConnect(2, 7);

        addConnect(3, 0);
        addConnect(3, 1);
        addConnect(3, 5);
        addConnect(3, 6);

        addConnect(4, 1);
        addConnect(4, 2);
        addConnect(4, 6);
        addConnect(4, 7);

        addConnect(5, 0);
        addConnect(5, 3);
        addConnect(5, 6);
        addConnect(5, 8);
        addConnect(5, 10);

        addConnect(6, 1);
        addConnect(6, 3);
        addConnect(6, 4);
        addConnect(6, 5);
        addConnect(6, 7);
        addConnect(6, 8);
        addConnect(6, 9);
        addConnect(6, 11);

        addConnect(7, 2);
        addConnect(7, 4);
        addConnect(7, 6);
        addConnect(7, 9);
        addConnect(7, 12);

        addConnect(8, 5);
        addConnect(8, 6);
        addConnect(8, 10);
        addConnect(8, 11);

        addConnect(9, 6);
        addConnect(9, 7);
        addConnect(9, 11);
        addConnect(9, 12);

        addConnect(10, 5);
        addConnect(10, 8);
        addConnect(10, 11);

        addConnect(11, 6);
        addConnect(11, 8);
        addConnect(11, 9);
        addConnect(11, 10);
        addConnect(11, 12);

        addConnect(12, 7);
        addConnect(12, 9);
        addConnect(12, 11);

        board[1].setToMid();
        board[3].setToMid();
        board[4].setToMid();
        board[5].setToMid();
        board[6].setToMid();
        board[7].setToMid();
        board[8].setToMid();
        board[9].setToMid();
        board[11].setToMid();

        testTheMove = new boolean[13][13][13];

        testTheMove[0][5][10] = true;testTheMove[0][1][2] = true;testTheMove[0][3][6] = true;
        testTheMove[1][3][5] = true;testTheMove[1][6][11] = true;testTheMove[1][4][7] = true;
        testTheMove[2][1][0] = true;testTheMove[2][4][6] = true;testTheMove[2][7][12] = true;
        testTheMove[3][6][9] = true;testTheMove[4][6][8] = true;testTheMove[5][3][1] = true;
        testTheMove[5][6][7] = true;testTheMove[5][8][11] = true;testTheMove[6][3][0] = true;
        testTheMove[6][8][10] = true;testTheMove[6][9][12] = true;testTheMove[6][4][2] = true;
        testTheMove[7][4][1] = true;testTheMove[7][9][11] = true;testTheMove[8][6][4] = true;
        testTheMove[9][6][3] = true;testTheMove[10][5][0] = true;testTheMove[10][8][6] = true;
        testTheMove[10][11][12] = true;testTheMove[11][6][1] = true;testTheMove[11][8][5] = true;
        testTheMove[11][9][7] = true;testTheMove[12][11][10] = true;testTheMove[12][7][2] = true;
    }

    private boolean possibleMove(int cur, int mid, int land)
    {
        if(testTheMove[cur][mid][land]){return true;}
        else{
            return false;
        }
    }

    /**
     * Takes in the array of frog positions, gets the position number, then sets
     * the board (array of lilyPads) corresponding position to contain a frog
     * 
     * On the last element in the array, the lilyPad object on the board is set
     * to say it contains a red frog
     */
    public void setBoard(int[] frogsIn)
    {
        remainingFrogs = frogsIn.length;
        firstFrogPos = frogsIn[0];
        for(int i = 0; i < frogsIn.length; i++){
            int frogPos = frogsIn[i];
            if(i == board.length-1){
                board[frogPos].placeFrog();
                board[frogPos].setToRedFrog();
            }
            else{
                board[frogPos].placeFrog();
            }
        }
    }

    /**
     * 
     */
    public void getSolution()
    {
        // arraylist that keeps track of jumps
        ArrayList<Integer> jumps = new ArrayList<Integer>();

        int prevCur = 0;
        int cur = 0;
        int mid = 0;
        int land = 0;

        //need to choose a starting point

        // boolean to stop loop of searching if the solution has not been found
        boolean solutionFound = false;
        int first = 0;
        do{
            for(int second = 0; second < 13; second++){
                for(int third = 0; third < 13; third++){
                    
                }
            }
            first++;
        }while(solutionFound!=true && first < 13);
    }

    /**
     * 
     */
    public void solve()
    {
    }
}

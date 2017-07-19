import org.apache.commons.cli.ParseException;

import java.io.FileNotFoundException;

/**
 * Created by shane schuller on 1/26/2017.
 */
public class GOLStepper {

    /*
        I wanted main to handle exceptions gracefully so that i could print out the list of options and help the person
         entering data out.
        Originally I had CLI.java handle this but decided to just throw the errors and give the person the ability to
        change the output as they wanted, this also helped in testing.
     */
    public static void main(String args[]){
        CLI cli = null;
        try {
            cli = new CLI (args);
        } catch (InvalidStructureException e) { // Rows and columns are not a supported size
            cli.handleException(e);
        } catch (ParseException e) {//Couldnt read the input
            cli.handleException(e);
        } catch (FileNotFoundException e) {//File provided isnt accessible
            cli.handleException(e);
        } catch (NullPointerException e){//Nothing was provided
            cli.handleException(e);
        }
        cli.printGame(predictNextStep(cli.getMassagedBoard()));
    }

    static boolean[][] predictNextStep(boolean[][] prevStep) {
        int numRows = prevStep.length;
        int numCols = prevStep[0].length;
        boolean[][] nextStep = new boolean[numRows][numCols];
        for (int row = 0; row < prevStep.length; row++) {
            for (int col = 0; col < prevStep[row].length; col++) {
                int numOfNeighbors = countNeighbors(row, col, prevStep);
                // under or overpopulation, cell dies
                if ((numOfNeighbors < 2) || (numOfNeighbors > 3)) {
                    nextStep[row][col] = false;
                }
                // cell lives on to next generation
                if (numOfNeighbors == 2) {
                    nextStep[row][col] = prevStep[row][col];
                }
                // cell either stays alive, or is born
                if (numOfNeighbors == 3) {
                    nextStep[row][col] = true;
                }
            }
        }

        return nextStep;
    }

    static int countNeighbors(int row, int col, boolean[][] board) {
        int count = 0;
        int x = row + 1;
        int y = col + 1;
        boolean[][] bigBoard = new boolean[board.length + 2][board[0].length + 2];
        //Without using a library for deep copy I am keeping the code maintainable at the cost of O(n) performance
        for(int r = 0; r < board.length; r++){
            System.arraycopy(board[r], 0, bigBoard[r + 1], 1, board[r].length);
        }
        //By making the board bigger i dont need logic to calculate array sizes or need to know where i am on the map.
        count += bigBoard[x - 1][y - 1]     ? 1 : 0;
        count += bigBoard[x][y - 1]         ? 1 : 0;
        count += bigBoard[x + 1][y - 1]     ? 1 : 0;
        count += bigBoard[x + 1][y]         ? 1 : 0;
        count += bigBoard[x + 1][y + 1]     ? 1 : 0;
        count += bigBoard[x][y + 1]         ? 1 : 0;
        count += bigBoard[x - 1][y + 1]     ? 1 : 0;
        count += bigBoard[x - 1][y]         ? 1 : 0;
        return count;
    }
}

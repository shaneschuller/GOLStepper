import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by shane on 2/4/2017.
 *
 * This is my wrapper class over the Apache Commons CLI library. Also abstracting this out means I can change my CLI
 * library if i deem another one is better.
 */
public class CLI {
    private Options options;
    private String ALIVE_FLAG_CHAR = "a";
    private String DEAD_FLAG_CHAR = "d";
    private String fileChar = "f";
    private BufferedReader input;
    private String aliveChar;
    private String deadChar;

    
    public CLI(String[] args) throws InvalidStructureException, ParseException, FileNotFoundException {
        init();
        CommandLineParser cmdLineParser = new DefaultParser();
        CommandLine cmd = cmdLineParser.parse(options, args);
        aliveChar = cmd.hasOption(ALIVE_FLAG_CHAR) ? cmd.getOptionValue(ALIVE_FLAG_CHAR) : "o";
        deadChar = cmd.hasOption(DEAD_FLAG_CHAR) ? cmd.getOptionValue(DEAD_FLAG_CHAR) : ".";
        if (cmd.getOptionValue(fileChar) == null) {
            throw new InvalidStructureException("file path cannot be blank");
        }
        input = new BufferedReader(new FileReader(cmd.getOptionValue(fileChar)));

    }

    public void handleException(Exception e){
        e.printStackTrace();
        HelpFormatter help = new HelpFormatter();
        help.printHelp("Main", options);
        System.exit(1);
    }


    public boolean[][] getMassagedBoard() {

        MassagedUserInputBoard muib = collectInputFromUserAndSanitize();

        boolean[][] massagedBoardFromUser = new boolean[muib.rows()][muib.cols()];
        for (int row = 0; row < muib.rows(); row++) {
            String column = muib.getColumn(row);
            for (int col = 0; col < column.length(); col++) {
                massagedBoardFromUser[row][col] = aliveChar.equals(column.charAt(col));
            }
        }

        return massagedBoardFromUser;
    }

    public void printGame(boolean[][] nextStep) {

        String output = "";
        for(int row = 0; row < nextStep.length; row++){
            output += "\n";
            for(int col = 0; col < nextStep[row].length; col++){
                output += nextStep[row][col] ? aliveChar : deadChar;
                output += "\t";
            }
        }

        System.out.print(output+"\n");
    }

    //Nice to have method to clean up my constructor.
    private void init(){
        options = new Options();

        Option file = new Option(fileChar, "file", true, "The full path to input file for game"
                + "state if -a is not used then the first character is assumed to be the dead character," +
                "default is 'o'");
        Option aliveCharacter = new Option(ALIVE_FLAG_CHAR, "alive-character", true, "This is the" +
                "character that denotes a cell is alive, default is '.'");
        Option deadCharacter = new Option(DEAD_FLAG_CHAR, "dead-character", true, "This is the" +
                "character that denotes a cell is dead");
        options.addOption(file);
        options.addOption(aliveCharacter);
        options.addOption(deadCharacter);
    }

    private MassagedUserInputBoard collectInputFromUserAndSanitize() {
        String cell = null;
        try {
            cell = sanitizeString(input.readLine());
        } catch (IOException e) {
            handleException(e);
        }
        ArrayList<String> boardFromUser = new ArrayList<String>();
        int previousRowSize = 0;
        if (cell != null) {
            previousRowSize = cell.length();
        }
        while (cell != null) {
            for(char c : cell.toCharArray()){
                if((aliveChar.charAt(0) != c) && (deadChar.charAt(0) != c)){
                    try {
                        throw new InvalidStructureException("Must only contain alive or dead characters, please refer" +
                                "to the description of alive and dead characters");
                    } catch (InvalidStructureException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(cell.length() != previousRowSize){
                try {
                    throw new InvalidStructureException("Table size mismatch, your rows and columns are not all the"+
                            "size");
                } catch (InvalidStructureException e) {
                    e.printStackTrace();
                }
            }
            previousRowSize = cell.length();
            boardFromUser.add(cell);
            try {
                cell = sanitizeString(input.readLine());
            } catch (IOException e) {
                handleException(e);
            }
        }
        return new MassagedUserInputBoard(boardFromUser);
    }

    //Moving this sanitation to a new method means we can add more sanitation later, such as | or [ for grid layout
    @org.jetbrains.annotations.Contract("null -> null")
    private static String sanitizeString(String s) {
        if(s == null){
            return null;
        }
        return s.replaceAll("\\s+","").replaceAll("\t","");
    }

    //Just a nice to have class so that i know i am referencing rows and columns not just .get(0) or .size() but i put
    //meaning to the calls.
    private class MassagedUserInputBoard {
        private ArrayList<String> board;

        public MassagedUserInputBoard(ArrayList<String> boardFromUser) {
            board = boardFromUser;
        }

        public int rows(){
            return board.size();
        }

        public int cols(){
            return board.get(0).length();
        }

        public String getColumn(int row) {
            return board.get(0);
        }
    }
}

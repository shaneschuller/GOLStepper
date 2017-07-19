import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by shane on 2/4/2017.
 *
 */
public class CLITest {

    @Test
    void CLI_HandlesOnlyFileFlag(){
        File file = new File("src/test/resources/board-using-default-chars.txt");

        try {
            CLI cli = new CLI(new String[]{"-f",file.getAbsolutePath()});
        } catch (InvalidStructureException e) {
            fail("Something is wrong with the file, make sure to check the return characters");
        } catch (ParseException e) {
            fail("Should parse input");
        } catch (FileNotFoundException e) {
            fail("Test Environment isnt setup");
        }

    }

    @Test
    void CLI_HandlesAliveCharacterFlag(){
        File file = new File("src/test/resources/board-using-custom-chars.txt");

        try {
            CLI cli = new CLI(new String[]{"-f",file.getAbsolutePath(), "-a", "*"});
        } catch (InvalidStructureException e) {
            fail("Something is wrong with the file, make sure to check the return characters");
        } catch (ParseException e) {
            fail("Should parse input");
        } catch (FileNotFoundException e) {
            fail("Test Environment isnt setup");
        }
    }

    @Test
    void CLI_HandlesDeadCharacterFlag(){
        File file = new File("src/test/resources/board-using-custom-chars.txt");

        try {
            CLI cli = new CLI(new String[]{"-f",file.getAbsolutePath(), "-d", "-"});
        } catch (InvalidStructureException e) {
            fail("Something is wrong with the file, make sure to check the return characters");
        } catch (ParseException e) {
            fail("Should parse input");
        } catch (FileNotFoundException e) {
            fail("Test Environment isnt setup");
        }
    }
    /*
    @BeforeEach
    void hijackConsole(){
      This is where i would build the streams, little inefficient since not every test needs them, but allows a nicer
        closing of the streams.
      Also I couldnt use BeforeAll here since that is called once at the beginning and I dont want to force the order
        of the tests.
    }
    */

    /*
    @AfterEach
    void relinquishConsole(){
      This is where I would need to close my streams and set system.out to be the JVM default.

    }
    */
    @Disabled
    void CLI_HandleException(){
        //Given more time I would need to set System.out to a PrintStream and assert the message is what I want.

    }

    @Disabled
    void CLI_getMassagedBoard(){
        //Not writing this test since at that point it isn't a unit test but an integration test. there is so much
        //that could happen during this execution that a unit test doesnt do it justice.
    }

    @Disabled
    void CLI_printGame(){
        //Given more time I would need to set System.out to a PrintStream and assert the message is what I want.
    }
}

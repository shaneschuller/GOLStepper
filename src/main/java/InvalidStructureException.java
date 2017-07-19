/**
 * Created by shane on 2/4/2017.
 *
 * Not writing tests for this, I am shelling out to super, which already has its own tests.
 */
public class InvalidStructureException extends Exception {

    public InvalidStructureException(String message){
        super(message);
    }
}

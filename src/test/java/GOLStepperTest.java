import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by shane on 2/1/2017.
 */
class GOLStepperTest {
    @Test
    void predictNextStep_8x6SuppliedExample() {
        boolean input[][] = {
                {false  , false ,   false   ,    false  ,   false   ,   false   ,   true    ,   false},
                {true   , true  ,   true    ,    false  ,   false   ,   false   ,   true    ,   false},
                {false  , false ,   false   ,    false  ,   false   ,   false   ,   true    ,   false},
                {false  , false ,   false   ,    false  ,   false   ,   false   ,   false   ,   false},
                {false  , false ,   false   ,    true   ,   true    ,   false   ,   false   ,   false},
                {false  , false ,   false   ,    true   ,   true    ,   false   ,   false   ,   false},
        };
        boolean output[][] = {
                {false  ,   true    ,   false   ,   false   ,   false   ,   false   ,   false   ,   false},
                {false  ,   true    ,   false   ,   false   ,   false   ,   true    ,   true    ,   true },
                {false  ,   true    ,   false   ,   false   ,   false   ,   false   ,   false   ,   false},
                {false  ,   false   ,   false   ,   false   ,   false   ,   false   ,   false   ,   false},
                {false  ,   false   ,   false   ,   true    ,   true    ,   false   ,   false   ,   false},
                {false  ,   false   ,   false   ,   true    ,   true    ,   false   ,   false   ,   false}
        };

        assertArrayEquals(output, GOLStepper.predictNextStep(input));

    }



    @Test
    void predictNextStep_2x2_AllTrue() {
        boolean input[][] = {
                {true,true},
                {true, true}
        };
        boolean output[][] = {
                {true,true},
                {true, true}
        };
        assertArrayEquals(output, GOLStepper.predictNextStep(input));
    }

    @Test
    void predictNextStep_3x3_Oscillator(){
        boolean input[][] = {
                {false,false,false},
                {true, true, true},
                {false, false, false}
        };
        boolean output[][] = {
                {false,true, false},
                {false, true, false},
                {false, true, false}
        };
        assertArrayEquals(output, GOLStepper.predictNextStep(input));
    }

    @Test
    void predictNextStep_1x1() {
        boolean input[][] = {
                {true}
        };
        boolean output[][] = {
                {false}
        };

        assertArrayEquals(output, GOLStepper.predictNextStep(input));

    }

    @Test
    void countNeighbors_1x1_True() {
        boolean[][] input = {{true}};
        assertEquals(0, GOLStepper.countNeighbors(0, 0, input));
    }

    @Test
    void countNeighbors_2x2_AllTrue() {
        boolean[][] input = {{true,true},{true,true}};
        assertEquals(3, GOLStepper.countNeighbors(0,1,input));

    }

    @Test
    void countNeighbors_3x3_OneFalse() {
        boolean[][] input = {{true,true},{true,true}, {true, false}};
        assertEquals(3, GOLStepper.countNeighbors(0,1,input));
    }

    @Test
    void main_GivenEmptyArgs() {
        assertThrows(NullPointerException.class, () -> {
            GOLStepper.main(new String[]{""});
        });
    }


}
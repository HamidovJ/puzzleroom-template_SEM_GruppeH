package at.edu.c02.puzzleroom;

import at.edu.c02.puzzleroom.commands.CommandLoad;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FieldTest {
    @Test
    public void startField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // We're using CommandLoad here - therefore it's not a full unit test, but allows us to test things easier
        // without having to duplicate the loading logic.
        // You will often find these constructs in "real life" applications (especially if tests were added later), when it's hard
        // to isolate everything.
        new CommandLoad(new String[]{"src/test/resources/startField.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Player should start in 2nd row, 1st column
        assertEquals(2, player.getRow());
        assertEquals(1, player.getCol());
    }

    @Test
    public void pathField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // Finish is reached when moving twice to the right
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Player should start at 0 steps
        assertEquals(0, player.getStepCount());

        // Moving right should be possible, since there is a path field
        boolean success = player.moveRight();
        assertTrue(success);

        // Player should now be at 1 step
        assertEquals(1, player.getStepCount());
    }

    @Test
    public void wallField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // Finish is reached when moving twice to the right
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Player should start at 0 steps
        assertEquals(0, player.getStepCount());

        // Moving left should not be possible, since there is a wall field
        boolean success = player.moveLeft();
        assertFalse(success);

        // Player should still be at 0 steps
        assertEquals(0, player.getStepCount());
    }

    @Test
    public void finishField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // Finish is reached when moving twice to the right
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Game should not be finished after loading
        assertFalse(gameBoard.isFinished());
        player.moveRight();
        // Game should still not be finished after moving right once
        assertFalse(gameBoard.isFinished());
        player.moveRight();
        // Game should be finished after moving right twice
        assertTrue(gameBoard.isFinished());
    }

    @Test
    public void oneWayField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        // Finish is reached when moving twice to the right
        new CommandLoad(new String[]{"src/test/resources/TOneWay.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        // Player should start at 0 steps
        assertEquals(0, player.getStepCount());

        // Moving right should  be possible
        boolean successMoveRight = player.moveRight();
        assertTrue(successMoveRight);

        // Moving right should NOT be possible, because this field is a ^ one-way field
        boolean successLeaveUpOneWayFieldWithRight = player.moveRight();
        assertFalse(successLeaveUpOneWayFieldWithRight);

        // Moving left should NOT be possible, because this field is a ^ one-way field
        boolean successLeaveUpOneWayFieldWithLeft = player.moveLeft();
        assertFalse(successLeaveUpOneWayFieldWithLeft);

        // Moving down should NOT be possible, because this field is a ^ one-way field
        boolean successLeaveUpOneWayFieldWithDown = player.moveDown();
        assertFalse(successLeaveUpOneWayFieldWithDown);

        // Moving up should be possible, because this field is a ^ one-way field
        boolean successLeaveUpOneWayFieldWithUp= player.moveUp();
        assertTrue(successLeaveUpOneWayFieldWithUp);
    }



    /*
#######
#o@@ x#
#######
     */

    @Test
    public void iceFieldSlideToWall() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();

        new CommandLoad(new String[]{"src/test/resources/iceFieldWall.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        assertEquals(1, player.getRow());
        assertEquals(1, player.getCol());

        boolean successRight = player.moveRight();
        assertTrue(successRight);

        assertEquals(1, player.getRow());
        assertEquals(4, player.getCol());

        boolean successRightToGoal = player.moveRight();
        assertTrue(successRightToGoal);

        assertEquals(1, player.getRow());
        assertEquals(5, player.getCol());
    }



    /*
#######
#o@@@@#
#@@@@##
#@@@@@#
##@@@@#
#@@@@x#
#######
     */

    @Test
    public void iceFieldSlideToNonIceField() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();

        new CommandLoad(new String[]{"src/test/resources/iceFieldComplex.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        assertEquals(1, player.getRow());
        assertEquals(1, player.getCol());

        boolean successDown = player.moveDown();
        assertTrue(successDown);

        assertEquals(3, player.getRow());
        assertEquals(1, player.getCol());

        boolean successRight = player.moveRight();
        assertTrue(successRight);

        assertEquals(3, player.getRow());
        assertEquals(5, player.getCol());

        boolean successDownToGoal = player.moveDown();
        assertTrue(successDownToGoal);

        assertEquals(5, player.getRow());
        assertEquals(5, player.getCol());
    }
}

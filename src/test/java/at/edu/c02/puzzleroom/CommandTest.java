package at.edu.c02.puzzleroom;

import at.edu.c02.puzzleroom.commands.CommandFastmove;
import at.edu.c02.puzzleroom.commands.CommandLoad;
import at.edu.c02.puzzleroom.commands.CommandMove;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidArgumentsException;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidMoveException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandTest {
    @Test
    public void movePositiveTest() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        new CommandMove(new String[]{"right"}).execute(gameBoard);

        // Player should now be at 1 step and at column 2 (because they moved right once)
        assertEquals(1, player.getStepCount());
        assertEquals(1, player.getRow());
        assertEquals(2, player.getCol());
    }

    @Test(expected = PuzzleRoomInvalidMoveException.class)
    public void movePositiveTestNegative() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);

        // Moving left should throw an InvalidMoveException
        new CommandMove(new String[]{"left"}).execute(gameBoard);
    }

    @Test(expected = PuzzleRoomInvalidArgumentsException.class)
    public void movePositiveTestNegative2() throws Exception {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"src/test/resources/simple.maze"}).execute(gameBoard);

        // This should throw a PuzzleRoomInvalidArgumentsException
        new CommandMove(new String[]{"invalid"}).execute(gameBoard);
    }

    @Test
    public void moveFastmoveTest() throws  Exception
    {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"test.maze"}).execute(gameBoard);
        Player player = gameBoard.getPlayer();

        new CommandFastmove(new String[]{"d","r","r"}).execute(gameBoard);

        assertEquals(3, player.getStepCount());
        assertEquals(2, player.getRow());
        assertEquals(3, player.getCol());
    }

    @Test(expected = PuzzleRoomInvalidArgumentsException.class)
    public void moveFastmoveTestNegative() throws  Exception
    {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"test.maze"}).execute(gameBoard);

        // Incorrect move command -> Should throw an InvalidArgumentsException
        new CommandFastmove(new String[]{"dd"}).execute(gameBoard);
    }

    @Test(expected = PuzzleRoomInvalidMoveException.class)
    public void moveFastmoveTestNegative2() throws  Exception
    {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"test.maze"}).execute(gameBoard);

        // Moving down twice should throw an InvalidMoveException
        new CommandFastmove(new String[]{"d","d"}).execute(gameBoard);
    }

    @Test
    public void fastMoveEndToTest() throws  Exception
    {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"EndToEnd.maze"}).execute(gameBoard);
        new CommandFastmove(new String[]{"d","r","d","l","d","r"}).execute(gameBoard);
        assertTrue(gameBoard.isFinished());
    }

    @Test(expected = PuzzleRoomInvalidMoveException.class)
    public void fastMoveEndToTestNegative() throws  Exception
    {
        GameBoard gameBoard = new GameBoardImpl();
        new CommandLoad(new String[]{"EndToEnd.maze"}).execute(gameBoard);
        new CommandFastmove(new String[]{"d","d","d","l","d","r"}).execute(gameBoard);
        assertFalse(gameBoard.isFinished());
    }
}

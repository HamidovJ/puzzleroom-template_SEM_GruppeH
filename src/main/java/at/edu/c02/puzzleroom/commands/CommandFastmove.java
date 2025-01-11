package at.edu.c02.puzzleroom.commands;

import at.edu.c02.puzzleroom.GameBoard;
import at.edu.c02.puzzleroom.Player;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomException;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidArgumentsException;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidMoveException;

public class CommandFastmove implements Command {
    private final String[] directions;

    public CommandFastmove(String[] arguments) throws PuzzleRoomException {
        directions = arguments;
    }

    public void execute(GameBoard gameBoard) throws PuzzleRoomException {
        Player player = gameBoard.getPlayer();
        if(player == null) {
            throw new PuzzleRoomInvalidMoveException();
        }

        for (String direction : directions)
        {
            boolean success = switch (direction)
            {
                case "l" -> player.moveLeft();
                case "r" -> player.moveRight();
                case "u" -> player.moveUp();
                case "d" -> player.moveDown();
                default -> throw new PuzzleRoomInvalidArgumentsException();
            };

            if(gameBoard.isFinished()) {
                break;
            }

            if(!success){
                throw new PuzzleRoomInvalidMoveException();
            }
        }
        CommandShow showCommand = new CommandShow();
        showCommand.execute(gameBoard);
    }
}

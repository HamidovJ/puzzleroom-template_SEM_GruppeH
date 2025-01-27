package at.edu.c02.puzzleroom.fields;

import at.edu.c02.puzzleroom.Direction;
import at.edu.c02.puzzleroom.GameBoard;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomException;

public class FieldIce extends BaseField {

    protected FieldIce(GameBoard gameBoard, char name, int row, int col) {
        super(gameBoard, name, row, col);
    }

    @Override
    public void initialize() throws PuzzleRoomException {

    }

    @Override
    public boolean enterField(Direction direction) {
        Field nextField = getNextField(direction);

        if (!nextField.enterField(direction)) {
            setPlayerPositionToField();
            gameBoard.getPlayer().walkStep();
        }

        return true;
    }

    private Field getNextField(Direction direction) {
        return switch (direction) {
            case Up -> gameBoard.getField(row - 1, col);
            case Down -> gameBoard.getField(row + 1, col);
            case Left -> gameBoard.getField(row, col - 1);
            case Right -> gameBoard.getField(row, col + 1);
        };
    }

    @Override
    public boolean leaveField(Direction direction) {
        return true;
    }
}

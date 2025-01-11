package at.edu.c02.puzzleroom.fields;

import at.edu.c02.puzzleroom.Direction;
import at.edu.c02.puzzleroom.GameBoard;

/**
 * A one-way field. The player can enter this field from any direction, but leave it only one-way.
 */
public class FieldOneWay extends BaseField {
    public FieldOneWay(GameBoard gameBoard, char name, int row, int col) {
        super(gameBoard, name, row, col);
    }

    public void initialize() {
    }

    public boolean enterField(Direction direction)
    {
        gameBoard.getPlayer().walkStep();
        setPlayerPositionToField();
        return true;
    }

    public boolean leaveField(Direction direction)
    {
        if (name == '^' && direction == Direction.Up)
            return true;
        else if (name == 'v' && direction == Direction.Down        )
            return true;
        else if (name == '<' && direction == Direction.Left)
            return true;
        else return name == '>' && direction == Direction.Right;
    }
}

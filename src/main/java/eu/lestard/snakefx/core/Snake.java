package eu.lestard.snakefx.core;

import eu.lestard.grid.Cell;
import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the snake.
 *
 * @author manuel.mauky
 */
public class Snake {


    private int x;
    private int y;
    private ObjectProperty<Direction> directionControlProperty;

    Direction currentDirection;

    Direction nextDirection;

    Cell<State> head;
    final List<Cell<State>> tail;


    private final CentralViewModel viewModel;
    private final GridModel<State> gridModel;
    private final String opponent;

    /**
     * @param viewModel the viewModel
     * @param gridModel      the grid on which the snake is created
     * @param gameLoop  the gameloop that is used for the movement of the snake
     */
    public Snake(final CentralViewModel viewModel, final GridModel<State> gridModel, final GameLoop gameLoop, int x, int y, String opponent) {
        this.viewModel = viewModel;
        this.gridModel = gridModel;
        gameLoop.addAction(this::move);
        this.x = x;
        this.y = y;
        this.opponent = opponent;

        tail = new ArrayList<>();
    }


    public void setDirectionControlProperty(ObjectProperty<Direction> property){
        directionControlProperty = property;
        directionControlProperty.addListener((observable, oldDirection, newDirection) ->
                this.changeDirection(newDirection));
    }
    /**
     * Initalizes the fields of the snake.
     */
    public void init() {
        setHead(gridModel.getCell(x, y));

        viewModel.collision.set(false);

        viewModel.points.set(0);

        currentDirection = Direction.UP;
        nextDirection = Direction.UP;
    }

    /**
     * Change the direction of the snake. The direction is only changed when the new direction has <bold>not</bold> the
     * same orientation as the old one.
     * <p/>
     * For example, when the snake currently has the direction UP and the new direction should be DOWN, nothing will
     * happend because both directions are vertical.
     * <p/>
     * This is to prevent the snake from moving directly into its own tail.
     *
     * @param newDirection
     */
    private void changeDirection(final Direction newDirection) {
        if (newDirection.hasSameOrientation(currentDirection)) {
            directionControlProperty.setValue(nextDirection);
        } else {
            nextDirection = newDirection;
        }
    }

    /**
     * Move the snake by one field.
     */
    void move() {
        currentDirection = nextDirection;

        final Cell<State> newHead = getFromDirection(head, currentDirection);

        if (newHead.getState().equals(State.TAIL) || newHead.getState().equals(State.HEAD)) {
            viewModel.winner.setValue(opponent);
            viewModel.collision.set(true);
            return;
        }

        boolean snakeWillGrow = false;
        if (newHead.getState().equals(State.FOOD)) {
            snakeWillGrow = true;
        }

        Cell<State> lastField = head;

        for (int i = 0; i < tail.size(); i++) {
            final Cell<State>  f = tail.get(i);

            lastField.changeState(State.TAIL);
            tail.set(i, lastField);

            lastField = f;
        }

        if (snakeWillGrow) {
            grow(lastField);
            addPoints();
        } else {
            lastField.changeState(State.EMPTY);
        }

        setHead(newHead);
    }

    /**
     * returns the cell that is located next to the given cell in the given
     * direction.
     *
     * @param cell
     * @param direction
     * @return the cell in the given direction
     */
    Cell<State> getFromDirection(Cell<State> cell, Direction direction) {
        int column = cell.getColumn();
        int row = cell.getRow();

        switch(direction){
        case UP:
            row -= 1;
            break;
        case DOWN:
            row += 1;
            break;
        case LEFT:
            column -= 1;
            break;
        case RIGHT:
            column += 1;
            break;
        }

        column += gridModel.getNumberOfColumns();
        column = column % gridModel.getNumberOfColumns();

        row += gridModel.getNumberOfRows();
        row = row % gridModel.getNumberOfRows();

        return gridModel.getCell(column, row);
    }

    public void newGame() {
        tail.clear();
        init();
    }

    private void setHead(final Cell<State> head) {
        this.head = head;
        head.changeState(State.HEAD);
    }

    /**
     * The given field is added to the tail of the snake and gets the state TAIL.
     *
     * @param field
     */
    private void grow(final Cell<State> field) {
        field.changeState(State.TAIL);
        tail.add(field);
    }

    private void addPoints() {
        final int current = viewModel.points.get();
        viewModel.points.set(current + 1);
    }


}
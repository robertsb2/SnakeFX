package eu.lestard.snakefx.core;

import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.animation.Animation.Status;
import javafx.beans.property.SimpleObjectProperty;

import javax.inject.Singleton;

import java.rmi.registry.Registry;

import static eu.lestard.snakefx.config.Config.*;

/**
 * The purpose of this function is to start a new Game.
 *
 * @author manuel.mauky
 */
@Singleton
public class NewGameFunction implements Runnable {

    private final CentralViewModel viewModel;
    private final GridModel<State> gridModel;
    private final FoodGenerator foodGenerator;
    private GameLoop gameLoop;

    public NewGameFunction(final CentralViewModel viewModel, final GridModel<State> gridModel, GameLoop gameLoop,
        final FoodGenerator foodGenerator) {
        this.viewModel = viewModel;
        this.gridModel = gridModel;
        this.gameLoop = gameLoop;


        this.foodGenerator = foodGenerator;
    }



    @Override
    public void run() {
        viewModel.gameloopStatus.set(Status.STOPPED);
        gridModel.getCells().forEach(cell -> cell.changeState(State.EMPTY));

        RegisterSnakes();
        foodGenerator.generateFood();

        viewModel.gameloopStatus.set(Status.RUNNING);
        viewModel.gameloopStatus.set(Status.PAUSED);
    }

    private void RegisterSnakes() {
        gameLoop.clearActions();
        Snake snake = new Snake(viewModel, gridModel, gameLoop, P1_START_X.get(), P1_START_Y.get(), "Player 2");
        viewModel.snakeDirection = new SimpleObjectProperty<>(Direction.UP);
        snake.setDirectionControlProperty(viewModel.snakeDirection);
        snake.newGame();
        if(viewModel.players.get() == 2){
            RegisterSecondPlayer();
        }
    }



    private void RegisterSecondPlayer(){
        Snake snake2 = new Snake(viewModel, gridModel, gameLoop, P2_START_X.get(), P2_START_Y.get(), "Player 1");
        viewModel.snake2Direction = new SimpleObjectProperty<>(Direction.UP);
        snake2.setDirectionControlProperty(viewModel.snake2Direction);
        snake2.newGame();
    }
}

package eu.lestard.snakefx.core;

import eu.lestard.grid.GridModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.animation.Animation.Status;

import javax.inject.Singleton;

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
    private final Snake snake;
//    private final Snake snake2;
    private final FoodGenerator foodGenerator;

    public NewGameFunction(final CentralViewModel viewModel, final GridModel<State> gridModel, final Snake snake, final Snake snake2,
        final FoodGenerator foodGenerator) {
        this.viewModel = viewModel;
        this.gridModel = gridModel;
        this.snake = snake;
        snake.setPosition(P1_START_X.get(), P1_START_Y.get());
//        this.snake2 = snake2;
//        snake2.setPosition(P2_START_X.get(), P2_START_Y.get());
        this.foodGenerator = foodGenerator;
    }

    @Override
    public void run() {
        viewModel.gameloopStatus.set(Status.STOPPED);

        gridModel.getCells().forEach(cell -> cell.changeState(State.EMPTY));

        snake.newGame();
//        snake2.newGame();

        foodGenerator.generateFood();

        viewModel.gameloopStatus.set(Status.RUNNING);
        viewModel.gameloopStatus.set(Status.PAUSED);
    }
}

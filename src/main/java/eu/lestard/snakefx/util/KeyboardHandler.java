package eu.lestard.snakefx.util;

import eu.lestard.snakefx.core.Direction;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.inject.Singleton;

/**
 * This class handles the input of the user.
 * 
 * @author manuel.mauky
 */
@Singleton
public class KeyboardHandler implements EventHandler<KeyEvent> {

	private final CentralViewModel viewModel;

	public KeyboardHandler(final CentralViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void handle(final KeyEvent event) {
		final KeyCode code = event.getCode();

		switch (code) {
		case UP:
			viewModel.snakeDirection.set(Direction.UP);
			break;
		case DOWN:
			viewModel.snakeDirection.set(Direction.DOWN);
			break;
		case LEFT:
			viewModel.snakeDirection.set(Direction.LEFT);
			break;
		case RIGHT:
			viewModel.snakeDirection.set(Direction.RIGHT);
			break;
			case W:
				viewModel.snake2Direction.set(Direction.UP);
				break;
			case S:
				viewModel.snake2Direction.set(Direction.DOWN);
				break;
			case A:
				viewModel.snake2Direction.set(Direction.LEFT);
				break;
			case D:
				viewModel.snake2Direction.set(Direction.RIGHT);
				break;

		}


	}
}
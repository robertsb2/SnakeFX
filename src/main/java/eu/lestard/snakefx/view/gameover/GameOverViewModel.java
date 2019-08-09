package eu.lestard.snakefx.view.gameover;

import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class GameOverViewModel implements ViewModel {
    private final CentralViewModel centralViewModel;
    private Label label;

    public GameOverViewModel(CentralViewModel viewModel){
        centralViewModel = viewModel;
        centralViewModel.collision.addListener((observable, oldValue, collisionHappened) -> {
            if(collisionHappened){
                gameOver();
            }
        });
    }

    private void gameOver() {
        label.setText(centralViewModel.winner.getValue() + " Wins!");
        if(centralViewModel.players.get() > 1) {
            centralViewModel.gameOver.setValue(true);
        }
    }

    public void addReference(Label playerId) {
        label = playerId;
    }
}

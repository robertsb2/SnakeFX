package eu.lestard.snakefx.view.gameover;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.lestard.snakefx.highscore.HighScoreEntry;
import eu.lestard.snakefx.view.highscore.HighscoreViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameOverView implements FxmlView<GameOverViewModel>, Initializable {
    public Label playerId;
    public Button closeButton;
    @FXML
    private TableView<HighScoreEntry> tableView;

    @InjectViewModel
    private GameOverViewModel viewModel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel.addReference(playerId);
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

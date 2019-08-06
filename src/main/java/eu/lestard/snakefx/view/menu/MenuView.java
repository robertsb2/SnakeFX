package eu.lestard.snakefx.view.menu;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.lestard.snakefx.util.TriggerablePopup;
import eu.lestard.snakefx.view.about.AboutView;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuView implements FxmlView<MenuViewModel>, Initializable {

    @InjectViewModel
    private MenuViewModel viewModel;

    private CentralViewModel centralViewModel;

    @FXML
    private AnchorPane menuViewPane;

    private TriggerablePopup aboutPopup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuViewPane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                final Window window = menuViewPane.getScene().getWindow();
                aboutPopup = new TriggerablePopup(AboutView.class, (Stage) window);
                aboutPopup.trigger().bindBidirectional(viewModel.aboutPopupVisible());
            }
        });
        centralViewModel = viewModel.GetCentralViewModal();
    }

    @FXML
    public void newGame() {

        Dialog<Pair<Integer, Integer>> gameOptionsDialog = new Dialog<>();

        gameOptionsDialog.setHeaderText("Enter the Size of the new Game");
        gameOptionsDialog.setTitle("New Game");
        gameOptionsDialog.setContentText("Size:");

        ButtonType newGame = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        gameOptionsDialog.getDialogPane().getButtonTypes().addAll(newGame, ButtonType.CANCEL);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<Integer> size = new ComboBox<>();
        size.getItems().addAll(viewModel.sizeOptions());
        size.getSelectionModel().select(size.getItems().indexOf(viewModel.newSize().get()));
        ComboBox<Integer> players = new ComboBox<>();
        players.getItems().addAll(viewModel.playerOptions());
        players.getSelectionModel().select(players.getItems().indexOf(viewModel.newPlayers().get()));


        gridPane.add(new Label("Board Size:"), 0, 0);
        gridPane.add(size, 1, 0);
        gridPane.add(new Label("Players:"), 2, 0);
        gridPane.add(players, 3, 0);
        gameOptionsDialog.getDialogPane().setContent(gridPane);

        gameOptionsDialog.setResultConverter(dialogButton -> {
            if (dialogButton == newGame) {
                return new Pair<>(
                        size.getSelectionModel().getSelectedItem(),
                        players.getSelectionModel().getSelectedItem());
            }
            return null;
        });

        final Optional<Pair<Integer, Integer>> gameOptional = gameOptionsDialog.showAndWait();

        gameOptional.ifPresent(pair -> {
            viewModel.newSize().setValue(pair.getKey());
            viewModel.newPlayers().setValue(pair.getValue());
            centralViewModel.players.setValue(pair.getValue());
        });
        viewModel.newGame();


    }

    @FXML
    public void showHighscores() {
        viewModel.showHighscores();
    }

    @FXML
    public void about() {
        viewModel.about();
    }

    @FXML
    public void exit() {
        viewModel.exit();
    }

}

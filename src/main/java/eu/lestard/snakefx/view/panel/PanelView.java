package eu.lestard.snakefx.view.panel;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.lestard.snakefx.core.SpeedLevel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PanelView implements FxmlView<PanelViewModel>, Initializable {

    @FXML
    private Label points;

    @FXML
    private ChoiceBox<SpeedLevel> speed;

    @FXML
    private Button playPause;

    @InjectViewModel
    private PanelViewModel viewModel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        points.textProperty().bind(viewModel.pointsLabelText());
        playPause.textProperty().bind(viewModel.playPauseButtonText());

        playPause.disableProperty().bind(viewModel.playPauseButtonDisabled());

        speed.getItems().addAll(viewModel.speedLevels());

        speed.valueProperty().bindBidirectional(viewModel.selectedSpeedLevel());

        speed.getSelectionModel().select(2);
    }

    @FXML
    public void togglePlayPause(){
        viewModel.togglePlayPause();
    }
}

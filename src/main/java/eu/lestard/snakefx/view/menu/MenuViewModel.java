package eu.lestard.snakefx.view.menu;


import de.saxsys.mvvmfx.ViewModel;
import eu.lestard.snakefx.core.NewGameFunction;
import eu.lestard.snakefx.viewmodel.CentralViewModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

@Singleton
public class MenuViewModel implements ViewModel {

    private final CentralViewModel centralViewModel;
    private final Runnable newGameFunction;

    private List<Integer> sizeOptions = new ArrayList<>();
    private List<Integer> playerOptions = new ArrayList<>();

    private IntegerProperty newSize = new SimpleIntegerProperty();
    private IntegerProperty newPlayers = new SimpleIntegerProperty();

    private BooleanProperty aboutPopupVisible = new SimpleBooleanProperty();

    public MenuViewModel(final CentralViewModel centralViewModel, final NewGameFunction newGameFunction) {
        this.centralViewModel = centralViewModel;
        this.newGameFunction = newGameFunction;

        sizeOptions.addAll(Arrays.asList(15,25,35,50));
        playerOptions.addAll(Arrays.asList(1,2));
        newSize.bindBidirectional(centralViewModel.gridSize);
        newPlayers.bindBidirectional(centralViewModel.players);
    }

    public BooleanProperty aboutPopupVisible(){
        return aboutPopupVisible;
    }

    public void newGame(){
        newGameFunction.run();
    }

    public void showHighscores(){
        centralViewModel.highscoreWindowOpen.set(true);
    }

    public void about(){
        aboutPopupVisible.set(true);
    }

    public void exit(){
        Platform.exit();
    }

    public IntegerProperty newSize() { return newSize; }

    public List<Integer> sizeOptions() {
        return sizeOptions;
    }

    public List<Integer> playerOptions() { return playerOptions; }

    public IntegerProperty newPlayers() { return newPlayers; }

    public CentralViewModel GetCentralViewModal(){
        return this.centralViewModel;
    }
}

package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.SubtitleTrack;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class Controller {
    private String videoString;
    private MediaPlayer mediaPlayer;
    private File file;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider slider , slider2 ;
    @FXML
    private TextField duration;

    private Duration elapsed;










    @FXML
    private void OpenFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a video (*mp4)","*mp4");
        fileChooser.getExtensionFilters().add(filter);
        file = fileChooser.showOpenDialog(null);
        videoString = file.toURI().toString();
        if(videoString != null){
            Media media = new Media(videoString);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();

            width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));







            slider.setValue(mediaPlayer.getVolume() * 100);
            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slider.getValue() / 100);

                }
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    slider2.setValue(newValue.toSeconds());
                }
            });

            slider2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(slider2.getValue()));
                }
            });


            mediaPlayer.play();

        }

    }

    @FXML
    private void Pause(ActionEvent actionEvent) {
        mediaPlayer.pause();
    }

    @FXML
    private void Play(ActionEvent actionEvent){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    @FXML
    private void Stop(ActionEvent actionEvent) {
        mediaPlayer.stop();

    }
    
    @FXML
    private void Forward(ActionEvent actionEvent){
        mediaPlayer.setRate(1.5);
    }

    @FXML
    private void Backward(ActionEvent actionEvent){
        mediaPlayer.setRate(.75);
    }

    @FXML
    private void FastForward(ActionEvent actionEvent){
        mediaPlayer.setRate(2);
    }

    @FXML
    private void SlowBackward(ActionEvent actionEvent){
            mediaPlayer.setRate(.5);
    }

    @FXML
    private void Exit(ActionEvent actionEvent){
        System.exit(0);
    }
}

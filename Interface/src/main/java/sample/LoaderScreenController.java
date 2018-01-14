package sample;
/**
 * Created by Anjana Senanayake on 21-Nov-17.
 */

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoaderScreenController implements Initializable
{
    @FXML
    private StackPane loaderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        new loaderSleep().start();
    }

    class loaderSleep extends Thread
    {
        public void run()
        {
            try {
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(4),loaderPane);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.setCycleCount(1);
                fadeIn.play();
                Thread.sleep(5000);

                Platform.runLater(new Runnable() {
                    public void run() {
                        Parent root = null;
                        try
                        {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
                            root = loader.load();
                            //Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            Controller controller = (Controller)loader.getController();
                            stage.setScene(new Scene(root, 808, 595));
                            controller.init(stage);
                            stage.setTitle("Simulator");
                            stage.setFullScreen(false);
                            stage.setResizable(false);
                            stage.show();

                            loaderPane.getScene().getWindow().hide();

                            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
                            {
                                @Override
                                public void handle(WindowEvent event)
                                {
                                    Platform.exit();
                                    System.exit(0);
                                }
                            });

                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

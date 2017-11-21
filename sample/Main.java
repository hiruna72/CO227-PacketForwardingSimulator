package sample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Loader.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /*public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = (Controller)loader.getController();
        controller.init(primaryStage);
        primaryStage.setTitle("Simulator");
        primaryStage.setScene(new Scene(root, 750, 500));
        primaryStage.show();
    }*/

    public static void main(String[] args)
    {
        launch(args);
    }

    public static Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(1000);
                    //updateMessage("2000 milliseconds");
                    updateProgress(i + 1, 5);
                }
                return true;
            }
        };
    }
}

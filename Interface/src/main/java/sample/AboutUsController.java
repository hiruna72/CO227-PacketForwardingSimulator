package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Anjana Senanayake on 02-Dec-17.
 */
public class AboutUsController implements Initializable
{
    @FXML
    Button backButton;
    @FXML
    TextFlow textFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Text flowText = new Text("CO227-Computer Engineering Project -Packet Forwarding Simulator- University Of Peradeniya");
        flowText.setFill(Color.BLUEVIOLET);
        //Stage.initStyle(StageStyle.UNDECORATED);
        textFlowPane.getChildren().add(flowText);
      //  ObservableList list = textFlowPane.getChildren();
      //  list.add(flowText);
    }

    @FXML
    public void backButtonAction(ActionEvent e)
    {
        Parent aboutUsParent = null;
        try {
            aboutUsParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } catch (IOException e1) {
            System.out.print("Error---------------");
        }
        Scene aboutUsScene = new Scene(aboutUsParent);

        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(aboutUsScene);
        window.show();
    }


}

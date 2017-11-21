package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends Simulator implements Initializable
{
    //Define Tables
    @FXML
    TableView<Link> linkTable;
    @FXML
    TableColumn<Link,String> linkId,linkSource,linkDestination;

    @FXML
    TableView<Packet> packetTable;
    @FXML
    TableColumn<Packet,String> packetId,packetSource,packetDestination;

    //Define Forms
    @FXML
    TextField linkSourceField,linkDestinationField;

    @FXML
    TextField packetIdField,packetSourceField,packetDestinationField;

    @FXML
    TextField numRoutersField;


    //Define Buttons
    @FXML
    Button addLinkButton,deleteLinkButton,openFileButton;

    @FXML
    Button addPacketButton,deletePacketButton,networkInitializer;

    @FXML
    private AnchorPane pane;

    private Task copyWorker;
    private Stage stage;
    @FXML
    ProgressIndicator progressIndicator;

    //Row index
    private IntegerProperty index = new SimpleIntegerProperty();
    private IntegerProperty index2 = new SimpleIntegerProperty();

    public static ObservableList<Link> data = FXCollections.observableArrayList();
    public static ObservableList<Packet> data2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        linkId.setCellValueFactory(new PropertyValueFactory<>("linkId"));
        linkSource.setCellValueFactory(new PropertyValueFactory<>("source"));
        linkDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

        packetId.setCellValueFactory(new PropertyValueFactory<>("packetId"));
        packetSource.setCellValueFactory(new PropertyValueFactory<>("sourceNode"));
        packetDestination.setCellValueFactory(new PropertyValueFactory<>("destinationNode"));

        linkTable.setItems(data);
        packetTable.setItems(data2);

        linkTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Link>()
        {
            @Override
            public void changed(ObservableValue<? extends Link> observable, Link oldValue, Link newValue)
            {
                index.set(data.indexOf(newValue));
            }
        });

        packetTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Packet>()
        {
            @Override
            public void changed(ObservableValue<? extends Packet> observable, Packet oldValue, Packet newValue)
            {
                index2.set(data2.indexOf(newValue));
            }
        });
    }

    public void init(Stage primaryStage)
    {
        this.stage = primaryStage;
    }

    @FXML
    public void getNumofRouters(ActionEvent e)
    {
        noRouters = Integer.valueOf(numRoutersField.getText());
        numRoutersField.setAlignment(Pos.CENTER);
    }

    @FXML
    public void openFile(MouseEvent e)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files","*.txt"));

        fileOpenMethod(file);
        linkTable.setItems(data);
    }
    @FXML
    public void  networkInitializer(ActionEvent e)
    {
        int src=0;
        forwardingTable= new int[noRouters][noRouters];
        ForwadingTable table1= new ForwadingTable(adjecencyMat,src,forwardingTable);

        //initialize routers
        for(int i=0;i<noRouters;i++)
        {
            Router tempRouter = new Router(i,adjecencyMat,forwardingTable,Links);
            Routers.add(tempRouter);
        }
        networkInitializer.setDisable(true);
        progressIndicator();

    }

    @FXML
    public void addLink(ActionEvent e)
    {
         try
         {
            String linkId1 = (linkSourceField.getText()) + " to " + (linkDestinationField.getText());
            Link newLink1 = new Link(linkId1, linkSourceField.getText(), linkDestinationField.getText());
            Links.put(linkId1, newLink1);
            String linkId2 = linkDestinationField.getText() + " to " + linkSourceField.getText();
            Link newLink2 = new Link(linkId2, linkSourceField.getText(), linkDestinationField.getText());
            Links.put(linkId2, newLink2);
            noLinks++;
            clearLinkForms();

            int router1 = Integer.parseInt(newLink1.getSource());
            int router2 = Integer.parseInt(newLink1.getDestination());
            adjecencyMat = new int[noRouters][noRouters];
            adjecencyMat[router1][router2] = 1;
            adjecencyMat[router2][router1] = 1;
            data.add(newLink1);
         }
         catch(ArrayIndexOutOfBoundsException k)
         {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a router within #0-"+(noRouters-1));
            alert.showAndWait();
         }
    }

    @FXML
    public  void deleteLink(ActionEvent e)
    {
        int i = index.get();
        if(i>-1)
        {
            Links.remove(data.get(i).getLinkId());
            Links.remove(data.get(i).getDestination()+" to "+data.get(i).getSource());
            data.remove(i);
            noLinks--;
        }
        linkTable.getSelectionModel().clearSelection();
    }

    public void clearLinkForms()
    {
        linkSourceField.clear();
        linkDestinationField.clear();
    }

    @FXML
    public void addPacket(ActionEvent e)
    {
        if((Integer.valueOf(packetDestinationField.getText())<noRouters))
        {
            try
            {
                Packet newPacket = new Packet(packetIdField.getText(), packetSourceField.getText(), packetDestinationField.getText(), true);
                Routers.get(Integer.valueOf(packetSourceField.getText())).addToPCconnectedQ(newPacket);
                data2.add(newPacket);
            }
            catch(IndexOutOfBoundsException k)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a router within #0-"+(noRouters-1));
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a router within #0-"+(noRouters-1));
            alert.showAndWait();
        }
        clearPacketForms();
    }

    @FXML
    public void deletePacket(ActionEvent e)
    {
        int i = index2.get();
        if(i>-1)
        {
            Routers.get(Integer.parseInt(data2.get(i).getSourceNode())).deleteFromPCconnectedQ(data2.get(i));
            data2.remove(i);
        }
        packetTable.getSelectionModel().clearSelection();
    }

    public void clearPacketForms()
    {
        packetIdField.clear();
        packetSourceField.clear();
        packetDestinationField.clear();
    }

    public void progressIndicator()
    {
        progressIndicator.setProgress(0);
        copyWorker = Main.createWorker();

        progressIndicator.progressProperty().unbind();
        progressIndicator.progressProperty().bind(copyWorker.progressProperty());
        //4progressIndicator.setDisable(true);
        new Thread(copyWorker).start();
    }
}

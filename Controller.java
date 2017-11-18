package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    @FXML
    TableColumn<Packet,Boolean> select;

    //Define Forms
    @FXML
    TextField linkSourceField,linkDestinationField;

    @FXML
    TextField packetIdField,packetSourceField,packetDestinationField;


    //Define Buttons
    @FXML
    Button addLinkButton,deleteLinkButton,openFileButton;

    @FXML
    Button addPacketButton,deletePacketButton;

    @FXML
    private AnchorPane pane;

    private Stage stage;

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

        packetId.setCellValueFactory(new PropertyValueFactory<>("packetID"));
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
                index2.set(data.indexOf(newValue));
            }
        });

        select.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Packet, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Packet, Boolean> param) {
                Packet packet = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(packet.getSelect());

                // Note: singleCol.setOnEditCommit(): Not work for
                // CheckBoxTableCell.

                // When "Single?" column change.
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        packet.setSelect(newValue);
                    }
                });
                System.out.println(packet.getSelect());
                return booleanProp;
            }
        });

        select.setCellFactory(new Callback<TableColumn<Packet, Boolean>,TableCell<Packet, Boolean>>()
        {
            @Override
            public TableCell<Packet, Boolean> call(TableColumn<Packet, Boolean> p) {
                CheckBoxTableCell<Packet, Boolean> cell = new CheckBoxTableCell<Packet, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }

    public void init(Stage primaryStage)
    {
        this.stage = primaryStage;
    }

    @FXML
    public void openFile(ActionEvent e)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files","*.txt"));

        fileOpenMethod(file);
        //System.out.println(file);

        linkId.setCellValueFactory(new PropertyValueFactory<>("linkId"));
        linkSource.setCellValueFactory(new PropertyValueFactory<>("source"));
        linkDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

        linkTable.setItems(data);
    }

    @FXML
    public void addLink(ActionEvent e)
    {
        String linkId1 = (linkSourceField.getText())+" to "+(linkDestinationField.getText());
        Link newLink1 = new Link(linkId1,linkSourceField.getText(),linkDestinationField.getText());
        Links.put(linkId1,newLink1);
        String linkId2 = linkDestinationField.getText()+" to "+linkSourceField.getText();
        Link newLink2 = new Link(linkId2,linkSourceField.getText(),linkDestinationField.getText());
        Links.put(linkId2,newLink2);

        data.add(newLink1);
        clearLinkForms();
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
        Packet newPacket = new Packet(packetIdField.getText(),packetSourceField.getText(),packetDestinationField.getText(),true);
        Routers.get(Integer.valueOf(packetSourceField.getText())).addToPCconnectedQ(newPacket);

        data2.add(newPacket);
        clearPacketForms();
    }

    public void clearPacketForms()
    {
        packetIdField.clear();
        packetSourceField.clear();
        packetDestinationField.clear();
    }
}

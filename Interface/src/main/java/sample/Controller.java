package sample;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Simulator.*;

public class Controller implements Initializable
{
    //Define Tables
    @FXML
    TableView<Link> linkTable;
    @FXML
    TableView<Packet> packetTable;
    @FXML
    TableView<Router> routerTable;

    //Define Columns
    @FXML
    TableColumn<Router,String> routerID;
    @FXML
    TableColumn<Router,Double> routerProcessingDelay;
    @FXML
    TableColumn<Link,String> linkId;
    @FXML
    TableColumn<Link,Double> linkDistance,linkRate;
    @FXML
    TableColumn<Packet,String> packetName;
    @FXML
    TableColumn<Packet,Integer> packetSource,packetDestination;
    @FXML
    TableColumn<Packet,Double> packetSize;

    //Define Forms
    @FXML
    TextField linkSourceField,linkDestinationField,linkDistanceField,linkRateField;
    @FXML
    TextField packetNameField,packetSourceField,packetDestinationField,packetSizeField;
    @FXML
    TextField numRoutersField;
    @FXML
    TextField processingDelayField;

    //Define Buttons
    @FXML
    Button addRouterButton,deleteRouterButton,openFileButton;
    @FXML
    Button simulateButton;
    @FXML
    Button addLinkButton,deleteLinkButton,deletePacketButton,networkInitializer;
    @FXML
    Button addPacketButton;

    @FXML
    public AnchorPane pane;
    @FXML
    ProgressIndicator progressIndicator;
    @FXML
    private TextArea console,packetsReached,packetsLost;
    private PrintStream printStream ;

    @FXML
    MenuBar myMenuBar;
    @FXML
    private MenuItem aboutUsItem;

    private Task copyWorker;
    private Stage stage;
    private int routerCount=0;
    public static boolean addPacketStatus=false;

    //Row index
    private IntegerProperty routerDataIndex = new SimpleIntegerProperty();
    private IntegerProperty linkDataIndex = new SimpleIntegerProperty();
    private IntegerProperty packetDataIndex = new SimpleIntegerProperty();

    public static ObservableList<Router> routerData = FXCollections.observableArrayList();
    public static ObservableList<Link> linkData = FXCollections.observableArrayList();
    public static ObservableList<Packet> packetData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        printStream = new PrintStream(new Console(console)) ;
        System.setOut(printStream);
        System.setErr(printStream);

        routerID.setCellValueFactory(new PropertyValueFactory<>("routerID"));
        routerProcessingDelay.setCellValueFactory(new PropertyValueFactory<>("processingDelay"));

        linkId.setCellValueFactory(new PropertyValueFactory<>("linkID"));
        linkDistance.setCellValueFactory(new PropertyValueFactory<>("linkDistance"));
        linkRate.setCellValueFactory(new PropertyValueFactory<>("transmissionRate"));

        packetName.setCellValueFactory(new PropertyValueFactory<>("packetName"));
        packetSource.setCellValueFactory(new PropertyValueFactory<>("src"));
        packetDestination.setCellValueFactory(new PropertyValueFactory<>("dest"));
        packetSize.setCellValueFactory(new PropertyValueFactory<>("size"));

        routerTable.setItems(routerData);
        linkTable.setItems(linkData);
        packetTable.setItems(packetData);

        routerTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Router>()
        {
            @Override
            public void changed(ObservableValue<? extends Router> observable, Router oldValue, Router newValue)
            {
                routerDataIndex.set(routerData.indexOf(newValue));
            }
        });

        linkTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Link>()
        {
            @Override
            public void changed(ObservableValue<? extends Link> observable, Link oldValue, Link newValue)
            {
                linkDataIndex.set(linkData.indexOf(newValue));
            }
        });

        packetTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Packet>()
        {
            @Override
            public void changed(ObservableValue<? extends Packet> observable, Packet oldValue, Packet newValue)
            {
                packetDataIndex.set(packetData.indexOf(newValue));
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
        Simulator.noRouters = Integer.valueOf(numRoutersField.getText());
        adjecencyMat = new int[noRouters][noRouters];
        numRoutersField.setAlignment(Pos.CENTER);
    }

    @FXML
    public void openFile(MouseEvent e)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files","*.txt"));

        Simulator.setUpForwadingTable(file);
        numRoutersField.setText(String.valueOf(noRouters));
        numRoutersField.setAlignment(Pos.CENTER);
        routerTable.setItems(routerData);
        linkTable.setItems(linkData);
    }
    @FXML
    public void  networkInitializer(ActionEvent e)
    {
        for(int i=0;i<noRouters;i++)
        {
            Queue tempQ1 = new Queue(i+" to "+i,"InputQ",""+i,20D);
            InputBuffer.put(i+" to "+i,tempQ1);
        }
        int src=0;
        forwardingTable= new int[noRouters][noRouters];
        ForwadingTable table1= new ForwadingTable(adjecencyMat,src,forwardingTable);

        networkInitializer.setDisable(true);
        progressIndicator();
        addPacketButton.setDisable(false);
        deletePacketButton.setDisable(false);
    }

    @FXML
    public void addRouter(ActionEvent e)
    {
        if(noRouters!=0)
        {
            try
            {
                Router tempRouter = new Router(routerCount, Double.valueOf(processingDelayField.getText()));
                String tempKey = routerCount + "";
                Routers.put(tempKey, tempRouter);
                routerCount++;
                routerData.add(tempRouter);
                processingDelayField.clear();
            }
            catch (NumberFormatException k)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("You've not entered a value for processing delay");
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("You've not entered number of routers");
            alert.showAndWait();
            processingDelayField.clear();
        }

    }

    @FXML
    public void deleteRouter(ActionEvent e)
    {
        int i = routerDataIndex.get();
        if(i>-1)
        {
            System.out.println(Routers.size());
            System.out.println(routerData.get(i).getRouterID());
            Routers.remove(routerData.get(i).getRouterID());
            routerData.remove(i);
            for(int j=i+1;j<=Routers.size();j++)
            {
                Routers.get(j+"").setRouterID((j-1)+"");
                Routers.put((j-1)+"",Routers.get(j+""));
                Routers.remove(j+"");
            }
            noRouters--;
            routerCount--;
        }
        routerTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void addLink(ActionEvent e)
    {
        int router1 = Integer.valueOf(linkSourceField.getText());
        int router2 = Integer.valueOf(linkDestinationField.getText());
        double linkDistance = Double.parseDouble(linkDistanceField.getText());
        double linkRate = Double.parseDouble(linkRateField.getText());
        if(noRouters!=0)
        {
            try
            {
                Link tempLink1 = new Link(router1 + " to " + router2, "onLink", linkDistance, linkRate);
                Links.put(router1 + " to " + router2, tempLink1);
                Controller.linkData.add(tempLink1);
                Link tempLink2 = new Link(router2 + " to " + router1, "onLink", linkDistance, linkRate);
                Links.put(router2 + " to " + router1, tempLink2);

                Queue tempQ1 = new Queue((router1) + " to " + (router2), "InputQ", "" + (router2), linkRate);
                Queue tempQ2 = new Queue((router2) + " to " + (router1), "InputQ", "" + (router1), linkRate);
                Queue tempQ3 = new Queue((router1) + " to " + (router2), "OutputQ", "" + (router1), linkRate);
                Queue tempQ4 = new Queue((router2) + " to " + (router1), "OutputQ", "" + (router2), linkRate);


                InputBuffer.put((router1) + " to " + (router2), tempQ1);
                InputBuffer.put((router2) + " to " + (router1), tempQ2);
                OutputBuffer.put((router1) + " to " + (router2), tempQ3);
                OutputBuffer.put((router2) + " to " + (router1), tempQ4);

                adjecencyMat[router1][router2] = 1;
                adjecencyMat[router2][router1] = 1;

                noLinks++;
                clearLinkForms();
            }
            catch (NumberFormatException k)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("One or more Fields are empty!");
                alert.showAndWait();
                clearLinkForms();
            }
            catch (ArrayIndexOutOfBoundsException k)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a router within #0-" + (noRouters - 1));
                alert.showAndWait();
                clearLinkForms();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("You've not entered number of routers");
            numRoutersField.requestFocus();
            alert.showAndWait();
            clearLinkForms();
        }
    }

    @FXML
    public  void deleteLink(ActionEvent e)
    {
        int i = linkDataIndex.get();
        if(i>-1)
        {
            int router1 = Integer.valueOf(linkData.get(i).getLinkID().split(" to ")[0]);
            int router2 = Integer.valueOf(linkData.get(i).getLinkID().split(" to ")[1]);
            //System.out.println(router1);
            //System.out.println(router2);
            Links.remove(linkData.get(i).getLinkID());
            System.out.println(Links.remove(router2+" to "+router1));
            linkData.remove(i);

            InputBuffer.remove((router1)+" to "+(router2));
            InputBuffer.remove((router2)+" to "+(router1));
            OutputBuffer.remove((router1)+" to "+(router2));
            OutputBuffer.remove((router2)+" to "+(router1));

            adjecencyMat[router1][router2]=0;
            adjecencyMat[router2][router1]=0;

            noLinks--;
        }
        linkTable.getSelectionModel().clearSelection();
    }

    public void clearLinkForms()
    {
        linkSourceField.clear();
        linkDestinationField.clear();
        linkDistanceField.clear();
        linkRateField.clear();
    }

    @FXML
    public void addPacket(ActionEvent e)
    {
        if((Integer.valueOf(packetDestinationField.getText())<noRouters))
        {
            try
            {
                addPacketStatus = true;
                int src = Integer.valueOf(packetSourceField.getText());
                int dest = Integer.valueOf(packetDestinationField.getText());
                double packetSize =Double.parseDouble(packetSizeField.getText());

                //System.out.println("adding Packets");
                Packet packet = new Packet(packetNameField.getText(),src,dest,packetSize,"InputQ",src+" to "+src);
                if(Simulator.InputBuffer.get(src+" to "+src).addPacketVirtually(packetSize))
                {
                    Simulator.InputBuffer.get(src+" to "+src).addPacket(packetNameField.getText());
                        Simulator.Packets.put(packetNameField.getText(), packet);
                }
                else
                {
                    System.out.println(src+" to "+src+" InputQ is full "+packetNameField.getText()+" is lost");
                }
                packetData.add(packet);
            }
            catch(IndexOutOfBoundsException k)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a router within #0-"+(noRouters-1));
                alert.showAndWait();
            }
            catch(NumberFormatException k)
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
        int i = packetDataIndex.get();
        if(i>-1)
        {
            int packetSrc = packetData.get(i).getSrc();
            int packetDest = packetData.get(i).getDest();
            if(Packets.containsKey(packetData.get(i).getPacketName()))
            {
                Simulator.InputBuffer.get(packetSrc+" to "+packetSrc).removeThisPacket(packetData.get(i).getPacketName());
                Packets.remove(packetData.get(i).getPacketName());
            }
            packetData.remove(i);
        }
        packetTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void clearPacketForms()
    {
        packetNameField.clear();
        packetSourceField.clear();
        packetDestinationField.clear();
        packetSizeField.clear();
    }

    @FXML
    public void progressIndicator()
    {
        progressIndicator.setProgress(0);
        copyWorker = Main.createWorker();

        progressIndicator.progressProperty().unbind();
        progressIndicator.progressProperty().bind(copyWorker.progressProperty());
        //4progressIndicator.setDisable(true);
        new Thread(copyWorker).start();
    }

    @FXML
    public void packetSimulation(ActionEvent e)
    {
       Thread t1 = new Thread(new Runnable() {
           @Override
           public void run()
           {
               Simulator.start();
           }
       });
       t1.start();
    }

    @FXML
    public void aboutUsMenuItem(ActionEvent e) throws IOException
    {
        Parent aboutUsParent = FXMLLoader.load(getClass().getResource("aboutUs.fxml"));

        Stage window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);

        window = (Stage)myMenuBar.getScene().getWindow();
        //window.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(aboutUsParent);
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void exitMenuItem(ActionEvent e)
    {
        System.exit(0);
    }

    public class Console extends OutputStream
    {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }
}


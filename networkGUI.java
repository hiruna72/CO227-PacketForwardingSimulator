import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import org.graphstream.ui.view.Viewer;

public class networkGUI extends JFrame {

    private JButton createBtn,exit,minimize,graphBtn;
    private JPanel mainPanel;
    private Dimension FRAME_SIZE = new Dimension(600, 520);// dimension for main panel
    private Color backgroundColor = new Color(16, 16, 16); 
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    protected networkPlot gr;
    protected Viewer viewer;
    
    

    public networkGUI(){
        // frame creating
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setUndecorated(true);
        setSize(FRAME_SIZE);
        
        
        // main panel is the parent that contains all the elements in the gui 
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(null);
        mainPanel.setSize(FRAME_SIZE);
        //mainPanel.addMouseListener(this); 
        //mainPanel.addMouseMotionListener(this);
        add(mainPanel);
        
        // exit and minimize button
        exit = new JButton("x");
        minimize = new JButton("-");
        
        // button panel for exit and minimize button
        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        btnPanel.setSize(70, 35);
        exit.setSize(35,35);
        minimize.setSize(35,35);
        btnPanel.setLocation(530, 0);
        btnPanel.add(minimize);
        btnPanel.add(exit);
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitactionPerformed(evt);
            }
        });
//        exit.addMouseListener(this);
        minimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minactionPerformed(evt);
            }
        });
//        minimize.addMouseListener(this);
        mainPanel.add(btnPanel);
        
        // Topic for the gui
        JLabel topic = new JLabel("Packet forwarding network simulator");
        topic.setForeground(Color.WHITE);
        topic.setFont(new Font("Tahoma",Font.BOLD , 28));
        topic.setSize(600, 50);
        topic.setLocation(50, 30);
        mainPanel.add(topic);
        
        JLabel lable = new JLabel("Enter your network details");
        lable.setForeground(Color.WHITE);
        lable.setFont(new Font("Tahoma",Font.BOLD , 22));
        lable.setSize(600, 50);
        lable.setLocation(20, 80);
        mainPanel.add(lable);
         
        JLabel routers = new JLabel("Number of routers: ");
         routers.setForeground(Color.WHITE);
         routers.setSize(280, 40);
         routers.setLocation(20, 150);
         routers.setFont(new Font("Tahoma",Font.PLAIN , 18));
         mainPanel.add(routers);
         
         
        jTextField1 = new JTextField();
        jTextField1.setSize(100, 40);
        jTextField1.setLocation(50, 190);
        mainPanel.add(jTextField1);
         
        JLabel links = new JLabel("Number of links:");
        links.setForeground(Color.WHITE);
        links.setSize(280, 40);
        links.setLocation(320, 150);
        links.setFont(new Font("Tahoma",Font.PLAIN , 18));
        mainPanel.add(links);
        
        jTextField2 = new JTextField();
        jTextField2.setSize(100, 40);
        jTextField2.setLocation(350, 190);
        mainPanel.add(jTextField2);
        
        JLabel destination = new JLabel("Links : ");
        destination.setForeground(Color.WHITE);
        destination.setSize(500, 50);
        destination.setLocation(50, 220);
        destination.setFont(new Font("Tahoma",Font.PLAIN , 18));
        mainPanel.add(destination);
        
        jTextField3 = new JTextField();
        jTextField3.setSize(400, 40);
        jTextField3.setLocation(50, 280);
        mainPanel.add(jTextField3);
        
        
        
        
        
        // create button
        createBtn = new JButton("create");
        createBtn.setSize(120, 40);
        createBtn.setLocation(40, 440);
        createBtn.setFocusable(false);
        createBtn.setFont(new Font("Tahoma",Font.PLAIN , 16));	
        createBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gr = new networkPlot(Integer.parseInt(jTextField1.getText()) ,Integer.parseInt(jTextField2.getText()),jTextField3.getText());
                jactionPerformedCreate(evt);
            }
        });
        mainPanel.add(createBtn);
        
        // graph button
        graphBtn = new JButton("Show network");
        graphBtn.setSize(100, 40);
        graphBtn.setLocation(440, 440);
        graphBtn.setFocusable(false);
        graphBtn.setFont(new Font("Tahoma",Font.PLAIN , 12));	
        graphBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gr = new networkPlot(Integer.parseInt(jTextField1.getText()) ,Integer.parseInt(jTextField2.getText()),jTextField3.getText());
                jgraphactionPerformed(evt);
            }
        });
        mainPanel.add(graphBtn);
        
        // set visible the gui
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
     
    }
    
    
    public void jactionPerformedCreate(ActionEvent e) {
        packetGUI GUI = new packetGUI(gr);
        
    }
    
    public void jgraphactionPerformed(ActionEvent e) {
        viewer = gr.graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    }
    
    public void exitactionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
    public void minactionPerformed(ActionEvent e) {
        setState(JFrame.ICONIFIED);
    }
    
    
    
}
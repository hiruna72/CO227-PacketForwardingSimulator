import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class gui227 extends JFrame {

    private JButton injectBtn,exit,minimize;
    private JPanel mainPanel;
    private Dimension FRAME_SIZE = new Dimension(600, 720);// dimension for main panel
    private Color backgroundColor = new Color(16, 16, 16); 
    private JTable resultsTable;
    private final DefaultTableModel connectionTable_dtm;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;
    private JTextField jTextField5;
    private JTextField jTextField6;
    private JTextField jTextField7;
    private JTextField jTextField8;
    private JTextField jTextField9;
    private int Routers = 10;
    //public static ArrayList<Packet> p = new ArrayList<Packet>();
    
    

    public gui227(){
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
        minimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minactionPerformed(evt);
            }
        });
        mainPanel.add(btnPanel);
        
        // Topic for the gui
        JLabel topic = new JLabel("Packet forwarding network simulator");
        topic.setForeground(Color.WHITE);
        topic.setFont(new Font("Tahoma",Font.BOLD , 28));
        topic.setSize(600, 50);
        topic.setLocation(50, 30);
        mainPanel.add(topic);
        
         JLabel packetName = new JLabel("Name");
         packetName.setForeground(Color.WHITE);
         packetName.setSize(100, 40);
         packetName.setLocation(70, 100);
         packetName.setFont(new Font("Tahoma",Font.PLAIN , 18));
         mainPanel.add(packetName);
         
         
        JLabel start = new JLabel("Source");
        start.setForeground(Color.WHITE);
        start.setSize(100, 40);
        start.setLocation(280, 100);
        start.setFont(new Font("Tahoma",Font.PLAIN , 18));
        mainPanel.add(start);
        
        JLabel destination = new JLabel("Destination");
        destination.setForeground(Color.WHITE);
        destination.setSize(100, 40);
        destination.setLocation(450, 100);
        destination.setFont(new Font("Tahoma",Font.PLAIN , 18));
        mainPanel.add(destination);
        
        //add text areas
        jTextField1 = new JTextField();
        jTextField1.setSize(100, 40);
        jTextField1.setLocation(50, 160);
        mainPanel.add(jTextField1);
        
        jTextField2 = new JTextField();
        jTextField2.setSize(100, 40);
        jTextField2.setLocation(250, 160);
        mainPanel.add(jTextField2);
        
        jTextField3 = new JTextField();
        jTextField3.setSize(100, 40);
        jTextField3.setLocation(450, 160);
        mainPanel.add(jTextField3);
        
        jTextField4 = new JTextField();
        jTextField4.setSize(100, 40);
        jTextField4.setLocation(50, 220);
        mainPanel.add(jTextField4);
        
        jTextField5 = new JTextField();
        jTextField5.setSize(100, 40);
        jTextField5.setLocation(250, 220);
        mainPanel.add(jTextField5);
        
        jTextField6 = new JTextField();
        jTextField6.setSize(100, 40);
        jTextField6.setLocation(450, 220);
        mainPanel.add(jTextField6);
        
        jTextField7 = new JTextField();
        jTextField7.setSize(100, 40);
        jTextField7.setLocation(50, 280);
        mainPanel.add(jTextField7);
        
        jTextField8 = new JTextField();
        jTextField8.setSize(100, 40);
        jTextField8.setLocation(250, 280);
        mainPanel.add(jTextField8);
        
        jTextField9 = new JTextField();
        jTextField9.setSize(100, 40);
        jTextField9.setLocation(450, 280);
        mainPanel.add(jTextField9);
        
        
        // inject button
        injectBtn = new JButton("Inject");
        injectBtn.setSize(120, 40);
        injectBtn.setLocation(240, 350);
        injectBtn.setFocusable(false);
        injectBtn.setFont(new Font("Tahoma",Font.PLAIN , 16));	
        injectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jactionPerformed(evt);
            }
        });
        mainPanel.add(injectBtn);
        
        //results table
        resultsTable=new JTable();
        resultsTable.setModel(
        new DefaultTableModel(new Object [][] {}
                ,new String [] {"Packet name", "Route", "Time"}));
        
        resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);// set resizable the columns
        JScrollPane scrollP2 = new JScrollPane();
        scrollP2.setViewportView(resultsTable);
        scrollP2.setSize(543, 200);
        scrollP2.setLocation(30, 504);
        resultsTable.setBackground(new Color(16, 16, 16));
        resultsTable.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13));
        resultsTable.setForeground(new Color(153, 153, 153));
        resultsTable.setFocusable(false);
        resultsTable.setGridColor(new Color(51, 51, 51));
        resultsTable.setRowHeight(22);
        resultsTable.setSelectionBackground(new Color(51, 51, 51));
        resultsTable.setSelectionForeground(new Color(204, 204, 204));
        resultsTable.getTableHeader().setReorderingAllowed(false);
        
        connectionTable_dtm=(DefaultTableModel) resultsTable.getModel();
        
        resultsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        resultsTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        resultsTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        mainPanel.add(scrollP2);
      
        
        // topic for routed packets table
        JLabel routedPkts = new JLabel("Routed Packets");
        routedPkts.setForeground(Color.CYAN);
        routedPkts.setFont(new Font("Tahoma",Font.BOLD , 22));
        routedPkts.setSize(300, 40);
        routedPkts.setLocation(30,466);
        mainPanel.add(routedPkts);
        
        // set visible the gui
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
     
    }
    
    
    public void jactionPerformed(ActionEvent e) {
        if( checkValidity( jTextField1.getText(),jTextField2.getText(),jTextField3.getText()) ){
            validPackets++;
            Packet pack = new Packet( jTextField1.getText() ,jTextField2.getText(),jTextField3.getText());
            p.add(pack);
        }
        if( checkValidity( jTextField4.getText(),jTextField5.getText(),jTextField6.getText()) ){
            validPackets++;
            Packet pack = new Packet( jTextField4.getText() ,jTextField5.getText(),jTextField6.getText());
            p.add(pack);
        }
        if( checkValidity( jTextField7.getText(),jTextField8.getText(),jTextField9.getText()) ){
            validPackets++;
            Packet pack = new Packet( jTextField7.getText() ,jTextField8.getText(),jTextField9.getText());
            p.add(pack);
        }
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        
    }
    
    public void exitactionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
    public void minactionPerformed(ActionEvent e) {
        setState(JFrame.ICONIFIED);
    }
    
    public void addDataToResultsTable(String name,String route ,String time){
        connectionTable_dtm.addRow(new Object[]{name,route,time});
    }
    
    public boolean checkValidity(String s, String t, String r){
        int T, R;
        
        if(s != null && !s.equals("")){
            try{
                T = Integer.parseInt(t);
                R = Integer.parseInt(r);
                return true;
            } catch (Exception e) {
                System.out.println("Invalid packet");
                return false;
            }
        } else {
            return false;
        }
    }

    
}
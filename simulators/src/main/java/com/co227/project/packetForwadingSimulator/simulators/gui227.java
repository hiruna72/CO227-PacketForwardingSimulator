package com.co227.project.packetForwadingSimulator.simulators;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class gui227 extends JFrame {
	private DrawGraph graph;
	private int validPackets=0;
    private JButton injectBtn,exit,minimize,graphBtn,runBtn;
    private JPanel mainPanel;
    private Dimension FRAME_SIZE = new Dimension(600, 600);// dimension for main panel
    private Color backgroundColor = new Color(16, 16, 16); 
    private JFrame f2;
    private static JTextField jTextField0;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;
    
    private JTextField jTextField5,jTextField6,jTextField7,jTextField8;
    private JTextField jTextField9,jTextField10,jTextField11,jTextField12;
    private JTextField jTextField13,jTextField14,jTextField15,jTextField16;
    
    private int Routers = 10;
    private static ArrayList<ArrayList<String>>injectedPackets;
    //public static ArrayList<Packet> p = new ArrayList<Packet>();
	private static int noPackets = 0;
    
    

    public gui227() {
        // frame creating
       // super();
        f2 = new JFrame("Packet Simulator");
        injectedPackets = new ArrayList<ArrayList<String>>();
        
        setSize(FRAME_SIZE);
        
        
        // main panel is the parent that contains all the elements in the gui 
        mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(null);
        mainPanel.setSize(FRAME_SIZE);
        add(mainPanel);
        
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
         packetName.setLocation(100, 100);
         packetName.setFont(new Font("Tahoma",Font.PLAIN , 18));
         mainPanel.add(packetName);
         
         
        JLabel start = new JLabel("Source");
        start.setForeground(Color.WHITE);
        start.setSize(100, 40);
        start.setLocation(200, 100);
        start.setFont(new Font("Tahoma",Font.PLAIN , 18));
        mainPanel.add(start);
        
        JLabel destination = new JLabel("Destination");
        destination.setForeground(Color.WHITE);
        destination.setSize(100, 40);
        destination.setLocation(300, 100);
        destination.setFont(new Font("Tahoma",Font.PLAIN , 18));
        mainPanel.add(destination);
        
        JLabel packetSize = new JLabel("Packet Size");
        packetSize.setForeground(Color.WHITE);
        packetSize.setSize(100, 40);
        packetSize.setLocation(400, 100);
        packetSize.setFont(new Font("Tahoma",Font.PLAIN , 18));
        mainPanel.add(packetSize);
        
        JLabel noOfPackets = new JLabel("#Packets");
        noOfPackets.setForeground(Color.WHITE);
        noOfPackets.setSize(100, 40);
        noOfPackets.setLocation(0, 500);
        noOfPackets.setFont(new Font("Tahoma",Font.PLAIN , 18));
        mainPanel.add(noOfPackets);
        
        //add text areas
        jTextField0 = new JTextField();
        jTextField0.setSize(100, 40);
        jTextField0.setLocation(100, 500);
        jTextField0.setText("0");
        mainPanel.add(jTextField0);
        
        //add text areas
        jTextField1 = new JTextField();
        jTextField1.setSize(100, 40);
        jTextField1.setLocation(100, 160);
        mainPanel.add(jTextField1);
        
        jTextField2 = new JTextField();
        jTextField2.setSize(100, 40);
        jTextField2.setLocation(200, 160);
        mainPanel.add(jTextField2);
        
        jTextField3 = new JTextField();
        jTextField3.setSize(100, 40);
        jTextField3.setLocation(300, 160);
        mainPanel.add(jTextField3);
        
        jTextField4 = new JTextField();
        jTextField4.setSize(100, 40);
        jTextField4.setLocation(400, 160);
        mainPanel.add(jTextField4);
        
        
        jTextField5 = new JTextField();
        jTextField5.setSize(100, 40);
        jTextField5.setLocation(100, 260);
        mainPanel.add(jTextField5);
        
        jTextField6 = new JTextField();
        jTextField6.setSize(100, 40);
        jTextField6.setLocation(200, 260);
        mainPanel.add(jTextField6);
        
        jTextField7 = new JTextField();
        jTextField7.setSize(100, 40);
        jTextField7.setLocation(300, 260);
        mainPanel.add(jTextField7);
        
        jTextField8 = new JTextField();
        jTextField8.setSize(100, 40);
        jTextField8.setLocation(400, 260);
        mainPanel.add(jTextField8);
        
        
        jTextField9 = new JTextField();
        jTextField9.setSize(100, 40);
        jTextField9.setLocation(100, 360);
        mainPanel.add(jTextField9);
        
        jTextField10 = new JTextField();
        jTextField10.setSize(100, 40);
        jTextField10.setLocation(200, 360);
        mainPanel.add(jTextField10);
        
        jTextField11 = new JTextField();
        jTextField11.setSize(100, 40);
        jTextField11.setLocation(300, 360);
        mainPanel.add(jTextField11);
        
        jTextField12 = new JTextField();
        jTextField12.setSize(100, 40);
        jTextField12.setLocation(400, 360);
        mainPanel.add(jTextField12);
        
        
        
        jTextField13 = new JTextField();
        jTextField13.setSize(100, 40);
        jTextField13.setLocation(100, 460);
        mainPanel.add(jTextField13);
        
        jTextField14 = new JTextField();
        jTextField14.setSize(100, 40);
        jTextField14.setLocation(200, 460);
        mainPanel.add(jTextField14);
        
        jTextField15 = new JTextField();
        jTextField15.setSize(100, 40);
        jTextField15.setLocation(300, 460);
        mainPanel.add(jTextField15);
        
        jTextField16 = new JTextField();
        jTextField16.setSize(100, 40);
        jTextField16.setLocation(400, 460);
        mainPanel.add(jTextField16);
        
        
        
        // inject button
        injectBtn = new JButton("Inject");
        injectBtn.setSize(100, 40);
        injectBtn.setLocation(200, 500);
        injectBtn.setFocusable(false);
        injectBtn.setFont(new Font("Tahoma",Font.PLAIN , 16));	
        injectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jactionPerformed(evt);
            }
        });
        mainPanel.add(injectBtn);
        
        
        // graph button
        graphBtn = new JButton("Graph");
        graphBtn.setSize(100, 40);
        graphBtn.setLocation(300, 500);
        graphBtn.setFocusable(false);
        graphBtn.setFont(new Font("Tahoma",Font.PLAIN , 16));	
        graphBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gactionPerformed(evt);
            }
        });
        mainPanel.add(graphBtn);
        
     // runBtn button
        runBtn = new JButton("Run");
        runBtn.setSize(100, 40);
        runBtn.setLocation(400, 500);
        runBtn.setFocusable(false);
        runBtn.setFont(new Font("Tahoma",Font.PLAIN , 16));	
        runBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ractionPerformed(evt);
            }
        });
        mainPanel.add(runBtn);
        
      
        
       
        
        // set visible the gui
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
     
    }
    
    public void ractionPerformed(ActionEvent e) {
		this.graph.startTimer();
    }
    public void gactionPerformed(ActionEvent e) {
			this.graph = new DrawGraph(Simulator.timeStamps);
    }
    public void jactionPerformed(ActionEvent e) {
    	noPackets = Integer.parseInt(jTextField0.getText());
        if( checkValidity( jTextField1.getText(),jTextField2.getText(),jTextField3.getText()) ){
            validPackets++;
            ArrayList<String> packetDetails = new ArrayList<String>();
            packetDetails.add(jTextField1.getText());
            packetDetails.add(jTextField2.getText());
            packetDetails.add(jTextField3.getText());
            packetDetails.add(jTextField4.getText());
            injectedPackets.add(packetDetails); 
        }
        if( checkValidity( jTextField5.getText(),jTextField6.getText(),jTextField7.getText()) ){
            validPackets++;
            ArrayList<String> packetDetails = new ArrayList<String>();
            packetDetails.add(jTextField5.getText());
            packetDetails.add(jTextField6.getText());
            packetDetails.add(jTextField7.getText());
            packetDetails.add(jTextField8.getText());
            injectedPackets.add(packetDetails);       
        }
        if( checkValidity( jTextField9.getText(),jTextField10.getText(),jTextField11.getText()) ){
            validPackets++;
            ArrayList<String> packetDetails = new ArrayList<String>();
            packetDetails.add(jTextField9.getText());
            packetDetails.add(jTextField10.getText());
            packetDetails.add(jTextField11.getText());
            packetDetails.add(jTextField12.getText());
            injectedPackets.add(packetDetails);       
        }
        
        if( checkValidity( jTextField13.getText(),jTextField14.getText(),jTextField15.getText()) ){
            validPackets++;
            ArrayList<String> packetDetails = new ArrayList<String>();
            packetDetails.add(jTextField13.getText());
            packetDetails.add(jTextField14.getText());
            packetDetails.add(jTextField15.getText());
            packetDetails.add(jTextField16.getText());
            injectedPackets.add(packetDetails);       
        }
        
        
        jTextField0.setText("");
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        jTextField13.setText("");
        jTextField14.setText("");
        jTextField15.setText("");
        jTextField16.setText("");
    }
    
    public void exitactionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
    public void minactionPerformed(ActionEvent e) {
        setState(JFrame.ICONIFIED);
    }
    
    //should check for valid router number
    
    public boolean checkValidity(String s, String t, String r){
        int src, dest;
        
        if(s != null && !s.equals("")){
            try{
            	src = Integer.parseInt(t);
            	dest = Integer.parseInt(r);
                return true;
            } catch (Exception e) {
                System.out.println("Invalid packet");
                return false;
            }
        } else {
            return false;
        }
    }


	public static void addNewPackets() {
		
		int length = injectedPackets.size();
		if(length==noPackets && noPackets>0){
		//	System.out.println(length);
			for(int i=0;i<length;i++){
	//			System.out.println("adding Packets");
				Packet packet = new Packet( injectedPackets.get(i).get(0),Integer.parseInt(injectedPackets.get(i).get(1)),Integer.parseInt(injectedPackets.get(i).get(2)),Double.parseDouble(injectedPackets.get(i).get(3)),"InputQ",injectedPackets.get(i).get(1)+" to "+injectedPackets.get(i).get(1));
				if(Simulator.InputBuffer.get(injectedPackets.get(i).get(1)+" to "+injectedPackets.get(i).get(1)).addPacketVirtually(Double.parseDouble(injectedPackets.get(i).get(3)))){
			     	Simulator.InputBuffer.get(injectedPackets.get(i).get(1)+" to "+injectedPackets.get(i).get(1)).addPacket(injectedPackets.get(i).get(0));
			     	Simulator.Packets.put(injectedPackets.get(i).get(0), packet);
				}
				else{
					System.out.println(injectedPackets.get(i).get(1)+" to "+injectedPackets.get(i).get(1)+" InputQ is full "+injectedPackets.get(i).get(0)+" is lost");
				}
				//injectedPackets.remove(i);
			}
			injectedPackets.clear();
			
		}
		
	}

    
	
	
	
	
}
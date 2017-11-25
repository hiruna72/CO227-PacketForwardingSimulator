package com.co227.project.packetForwadingSimulator.simulators;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class gui227 extends JFrame {
	private int validPackets=0;
    private JButton injectBtn,exit,minimize;
    private JPanel mainPanel;
    private Dimension FRAME_SIZE = new Dimension(600, 600);// dimension for main panel
    private Color backgroundColor = new Color(16, 16, 16); 
    private JFrame f2;
  
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;
    
    private JTextField jTextField5,jTextField6,jTextField7,jTextField8;
    private JTextField jTextField9,jTextField10,jTextField11,jTextField12;
    
    private int Routers = 10;
    private static ArrayList<ArrayList<String>>injectedPackets;
    //public static ArrayList<Packet> p = new ArrayList<Packet>();
    
    

    public gui227() {
        // frame creating
        super();
        f2 = new JFrame("Packet Simulator");
        injectedPackets = new ArrayList<ArrayList<String>>();
        
        
        
        setSize(FRAME_SIZE);
        
        
        // main panel is the parent that contains all the elements in the gui 
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
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
        
        
        // inject button
        injectBtn = new JButton("Inject");
        injectBtn.setSize(120, 40);
        injectBtn.setLocation(240, 500);
        injectBtn.setFocusable(false);
        injectBtn.setFont(new Font("Tahoma",Font.PLAIN , 16));	
        injectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jactionPerformed(evt);
            }
        });
        mainPanel.add(injectBtn);
        
        
        
      
        
       
        
        // set visible the gui
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
     
    }
    
    
    public void jactionPerformed(ActionEvent e) {
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
		if(length>2){
	//		System.out.println(length);
			for(int i=0;i<length;i++){
	//			System.out.println("adding Packets");
				Packet packet = new Packet( injectedPackets.get(i).get(0),Integer.parseInt(injectedPackets.get(i).get(1)),Integer.parseInt(injectedPackets.get(i).get(2)),Double.parseDouble(injectedPackets.get(i).get(3)),"InputQ",injectedPackets.get(i).get(1)+" to "+injectedPackets.get(i).get(1));
				if(Simulator.InputBuffer.get(injectedPackets.get(i).get(1)+" to "+injectedPackets.get(i).get(1)).addPacketVirtually(1F)){
			     	Simulator.InputBuffer.get(injectedPackets.get(i).get(1)+" to "+injectedPackets.get(i).get(1)).addPacket(injectedPackets.get(i).get(0));
			     	Simulator.Packets.put(injectedPackets.get(i).get(0), packet);
				}
				//injectedPackets.remove(i);
			}
			injectedPackets.clear();
			
		}
		
	}

    
	
	
	
	
}
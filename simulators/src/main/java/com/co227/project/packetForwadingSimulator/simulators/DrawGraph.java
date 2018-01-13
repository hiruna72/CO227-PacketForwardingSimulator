package com.co227.project.packetForwadingSimulator.simulators;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;




public class DrawGraph implements ActionListener {
	
	//public Viewer fasdj;//org.graphstream.ui.renderer;
	public static String startingEdge;
	public static String startingNode;
	public static int edgeNo;
	public int length=0;
    public Graph graph;
    public Timer watch; 
    public SpriteManager sman;
    private ArrayList<ArrayList<String>> timeStamps;
	DrawGraph(ArrayList<ArrayList<String>> timeStamps){
		graph = new SingleGraph("net");
		this.timeStamps = timeStamps;
		createGraph(graph);
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MyNewFrame frame = new MyNewFrame(graph);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
		watch = new Timer(1000, this);
		
		
		
	}
	
	
	
	public void createGraph(Graph graph){
		
		graph.setStrict(false);
		graph.setAutoCreate( true );
		
		graph.addEdge("0 to 1", "0","1");
	    graph.addEdge("7 to 0", "0", "7");
	    graph.addEdge("1 to 2", "1", "2");
	    graph.addEdge("1 to 7", "1", "7");
	    graph.addEdge("2 to 3", "2", "3");
	    graph.addEdge("2 to 8", "2", "8");
	    graph.addEdge("2 to 5", "2", "5");
	    graph.addEdge("3 to 4", "3", "4");
	    graph.addEdge("3 to 5", "3", "5");
	    graph.addEdge("4 to 5", "4", "5");
	    graph.addEdge("5 to 6", "5", "6");
	    graph.addEdge("6 to 7", "6", "7");
	    graph.addEdge("6 to 8", "6", "8");
	    graph.addEdge("7 to 8", "7", "8");
	    graph.addAttribute("ui.stylesheet", 
	    		"graph { fill-color: black; }"
	    		+ "node { fill-color: blue; text-mode: normal;text-color: white;size: 15px;text-size:15px;text-alignment:center; } "
	    		+ "edge { fill-color: yellow; }"
	    		+ "sprite { fill-color: yellow;text-mode: normal; text-color: white;size: 10px;text-size:10px;text-alignment:above; }");
	    
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
		for(Node n:graph) {
			//System.out.println("node ID: "+n.getId()+" degree of node "+n.getDegree());
			n.addAttribute("label", n.getId());
		}
	}


	public void actionPerformed(ActionEvent e) {
		String [] colors = {"red","green","yellow","blue"};
		if(this.length<this.timeStamps.size()){
			this.sman = new SpriteManager(graph);
			for(int i=0;i<this.timeStamps.get(this.length).size();i++){
				 String packetID =  this.timeStamps.get(this.length).get(i).split(" ")[0];
				 Sprite s = sman.addSprite(packetID); 	
				 s.addAttribute("label", packetID);
				 int no =Integer.parseInt(packetID.split("p")[1]);
				 System.out.println(colors[no-1]);
				 s.addAttribute("fill-color:red");
				 String locationType = this.timeStamps.get(this.length).get(i).split(" ")[1];
				 if(locationType.equals("r")){
					 System.out.println("on a node "+this.timeStamps.get(this.length).get(i).split(" ")[0]);
					 String location = this.timeStamps.get(this.length).get(i).split(" ")[2];
					 s.detach();
					 s.attachToNode(location);
					 s.setPosition(0);
				 }
				 else{
					 System.out.println("on a link "+this.timeStamps.get(this.length).get(i).split(" ")[0]);
					 String location = this.timeStamps.get(this.length).get(i).split(" l ")[1];
					 String edge1 = location.split(" to ")[0];
					 String edge2 = location.split(" to ")[1];
					 location = edge1+" to "+edge2;
					 s.detach();
					 s.attachToEdge(location);
					 if(!s.attached()){
						 location = edge2+" to "+edge1;
						 s.detach();
						 s.attachToEdge(location);
					 }
					 s.setPosition(0.5);
				 }
				 
				 
				// s.detach();
			}
	    	this.length++;
	    	
	    }
		else{
			
			System.out.println("over");
		}
		
	}



	public void startTimer() {
		this.watch.start();
	}
}

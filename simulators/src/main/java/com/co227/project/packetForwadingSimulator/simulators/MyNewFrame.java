package com.co227.project.packetForwadingSimulator.simulators;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class MyNewFrame extends JFrame{
    private static final long serialVersionUID = 8394236698316485656L;

    //private Graph graph = new MultiGraph("embedded");
    //private Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
    private Viewer viewer; 
    private View view; 

    public MyNewFrame(Graph graph) {
         setLayout(new BorderLayout());
         this.viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
         this.view = viewer.addDefaultView(false);
         add((Component) view, BorderLayout.CENTER);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         viewer.enableAutoLayout();
    }
}
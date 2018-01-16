package sample;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

public class MyNewFrame extends JFrame
{
    private static final long serialVersionUID = 8394236698316485656L;
    public static Viewer viewer;
    private View view;

    public MyNewFrame(Graph graph)
    {
        setLayout(new BorderLayout());
        this.viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.view = viewer.addDefaultView(false);
        add((Component) view, BorderLayout.CENTER);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        viewer.enableAutoLayout();

    }
}
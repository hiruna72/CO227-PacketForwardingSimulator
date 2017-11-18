import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.Viewer;

public class networkPlot {

		protected Graph graph;
		public Viewer viewer;

		public networkPlot (int routers, int links, String linkDetails){
			graph = new SingleGraph("Network");
			Node[] n = new Node[routers];
			for(int i = 1; i<= routers; i++){
				int k = i-1;
				n[k] = graph.addNode("R" + i);
				n[k].addAttribute("ui.label", "R"+i);
			}

			String[] linksArray = linkDetails.split(",");
			for(int j = 1; j <= links; j++){
				String[] a = linksArray[j-1].split(" ");
				graph.addEdge("L"+j, "R"+a[0], "R"+a[1]);
			}
			
			
		}

		public void showGraph(){
			graph.display();
		}

		
}
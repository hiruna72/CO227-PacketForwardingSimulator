package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Simulator
{
	static int noRouters = 5,noLinks;
    static HashMap<String,Link>Links = new HashMap<>();
    static int[][] adjecencyMat = null,forwardingTable;
	static ArrayList<Router> Routers = new ArrayList<Router>();

	public static void main(String[] args)
	{


		boolean settingTopology=false;



		for(int i=0;i<adjecencyMat.length;i++)
		{
			for(int j=0;j<adjecencyMat.length;j++)
			{
				System.out.print(adjecencyMat[i][j]+" ");
			}
			System.out.println();
		}

		int src=0;
		forwardingTable= new int[noRouters][noRouters];
		ForwadingTable table1= new ForwadingTable(adjecencyMat,src,forwardingTable);
//		for(int i=0;i<forwardingTable.length;i++){
//			for(int j=0;j<forwardingTable.length;j++){
//				System.out.print(forwardingTable[i][j]+" ");
//			}
//			System.out.println();
//		}
//		Pair tempPair = new Pair(0,1);
//		System.out.println(Links.get(tempPair.getPair()).getLinkId());
		
		
		//initialize routers
		for(int i=0;i<noRouters;i++){
			Router tempRouter = new Router(i,adjecencyMat,forwardingTable,Links);
			Routers.add(tempRouter);
		}
		
		settingTopology = true;
		

/*		Packet firstPacket = new Packet("firstPacket","2","0");
		firstPacket.setRoute("routerID 2");
		Links.get("2 to 1").addPacketIn(firstPacket);
		
		Packet firstPacket = new Packet("firstPacket","2","0");
		Routers.get(2).addToPCconnectedQ(firstPacket);
		
		Packet p3 = new Packet("p3","2","0");
		Routers.get(2).addToPCconnectedQ(p3);
		
		Packet p4 = new Packet("p4","2","0");
		Routers.get(2).addToPCconnectedQ(p4);
		
		Packet p5 = new Packet("p5","2","0");
		Routers.get(2).addToPCconnectedQ(p5);
		
		Packet p6 = new Packet("p6","2","0");
		Routers.get(2).addToPCconnectedQ(p6);
		
		Packet p7 = new Packet("p7","2","0");
		Routers.get(2).addToPCconnectedQ(p7);
		Packet secondPacket = new Packet("secondPacket","0","3");
		secondPacket.setRoute("routerID 0");
		Links.get("0 to 2").addPacketIn(secondPacket);
		
		int cycleNo=0;
		while(cycleNo<10){
			for(int i=0;i<noRouters;i++){
				Routers.get(i).process(cycleNo);
			}
			cycleNo++;
		}
		System.out.println("firstPacket");
		for(int i=0;i<firstPacket.getRoute().size();i++){
			System.out.println(firstPacket.getRoute().get(i));
		}
		System.out.println("secondPacket");
		for(int i=0;i<secondPacket.getRoute().size();i++){
			System.out.println(secondPacket.getRoute().get(i));
		}*/
	}

	public void fileOpenMethod(File file)
    {
        boolean firstLine=true;
        //String network="B.txt";
        //String matFile = "C:/Hiruna/CO227/Project/src/"+network;
        //String matFile = "./src/"+network;
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            while ((line = br.readLine()) != null )
            {
                String [] cmd = line.split(" ");
                if(firstLine)
                {
                    noRouters=Integer.valueOf(cmd[0]);
                    noLinks=Integer.valueOf(cmd[1]);
                    adjecencyMat = new int[noRouters][noRouters];
                    firstLine=false;
                    //System.out.println(firstLine);
                }
                else
                {
                    int router1=Integer.valueOf(cmd[0]);
                    int router2=Integer.valueOf(cmd[1]);
                    //Pair tempPair = new Pair(router1,router2);
                    Link tempLink1 = new Link((router1)+" to "+(router2),cmd[0],cmd[1]);
                    Links.put((router1)+" to "+(router2), tempLink1);
                    Controller.data.add(tempLink1);
                    Link tempLink2 = new Link((router2)+" to "+(router1),cmd[1],cmd[0]);
                    Links.put((router2)+" to "+(router1), tempLink2);
                    //System.out.println(Links.get((router2)+" to "+(router1)).getLinkId());

                    adjecencyMat[router1][router2]=1;
                    adjecencyMat[router2][router1]=1;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

# CO227-PacketForwardingSimulator
Packet forwarding network simulator

input file method

[no of routers] [no of links]  
//then describe link one by one  
[route1] [router2] [link distance] [propagation speed of the link]  

to run
compile and run app.java file

example input method used 

9 14  
0 1 10 10  
0 7 20 10  
1 2 30 15  
1 7 40 20  
2 3 60 20  
2 8 70 14  
2 5 80 16  
3 4 50 10  
3 5 40 20  
4 5 60 15  
5 6 90 15  
6 7 75 15  
6 8 65 13  
7 8 45 45  

which describes the following graph (discard lengths of links)

![Alt text](/simulators/src/main/java/com/co227/project/packetForwadingSimulator/simulators/graph.PNG?raw=true "exampleGraph")

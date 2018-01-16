#Create a simulator object
set ns [new Simulator]

#Define different colors for data flows (for NAM)
$ns color 1 Blue
$ns color 2 Red

#Open the NAM trace file
set nf [open out.nam w]
$ns namtrace-all $nf

set nt [open test.tr w]
$ns trace-all $nt
#Define a 'finish' procedure
proc finish {} {
        global ns nf nt
        $ns flush-trace
        #Close the NAM trace file
        close $nf
	close $nt
        #Execute NAM on the trace file
        exec nam out.nam &
        exit 0
}

#Create four nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

#Create links between the nodes
$ns duplex-link $n0 $n2 2Mb 1ms DropTail
$ns duplex-link $n1 $n2 2Mb 1ms DropTail
$ns duplex-link $n2 $n3 1.7Mb 2ms DropTail

#Set Queue Size of link (n2-n3) to 10
$ns queue-limit $n2 $n3 10

#Give node position (for NAM)
$ns duplex-link-op $n0 $n2 orient right-down
$ns duplex-link-op $n1 $n2 orient right-up
$ns duplex-link-op $n2 $n3 orient right

#Monitor the queue for link (n2-n3). (for NAM)
$ns duplex-link-op $n2 $n3 queuePos 0.5




#Setup a UDP connection
set udp [new Agent/UDP]
$ns attach-agent $n1 $udp
set null [new Agent/Null]
$ns attach-agent $n3 $null
$ns connect $udp $null
$udp set fid_ 2

#Setup a CBR over UDP connection
set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set type_ CBR
$cbr set packet_size_ 1000
$cbr set interval_ 2
$cbr set random_ false



#Setup a UDP connection
set udp2 [new Agent/UDP]
$ns attach-agent $n3 $udp2
set null2 [new Agent/Null]
$ns attach-agent $n1 $null2
$ns connect $udp2 $null2
$udp2 set fid_ 2

#Setup a CBR over UDP connection
set cbr2 [new Application/Traffic/CBR]
$cbr2 attach-agent $udp2
$cbr2 set type_ CBR
$cbr2 set packet_size_ 1000
$cbr2 set interval_ 2
$cbr2 set random_ false


#Setup a UDP connection
set udp0 [new Agent/UDP]
$ns attach-agent $n0 $udp0
$ns connect $udp0 $null
$udp0 set fid_ 2

#Setup a CBR over UDP connection
set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $udp0
$cbr0 set type_ CBR
$cbr0 set packet_size_ 1000
$cbr0 set interval_ 2
$cbr0 set random_ false



#Schedule events for the CBR and FTP agents
$ns at 0.1 "$cbr start"
$ns at 0.1 "$cbr2 start"
$ns at 0.1 "$cbr0 start"
$ns at 4.5 "$cbr stop"
$ns at 4.5 "$cbr2 stop"
$ns at 4.5 "$cbr0 stop"

#Call the finish procedure after 5 seconds of simulation time
$ns at 5.0 "finish"

#Print CBR packet size and interval
puts "CBR packet size = [$cbr set packet_size_]"
puts "CBR interval = [$cbr set interval_]"

#Run the simulation
$ns run


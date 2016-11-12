import java.io.*; 
import java.net.*; 
import java.util.LinkedList;
public class UDP_Server implements Cloneable, Runnable
{ 
	private static final int ECHOMAX  = 255;      // Maximum size of Echo //
	private static final int TIMEOUT  = 55000;    // Resend timeout (milliseconds)
	//private static final int MAXTRIES = 5;      // Maximum retransmissions
	private static final int PORT0    = 8083;
	private static final int PORT1    = 8084;
	private DatagramSocket socket;
	private DatagramPacket packet;
	InetAddress serverAddress;
	Thread runner = null;
	volatile boolean shouldStop = false;
	
	
	
	public static void main(String[] args) throws IOException{
		UDP_Server brk = new UDP_Server();
		Lname obj = new Lname();
		obj.start();
		obj.run();
		brk.startserver(PORT0);
	}
	
	
	public synchronized void startserver(int port) throws IOException{
		serverAddress = InetAddress.getByName("255.255.255.255");
		socket = new DatagramSocket();
    	System.out.println(socket.getLocalPort());
    	
		if (runner == null){
			socket = new DatagramSocket(PORT0);
	    	socket.setSoTimeout(TIMEOUT);
	    	socket.setBroadcast(true);
	    	runner = new Thread(this);
	    	runner.start();
		}
	}
	
	public synchronized void stopServer(){
		if (socket != null){
			shouldStop = true;
			runner.interrupt();
			runner = null;
			socket.close();
		}
		}
		
		public void run(){
			if (socket != null){
				while (!shouldStop){
					try{							   
						while (true) 
						{ // Run forever, receiving and echoing datagrams  // Receive packet from client
							packet = new DatagramPacket(new byte[255], ECHOMAX);
							socket.receive(packet);
							System.out.println("Handling client at " + socket.getLocalPort() + " on port " + packet.getPort() + " message: " + new String(packet.getData()));
							System.out.println(packet.getPort() + " " + packet.getAddress());
							socket.send(packet); 
							packet.setData(new byte[255], 0, 254);
						} 
					}
					catch(IOException e){}
		   		}
				
			}
		}
		
		public static class Lname extends Thread{
			public void run() {
				try {
					DatagramSocket socketx = new DatagramSocket();
					socketx = new DatagramSocket(PORT1);
					socketx.setSoTimeout(TIMEOUT);
					socketx.setBroadcast(true);
					DatagramPacket packetx = new DatagramPacket(new byte[255], ECHOMAX);
					socketx.receive(packetx);
					System.out.println("User Name: ");
					System.out.print( new String(packetx.getData()));
 
					//listx.add(new clients(ad,ips)); 
					System.out.println("Connected...");
 
					
					} catch (IOException e) {
					// TODO Auto-generated catch block
			 
					}
			}
		}
		
		public class clients {
			//attributes
			String ip; 
			String name;
			public clients(String a, String ip){this.ip = ip; name    = a;}
 
		}
	} 

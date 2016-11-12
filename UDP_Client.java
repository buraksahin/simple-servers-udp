 

import java.net.*;
import java.io.*;
import java.util.*;


public class UDP_Client {
	private static final int TIMEOUT  = 3000;  // Resend timeout (milliseconds)
	private static final int MAXTRIES = 10;    // Maximum retransmissions
	private static final int PORT0    = 8083;
	private static final int PORT1    = 8084;

	public static class Lname extends Thread{
			public void run() {
				try{
					DatagramSocket socketx = new DatagramSocket();
					Scanner xmessage = new Scanner(System.in);
					InetAddress serverAddressx = InetAddress.getByName("255.255.255.255");
					DatagramPacket sendPacketx;
					byte[] nameToSend;
					String name;
					name = xmessage.nextLine();
					String portx = socketx.getPort() + "";
					String total = portx + "+" + name;
					nameToSend   = (byte[])total.getBytes();
					nameToSend = (byte[])total.getBytes();
					sendPacketx    = new DatagramPacket(nameToSend, nameToSend.length, serverAddressx, PORT1);
					socketx.send(sendPacketx); 
			}
			catch (IOException e){ }
			}
			}
			
			
			
    public static void main(String[] argv) throws IOException{
    	//InetAddress serverAddress = InetAddress.getByName("255.255.255.255");
			InetAddress serverAddress = InetAddress.getByName("255.255.255.255");
    	Lname a = new Lname();
		a.start();
		// - Scanner for message - //
    	Scanner smessage = new Scanner(System.in);
		
		// - Variable for packet - //
    	byte[] bytesToSend;
    	
		// - Datagram Socket UPD - //
    	DatagramSocket socket = new DatagramSocket();
    	System.out.println("Local port number: " + socket.getLocalPort());
		System.out.println("Port number      : " + socket.getPort());
		
		// - Timeout - //
    	socket.setSoTimeout(TIMEOUT);
		
		// - Broadcast TRUE - //
    	socket.setBroadcast(true);
		
		// - UDP Packages - //
    	DatagramPacket sendPacket;
    	DatagramPacket receivePacket;
    	
		// - Counting Tries - //
    	int tries = 0;
    	
		// - Check Received - //
    	boolean receivedResponse = false;
		
		// - Message out to consol - //
    	System.out.println("Please enter a message...");
		
    	do{
    		
    		try{
    			bytesToSend   = new byte[255];
    			bytesToSend   = (byte[])smessage.nextLine().getBytes();
        		sendPacket    = new DatagramPacket(bytesToSend, bytesToSend.length, serverAddress, PORT0);
        		socket.send(sendPacket); 
        		receivePacket = new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);
        		socket.receive(receivePacket);
    	    	System.out.println("Received: " + new String(receivePacket.getData()));    	      
    		}
    		catch (InterruptedIOException e){tries+= 1;}
    	}while ((!receivedResponse)&&(tries<MAXTRIES));
    	
    	
    	socket.close();
    }
}
		
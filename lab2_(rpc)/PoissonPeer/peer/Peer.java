package PoissonPeer.peer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Peer {
    String host;
    Logger logger;
	Integer port;
	Integer[] availablePorts;

    public Peer(String hostname, Integer port, Integer[] portList) {
		host   = hostname;
		logger = Logger.getLogger("logfile_" + hostname + ":"  + port);
		this.port = port;
		availablePorts = removeSamePort(port, portList);
		try {
			FileHandler handler = new FileHandler("./" + hostname + ":" + port + "_peer.log", true);
			logger.addHandler(handler);
			SimpleFormatter formatter = new SimpleFormatter();
			handler.setFormatter(formatter);
		} catch ( Exception e ) {
			 e.printStackTrace();
		}
    }

	private static Integer[] removeSamePort(Integer port, Integer[] portList){
		Integer[] newPortList = new Integer[portList.length - 1];
		int i = 0;
		for (Integer p : portList){
			if (p != port){
				newPortList[i] = p;
				i++;
			}
		}
		return newPortList;
	}

	public void start(){
		try {
			System.out.printf("new peer @ host=%s:%s\n", host, port);
			new Thread(new Server(host, port, logger)).start();
			new Thread(new Client(host, availablePorts,logger)).start();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}


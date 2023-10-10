package PoissonPeer.peer;

import PoissonPeer.PoissonProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

class Client implements Runnable {
    String host;
    Logger logger;
    Scanner scanner;
    Integer[] availablePorts;

    static final int lambda = 5;


    public Client(String host, Integer[] availablePorts, Logger logger) throws Exception {
        this.host = host;
        this.logger = logger;
        this.scanner = new Scanner(System.in);
        this.availablePorts = availablePorts;
    }

    private void sendRandomCommand(String server, Integer port, String command){
        String message = host + " " + port + " " + command;
        logger.info("client: message to send: " + message);

        //make connection
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getByName(server), port);
            logger.info("client: connected to server " + socket.getInetAddress() + "[port = " + socket.getPort() + "]");
        } catch (IOException e) {
            logger.severe("client: error connecting to server " + server + "[port = " + port + "]");
            throw new RuntimeException(e);
        }

        //prepare socket I/O channels
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            logger.severe("client: error opening I/O channels");
            throw new RuntimeException(e);
        }

        //send command
        out.println(command);
        out.flush();

        //receive result
        String result = null;
        try {
            result = in.readLine();
        } catch (IOException e) {
            logger.severe("client: error reading result");
            throw new RuntimeException(e);
        }
        logger.info("client: result = " + result);

        //close connection
        try {
            socket.close();
        } catch (IOException e) {
            logger.severe("client: error closing connection");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            logger.info("client: endpoint running ...\n");
            PoissonProcess poisson = new PoissonProcess(lambda, new Random());

            /*
             * send messages such as:
             *   - ip port add x y
             *   - ip port sub x y
             *   - ip port mul x y
             *   - ip port div x y
             * where x, y are floting point values and ip is the address of the server
             */
            while (true) {
                try {
                    //Use PoissonProcess to generate the time between requests
                    double time = poisson.timeForNextEvent() * 60.0 * 1000.0;
                    System.out.println("next event in -> " + (int)time + " ms");
                    try {
                        Thread.sleep((int)time);
                    }
                    catch (InterruptedException e) {
                        System.out.println("thread interrupted");
                        e.printStackTrace(System.out);
                    }

                    //Generate random command
                    Random rand = new Random();
                    int randomPort = rand.nextInt(availablePorts.length);
                    int randomCommand = rand.nextInt(4);
                    int x = rand.nextInt(100);
                    int y = rand.nextInt(100);
                    String command = "";
                    switch (randomCommand) {
                        case 0:
                            command = "add";
                            break;
                        case 1:
                            command = "sub";
                            break;
                        case 2:
                            command = "mul";
                            break;
                        case 3:
                            command = "div";
                            break;
                    }
                    sendRandomCommand(host, availablePorts[randomPort], command + " " + x + " " + y);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

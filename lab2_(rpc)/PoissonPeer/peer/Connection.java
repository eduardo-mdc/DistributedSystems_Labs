package PoissonPeer.peer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

class Connection implements Runnable {
    String clientAddress;
    Socket clientSocket;
    Logger logger;

    public Connection(String clientAddress, Socket clientSocket, Logger logger) {
        this.clientAddress = clientAddress;
        this.clientSocket = clientSocket;
        this.logger = logger;
    }

    @Override
    public void run() {
        /*
         * prepare socket I/O channels
         */
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String command;
            command = in.readLine();
            logger.info("server: message from host " + clientAddress + "[command = " + command + "]");
            /*
             * parse command
             */
            Scanner sc = new Scanner(command);
            String op = sc.next();
            double x = Double.parseDouble(sc.next());
            double y = Double.parseDouble(sc.next());
            double result = 0.0;
            /*
             * execute op
             */
            switch (op) {
                case "add":
                    result = x + y;
                    break;
                case "sub":
                    result = x - y;
                    break;
                case "mul":
                    result = x * y;
                    break;
                case "div":
                    result = x / y;
                    break;
            }
            /*
             * send result
             */
            out.println(String.valueOf(result));
            out.flush();
            /*
             * close connection
             */
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

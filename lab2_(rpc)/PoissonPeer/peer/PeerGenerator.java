package PoissonPeer.peer;

import java.util.ArrayList;

public class PeerGenerator {

    static Integer[] peerPortList = {5000, 5001, 5002, 5003, 5004};

    public static void main(String[] args) {
        String hostname = args[0];
        ArrayList<Peer> peers = new ArrayList<>();
        for (Integer port : peerPortList) {
            try {
                peers.add(new Peer(hostname, port, peerPortList));
                peers.get(peers.size() - 1).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}

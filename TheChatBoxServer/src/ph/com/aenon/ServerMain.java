package ph.com.aenon;

import java.net.*;

public class ServerMain {

    private static DatagramSocket serverSocket;
    private static DatagramPacket receivePacket;
    private static DatagramPacket sendPacket;

    public static void main(String[] args) throws Exception{
        System.out.println("UDP Server Online!");
        System.out.println("");

        serverSocket = new DatagramSocket(9876);

        String codeOnline = new String("ONLINE123*.");
        String codeOffline = new String("OFFLINE321.*");

        String client1 = new String("");
        String client2 = new String("");

        while(true){
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            if (sentence.equals(codeOnline)) {
                if (client1.equals("")) {
                    client1 = IPAddress.toString();
                    System.out.println("");
                    System.out.println(IPAddress + " goes online!");
                } else if (client2.equals("")) {
                    client2 = IPAddress.toString();
                    System.out.println("");
                    System.out.println(IPAddress + " goes online!");
                }
            } else if (sentence.equals(codeOffline)){
                if (client1.equals(IPAddress.toString())){
                    client1 = "";
                    System.out.println("");
                    System.out.println(IPAddress + " went offline!");
                    System.out.println("");
                }
            } else{
                System.out.println(IPAddress + ": " + sentence);
                String capitalizedSentence = sentence.toUpperCase();
                sendData = capitalizedSentence.getBytes();

                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }

        }
    }
}

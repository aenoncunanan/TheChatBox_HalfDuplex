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

        String preCode = new String(".,paSs,#");
        String codeOnline = new String("ok");
        String codeOffline = new String("OFFLINE321.*");

        String clientAddress1 = new String("");
        String clientName1 = new String("");
        String clientAddress2 = new String("");
        String clientName2 = new String("");

        while(true){
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

            String checkCode = "";
            for (int i = 0; i < preCode.length() && sentence.length() >= preCode.length(); i++){
                checkCode = checkCode + sentence.charAt(i);
            }

            if (checkCode.equals(preCode)){
                if (sentence.equals(preCode + codeOnline)){
                    String toSend = "matched!";
                    sendData = toSend.getBytes();

                    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    serverSocket.send(sendPacket);

                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);

                    String clientName = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    if (clientAddress1.equals("")) {
                        clientAddress1 = IPAddress.toString();
                        clientName1 = clientName;
                        System.out.println("");
                        System.out.println(clientName1 + "(" + IPAddress + ")" + " goes online!");
                    } else if (clientAddress2.equals("")) {
                        clientAddress2 = IPAddress.toString();
                        clientName2 = clientName;
                        System.out.println("");
                        System.out.println(clientName2 + "(" + IPAddress + ")" + " goes online!");
                    }
                }else{
                    String toSend = "mismatched!";
                    sendData = toSend.getBytes();

                    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    serverSocket.send(sendPacket);
                }
            }else if (sentence.equals(codeOffline)){
                if (clientAddress1.equals(IPAddress.toString())){
                    clientAddress1 = "";
                    System.out.println("");
                    System.out.println(clientName1 + "(" + IPAddress + ")" + " went offline!");
                    System.out.println("");
                }else if (clientAddress2.equals(IPAddress.toString())){
                    clientAddress2 = "";
                    System.out.println("");
                    System.out.println(clientName2 + "(" + IPAddress + ")" + " went offline!");
                    System.out.println("");
                }
            } else{
                System.out.println(IPAddress + ": " + sentence);
                String toSend = IPAddress + ": " + sentence;
                sendData = toSend.toUpperCase().getBytes();

                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }

        }
    }
}
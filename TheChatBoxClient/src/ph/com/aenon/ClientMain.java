package ph.com.aenon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

public class ClientMain {

    private static DatagramSocket clientSocket;
    private static DatagramPacket sendPacket;
    private static DatagramPacket receivePacket;

    public static void main(String[] args) throws Exception{
        Scanner s = new Scanner(System.in);
        boolean online = true;

        String ServerIP = "192.168.1.9"; //Server's IP Address

        clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(ServerIP);

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        String codeOnline = "ONLINE123*.";
        String codeOffline = "OFFLINE321.*";

        sendData = codeOnline.getBytes();

        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);

        while(online){
            System.out.println("Enter your message: ");
            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            clientSocket = new DatagramSocket();

            IPAddress = InetAddress.getByName(ServerIP);

            sendData = new byte[1024];
            receiveData = new byte[1024];

            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();

            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);

            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + modifiedSentence);

            System.out.println("");
            System.out.print("Go Offline? [Y/N] ");
            String choice = s.nextLine();
            System.out.println("");

            switch(choice){
                case "Y":
                    online = false;

                    sendData = codeOffline.getBytes();

                    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                    clientSocket.send(sendPacket);

                    clientSocket.close();
                    break;
                case "y":
                    online = false;

                    sendData = codeOffline.getBytes();

                    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                    clientSocket.send(sendPacket);

                    clientSocket.close();
                    break;
                case "N":
                    online = true;
                    break;
                case "n":
                    online = true;
                    break;
                default:
                    online = false;

                    sendData = codeOffline.getBytes();

                    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                    clientSocket.send(sendPacket);

                    clientSocket.close();
            }

        }


    }
}

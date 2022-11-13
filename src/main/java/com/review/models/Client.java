package com.review.models;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    private int destPort ;
    private String hostname;
    private DatagramSocket socket;
    private DatagramPacket dpreceive, dpsend;
    private InetAddress add;
    private Scanner stdIn;

    public Client(int destPort, String hostname) throws IOException {
        this.destPort = destPort;
        this.hostname = hostname;
        this.add = InetAddress.getByName(hostname);
        this.socket = new DatagramSocket();
        this.stdIn = new Scanner(System.in);
    }
    public void SearchProduct(String query) throws IOException{
        query = "search#"+query;
        byte[] data = query.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }

    public void ConnectClient() throws IOException {
        while(true){
            System.out.print("Client input: \n");
            String tmp = stdIn.nextLine();
            byte[] data = tmp.getBytes();
            dpsend = new DatagramPacket(data, data.length, add, destPort);
            socket.send(dpsend);
            if(tmp.equals("bye")){
                System.out.println("Client socket closed");
                stdIn.close();
                socket.close();
                break;
            }
            //Get response from server
            dpreceive = new DatagramPacket(new byte[4096],4096);
            socket.receive(dpreceive);
            tmp = new String(dpreceive.getData(),0,dpreceive.getLength());
            System.out.println("Client get :");
            System.out.println(tmp);
        }
    }
    public static void main(String args[]){

        try {
            Client cl = new Client(1234,"localhost");
            cl.ConnectClient();
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
}

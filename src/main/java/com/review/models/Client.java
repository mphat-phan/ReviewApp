package com.review.models;

import com.review.Encryptions.AES;
import com.review.Encryptions.RSA;

import java.io.IOException;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Client {
    private static int destPort ;
    private String hostname;
    private DatagramSocket socket;
    private DatagramPacket dpreceive, dpsend;
    private InetAddress add;
    private Scanner stdIn;
    private List<Product> listdata;
    private List<Rate> ListDataReviews;
    private static RSA rsa=new RSA();
    private static AES aes=new AES();
    public Client(int destPort, String hostname) throws IOException {
        this.destPort = destPort;
        this.hostname = hostname;
        this.add = InetAddress.getByName(hostname);
        this.socket = new DatagramSocket();
        this.stdIn = new Scanner(System.in);
    }
    public void SearchProduct(String query) throws IOException,ClassNotFoundException{
        query = "search#"+query;
        query=aes.encrypt(query);
        byte[] data = query.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public void GetReviewProduct(Integer ID) throws IOException,ClassNotFoundException{
        String IDProduct="";
        IDProduct = ID.toString();
        IDProduct = "click#"+IDProduct;
        IDProduct=aes.encrypt(IDProduct);
        byte[] data = IDProduct.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public List<Product> ReceiveList() throws IOException,ClassNotFoundException{
            dpreceive = new DatagramPacket(new byte[4096],4096);
            socket.receive(dpreceive);
            listdata=aes.decryptList(new String(dpreceive.getData(),0,dpreceive.getLength()));
            return listdata;
    }
    public List<Rate> ReceiveListReviews() throws IOException,ClassNotFoundException{
        dpreceive = new DatagramPacket(new byte[4096],4096);
        socket.receive(dpreceive);
        ListDataReviews = aes.decryptListReviews(new String(dpreceive.getData(),0,dpreceive.getLength()));
        return ListDataReviews;
    }
    public void ConnectClient() throws IOException,NoSuchAlgorithmException,InvalidKeySpecException {
            traodoikey(socket, dpsend, add);
//        while (true) {
//            System.out.print("Client input: \n");
//            String tmp = stdIn.nextLine();
//            tmp=aes.encrypt(tmp);
//            byte[] data = tmp.getBytes();
//            dpsend = new DatagramPacket(data, data.length, add, destPort);
//            socket.send(dpsend);
//            if (tmp.equals("bye")) {
//                System.out.println("Client socket closed");
//                stdIn.close();
//                socket.close();
//                break;
//            }
            //Get response from server
//            dpreceive = new DatagramPacket(new byte[4096], 4096);
//            socket.receive(dpreceive);
//            try {
//                tmp = new String(dpreceive.getData(), 0, dpreceive.getLength());
//                listdata = aes.decryptList(tmp);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            if (!listdata.isEmpty()) System.out.println(listdata.get(0).getProductName());
//            else System.out.println("fall");
//        }
    }
    private static void traodoikey(DatagramSocket socket,DatagramPacket dpsend,InetAddress add) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String tmp=""+socket.getLocalPort();
        byte[] data = tmp.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
        DatagramPacket dpreceive = new DatagramPacket(new byte[4096], 4096);
        socket.receive(dpreceive);
        rsa.keyp = dpreceive.getData();
        aes.createkey();
        tmp=rsa.mahoa(aes.getPassword()+";"+aes.getSalt());
        data = tmp.getBytes();
        dpsend = new DatagramPacket(data, data.length, add, destPort);
        socket.send(dpsend);
    }
    public static void main(String args[])throws IOException,NoSuchAlgorithmException,InvalidKeySpecException{

    }
}

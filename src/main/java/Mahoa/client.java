package Mahoa;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class recieve implements Runnable{
    public static DatagramSocket socket;
    public DatagramPacket dpreceive;
    public recieve(DatagramSocket s,DatagramPacket dpreceive){
        socket=s;
        this.dpreceive=dpreceive;
    }

    public void run(){
        try{
            while(true){
                dpreceive = new DatagramPacket(new byte[4096], 4096);
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(),0,dpreceive.getLength());
                System.out.println("Client get: " + tmp + " from server");
            }
        }catch(IOException e){}
    }
}
public class client {
    public static int destPort = 1234;
    public static String hostname = "localhost";
    public static RSA rsa=new RSA();
    public static AES aes=new AES();
    private static void traodoikey(DatagramSocket socket,DatagramPacket dpsend,InetAddress add) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
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
    public static void main(String args[]) throws UnknownHostException, SocketException, IOException, NoSuchAlgorithmException, InvalidKeySpecException{
        DatagramSocket socket;
        DatagramPacket dpsend=null, dprecieve=new DatagramPacket(new byte[4096], 4096);
        InetAddress add; Scanner stdIn;
        ExecutorService exe=Executors.newSingleThreadExecutor();
        try {
            add = InetAddress.getByName(hostname);
            socket = new DatagramSocket();
            stdIn = new Scanner(System.in);
            traodoikey(socket,dpsend,add);
            while(true){
                System.out.print("Client input: ");
                String tmp=stdIn.nextLine();
                if(tmp.equals("bye")){
                    System.out.println("Client socket closed");
                    stdIn.close();
                    socket.close();
                    break;
                }

                tmp=aes.encrypt(tmp);
                byte[] data = tmp.getBytes();
                dpsend = new DatagramPacket(data, data.length, add, destPort);
                System.out.println("Client sent "+ tmp + " to " + add.getHostAddress() + " from port " + socket.getLocalPort());
                socket.send(dpsend);
                recieve rs=new recieve(socket,dprecieve);
                exe.execute(rs);
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
}
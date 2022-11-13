package Mahoa;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
public class server{
    public static int buffsize = 4096;
    public static int port = 1234;
    public static DatagramSocket socket;
    public static DatagramPacket dpreceive, dpsend;
    static RSA rsa=new RSA();
    private static HashMap<String,AES> listip=new HashMap<>();
    public static void Sendback(String tmp){
        try {
            dpsend = new DatagramPacket(tmp.getBytes(), tmp.getBytes().length,dpreceive.getAddress(),dpreceive.getPort());
            System.out.println("Server sent back " + tmp+ " to client");
            socket.send(dpsend);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    public static void traodoikey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
        dpsend = new DatagramPacket(rsa.publickey.getEncoded(), rsa.publickey.getEncoded().length,dpreceive.getAddress(),dpreceive.getPort());
        socket.send(dpsend);
        socket.receive(dpreceive);
        AES aes=new AES();
        String key=new String(dpreceive.getData(),0,dpreceive.getLength());
        key=rsa.giaima(key);
        String[] token=key.split(";");
        aes.setPassword(token[0]);
        aes.setSalt(token[1]);
        aes.createkey();
        listip.put(dpreceive.getAddress().getHostAddress(), aes);
    }
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {

        try {
            socket = new DatagramSocket(1234);
            dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
            rsa.createkey();
            while(true){
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(), 0 , dpreceive.getLength());
                if(listip.containsKey(dpreceive.getAddress().getHostAddress())){
                    AES aes=listip.get(dpreceive.getAddress().getHostAddress());
                    tmp=aes.decrypt(tmp);
                    System.out.println("Server received: " + tmp + " from " + dpreceive.getAddress().getHostAddress() + " at port "+ socket.getLocalPort());
                    Sendback(tmp);
                }
                else {
                    traodoikey();
                }
                if(tmp.equals("bye")){
                    System.out.println(listip.get(dpreceive.getAddress().getHostAddress())+" has disconnected");
                    listip.remove(dpreceive.getAddress().getHostAddress());
                }
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }

    }
}
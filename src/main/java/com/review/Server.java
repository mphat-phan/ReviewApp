package com.review;

import com.review.Encryptions.AES;
import com.review.Encryptions.RSA;
import com.review.models.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;

public class Server {
    private int buffsize ;
    private int port;
    public static DatagramSocket socket;
    public static DatagramPacket dpreceive, dpsend;
    static RSA rsa=new RSA();
    private static HashMap<String,AES> listip=new HashMap<>();
    public Server(int port, int buffsize) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.dpreceive = new DatagramPacket(new byte[buffsize], buffsize);
    }
    public void Send(String value) throws IOException {
        dpsend = new DatagramPacket(value.getBytes(), value.getBytes().length,dpreceive.getAddress(),dpreceive.getPort());
        System.out.println("Sever send :");
        System.out.println(value);
        socket.send(dpsend);
    }
    public void SendList(List<Product> list,String ip){
        AES aes=listip.get(ip);
        try {
            Send(aes.encryptList(list));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SendDetailProduct(ProductDetail productDetail, String ip){
        AES aes=listip.get(ip);
        try {
            Send(aes.encryptProductDetail(productDetail));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SendListReviews(List<Rate> Rate, String ip){
        AES aes=listip.get(ip);
        try {
            Send(aes.encryptListReviews(Rate));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] Receive(String tmp)throws IOException{
        String[] token = tmp.split("#");
        return token;
    }
    public void CheckRequest(String tmp) throws IOException{
        String[] token = Receive(tmp);
        if(token[0].equals("search")){
            SearchProduct(token[1]);
        }
        else if(token[0].equals("ReviewProduct")){
            ReviewProduct(Integer.parseInt(token[1]));
        }
        else if (token[0].equals("clickDetail")) {
            DetailProduct(Integer.parseInt(token[1]));
        }
        else if(token[0].equals("searchSendo")){
            SearchProductSendo(token[1]);
        }
        else if(token[0].equals("searchShopee")){
            SearchProductShopee(token[1]);
        }
        else if (token[0].equals("clickDetailShopee")) {
            DetailProductShopee(Integer.parseInt(token[1]),Integer.parseInt(token[2]));
        }
        else if(token[0].equals("searchLazada")){
            SearchProductLazada(token[1]);
        }

    }
    public void SearchProduct(String query)throws IOException{
        Tiki tiki = new Tiki();
        SendList(tiki.getProductsByQuery(query),dpreceive.getAddress().getHostAddress());
    }
    public void SearchProductLazada(String query)throws IOException{
        Lazada lazada = new Lazada();
        SendList(lazada.getProductsByQueryLazada(query),dpreceive.getAddress().getHostAddress());
    }
    public void SearchProductSendo(String query)throws IOException{
        Sendo sendo = new Sendo();
        SendList(sendo.getProductsByQuerySendo(query),dpreceive.getAddress().getHostAddress());
    }
    public void SearchProductShopee(String query)throws IOException{
        Shopee shopee = new Shopee();
        SendList(shopee.getProductsByQueryShopee(query),dpreceive.getAddress().getHostAddress());
    }
    public void ReviewProduct(Integer ID)throws IOException{
        Tiki tiki = new Tiki();
        SendListReviews(tiki.getRatesByQuery(ID),dpreceive.getAddress().getHostAddress());
    }
    public void DetailProduct(Integer ID)throws IOException{
        Tiki tiki = new Tiki();
        SendDetailProduct(tiki.getDetailProduct(ID),dpreceive.getAddress().getHostAddress());
    }
    public void DetailProductShopee(Integer ID,Integer IDshop)throws IOException{
        Shopee shopee = new Shopee();
        SendDetailProduct(shopee.getDetailProduct(ID,IDshop),dpreceive.getAddress().getHostAddress());
    }
    public void ConnectSever() throws IOException,NoSuchAlgorithmException,InvalidKeySpecException {
        ShareIp();
            rsa.createkey();
            while(true){
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(), 0 , dpreceive.getLength());
                if(listip.containsKey(dpreceive.getAddress().getHostAddress())){
                    AES aes=listip.get(dpreceive.getAddress().getHostAddress());
                    tmp=aes.decrypt(tmp);
                    if(tmp.equals("bye")){
                        System.out.println(listip.get(dpreceive.getAddress().getHostAddress())+" has disconnected");
                        listip.remove(dpreceive.getAddress().getHostAddress());
                    }
                    else {
                        System.out.println("Server receive "+ tmp);
                        CheckRequest(tmp);
                    }
                }
                else {
                    traodoikey();
                }

            }
    }
    public static void ShareIp() throws IOException, NullPointerException, UnknownHostException {
        Socket s=new Socket("210.211.117.37", 80);
        String localip=s.getLocalAddress().toString().substring(1);
        String aip="https://api-generator.retool.com/ed49kC/ipserver/1";
        String jsondata="{\"Ip\": \""+localip+"\",\"id\": 1}";
        System.out.println(localip);
        Jsoup.connect(aip)
                .ignoreContentType(true).ignoreHttpErrors(true)
                .header("Content-Type","application/json")
                .requestBody(jsondata)
                .method(Connection.Method.PUT).execute();
    }
    public static void traodoikey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
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
    public static void main(String[] args) throws IOException,NoSuchAlgorithmException,InvalidKeySpecException
    {
        Server sv = new Server(1234, 512);
        while(true) {
            sv.ConnectSever();
        }
    }
}

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
    public void Send(String value,String[] s) throws IOException {
        dpsend = new DatagramPacket(value.getBytes(), value.getBytes().length, InetAddress.getByName(s[0]),Integer.parseInt(s[1]));
        System.out.println("Sever send :");
        System.out.println(value);
        socket.send(dpsend);
    }
    public void SendList(List<Product> list,String[] s){
        AES aes=listip.get(s[0]+":"+s[1]);
        try {
            Send(aes.encryptList(list),s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SendDetailProduct(ProductDetail productDetail,String[] s){
        AES aes=listip.get(s[0]+":"+s[1]);
        try {
            Send(aes.encryptProductDetail(productDetail),s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SendListReviews(List<Rate> Rate,String[] s){
        AES aes=listip.get(s[0]+":"+s[1]);
        try {
            Send(aes.encryptListReviews(Rate),s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] Receive(String tmp)throws IOException{
        String[] token = tmp.split("#");
        return token;
    }
    public void CheckRequest(String tmp,String[] s) throws IOException{
        String[] token = Receive(tmp);
        if(token[0].equals("search")){
            SearchProduct(token[1],s);
        }
        else if(token[0].equals("ReviewProduct")){
            ReviewProduct(Integer.parseInt(token[1]),s);
        }
        else if (token[0].equals("clickDetail")) {
            DetailProduct(Integer.parseInt(token[1]),s);
        }
        else if(token[0].equals("searchSendo")){
            SearchProductSendo(token[1],s);
        }
        else if(token[0].equals("clickDetailSendo")){
            DetailProductSendo(Integer.valueOf(token[1]),s);
        }
        else if(token[0].equals("ReviewProductSendo")){
            ReviewProductSendo(Integer.valueOf(token[1]),s);
        }
        else if(token[0].equals("searchShopee")){
            SearchProductShopee(token[1],s);
        }
        else if (token[0].equals("clickDetailShopee")) {
            DetailProductShopee(Integer.parseInt(token[1]),Integer.parseInt(token[2]),s);
        }
        else if (token[0].equals("ReviewProductShopee")) {
            ReviewProductShopee(token[1],token[2],s);
        }
        else if(token[0].equals("searchLazada")){
            SearchProductLazada(token[1],s);
        }
        else if(token[0].equals("clickDetailLazada"))
        {
            DetailProductLazada(Integer.parseInt(token[1]),s);
        }
        else if(token[0].equals("ReviewProductLazada"))
        {
            ReviewProductLazada(Integer.parseInt(token[1]),s);
        }

    }
    public void SearchProduct(String query,String[] s)throws IOException{
        Tiki tiki = new Tiki();
        SendList(tiki.getProductsByQuery(query),s);
    }
    public void SearchProductLazada(String query,String[] s)throws IOException{
        Lazada lazada = new Lazada();
        SendList(lazada.getProductsByQueryLazada(query),s);
    }
    public void SearchProductSendo(String query,String[] s)throws IOException{
        Sendo sendo = new Sendo();
        SendList(sendo.getProductsByQuerySendo(query),s);
    }
    public void DetailProductSendo(Integer ID,String[] s)throws IOException{
        Sendo sendo = new Sendo();
        SendDetailProduct(sendo.getDetailProductSendo(String.valueOf(ID)),s);
    }
    public void ReviewProductSendo(Integer ID,String[] s)throws IOException{
        Sendo sendo = new Sendo();
        SendListReviews(sendo.getRatesByQuerySendo(ID),s);
    }
    public void SearchProductShopee(String query,String[] s)throws IOException{
        Shopee shopee = new Shopee();
        SendList(shopee.getProductsByQueryShopee(query),s);
    }
    public void ReviewProduct(Integer ID,String[] s)throws IOException{
        Tiki tiki = new Tiki();
        SendListReviews(tiki.getRatesByQuery(ID),s);
    }

    public void DetailProduct(Integer ID,String[] s)throws IOException{
        Tiki tiki = new Tiki();
        SendDetailProduct(tiki.getDetailProduct(ID),s);
    }
    public void ReviewProductLazada(Integer ID,String[] s)throws IOException{
        Lazada lazada = new Lazada();
        SendListReviews(lazada.getRatesByQueryLazada(String.valueOf(ID)),s);
    }
    public void DetailProductLazada(Integer ID,String[] s)throws IOException{
        Lazada lazada = new Lazada();
        SendDetailProduct(lazada.getDetailProduct(String.valueOf(ID)),s);
    }
    public void DetailProductShopee(Integer ID,Integer IDshop,String[] s)throws IOException{
        Shopee shopee = new Shopee();
        SendDetailProduct(shopee.getDetailProduct(String.valueOf(ID),String.valueOf(IDshop)),s);
    }
    public void ReviewProductShopee(String ID,String IDshop,String[] s)throws IOException{
        Shopee shopee = new Shopee();
        SendListReviews(shopee.getRatesByQuery(ID,IDshop),s);
    }
    public void ConnectSever() throws IOException,NoSuchAlgorithmException,InvalidKeySpecException {
        ShareIp();
            rsa.createkey();
            while(true){
                socket.receive(dpreceive);
                String tmp = new String(dpreceive.getData(), 0 , dpreceive.getLength());
                String[] s={dpreceive.getAddress().getHostAddress(),String.valueOf(dpreceive.getPort())};
                if(listip.containsKey(s[0]+":"+s[1])){
                    AES aes=listip.get(s[0]+":"+s[1]);
                    tmp=aes.decrypt(tmp);
                    if(tmp.equals("bye")){
                        System.out.println(s[0]+":"+s[1]+" has disconnected");
                        listip.remove(s);
                    }
                    else {
                        System.out.println("Server receive "+ tmp +" from "+s[0]+":"+s[1]);
                        CheckRequest(tmp,s);
                    }
                }
                else {
                    traodoikey(s);
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
    public static void traodoikey(String[] s) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        dpsend = new DatagramPacket(rsa.publickey.getEncoded(), rsa.publickey.getEncoded().length,InetAddress.getByName(s[0]), Integer.parseInt(s[1]));
        socket.send(dpsend);
        socket.receive(dpreceive);
        AES aes=new AES();
        String key=new String(dpreceive.getData(),0,dpreceive.getLength());
        key=rsa.giaima(key);
        String[] token=key.split(";");
        aes.setPassword(token[0]);
        aes.setSalt(token[1]);
        aes.createkey();
        listip.put(s[0]+":"+s[1], aes);
    }
    public static void main(String[] args) throws IOException,NoSuchAlgorithmException,InvalidKeySpecException
    {
        Server sv = new Server(1234, 512);
        while(true) {
            sv.ConnectSever();
        }
    }
}

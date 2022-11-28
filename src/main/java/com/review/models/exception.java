package com.review.models;
import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class exception {
    private  String[] urlDefault = {"//tiki.vn/assets/img/avatar.png",""};
    private String dirPath = new java.io.File("images/an-danh.png").getAbsolutePath();
    private String dirPathNull = new java.io.File("images/null.jpg").getAbsolutePath();
    public Boolean checkUrl(String Url){
        for (int i=0;i< urlDefault.length;i++){
            if(Url.equals(urlDefault[i])){
                return true;
            }
        }
        return false;
    }
    public String getImage(JSONObject js,String s){
        String image;
        try{
            image=js.getString(s);
            if(checkUrl(image)){
                image=dirPath;
            }
        }
        catch(JSONException e){
            image=dirPath;
        }
        return image;
    }
    public List<String> getListImage(JSONObject js,String s,String a){
        List<String> listimage=new ArrayList<>();
        try{
            JSONArray jsa=js.getJSONArray(s);
                for(int i=0;i<jsa.length();i++)
                    listimage.add(jsa.getJSONObject(i).getString(a));
        }
        catch (JSONException e){
            return listimage;
        }
        return listimage;
    }
    public List<String> getListImagebyString(JSONObject js,String s,String a){
        List<String> listimage=new ArrayList<>();
        try{
            String[] n=getImagebyString(js.get(s).toString());
            for(int i=0;i<n.length;i++) {
                if(!checkUrl(n[i])) {
                    listimage.add(a + n[i]);
                }
            }
        }
        catch (JSONException e){
            return listimage;
        }
        return listimage;
    }
    public String[] getIntbyString(String s){
        return s.replaceAll("[^0-9a-zA-Z,-]","").split(",");
    }
    public String[] getImagebyString(String s){
        return s.replaceAll("[^0-9a-zA-Z,_./:-]","").split(",");
    }
}

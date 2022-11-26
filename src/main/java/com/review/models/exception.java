package com.review.models;
import org.jsoup.*;
import org.json.*;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class exception {

    public String getImage(JSONObject js,String s){
        String image;
        try{
            image=js.getString(s);
        }
        catch(JSONException e){
            image="";
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
            for(int i=0;i<n.length;i++)
                listimage.add(a+n[i]);
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
        return s.replaceAll("[^0-9a-zA-Z,_-]","").split(",");
    }
}

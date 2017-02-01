package com.example.avi.tabwithfragment;


import android.util.Log;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;


/**
 * Created by avi on 12.04.2015.
 */
public class Connections {
    String[] sD;
    String[] sF;
    Map listDir;
    String[] listSDir;
    Integer[] listImg;
    XMLRPCClient client;


    public void doIt(String server) throws Exception {
       // try {
            client = new XMLRPCClient(new URL(server));
            client.setTimeout(5); //5 sec
     //   } catch (MalformedURLException e) {
       //     e.printStackTrace();
     //   }
            Integer i = (Integer)client.call("add",10, 3);
            Log.d("Asdf", Integer.toString(i));
    }

    public void getDir(String dirName){
        try {
            listDir =(HashMap) client.call("test_map", dirName);
            Object[] oD = (Object[]) listDir.get("dirs");
            sD = Arrays.copyOf(oD, oD.length, String[].class);
            Arrays.sort(sD, String.CASE_INSENSITIVE_ORDER);

            Object[] oF = (Object[]) listDir.get("files");
            sF = Arrays.copyOf(oF, oF.length, String[].class);
            Arrays.sort(sF, String.CASE_INSENSITIVE_ORDER);

            listSDir = concatStr (sD, sF);
            listImg = imageIdGet(sD,sF);
        } catch (XMLRPCException e) {
            e.printStackTrace();
        }
    }

    public void startPlay(String filetoplay){
        try {
            client.call("startPlay", filetoplay);
        } catch (XMLRPCException e) {
            e.printStackTrace();
        }
    }

    public void seekPlay(String value){
        try {
            client.call("seekPlay", value);
        } catch (XMLRPCException e) {
            e.printStackTrace();
        }
    }

    public void pausePlay(){
        try {
            client.call("pausePlay");
        } catch (XMLRPCException e) {
            e.printStackTrace();
        }
    }

    public void swAudio(){
        try {
            client.call("swAudio");
        } catch (XMLRPCException e) {
            e.printStackTrace();
        }
    }

    public void stopPlay(){
        try {
            client.call("stopPlay");
        } catch (XMLRPCException e) {
            e.printStackTrace();
        }
    }

    public void intentPlay(String sharedText){
        try {
            client.call("intentPlay", sharedText);
        } catch (XMLRPCException e) {
            e.printStackTrace();
        }

    }

    private Integer[] imageIdGet(String[] A, String[] B) {
        // TODO Делаем массив картинок
        int aLen = A.length;
        int bLen = B.length;
        Integer[] imageId = new Integer[aLen+bLen+1];
        for (int i = 0; i < aLen+bLen+1; i++) {
            if(i < aLen+1) {imageId[i] = R.drawable.ic_folder_black_24dp;}
            else {imageId[i] = R.drawable.ic_movie_black_24dp;}
        }

        return imageId;
    }

    private String[] concatStr(String[] A, String[] B) {
        int aLen = A.length;
        int bLen = B.length;

        String[] C = new String[aLen+bLen+1];
        C[0] = "..";
        System.arraycopy(A, 0, C, 1, aLen);
        System.arraycopy(B, 0, C, aLen+1, bLen);
        return C;
    }



}

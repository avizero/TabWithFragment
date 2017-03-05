package com.example.avi.tabwithfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import de.timroes.axmlrpc.XMLRPCClient;
import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCTimeoutException;

/**
 * Created by avi on 31.01.2017.
 */
// TODO объеденить вместе с connections
public class DownloadTask extends AsyncTask<String, Void, Void>  {

    private Context mContext;

    //SharedPreferences sp;
    //Connections con;

    //public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
    //    // TODO Проверять общие настройки, ключевые параметры и изменять UI
        // или поведение приложения, если потребуется.
    //}

    public DownloadTask (Context context){
        mContext = context;
    }

    private Exception error;
    XMLRPCClient client;

    @Override
    protected void onPreExecute() {

       // super.onPreExecute();
        //get sharedPreferences here
        //SharedPreferences sharedPreferences = mContext.getSharedPreferences() <SharedPreferencesName>, <Mode>);

    }

    @Override
    protected Void doInBackground(String... commands) {
        //Connections con = new Connections();

        try {
            client = new XMLRPCClient(new URL(StatusMpv.getServerAddress()));
            client.setTimeout(5); //5 sec

        } catch (Exception e) {
            error = e;
            e.printStackTrace();
            //return null;
        }


        switch (commands[0]) {
            case "doIt":
                try {
                    //String server = sp.getString("serverAddress", "");
                    //String server = sp.getString("serverAddress", "");
                    //String server = sp.getString("server", ""); образец
                    //System.out.println(server);

                    //con.doIt(server);
                    //client = new XMLRPCClient(new URL("http://192.168.56.103:8000/rpc2"));
                    //client.setTimeout(5); //5 sec
                    //   } catch (MalformedURLException e) {
                    //     e.printStackTrace();
                    //   }
                    Integer i = (Integer)client.call("add",10, 3);
                   // Log.d("Asdf", Integer.toString(i));

                } catch (XMLRPCTimeoutException e) {
                    //  System.out.println("HA HA HA");
                    e.printStackTrace();
                    error = e;
                 //   return null;
                } catch (XMLRPCException e) {
                    error = e;
                    e.printStackTrace();
                 //   return null;
                } catch (Exception e) {
                    error = e;
                    e.printStackTrace();
                //    return null;
                }
                //return con;
            case "getDir":
                try {
                   // StatusMpv.setListDir((HashMap) client.call("test_map", commands[1]));
                    StatusMpv.setListDir((HashMap) client.call("test_map", commands[1]));
                   // Object[] oD = (Object[]) listDir.get("dirs");
                   // sD = Arrays.copyOf(oD, oD.length, String[].class);
                   // Arrays.sort(sD, String.CASE_INSENSITIVE_ORDER);

                   // Object[] oF = (Object[]) listDir.get("files");
                   // sF = Arrays.copyOf(oF, oF.length, String[].class);
                   // Arrays.sort(sF, String.CASE_INSENSITIVE_ORDER);

                  //  listSDir = concatStr (sD, sF);
                   // listImg = imageIdGet(sD,sF);
                } catch (XMLRPCException e) {
                   // e.printStackTrace();
                }
                break;
            case "startPlay":
                try {
                    client.call("startPlay",commands[1]);
                } catch (XMLRPCException e) {
                    //  e.printStackTrace();
                }
                break;
            case "seekPlay":
                //con.seekPlay(commands[1]);
                //return con;
            case "pausePlay":
                //con.pausePlay();
                //return con;
            case "swAudio":
                //con.swAudio();
                //return con;
            case "stopPlay":
                try {
                    client.call("stopPlay");
                } catch (XMLRPCException e) {
                    //  e.printStackTrace();
                }
                break;
            case "intentPlay":
                //con.intentPlay(commands[1]);
                //return con;
            case "getFilename":
                try {
                    StatusMpv.setCurrentPlayFile((String)client.call("getFilename"));
                    Log.d("Asdf", StatusMpv.getCurrentPlayFile());
                } catch (XMLRPCException e) {
                  //  e.printStackTrace();
                }
                break;

                //con.intentPlay(commands[1]);
                //return con;
        }
        //return null;
        return null;
    }

    /**
     * Uses the logging framework to display the output of the fetch
     * operation in the log fragment.
     */
    @Override
    protected void onPostExecute(Void result) {
        // xz

        //if (result!=null) {
        //      Toast.makeText(mContext, "Success!",
        //              Toast.LENGTH_SHORT).show();
        //} else {
       //     Toast.makeText(mContext, "Fuck!!", //error.getMessage(),
       //             Toast.LENGTH_SHORT).show();
       // }
    }
}
package com.example.avi.tabwithfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import de.timroes.axmlrpc.XMLRPCException;
import de.timroes.axmlrpc.XMLRPCTimeoutException;

/**
 * Created by avi on 31.01.2017.
 */
// TODO объеденить вместе с connections
public class DownloadTask extends AsyncTask<String, Void, Connections> {

    private Context mContext;

    SharedPreferences sp;
    Connections con;

    public DownloadTask (Context context){
        mContext = context;
    }

    private Exception error;

    @Override
    protected Connections doInBackground(String... commands) {
        //Connections con = new Connections();
        switch (commands[0]) {
            case "doIt":
                try {

                    String server = sp.getString("server", "");
                    // System.out.println(server);
                    con.doIt(server);
                } catch (XMLRPCTimeoutException e) {
                    //  System.out.println("Ha HA HA");
                    e.printStackTrace();
                    error = e;
                    return null;
                } catch (XMLRPCException e) {
                    error = e;
                    e.printStackTrace();
                    return null;
                } catch (Exception e) {
                    error = e;
                    e.printStackTrace();
                    return null;
                }
                return con;
            case "getDir":
                con.getDir(commands[1]);
                return con;
            case "startPlay":
                con.startPlay(commands[1]);
                return con;
            case "seekPlay":
                con.seekPlay(commands[1]);
                return con;
            case "pausePlay":
                con.pausePlay();
                return con;
            case "swAudio":
                con.swAudio();
                return con;
            case "stopPlay":
                con.stopPlay();
                return con;
            case "intentPlay":
                con.intentPlay(commands[1]);
                return con;
        }
        return null;
    }

    /**
     * Uses the logging framework to display the output of the fetch
     * operation in the log fragment.
     */
    @Override
    protected void onPostExecute(Connections result) {
        // xz

        if (result!=null) {
            //  Toast.makeText(MainActivity.this, "Success!",
            //          Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, error.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.avi.tabwithfragment;

import java.util.Map;

/**
 * Created by avi on 02.02.2017.
 */

public  class StatusMpv {
    String[] sD;
    String[] sF;
    private static Map listDir;
    private static Map ControlsMap;
    private static String currentPlayFile;
    String[] listSDir;
    Integer[] listImg;
    private static String serverAddress;

    public static Map getControlsMap() {
        return ControlsMap;
    }

    public static void setControlsMap(Map controlsMap) {
        ControlsMap = controlsMap;
    }

    public static Map getListDir() {
        return listDir;
    }

    public static void setListDir(Map listDir) {
        StatusMpv.listDir = listDir;
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static void setServerAddress(String serverAddress) {
        StatusMpv.serverAddress = serverAddress;
    }

    public static String getCurrentPlayFile() {
        return currentPlayFile;
    }

    public static void setCurrentPlayFile(String currentPlayFile) {
        StatusMpv.currentPlayFile = currentPlayFile;
    }
}

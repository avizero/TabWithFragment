package com.example.avi.tabwithfragment;

import java.util.Map;

/**
 * Created by avi on 02.02.2017.
 */

public class StatusMpv {
    String[] sD;
    String[] sF;
    Map listDir;
    String currentFile;
    String[] listSDir;
    Integer[] listImg;

    public Map getListDir() {
        return listDir;
    }

    public void setListDir(Map listDir) {
        this.listDir = listDir;
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }
}

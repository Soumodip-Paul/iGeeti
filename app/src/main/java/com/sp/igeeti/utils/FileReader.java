package com.sp.igeeti.utils;

import java.io.File;
import java.util.ArrayList;

public class FileReader {
    private FileReader(){}
    public static ArrayList<File> fetch(File file){
        ArrayList<File> songs = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null){
            for (File songFile :
                    files) {
                if(!songFile.isHidden() && songFile.isDirectory()){
                    songs.addAll(fetch(songFile));
                }
                else {
                    if(songFile.getName().endsWith(".mp3") && !songFile.getName().startsWith("."))
                        songs.add(songFile);
                }
            }
        }
        return songs;
    }
    public static void getFileList(File file, ArrayList<String> songList, ArrayList<String> songNames){
            ArrayList<File> songFiles = fetch(file);
        for (int i = 0; i < songFiles.size(); i++) {
            songList.add(songFiles.get(i).toString());
            songNames.add(songFiles.get(i).getName().replace(".mp3", ""));
        }
    }
}

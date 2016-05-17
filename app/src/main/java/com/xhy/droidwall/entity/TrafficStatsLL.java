package com.xhy.droidwall.entity;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by change100 on 2016/5/16.
 */
public class TrafficStatsLL {

    public static String readInStream(FileInputStream inStream){

        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while((length = inStream.read(buffer)) != -1){
                outStream.write(buffer,0,length);
            }
            outStream.close();
            inStream.close();
            return outStream.toString();

        } catch (IOException e){
            Log.i("FileTest",e.getMessage());
        }
        return null;
    }

    public static long getMobileRxBytes() {
        long ReturnLong = 0;
        try{
            File file = new File("/proc/net/dev");
            FileInputStream inStream = new FileInputStream(file);
            String a = readInStream(inStream);
            int startPos = a.indexOf("rmnet:0");
            a = a.substring(startPos);
            Pattern p = Pattern.compile(" \\d+ ");
            Matcher m = p.matcher(a);
            while(m.find()){
                ReturnLong = Long.parseLong(m.group().trim());
                break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ReturnLong;
    }

    public static long getMobileTxBytes(){

        long ReturnLong = 0;
        try{
            int count = 0;
            File file = new File("/proc/net/dev");
            FileInputStream inStream = new FileInputStream(file);
            String a = readInStream(inStream);
            int startPos = a.indexOf("rmnet0:");
            a = a.substring(startPos);
            Pattern p = Pattern.compile(" \\d+ ");
            Matcher m = p.matcher(a);
            while(m.find()){
                if(count == 8){
                    ReturnLong = Long.parseLong(m.group().trim());
                    break;
                }
                count++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return ReturnLong;
    }

    public static long getWifiRxBytes() {
        long ReturnLong = 0;
        try{
            File file = new File("/proc/net/dev");
            FileInputStream inStream = new FileInputStream(file);
            String a = readInStream(inStream);
            int startPos = a.indexOf("wlan0:");
            a = a.substring(startPos);
            Pattern p = Pattern.compile(" \\d+ ");
            Matcher m = p.matcher(a);
            while(m.find()){
                ReturnLong = Long.parseLong(m.group().trim());
                break;
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return ReturnLong;
    }

    public static long getWifiTxBytes(){

        long ReturnLong  = 0;
        try{
            int count = 0;
            File file = new File("/proc/net/dev");
            FileInputStream inStream = new FileInputStream(file);
            String a = readInStream(inStream);
            int startPos = a.indexOf("wlan0:");
            a = a.substring(startPos);
            Pattern p = Pattern.compile(" \\d+ ");
            Matcher m = p.matcher(a);
            while (m.find()){
                if(count == 8){
                    ReturnLong = Long.parseLong(m.group().trim());
                    break;
                }
                count++;
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return ReturnLong;
    }

    public static long getUidRxBytes(int uid){
        long ReturnLong = 0;

        try {
            String url = "/proc/uid_stat/"+String.valueOf(uid)+"/tcp_rcv";
            File file = new File(url);
            FileInputStream inStream;
            if(file.exists()){

                inStream = new FileInputStream(file);
                ReturnLong = Long.parseLong(readInStream(inStream).trim());

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return ReturnLong;
    }

    public static long getUidTxBytes(int uid) {

        long ReturnLong = 0;
        try{
            String url = "/proc/uid_stat/"+String.valueOf(uid)+"/tcp_snd";
            File file = new File(url);
            if(file.exists()){
                FileInputStream inStream = new FileInputStream(file);
                ReturnLong = Long.parseLong(readInStream(inStream).trim());

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ReturnLong;
    }
}

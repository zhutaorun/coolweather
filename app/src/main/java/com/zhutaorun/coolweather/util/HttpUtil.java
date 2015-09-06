package com.zhutaorun.coolweather.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.crypto.Mac;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import javax.crypto.spec.SecretKeySpec;
/**
 * Created by zhutaorun on 15/9/1.
 */
public class HttpUtil {

    public static String KEY = "81d003_SmartWeatherAPI_312856d";
    public static String API_HOST = "http://open.weather.com.cn/data/?areaid=xxxxxxxxxx&type=xxxxxxxx&date=xxxxxxxxx&appid=xxxxxxx";
    //"http://open.weather.com.cn/data/?areaid=".$areaid."&type=".$type."&date=".$date."&appid=".$appid_six."&key=".urlencode($key)











    public class javademo {

        private static final char last2byte = (char) Integer.parseInt("00000011", 2);
        private static final char last4byte = (char) Integer.parseInt("00001111", 2);
        private static final char last6byte = (char) Integer.parseInt("00111111", 2);
        private static final char lead6byte = (char) Integer.parseInt("11111100", 2);
        private static final char lead4byte = (char) Integer.parseInt("11110000", 2);
        private static final char lead2byte = (char) Integer.parseInt("11000000", 2);
        private static final char[] encodeTable = new char[] { 'A', 'B', 'C', 'D',
                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
                'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
                '4', '5', '6', '7', '8', '9', '+', '/'
        };

        public static String standardURLEncoder(String data, String key) {
            byte[] byteHMAC = null;
            String urlEncoder = "";
            try {
                Mac mac = Mac.getInstance("HmacSHA1");
                SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
                mac.init(spec);
                byteHMAC = mac.doFinal(data.getBytes());
                if (byteHMAC != null) {
                    String oauth = encode(byteHMAC);
                    if (oauth != null) {
                        urlEncoder = URLEncoder.encode(oauth, "utf8");
                    }
                }
            } catch (InvalidKeyException e1) {
                e1.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return urlEncoder;
        }

        public static String encode(byte[] from) {
            StringBuffer to = new StringBuffer((int) (from.length * 1.34) + 3);
            int num = 0;
            char currentByte = 0;
            for (int i = 0; i < from.length; i++) {
                num = num % 8;
                while (num < 8) {
                    switch (num) {
                        case 0:
                            currentByte = (char) (from[i] & lead6byte);
                            currentByte = (char) (currentByte >>> 2);
                            break;
                        case 2:
                            currentByte = (char) (from[i] & last6byte);
                            break;
                        case 4:
                            currentByte = (char) (from[i] & last4byte);
                            currentByte = (char) (currentByte << 2);
                            if ((i + 1) < from.length) {
                                currentByte |= (from[i + 1] & lead2byte) >>> 6;
                            }
                            break;
                        case 6:
                            currentByte = (char) (from[i] & last2byte);
                            currentByte = (char) (currentByte << 4);
                            if ((i + 1) < from.length) {
                                currentByte |= (from[i + 1] & lead4byte) >>> 4;
                            }
                            break;
                    }
                    to.append(encodeTable[currentByte]);
                    num += 6;
                }
            }
            if (to.length() % 4 != 0) {
                for (int i = 4 - to.length() % 4; i > 0; i--) {
                    to.append("=");
                }
            }
            return to.toString();
        }


        public static void main(String[] args) {
            try {

                //需要加密的数据
                String data = "http://open.weather.com.cn/data/?areaid=xxxxxxxxxx&type=xxxxxxxx&date=xxxxxxxxx&appid=xxxxxxx";
                //密钥
                String key = "xxxxx_SmartWeatherAPI_xxxxxxx";

                String str = standardURLEncoder(data, key);

                System.out.println(str);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;
//                try{
//                    URL url = new URL(address);
//                    connection = (HttpURLConnection)url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(8000);
//                    connection.setReadTimeout(8000);
//
//                    String c = address.substring(address.length() -1,address.length());
//                    String l = "l";
//                    Log.d("TAG",c);
//                    if(c != l){
//                        connection.setRequestProperty("apikey","d1e19200ccd340362c47275a58a317e1");
//                        connection.connect();
//                        InputStream in = connection.getInputStream();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
//                        StringBuilder response = new StringBuilder();
//                        String line;
//                        while ((line = reader.readLine()) != null){
//                            response.append(line);
//                            response.append("\r\n");
//                        }
//                        if(listener != null){
//                            //回调onFinish()方法
//                            listener.onFinish(response.toString());
//                        }
//                    }
//                    else  if(c==l){
//                        InputStream in = connection.getInputStream();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                        StringBuilder response = new StringBuilder();
//                        String line;
//                        while ((line = reader.readLine()) != null){
//                            response.append(line);
//                        }
//                        if(listener != null){
//                            //回调onFinish()方法
//                            listener.onFinish(response.toString());
//                        }
//                    }
//
//                }
//                catch (Exception e){
//                    if (listener !=null){
//                        // 回调onError() 方法
//                        listener.onError(e);
//                    }
//                }finally {
//                    if(connection != null){
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
//    }

}

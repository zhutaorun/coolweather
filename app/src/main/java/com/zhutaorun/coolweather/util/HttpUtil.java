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

    public static void sendHttpRequest(final String address,
                                       final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    String c = address.substring(address.length() -1,address.length());
                    String l = "l";
                    Log.d("TAG",c);
                    if(c != l){
                        connection.setRequestProperty("apikey","d1e19200ccd340362c47275a58a317e1");
                        connection.connect();
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null){
                            response.append(line);
                            response.append("\r\n");
                        }
                        reader.close();
                        if(listener != null){
                            //回调onFinish()方法
                            listener.onFinish(response.toString());
                        }
                    }
                    else  if(c==l){
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null){
                            response.append(line);
                        }
                        if(listener != null){
                            //回调onFinish()方法
                            listener.onFinish(response.toString());
                        }
                    }

                }
                catch (Exception e){
                    if (listener !=null){
                        // 回调onError() 方法
                        listener.onError(e);
                    }
                }
                finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}

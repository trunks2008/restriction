package com.cn.hydra.aspectdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CyclicBarrier;

/**
 * @program: micro-mall
 * @author: Hydra
 * @create: 2020-10-31 07:53
 **/
public class HttpSendTest {
    public static void main(String[] args) {
        CyclicBarrier barrier=new CyclicBarrier(200);
        Thread[] threads=new Thread[100];
        for (int i = 0; i <100 ; i++) {
            threads[i]=new Thread(()->{
                try {
                    sendGet("http://127.0.0.1:8088/api/test2","name=hydra","hydra");
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }

        Thread[] threads2=new Thread[100];
        for (int i = 0; i <100 ; i++) {
            threads2[i]=new Thread(()->{
                try {
                    sendGet("http://127.0.0.1:8088/api/test2","name=trunks","trunks");
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads2[i].start();
        }
    }

    public static String sendGet(String url, String param, String token) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("token", token);
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result);
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

}

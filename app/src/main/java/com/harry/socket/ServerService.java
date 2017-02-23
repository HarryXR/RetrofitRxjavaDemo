/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/2/22.
 */
public class ServerService extends Service {

    public String[] mDefMessages = {"你好啊", "你发的什么东西啊", "不要再骚扰我了", "哪里跑", "俺老孙来也"};

    private boolean mIsServiceDestroyed = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new ServiceThread()).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class ServiceThread implements Runnable {

        @Override
        public void run() {

            try {
                ServerSocket ss = new ServerSocket(3000); ////创建一个ServerSocket对象
                while (!mIsServiceDestroyed) {
                    final Socket client = ss.accept(); //调用ServerSocket的accept()方法，接受客户端所发送的请求
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void responseClient(Socket client) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //发送消息给客户端
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        out.println("欢迎来到聊天室");

        while (!mIsServiceDestroyed) {
            //读取客户端消息
            String str = in.readLine();
            System.out.println("msg from client:" + str);
            if (str == null) {
                break;//客户端断开
            }
            int i = new Random().nextInt(mDefMessages.length);
            String msg = mDefMessages[i];
            out.println(msg);
            System.out.println("send msg to client:" + msg);
        }
        System.out.println("client quit...");
        out.close();
        in.close();
        client.close();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }
}

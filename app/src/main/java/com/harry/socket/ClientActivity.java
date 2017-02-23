/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.harry.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/2/22.
 */
public class ClientActivity extends Activity {

    @BindView(R.id.edit_content)
    TextView mEdit;
    @BindView(R.id.btn_send)
    TextView mSend;
    @BindView(R.id.tv_content)
    TextView mTvContent;

    Socket mClientSocket = null;
    PrintWriter mPrintWriter;

    String edit;
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x11) {
                //显示接受的服务端信息
                mTvContent.append(msg.obj + "\n");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, ServerService.class);
        startService(intent);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });
        // 连接服务器
        new Thread() {
            @Override
            public void run() {
                connectServer();
            }
        }.start();
    }

    private void connectServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 3000);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                    true);
                myHandler.obtainMessage(0x11, "client:connect server success").sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
                myHandler.obtainMessage(0x11, "client:connect server success").sendToTarget();
            }
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!ClientActivity.this.isFinishing()) {
                String msg = br.readLine();
                System.out.println("receive:" + msg);
                if (msg != null) {
                    String time = formateTime(System.currentTimeMillis());
                    String showMsg = "server " + time + ":" + msg + "\n";
                    myHandler.obtainMessage(0x11, showMsg).sendToTarget();
                }
            }
            System.out.print("quit...");
            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formateTime(long l) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(l));
    }

    private void sendMsg() {
        edit = mEdit.getText().toString();

        if (!TextUtils.isEmpty(edit) && mPrintWriter != null) {
            try {
                new Thread() {
                    @Override
                    public void run() {
                        mPrintWriter.println(edit);
                    }
                }.start();

                String time = formateTime(System.currentTimeMillis());
                mTvContent.append("client:" + time + ":" + edit + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        try {
            mClientSocket.shutdownInput();
            mClientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}

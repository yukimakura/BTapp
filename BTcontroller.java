package com.example.yukimakura.btremoteapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by yukimakura on 2017/03/09.
 */

public class BTcontroller extends Activity {
    BluetoothCommunicatorListener BLC;
    Intent beforeintent;


    float x=0,y=0,z=0;
    Handler joyhandler;
    Runnable r,joyr;

   // char Up='0',Left='0',Down='0',Right='0',Motor='0',Arm_up='0',Arm_down='0',Push_up='0',Push_down='0';
   private Thread thread = null;
    private final Handler handler = new Handler();
    private volatile boolean stopRun = false;

    private char send_data = 0b00000000;
    //最下位ビット（右）の順から右前進ビット、右後退ビット、左前進ビット、左後退ビット



    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        GridLayout glayout = new GridLayout(this);

        setContentView(glayout);

        //遷移元のインテントを格納
        beforeintent = getIntent();
        String BTname = beforeintent.getStringExtra("btname");
        TextView BTnameview = (TextView) findViewById(R.id.infotext);
        final TextView status_num_bin = (TextView)findViewById(R.id.status);

        final BluetoothCommunicator BTcom = new BluetoothCommunicator(this, BLC, BTname);


        BTcom.getBtname(BTname);
        BTnameview.setText(BTname);

        final Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                    try {
                       // ここに繰り返し処理を書く
                       BTcom.writeMessage(send_data);

                       Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        });
        t.start();



    }


    @Override
    public void onResume(){
        super.onResume();

    }


}

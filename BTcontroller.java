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
        setContentView(R.layout.controllerlayout);
        //遷移元のインテントを格納
        beforeintent = getIntent();
        String BTname = beforeintent.getStringExtra("btname");
        TextView BTnameview = (TextView) findViewById(R.id.infotext);

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


        //右後退
        Button right_BK = (Button)findViewById(R.id.right_back);
        right_BK.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    send_data = (char)(send_data | 0b00000010);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    send_data = (char)(send_data & 0b11111101);
                }
                return false;
            }
        });

        //右前進
        Button right_AD = (Button)findViewById(R.id.right_advancing);
        right_AD.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    send_data = (char)(send_data | 0b00000001);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    send_data = (char)(send_data & 0b11111110);
                }
                return false;
            }
        });

        //左後退
        Button left_BK = (Button)findViewById(R.id.left_back);
        left_BK.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    send_data = (char)(send_data | 0b00001000);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    send_data = (char)(send_data & 0b11110111);
                }
                return false;
            }
        });

        //左前進
        Button left_AD = (Button)findViewById(R.id.left_advancing);
        left_AD.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    send_data = (char)(send_data | 0b00000100);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    send_data = (char)(send_data & 0b11111011);
                }
                return false;
            }
        });

    }


    @Override
    public void onResume(){
        super.onResume();

    }


}

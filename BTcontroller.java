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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yukimakura on 2017/03/09.
 */

public class BTcontroller extends Activity {
    BluetoothCommunicatorListener BLC;
    Intent beforeintent;

    Handler handler;


    float x=0,y=0,z=0;
    Handler joyhandler;
    Runnable r,joyr;

   // char Up='0',Left='0',Down='0',Right='0',Motor='0',Arm_up='0',Arm_down='0',Push_up='0',Push_down='0';
   private Thread thread = null;

    private volatile boolean stopRun = false;

    private char send_data[] = {0x00,0x00};
    //最下位ビット（右）の順から右前進ビット、右後退ビット、左前進ビット、左後退ビット



    Timer timer;
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.controllerlayout);
        //遷移元のインテントを格納
        beforeintent = getIntent();
        String BTname = beforeintent.getStringExtra("btname");
        TextView BTnameview = (TextView) findViewById(R.id.infotext);
        final TextView status_num_bin = (TextView)findViewById(R.id.status);

        final BluetoothCommunicator BTcom = new BluetoothCommunicator(this, BLC, BTname);


        BTcom.getBtname(BTname);
        BTnameview.setText(BTname);

        handler = new Handler();

        final Runnable r = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                // ここに繰り返し処理を書く
                BTcom.writeMessage(send_data[0]);
                BTcom.writeMessage(send_data[1]);


                Log.e("thread","call");

                handler.postDelayed(this,100);
            }
        };
        handler.post(r);



        //右後退
        Button right_BK = (Button)findViewById(R.id.right_back);
        right_BK.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                char data[] = {0b00000011,0b01000000};
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    send_data[0] = (char)(send_data[0] & 0x7F);
                    send_data[1] = (char)(send_data[1] & 0x7F);
                    send_data[0] = (char)(send_data[0] | data[0]);
                    send_data[1] = (char)(send_data[1] | data[1]);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    send_data[0] = (char)(send_data[0] & ~data[0]);
                    send_data[0] = (char)(send_data[0]| 0x80);
                    send_data[1] = (char)(send_data[1] & ~data[1]);
                    send_data[1] = (char)(send_data[1]| 0x80);
                }
                String s1 = String.format("%8s", Integer.toBinaryString(send_data[0])).replaceAll(" ", "0");
                String s2 = String.format("%8s", Integer.toBinaryString(send_data[1])).replaceAll(" ", "0");
                status_num_bin.setText("[0]="+s1+"[1]="+s2);


                return false;
            }
        });

        //右前進
        Button right_AD = (Button)findViewById(R.id.right_advancing);
        right_AD.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                char data[] = {0b00000000,0b01001100};
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    send_data[0] = (char)(send_data[0] & 0x7F);
                    send_data[1] = (char)(send_data[1] & 0x7F);
                    send_data[0] = (char)(send_data[0] | data[0]);
                    send_data[1] = (char)(send_data[1] | data[1]);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    send_data[0] = (char)(send_data[0] & ~data[0]);
                    send_data[0] = (char)(send_data[0]| 0x80);
                    send_data[1] = (char)(send_data[1] & ~data[1]);
                    send_data[1] = (char)(send_data[1]| 0x80);
                }
                String s1 = String.format("%8s", Integer.toBinaryString(send_data[0])).replaceAll(" ", "0");
                String s2 = String.format("%8s", Integer.toBinaryString(send_data[1])).replaceAll(" ", "0");
                status_num_bin.setText("[0]="+s1+"[1]="+s2);

                return false;
            }
        });

        //左後退
        Button left_BK = (Button)findViewById(R.id.left_back);
        left_BK.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                char data[] = {0b00110000,0b01011000};
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    send_data[0] = (char)(send_data[0] & 0x7F);
                    send_data[1] = (char)(send_data[1] & 0x7F);
                    send_data[0] = (char)(send_data[0] | data[0]);
                    send_data[1] = (char)(send_data[1] | data[1]);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    send_data[0] = (char)(send_data[0] & ~data[0]);
                    send_data[0] = (char)(send_data[0]| 0x80);
                    send_data[1] = (char)(send_data[1] & ~data[1]);
                    send_data[1] = (char)(send_data[1]| 0x80);
                }
                String s1 = String.format("%8s", Integer.toBinaryString(send_data[0])).replaceAll(" ", "0");
                String s2 = String.format("%8s", Integer.toBinaryString(send_data[1])).replaceAll(" ", "0");
                status_num_bin.setText("[0]="+s1+"[1]="+s2);

                return false;
            }
        });

        //左前進
        Button left_AD = (Button)findViewById(R.id.left_advancing);
        left_AD.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                char data[] = {0b00001111,0b01000110};
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    send_data[0] = (char)(send_data[0] & 0x7F);
                    send_data[1] = (char)(send_data[1] & 0x7F);
                    send_data[0] = (char)(send_data[0] | data[0]);
                    send_data[1] = (char)(send_data[1] | data[1]);

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    send_data[0] = (char)(send_data[0] & ~data[0]);
                    send_data[0] = (char)(send_data[0]| 0x80);
                    send_data[1] = (char)(send_data[1] & ~data[1]);
                    send_data[1] = (char)(send_data[1]| 0x80);
                }
                String s1 = String.format("%8s", Integer.toBinaryString(send_data[0])).replaceAll(" ", "0");
                String s2 = String.format("%8s", Integer.toBinaryString(send_data[1])).replaceAll(" ", "0");
                status_num_bin.setText("[0]="+s1+"[1]="+s2);

                return false;
            }
        });

    }


    @Override
    public void onPause(){
        super.onPause();

        handler.removeCallbacksAndMessages(null);
    }


}

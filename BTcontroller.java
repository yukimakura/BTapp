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



    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.controllerlayout);
        //遷移元のインテントを格納
        beforeintent = getIntent();
        String BTname = beforeintent.getStringExtra("btname");
        TextView BTnameview = (TextView)findViewById(R.id.infotext);

        final BluetoothCommunicator BTcom = new BluetoothCommunicator(this,BLC,BTname);





        BTcom.getBtname(BTname);
        BTnameview.setText(BTname);


        Button up = (Button)findViewById(R.id.up);
        Button left = (Button)findViewById(R.id.left);
        Button back = (Button)findViewById(R.id.back);
        Button right = (Button)findViewById(R.id.right);
        Button arm_up = (Button)findViewById(R.id.arm_up);
        Button arm_down = (Button)findViewById(R.id.arm_down);
        Button UPFire = (Button)findViewById(R.id.UPFire);
        Button r_senkai = (Button)findViewById(R.id.r_senkai);
        Button l_senkai = (Button)findViewById(R.id.l_senkai);

//        handler = new Handler();
//        r = new Runnable() {
//            //int count = 0;
//            @Override
//            public void run() {
//                // UIスレッド
////                count++;
////                if (count > 5) { // 5回実行したら終了
////                    return;
////                }
//
//
////                BTcom.writeMessage("u"+String.valueOf(Up)+"d"+String.valueOf(Down)+
////                        "r"+String.valueOf(Right)+"l"+String.valueOf(Left)+"m"+String.valueOf(Motor)+
////                        "a"+String.valueOf(Arm_up)+"b"+String.valueOf(Arm_down)
////                        +"x"+String.valueOf(Push_up)+"v"+String.valueOf(Push_down));
//
////                BTcom.writeMessage("u"+Up+"d"+Down+
////                        "r"+Right+"l"+Left+"m"+Motor+
////                        "a"+Arm_up+"b"+Arm_down
////                        +"x"+Push_up+"v"+Push_down);
////                BTcom.writeMessage("u");
////                BTcom.writeMessage(String.valueOf(Up));
////                BTcom.writeMessage("d");
////                BTcom.writeMessage(String.valueOf(Down));
////                BTcom.writeMessage("r");
////                BTcom.writeMessage(String.valueOf(Right));
////                BTcom.writeMessage("l");
////                BTcom.writeMessage(String.valueOf(Left));
////                BTcom.writeMessage("m");
////                BTcom.writeMessage(String.valueOf(Motor));
////                BTcom.writeMessage("a");
////                BTcom.writeMessage(String.valueOf(Arm_up));
////                BTcom.writeMessage("b");
////                BTcom.writeMessage(String.valueOf(Arm_down));
////                BTcom.writeMessage("x");
////                BTcom.writeMessage(String.valueOf(Push_up));
////                BTcom.writeMessage("v");
////                BTcom.writeMessage(String.valueOf(Push_down));
//
////                BTcom.writeMessage(String.valueOf(num_comm_converter(x)));
////                BTcom.writeMessage("y");
////                BTcom.writeMessage(String.valueOf(num_comm_converter(y)));
////                BTcom.writeMessage("z");
////                BTcom.writeMessage(String.valueOf(num_comm_converter(z)));
//
//                handler.postDelayed(this, 300);
//            }
//        };
//
//        handler.post(r);



        //シリアル通信のためマイコン側はasciiコードに対応させる


//
//        BTcom.writeMessage("x");
//        BTcom.writeMessage(String.valueOf(num_comm_converter(x)));
//        BTcom.writeMessage("y");
//        BTcom.writeMessage(String.valueOf(num_comm_converter(y)));
//        BTcom.writeMessage("z");
//        BTcom.writeMessage(String.valueOf(num_comm_converter(z)));





        //前進ボタンの処理
        up.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    BTcom.writeMessage("a");

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    BTcom.writeMessage("b");
                }
                return false;
            }
        });

        //左ボタンの処理
        left.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    BTcom.writeMessage("c");

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    BTcom.writeMessage("d");
                }
                return false;
            }
        });

        //後退ボタンの処理
        back.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    BTcom.writeMessage("e");

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    BTcom.writeMessage("f");

                }
                return false;
            }
        });

        //右ボタンの処理
        right.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    BTcom.writeMessage("g");

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    BTcom.writeMessage("h");

                }
                return false;
            }
        });

        //腕上昇ボタンの処理
        arm_up.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    BTcom.writeMessage("i");

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    BTcom.writeMessage("j");

                }
                return false;
            }
        });

        //腕下降ボタンの処理
        arm_down.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    BTcom.writeMessage("k");

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    BTcom.writeMessage("l");

                }
                return false;
            }
        });

        //Fireボタン
        UPFire.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    BTcom.writeMessage("o");

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    BTcom.writeMessage("p");

                }
                return false;
            }
        });

        //左旋回ボタン
        l_senkai.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    BTcom.writeMessage("q");

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    BTcom.writeMessage("r");

                }
                return false;
            }
        });

        //右旋回ボタン
        r_senkai.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //ここに指がタッチしたときの処理を記述
                    BTcom.writeMessage("s");

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //ここに指を離したときの処理
                    BTcom.writeMessage("t");

                }
                return false;
            }
        });








    }
    private char num_comm_converter(float num){
        char buff_num;
        buff_num = (char)Math.floor((double)num*10);
        return  (char)(buff_num + 'A');
    }

//  //  private void run(){
//        Log.e("message",String.valueOf(num_comm_converter(x)));
//    }

    @Override
    public void onResume(){
        super.onResume();


    }

    public char change_char(float num){
        int buff;
        buff = (int)(num*10);
        return (char)(num+'A');
    }


    @Override
    public boolean onGenericMotionEvent(MotionEvent event){
//        String BTname = beforeintent.getStringExtra("btname");
//        final BluetoothCommunicator BTcom = new BluetoothCommunicator(this,BLC,BTname);
       boolean handled = false; // 処理したらtrueに

        // 左ジョイスティックの値をログ出力してみる
//        Arm_up = change_char(event.getAxisValue(MotionEvent.AXIS_X));
//
//        Arm_down = change_char(event.getAxisValue(MotionEvent.AXIS_RZ));

       // BTcom.writeMessage(String.valueOf(num_comm_converter(x)));

        Log.e("messagejoystick",String.valueOf(num_comm_converter(x)));

//        String msg = "(armup,armdown)=" + Arm_up + "," + Arm_down;
       // Log.e("GamePad", msg);

        return handled || super.onGenericMotionEvent(event);
    }






}

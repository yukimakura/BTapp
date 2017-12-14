package com.example.yukimakura.btremoteapp;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Handler;

import static android.R.id.list;

public class MainActivity extends Activity  {
    Button b1,b2,b3,b4,helpbtn;
    private BluetoothAdapter BA;
    public Set<BluetoothDevice> pairedDevices;
    Intent intent;
    static final int RESULT_SUBACTIVITY = 1000;
    Handler handler;

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);

        helpbtn=(Button)findViewById(R.id.helpbutton);

        BA = BluetoothAdapter.getDefaultAdapter();
        lv = (ListView)findViewById(R.id.listView);


    }

    public void on(View v){
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void off(View v){
        BA.disable();
        Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
    }


    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }


    public void list(View v){
        pairedDevices = BA.getBondedDevices();

        ArrayList<String> list = new ArrayList<String>();

        for(BluetoothDevice bt : pairedDevices) list.add(bt.getName());
        Toast.makeText(getApplicationContext(), "Showing Paired Devices",Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new  ArrayAdapter(this,android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);

    }
    @Override
    public void onResume(){
        super.onResume();
        final BluetoothDevice Btdevice = null;


        //ヘルプ用のインテント
        final Intent helpintent = new Intent(MainActivity.this,help.class);

        helpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(helpintent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent = new Intent(MainActivity.this,BTcontroller.class);
                //遷移先のインテントに選択されたリスト番号を送る。

                //intent.putExtra("btinfo", (Serializable) pairedDevices);
                intent.putExtra("btname",(String)lv.getItemAtPosition(position));
                //遷移！
                startActivity( intent );
                String msg = position + "番目のアイテムがクリックされました" + lv.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),msg , Toast.LENGTH_LONG).show();
            }

        });

    }


}
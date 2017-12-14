package com.example.yukimakura.btremoteapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by yukimakura on 2017/03/15.
 */

public class help extends Activity {
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.help);

        TextView textView = new TextView(this);

        textView.setText("このアプリの使い方についてのヘルプページです。 ¥n1.bluetoothをオンにする。¥n2.「更新」ボタンをタップする。¥nすると、 今まででつなげたことのあるデバイス一覧が現れる。¥n3.接続したいデバイスをタップする。¥n4.あとはお好きにどうぞ¥nなお、端末のバックボタンにてトップページに戻れます。¥n再度、別のデバイスと接続する場合には再度「更新」をタップしてから一覧をタップしてください。¥n2017-3-15¥nCreated by Yukimakura");
    }
}

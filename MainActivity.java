package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    public class MainActivity extends AppCompatActivity {
        /*    String hello[] = {"식사는 하셨어요?", "별 일 없으시죠?", "안녕히 주무셨어요?"};
            Random r = new Random();
            int x = r.nextInt(2);*/
        String pnum = "010-9657-8816";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button btnDial = (Button) findViewById(R.id.btnDial);
            Button btnSms = (Button) findViewById(R.id.btnSms);
            Button btnPhoto = (Button) findViewById(R.id.btnPhoto);
            Button btnVideo = (Button) findViewById(R.id.btnVideo);
            Button btnComm = (Button) findViewById(R.id.btnComm);


            btnDial.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Uri uri = Uri.parse("tel:"+ Uri.encode(pnum));
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(intent);
                }
            });

            btnSms.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.putExtra("sms_body", "부모님에게 안부 문자를 드려봐요! 호통해요!");
                    intent.setData(Uri.parse("smsto:" + Uri.encode(pnum)));
                    startActivity(intent);
                }
            });

            btnPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Photo.class);
                    startActivity(intent);
                }
            });

            btnComm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Communication.class);
                    startActivity(intent);
                }
            });
        }
    }
// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.seccon2015.rock_paper_scissors;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends Activity
    implements android.view.View.OnClickListener
{

    Button P;
    Button S;
    int cnt;
    int flag;
    private final Handler handler = new Handler();
    int m;
    int n;
    Button r;
    private final Runnable showMessageTask = new Runnable() {

        final MainActivity this$0;

        public void run()
        {
            TextView textview = (TextView)findViewById(0x7f0c0052);
            if (n - m == 1)
            {
                MainActivity mainactivity = MainActivity.this;
                mainactivity.cnt = mainactivity.cnt + 1;
                textview.setText((new StringBuilder()).append("WIN! +").append(String.valueOf(cnt)).toString());
            } else
            if (m - n == 1)
            {
                cnt = 0;
                textview.setText("LOSE +0");
            } else
            if (m == n)
            {
                textview.setText((new StringBuilder()).append("DRAW +").append(String.valueOf(cnt)).toString());
            } else
            if (m < n)
            {
                cnt = 0;
                textview.setText("LOSE +0");
            } else
            {
                MainActivity mainactivity1 = MainActivity.this;
                mainactivity1.cnt = mainactivity1.cnt + 1;
                textview.setText((new StringBuilder()).append("WIN! +").append(String.valueOf(cnt)).toString());
            }
            if (1000 == cnt)
            {
                textview.setText((new StringBuilder()).append("SECCON{").append(String.valueOf((cnt + calc()) * 107)).append("}").toString());
            }
            flag = 0;
        }

            
            {
                this$0 = MainActivity.this;
                super();
            }
    };

    public MainActivity()
    {
        cnt = 0;
    }

    public native int calc();

    public void onClick(View view)
    {
        if (flag == 1)
        {
            return;
        }
        flag = 1;
        ((TextView)findViewById(0x7f0c0052)).setText("");
        TextView textview = (TextView)findViewById(0x7f0c0050);
        TextView textview1 = (TextView)findViewById(0x7f0c0051);
        m = 0;
        n = (new Random()).nextInt(3);
        int i = n;
        textview1.setText((new String[] {
            "CPU: Paper", "CPU: Rock", "CPU: Scissors"
        })[i]);
        if (view == P)
        {
            textview.setText("YOU: Paper");
            m = 0;
        }
        if (view == r)
        {
            textview.setText("YOU: Rock");
            m = 1;
        }
        if (view == S)
        {
            textview.setText("YOU: Scissors");
            m = 2;
        }
        handler.postDelayed(showMessageTask, 1000L);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f040018);
        P = (Button)findViewById(0x7f0c004d);
        S = (Button)findViewById(0x7f0c004f);
        r = (Button)findViewById(0x7f0c004e);
        P.setOnClickListener(this);
        r.setOnClickListener(this);
        S.setOnClickListener(this);
        flag = 0;
    }

    static 
    {
        System.loadLibrary("calc");
    }
}

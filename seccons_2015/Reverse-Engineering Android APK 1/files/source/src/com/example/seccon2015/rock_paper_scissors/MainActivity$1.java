// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.example.seccon2015.rock_paper_scissors;

import android.widget.TextView;

// Referenced classes of package com.example.seccon2015.rock_paper_scissors:
//            MainActivity

class this._cls0
    implements Runnable
{

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

    ()
    {
        this$0 = MainActivity.this;
        super();
    }
}

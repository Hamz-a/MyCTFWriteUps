// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

// Referenced classes of package android.support.v4.widget:
//            SwipeRefreshLayout

class this._cls0 extends Animation
{

    final SwipeRefreshLayout this$0;

    public void applyTransformation(float f, Transformation transformation)
    {
        float f1 = SwipeRefreshLayout.access$1400(SwipeRefreshLayout.this);
        float f2 = -SwipeRefreshLayout.access$1400(SwipeRefreshLayout.this);
        SwipeRefreshLayout.access$700(SwipeRefreshLayout.this, f1 + f2 * f);
        SwipeRefreshLayout.access$1300(SwipeRefreshLayout.this, f);
    }

    ()
    {
        this$0 = SwipeRefreshLayout.this;
        super();
    }
}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

// Referenced classes of package android.support.v7.internal.widget:
//            FitWindowsViewGroup

public class FitWindowsFrameLayout extends FrameLayout
    implements FitWindowsViewGroup
{

    private FitWindowsViewGroup.OnFitSystemWindowsListener mListener;

    public FitWindowsFrameLayout(Context context)
    {
        super(context);
    }

    public FitWindowsFrameLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    protected boolean fitSystemWindows(Rect rect)
    {
        if (mListener != null)
        {
            mListener.onFitSystemWindows(rect);
        }
        return super.fitSystemWindows(rect);
    }

    public void setOnFitSystemWindowsListener(FitWindowsViewGroup.OnFitSystemWindowsListener onfitsystemwindowslistener)
    {
        mListener = onfitsystemwindowslistener;
    }
}

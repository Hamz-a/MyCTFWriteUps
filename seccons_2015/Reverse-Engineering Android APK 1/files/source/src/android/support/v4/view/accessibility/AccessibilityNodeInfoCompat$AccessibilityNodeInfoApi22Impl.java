// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view.accessibility;

import android.view.View;

// Referenced classes of package android.support.v4.view.accessibility:
//            AccessibilityNodeInfoCompat, AccessibilityNodeInfoCompatApi22

static class  extends 
{

    public Object getTraversalAfter(Object obj)
    {
        return AccessibilityNodeInfoCompatApi22.getTraversalAfter(obj);
    }

    public Object getTraversalBefore(Object obj)
    {
        return AccessibilityNodeInfoCompatApi22.getTraversalBefore(obj);
    }

    public void setTraversalAfter(Object obj, View view)
    {
        AccessibilityNodeInfoCompatApi22.setTraversalAfter(obj, view);
    }

    public void setTraversalAfter(Object obj, View view, int i)
    {
        AccessibilityNodeInfoCompatApi22.setTraversalAfter(obj, view, i);
    }

    public void setTraversalBefore(Object obj, View view)
    {
        AccessibilityNodeInfoCompatApi22.setTraversalBefore(obj, view);
    }

    public void setTraversalBefore(Object obj, View view, int i)
    {
        AccessibilityNodeInfoCompatApi22.setTraversalBefore(obj, view, i);
    }

    ()
    {
    }
}

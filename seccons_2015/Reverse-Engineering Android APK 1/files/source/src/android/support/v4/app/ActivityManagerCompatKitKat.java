// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.app.ActivityManager;

class ActivityManagerCompatKitKat
{

    ActivityManagerCompatKitKat()
    {
    }

    public static boolean isLowRamDevice(ActivityManager activitymanager)
    {
        return activitymanager.isLowRamDevice();
    }
}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.graphics;

import android.graphics.Bitmap;

// Referenced classes of package android.support.v4.graphics:
//            BitmapCompat

static class 
    implements 
{

    public int getAllocationByteCount(Bitmap bitmap)
    {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public boolean hasMipMap(Bitmap bitmap)
    {
        return false;
    }

    public void setHasMipMap(Bitmap bitmap, boolean flag)
    {
    }

    ()
    {
    }
}

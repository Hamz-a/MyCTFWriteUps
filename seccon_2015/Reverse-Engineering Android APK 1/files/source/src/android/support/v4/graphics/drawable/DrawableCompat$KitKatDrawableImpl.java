// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

// Referenced classes of package android.support.v4.graphics.drawable:
//            DrawableCompat, DrawableCompatKitKat

static class eImpl extends eImpl
{

    public boolean isAutoMirrored(Drawable drawable)
    {
        return DrawableCompatKitKat.isAutoMirrored(drawable);
    }

    public void setAutoMirrored(Drawable drawable, boolean flag)
    {
        DrawableCompatKitKat.setAutoMirrored(drawable, flag);
    }

    public Drawable wrap(Drawable drawable)
    {
        return DrawableCompatKitKat.wrapForTinting(drawable);
    }

    eImpl()
    {
    }
}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

// Referenced classes of package android.support.v4.graphics.drawable:
//            DrawableWrapperHoneycomb

class DrawableWrapperKitKat extends DrawableWrapperHoneycomb
{

    DrawableWrapperKitKat(Drawable drawable)
    {
        super(drawable);
    }

    public boolean isAutoMirrored()
    {
        return mDrawable.isAutoMirrored();
    }

    public void setAutoMirrored(boolean flag)
    {
        mDrawable.setAutoMirrored(flag);
    }
}

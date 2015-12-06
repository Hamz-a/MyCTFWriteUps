// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

// Referenced classes of package android.support.v4.graphics.drawable:
//            DrawableWrapper, DrawableCompatBase, DrawableCompatHoneycomb, DrawableCompatJellybeanMr1, 
//            DrawableCompatKitKat, DrawableCompatLollipop, DrawableCompatApi22, DrawableCompatApi23

public class DrawableCompat
{
    static class BaseDrawableImpl
        implements DrawableImpl
    {

        public int getLayoutDirection(Drawable drawable)
        {
            return 0;
        }

        public boolean isAutoMirrored(Drawable drawable)
        {
            return false;
        }

        public void jumpToCurrentState(Drawable drawable)
        {
        }

        public void setAutoMirrored(Drawable drawable, boolean flag)
        {
        }

        public void setHotspot(Drawable drawable, float f, float f1)
        {
        }

        public void setHotspotBounds(Drawable drawable, int i, int j, int k, int l)
        {
        }

        public void setLayoutDirection(Drawable drawable, int i)
        {
        }

        public void setTint(Drawable drawable, int i)
        {
            DrawableCompatBase.setTint(drawable, i);
        }

        public void setTintList(Drawable drawable, ColorStateList colorstatelist)
        {
            DrawableCompatBase.setTintList(drawable, colorstatelist);
        }

        public void setTintMode(Drawable drawable, android.graphics.PorterDuff.Mode mode)
        {
            DrawableCompatBase.setTintMode(drawable, mode);
        }

        public Drawable wrap(Drawable drawable)
        {
            return DrawableCompatBase.wrapForTinting(drawable);
        }

        BaseDrawableImpl()
        {
        }
    }

    static interface DrawableImpl
    {

        public abstract int getLayoutDirection(Drawable drawable);

        public abstract boolean isAutoMirrored(Drawable drawable);

        public abstract void jumpToCurrentState(Drawable drawable);

        public abstract void setAutoMirrored(Drawable drawable, boolean flag);

        public abstract void setHotspot(Drawable drawable, float f, float f1);

        public abstract void setHotspotBounds(Drawable drawable, int i, int j, int k, int l);

        public abstract void setLayoutDirection(Drawable drawable, int i);

        public abstract void setTint(Drawable drawable, int i);

        public abstract void setTintList(Drawable drawable, ColorStateList colorstatelist);

        public abstract void setTintMode(Drawable drawable, android.graphics.PorterDuff.Mode mode);

        public abstract Drawable wrap(Drawable drawable);
    }

    static class HoneycombDrawableImpl extends BaseDrawableImpl
    {

        public void jumpToCurrentState(Drawable drawable)
        {
            DrawableCompatHoneycomb.jumpToCurrentState(drawable);
        }

        public Drawable wrap(Drawable drawable)
        {
            return DrawableCompatHoneycomb.wrapForTinting(drawable);
        }

        HoneycombDrawableImpl()
        {
        }
    }

    static class JellybeanMr1DrawableImpl extends HoneycombDrawableImpl
    {

        public int getLayoutDirection(Drawable drawable)
        {
            int i = DrawableCompatJellybeanMr1.getLayoutDirection(drawable);
            if (i < 0)
            {
                return i;
            } else
            {
                return 0;
            }
        }

        public void setLayoutDirection(Drawable drawable, int i)
        {
            DrawableCompatJellybeanMr1.setLayoutDirection(drawable, i);
        }

        JellybeanMr1DrawableImpl()
        {
        }
    }

    static class KitKatDrawableImpl extends JellybeanMr1DrawableImpl
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

        KitKatDrawableImpl()
        {
        }
    }

    static class LollipopDrawableImpl extends KitKatDrawableImpl
    {

        public void setHotspot(Drawable drawable, float f, float f1)
        {
            DrawableCompatLollipop.setHotspot(drawable, f, f1);
        }

        public void setHotspotBounds(Drawable drawable, int i, int j, int k, int l)
        {
            DrawableCompatLollipop.setHotspotBounds(drawable, i, j, k, l);
        }

        public void setTint(Drawable drawable, int i)
        {
            DrawableCompatLollipop.setTint(drawable, i);
        }

        public void setTintList(Drawable drawable, ColorStateList colorstatelist)
        {
            DrawableCompatLollipop.setTintList(drawable, colorstatelist);
        }

        public void setTintMode(Drawable drawable, android.graphics.PorterDuff.Mode mode)
        {
            DrawableCompatLollipop.setTintMode(drawable, mode);
        }

        public Drawable wrap(Drawable drawable)
        {
            return DrawableCompatLollipop.wrapForTinting(drawable);
        }

        LollipopDrawableImpl()
        {
        }
    }

    static class LollipopMr1DrawableImpl extends LollipopDrawableImpl
    {

        public Drawable wrap(Drawable drawable)
        {
            return DrawableCompatApi22.wrapForTinting(drawable);
        }

        LollipopMr1DrawableImpl()
        {
        }
    }

    static class MDrawableImpl extends LollipopMr1DrawableImpl
    {

        public int getLayoutDirection(Drawable drawable)
        {
            return DrawableCompatApi23.getLayoutDirection(drawable);
        }

        public void setLayoutDirection(Drawable drawable, int i)
        {
            DrawableCompatApi23.setLayoutDirection(drawable, i);
        }

        MDrawableImpl()
        {
        }
    }


    static final DrawableImpl IMPL;

    public DrawableCompat()
    {
    }

    public static int getLayoutDirection(Drawable drawable)
    {
        return IMPL.getLayoutDirection(drawable);
    }

    public static boolean isAutoMirrored(Drawable drawable)
    {
        return IMPL.isAutoMirrored(drawable);
    }

    public static void jumpToCurrentState(Drawable drawable)
    {
        IMPL.jumpToCurrentState(drawable);
    }

    public static void setAutoMirrored(Drawable drawable, boolean flag)
    {
        IMPL.setAutoMirrored(drawable, flag);
    }

    public static void setHotspot(Drawable drawable, float f, float f1)
    {
        IMPL.setHotspot(drawable, f, f1);
    }

    public static void setHotspotBounds(Drawable drawable, int i, int j, int k, int l)
    {
        IMPL.setHotspotBounds(drawable, i, j, k, l);
    }

    public static void setLayoutDirection(Drawable drawable, int i)
    {
        IMPL.setLayoutDirection(drawable, i);
    }

    public static void setTint(Drawable drawable, int i)
    {
        IMPL.setTint(drawable, i);
    }

    public static void setTintList(Drawable drawable, ColorStateList colorstatelist)
    {
        IMPL.setTintList(drawable, colorstatelist);
    }

    public static void setTintMode(Drawable drawable, android.graphics.PorterDuff.Mode mode)
    {
        IMPL.setTintMode(drawable, mode);
    }

    public static Drawable unwrap(Drawable drawable)
    {
        Drawable drawable1 = drawable;
        if (drawable instanceof DrawableWrapper)
        {
            drawable1 = ((DrawableWrapper)drawable).getWrappedDrawable();
        }
        return drawable1;
    }

    public static Drawable wrap(Drawable drawable)
    {
        return IMPL.wrap(drawable);
    }

    static 
    {
        int i = android.os.Build.VERSION.SDK_INT;
        if (i >= 23)
        {
            IMPL = new MDrawableImpl();
        } else
        if (i >= 22)
        {
            IMPL = new LollipopMr1DrawableImpl();
        } else
        if (i >= 21)
        {
            IMPL = new LollipopDrawableImpl();
        } else
        if (i >= 19)
        {
            IMPL = new KitKatDrawableImpl();
        } else
        if (i >= 17)
        {
            IMPL = new JellybeanMr1DrawableImpl();
        } else
        if (i >= 11)
        {
            IMPL = new HoneycombDrawableImpl();
        } else
        {
            IMPL = new BaseDrawableImpl();
        }
    }
}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.WindowInsets;

// Referenced classes of package android.support.v4.view:
//            WindowInsetsCompatApi21, WindowInsetsCompat, OnApplyWindowInsetsListener

class ViewCompatLollipop
{

    ViewCompatLollipop()
    {
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowinsetscompat)
    {
        Object obj = windowinsetscompat;
        if (windowinsetscompat instanceof WindowInsetsCompatApi21)
        {
            WindowInsets windowinsets = ((WindowInsetsCompatApi21)windowinsetscompat).unwrap();
            view = view.dispatchApplyWindowInsets(windowinsets);
            obj = windowinsetscompat;
            if (view != windowinsets)
            {
                obj = new WindowInsetsCompatApi21(view);
            }
        }
        return ((WindowInsetsCompat) (obj));
    }

    public static boolean dispatchNestedFling(View view, float f, float f1, boolean flag)
    {
        return view.dispatchNestedFling(f, f1, flag);
    }

    public static boolean dispatchNestedPreFling(View view, float f, float f1)
    {
        return view.dispatchNestedPreFling(f, f1);
    }

    public static boolean dispatchNestedPreScroll(View view, int i, int j, int ai[], int ai1[])
    {
        return view.dispatchNestedPreScroll(i, j, ai, ai1);
    }

    public static boolean dispatchNestedScroll(View view, int i, int j, int k, int l, int ai[])
    {
        return view.dispatchNestedScroll(i, j, k, l, ai);
    }

    static ColorStateList getBackgroundTintList(View view)
    {
        return view.getBackgroundTintList();
    }

    static android.graphics.PorterDuff.Mode getBackgroundTintMode(View view)
    {
        return view.getBackgroundTintMode();
    }

    public static float getElevation(View view)
    {
        return view.getElevation();
    }

    public static String getTransitionName(View view)
    {
        return view.getTransitionName();
    }

    public static float getTranslationZ(View view)
    {
        return view.getTranslationZ();
    }

    public static float getZ(View view)
    {
        return view.getZ();
    }

    public static boolean hasNestedScrollingParent(View view)
    {
        return view.hasNestedScrollingParent();
    }

    public static boolean isImportantForAccessibility(View view)
    {
        return view.isImportantForAccessibility();
    }

    public static boolean isNestedScrollingEnabled(View view)
    {
        return view.isNestedScrollingEnabled();
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowinsetscompat)
    {
        Object obj = windowinsetscompat;
        if (windowinsetscompat instanceof WindowInsetsCompatApi21)
        {
            WindowInsets windowinsets = ((WindowInsetsCompatApi21)windowinsetscompat).unwrap();
            view = view.onApplyWindowInsets(windowinsets);
            obj = windowinsetscompat;
            if (view != windowinsets)
            {
                obj = new WindowInsetsCompatApi21(view);
            }
        }
        return ((WindowInsetsCompat) (obj));
    }

    public static void requestApplyInsets(View view)
    {
        view.requestApplyInsets();
    }

    static void setBackgroundTintList(View view, ColorStateList colorstatelist)
    {
        view.setBackgroundTintList(colorstatelist);
    }

    static void setBackgroundTintMode(View view, android.graphics.PorterDuff.Mode mode)
    {
        view.setBackgroundTintMode(mode);
    }

    public static void setElevation(View view, float f)
    {
        view.setElevation(f);
    }

    public static void setNestedScrollingEnabled(View view, boolean flag)
    {
        view.setNestedScrollingEnabled(flag);
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onapplywindowinsetslistener)
    {
        view.setOnApplyWindowInsetsListener(new android.view.View.OnApplyWindowInsetsListener(onapplywindowinsetslistener) {

            final OnApplyWindowInsetsListener val$listener;

            public WindowInsets onApplyWindowInsets(View view1, WindowInsets windowinsets)
            {
                windowinsets = new WindowInsetsCompatApi21(windowinsets);
                return ((WindowInsetsCompatApi21)listener.onApplyWindowInsets(view1, windowinsets)).unwrap();
            }

            
            {
                listener = onapplywindowinsetslistener;
                super();
            }
        });
    }

    public static void setTransitionName(View view, String s)
    {
        view.setTransitionName(s);
    }

    public static void setTranslationZ(View view, float f)
    {
        view.setTranslationZ(f);
    }

    public static boolean startNestedScroll(View view, int i)
    {
        return view.startNestedScroll(i);
    }

    public static void stopNestedScroll(View view)
    {
        view.stopNestedScroll();
    }
}

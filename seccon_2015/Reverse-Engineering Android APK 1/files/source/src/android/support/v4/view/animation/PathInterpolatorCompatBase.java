// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view.animation;

import android.graphics.Path;
import android.view.animation.Interpolator;

// Referenced classes of package android.support.v4.view.animation:
//            PathInterpolatorDonut

class PathInterpolatorCompatBase
{

    private PathInterpolatorCompatBase()
    {
    }

    public static Interpolator create(float f, float f1)
    {
        return new PathInterpolatorDonut(f, f1);
    }

    public static Interpolator create(float f, float f1, float f2, float f3)
    {
        return new PathInterpolatorDonut(f, f1, f2, f3);
    }

    public static Interpolator create(Path path)
    {
        return new PathInterpolatorDonut(path);
    }
}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view.animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Interpolator;

class PathInterpolatorDonut
    implements Interpolator
{

    private static final float PRECISION = 0.002F;
    private final float mX[];
    private final float mY[];

    public PathInterpolatorDonut(float f, float f1)
    {
        this(createQuad(f, f1));
    }

    public PathInterpolatorDonut(float f, float f1, float f2, float f3)
    {
        this(createCubic(f, f1, f2, f3));
    }

    public PathInterpolatorDonut(Path path)
    {
        path = new PathMeasure(path, false);
        float f = path.getLength();
        int j = (int)(f / 0.002F) + 1;
        mX = new float[j];
        mY = new float[j];
        float af[] = new float[2];
        for (int i = 0; i < j; i++)
        {
            path.getPosTan(((float)i * f) / (float)(j - 1), af, null);
            mX[i] = af[0];
            mY[i] = af[1];
        }

    }

    private static Path createCubic(float f, float f1, float f2, float f3)
    {
        Path path = new Path();
        path.moveTo(0.0F, 0.0F);
        path.cubicTo(f, f1, f2, f3, 1.0F, 1.0F);
        return path;
    }

    private static Path createQuad(float f, float f1)
    {
        Path path = new Path();
        path.moveTo(0.0F, 0.0F);
        path.quadTo(f, f1, 1.0F, 1.0F);
        return path;
    }

    public float getInterpolation(float f)
    {
        if (f <= 0.0F)
        {
            return 0.0F;
        }
        if (f >= 1.0F)
        {
            return 1.0F;
        }
        int i = 0;
        int j;
        for (j = mX.length - 1; j - i > 1;)
        {
            int k = (i + j) / 2;
            if (f < mX[k])
            {
                j = k;
            } else
            {
                i = k;
            }
        }

        float f1 = mX[j] - mX[i];
        if (f1 == 0.0F)
        {
            return mY[i];
        } else
        {
            f = (f - mX[i]) / f1;
            f1 = mY[i];
            return (mY[j] - f1) * f + f1;
        }
    }
}

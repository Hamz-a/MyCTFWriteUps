// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;

// Referenced classes of package android.support.v4.view:
//            ViewPropertyAnimatorListener

class ViewPropertyAnimatorCompatJB
{

    ViewPropertyAnimatorCompatJB()
    {
    }

    public static void setListener(View view, ViewPropertyAnimatorListener viewpropertyanimatorlistener)
    {
        if (viewpropertyanimatorlistener != null)
        {
            view.animate().setListener(new AnimatorListenerAdapter(viewpropertyanimatorlistener, view) {

                final ViewPropertyAnimatorListener val$listener;
                final View val$view;

                public void onAnimationCancel(Animator animator)
                {
                    listener.onAnimationCancel(view);
                }

                public void onAnimationEnd(Animator animator)
                {
                    listener.onAnimationEnd(view);
                }

                public void onAnimationStart(Animator animator)
                {
                    listener.onAnimationStart(view);
                }

            
            {
                listener = viewpropertyanimatorlistener;
                view = view1;
                super();
            }
            });
            return;
        } else
        {
            view.animate().setListener(null);
            return;
        }
    }

    public static void withEndAction(View view, Runnable runnable)
    {
        view.animate().withEndAction(runnable);
    }

    public static void withLayer(View view)
    {
        view.animate().withLayer();
    }

    public static void withStartAction(View view, Runnable runnable)
    {
        view.animate().withStartAction(runnable);
    }
}

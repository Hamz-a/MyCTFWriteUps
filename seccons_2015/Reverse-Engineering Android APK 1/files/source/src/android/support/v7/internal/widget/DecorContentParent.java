// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.internal.widget;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.Menu;

public interface DecorContentParent
{

    public abstract boolean canShowOverflowMenu();

    public abstract void dismissPopups();

    public abstract CharSequence getTitle();

    public abstract boolean hasIcon();

    public abstract boolean hasLogo();

    public abstract boolean hideOverflowMenu();

    public abstract void initFeature(int i);

    public abstract boolean isOverflowMenuShowPending();

    public abstract boolean isOverflowMenuShowing();

    public abstract void restoreToolbarHierarchyState(SparseArray sparsearray);

    public abstract void saveToolbarHierarchyState(SparseArray sparsearray);

    public abstract void setIcon(int i);

    public abstract void setIcon(Drawable drawable);

    public abstract void setLogo(int i);

    public abstract void setMenu(Menu menu, android.support.v7.internal.view.menu.MenuPresenter.Callback callback);

    public abstract void setMenuPrepared();

    public abstract void setUiOptions(int i);

    public abstract void setWindowCallback(android.view.Window.Callback callback);

    public abstract void setWindowTitle(CharSequence charsequence);

    public abstract boolean showOverflowMenu();
}

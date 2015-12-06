// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.internal.app.AppCompatViewInflater;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.internal.app.WindowDecorActionBar;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.internal.view.StandaloneActionMode;
import android.support.v7.internal.view.menu.ListMenuPresenter;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.widget.ActionBarContextView;
import android.support.v7.internal.widget.ContentFrameLayout;
import android.support.v7.internal.widget.DecorContentParent;
import android.support.v7.internal.widget.FitWindowsViewGroup;
import android.support.v7.internal.widget.TintManager;
import android.support.v7.internal.widget.ViewStubCompat;
import android.support.v7.internal.widget.ViewUtils;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

// Referenced classes of package android.support.v7.app:
//            AppCompatDelegateImplBase, ActionBar, AppCompatCallback

class AppCompatDelegateImplV7 extends AppCompatDelegateImplBase
    implements android.support.v7.internal.view.menu.MenuBuilder.Callback, LayoutInflaterFactory
{
    private final class ActionMenuPresenterCallback
        implements android.support.v7.internal.view.menu.MenuPresenter.Callback
    {

        final AppCompatDelegateImplV7 this$0;

        public void onCloseMenu(MenuBuilder menubuilder, boolean flag)
        {
            checkCloseActionMenu(menubuilder);
        }

        public boolean onOpenSubMenu(MenuBuilder menubuilder)
        {
            android.view.Window.Callback callback = getWindowCallback();
            if (callback != null)
            {
                callback.onMenuOpened(108, menubuilder);
            }
            return true;
        }

        private ActionMenuPresenterCallback()
        {
            this$0 = AppCompatDelegateImplV7.this;
            super();
        }

    }

    class ActionModeCallbackWrapperV7
        implements android.support.v7.view.ActionMode.Callback
    {

        private android.support.v7.view.ActionMode.Callback mWrapped;
        final AppCompatDelegateImplV7 this$0;

        public boolean onActionItemClicked(ActionMode actionmode, MenuItem menuitem)
        {
            return mWrapped.onActionItemClicked(actionmode, menuitem);
        }

        public boolean onCreateActionMode(ActionMode actionmode, Menu menu)
        {
            return mWrapped.onCreateActionMode(actionmode, menu);
        }

        public void onDestroyActionMode(ActionMode actionmode)
        {
            mWrapped.onDestroyActionMode(actionmode);
            if (mActionModePopup != null)
            {
                mWindow.getDecorView().removeCallbacks(mShowActionModePopup);
            }
            if (mActionModeView != null)
            {
                endOnGoingFadeAnimation();
                mFadeAnim = ViewCompat.animate(mActionModeView).alpha(0.0F);
                mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() {

                    final ActionModeCallbackWrapperV7 this$1;

                    public void onAnimationEnd(View view)
                    {
                        mActionModeView.setVisibility(8);
                        if (mActionModePopup == null) goto _L2; else goto _L1
_L1:
                        mActionModePopup.dismiss();
_L4:
                        mActionModeView.removeAllViews();
                        mFadeAnim.setListener(null);
                        mFadeAnim = null;
                        return;
_L2:
                        if (mActionModeView.getParent() instanceof View)
                        {
                            ViewCompat.requestApplyInsets((View)mActionModeView.getParent());
                        }
                        if (true) goto _L4; else goto _L3
_L3:
                    }

            
            {
                this$1 = ActionModeCallbackWrapperV7.this;
                super();
            }
                });
            }
            if (mAppCompatCallback != null)
            {
                mAppCompatCallback.onSupportActionModeFinished(mActionMode);
            }
            mActionMode = null;
        }

        public boolean onPrepareActionMode(ActionMode actionmode, Menu menu)
        {
            return mWrapped.onPrepareActionMode(actionmode, menu);
        }

        public ActionModeCallbackWrapperV7(android.support.v7.view.ActionMode.Callback callback)
        {
            this$0 = AppCompatDelegateImplV7.this;
            super();
            mWrapped = callback;
        }
    }

    private class ListMenuDecorView extends FrameLayout
    {

        final AppCompatDelegateImplV7 this$0;

        private boolean isOutOfBounds(int i, int j)
        {
            return i < -5 || j < -5 || i > getWidth() + 5 || j > getHeight() + 5;
        }

        public boolean dispatchKeyEvent(KeyEvent keyevent)
        {
            return AppCompatDelegateImplV7.this.dispatchKeyEvent(keyevent) || super.dispatchKeyEvent(keyevent);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionevent)
        {
            if (motionevent.getAction() == 0 && isOutOfBounds((int)motionevent.getX(), (int)motionevent.getY()))
            {
                closePanel(0);
                return true;
            } else
            {
                return super.onInterceptTouchEvent(motionevent);
            }
        }

        public void setBackgroundResource(int i)
        {
            setBackgroundDrawable(TintManager.getDrawable(getContext(), i));
        }

        public ListMenuDecorView(Context context)
        {
            this$0 = AppCompatDelegateImplV7.this;
            super(context);
        }
    }

    private static final class PanelFeatureState
    {

        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        Context listPresenterContext;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastOpen;
        int windowAnimations;
        int x;
        int y;

        void applyFrozenState()
        {
            if (menu != null && frozenMenuState != null)
            {
                menu.restorePresenterStates(frozenMenuState);
                frozenMenuState = null;
            }
        }

        public void clearMenuPresenters()
        {
            if (menu != null)
            {
                menu.removeMenuPresenter(listMenuPresenter);
            }
            listMenuPresenter = null;
        }

        MenuView getListMenuView(android.support.v7.internal.view.menu.MenuPresenter.Callback callback)
        {
            if (menu == null)
            {
                return null;
            }
            if (listMenuPresenter == null)
            {
                listMenuPresenter = new ListMenuPresenter(listPresenterContext, android.support.v7.appcompat.R.layout.abc_list_menu_item_layout);
                listMenuPresenter.setCallback(callback);
                menu.addMenuPresenter(listMenuPresenter);
            }
            return listMenuPresenter.getMenuView(decorView);
        }

        public boolean hasPanelItems()
        {
            boolean flag1 = true;
            boolean flag;
            if (shownPanelView == null)
            {
                flag = false;
            } else
            {
                flag = flag1;
                if (createdPanelView == null)
                {
                    flag = flag1;
                    if (listMenuPresenter.getAdapter().getCount() <= 0)
                    {
                        return false;
                    }
                }
            }
            return flag;
        }

        void onRestoreInstanceState(Parcelable parcelable)
        {
            parcelable = (SavedState)parcelable;
            featureId = ((SavedState) (parcelable)).featureId;
            wasLastOpen = ((SavedState) (parcelable)).isOpen;
            frozenMenuState = ((SavedState) (parcelable)).menuState;
            shownPanelView = null;
            decorView = null;
        }

        Parcelable onSaveInstanceState()
        {
            SavedState savedstate = new SavedState();
            savedstate.featureId = featureId;
            savedstate.isOpen = isOpen;
            if (menu != null)
            {
                savedstate.menuState = new Bundle();
                menu.savePresenterStates(savedstate.menuState);
            }
            return savedstate;
        }

        void setMenu(MenuBuilder menubuilder)
        {
            if (menubuilder != menu)
            {
                if (menu != null)
                {
                    menu.removeMenuPresenter(listMenuPresenter);
                }
                menu = menubuilder;
                if (menubuilder != null && listMenuPresenter != null)
                {
                    menubuilder.addMenuPresenter(listMenuPresenter);
                    return;
                }
            }
        }

        void setStyle(Context context)
        {
            TypedValue typedvalue = new TypedValue();
            android.content.res.Resources.Theme theme = context.getResources().newTheme();
            theme.setTo(context.getTheme());
            theme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarPopupTheme, typedvalue, true);
            if (typedvalue.resourceId != 0)
            {
                theme.applyStyle(typedvalue.resourceId, true);
            }
            theme.resolveAttribute(android.support.v7.appcompat.R.attr.panelMenuListTheme, typedvalue, true);
            if (typedvalue.resourceId != 0)
            {
                theme.applyStyle(typedvalue.resourceId, true);
            } else
            {
                theme.applyStyle(android.support.v7.appcompat.R.style.Theme_AppCompat_CompactMenu, true);
            }
            context = new ContextThemeWrapper(context, 0);
            context.getTheme().setTo(theme);
            listPresenterContext = context;
            context = context.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.Theme);
            background = context.getResourceId(android.support.v7.appcompat.R.styleable.Theme_panelBackground, 0);
            windowAnimations = context.getResourceId(android.support.v7.appcompat.R.styleable.Theme_android_windowAnimationStyle, 0);
            context.recycle();
        }

        PanelFeatureState(int i)
        {
            featureId = i;
            refreshDecorView = false;
        }
    }

    private static class PanelFeatureState.SavedState
        implements Parcelable
    {

        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

            public PanelFeatureState.SavedState createFromParcel(Parcel parcel)
            {
                return PanelFeatureState.SavedState.readFromParcel(parcel);
            }

            public volatile Object createFromParcel(Parcel parcel)
            {
                return createFromParcel(parcel);
            }

            public PanelFeatureState.SavedState[] newArray(int i)
            {
                return new PanelFeatureState.SavedState[i];
            }

            public volatile Object[] newArray(int i)
            {
                return newArray(i);
            }

        };
        int featureId;
        boolean isOpen;
        Bundle menuState;

        private static PanelFeatureState.SavedState readFromParcel(Parcel parcel)
        {
            boolean flag = true;
            PanelFeatureState.SavedState savedstate = new PanelFeatureState.SavedState();
            savedstate.featureId = parcel.readInt();
            if (parcel.readInt() != 1)
            {
                flag = false;
            }
            savedstate.isOpen = flag;
            if (savedstate.isOpen)
            {
                savedstate.menuState = parcel.readBundle();
            }
            return savedstate;
        }

        public int describeContents()
        {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i)
        {
            parcel.writeInt(featureId);
            if (isOpen)
            {
                i = 1;
            } else
            {
                i = 0;
            }
            parcel.writeInt(i);
            if (isOpen)
            {
                parcel.writeBundle(menuState);
            }
        }



        private PanelFeatureState.SavedState()
        {
        }

    }

    private final class PanelMenuPresenterCallback
        implements android.support.v7.internal.view.menu.MenuPresenter.Callback
    {

        final AppCompatDelegateImplV7 this$0;

        public void onCloseMenu(MenuBuilder menubuilder, boolean flag)
        {
label0:
            {
                MenuBuilder menubuilder1 = menubuilder.getRootMenu();
                AppCompatDelegateImplV7 appcompatdelegateimplv7;
                boolean flag1;
                if (menubuilder1 != menubuilder)
                {
                    flag1 = true;
                } else
                {
                    flag1 = false;
                }
                appcompatdelegateimplv7 = AppCompatDelegateImplV7.this;
                if (flag1)
                {
                    menubuilder = menubuilder1;
                }
                menubuilder = appcompatdelegateimplv7.findMenuPanel(menubuilder);
                if (menubuilder != null)
                {
                    if (!flag1)
                    {
                        break label0;
                    }
                    callOnPanelClosed(((PanelFeatureState) (menubuilder)).featureId, menubuilder, menubuilder1);
                    closePanel(menubuilder, true);
                }
                return;
            }
            closePanel(menubuilder, flag);
        }

        public boolean onOpenSubMenu(MenuBuilder menubuilder)
        {
            if (menubuilder == null && mHasActionBar)
            {
                android.view.Window.Callback callback = getWindowCallback();
                if (callback != null && !isDestroyed())
                {
                    callback.onMenuOpened(108, menubuilder);
                }
            }
            return true;
        }

        private PanelMenuPresenterCallback()
        {
            this$0 = AppCompatDelegateImplV7.this;
            super();
        }

    }


    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    private AppCompatViewInflater mAppCompatViewInflater;
    private boolean mClosingActionMenu;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    ViewPropertyAnimatorCompat mFadeAnim;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    private int mInvalidatePanelMenuFeatures;
    private boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable = new Runnable() {

        final AppCompatDelegateImplV7 this$0;

        public void run()
        {
            if ((mInvalidatePanelMenuFeatures & 1) != 0)
            {
                doInvalidatePanelMenu(0);
            }
            if ((mInvalidatePanelMenuFeatures & 0x1000) != 0)
            {
                doInvalidatePanelMenu(108);
            }
            mInvalidatePanelMenuPosted = false;
            mInvalidatePanelMenuFeatures = 0;
        }

            
            {
                this$0 = AppCompatDelegateImplV7.this;
                super();
            }
    };
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState mPanels[];
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private View mStatusGuard;
    private ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private TextView mTitleView;
    private ViewGroup mWindowDecor;

    AppCompatDelegateImplV7(Context context, Window window, AppCompatCallback appcompatcallback)
    {
        super(context, window, appcompatcallback);
        mFadeAnim = null;
    }

    private void applyFixedSizeWindow()
    {
        ContentFrameLayout contentframelayout = (ContentFrameLayout)mSubDecor.findViewById(0x1020002);
        contentframelayout.setDecorPadding(mWindowDecor.getPaddingLeft(), mWindowDecor.getPaddingTop(), mWindowDecor.getPaddingRight(), mWindowDecor.getPaddingBottom());
        TypedArray typedarray = mContext.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.Theme);
        typedarray.getValue(android.support.v7.appcompat.R.styleable.Theme_windowMinWidthMajor, contentframelayout.getMinWidthMajor());
        typedarray.getValue(android.support.v7.appcompat.R.styleable.Theme_windowMinWidthMinor, contentframelayout.getMinWidthMinor());
        if (typedarray.hasValue(android.support.v7.appcompat.R.styleable.Theme_windowFixedWidthMajor))
        {
            typedarray.getValue(android.support.v7.appcompat.R.styleable.Theme_windowFixedWidthMajor, contentframelayout.getFixedWidthMajor());
        }
        if (typedarray.hasValue(android.support.v7.appcompat.R.styleable.Theme_windowFixedWidthMinor))
        {
            typedarray.getValue(android.support.v7.appcompat.R.styleable.Theme_windowFixedWidthMinor, contentframelayout.getFixedWidthMinor());
        }
        if (typedarray.hasValue(android.support.v7.appcompat.R.styleable.Theme_windowFixedHeightMajor))
        {
            typedarray.getValue(android.support.v7.appcompat.R.styleable.Theme_windowFixedHeightMajor, contentframelayout.getFixedHeightMajor());
        }
        if (typedarray.hasValue(android.support.v7.appcompat.R.styleable.Theme_windowFixedHeightMinor))
        {
            typedarray.getValue(android.support.v7.appcompat.R.styleable.Theme_windowFixedHeightMinor, contentframelayout.getFixedHeightMinor());
        }
        typedarray.recycle();
        contentframelayout.requestLayout();
    }

    private void callOnPanelClosed(int i, PanelFeatureState panelfeaturestate, Menu menu)
    {
        PanelFeatureState panelfeaturestate2 = panelfeaturestate;
        Object obj = menu;
        if (menu == null)
        {
            PanelFeatureState panelfeaturestate1 = panelfeaturestate;
            if (panelfeaturestate == null)
            {
                panelfeaturestate1 = panelfeaturestate;
                if (i >= 0)
                {
                    panelfeaturestate1 = panelfeaturestate;
                    if (i < mPanels.length)
                    {
                        panelfeaturestate1 = mPanels[i];
                    }
                }
            }
            panelfeaturestate2 = panelfeaturestate1;
            obj = menu;
            if (panelfeaturestate1 != null)
            {
                obj = panelfeaturestate1.menu;
                panelfeaturestate2 = panelfeaturestate1;
            }
        }
        while (panelfeaturestate2 != null && !panelfeaturestate2.isOpen || isDestroyed()) 
        {
            return;
        }
        mOriginalWindowCallback.onPanelClosed(i, ((Menu) (obj)));
    }

    private void checkCloseActionMenu(MenuBuilder menubuilder)
    {
        if (mClosingActionMenu)
        {
            return;
        }
        mClosingActionMenu = true;
        mDecorContentParent.dismissPopups();
        android.view.Window.Callback callback = getWindowCallback();
        if (callback != null && !isDestroyed())
        {
            callback.onPanelClosed(108, menubuilder);
        }
        mClosingActionMenu = false;
    }

    private void closePanel(int i)
    {
        closePanel(getPanelState(i, true), true);
    }

    private void closePanel(PanelFeatureState panelfeaturestate, boolean flag)
    {
        if (flag && panelfeaturestate.featureId == 0 && mDecorContentParent != null && mDecorContentParent.isOverflowMenuShowing())
        {
            checkCloseActionMenu(panelfeaturestate.menu);
        } else
        {
            WindowManager windowmanager = (WindowManager)mContext.getSystemService("window");
            if (windowmanager != null && panelfeaturestate.isOpen && panelfeaturestate.decorView != null)
            {
                windowmanager.removeView(panelfeaturestate.decorView);
                if (flag)
                {
                    callOnPanelClosed(panelfeaturestate.featureId, panelfeaturestate, null);
                }
            }
            panelfeaturestate.isPrepared = false;
            panelfeaturestate.isHandled = false;
            panelfeaturestate.isOpen = false;
            panelfeaturestate.shownPanelView = null;
            panelfeaturestate.refreshDecorView = true;
            if (mPreparedPanel == panelfeaturestate)
            {
                mPreparedPanel = null;
                return;
            }
        }
    }

    private ViewGroup createSubDecor()
    {
        Object obj;
        obj = mContext.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.Theme);
        if (!((TypedArray) (obj)).hasValue(android.support.v7.appcompat.R.styleable.Theme_windowActionBar))
        {
            ((TypedArray) (obj)).recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (!((TypedArray) (obj)).getBoolean(android.support.v7.appcompat.R.styleable.Theme_windowNoTitle, false)) goto _L2; else goto _L1
_L1:
        requestWindowFeature(1);
_L10:
        Object obj1;
        if (((TypedArray) (obj)).getBoolean(android.support.v7.appcompat.R.styleable.Theme_windowActionBarOverlay, false))
        {
            requestWindowFeature(109);
        }
        if (((TypedArray) (obj)).getBoolean(android.support.v7.appcompat.R.styleable.Theme_windowActionModeOverlay, false))
        {
            requestWindowFeature(10);
        }
        mIsFloating = ((TypedArray) (obj)).getBoolean(android.support.v7.appcompat.R.styleable.Theme_android_windowIsFloating, false);
        ((TypedArray) (obj)).recycle();
        obj1 = LayoutInflater.from(mContext);
        obj = null;
        if (mWindowNoTitle) goto _L4; else goto _L3
_L3:
        if (!mIsFloating) goto _L6; else goto _L5
_L5:
        obj = (ViewGroup)((LayoutInflater) (obj1)).inflate(android.support.v7.appcompat.R.layout.abc_dialog_title_material, null);
        mOverlayActionBar = false;
        mHasActionBar = false;
_L8:
        if (obj == null)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("AppCompat does not support the current theme features: { windowActionBar: ").append(mHasActionBar).append(", windowActionBarOverlay: ").append(mOverlayActionBar).append(", android:windowIsFloating: ").append(mIsFloating).append(", windowActionModeOverlay: ").append(mOverlayActionMode).append(", windowNoTitle: ").append(mWindowNoTitle).append(" }").toString());
        }
        break; /* Loop/switch isn't completed */
_L2:
        if (((TypedArray) (obj)).getBoolean(android.support.v7.appcompat.R.styleable.Theme_windowActionBar, false))
        {
            requestWindowFeature(108);
        }
        continue; /* Loop/switch isn't completed */
_L6:
        if (mHasActionBar)
        {
            obj = new TypedValue();
            mContext.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarTheme, ((TypedValue) (obj)), true);
            if (((TypedValue) (obj)).resourceId != 0)
            {
                obj = new ContextThemeWrapper(mContext, ((TypedValue) (obj)).resourceId);
            } else
            {
                obj = mContext;
            }
            obj1 = (ViewGroup)LayoutInflater.from(((Context) (obj))).inflate(android.support.v7.appcompat.R.layout.abc_screen_toolbar, null);
            mDecorContentParent = (DecorContentParent)((ViewGroup) (obj1)).findViewById(android.support.v7.appcompat.R.id.decor_content_parent);
            mDecorContentParent.setWindowCallback(getWindowCallback());
            if (mOverlayActionBar)
            {
                mDecorContentParent.initFeature(109);
            }
            if (mFeatureProgress)
            {
                mDecorContentParent.initFeature(2);
            }
            obj = obj1;
            if (mFeatureIndeterminateProgress)
            {
                mDecorContentParent.initFeature(5);
                obj = obj1;
            }
        }
        continue; /* Loop/switch isn't completed */
_L4:
        if (mOverlayActionMode)
        {
            obj = (ViewGroup)((LayoutInflater) (obj1)).inflate(android.support.v7.appcompat.R.layout.abc_screen_simple_overlay_action_mode, null);
        } else
        {
            obj = (ViewGroup)((LayoutInflater) (obj1)).inflate(android.support.v7.appcompat.R.layout.abc_screen_simple, null);
        }
        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            ViewCompat.setOnApplyWindowInsetsListener(((View) (obj)), new OnApplyWindowInsetsListener() {

                final AppCompatDelegateImplV7 this$0;

                public WindowInsetsCompat onApplyWindowInsets(View view1, WindowInsetsCompat windowinsetscompat)
                {
                    int i = windowinsetscompat.getSystemWindowInsetTop();
                    int j = updateStatusGuard(i);
                    WindowInsetsCompat windowinsetscompat1 = windowinsetscompat;
                    if (i != j)
                    {
                        windowinsetscompat1 = windowinsetscompat.replaceSystemWindowInsets(windowinsetscompat.getSystemWindowInsetLeft(), j, windowinsetscompat.getSystemWindowInsetRight(), windowinsetscompat.getSystemWindowInsetBottom());
                    }
                    return ViewCompat.onApplyWindowInsets(view1, windowinsetscompat1);
                }

            
            {
                this$0 = AppCompatDelegateImplV7.this;
                super();
            }
            });
        } else
        {
            ((FitWindowsViewGroup)obj).setOnFitSystemWindowsListener(new android.support.v7.internal.widget.FitWindowsViewGroup.OnFitSystemWindowsListener() {

                final AppCompatDelegateImplV7 this$0;

                public void onFitSystemWindows(Rect rect)
                {
                    rect.top = updateStatusGuard(rect.top);
                }

            
            {
                this$0 = AppCompatDelegateImplV7.this;
                super();
            }
            });
        }
        if (true) goto _L8; else goto _L7
_L7:
        if (mDecorContentParent == null)
        {
            mTitleView = (TextView)((ViewGroup) (obj)).findViewById(android.support.v7.appcompat.R.id.title);
        }
        ViewUtils.makeOptionalFitsSystemWindows(((View) (obj)));
        ViewGroup viewgroup = (ViewGroup)mWindow.findViewById(0x1020002);
        ContentFrameLayout contentframelayout = (ContentFrameLayout)((ViewGroup) (obj)).findViewById(android.support.v7.appcompat.R.id.action_bar_activity_content);
        View view;
        for (; viewgroup.getChildCount() > 0; contentframelayout.addView(view))
        {
            view = viewgroup.getChildAt(0);
            viewgroup.removeViewAt(0);
        }

        mWindow.setContentView(((View) (obj)));
        viewgroup.setId(-1);
        contentframelayout.setId(0x1020002);
        if (viewgroup instanceof FrameLayout)
        {
            ((FrameLayout)viewgroup).setForeground(null);
        }
        return ((ViewGroup) (obj));
        if (true) goto _L10; else goto _L9
_L9:
    }

    private void doInvalidatePanelMenu(int i)
    {
        PanelFeatureState panelfeaturestate = getPanelState(i, true);
        if (panelfeaturestate.menu != null)
        {
            Bundle bundle = new Bundle();
            panelfeaturestate.menu.saveActionViewStates(bundle);
            if (bundle.size() > 0)
            {
                panelfeaturestate.frozenActionViewState = bundle;
            }
            panelfeaturestate.menu.stopDispatchingItemsChanged();
            panelfeaturestate.menu.clear();
        }
        panelfeaturestate.refreshMenuContent = true;
        panelfeaturestate.refreshDecorView = true;
        if ((i == 108 || i == 0) && mDecorContentParent != null)
        {
            PanelFeatureState panelfeaturestate1 = getPanelState(0, false);
            if (panelfeaturestate1 != null)
            {
                panelfeaturestate1.isPrepared = false;
                preparePanel(panelfeaturestate1, null);
            }
        }
    }

    private void endOnGoingFadeAnimation()
    {
        if (mFadeAnim != null)
        {
            mFadeAnim.cancel();
        }
    }

    private void ensureSubDecor()
    {
        if (!mSubDecorInstalled)
        {
            mSubDecor = createSubDecor();
            Object obj = getTitle();
            if (!TextUtils.isEmpty(((CharSequence) (obj))))
            {
                onTitleChanged(((CharSequence) (obj)));
            }
            applyFixedSizeWindow();
            onSubDecorInstalled(mSubDecor);
            mSubDecorInstalled = true;
            obj = getPanelState(0, false);
            if (!isDestroyed() && (obj == null || ((PanelFeatureState) (obj)).menu == null))
            {
                invalidatePanelMenu(108);
            }
        }
    }

    private PanelFeatureState findMenuPanel(Menu menu)
    {
        PanelFeatureState apanelfeaturestate[] = mPanels;
        int i;
        int j;
        if (apanelfeaturestate != null)
        {
            i = apanelfeaturestate.length;
        } else
        {
            i = 0;
        }
        for (j = 0; j < i; j++)
        {
            PanelFeatureState panelfeaturestate = apanelfeaturestate[j];
            if (panelfeaturestate != null && panelfeaturestate.menu == menu)
            {
                return panelfeaturestate;
            }
        }

        return null;
    }

    private PanelFeatureState getPanelState(int i, boolean flag)
    {
        PanelFeatureState apanelfeaturestate[];
label0:
        {
            PanelFeatureState apanelfeaturestate1[] = mPanels;
            if (apanelfeaturestate1 != null)
            {
                apanelfeaturestate = apanelfeaturestate1;
                if (apanelfeaturestate1.length > i)
                {
                    break label0;
                }
            }
            PanelFeatureState apanelfeaturestate2[] = new PanelFeatureState[i + 1];
            if (apanelfeaturestate1 != null)
            {
                System.arraycopy(apanelfeaturestate1, 0, apanelfeaturestate2, 0, apanelfeaturestate1.length);
            }
            apanelfeaturestate = apanelfeaturestate2;
            mPanels = apanelfeaturestate2;
        }
        PanelFeatureState panelfeaturestate1 = apanelfeaturestate[i];
        PanelFeatureState panelfeaturestate = panelfeaturestate1;
        if (panelfeaturestate1 == null)
        {
            panelfeaturestate = new PanelFeatureState(i);
            apanelfeaturestate[i] = panelfeaturestate;
        }
        return panelfeaturestate;
    }

    private boolean initializePanelContent(PanelFeatureState panelfeaturestate)
    {
        if (panelfeaturestate.createdPanelView != null)
        {
            panelfeaturestate.shownPanelView = panelfeaturestate.createdPanelView;
        } else
        {
            if (panelfeaturestate.menu == null)
            {
                return false;
            }
            if (mPanelMenuPresenterCallback == null)
            {
                mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
            }
            panelfeaturestate.shownPanelView = (View)panelfeaturestate.getListMenuView(mPanelMenuPresenterCallback);
            if (panelfeaturestate.shownPanelView == null)
            {
                return false;
            }
        }
        return true;
    }

    private boolean initializePanelDecor(PanelFeatureState panelfeaturestate)
    {
        panelfeaturestate.setStyle(getActionBarThemedContext());
        panelfeaturestate.decorView = new ListMenuDecorView(panelfeaturestate.listPresenterContext);
        panelfeaturestate.gravity = 81;
        return true;
    }

    private boolean initializePanelMenu(PanelFeatureState panelfeaturestate)
    {
label0:
        {
            Context context = mContext;
            Object obj;
            if (panelfeaturestate.featureId != 0)
            {
                obj = context;
                if (panelfeaturestate.featureId != 108)
                {
                    break label0;
                }
            }
            obj = context;
            if (mDecorContentParent != null)
            {
                TypedValue typedvalue = new TypedValue();
                android.content.res.Resources.Theme theme = context.getTheme();
                theme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarTheme, typedvalue, true);
                obj = null;
                Object obj1;
                if (typedvalue.resourceId != 0)
                {
                    obj = context.getResources().newTheme();
                    ((android.content.res.Resources.Theme) (obj)).setTo(theme);
                    ((android.content.res.Resources.Theme) (obj)).applyStyle(typedvalue.resourceId, true);
                    ((android.content.res.Resources.Theme) (obj)).resolveAttribute(android.support.v7.appcompat.R.attr.actionBarWidgetTheme, typedvalue, true);
                } else
                {
                    theme.resolveAttribute(android.support.v7.appcompat.R.attr.actionBarWidgetTheme, typedvalue, true);
                }
                obj1 = obj;
                if (typedvalue.resourceId != 0)
                {
                    obj1 = obj;
                    if (obj == null)
                    {
                        obj1 = context.getResources().newTheme();
                        ((android.content.res.Resources.Theme) (obj1)).setTo(theme);
                    }
                    ((android.content.res.Resources.Theme) (obj1)).applyStyle(typedvalue.resourceId, true);
                }
                obj = context;
                if (obj1 != null)
                {
                    obj = new ContextThemeWrapper(context, 0);
                    ((Context) (obj)).getTheme().setTo(((android.content.res.Resources.Theme) (obj1)));
                }
            }
        }
        obj = new MenuBuilder(((Context) (obj)));
        ((MenuBuilder) (obj)).setCallback(this);
        panelfeaturestate.setMenu(((MenuBuilder) (obj)));
        return true;
    }

    private void invalidatePanelMenu(int i)
    {
        mInvalidatePanelMenuFeatures = mInvalidatePanelMenuFeatures | 1 << i;
        if (!mInvalidatePanelMenuPosted && mWindowDecor != null)
        {
            ViewCompat.postOnAnimation(mWindowDecor, mInvalidatePanelMenuRunnable);
            mInvalidatePanelMenuPosted = true;
        }
    }

    private boolean onKeyDownPanel(int i, KeyEvent keyevent)
    {
        if (keyevent.getRepeatCount() == 0)
        {
            PanelFeatureState panelfeaturestate = getPanelState(i, true);
            if (!panelfeaturestate.isOpen)
            {
                return preparePanel(panelfeaturestate, keyevent);
            }
        }
        return false;
    }

    private boolean onKeyUpPanel(int i, KeyEvent keyevent)
    {
        if (mActionMode == null) goto _L2; else goto _L1
_L1:
        boolean flag1 = false;
_L5:
        return flag1;
_L2:
        PanelFeatureState panelfeaturestate;
        boolean flag3;
        flag3 = false;
        panelfeaturestate = getPanelState(i, true);
        if (i != 0 || mDecorContentParent == null || !mDecorContentParent.canShowOverflowMenu() || ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(mContext))) goto _L4; else goto _L3
_L3:
        boolean flag;
        if (!mDecorContentParent.isOverflowMenuShowing())
        {
            flag = flag3;
            if (!isDestroyed())
            {
                flag = flag3;
                if (preparePanel(panelfeaturestate, keyevent))
                {
                    flag = mDecorContentParent.showOverflowMenu();
                }
            }
        } else
        {
            flag = mDecorContentParent.hideOverflowMenu();
        }
_L6:
        flag1 = flag;
        if (flag)
        {
            keyevent = (AudioManager)mContext.getSystemService("audio");
            boolean flag2;
            if (keyevent != null)
            {
                keyevent.playSoundEffect(0);
                return flag;
            } else
            {
                Log.w("AppCompatDelegate", "Couldn't get audio manager");
                return flag;
            }
        }
        if (true) goto _L5; else goto _L4
_L4:
        if (panelfeaturestate.isOpen || panelfeaturestate.isHandled)
        {
            flag = panelfeaturestate.isOpen;
            closePanel(panelfeaturestate, true);
        } else
        {
            flag = flag3;
            if (panelfeaturestate.isPrepared)
            {
                flag2 = true;
                if (panelfeaturestate.refreshMenuContent)
                {
                    panelfeaturestate.isPrepared = false;
                    flag2 = preparePanel(panelfeaturestate, keyevent);
                }
                flag = flag3;
                if (flag2)
                {
                    openPanel(panelfeaturestate, keyevent);
                    flag = true;
                }
            }
        }
          goto _L6
    }

    private void openPanel(PanelFeatureState panelfeaturestate, KeyEvent keyevent)
    {
        if (!panelfeaturestate.isOpen && !isDestroyed()) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if (panelfeaturestate.featureId != 0)
        {
            break; /* Loop/switch isn't completed */
        }
        Object obj = mContext;
        boolean flag;
        boolean flag1;
        if ((((Context) (obj)).getResources().getConfiguration().screenLayout & 0xf) == 4)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (((Context) (obj)).getApplicationInfo().targetSdkVersion >= 11)
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        if (flag && flag1) goto _L1; else goto _L3
_L3:
        WindowManager windowmanager;
        obj = getWindowCallback();
        if (obj != null && !((android.view.Window.Callback) (obj)).onMenuOpened(panelfeaturestate.featureId, panelfeaturestate.menu))
        {
            closePanel(panelfeaturestate, true);
            return;
        }
        windowmanager = (WindowManager)mContext.getSystemService("window");
        if (windowmanager == null || !preparePanel(panelfeaturestate, keyevent)) goto _L1; else goto _L4
_L4:
        byte byte0;
        byte0 = -2;
        if (panelfeaturestate.decorView != null && !panelfeaturestate.refreshDecorView)
        {
            break MISSING_BLOCK_LABEL_404;
        }
        if (panelfeaturestate.decorView != null) goto _L6; else goto _L5
_L5:
        if (!initializePanelDecor(panelfeaturestate) || panelfeaturestate.decorView == null) goto _L1; else goto _L7
_L7:
        if (!initializePanelContent(panelfeaturestate) || !panelfeaturestate.hasPanelItems()) goto _L1; else goto _L8
_L8:
        int i;
        Object obj1 = panelfeaturestate.shownPanelView.getLayoutParams();
        keyevent = ((KeyEvent) (obj1));
        if (obj1 == null)
        {
            keyevent = new android.view.ViewGroup.LayoutParams(-2, -2);
        }
        i = panelfeaturestate.background;
        panelfeaturestate.decorView.setBackgroundResource(i);
        obj1 = panelfeaturestate.shownPanelView.getParent();
        if (obj1 != null && (obj1 instanceof ViewGroup))
        {
            ((ViewGroup)obj1).removeView(panelfeaturestate.shownPanelView);
        }
        panelfeaturestate.decorView.addView(panelfeaturestate.shownPanelView, keyevent);
        i = byte0;
        if (!panelfeaturestate.shownPanelView.hasFocus())
        {
            panelfeaturestate.shownPanelView.requestFocus();
            i = byte0;
        }
_L9:
        panelfeaturestate.isHandled = false;
        keyevent = new android.view.WindowManager.LayoutParams(i, -2, panelfeaturestate.x, panelfeaturestate.y, 1002, 0x820000, -3);
        keyevent.gravity = panelfeaturestate.gravity;
        keyevent.windowAnimations = panelfeaturestate.windowAnimations;
        windowmanager.addView(panelfeaturestate.decorView, keyevent);
        panelfeaturestate.isOpen = true;
        return;
_L6:
        if (panelfeaturestate.refreshDecorView && panelfeaturestate.decorView.getChildCount() > 0)
        {
            panelfeaturestate.decorView.removeAllViews();
        }
          goto _L7
        i = byte0;
        if (panelfeaturestate.createdPanelView != null)
        {
            keyevent = panelfeaturestate.createdPanelView.getLayoutParams();
            i = byte0;
            if (keyevent != null)
            {
                i = byte0;
                if (((android.view.ViewGroup.LayoutParams) (keyevent)).width == -1)
                {
                    i = -1;
                }
            }
        }
          goto _L9
    }

    private boolean performPanelShortcut(PanelFeatureState panelfeaturestate, int i, KeyEvent keyevent, int j)
    {
        if (!keyevent.isSystem()) goto _L2; else goto _L1
_L1:
        boolean flag1 = false;
_L4:
        return flag1;
_L2:
        boolean flag;
label0:
        {
            flag1 = false;
            if (!panelfeaturestate.isPrepared)
            {
                flag = flag1;
                if (!preparePanel(panelfeaturestate, keyevent))
                {
                    break label0;
                }
            }
            flag = flag1;
            if (panelfeaturestate.menu != null)
            {
                flag = panelfeaturestate.menu.performShortcut(i, keyevent, j);
            }
        }
        flag1 = flag;
        if (flag)
        {
            flag1 = flag;
            if ((j & 1) == 0)
            {
                flag1 = flag;
                if (mDecorContentParent == null)
                {
                    closePanel(panelfeaturestate, true);
                    return flag;
                }
            }
        }
        if (true) goto _L4; else goto _L3
_L3:
    }

    private boolean preparePanel(PanelFeatureState panelfeaturestate, KeyEvent keyevent)
    {
        if (!isDestroyed()) goto _L2; else goto _L1
_L1:
        return false;
_L2:
        android.view.Window.Callback callback;
        int i;
        if (panelfeaturestate.isPrepared)
        {
            return true;
        }
        if (mPreparedPanel != null && mPreparedPanel != panelfeaturestate)
        {
            closePanel(mPreparedPanel, false);
        }
        callback = getWindowCallback();
        if (callback != null)
        {
            panelfeaturestate.createdPanelView = callback.onCreatePanelView(panelfeaturestate.featureId);
        }
        if (panelfeaturestate.featureId == 0 || panelfeaturestate.featureId == 108)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        if (i && mDecorContentParent != null)
        {
            mDecorContentParent.setMenuPrepared();
        }
        if (panelfeaturestate.createdPanelView != null || i && (peekSupportActionBar() instanceof ToolbarActionBar))
        {
            break MISSING_BLOCK_LABEL_412;
        }
        if (panelfeaturestate.menu != null && !panelfeaturestate.refreshMenuContent)
        {
            break MISSING_BLOCK_LABEL_280;
        }
        if (panelfeaturestate.menu == null && (!initializePanelMenu(panelfeaturestate) || panelfeaturestate.menu == null))
        {
            continue; /* Loop/switch isn't completed */
        }
        if (i && mDecorContentParent != null)
        {
            if (mActionMenuPresenterCallback == null)
            {
                mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
            }
            mDecorContentParent.setMenu(panelfeaturestate.menu, mActionMenuPresenterCallback);
        }
        panelfeaturestate.menu.stopDispatchingItemsChanged();
        if (callback.onCreatePanelMenu(panelfeaturestate.featureId, panelfeaturestate.menu))
        {
            break; /* Loop/switch isn't completed */
        }
        panelfeaturestate.setMenu(null);
        if (i && mDecorContentParent != null)
        {
            mDecorContentParent.setMenu(null, mActionMenuPresenterCallback);
            return false;
        }
        if (true) goto _L1; else goto _L3
_L3:
        panelfeaturestate.refreshMenuContent = false;
        panelfeaturestate.menu.stopDispatchingItemsChanged();
        if (panelfeaturestate.frozenActionViewState != null)
        {
            panelfeaturestate.menu.restoreActionViewStates(panelfeaturestate.frozenActionViewState);
            panelfeaturestate.frozenActionViewState = null;
        }
        if (!callback.onPreparePanel(0, panelfeaturestate.createdPanelView, panelfeaturestate.menu))
        {
            if (i && mDecorContentParent != null)
            {
                mDecorContentParent.setMenu(null, mActionMenuPresenterCallback);
            }
            panelfeaturestate.menu.startDispatchingItemsChanged();
            return false;
        }
        boolean flag;
        if (keyevent != null)
        {
            i = keyevent.getDeviceId();
        } else
        {
            i = -1;
        }
        if (KeyCharacterMap.load(i).getKeyboardType() != 1)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        panelfeaturestate.qwertyMode = flag;
        panelfeaturestate.menu.setQwertyMode(panelfeaturestate.qwertyMode);
        panelfeaturestate.menu.startDispatchingItemsChanged();
        panelfeaturestate.isPrepared = true;
        panelfeaturestate.isHandled = false;
        mPreparedPanel = panelfeaturestate;
        return true;
    }

    private void reopenMenu(MenuBuilder menubuilder, boolean flag)
    {
        if (mDecorContentParent == null || !mDecorContentParent.canShowOverflowMenu() || ViewConfigurationCompat.hasPermanentMenuKey(ViewConfiguration.get(mContext)) && !mDecorContentParent.isOverflowMenuShowPending())
        {
            break MISSING_BLOCK_LABEL_211;
        }
        menubuilder = getWindowCallback();
        if (mDecorContentParent.isOverflowMenuShowing() && flag) goto _L2; else goto _L1
_L1:
        if (menubuilder != null && !isDestroyed())
        {
            if (mInvalidatePanelMenuPosted && (mInvalidatePanelMenuFeatures & 1) != 0)
            {
                mWindowDecor.removeCallbacks(mInvalidatePanelMenuRunnable);
                mInvalidatePanelMenuRunnable.run();
            }
            PanelFeatureState panelfeaturestate = getPanelState(0, true);
            if (panelfeaturestate.menu != null && !panelfeaturestate.refreshMenuContent && menubuilder.onPreparePanel(0, panelfeaturestate.createdPanelView, panelfeaturestate.menu))
            {
                menubuilder.onMenuOpened(108, panelfeaturestate.menu);
                mDecorContentParent.showOverflowMenu();
            }
        }
_L4:
        return;
_L2:
        mDecorContentParent.hideOverflowMenu();
        if (isDestroyed()) goto _L4; else goto _L3
_L3:
        menubuilder.onPanelClosed(108, getPanelState(0, true).menu);
        return;
        menubuilder = getPanelState(0, true);
        menubuilder.refreshDecorView = true;
        closePanel(menubuilder, false);
        openPanel(menubuilder, null);
        return;
    }

    private int sanitizeWindowFeatureId(int i)
    {
        int j;
        if (i == 8)
        {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            j = 108;
        } else
        {
            j = i;
            if (i == 9)
            {
                Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
                return 109;
            }
        }
        return j;
    }

    private boolean shouldInheritContext(ViewParent viewparent)
    {
        ViewParent viewparent1 = viewparent;
        if (viewparent == null)
        {
            return false;
        }
        do
        {
            if (viewparent1 == null)
            {
                return true;
            }
            if (viewparent1 != mWindowDecor && (viewparent1 instanceof View) && !ViewCompat.isAttachedToWindow((View)viewparent1))
            {
                viewparent1 = viewparent1.getParent();
            } else
            {
                return false;
            }
        } while (true);
    }

    private void throwFeatureRequestIfSubDecorInstalled()
    {
        if (mSubDecorInstalled)
        {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        } else
        {
            return;
        }
    }

    private int updateStatusGuard(int i)
    {
        boolean flag3 = false;
        boolean flag = false;
        boolean flag4 = false;
        boolean flag2 = flag;
        int j = i;
        if (mActionModeView != null)
        {
            flag2 = flag;
            j = i;
            if (mActionModeView.getLayoutParams() instanceof android.view.ViewGroup.MarginLayoutParams)
            {
                Object obj = (android.view.ViewGroup.MarginLayoutParams)mActionModeView.getLayoutParams();
                int k = 0;
                j = 0;
                boolean flag1;
                int l;
                if (mActionModeView.isShown())
                {
                    if (mTempRect1 == null)
                    {
                        mTempRect1 = new Rect();
                        mTempRect2 = new Rect();
                    }
                    Rect rect = mTempRect1;
                    Rect rect1 = mTempRect2;
                    rect.set(0, i, 0, 0);
                    ViewUtils.computeFitSystemWindows(mSubDecor, rect, rect1);
                    if (rect1.top == 0)
                    {
                        k = i;
                    } else
                    {
                        k = 0;
                    }
                    if (((android.view.ViewGroup.MarginLayoutParams) (obj)).topMargin != k)
                    {
                        k = 1;
                        obj.topMargin = i;
                        if (mStatusGuard == null)
                        {
                            mStatusGuard = new View(mContext);
                            mStatusGuard.setBackgroundColor(mContext.getResources().getColor(android.support.v7.appcompat.R.color.abc_input_method_navigation_guard));
                            mSubDecor.addView(mStatusGuard, -1, new android.view.ViewGroup.LayoutParams(-1, i));
                            j = k;
                        } else
                        {
                            android.view.ViewGroup.LayoutParams layoutparams = mStatusGuard.getLayoutParams();
                            j = k;
                            if (layoutparams.height != i)
                            {
                                layoutparams.height = i;
                                mStatusGuard.setLayoutParams(layoutparams);
                                j = k;
                            }
                        }
                    }
                    if (mStatusGuard != null)
                    {
                        flag2 = true;
                    } else
                    {
                        flag2 = false;
                    }
                    k = j;
                    flag1 = flag2;
                    l = i;
                    if (!mOverlayActionMode)
                    {
                        k = j;
                        flag1 = flag2;
                        l = i;
                        if (flag2)
                        {
                            l = 0;
                            flag1 = flag2;
                            k = j;
                        }
                    }
                } else
                {
                    flag1 = flag4;
                    l = i;
                    if (((android.view.ViewGroup.MarginLayoutParams) (obj)).topMargin != 0)
                    {
                        k = 1;
                        obj.topMargin = 0;
                        flag1 = flag4;
                        l = i;
                    }
                }
                flag2 = flag1;
                j = l;
                if (k != 0)
                {
                    mActionModeView.setLayoutParams(((android.view.ViewGroup.LayoutParams) (obj)));
                    j = l;
                    flag2 = flag1;
                }
            }
        }
        if (mStatusGuard != null)
        {
            obj = mStatusGuard;
            if (flag2)
            {
                i = ((flag3) ? 1 : 0);
            } else
            {
                i = 8;
            }
            ((View) (obj)).setVisibility(i);
        }
        return j;
    }

    public void addContentView(View view, android.view.ViewGroup.LayoutParams layoutparams)
    {
        ensureSubDecor();
        ((ViewGroup)mSubDecor.findViewById(0x1020002)).addView(view, layoutparams);
        mOriginalWindowCallback.onContentChanged();
    }

    View callActivityOnCreateView(View view, String s, Context context, AttributeSet attributeset)
    {
        if (mOriginalWindowCallback instanceof android.view.LayoutInflater.Factory)
        {
            view = ((android.view.LayoutInflater.Factory)mOriginalWindowCallback).onCreateView(s, context, attributeset);
            if (view != null)
            {
                return view;
            }
        }
        return null;
    }

    public View createView(View view, String s, Context context, AttributeSet attributeset)
    {
        boolean flag;
        boolean flag1;
        if (android.os.Build.VERSION.SDK_INT < 21)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (mAppCompatViewInflater == null)
        {
            mAppCompatViewInflater = new AppCompatViewInflater();
        }
        if (flag && mSubDecorInstalled && shouldInheritContext((ViewParent)view))
        {
            flag1 = true;
        } else
        {
            flag1 = false;
        }
        return mAppCompatViewInflater.createView(view, s, context, attributeset, flag1, flag, true);
    }

    boolean dispatchKeyEvent(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 82 && mOriginalWindowCallback.dispatchKeyEvent(keyevent))
        {
            return true;
        }
        int i = keyevent.getKeyCode();
        boolean flag;
        if (keyevent.getAction() == 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        if (flag)
        {
            return onKeyDown(i, keyevent);
        } else
        {
            return onKeyUp(i, keyevent);
        }
    }

    ViewGroup getSubDecor()
    {
        return mSubDecor;
    }

    public boolean hasWindowFeature(int i)
    {
        i = sanitizeWindowFeatureId(i);
        switch (i)
        {
        default:
            return mWindow.hasFeature(i);

        case 108: // 'l'
            return mHasActionBar;

        case 109: // 'm'
            return mOverlayActionBar;

        case 10: // '\n'
            return mOverlayActionMode;

        case 2: // '\002'
            return mFeatureProgress;

        case 5: // '\005'
            return mFeatureIndeterminateProgress;

        case 1: // '\001'
            return mWindowNoTitle;
        }
    }

    public void initWindowDecorActionBar()
    {
        ensureSubDecor();
        if (mHasActionBar && mActionBar == null)
        {
            if (mOriginalWindowCallback instanceof Activity)
            {
                mActionBar = new WindowDecorActionBar((Activity)mOriginalWindowCallback, mOverlayActionBar);
            } else
            if (mOriginalWindowCallback instanceof Dialog)
            {
                mActionBar = new WindowDecorActionBar((Dialog)mOriginalWindowCallback);
            }
            if (mActionBar != null)
            {
                mActionBar.setDefaultDisplayHomeAsUpEnabled(mEnableDefaultActionBarUp);
                return;
            }
        }
    }

    public void installViewFactory()
    {
        LayoutInflater layoutinflater = LayoutInflater.from(mContext);
        if (layoutinflater.getFactory() == null)
        {
            LayoutInflaterCompat.setFactory(layoutinflater, this);
            return;
        } else
        {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
            return;
        }
    }

    public void invalidateOptionsMenu()
    {
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null && actionbar.invalidateOptionsMenu())
        {
            return;
        } else
        {
            invalidatePanelMenu(0);
            return;
        }
    }

    boolean onBackPressed()
    {
        if (mActionMode != null)
        {
            mActionMode.finish();
        } else
        {
            ActionBar actionbar = getSupportActionBar();
            if (actionbar == null || !actionbar.collapseActionView())
            {
                return false;
            }
        }
        return true;
    }

    public void onConfigurationChanged(Configuration configuration)
    {
        if (mHasActionBar && mSubDecorInstalled)
        {
            ActionBar actionbar = getSupportActionBar();
            if (actionbar != null)
            {
                actionbar.onConfigurationChanged(configuration);
            }
        }
    }

    public void onCreate(Bundle bundle)
    {
label0:
        {
            mWindowDecor = (ViewGroup)mWindow.getDecorView();
            if ((mOriginalWindowCallback instanceof Activity) && NavUtils.getParentActivityName((Activity)mOriginalWindowCallback) != null)
            {
                bundle = peekSupportActionBar();
                if (bundle != null)
                {
                    break label0;
                }
                mEnableDefaultActionBarUp = true;
            }
            return;
        }
        bundle.setDefaultDisplayHomeAsUpEnabled(true);
    }

    public final View onCreateView(View view, String s, Context context, AttributeSet attributeset)
    {
        View view1 = callActivityOnCreateView(view, s, context, attributeset);
        if (view1 != null)
        {
            return view1;
        } else
        {
            return createView(view, s, context, attributeset);
        }
    }

    boolean onKeyDown(int i, KeyEvent keyevent)
    {
        i;
        JVM INSTR tableswitch 82 82: default 20
    //                   82 37;
           goto _L1 _L2
_L1:
        if (android.os.Build.VERSION.SDK_INT < 11)
        {
            onKeyShortcut(i, keyevent);
        }
        return false;
_L2:
        onKeyDownPanel(0, keyevent);
        if (true) goto _L1; else goto _L3
_L3:
    }

    boolean onKeyShortcut(int i, KeyEvent keyevent)
    {
        ActionBar actionbar = getSupportActionBar();
        if (actionbar == null || !actionbar.onKeyShortcut(i, keyevent)) goto _L2; else goto _L1
_L1:
        return true;
_L2:
        if (mPreparedPanel == null || !performPanelShortcut(mPreparedPanel, keyevent.getKeyCode(), keyevent, 1))
        {
            break; /* Loop/switch isn't completed */
        }
        if (mPreparedPanel != null)
        {
            mPreparedPanel.isHandled = true;
            return true;
        }
        if (true) goto _L1; else goto _L3
_L3:
        boolean flag;
        if (mPreparedPanel != null)
        {
            break; /* Loop/switch isn't completed */
        }
        PanelFeatureState panelfeaturestate = getPanelState(0, true);
        preparePanel(panelfeaturestate, keyevent);
        flag = performPanelShortcut(panelfeaturestate, keyevent.getKeyCode(), keyevent, 1);
        panelfeaturestate.isPrepared = false;
        if (flag) goto _L1; else goto _L4
_L4:
        return false;
    }

    boolean onKeyUp(int i, KeyEvent keyevent)
    {
        i;
        JVM INSTR lookupswitch 2: default 28
    //                   4: 39
    //                   82: 30;
           goto _L1 _L2 _L3
_L1:
        return false;
_L3:
        onKeyUpPanel(0, keyevent);
        return true;
_L2:
        keyevent = getPanelState(0, false);
        if (keyevent != null && ((PanelFeatureState) (keyevent)).isOpen)
        {
            closePanel(keyevent, true);
            return true;
        }
        if (onBackPressed())
        {
            return true;
        }
        if (true) goto _L1; else goto _L4
_L4:
    }

    public boolean onMenuItemSelected(MenuBuilder menubuilder, MenuItem menuitem)
    {
        android.view.Window.Callback callback = getWindowCallback();
        if (callback != null && !isDestroyed())
        {
            menubuilder = findMenuPanel(menubuilder.getRootMenu());
            if (menubuilder != null)
            {
                return callback.onMenuItemSelected(((PanelFeatureState) (menubuilder)).featureId, menuitem);
            }
        }
        return false;
    }

    public void onMenuModeChange(MenuBuilder menubuilder)
    {
        reopenMenu(menubuilder, true);
    }

    boolean onMenuOpened(int i, Menu menu)
    {
        if (i == 108)
        {
            menu = getSupportActionBar();
            if (menu != null)
            {
                menu.dispatchMenuVisibilityChanged(true);
            }
            return true;
        } else
        {
            return false;
        }
    }

    void onPanelClosed(int i, Menu menu)
    {
        if (i == 108)
        {
            menu = getSupportActionBar();
            if (menu != null)
            {
                menu.dispatchMenuVisibilityChanged(false);
            }
        } else
        if (i == 0)
        {
            menu = getPanelState(i, true);
            if (((PanelFeatureState) (menu)).isOpen)
            {
                closePanel(menu, false);
                return;
            }
        }
    }

    public void onPostCreate(Bundle bundle)
    {
        ensureSubDecor();
    }

    public void onPostResume()
    {
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null)
        {
            actionbar.setShowHideAnimationEnabled(true);
        }
    }

    public void onStop()
    {
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null)
        {
            actionbar.setShowHideAnimationEnabled(false);
        }
    }

    void onSubDecorInstalled(ViewGroup viewgroup)
    {
    }

    void onTitleChanged(CharSequence charsequence)
    {
        if (mDecorContentParent != null)
        {
            mDecorContentParent.setWindowTitle(charsequence);
        } else
        {
            if (peekSupportActionBar() != null)
            {
                peekSupportActionBar().setWindowTitle(charsequence);
                return;
            }
            if (mTitleView != null)
            {
                mTitleView.setText(charsequence);
                return;
            }
        }
    }

    public boolean requestWindowFeature(int i)
    {
        i = sanitizeWindowFeatureId(i);
        if (mWindowNoTitle && i == 108)
        {
            return false;
        }
        if (mHasActionBar && i == 1)
        {
            mHasActionBar = false;
        }
        switch (i)
        {
        default:
            return mWindow.requestFeature(i);

        case 108: // 'l'
            throwFeatureRequestIfSubDecorInstalled();
            mHasActionBar = true;
            return true;

        case 109: // 'm'
            throwFeatureRequestIfSubDecorInstalled();
            mOverlayActionBar = true;
            return true;

        case 10: // '\n'
            throwFeatureRequestIfSubDecorInstalled();
            mOverlayActionMode = true;
            return true;

        case 2: // '\002'
            throwFeatureRequestIfSubDecorInstalled();
            mFeatureProgress = true;
            return true;

        case 5: // '\005'
            throwFeatureRequestIfSubDecorInstalled();
            mFeatureIndeterminateProgress = true;
            return true;

        case 1: // '\001'
            throwFeatureRequestIfSubDecorInstalled();
            mWindowNoTitle = true;
            return true;
        }
    }

    public void setContentView(int i)
    {
        ensureSubDecor();
        ViewGroup viewgroup = (ViewGroup)mSubDecor.findViewById(0x1020002);
        viewgroup.removeAllViews();
        LayoutInflater.from(mContext).inflate(i, viewgroup);
        mOriginalWindowCallback.onContentChanged();
    }

    public void setContentView(View view)
    {
        ensureSubDecor();
        ViewGroup viewgroup = (ViewGroup)mSubDecor.findViewById(0x1020002);
        viewgroup.removeAllViews();
        viewgroup.addView(view);
        mOriginalWindowCallback.onContentChanged();
    }

    public void setContentView(View view, android.view.ViewGroup.LayoutParams layoutparams)
    {
        ensureSubDecor();
        ViewGroup viewgroup = (ViewGroup)mSubDecor.findViewById(0x1020002);
        viewgroup.removeAllViews();
        viewgroup.addView(view, layoutparams);
        mOriginalWindowCallback.onContentChanged();
    }

    public void setSupportActionBar(Toolbar toolbar)
    {
        if (!(mOriginalWindowCallback instanceof Activity))
        {
            return;
        }
        if (getSupportActionBar() instanceof WindowDecorActionBar)
        {
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        } else
        {
            mMenuInflater = null;
            toolbar = new ToolbarActionBar(toolbar, ((Activity)mContext).getTitle(), mAppCompatWindowCallback);
            mActionBar = toolbar;
            mWindow.setCallback(toolbar.getWrappedWindowCallback());
            toolbar.invalidateOptionsMenu();
            return;
        }
    }

    public ActionMode startSupportActionMode(android.support.v7.view.ActionMode.Callback callback)
    {
        if (callback == null)
        {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        if (mActionMode != null)
        {
            mActionMode.finish();
        }
        callback = new ActionModeCallbackWrapperV7(callback);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null)
        {
            mActionMode = actionbar.startActionMode(callback);
            if (mActionMode != null && mAppCompatCallback != null)
            {
                mAppCompatCallback.onSupportActionModeStarted(mActionMode);
            }
        }
        if (mActionMode == null)
        {
            mActionMode = startSupportActionModeFromWindow(callback);
        }
        return mActionMode;
    }

    ActionMode startSupportActionModeFromWindow(android.support.v7.view.ActionMode.Callback callback)
    {
        endOnGoingFadeAnimation();
        if (mActionMode != null)
        {
            mActionMode.finish();
        }
        ActionModeCallbackWrapperV7 actionmodecallbackwrapperv7 = new ActionModeCallbackWrapperV7(callback);
        Object obj1 = null;
        Object obj = obj1;
        if (mAppCompatCallback != null)
        {
            obj = obj1;
            android.content.res.Resources.Theme theme;
            int i;
            boolean flag;
            if (!isDestroyed())
            {
                try
                {
                    obj = mAppCompatCallback.onWindowStartingSupportActionMode(actionmodecallbackwrapperv7);
                }
                catch (AbstractMethodError abstractmethoderror)
                {
                    abstractmethoderror = ((AbstractMethodError) (obj1));
                }
            }
        }
        if (obj == null) goto _L2; else goto _L1
_L1:
        mActionMode = ((ActionMode) (obj));
_L4:
        if (mActionMode != null && mAppCompatCallback != null)
        {
            mAppCompatCallback.onSupportActionModeStarted(mActionMode);
        }
        return mActionMode;
_L2:
        if (mActionModeView == null)
        {
            if (mIsFloating)
            {
                obj1 = new TypedValue();
                obj = mContext.getTheme();
                ((android.content.res.Resources.Theme) (obj)).resolveAttribute(android.support.v7.appcompat.R.attr.actionBarTheme, ((TypedValue) (obj1)), true);
                if (((TypedValue) (obj1)).resourceId != 0)
                {
                    theme = mContext.getResources().newTheme();
                    theme.setTo(((android.content.res.Resources.Theme) (obj)));
                    theme.applyStyle(((TypedValue) (obj1)).resourceId, true);
                    obj = new ContextThemeWrapper(mContext, 0);
                    ((Context) (obj)).getTheme().setTo(theme);
                } else
                {
                    obj = mContext;
                }
                mActionModeView = new ActionBarContextView(((Context) (obj)));
                mActionModePopup = new PopupWindow(((Context) (obj)), null, android.support.v7.appcompat.R.attr.actionModePopupWindowStyle);
                PopupWindowCompat.setWindowLayoutType(mActionModePopup, 2);
                mActionModePopup.setContentView(mActionModeView);
                mActionModePopup.setWidth(-1);
                ((Context) (obj)).getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, ((TypedValue) (obj1)), true);
                i = TypedValue.complexToDimensionPixelSize(((TypedValue) (obj1)).data, ((Context) (obj)).getResources().getDisplayMetrics());
                mActionModeView.setContentHeight(i);
                mActionModePopup.setHeight(-2);
                mShowActionModePopup = new Runnable() {

                    final AppCompatDelegateImplV7 this$0;

                    public void run()
                    {
                        mActionModePopup.showAtLocation(mActionModeView, 55, 0, 0);
                        endOnGoingFadeAnimation();
                        ViewCompat.setAlpha(mActionModeView, 0.0F);
                        mFadeAnim = ViewCompat.animate(mActionModeView).alpha(1.0F);
                        mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() {

                            final _cls4 this$1;

                            public void onAnimationEnd(View view)
                            {
                                ViewCompat.setAlpha(mActionModeView, 1.0F);
                                mFadeAnim.setListener(null);
                                mFadeAnim = null;
                            }

                            public void onAnimationStart(View view)
                            {
                                mActionModeView.setVisibility(0);
                            }

            
            {
                this$1 = _cls4.this;
                super();
            }
                        });
                    }

            
            {
                this$0 = AppCompatDelegateImplV7.this;
                super();
            }
                };
            } else
            {
                obj = (ViewStubCompat)mSubDecor.findViewById(android.support.v7.appcompat.R.id.action_mode_bar_stub);
                if (obj != null)
                {
                    ((ViewStubCompat) (obj)).setLayoutInflater(LayoutInflater.from(getActionBarThemedContext()));
                    mActionModeView = (ActionBarContextView)((ViewStubCompat) (obj)).inflate();
                }
            }
        }
        if (mActionModeView != null)
        {
            endOnGoingFadeAnimation();
            mActionModeView.killMode();
            obj = mActionModeView.getContext();
            obj1 = mActionModeView;
            if (mActionModePopup == null)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            obj = new StandaloneActionMode(((Context) (obj)), ((ActionBarContextView) (obj1)), actionmodecallbackwrapperv7, flag);
            if (callback.onCreateActionMode(((ActionMode) (obj)), ((ActionMode) (obj)).getMenu()))
            {
                ((ActionMode) (obj)).invalidate();
                mActionModeView.initForMode(((ActionMode) (obj)));
                mActionMode = ((ActionMode) (obj));
                ViewCompat.setAlpha(mActionModeView, 0.0F);
                mFadeAnim = ViewCompat.animate(mActionModeView).alpha(1.0F);
                mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() {

                    final AppCompatDelegateImplV7 this$0;

                    public void onAnimationEnd(View view)
                    {
                        ViewCompat.setAlpha(mActionModeView, 1.0F);
                        mFadeAnim.setListener(null);
                        mFadeAnim = null;
                    }

                    public void onAnimationStart(View view)
                    {
                        mActionModeView.setVisibility(0);
                        mActionModeView.sendAccessibilityEvent(32);
                        if (mActionModeView.getParent() != null)
                        {
                            ViewCompat.requestApplyInsets((View)mActionModeView.getParent());
                        }
                    }

            
            {
                this$0 = AppCompatDelegateImplV7.this;
                super();
            }
                });
                if (mActionModePopup != null)
                {
                    mWindow.getDecorView().post(mShowActionModePopup);
                }
            } else
            {
                mActionMode = null;
            }
        }
        continue; /* Loop/switch isn't completed */
        if (true) goto _L4; else goto _L3
_L3:
    }



/*
    static int access$002(AppCompatDelegateImplV7 appcompatdelegateimplv7, int i)
    {
        appcompatdelegateimplv7.mInvalidatePanelMenuFeatures = i;
        return i;
    }

*/





/*
    static boolean access$202(AppCompatDelegateImplV7 appcompatdelegateimplv7, boolean flag)
    {
        appcompatdelegateimplv7.mInvalidatePanelMenuPosted = flag;
        return flag;
    }

*/





}

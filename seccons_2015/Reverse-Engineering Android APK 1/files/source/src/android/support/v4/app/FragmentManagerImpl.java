// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Referenced classes of package android.support.v4.app:
//            FragmentManager, FragmentHostCallback, Fragment, BackStackRecord, 
//            LoaderManagerImpl, SuperNotCalledException, FragmentContainer, NoSaveStateFrameLayout, 
//            FragmentManagerState, FragmentState, BackStackState, FragmentController, 
//            FragmentTransaction

final class FragmentManagerImpl extends FragmentManager
    implements LayoutInflaterFactory
{
    static class AnimateOnHWLayerIfNeededListener
        implements android.view.animation.Animation.AnimationListener
    {

        private boolean mShouldRunOnHWLayer;
        private View mView;

        public void onAnimationEnd(Animation animation)
        {
            if (mShouldRunOnHWLayer)
            {
                mView.post(new Runnable() {

                    final AnimateOnHWLayerIfNeededListener this$0;

                    public void run()
                    {
                        ViewCompat.setLayerType(mView, 0, null);
                    }

            
            {
                this$0 = AnimateOnHWLayerIfNeededListener.this;
                super();
            }
                });
            }
        }

        public void onAnimationRepeat(Animation animation)
        {
        }

        public void onAnimationStart(Animation animation)
        {
            mShouldRunOnHWLayer = FragmentManagerImpl.shouldRunOnHWLayer(mView, animation);
            if (mShouldRunOnHWLayer)
            {
                mView.post(new Runnable() {

                    final AnimateOnHWLayerIfNeededListener this$0;

                    public void run()
                    {
                        ViewCompat.setLayerType(mView, 2, null);
                    }

            
            {
                this$0 = AnimateOnHWLayerIfNeededListener.this;
                super();
            }
                });
            }
        }


        public AnimateOnHWLayerIfNeededListener(View view, Animation animation)
        {
            mShouldRunOnHWLayer = false;
            if (view == null || animation == null)
            {
                return;
            } else
            {
                mView = view;
                return;
            }
        }
    }

    static class FragmentTag
    {

        public static final int Fragment[] = {
            0x1010003, 0x10100d0, 0x10100d1
        };
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;


        FragmentTag()
        {
        }
    }


    static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5F);
    static final Interpolator ACCELERATE_QUINT = new AccelerateInterpolator(2.5F);
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5F);
    static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5F);
    static final boolean HONEYCOMB;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    ArrayList mActive;
    ArrayList mAdded;
    ArrayList mAvailBackStackIndices;
    ArrayList mAvailIndices;
    ArrayList mBackStack;
    ArrayList mBackStackChangeListeners;
    ArrayList mBackStackIndices;
    FragmentContainer mContainer;
    FragmentController mController;
    ArrayList mCreatedMenus;
    int mCurState;
    boolean mDestroyed;
    Runnable mExecCommit;
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback mHost;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList mPendingActions;
    SparseArray mStateArray;
    Bundle mStateBundle;
    boolean mStateSaved;
    Runnable mTmpActions[];

    FragmentManagerImpl()
    {
        mCurState = 0;
        mStateBundle = null;
        mStateArray = null;
        mExecCommit = new Runnable() {

            final FragmentManagerImpl this$0;

            public void run()
            {
                execPendingActions();
            }

            
            {
                this$0 = FragmentManagerImpl.this;
                super();
            }
        };
    }

    private void checkStateLoss()
    {
        if (mStateSaved)
        {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (mNoTransactionsBecause != null)
        {
            throw new IllegalStateException((new StringBuilder()).append("Can not perform this action inside of ").append(mNoTransactionsBecause).toString());
        } else
        {
            return;
        }
    }

    static Animation makeFadeAnimation(Context context, float f, float f1)
    {
        context = new AlphaAnimation(f, f1);
        context.setInterpolator(DECELERATE_CUBIC);
        context.setDuration(220L);
        return context;
    }

    static Animation makeOpenCloseAnimation(Context context, float f, float f1, float f2, float f3)
    {
        context = new AnimationSet(false);
        Object obj = new ScaleAnimation(f, f1, f, f1, 1, 0.5F, 1, 0.5F);
        ((ScaleAnimation) (obj)).setInterpolator(DECELERATE_QUINT);
        ((ScaleAnimation) (obj)).setDuration(220L);
        context.addAnimation(((Animation) (obj)));
        obj = new AlphaAnimation(f2, f3);
        ((AlphaAnimation) (obj)).setInterpolator(DECELERATE_CUBIC);
        ((AlphaAnimation) (obj)).setDuration(220L);
        context.addAnimation(((Animation) (obj)));
        return context;
    }

    static boolean modifiesAlpha(Animation animation)
    {
        if (!(animation instanceof AlphaAnimation)) goto _L2; else goto _L1
_L1:
        return true;
_L2:
label0:
        {
            if (!(animation instanceof AnimationSet))
            {
                break label0;
            }
            animation = ((AnimationSet)animation).getAnimations();
            int i = 0;
            do
            {
                if (i >= animation.size())
                {
                    break label0;
                }
                if (animation.get(i) instanceof AlphaAnimation)
                {
                    break;
                }
                i++;
            } while (true);
        }
        if (true) goto _L1; else goto _L3
_L3:
        return false;
    }

    public static int reverseTransit(int i)
    {
        switch (i)
        {
        default:
            return 0;

        case 4097: 
            return 8194;

        case 8194: 
            return 4097;

        case 4099: 
            return 4099;
        }
    }

    private void setHWLayerAnimListenerIfAlpha(View view, Animation animation)
    {
        while (view == null || animation == null || !shouldRunOnHWLayer(view, animation)) 
        {
            return;
        }
        animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(view, animation));
    }

    static boolean shouldRunOnHWLayer(View view, Animation animation)
    {
        return ViewCompat.getLayerType(view) == 0 && ViewCompat.hasOverlappingRendering(view) && modifiesAlpha(animation);
    }

    private void throwException(RuntimeException runtimeexception)
    {
        Log.e("FragmentManager", runtimeexception.getMessage());
        Log.e("FragmentManager", "Activity state:");
        Object obj = new PrintWriter(new LogWriter("FragmentManager"));
        if (mHost != null)
        {
            try
            {
                mHost.onDump("  ", null, ((PrintWriter) (obj)), new String[0]);
            }
            // Misplaced declaration of an exception variable
            catch (Object obj)
            {
                Log.e("FragmentManager", "Failed dumping state", ((Throwable) (obj)));
            }
        } else
        {
            try
            {
                dump("  ", null, ((PrintWriter) (obj)), new String[0]);
            }
            catch (Exception exception)
            {
                Log.e("FragmentManager", "Failed dumping state", exception);
            }
        }
        throw runtimeexception;
    }

    public static int transitToStyleIndex(int i, boolean flag)
    {
        switch (i)
        {
        default:
            return -1;

        case 4097: 
            return !flag ? 2 : 1;

        case 8194: 
            return !flag ? 4 : 3;

        case 4099: 
            break;
        }
        return !flag ? 6 : 5;
    }

    void addBackStackState(BackStackRecord backstackrecord)
    {
        if (mBackStack == null)
        {
            mBackStack = new ArrayList();
        }
        mBackStack.add(backstackrecord);
        reportBackStackChanged();
    }

    public void addFragment(Fragment fragment, boolean flag)
    {
        if (mAdded == null)
        {
            mAdded = new ArrayList();
        }
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("add: ").append(fragment).toString());
        }
        makeActive(fragment);
        if (!fragment.mDetached)
        {
            if (mAdded.contains(fragment))
            {
                throw new IllegalStateException((new StringBuilder()).append("Fragment already added: ").append(fragment).toString());
            }
            mAdded.add(fragment);
            fragment.mAdded = true;
            fragment.mRemoving = false;
            if (fragment.mHasMenu && fragment.mMenuVisible)
            {
                mNeedMenuInvalidate = true;
            }
            if (flag)
            {
                moveToState(fragment);
            }
        }
    }

    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onbackstackchangedlistener)
    {
        if (mBackStackChangeListeners == null)
        {
            mBackStackChangeListeners = new ArrayList();
        }
        mBackStackChangeListeners.add(onbackstackchangedlistener);
    }

    public int allocBackStackIndex(BackStackRecord backstackrecord)
    {
        this;
        JVM INSTR monitorenter ;
        int i;
        if (mAvailBackStackIndices != null && mAvailBackStackIndices.size() > 0)
        {
            break MISSING_BLOCK_LABEL_100;
        }
        if (mBackStackIndices == null)
        {
            mBackStackIndices = new ArrayList();
        }
        i = mBackStackIndices.size();
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("Setting back stack index ").append(i).append(" to ").append(backstackrecord).toString());
        }
        mBackStackIndices.add(backstackrecord);
        this;
        JVM INSTR monitorexit ;
        return i;
        i = ((Integer)mAvailBackStackIndices.remove(mAvailBackStackIndices.size() - 1)).intValue();
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("Adding back stack index ").append(i).append(" with ").append(backstackrecord).toString());
        }
        mBackStackIndices.set(i, backstackrecord);
        this;
        JVM INSTR monitorexit ;
        return i;
        backstackrecord;
        this;
        JVM INSTR monitorexit ;
        throw backstackrecord;
    }

    public void attachController(FragmentHostCallback fragmenthostcallback, FragmentContainer fragmentcontainer, Fragment fragment)
    {
        if (mHost != null)
        {
            throw new IllegalStateException("Already attached");
        } else
        {
            mHost = fragmenthostcallback;
            mContainer = fragmentcontainer;
            mParent = fragment;
            return;
        }
    }

    public void attachFragment(Fragment fragment, int i, int j)
    {
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("attach: ").append(fragment).toString());
        }
        if (fragment.mDetached)
        {
            fragment.mDetached = false;
            if (!fragment.mAdded)
            {
                if (mAdded == null)
                {
                    mAdded = new ArrayList();
                }
                if (mAdded.contains(fragment))
                {
                    throw new IllegalStateException((new StringBuilder()).append("Fragment already added: ").append(fragment).toString());
                }
                if (DEBUG)
                {
                    Log.v("FragmentManager", (new StringBuilder()).append("add from attach: ").append(fragment).toString());
                }
                mAdded.add(fragment);
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible)
                {
                    mNeedMenuInvalidate = true;
                }
                moveToState(fragment, mCurState, i, j, false);
            }
        }
    }

    public FragmentTransaction beginTransaction()
    {
        return new BackStackRecord(this);
    }

    public void detachFragment(Fragment fragment, int i, int j)
    {
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("detach: ").append(fragment).toString());
        }
        if (!fragment.mDetached)
        {
            fragment.mDetached = true;
            if (fragment.mAdded)
            {
                if (mAdded != null)
                {
                    if (DEBUG)
                    {
                        Log.v("FragmentManager", (new StringBuilder()).append("remove from detach: ").append(fragment).toString());
                    }
                    mAdded.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible)
                {
                    mNeedMenuInvalidate = true;
                }
                fragment.mAdded = false;
                moveToState(fragment, 1, i, j, false);
            }
        }
    }

    public void dispatchActivityCreated()
    {
        mStateSaved = false;
        moveToState(2, false);
    }

    public void dispatchConfigurationChanged(Configuration configuration)
    {
        if (mAdded != null)
        {
            for (int i = 0; i < mAdded.size(); i++)
            {
                Fragment fragment = (Fragment)mAdded.get(i);
                if (fragment != null)
                {
                    fragment.performConfigurationChanged(configuration);
                }
            }

        }
    }

    public boolean dispatchContextItemSelected(MenuItem menuitem)
    {
        if (mAdded != null)
        {
            for (int i = 0; i < mAdded.size(); i++)
            {
                Fragment fragment = (Fragment)mAdded.get(i);
                if (fragment != null && fragment.performContextItemSelected(menuitem))
                {
                    return true;
                }
            }

        }
        return false;
    }

    public void dispatchCreate()
    {
        mStateSaved = false;
        moveToState(1, false);
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuinflater)
    {
        boolean flag1 = false;
        boolean flag = false;
        ArrayList arraylist1 = null;
        ArrayList arraylist = null;
        if (mAdded != null)
        {
            int i = 0;
            do
            {
                arraylist1 = arraylist;
                flag1 = flag;
                if (i >= mAdded.size())
                {
                    break;
                }
                Fragment fragment = (Fragment)mAdded.get(i);
                arraylist1 = arraylist;
                flag1 = flag;
                if (fragment != null)
                {
                    arraylist1 = arraylist;
                    flag1 = flag;
                    if (fragment.performCreateOptionsMenu(menu, menuinflater))
                    {
                        flag1 = true;
                        arraylist1 = arraylist;
                        if (arraylist == null)
                        {
                            arraylist1 = new ArrayList();
                        }
                        arraylist1.add(fragment);
                    }
                }
                i++;
                arraylist = arraylist1;
                flag = flag1;
            } while (true);
        }
        if (mCreatedMenus != null)
        {
            for (int j = 0; j < mCreatedMenus.size(); j++)
            {
                menu = (Fragment)mCreatedMenus.get(j);
                if (arraylist1 == null || !arraylist1.contains(menu))
                {
                    menu.onDestroyOptionsMenu();
                }
            }

        }
        mCreatedMenus = arraylist1;
        return flag1;
    }

    public void dispatchDestroy()
    {
        mDestroyed = true;
        execPendingActions();
        moveToState(0, false);
        mHost = null;
        mContainer = null;
        mParent = null;
    }

    public void dispatchDestroyView()
    {
        moveToState(1, false);
    }

    public void dispatchLowMemory()
    {
        if (mAdded != null)
        {
            for (int i = 0; i < mAdded.size(); i++)
            {
                Fragment fragment = (Fragment)mAdded.get(i);
                if (fragment != null)
                {
                    fragment.performLowMemory();
                }
            }

        }
    }

    public boolean dispatchOptionsItemSelected(MenuItem menuitem)
    {
        if (mAdded != null)
        {
            for (int i = 0; i < mAdded.size(); i++)
            {
                Fragment fragment = (Fragment)mAdded.get(i);
                if (fragment != null && fragment.performOptionsItemSelected(menuitem))
                {
                    return true;
                }
            }

        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu)
    {
        if (mAdded != null)
        {
            for (int i = 0; i < mAdded.size(); i++)
            {
                Fragment fragment = (Fragment)mAdded.get(i);
                if (fragment != null)
                {
                    fragment.performOptionsMenuClosed(menu);
                }
            }

        }
    }

    public void dispatchPause()
    {
        moveToState(4, false);
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu)
    {
        boolean flag1 = false;
        boolean flag = false;
        if (mAdded != null)
        {
            int i = 0;
            do
            {
                flag1 = flag;
                if (i >= mAdded.size())
                {
                    break;
                }
                Fragment fragment = (Fragment)mAdded.get(i);
                flag1 = flag;
                if (fragment != null)
                {
                    flag1 = flag;
                    if (fragment.performPrepareOptionsMenu(menu))
                    {
                        flag1 = true;
                    }
                }
                i++;
                flag = flag1;
            } while (true);
        }
        return flag1;
    }

    public void dispatchReallyStop()
    {
        moveToState(2, false);
    }

    public void dispatchResume()
    {
        mStateSaved = false;
        moveToState(5, false);
    }

    public void dispatchStart()
    {
        mStateSaved = false;
        moveToState(4, false);
    }

    public void dispatchStop()
    {
        mStateSaved = true;
        moveToState(3, false);
    }

    public void dump(String s, FileDescriptor filedescriptor, PrintWriter printwriter, String as[])
    {
        String s1 = (new StringBuilder()).append(s).append("    ").toString();
        if (mActive != null)
        {
            int k1 = mActive.size();
            if (k1 > 0)
            {
                printwriter.print(s);
                printwriter.print("Active Fragments in ");
                printwriter.print(Integer.toHexString(System.identityHashCode(this)));
                printwriter.println(":");
                for (int i = 0; i < k1; i++)
                {
                    Fragment fragment = (Fragment)mActive.get(i);
                    printwriter.print(s);
                    printwriter.print("  #");
                    printwriter.print(i);
                    printwriter.print(": ");
                    printwriter.println(fragment);
                    if (fragment != null)
                    {
                        fragment.dump(s1, filedescriptor, printwriter, as);
                    }
                }

            }
        }
        if (mAdded != null)
        {
            int l1 = mAdded.size();
            if (l1 > 0)
            {
                printwriter.print(s);
                printwriter.println("Added Fragments:");
                for (int j = 0; j < l1; j++)
                {
                    Fragment fragment1 = (Fragment)mAdded.get(j);
                    printwriter.print(s);
                    printwriter.print("  #");
                    printwriter.print(j);
                    printwriter.print(": ");
                    printwriter.println(fragment1.toString());
                }

            }
        }
        if (mCreatedMenus != null)
        {
            int i2 = mCreatedMenus.size();
            if (i2 > 0)
            {
                printwriter.print(s);
                printwriter.println("Fragments Created Menus:");
                for (int k = 0; k < i2; k++)
                {
                    Fragment fragment2 = (Fragment)mCreatedMenus.get(k);
                    printwriter.print(s);
                    printwriter.print("  #");
                    printwriter.print(k);
                    printwriter.print(": ");
                    printwriter.println(fragment2.toString());
                }

            }
        }
        if (mBackStack != null)
        {
            int j2 = mBackStack.size();
            if (j2 > 0)
            {
                printwriter.print(s);
                printwriter.println("Back Stack:");
                for (int l = 0; l < j2; l++)
                {
                    BackStackRecord backstackrecord = (BackStackRecord)mBackStack.get(l);
                    printwriter.print(s);
                    printwriter.print("  #");
                    printwriter.print(l);
                    printwriter.print(": ");
                    printwriter.println(backstackrecord.toString());
                    backstackrecord.dump(s1, filedescriptor, printwriter, as);
                }

            }
        }
        this;
        JVM INSTR monitorenter ;
        if (mBackStackIndices == null) goto _L2; else goto _L1
_L1:
        int k2 = mBackStackIndices.size();
        if (k2 <= 0) goto _L2; else goto _L3
_L3:
        printwriter.print(s);
        printwriter.println("Back Stack Indices:");
        int i1 = 0;
_L4:
        if (i1 >= k2)
        {
            break; /* Loop/switch isn't completed */
        }
        filedescriptor = (BackStackRecord)mBackStackIndices.get(i1);
        printwriter.print(s);
        printwriter.print("  #");
        printwriter.print(i1);
        printwriter.print(": ");
        printwriter.println(filedescriptor);
        i1++;
        if (true) goto _L4; else goto _L2
_L2:
        if (mAvailBackStackIndices != null && mAvailBackStackIndices.size() > 0)
        {
            printwriter.print(s);
            printwriter.print("mAvailBackStackIndices: ");
            printwriter.println(Arrays.toString(mAvailBackStackIndices.toArray()));
        }
        this;
        JVM INSTR monitorexit ;
        if (mPendingActions != null)
        {
            int l2 = mPendingActions.size();
            if (l2 > 0)
            {
                printwriter.print(s);
                printwriter.println("Pending Actions:");
                for (int j1 = 0; j1 < l2; j1++)
                {
                    filedescriptor = (Runnable)mPendingActions.get(j1);
                    printwriter.print(s);
                    printwriter.print("  #");
                    printwriter.print(j1);
                    printwriter.print(": ");
                    printwriter.println(filedescriptor);
                }

            }
        }
        break MISSING_BLOCK_LABEL_706;
        s;
        this;
        JVM INSTR monitorexit ;
        throw s;
        printwriter.print(s);
        printwriter.println("FragmentManager misc state:");
        printwriter.print(s);
        printwriter.print("  mHost=");
        printwriter.println(mHost);
        printwriter.print(s);
        printwriter.print("  mContainer=");
        printwriter.println(mContainer);
        if (mParent != null)
        {
            printwriter.print(s);
            printwriter.print("  mParent=");
            printwriter.println(mParent);
        }
        printwriter.print(s);
        printwriter.print("  mCurState=");
        printwriter.print(mCurState);
        printwriter.print(" mStateSaved=");
        printwriter.print(mStateSaved);
        printwriter.print(" mDestroyed=");
        printwriter.println(mDestroyed);
        if (mNeedMenuInvalidate)
        {
            printwriter.print(s);
            printwriter.print("  mNeedMenuInvalidate=");
            printwriter.println(mNeedMenuInvalidate);
        }
        if (mNoTransactionsBecause != null)
        {
            printwriter.print(s);
            printwriter.print("  mNoTransactionsBecause=");
            printwriter.println(mNoTransactionsBecause);
        }
        if (mAvailIndices != null && mAvailIndices.size() > 0)
        {
            printwriter.print(s);
            printwriter.print("  mAvailIndices: ");
            printwriter.println(Arrays.toString(mAvailIndices.toArray()));
        }
        return;
    }

    public void enqueueAction(Runnable runnable, boolean flag)
    {
        if (!flag)
        {
            checkStateLoss();
        }
        this;
        JVM INSTR monitorenter ;
        if (mDestroyed || mHost == null)
        {
            throw new IllegalStateException("Activity has been destroyed");
        }
        break MISSING_BLOCK_LABEL_40;
        runnable;
        this;
        JVM INSTR monitorexit ;
        throw runnable;
        if (mPendingActions == null)
        {
            mPendingActions = new ArrayList();
        }
        mPendingActions.add(runnable);
        if (mPendingActions.size() == 1)
        {
            mHost.getHandler().removeCallbacks(mExecCommit);
            mHost.getHandler().post(mExecCommit);
        }
        this;
        JVM INSTR monitorexit ;
    }

    public boolean execPendingActions()
    {
        if (mExecutingActions)
        {
            throw new IllegalStateException("Recursive entry to executePendingTransactions");
        }
        if (Looper.myLooper() != mHost.getHandler().getLooper())
        {
            throw new IllegalStateException("Must be called from main thread of process");
        }
        boolean flag = false;
_L2:
        this;
        JVM INSTR monitorenter ;
        if (mPendingActions != null && mPendingActions.size() != 0)
        {
            break MISSING_BLOCK_LABEL_141;
        }
        this;
        JVM INSTR monitorexit ;
        int k;
        if (!mHavePendingDeferredStart)
        {
            break MISSING_BLOCK_LABEL_274;
        }
        k = 0;
        for (int i = 0; i < mActive.size();)
        {
            Fragment fragment = (Fragment)mActive.get(i);
            int l = k;
            if (fragment != null)
            {
                l = k;
                if (fragment.mLoaderManager != null)
                {
                    l = k | fragment.mLoaderManager.hasRunningLoaders();
                }
            }
            i++;
            k = l;
        }

        break; /* Loop/switch isn't completed */
        k = mPendingActions.size();
        if (mTmpActions == null || mTmpActions.length < k)
        {
            mTmpActions = new Runnable[k];
        }
        mPendingActions.toArray(mTmpActions);
        mPendingActions.clear();
        mHost.getHandler().removeCallbacks(mExecCommit);
        this;
        JVM INSTR monitorexit ;
        mExecutingActions = true;
        for (int j = 0; j < k; j++)
        {
            mTmpActions[j].run();
            mTmpActions[j] = null;
        }

        break MISSING_BLOCK_LABEL_250;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
        mExecutingActions = false;
        flag = true;
        if (true) goto _L2; else goto _L1
_L1:
        if (k == 0)
        {
            mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
        return flag;
    }

    public boolean executePendingTransactions()
    {
        return execPendingActions();
    }

    public Fragment findFragmentById(int i)
    {
        if (mAdded == null) goto _L2; else goto _L1
_L1:
        int j = mAdded.size() - 1;
_L11:
        if (j < 0) goto _L2; else goto _L3
_L3:
        Fragment fragment = (Fragment)mAdded.get(j);
        if (fragment == null || fragment.mFragmentId != i) goto _L5; else goto _L4
_L4:
        return fragment;
_L5:
        j--;
        continue; /* Loop/switch isn't completed */
_L2:
        if (mActive == null)
        {
            break MISSING_BLOCK_LABEL_118;
        }
        j = mActive.size() - 1;
_L9:
        if (j < 0) goto _L7; else goto _L6
_L6:
        Fragment fragment1;
        fragment1 = (Fragment)mActive.get(j);
        if (fragment1 == null)
        {
            continue; /* Loop/switch isn't completed */
        }
        fragment = fragment1;
        if (fragment1.mFragmentId == i) goto _L4; else goto _L8
_L8:
        j--;
          goto _L9
_L7:
        return null;
        if (true) goto _L11; else goto _L10
_L10:
    }

    public Fragment findFragmentByTag(String s)
    {
        if (mAdded == null || s == null) goto _L2; else goto _L1
_L1:
        int i = mAdded.size() - 1;
_L11:
        if (i < 0) goto _L2; else goto _L3
_L3:
        Fragment fragment = (Fragment)mAdded.get(i);
        if (fragment == null || !s.equals(fragment.mTag)) goto _L5; else goto _L4
_L4:
        return fragment;
_L5:
        i--;
        continue; /* Loop/switch isn't completed */
_L2:
        if (mActive == null || s == null)
        {
            break MISSING_BLOCK_LABEL_132;
        }
        i = mActive.size() - 1;
_L9:
        if (i < 0) goto _L7; else goto _L6
_L6:
        Fragment fragment1;
        fragment1 = (Fragment)mActive.get(i);
        if (fragment1 == null)
        {
            continue; /* Loop/switch isn't completed */
        }
        fragment = fragment1;
        if (s.equals(fragment1.mTag)) goto _L4; else goto _L8
_L8:
        i--;
          goto _L9
_L7:
        return null;
        if (true) goto _L11; else goto _L10
_L10:
    }

    public Fragment findFragmentByWho(String s)
    {
        if (mActive != null && s != null)
        {
            for (int i = mActive.size() - 1; i >= 0; i--)
            {
                Fragment fragment = (Fragment)mActive.get(i);
                if (fragment == null)
                {
                    continue;
                }
                fragment = fragment.findFragmentByWho(s);
                if (fragment != null)
                {
                    return fragment;
                }
            }

        }
        return null;
    }

    public void freeBackStackIndex(int i)
    {
        this;
        JVM INSTR monitorenter ;
        mBackStackIndices.set(i, null);
        if (mAvailBackStackIndices == null)
        {
            mAvailBackStackIndices = new ArrayList();
        }
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("Freeing back stack index ").append(i).toString());
        }
        mAvailBackStackIndices.add(Integer.valueOf(i));
        this;
        JVM INSTR monitorexit ;
        return;
        Exception exception;
        exception;
        this;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public FragmentManager.BackStackEntry getBackStackEntryAt(int i)
    {
        return (FragmentManager.BackStackEntry)mBackStack.get(i);
    }

    public int getBackStackEntryCount()
    {
        if (mBackStack != null)
        {
            return mBackStack.size();
        } else
        {
            return 0;
        }
    }

    public Fragment getFragment(Bundle bundle, String s)
    {
        int i = bundle.getInt(s, -1);
        if (i == -1)
        {
            bundle = null;
        } else
        {
            if (i >= mActive.size())
            {
                throwException(new IllegalStateException((new StringBuilder()).append("Fragment no longer exists for key ").append(s).append(": index ").append(i).toString()));
            }
            Fragment fragment = (Fragment)mActive.get(i);
            bundle = fragment;
            if (fragment == null)
            {
                throwException(new IllegalStateException((new StringBuilder()).append("Fragment no longer exists for key ").append(s).append(": index ").append(i).toString()));
                return fragment;
            }
        }
        return bundle;
    }

    public List getFragments()
    {
        return mActive;
    }

    LayoutInflaterFactory getLayoutInflaterFactory()
    {
        return this;
    }

    public void hideFragment(Fragment fragment, int i, int j)
    {
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("hide: ").append(fragment).toString());
        }
        if (!fragment.mHidden)
        {
            fragment.mHidden = true;
            if (fragment.mView != null)
            {
                Animation animation = loadAnimation(fragment, i, false, j);
                if (animation != null)
                {
                    setHWLayerAnimListenerIfAlpha(fragment.mView, animation);
                    fragment.mView.startAnimation(animation);
                }
                fragment.mView.setVisibility(8);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible)
            {
                mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(true);
        }
    }

    public boolean isDestroyed()
    {
        return mDestroyed;
    }

    Animation loadAnimation(Fragment fragment, int i, boolean flag, int j)
    {
        Animation animation = fragment.onCreateAnimation(i, flag, fragment.mNextAnim);
        if (animation != null)
        {
            return animation;
        }
        if (fragment.mNextAnim != 0)
        {
            fragment = AnimationUtils.loadAnimation(mHost.getContext(), fragment.mNextAnim);
            if (fragment != null)
            {
                return fragment;
            }
        }
        if (i == 0)
        {
            return null;
        }
        i = transitToStyleIndex(i, flag);
        if (i < 0)
        {
            return null;
        }
        switch (i)
        {
        default:
            i = j;
            if (j == 0)
            {
                i = j;
                if (mHost.onHasWindowAnimations())
                {
                    i = mHost.onGetWindowAnimations();
                }
            }
            if (i == 0)
            {
                return null;
            } else
            {
                return null;
            }

        case 1: // '\001'
            return makeOpenCloseAnimation(mHost.getContext(), 1.125F, 1.0F, 0.0F, 1.0F);

        case 2: // '\002'
            return makeOpenCloseAnimation(mHost.getContext(), 1.0F, 0.975F, 1.0F, 0.0F);

        case 3: // '\003'
            return makeOpenCloseAnimation(mHost.getContext(), 0.975F, 1.0F, 0.0F, 1.0F);

        case 4: // '\004'
            return makeOpenCloseAnimation(mHost.getContext(), 1.0F, 1.075F, 1.0F, 0.0F);

        case 5: // '\005'
            return makeFadeAnimation(mHost.getContext(), 0.0F, 1.0F);

        case 6: // '\006'
            return makeFadeAnimation(mHost.getContext(), 1.0F, 0.0F);
        }
    }

    void makeActive(Fragment fragment)
    {
        if (fragment.mIndex < 0)
        {
            if (mAvailIndices == null || mAvailIndices.size() <= 0)
            {
                if (mActive == null)
                {
                    mActive = new ArrayList();
                }
                fragment.setIndex(mActive.size(), mParent);
                mActive.add(fragment);
            } else
            {
                fragment.setIndex(((Integer)mAvailIndices.remove(mAvailIndices.size() - 1)).intValue(), mParent);
                mActive.set(fragment.mIndex, fragment);
            }
            if (DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("Allocated fragment index ").append(fragment).toString());
                return;
            }
        }
    }

    void makeInactive(Fragment fragment)
    {
        if (fragment.mIndex < 0)
        {
            return;
        }
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("Freeing fragment index ").append(fragment).toString());
        }
        mActive.set(fragment.mIndex, null);
        if (mAvailIndices == null)
        {
            mAvailIndices = new ArrayList();
        }
        mAvailIndices.add(Integer.valueOf(fragment.mIndex));
        mHost.inactivateFragment(fragment.mWho);
        fragment.initState();
    }

    void moveToState(int i, int j, int k, boolean flag)
    {
        if (mHost == null && i != 0)
        {
            throw new IllegalStateException("No host");
        }
        if (flag || mCurState != i)
        {
            mCurState = i;
            if (mActive != null)
            {
                boolean flag1 = false;
                for (int l = 0; l < mActive.size();)
                {
                    Fragment fragment = (Fragment)mActive.get(l);
                    boolean flag2 = flag1;
                    if (fragment != null)
                    {
                        moveToState(fragment, i, j, k, false);
                        flag2 = flag1;
                        if (fragment.mLoaderManager != null)
                        {
                            flag2 = flag1 | fragment.mLoaderManager.hasRunningLoaders();
                        }
                    }
                    l++;
                    flag1 = flag2;
                }

                if (!flag1)
                {
                    startPendingDeferredFragments();
                }
                if (mNeedMenuInvalidate && mHost != null && mCurState == 5)
                {
                    mHost.onSupportInvalidateOptionsMenu();
                    mNeedMenuInvalidate = false;
                    return;
                }
            }
        }
    }

    void moveToState(int i, boolean flag)
    {
        moveToState(i, 0, 0, flag);
    }

    void moveToState(Fragment fragment)
    {
        moveToState(fragment, mCurState, 0, 0, false);
    }

    void moveToState(Fragment fragment, int i, int j, int k, boolean flag)
    {
        int l;
label0:
        {
            if (fragment.mAdded)
            {
                l = i;
                if (!fragment.mDetached)
                {
                    break label0;
                }
            }
            l = i;
            if (i > 1)
            {
                l = 1;
            }
        }
        int j1 = l;
        if (fragment.mRemoving)
        {
            j1 = l;
            if (l > fragment.mState)
            {
                j1 = fragment.mState;
            }
        }
        i = j1;
        if (fragment.mDeferStart)
        {
            i = j1;
            if (fragment.mState < 4)
            {
                i = j1;
                if (j1 > 3)
                {
                    i = 3;
                }
            }
        }
        if (fragment.mState >= i) goto _L2; else goto _L1
_L1:
        int i1;
        int k1;
        int l1;
        if (fragment.mFromLayout && !fragment.mInLayout)
        {
            return;
        }
        if (fragment.mAnimatingAway != null)
        {
            fragment.mAnimatingAway = null;
            moveToState(fragment, fragment.mStateAfterAnimating, 0, 0, true);
        }
        i1 = i;
        l1 = i;
        k1 = i;
        fragment.mState;
        JVM INSTR tableswitch 0 4: default 184
    //                   0 194
    //                   1 578
    //                   2 912
    //                   3 912
    //                   4 962;
           goto _L3 _L4 _L5 _L6 _L6 _L7
_L3:
        i1 = i;
_L16:
        fragment.mState = i1;
        return;
_L4:
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("moveto CREATED: ").append(fragment).toString());
        }
        k1 = i;
        if (fragment.mSavedFragmentState != null)
        {
            fragment.mSavedFragmentState.setClassLoader(mHost.getContext().getClassLoader());
            fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray("android:view_state");
            fragment.mTarget = getFragment(fragment.mSavedFragmentState, "android:target_state");
            if (fragment.mTarget != null)
            {
                fragment.mTargetRequestCode = fragment.mSavedFragmentState.getInt("android:target_req_state", 0);
            }
            fragment.mUserVisibleHint = fragment.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
            k1 = i;
            if (!fragment.mUserVisibleHint)
            {
                fragment.mDeferStart = true;
                k1 = i;
                if (i > 3)
                {
                    k1 = 3;
                }
            }
        }
        fragment.mHost = mHost;
        fragment.mParentFragment = mParent;
        FragmentManagerImpl fragmentmanagerimpl;
        if (mParent != null)
        {
            fragmentmanagerimpl = mParent.mChildFragmentManager;
        } else
        {
            fragmentmanagerimpl = mHost.getFragmentManagerImpl();
        }
        fragment.mFragmentManager = fragmentmanagerimpl;
        fragment.mCalled = false;
        fragment.onAttach(mHost.getContext());
        if (!fragment.mCalled)
        {
            throw new SuperNotCalledException((new StringBuilder()).append("Fragment ").append(fragment).append(" did not call through to super.onAttach()").toString());
        }
        if (fragment.mParentFragment == null)
        {
            mHost.onAttachFragment(fragment);
        }
        if (!fragment.mRetaining)
        {
            fragment.performCreate(fragment.mSavedFragmentState);
        }
        fragment.mRetaining = false;
        i1 = k1;
        if (fragment.mFromLayout)
        {
            fragment.mView = fragment.performCreateView(fragment.getLayoutInflater(fragment.mSavedFragmentState), null, fragment.mSavedFragmentState);
            if (fragment.mView != null)
            {
                fragment.mInnerView = fragment.mView;
                ViewGroup viewgroup;
                Object obj;
                if (android.os.Build.VERSION.SDK_INT >= 11)
                {
                    ViewCompat.setSaveFromParentEnabled(fragment.mView, false);
                } else
                {
                    fragment.mView = NoSaveStateFrameLayout.wrap(fragment.mView);
                }
                if (fragment.mHidden)
                {
                    fragment.mView.setVisibility(8);
                }
                fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                i1 = k1;
            } else
            {
                fragment.mInnerView = null;
                i1 = k1;
            }
        }
_L5:
        l1 = i1;
        if (i1 > 1)
        {
            if (DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("moveto ACTIVITY_CREATED: ").append(fragment).toString());
            }
            if (!fragment.mFromLayout)
            {
                viewgroup = null;
                if (fragment.mContainerId != 0)
                {
                    obj = (ViewGroup)mContainer.onFindViewById(fragment.mContainerId);
                    viewgroup = ((ViewGroup) (obj));
                    if (obj == null)
                    {
                        viewgroup = ((ViewGroup) (obj));
                        if (!fragment.mRestored)
                        {
                            throwException(new IllegalArgumentException((new StringBuilder()).append("No view found for id 0x").append(Integer.toHexString(fragment.mContainerId)).append(" (").append(fragment.getResources().getResourceName(fragment.mContainerId)).append(") for fragment ").append(fragment).toString()));
                            viewgroup = ((ViewGroup) (obj));
                        }
                    }
                }
                fragment.mContainer = viewgroup;
                fragment.mView = fragment.performCreateView(fragment.getLayoutInflater(fragment.mSavedFragmentState), viewgroup, fragment.mSavedFragmentState);
                if (fragment.mView != null)
                {
                    fragment.mInnerView = fragment.mView;
                    if (android.os.Build.VERSION.SDK_INT >= 11)
                    {
                        ViewCompat.setSaveFromParentEnabled(fragment.mView, false);
                    } else
                    {
                        fragment.mView = NoSaveStateFrameLayout.wrap(fragment.mView);
                    }
                    if (viewgroup != null)
                    {
                        obj = loadAnimation(fragment, j, true, k);
                        if (obj != null)
                        {
                            setHWLayerAnimListenerIfAlpha(fragment.mView, ((Animation) (obj)));
                            fragment.mView.startAnimation(((Animation) (obj)));
                        }
                        viewgroup.addView(fragment.mView);
                    }
                    if (fragment.mHidden)
                    {
                        fragment.mView.setVisibility(8);
                    }
                    fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                } else
                {
                    fragment.mInnerView = null;
                }
            }
            fragment.performActivityCreated(fragment.mSavedFragmentState);
            if (fragment.mView != null)
            {
                fragment.restoreViewState(fragment.mSavedFragmentState);
            }
            fragment.mSavedFragmentState = null;
            l1 = i1;
        }
_L6:
        k1 = l1;
        if (l1 > 3)
        {
            if (DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("moveto STARTED: ").append(fragment).toString());
            }
            fragment.performStart();
            k1 = l1;
        }
_L7:
        i1 = k1;
        if (k1 > 4)
        {
            if (DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("moveto RESUMED: ").append(fragment).toString());
            }
            fragment.mResumed = true;
            fragment.performResume();
            fragment.mSavedFragmentState = null;
            fragment.mSavedViewState = null;
            i1 = k1;
        }
        continue; /* Loop/switch isn't completed */
_L2:
        i1 = i;
        if (fragment.mState <= i)
        {
            continue; /* Loop/switch isn't completed */
        }
        fragment.mState;
        JVM INSTR tableswitch 1 5: default 1128
    //                   1 1134
    //                   2 1318
    //                   3 1277
    //                   4 1236
    //                   5 1190;
           goto _L8 _L9 _L10 _L11 _L12 _L13
_L8:
        i1 = i;
        break; /* Loop/switch isn't completed */
_L9:
        i1 = i;
        if (i >= 1)
        {
            break; /* Loop/switch isn't completed */
        }
        if (mDestroyed && fragment.mAnimatingAway != null)
        {
            View view = fragment.mAnimatingAway;
            fragment.mAnimatingAway = null;
            view.clearAnimation();
        }
        if (fragment.mAnimatingAway != null)
        {
            fragment.mStateAfterAnimating = i;
            i1 = 1;
            break; /* Loop/switch isn't completed */
        }
        break; /* Loop/switch isn't completed */
_L13:
        if (i < 5)
        {
            if (DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("movefrom RESUMED: ").append(fragment).toString());
            }
            fragment.performPause();
            fragment.mResumed = false;
        }
_L12:
        if (i < 4)
        {
            if (DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("movefrom STARTED: ").append(fragment).toString());
            }
            fragment.performStop();
        }
_L11:
        if (i < 3)
        {
            if (DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("movefrom STOPPED: ").append(fragment).toString());
            }
            fragment.performReallyStop();
        }
_L10:
        if (i < 2)
        {
            if (DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("movefrom ACTIVITY_CREATED: ").append(fragment).toString());
            }
            if (fragment.mView != null && mHost.onShouldSaveFragmentState(fragment) && fragment.mSavedViewState == null)
            {
                saveFragmentViewState(fragment);
            }
            fragment.performDestroyView();
            if (fragment.mView != null && fragment.mContainer != null)
            {
                Object obj1 = null;
                Animation animation = obj1;
                if (mCurState > 0)
                {
                    animation = obj1;
                    if (!mDestroyed)
                    {
                        animation = loadAnimation(fragment, j, false, k);
                    }
                }
                if (animation != null)
                {
                    fragment.mAnimatingAway = fragment.mView;
                    fragment.mStateAfterAnimating = i;
                    animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(animation, fragment) {

                        final FragmentManagerImpl this$0;
                        final Fragment val$fragment;

                        public void onAnimationEnd(Animation animation1)
                        {
                            super.onAnimationEnd(animation1);
                            if (fragment.mAnimatingAway != null)
                            {
                                fragment.mAnimatingAway = null;
                                moveToState(fragment, fragment.mStateAfterAnimating, 0, 0, false);
                            }
                        }

            
            {
                this$0 = FragmentManagerImpl.this;
                fragment = fragment1;
                super(final_view, animation);
            }
                    });
                    fragment.mView.startAnimation(animation);
                }
                fragment.mContainer.removeView(fragment.mView);
            }
            fragment.mContainer = null;
            fragment.mView = null;
            fragment.mInnerView = null;
        }
        if (true) goto _L9; else goto _L14
_L14:
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("movefrom CREATED: ").append(fragment).toString());
        }
        if (!fragment.mRetaining)
        {
            fragment.performDestroy();
        }
        fragment.mCalled = false;
        fragment.onDetach();
        if (!fragment.mCalled)
        {
            throw new SuperNotCalledException((new StringBuilder()).append("Fragment ").append(fragment).append(" did not call through to super.onDetach()").toString());
        }
        i1 = i;
        if (!flag)
        {
            if (!fragment.mRetaining)
            {
                makeInactive(fragment);
                i1 = i;
            } else
            {
                fragment.mHost = null;
                fragment.mParentFragment = null;
                fragment.mFragmentManager = null;
                fragment.mChildFragmentManager = null;
                i1 = i;
            }
        }
        if (true) goto _L16; else goto _L15
_L15:
    }

    public void noteStateNotSaved()
    {
        mStateSaved = false;
    }

    public View onCreateView(View view, String s, Context context, AttributeSet attributeset)
    {
        if ("fragment".equals(s))
        {
            s = attributeset.getAttributeValue(null, "class");
            TypedArray typedarray = context.obtainStyledAttributes(attributeset, FragmentTag.Fragment);
            String s1 = s;
            if (s == null)
            {
                s1 = typedarray.getString(0);
            }
            int k = typedarray.getResourceId(1, -1);
            String s2 = typedarray.getString(2);
            typedarray.recycle();
            if (Fragment.isSupportFragmentClass(mHost.getContext(), s1))
            {
                int i;
                if (view != null)
                {
                    i = view.getId();
                } else
                {
                    i = 0;
                }
                if (i == -1 && k == -1 && s2 == null)
                {
                    throw new IllegalArgumentException((new StringBuilder()).append(attributeset.getPositionDescription()).append(": Must specify unique android:id, android:tag, or have a parent with an id for ").append(s1).toString());
                }
                if (k != -1)
                {
                    s = findFragmentById(k);
                } else
                {
                    s = null;
                }
                view = s;
                if (s == null)
                {
                    view = s;
                    if (s2 != null)
                    {
                        view = findFragmentByTag(s2);
                    }
                }
                s = view;
                if (view == null)
                {
                    s = view;
                    if (i != -1)
                    {
                        s = findFragmentById(i);
                    }
                }
                if (DEBUG)
                {
                    Log.v("FragmentManager", (new StringBuilder()).append("onCreateView: id=0x").append(Integer.toHexString(k)).append(" fname=").append(s1).append(" existing=").append(s).toString());
                }
                if (s == null)
                {
                    view = Fragment.instantiate(context, s1);
                    view.mFromLayout = true;
                    int j;
                    if (k != 0)
                    {
                        j = k;
                    } else
                    {
                        j = i;
                    }
                    view.mFragmentId = j;
                    view.mContainerId = i;
                    view.mTag = s2;
                    view.mInLayout = true;
                    view.mFragmentManager = this;
                    view.mHost = mHost;
                    view.onInflate(mHost.getContext(), attributeset, ((Fragment) (view)).mSavedFragmentState);
                    addFragment(view, true);
                } else
                {
                    if (((Fragment) (s)).mInLayout)
                    {
                        throw new IllegalArgumentException((new StringBuilder()).append(attributeset.getPositionDescription()).append(": Duplicate id 0x").append(Integer.toHexString(k)).append(", tag ").append(s2).append(", or parent id 0x").append(Integer.toHexString(i)).append(" with another fragment for ").append(s1).toString());
                    }
                    s.mInLayout = true;
                    view = s;
                    if (!((Fragment) (s)).mRetaining)
                    {
                        s.onInflate(mHost.getContext(), attributeset, ((Fragment) (s)).mSavedFragmentState);
                        view = s;
                    }
                }
                if (mCurState < 1 && ((Fragment) (view)).mFromLayout)
                {
                    moveToState(view, 1, 0, 0, false);
                } else
                {
                    moveToState(view);
                }
                if (((Fragment) (view)).mView == null)
                {
                    throw new IllegalStateException((new StringBuilder()).append("Fragment ").append(s1).append(" did not create a view.").toString());
                }
                if (k != 0)
                {
                    ((Fragment) (view)).mView.setId(k);
                }
                if (((Fragment) (view)).mView.getTag() == null)
                {
                    ((Fragment) (view)).mView.setTag(s2);
                }
                return ((Fragment) (view)).mView;
            }
        }
        return null;
    }

    public void performPendingDeferredStart(Fragment fragment)
    {
label0:
        {
            if (fragment.mDeferStart)
            {
                if (!mExecutingActions)
                {
                    break label0;
                }
                mHavePendingDeferredStart = true;
            }
            return;
        }
        fragment.mDeferStart = false;
        moveToState(fragment, mCurState, 0, 0, false);
    }

    public void popBackStack()
    {
        enqueueAction(new Runnable() {

            final FragmentManagerImpl this$0;

            public void run()
            {
                popBackStackState(mHost.getHandler(), null, -1, 0);
            }

            
            {
                this$0 = FragmentManagerImpl.this;
                super();
            }
        }, false);
    }

    public void popBackStack(final int id, final int flags)
    {
        if (id < 0)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Bad id: ").append(id).toString());
        } else
        {
            enqueueAction(new Runnable() {

                final FragmentManagerImpl this$0;
                final int val$flags;
                final int val$id;

                public void run()
                {
                    popBackStackState(mHost.getHandler(), null, id, flags);
                }

            
            {
                this$0 = FragmentManagerImpl.this;
                id = i;
                flags = j;
                super();
            }
            }, false);
            return;
        }
    }

    public void popBackStack(final String name, final int flags)
    {
        enqueueAction(new Runnable() {

            final FragmentManagerImpl this$0;
            final int val$flags;
            final String val$name;

            public void run()
            {
                popBackStackState(mHost.getHandler(), name, -1, flags);
            }

            
            {
                this$0 = FragmentManagerImpl.this;
                name = s;
                flags = i;
                super();
            }
        }, false);
    }

    public boolean popBackStackImmediate()
    {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(mHost.getHandler(), null, -1, 0);
    }

    public boolean popBackStackImmediate(int i, int j)
    {
        checkStateLoss();
        executePendingTransactions();
        if (i < 0)
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Bad id: ").append(i).toString());
        } else
        {
            return popBackStackState(mHost.getHandler(), null, i, j);
        }
    }

    public boolean popBackStackImmediate(String s, int i)
    {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(mHost.getHandler(), s, -1, i);
    }

    boolean popBackStackState(Handler handler, String s, int i, int j)
    {
        if (mBackStack == null)
        {
            return false;
        }
        if (s != null || i >= 0 || (j & 1) != 0) goto _L2; else goto _L1
_L1:
        i = mBackStack.size() - 1;
        if (i < 0)
        {
            return false;
        }
        handler = (BackStackRecord)mBackStack.remove(i);
        s = new SparseArray();
        SparseArray sparsearray = new SparseArray();
        handler.calculateBackFragments(s, sparsearray);
        handler.popFromBackStack(true, null, s, sparsearray);
        reportBackStackChanged();
_L4:
        return true;
_L2:
        int k;
label0:
        {
            k = -1;
            if (s == null && i < 0)
            {
                break label0;
            }
            int l = mBackStack.size() - 1;
label1:
            do
            {
                if (l >= 0)
                {
                    handler = (BackStackRecord)mBackStack.get(l);
                    break MISSING_BLOCK_LABEL_132;
                }
                do
                {
                    if (l < 0)
                    {
                        return false;
                    }
                    break label1;
                } while (s != null && s.equals(handler.getName()) || i >= 0 && i == ((BackStackRecord) (handler)).mIndex);
                l--;
            } while (true);
            k = l;
            if ((j & 1) != 0)
            {
                j = l - 1;
                do
                {
                    k = j;
                    if (j < 0)
                    {
                        break;
                    }
                    handler = (BackStackRecord)mBackStack.get(j);
                    if (s == null || !s.equals(handler.getName()))
                    {
                        k = j;
                        if (i < 0)
                        {
                            break;
                        }
                        k = j;
                        if (i != ((BackStackRecord) (handler)).mIndex)
                        {
                            break;
                        }
                    }
                    j--;
                } while (true);
            }
        }
        if (k == mBackStack.size() - 1)
        {
            return false;
        }
        s = new ArrayList();
        for (i = mBackStack.size() - 1; i > k; i--)
        {
            s.add(mBackStack.remove(i));
        }

        j = s.size() - 1;
        SparseArray sparsearray1 = new SparseArray();
        SparseArray sparsearray2 = new SparseArray();
        for (i = 0; i <= j; i++)
        {
            ((BackStackRecord)s.get(i)).calculateBackFragments(sparsearray1, sparsearray2);
        }

        handler = null;
        i = 0;
        while (i <= j) 
        {
            if (DEBUG)
            {
                Log.v("FragmentManager", (new StringBuilder()).append("Popping back stack state: ").append(s.get(i)).toString());
            }
            BackStackRecord backstackrecord = (BackStackRecord)s.get(i);
            boolean flag;
            if (i == j)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            handler = backstackrecord.popFromBackStack(flag, handler, sparsearray1, sparsearray2);
            i++;
        }
        reportBackStackChanged();
        if (true) goto _L4; else goto _L3
_L3:
    }

    public void putFragment(Bundle bundle, String s, Fragment fragment)
    {
        if (fragment.mIndex < 0)
        {
            throwException(new IllegalStateException((new StringBuilder()).append("Fragment ").append(fragment).append(" is not currently in the FragmentManager").toString()));
        }
        bundle.putInt(s, fragment.mIndex);
    }

    public void removeFragment(Fragment fragment, int i, int j)
    {
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("remove: ").append(fragment).append(" nesting=").append(fragment.mBackStackNesting).toString());
        }
        int k;
        if (!fragment.isInBackStack())
        {
            k = 1;
        } else
        {
            k = 0;
        }
        if (!fragment.mDetached || k != 0)
        {
            if (mAdded != null)
            {
                mAdded.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible)
            {
                mNeedMenuInvalidate = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
            if (k != 0)
            {
                k = 0;
            } else
            {
                k = 1;
            }
            moveToState(fragment, k, i, j, false);
        }
    }

    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onbackstackchangedlistener)
    {
        if (mBackStackChangeListeners != null)
        {
            mBackStackChangeListeners.remove(onbackstackchangedlistener);
        }
    }

    void reportBackStackChanged()
    {
        if (mBackStackChangeListeners != null)
        {
            for (int i = 0; i < mBackStackChangeListeners.size(); i++)
            {
                ((FragmentManager.OnBackStackChangedListener)mBackStackChangeListeners.get(i)).onBackStackChanged();
            }

        }
    }

    void restoreAllState(Parcelable parcelable, List list)
    {
        if (parcelable != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        if (((FragmentManagerState) (parcelable = (FragmentManagerState)parcelable)).mActive != null)
        {
            if (list != null)
            {
                for (int i = 0; i < list.size(); i++)
                {
                    Fragment fragment = (Fragment)list.get(i);
                    if (DEBUG)
                    {
                        Log.v("FragmentManager", (new StringBuilder()).append("restoreAllState: re-attaching retained ").append(fragment).toString());
                    }
                    FragmentState fragmentstate1 = ((FragmentManagerState) (parcelable)).mActive[fragment.mIndex];
                    fragmentstate1.mInstance = fragment;
                    fragment.mSavedViewState = null;
                    fragment.mBackStackNesting = 0;
                    fragment.mInLayout = false;
                    fragment.mAdded = false;
                    fragment.mTarget = null;
                    if (fragmentstate1.mSavedFragmentState != null)
                    {
                        fragmentstate1.mSavedFragmentState.setClassLoader(mHost.getContext().getClassLoader());
                        fragment.mSavedViewState = fragmentstate1.mSavedFragmentState.getSparseParcelableArray("android:view_state");
                        fragment.mSavedFragmentState = fragmentstate1.mSavedFragmentState;
                    }
                }

            }
            mActive = new ArrayList(((FragmentManagerState) (parcelable)).mActive.length);
            if (mAvailIndices != null)
            {
                mAvailIndices.clear();
            }
            int j = 0;
            while (j < ((FragmentManagerState) (parcelable)).mActive.length) 
            {
                FragmentState fragmentstate = ((FragmentManagerState) (parcelable)).mActive[j];
                if (fragmentstate != null)
                {
                    Fragment fragment2 = fragmentstate.instantiate(mHost, mParent);
                    if (DEBUG)
                    {
                        Log.v("FragmentManager", (new StringBuilder()).append("restoreAllState: active #").append(j).append(": ").append(fragment2).toString());
                    }
                    mActive.add(fragment2);
                    fragmentstate.mInstance = null;
                } else
                {
                    mActive.add(null);
                    if (mAvailIndices == null)
                    {
                        mAvailIndices = new ArrayList();
                    }
                    if (DEBUG)
                    {
                        Log.v("FragmentManager", (new StringBuilder()).append("restoreAllState: avail #").append(j).toString());
                    }
                    mAvailIndices.add(Integer.valueOf(j));
                }
                j++;
            }
            if (list != null)
            {
                int k = 0;
                while (k < list.size()) 
                {
                    Fragment fragment1 = (Fragment)list.get(k);
                    if (fragment1.mTargetIndex >= 0)
                    {
                        if (fragment1.mTargetIndex < mActive.size())
                        {
                            fragment1.mTarget = (Fragment)mActive.get(fragment1.mTargetIndex);
                        } else
                        {
                            Log.w("FragmentManager", (new StringBuilder()).append("Re-attaching retained fragment ").append(fragment1).append(" target no longer exists: ").append(fragment1.mTargetIndex).toString());
                            fragment1.mTarget = null;
                        }
                    }
                    k++;
                }
            }
            if (((FragmentManagerState) (parcelable)).mAdded != null)
            {
                mAdded = new ArrayList(((FragmentManagerState) (parcelable)).mAdded.length);
                for (int l = 0; l < ((FragmentManagerState) (parcelable)).mAdded.length; l++)
                {
                    list = (Fragment)mActive.get(((FragmentManagerState) (parcelable)).mAdded[l]);
                    if (list == null)
                    {
                        throwException(new IllegalStateException((new StringBuilder()).append("No instantiated fragment for index #").append(((FragmentManagerState) (parcelable)).mAdded[l]).toString()));
                    }
                    list.mAdded = true;
                    if (DEBUG)
                    {
                        Log.v("FragmentManager", (new StringBuilder()).append("restoreAllState: added #").append(l).append(": ").append(list).toString());
                    }
                    if (mAdded.contains(list))
                    {
                        throw new IllegalStateException("Already added!");
                    }
                    mAdded.add(list);
                }

            } else
            {
                mAdded = null;
            }
            if (((FragmentManagerState) (parcelable)).mBackStack != null)
            {
                mBackStack = new ArrayList(((FragmentManagerState) (parcelable)).mBackStack.length);
                int i1 = 0;
                while (i1 < ((FragmentManagerState) (parcelable)).mBackStack.length) 
                {
                    list = ((FragmentManagerState) (parcelable)).mBackStack[i1].instantiate(this);
                    if (DEBUG)
                    {
                        Log.v("FragmentManager", (new StringBuilder()).append("restoreAllState: back stack #").append(i1).append(" (index ").append(((BackStackRecord) (list)).mIndex).append("): ").append(list).toString());
                        list.dump("  ", new PrintWriter(new LogWriter("FragmentManager")), false);
                    }
                    mBackStack.add(list);
                    if (((BackStackRecord) (list)).mIndex >= 0)
                    {
                        setBackStackIndex(((BackStackRecord) (list)).mIndex, list);
                    }
                    i1++;
                }
            } else
            {
                mBackStack = null;
                return;
            }
        }
        if (true) goto _L1; else goto _L3
_L3:
    }

    ArrayList retainNonConfig()
    {
        ArrayList arraylist1 = null;
        ArrayList arraylist = null;
        if (mActive != null)
        {
            int i = 0;
            do
            {
                arraylist1 = arraylist;
                if (i >= mActive.size())
                {
                    break;
                }
                Fragment fragment = (Fragment)mActive.get(i);
                ArrayList arraylist2 = arraylist;
                if (fragment != null)
                {
                    arraylist2 = arraylist;
                    if (fragment.mRetainInstance)
                    {
                        arraylist1 = arraylist;
                        if (arraylist == null)
                        {
                            arraylist1 = new ArrayList();
                        }
                        arraylist1.add(fragment);
                        fragment.mRetaining = true;
                        int j;
                        if (fragment.mTarget != null)
                        {
                            j = fragment.mTarget.mIndex;
                        } else
                        {
                            j = -1;
                        }
                        fragment.mTargetIndex = j;
                        arraylist2 = arraylist1;
                        if (DEBUG)
                        {
                            Log.v("FragmentManager", (new StringBuilder()).append("retainNonConfig: keeping retained ").append(fragment).toString());
                            arraylist2 = arraylist1;
                        }
                    }
                }
                i++;
                arraylist = arraylist2;
            } while (true);
        }
        return arraylist1;
    }

    Parcelable saveAllState()
    {
        execPendingActions();
        if (HONEYCOMB)
        {
            mStateSaved = true;
        }
        if (mActive != null && mActive.size() > 0)
        {
            int j1 = mActive.size();
            FragmentState afragmentstate[] = new FragmentState[j1];
            boolean flag = false;
            int i = 0;
            while (i < j1) 
            {
                Fragment fragment = (Fragment)mActive.get(i);
                if (fragment == null)
                {
                    continue;
                }
                if (fragment.mIndex < 0)
                {
                    throwException(new IllegalStateException((new StringBuilder()).append("Failure saving state: active ").append(fragment).append(" has cleared index: ").append(fragment.mIndex).toString()));
                }
                boolean flag1 = true;
                FragmentState fragmentstate = new FragmentState(fragment);
                afragmentstate[i] = fragmentstate;
                if (fragment.mState > 0 && fragmentstate.mSavedFragmentState == null)
                {
                    fragmentstate.mSavedFragmentState = saveFragmentBasicState(fragment);
                    if (fragment.mTarget != null)
                    {
                        if (fragment.mTarget.mIndex < 0)
                        {
                            throwException(new IllegalStateException((new StringBuilder()).append("Failure saving state: ").append(fragment).append(" has target not in fragment manager: ").append(fragment.mTarget).toString()));
                        }
                        if (fragmentstate.mSavedFragmentState == null)
                        {
                            fragmentstate.mSavedFragmentState = new Bundle();
                        }
                        putFragment(fragmentstate.mSavedFragmentState, "android:target_state", fragment.mTarget);
                        if (fragment.mTargetRequestCode != 0)
                        {
                            fragmentstate.mSavedFragmentState.putInt("android:target_req_state", fragment.mTargetRequestCode);
                        }
                    }
                } else
                {
                    fragmentstate.mSavedFragmentState = fragment.mSavedFragmentState;
                }
                flag = flag1;
                if (DEBUG)
                {
                    Log.v("FragmentManager", (new StringBuilder()).append("Saved state of ").append(fragment).append(": ").append(fragmentstate.mSavedFragmentState).toString());
                    flag = flag1;
                }
                i++;
            }
            if (!flag)
            {
                if (DEBUG)
                {
                    Log.v("FragmentManager", "saveAllState: no fragments!");
                    return null;
                }
            } else
            {
                Object aobj[] = null;
                FragmentManagerState fragmentmanagerstate = null;
                int ai[] = ((int []) (aobj));
                if (mAdded != null)
                {
                    int l = mAdded.size();
                    ai = ((int []) (aobj));
                    if (l > 0)
                    {
                        aobj = new int[l];
                        int j = 0;
                        do
                        {
                            ai = ((int []) (aobj));
                            if (j >= l)
                            {
                                break;
                            }
                            aobj[j] = ((Fragment)mAdded.get(j)).mIndex;
                            if (aobj[j] < 0)
                            {
                                throwException(new IllegalStateException((new StringBuilder()).append("Failure saving state: active ").append(mAdded.get(j)).append(" has cleared index: ").append(aobj[j]).toString()));
                            }
                            if (DEBUG)
                            {
                                Log.v("FragmentManager", (new StringBuilder()).append("saveAllState: adding fragment #").append(j).append(": ").append(mAdded.get(j)).toString());
                            }
                            j++;
                        } while (true);
                    }
                }
                aobj = fragmentmanagerstate;
                if (mBackStack != null)
                {
                    int i1 = mBackStack.size();
                    aobj = fragmentmanagerstate;
                    if (i1 > 0)
                    {
                        BackStackState abackstackstate[] = new BackStackState[i1];
                        int k = 0;
                        do
                        {
                            aobj = abackstackstate;
                            if (k >= i1)
                            {
                                break;
                            }
                            abackstackstate[k] = new BackStackState((BackStackRecord)mBackStack.get(k));
                            if (DEBUG)
                            {
                                Log.v("FragmentManager", (new StringBuilder()).append("saveAllState: adding back stack #").append(k).append(": ").append(mBackStack.get(k)).toString());
                            }
                            k++;
                        } while (true);
                    }
                }
                abackstackstate = new FragmentManagerState();
                abackstackstate.mActive = afragmentstate;
                abackstackstate.mAdded = ai;
                abackstackstate.mBackStack = ((BackStackState []) (aobj));
                return abackstackstate;
            }
        }
        return null;
    }

    Bundle saveFragmentBasicState(Fragment fragment)
    {
        Bundle bundle1 = null;
        if (mStateBundle == null)
        {
            mStateBundle = new Bundle();
        }
        fragment.performSaveInstanceState(mStateBundle);
        if (!mStateBundle.isEmpty())
        {
            bundle1 = mStateBundle;
            mStateBundle = null;
        }
        if (fragment.mView != null)
        {
            saveFragmentViewState(fragment);
        }
        Bundle bundle = bundle1;
        if (fragment.mSavedViewState != null)
        {
            bundle = bundle1;
            if (bundle1 == null)
            {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", fragment.mSavedViewState);
        }
        bundle1 = bundle;
        if (!fragment.mUserVisibleHint)
        {
            bundle1 = bundle;
            if (bundle == null)
            {
                bundle1 = new Bundle();
            }
            bundle1.putBoolean("android:user_visible_hint", fragment.mUserVisibleHint);
        }
        return bundle1;
    }

    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment)
    {
        Object obj = null;
        if (fragment.mIndex < 0)
        {
            throwException(new IllegalStateException((new StringBuilder()).append("Fragment ").append(fragment).append(" is not currently in the FragmentManager").toString()));
        }
        Fragment.SavedState savedstate = obj;
        if (fragment.mState > 0)
        {
            fragment = saveFragmentBasicState(fragment);
            savedstate = obj;
            if (fragment != null)
            {
                savedstate = new Fragment.SavedState(fragment);
            }
        }
        return savedstate;
    }

    void saveFragmentViewState(Fragment fragment)
    {
        if (fragment.mInnerView != null)
        {
            if (mStateArray == null)
            {
                mStateArray = new SparseArray();
            } else
            {
                mStateArray.clear();
            }
            fragment.mInnerView.saveHierarchyState(mStateArray);
            if (mStateArray.size() > 0)
            {
                fragment.mSavedViewState = mStateArray;
                mStateArray = null;
                return;
            }
        }
    }

    public void setBackStackIndex(int i, BackStackRecord backstackrecord)
    {
        this;
        JVM INSTR monitorenter ;
        int k;
        if (mBackStackIndices == null)
        {
            mBackStackIndices = new ArrayList();
        }
        k = mBackStackIndices.size();
        int j = k;
        if (i >= k) goto _L2; else goto _L1
_L1:
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("Setting back stack index ").append(i).append(" to ").append(backstackrecord).toString());
        }
        mBackStackIndices.set(i, backstackrecord);
_L4:
        this;
        JVM INSTR monitorexit ;
        return;
_L2:
        if (j >= i)
        {
            break; /* Loop/switch isn't completed */
        }
        mBackStackIndices.add(null);
        if (mAvailBackStackIndices == null)
        {
            mAvailBackStackIndices = new ArrayList();
        }
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("Adding available back stack index ").append(j).toString());
        }
        mAvailBackStackIndices.add(Integer.valueOf(j));
        j++;
        if (true) goto _L2; else goto _L3
_L3:
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("Adding back stack index ").append(i).append(" with ").append(backstackrecord).toString());
        }
        mBackStackIndices.add(backstackrecord);
          goto _L4
        backstackrecord;
        this;
        JVM INSTR monitorexit ;
        throw backstackrecord;
    }

    public void showFragment(Fragment fragment, int i, int j)
    {
        if (DEBUG)
        {
            Log.v("FragmentManager", (new StringBuilder()).append("show: ").append(fragment).toString());
        }
        if (fragment.mHidden)
        {
            fragment.mHidden = false;
            if (fragment.mView != null)
            {
                Animation animation = loadAnimation(fragment, i, true, j);
                if (animation != null)
                {
                    setHWLayerAnimListenerIfAlpha(fragment.mView, animation);
                    fragment.mView.startAnimation(animation);
                }
                fragment.mView.setVisibility(0);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible)
            {
                mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(false);
        }
    }

    void startPendingDeferredFragments()
    {
        if (mActive != null)
        {
            int i = 0;
            while (i < mActive.size()) 
            {
                Fragment fragment = (Fragment)mActive.get(i);
                if (fragment != null)
                {
                    performPendingDeferredStart(fragment);
                }
                i++;
            }
        }
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder(128);
        stringbuilder.append("FragmentManager{");
        stringbuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringbuilder.append(" in ");
        if (mParent != null)
        {
            DebugUtils.buildShortClassTag(mParent, stringbuilder);
        } else
        {
            DebugUtils.buildShortClassTag(mHost, stringbuilder);
        }
        stringbuilder.append("}}");
        return stringbuilder.toString();
    }

    static 
    {
        boolean flag = false;
        DEBUG = false;
        if (android.os.Build.VERSION.SDK_INT >= 11)
        {
            flag = true;
        }
        HONEYCOMB = flag;
    }
}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import java.util.Collections;
import java.util.List;

// Referenced classes of package android.support.v4.view.accessibility:
//            AccessibilityNodeInfoCompat

static class 
    implements 
{

    public void addAction(Object obj, int i)
    {
    }

    public void addAction(Object obj, Object obj1)
    {
    }

    public void addChild(Object obj, View view)
    {
    }

    public void addChild(Object obj, View view, int i)
    {
    }

    public boolean canOpenPopup(Object obj)
    {
        return false;
    }

    public List findAccessibilityNodeInfosByText(Object obj, String s)
    {
        return Collections.emptyList();
    }

    public List findAccessibilityNodeInfosByViewId(Object obj, String s)
    {
        return Collections.emptyList();
    }

    public Object findFocus(Object obj, int i)
    {
        return null;
    }

    public Object focusSearch(Object obj, int i)
    {
        return null;
    }

    public int getAccessibilityActionId(Object obj)
    {
        return 0;
    }

    public CharSequence getAccessibilityActionLabel(Object obj)
    {
        return null;
    }

    public List getActionList(Object obj)
    {
        return null;
    }

    public int getActions(Object obj)
    {
        return 0;
    }

    public void getBoundsInParent(Object obj, Rect rect)
    {
    }

    public void getBoundsInScreen(Object obj, Rect rect)
    {
    }

    public Object getChild(Object obj, int i)
    {
        return null;
    }

    public int getChildCount(Object obj)
    {
        return 0;
    }

    public CharSequence getClassName(Object obj)
    {
        return null;
    }

    public Object getCollectionInfo(Object obj)
    {
        return null;
    }

    public int getCollectionInfoColumnCount(Object obj)
    {
        return 0;
    }

    public int getCollectionInfoRowCount(Object obj)
    {
        return 0;
    }

    public int getCollectionItemColumnIndex(Object obj)
    {
        return 0;
    }

    public int getCollectionItemColumnSpan(Object obj)
    {
        return 0;
    }

    public Object getCollectionItemInfo(Object obj)
    {
        return null;
    }

    public int getCollectionItemRowIndex(Object obj)
    {
        return 0;
    }

    public int getCollectionItemRowSpan(Object obj)
    {
        return 0;
    }

    public CharSequence getContentDescription(Object obj)
    {
        return null;
    }

    public CharSequence getError(Object obj)
    {
        return null;
    }

    public Bundle getExtras(Object obj)
    {
        return new Bundle();
    }

    public int getInputType(Object obj)
    {
        return 0;
    }

    public Object getLabelFor(Object obj)
    {
        return null;
    }

    public Object getLabeledBy(Object obj)
    {
        return null;
    }

    public int getLiveRegion(Object obj)
    {
        return 0;
    }

    public int getMaxTextLength(Object obj)
    {
        return -1;
    }

    public int getMovementGranularities(Object obj)
    {
        return 0;
    }

    public CharSequence getPackageName(Object obj)
    {
        return null;
    }

    public Object getParent(Object obj)
    {
        return null;
    }

    public Object getRangeInfo(Object obj)
    {
        return null;
    }

    public CharSequence getText(Object obj)
    {
        return null;
    }

    public int getTextSelectionEnd(Object obj)
    {
        return -1;
    }

    public int getTextSelectionStart(Object obj)
    {
        return -1;
    }

    public Object getTraversalAfter(Object obj)
    {
        return null;
    }

    public Object getTraversalBefore(Object obj)
    {
        return null;
    }

    public String getViewIdResourceName(Object obj)
    {
        return null;
    }

    public Object getWindow(Object obj)
    {
        return null;
    }

    public int getWindowId(Object obj)
    {
        return 0;
    }

    public boolean isAccessibilityFocused(Object obj)
    {
        return false;
    }

    public boolean isCheckable(Object obj)
    {
        return false;
    }

    public boolean isChecked(Object obj)
    {
        return false;
    }

    public boolean isClickable(Object obj)
    {
        return false;
    }

    public boolean isCollectionInfoHierarchical(Object obj)
    {
        return false;
    }

    public boolean isCollectionItemHeading(Object obj)
    {
        return false;
    }

    public boolean isCollectionItemSelected(Object obj)
    {
        return false;
    }

    public boolean isContentInvalid(Object obj)
    {
        return false;
    }

    public boolean isDismissable(Object obj)
    {
        return false;
    }

    public boolean isEditable(Object obj)
    {
        return false;
    }

    public boolean isEnabled(Object obj)
    {
        return false;
    }

    public boolean isFocusable(Object obj)
    {
        return false;
    }

    public boolean isFocused(Object obj)
    {
        return false;
    }

    public boolean isLongClickable(Object obj)
    {
        return false;
    }

    public boolean isMultiLine(Object obj)
    {
        return false;
    }

    public boolean isPassword(Object obj)
    {
        return false;
    }

    public boolean isScrollable(Object obj)
    {
        return false;
    }

    public boolean isSelected(Object obj)
    {
        return false;
    }

    public boolean isVisibleToUser(Object obj)
    {
        return false;
    }

    public Object newAccessibilityAction(int i, CharSequence charsequence)
    {
        return null;
    }

    public Object obtain()
    {
        return null;
    }

    public Object obtain(View view)
    {
        return null;
    }

    public Object obtain(View view, int i)
    {
        return null;
    }

    public Object obtain(Object obj)
    {
        return null;
    }

    public Object obtainCollectionInfo(int i, int j, boolean flag, int k)
    {
        return null;
    }

    public Object obtainCollectionItemInfo(int i, int j, int k, int l, boolean flag, boolean flag1)
    {
        return null;
    }

    public boolean performAction(Object obj, int i)
    {
        return false;
    }

    public boolean performAction(Object obj, int i, Bundle bundle)
    {
        return false;
    }

    public void recycle(Object obj)
    {
    }

    public boolean refresh(Object obj)
    {
        return false;
    }

    public boolean removeAction(Object obj, Object obj1)
    {
        return false;
    }

    public boolean removeChild(Object obj, View view)
    {
        return false;
    }

    public boolean removeChild(Object obj, View view, int i)
    {
        return false;
    }

    public void setAccessibilityFocused(Object obj, boolean flag)
    {
    }

    public void setBoundsInParent(Object obj, Rect rect)
    {
    }

    public void setBoundsInScreen(Object obj, Rect rect)
    {
    }

    public void setCanOpenPopup(Object obj, boolean flag)
    {
    }

    public void setCheckable(Object obj, boolean flag)
    {
    }

    public void setChecked(Object obj, boolean flag)
    {
    }

    public void setClassName(Object obj, CharSequence charsequence)
    {
    }

    public void setClickable(Object obj, boolean flag)
    {
    }

    public void setCollectionInfo(Object obj, Object obj1)
    {
    }

    public void setCollectionItemInfo(Object obj, Object obj1)
    {
    }

    public void setContentDescription(Object obj, CharSequence charsequence)
    {
    }

    public void setContentInvalid(Object obj, boolean flag)
    {
    }

    public void setDismissable(Object obj, boolean flag)
    {
    }

    public void setEditable(Object obj, boolean flag)
    {
    }

    public void setEnabled(Object obj, boolean flag)
    {
    }

    public void setError(Object obj, CharSequence charsequence)
    {
    }

    public void setFocusable(Object obj, boolean flag)
    {
    }

    public void setFocused(Object obj, boolean flag)
    {
    }

    public void setInputType(Object obj, int i)
    {
    }

    public void setLabelFor(Object obj, View view)
    {
    }

    public void setLabelFor(Object obj, View view, int i)
    {
    }

    public void setLabeledBy(Object obj, View view)
    {
    }

    public void setLabeledBy(Object obj, View view, int i)
    {
    }

    public void setLiveRegion(Object obj, int i)
    {
    }

    public void setLongClickable(Object obj, boolean flag)
    {
    }

    public void setMaxTextLength(Object obj, int i)
    {
    }

    public void setMovementGranularities(Object obj, int i)
    {
    }

    public void setMultiLine(Object obj, boolean flag)
    {
    }

    public void setPackageName(Object obj, CharSequence charsequence)
    {
    }

    public void setParent(Object obj, View view)
    {
    }

    public void setParent(Object obj, View view, int i)
    {
    }

    public void setPassword(Object obj, boolean flag)
    {
    }

    public void setRangeInfo(Object obj, Object obj1)
    {
    }

    public void setScrollable(Object obj, boolean flag)
    {
    }

    public void setSelected(Object obj, boolean flag)
    {
    }

    public void setSource(Object obj, View view)
    {
    }

    public void setSource(Object obj, View view, int i)
    {
    }

    public void setText(Object obj, CharSequence charsequence)
    {
    }

    public void setTextSelection(Object obj, int i, int j)
    {
    }

    public void setTraversalAfter(Object obj, View view)
    {
    }

    public void setTraversalAfter(Object obj, View view, int i)
    {
    }

    public void setTraversalBefore(Object obj, View view)
    {
    }

    public void setTraversalBefore(Object obj, View view, int i)
    {
    }

    public void setViewIdResourceName(Object obj, String s)
    {
    }

    public void setVisibleToUser(Object obj, boolean flag)
    {
    }

    ()
    {
    }
}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v7.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

// Referenced classes of package android.support.v7.app:
//            AppCompatDelegateImplV7

private static class <init>
    implements Parcelable
{

    public static final android.os..SavedState.menuState CREATOR = new android.os.Parcelable.Creator() {

        public AppCompatDelegateImplV7.PanelFeatureState.SavedState createFromParcel(Parcel parcel)
        {
            return AppCompatDelegateImplV7.PanelFeatureState.SavedState.readFromParcel(parcel);
        }

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public AppCompatDelegateImplV7.PanelFeatureState.SavedState[] newArray(int i)
        {
            return new AppCompatDelegateImplV7.PanelFeatureState.SavedState[i];
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

    };
    int featureId;
    boolean isOpen;
    Bundle menuState;

    private static <init> readFromParcel(Parcel parcel)
    {
        boolean flag = true;
        <init> <init>1 = new <init>();
        <init>1.featureId = parcel.readInt();
        if (parcel.readInt() != 1)
        {
            flag = false;
        }
        <init>1.isOpen = flag;
        if (<init>1.isOpen)
        {
            <init>1.menuState = parcel.readBundle();
        }
        return <init>1;
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



    private _cls1()
    {
    }

    _cls1(_cls1 _pcls1)
    {
        this();
    }
}

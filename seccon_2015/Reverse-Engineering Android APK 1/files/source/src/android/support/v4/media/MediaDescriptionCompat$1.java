// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.media;

import android.os.Parcel;

// Referenced classes of package android.support.v4.media:
//            MediaDescriptionCompat, MediaDescriptionCompatApi21

static final class _cls21
    implements android.os.ptionCompat._cls1
{

    public MediaDescriptionCompat createFromParcel(Parcel parcel)
    {
        if (android.os.ptionCompat._fld1 < 21)
        {
            return new MediaDescriptionCompat(parcel, null);
        } else
        {
            return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(parcel));
        }
    }

    public volatile Object createFromParcel(Parcel parcel)
    {
        return createFromParcel(parcel);
    }

    public MediaDescriptionCompat[] newArray(int i)
    {
        return new MediaDescriptionCompat[i];
    }

    public volatile Object[] newArray(int i)
    {
        return newArray(i);
    }

    _cls21()
    {
    }
}

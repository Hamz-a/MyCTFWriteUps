// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

// Referenced classes of package android.support.v4.media:
//            MediaDescriptionCompat

public static final class 
{

    private CharSequence mDescription;
    private Bundle mExtras;
    private Bitmap mIcon;
    private Uri mIconUri;
    private String mMediaId;
    private Uri mMediaUri;
    private CharSequence mSubtitle;
    private CharSequence mTitle;

    public MediaDescriptionCompat build()
    {
        return new MediaDescriptionCompat(mMediaId, mTitle, mSubtitle, mDescription, mIcon, mIconUri, mExtras, mMediaUri, null);
    }

    public mMediaUri setDescription(CharSequence charsequence)
    {
        mDescription = charsequence;
        return this;
    }

    public mDescription setExtras(Bundle bundle)
    {
        mExtras = bundle;
        return this;
    }

    public mExtras setIconBitmap(Bitmap bitmap)
    {
        mIcon = bitmap;
        return this;
    }

    public mIcon setIconUri(Uri uri)
    {
        mIconUri = uri;
        return this;
    }

    public mIconUri setMediaId(String s)
    {
        mMediaId = s;
        return this;
    }

    public mMediaId setMediaUri(Uri uri)
    {
        mMediaUri = uri;
        return this;
    }

    public mMediaUri setSubtitle(CharSequence charsequence)
    {
        mSubtitle = charsequence;
        return this;
    }

    public mSubtitle setTitle(CharSequence charsequence)
    {
        mTitle = charsequence;
        return this;
    }

    public ()
    {
    }
}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.print;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

// Referenced classes of package android.support.v4.print:
//            PrintHelperKitkat

class Callback extends AsyncTask
{

    final cancel this$1;
    final CancellationSignal val$cancellationSignal;
    final android.print.ayoutResultCallback val$layoutResultCallback;
    final PrintAttributes val$newPrintAttributes;
    final PrintAttributes val$oldPrintAttributes;

    protected transient Bitmap doInBackground(Uri auri[])
    {
        try
        {
            auri = PrintHelperKitkat.access$300(_fld0, imageFile, 3500);
        }
        // Misplaced declaration of an exception variable
        catch (Uri auri[])
        {
            return null;
        }
        return auri;
    }

    protected volatile Object doInBackground(Object aobj[])
    {
        return doInBackground((Uri[])aobj);
    }

    protected void onCancelled(Bitmap bitmap)
    {
        val$layoutResultCallback.onLayoutCancelled();
        oadBitmap = null;
    }

    protected volatile void onCancelled(Object obj)
    {
        onCancelled((Bitmap)obj);
    }

    protected void onPostExecute(Bitmap bitmap)
    {
        boolean flag = true;
        super.onPostExecute(bitmap);
        itmap = bitmap;
        if (bitmap != null)
        {
            bitmap = (new android.print.der(jobName)).setContentType(1).setPageCount(1).build();
            if (val$newPrintAttributes.equals(val$oldPrintAttributes))
            {
                flag = false;
            }
            val$layoutResultCallback.onLayoutFinished(bitmap, flag);
        } else
        {
            val$layoutResultCallback.onLayoutFailed(null);
        }
        oadBitmap = null;
    }

    protected volatile void onPostExecute(Object obj)
    {
        onPostExecute((Bitmap)obj);
    }

    protected void onPreExecute()
    {
        val$cancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() {

            final PrintHelperKitkat._cls2._cls1 this$2;

            public void onCancel()
            {
                cancelLoad();
                cancel(false);
            }

            
            {
                this$2 = PrintHelperKitkat._cls2._cls1.this;
                super();
            }
        });
    }

    Callback()
    {
        this$1 = final_callback;
        val$cancellationSignal = cancellationsignal;
        val$newPrintAttributes = printattributes;
        val$oldPrintAttributes = printattributes1;
        val$layoutResultCallback = android.print.ayoutResultCallback.this;
        super();
    }

    // Unreferenced inner class android/support/v4/print/PrintHelperKitkat$2

/* anonymous class */
    class PrintHelperKitkat._cls2 extends PrintDocumentAdapter
    {

        private PrintAttributes mAttributes;
        Bitmap mBitmap;
        AsyncTask mLoadBitmap;
        final PrintHelperKitkat this$0;
        final PrintHelperKitkat.OnPrintFinishCallback val$callback;
        final int val$fittingMode;
        final Uri val$imageFile;
        final String val$jobName;

        private void cancelLoad()
        {
            synchronized (PrintHelperKitkat.access$400(PrintHelperKitkat.this))
            {
                if (mDecodeOptions != null)
                {
                    mDecodeOptions.requestCancelDecode();
                    mDecodeOptions = null;
                }
            }
            return;
            exception;
            obj;
            JVM INSTR monitorexit ;
            throw exception;
        }

        public void onFinish()
        {
            super.onFinish();
            cancelLoad();
            if (mLoadBitmap != null)
            {
                mLoadBitmap.cancel(true);
            }
            if (callback != null)
            {
                callback.onFinish();
            }
            if (mBitmap != null)
            {
                mBitmap.recycle();
                mBitmap = null;
            }
        }

        public void onLayout(final PrintAttributes oldPrintAttributes, final PrintAttributes newPrintAttributes, final CancellationSignal cancellationSignal, android.print.PrintDocumentAdapter.LayoutResultCallback layoutresultcallback, Bundle bundle)
        {
            boolean flag = true;
            mAttributes = newPrintAttributes;
            if (cancellationSignal.isCanceled())
            {
                layoutresultcallback.onLayoutCancelled();
                return;
            }
            if (mBitmap != null)
            {
                cancellationSignal = (new android.print.PrintDocumentInfo.Builder(jobName)).setContentType(1).setPageCount(1).build();
                if (newPrintAttributes.equals(oldPrintAttributes))
                {
                    flag = false;
                }
                layoutresultcallback.onLayoutFinished(cancellationSignal, flag);
                return;
            } else
            {
                mLoadBitmap = (layoutresultcallback. new PrintHelperKitkat._cls2._cls1()).execute(new Uri[0]);
                return;
            }
        }

        public void onWrite(PageRange apagerange[], ParcelFileDescriptor parcelfiledescriptor, CancellationSignal cancellationsignal, android.print.PrintDocumentAdapter.WriteResultCallback writeresultcallback)
        {
            cancellationsignal = new PrintedPdfDocument(mContext, mAttributes);
            apagerange = PrintHelperKitkat.access$000(PrintHelperKitkat.this, mBitmap, mAttributes.getColorMode());
            android.graphics.pdf.PdfDocument.Page page = cancellationsignal.startPage(1);
            Object obj = new RectF(page.getInfo().getContentRect());
            obj = PrintHelperKitkat.access$100(PrintHelperKitkat.this, mBitmap.getWidth(), mBitmap.getHeight(), ((RectF) (obj)), fittingMode);
            page.getCanvas().drawBitmap(apagerange, ((android.graphics.Matrix) (obj)), null);
            cancellationsignal.finishPage(page);
            cancellationsignal.writeTo(new FileOutputStream(parcelfiledescriptor.getFileDescriptor()));
            writeresultcallback.onWriteFinished(new PageRange[] {
                PageRange.ALL_PAGES
            });
_L1:
            if (cancellationsignal != null)
            {
                cancellationsignal.close();
            }
            IOException ioexception;
            if (parcelfiledescriptor != null)
            {
                try
                {
                    parcelfiledescriptor.close();
                }
                // Misplaced declaration of an exception variable
                catch (ParcelFileDescriptor parcelfiledescriptor) { }
            }
            if (apagerange != mBitmap)
            {
                apagerange.recycle();
            }
            return;
            ioexception;
            Log.e("PrintHelperKitkat", "Error writing printed content", ioexception);
            writeresultcallback.onWriteFailed(null);
              goto _L1
            writeresultcallback;
            if (cancellationsignal != null)
            {
                cancellationsignal.close();
            }
            if (parcelfiledescriptor != null)
            {
                try
                {
                    parcelfiledescriptor.close();
                }
                // Misplaced declaration of an exception variable
                catch (ParcelFileDescriptor parcelfiledescriptor) { }
            }
            if (apagerange != mBitmap)
            {
                apagerange.recycle();
            }
            throw writeresultcallback;
        }


            
            {
                this$0 = final_printhelperkitkat;
                jobName = s;
                imageFile = uri;
                callback = onprintfinishcallback;
                fittingMode = I.this;
                super();
                mBitmap = null;
            }
    }

}

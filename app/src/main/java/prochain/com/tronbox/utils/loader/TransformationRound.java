package prochain.com.tronbox.utils.loader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import prochain.com.tronbox.BuildConfig;


/**
 * 圆角图片

 */
public class TransformationRound extends BitmapTransformation {
    private float radius = 0f;
    private final String ID = BuildConfig.APPLICATION_ID + "TransformationRound." + BuildConfig.VERSION_CODE;
    private final byte[] ID_BYTES = ID.getBytes(CHARSET);

    public TransformationRound() {
        this.radius = 1;
    }

    public TransformationRound(int radius) {
        this.radius = Resources.getSystem().getDisplayMetrics().density * radius;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap toTransform) {
        if (toTransform == null) return null;

        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, toTransform.getWidth(), toTransform.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TransformationRound;
    }
}

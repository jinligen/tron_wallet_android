package prochain.com.tronbox.utils.loader;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;



/**
 * Glide
 * ImageLoader
 *

 */
public class GlideImageLoader implements ImageLoaderUtils.ImageLoader {
    public GlideImageLoader() {
    }

    @Override
    public void loadImage(Context context, Object source, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .dontAnimate();

        GlideApp.with(context)
                .load(source)
//                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, Object source, ImageView imageView, int placeholderImg, int errorImg) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderImg) // 占位符默认图片
                .error(errorImg) // 错误
                .fallback(errorImg) // 当于传递了Null,传递nul
                .dontAnimate();

        GlideApp.with(context)
                .load(source)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadRoundImage(Context context, Object source, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .optionalTransform(new TransformationRound())
                .dontAnimate();

        GlideApp.with(context)
                .load(source)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadRoundImage(Context context, Object source, ImageView imageView, int placeholderImg, int errorImg) {
        RequestOptions options = new RequestOptions()
                .optionalTransform(new TransformationRound())
                .placeholder(placeholderImg)
                .error(errorImg)
                .fallback(errorImg)
                .dontAnimate();

        GlideApp.with(context)
                .load(source)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCircleImage(Context context, Object source, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .optionalTransform(new TransformationCircle())
                .dontAnimate();

        GlideApp.with(context)
                .load(source)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCircleImage(Context context, Object source, ImageView imageView, int errorImg) {
        RequestOptions options = new RequestOptions()
                .optionalTransform(new TransformationCircle())
                .error(errorImg)
                .fallback(errorImg)
                .dontAnimate();

        GlideApp.with(context)
                .load(source)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCircleImage(Context context, Object source, ImageView imageView, int placeholderImg, int errorImg) {
        RequestOptions options = new RequestOptions()
                .optionalTransform(new TransformationCircle())
                .placeholder(placeholderImg)
                .error(errorImg)
                .fallback(errorImg)
                .dontAnimate();

        GlideApp.with(context)
                .load(source)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
    }

    /**
     * 清除图片磁盘缓存
     */
    private static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlideApp.get(context)
                                .clearDiskCache();
                    }
                }).start();
            } else {
                GlideApp.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    private static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                GlideApp.get(context)
                        .clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

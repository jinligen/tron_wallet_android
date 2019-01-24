package prochain.com.tronbox.utils.loader;

import android.content.Context;
import android.widget.ImageView;

/**
 * 图片加载
 * 默认使用Glide框架
 *

 */
public class ImageLoaderUtils {

    private static ImageLoader mImageLoader;

    public static void initImageLoader(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public interface ImageLoader {
        /**
         * 加载图片
         *
         * @param context   Context
         * @param source    Bitmap，Drawable，String，Uri，File，Integer，URL，byte[]，Object
         * @param imageView ImageView
         */
        void loadImage(Context context, Object source, ImageView imageView);

        /**
         * 加载图片
         *
         * @param context        Context
         * @param source         Bitmap，Drawable，String，Uri，File，Integer，URL，byte[]，Object
         * @param imageView      ImageView
         * @param placeholderImg 占位符默认图片
         * @param errorImg       错误图片
         */
        void loadImage(Context context, Object source, ImageView imageView, int placeholderImg, int errorImg);

        /**
         * 方形的圆角图片
         *
         * @param context   Context
         * @param source    Bitmap，Drawable，String，Uri，File，Integer，URL，byte[]，Object
         * @param imageView ImageView
         */
        void loadRoundImage(Context context, Object source, ImageView imageView);

        /**
         * 方形的圆角图片
         *
         * @param context        Context
         * @param source         Bitmap，Drawable，String，Uri，File，Integer，URL，byte[]，Object
         * @param imageView      ImageView
         * @param placeholderImg 占位符默认图片
         * @param errorImg       错误图片
         */
        void loadRoundImage(Context context, Object source, ImageView imageView, int placeholderImg, int errorImg);

        /**
         * 圆形图片
         *
         * @param context   Context
         * @param source    Bitmap，Drawable，String，Uri，File，Integer，URL，byte[]，Object
         * @param imageView ImageView
         */
        void loadCircleImage(Context context, Object source, ImageView imageView);

        /**
         * 圆形图片
         *
         * @param context        Context
         * @param source         Bitmap，Drawable，String，Uri，File，Integer，URL，byte[]，Object
         * @param imageView      ImageView
         * @param errorImg       错误图片
         */
        void loadCircleImage(Context context, Object source, ImageView imageView, int errorImg);

        /**
         * 圆形图片
         *
         * @param context        Context
         * @param source         Bitmap，Drawable，String，Uri，File，Integer，URL，byte[]，Object
         * @param imageView      ImageView
         * @param placeholderImg 占位符默认图片
         * @param errorImg       错误图片
         */
        void loadCircleImage(Context context, Object source, ImageView imageView, int placeholderImg, int errorImg);

        /**
         * 清除缓存
         */
        void clearMemoryCache(Context context);



    }
}

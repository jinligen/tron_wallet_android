package prochain.com.tronbox.utils.loader;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * GlideModule
 * <p>
 * 生成的API仅适用于现在的应用程序。
 * 将生成的API限制为应用程序允许我们有一个API的实现，而不是N个实现，每个库和应用程序都有一个实现。
 * 因此，管理导入并确保特定应用程序中的所有调用路径都具有适用的正确选项要简单得多。
 * 未来版本中可能会解除（实验或其他）限制。
 * 现在，只有在AppGlideModule找到正确注释的情况下才会生成API。
 * AppGlideModule每个应用程序只能有一个。
 * 因此，如果不排除使用该库的应用程序使用生成的API，则无法为库生成API。
 *

 */
@GlideModule
public final class GlideModuleConfig extends AppGlideModule {
    public GlideModuleConfig() {
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
        // 内存缓存配置
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .build();
        // 位图池配置
        MemorySizeCalculator calculatorBitmapPool = new MemorySizeCalculator.Builder(context)
                .setBitmapPoolScreens(3)
                .build();

        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()))
                .setBitmapPool(new LruBitmapPool(calculatorBitmapPool.getBitmapPoolSize()))
                .setDiskCache(new InternalCacheDiskCacheFactory(context, "app_image_manager_disk_cache", diskCacheSizeBytes))
                .setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor())
                .setDefaultRequestOptions(new RequestOptions())
                .setLogLevel(Log.WARN)
                .build(context);
    }
}

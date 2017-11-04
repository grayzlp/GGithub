package com.grayzlp.ggithub.util.glide;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.grayzlp.ggithub.R;

/**
 * Generate a dedicated glide api for this application.
 */

@GlideModule
public class GGithubGlideModule extends AppGlideModule{

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Configure glide
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));

        int diskCacheSizeBytes = 1024 * 1024 * 200; // 200MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));

        builder.setLogLevel(Log.DEBUG);

        // Set default behavior
        builder.setDefaultRequestOptions(
                new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .placeholder(R.drawable.image_placeholder));


    }
}


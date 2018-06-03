package com.hogwarts_eng_school.hogwarts_teacher.view.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.annimon.stream.Objects;
import com.annimon.stream.function.Supplier;
import com.hogwarts_eng_school.hogwarts_teacher.utils.BitmapUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.UiThread;

@EView
public class LoadableImageView extends RoundedImageView {
    private String id;

    public LoadableImageView(Context context) {
        super(context);
    }

    public LoadableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void configure(
            String id,
            int emptyResource,
            Supplier<byte []> cacheProvider,
            Supplier<byte []> loadingProvider
    ) {
        this.id = id;

        loadImageFromCache(id, emptyResource, cacheProvider, loadingProvider);
    }

    @Background
    void loadImageFromCache(String id, int emptyResource, Supplier<byte []> cacheProvider, Supplier<byte []> loadingProvider) {
        byte [] imageFromCache = cacheProvider.get();

        if (imageFromCache != null) {
            setImage(emptyResource);
            setImage(BitmapUtils.iconFromBytes(imageFromCache));
        } else {
            setImage(emptyResource);

            try {
                loadImageFromNetwork(id, emptyResource, loadingProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void loadImageFromNetwork(String id, int emptyResource, Supplier<byte []> loadingProvider) {
        byte [] image = loadingProvider.get();

        synchronized (LoadableImageView.class) {
            if (Objects.equals(this.id, id)) {
                if (image == null || image.length == 0) {
                    setImage(emptyResource);
                } else {
                    setImage(BitmapUtils.iconFromBytes(image));
                }
            }
        }
    }

    @UiThread
    void setImage(int resource) {
        setImageResource(resource);
    }

    @UiThread
    void setImage(Bitmap image) {
        setImageBitmap(image);
    }
}
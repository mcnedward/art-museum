package com.mcnedward.museum;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mcnedward.museum.enums.ScalingLogic;
import com.mcnedward.museum.model.Image;

import java.lang.ref.WeakReference;

/**
 * Created by Edward on 3/18/2016.
 */
public class ImageCard extends LinearLayout {

    private Context context;
    private WeakReference<ImageLoaderTask> taskReference;
    private ImageView imageView;

    public ImageCard(Context context) {
        super(context);
        initialize(context);
    }

    public ImageCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        inflate(context, R.layout.view_image_card, this);
        resetCard();
        imageView = (ImageView) findViewById(R.id.image_card);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void setImageBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    public void resetCard() {
        setBackground(ContextCompat.getDrawable(context, R.drawable.empty));
    }

    public void handleImageMedia(Image image) {
        if (cancelPotentialLoad(image)) {
            ImageLoaderTask task = new ImageLoaderTask(this);
            taskReference = new WeakReference<>(task);
            task.execute(image);
        }
    }

    private boolean cancelPotentialLoad(Image image) {
        ImageLoaderTask task = getImageLoaderTask(this);
        if (task != null) {
            Image taskImage = task.image;
            if ((taskImage == null) || (!taskImage.equals(image))) {
                task.cancel(true);
            } else
                return false;
        }
        return true;
    }

    private ImageLoaderTask getImageLoaderTask(ImageCard imageCard) {
        if (imageCard != null && taskReference != null) {
            return taskReference.get();
        }
        return null;
    }

    class ImageLoaderTask extends AsyncTask<Image, Void, Bitmap> {

        private Image image;
        private final WeakReference<ImageCard> imageCardWeakReference;

        public ImageLoaderTask(ImageCard imageCard) {
            imageCardWeakReference = new WeakReference<>(imageCard);
        }

        @Override
        protected Bitmap doInBackground(Image... images) {
            this.image = images[0];
            Bitmap unscaledBitmap = BitmapUtil.decodeFile(image.getPath(), ImageSize.LARGE.size, ImageSize.LARGE.size, ScalingLogic.FIT);
            if (unscaledBitmap == null) {
                cancel(true);
                return null;
            }
            return BitmapUtil.scaleBitmap(unscaledBitmap, ImageSize.LARGE.size, ImageSize.LARGE.size, ScalingLogic.FIT);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled())
                bitmap = null;

            if (imageCardWeakReference != null) {
                ImageCard imageCard = imageCardWeakReference.get();
                ImageLoaderTask task = getImageLoaderTask(imageCard);
                if (this == task)
                    imageCard.setImageBitmap(bitmap);
                else
                    imageCard.resetCard();
            }
        }
    }
}

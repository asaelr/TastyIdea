package com.example.asaelr.tastyidea;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.kinvey.java.Query;
import com.kinvey.java.core.DownloaderProgressListener;
import com.kinvey.java.core.MediaHttpDownloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by asael on 21/05/16.
 */
public class ImageDownloader {
    private final ImageView imageView;
    private final Recipe recipe;

    public ImageDownloader(Recipe recipe, ImageView image) {
        this.recipe = recipe;
        this.imageView = image;
        if (recipe.image != null) updateFinalPicture();
        else download();
    }

    public void download() {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Query query = new Query();
        String filename = "image"+recipe.getId()+".png";
        query.equals("_filename",filename);
        Log.i("downloader","trying to download file "+filename);
        networking.Networking.getClient().file().download(query, stream, new DownloaderProgressListener() {

            boolean complete = false;

            @Override
            public void progressChanged(MediaHttpDownloader mediaHttpDownloader) throws IOException {
                switch (mediaHttpDownloader.getDownloadState()) {
                    case DOWNLOAD_COMPLETE:
                        Log.i("downloader","complete");
                        complete = true;
                        break;
                    case DOWNLOAD_FAILED_FILE_NOT_FOUND:
                        Log.i("downloader","DOWNLOAD_FAILED_FILE_NOT_FOUND");
                        break;
                    case DOWNLOAD_IN_PROGRESS:
                        Log.i("downloader","DOWNLOAD_IN_PROGRESS");
                        break;
                    case INITIATION_STARTED:
                        Log.i("downloader","INITIATION_STARTED");
                        break;
                    case NOT_STARTED:
                        Log.i("downloader","NOT_STARTED");
                        break;
                }

            }

            @Override
            public void onSuccess(Void aVoid) {
                Log.i("downloader","success");
                if (complete) {
                    recipe.image = stream.toByteArray();
                    updateFinalPicture();
                }
}

            @Override
            public void onFailure(Throwable throwable) {
                Log.i("downloader","failed",throwable);

            }
        });
    }

    private void updateFinalPicture() {
        Bitmap bm =  BitmapFactory.decodeByteArray(recipe.image,0,recipe.image.length);
        imageView.setImageBitmap(bm);

    }
}

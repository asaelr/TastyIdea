package com.example.asaelr.tastyidea;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kinvey.java.Query;
import com.kinvey.java.core.DownloaderProgressListener;
import com.kinvey.java.core.MediaHttpDownloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import networking.RecipeMetadata;

/**
 * Created by asael on 21/05/16.
 */
public class ImageDownloader {
    private static Map<String, Bitmap> map = new HashMap<>();
    private final ImageView imageView;
    private final ProgressBar progressBar;
    private final String id;

    public ImageDownloader(String id, ImageView imageView, ProgressBar progressBar) {
        this.id = id;
        this.imageView = imageView;
        this.progressBar = progressBar;
        imageView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        if (map.containsKey(id)) {
            Log.i("downloader","updating from map "+id);
            updatePicture();
        }
        else download();

    }

    public void download() {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Query query = new Query();
        final String filename = "image"+id+".png";
        query.equals("_filename",filename);
        Log.i("downloader","trying to download file "+filename);
        networking.Networking.getClient().file().download(query, stream, new DownloaderProgressListener() {

            boolean failed = false;
            //Handler handler = new Handler();

            @Override
            public void progressChanged(final MediaHttpDownloader mediaHttpDownloader) throws IOException {
                switch (mediaHttpDownloader.getDownloadState()) {
                    case DOWNLOAD_COMPLETE:
                        Log.i("downloader",filename+" DOWNLOAD_COMPLETE");
                        break;
                    case DOWNLOAD_FAILED_FILE_NOT_FOUND:
                        Log.i("downloader",filename+" DOWNLOAD_FAILED_FILE_NOT_FOUND");
                        break;
                    case DOWNLOAD_IN_PROGRESS:
                        Log.i("downloader",filename+" DOWNLOAD_IN_PROGRESS");
                        break;
                    case INITIATION_STARTED:
                        Log.i("downloader",filename+" INITIATION_STARTED");
                        break;
                    case NOT_STARTED:
                        Log.i("downloader",filename+" NOT_STARTED");
                        break;
                }

            }

            @Override
            public void onSuccess(Void aVoid) {
                Log.i("downloader",filename+" success, failed="+failed);
                if (!failed && stream.size()>0) {
                    Bitmap bm = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());
                    Log.i("downloader", "putting " + filename + " in map. size: " + stream.size());
                    map.put(id, bm);
                }
                updatePicture();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.i("downloader",filename+" failed");
                failed = true;
                updatePicture();

            }
        });
    }

    private void updatePicture() {
        progressBar.setVisibility(View.INVISIBLE);
        Log.i("downloader:updPic","progress->GONE");
        imageView.setVisibility(View.VISIBLE);
        if (map.containsKey(id)) {
            imageView.setImageBitmap(map.get(id));
        }
        else {
            imageView.setImageResource(R.drawable.logo_small);
        }
        //imageView.postInvalidate();

    }
}

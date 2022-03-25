package fr.uge.hyperstack.model.media;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.loader.content.CursorLoader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

/**
 * A mp3 file
 */
public class Sound implements Serializable {
    private final String name;
    private transient Bitmap cachedSound = null;
    private final MediaPlayer player = new MediaPlayer();

    public static final String DATA_LOCATION = "data";

    public Sound(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void playSound(Context context, Uri uri) throws IOException {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(":");
        String path = Environment.getExternalStorageDirectory() + "/" + split[1];
        player.setDataSource(path);
        player.setOnPreparedListener(mediaPlayer -> player.start());
        player.prepareAsync();
    }

    public void pauseSound() {
        if (player.isPlaying()) {
            player.pause();
        }
    }

    public void stopSound() {
        if (player.isPlaying()) {
            player.stop();
        }
    }

    public void getSound(Context context) throws IOException {
        if (cachedSound != null) return;
        AssetFileDescriptor afd = context.getAssets().openFd(DATA_LOCATION + "/sound/" + name + ".mp3");
        MediaPlayer player = new MediaPlayer();
        player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        player.prepare();
        player.start();
        try (InputStream is = context.getAssets().open(DATA_LOCATION + "/sound/" + name + ".mp3")) {
            cachedSound = BitmapFactory.decodeStream(is);
        }
    }

    public static List<Sound> loadSoundList(Context context) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(DATA_LOCATION + "/sounds.json")));
        StringBuilder sb = new StringBuilder();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            sb.append(line).append("\n");
        }
        String text = sb.toString();
        return new Gson().fromJson(text, new TypeToken<List<Sound>>() {
        }.getType());
    }
}
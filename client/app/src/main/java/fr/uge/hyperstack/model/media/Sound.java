package fr.uge.hyperstack.model.media;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

import fr.uge.hyperstack.model.ElementVisitor;
import fr.uge.hyperstack.model.MediaElement;

/**
 * A mp3 file
 */
public class Sound implements MediaElement, Serializable {
    private final String name;
    private transient Bitmap cachedSound = null;
    private final MediaPlayer player = new MediaPlayer();
    private float x;
    private float y;
    private float width;
    private float height;

    public static final String DATA_LOCATION = "data";

    public Sound(String name) {
        this.name = name;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setSoundFromExternalStorage(Context context, Uri uri) throws IOException {
        player.setDataSource(context, uri);
        player.prepareAsync();
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void playSound() {
        player.start();
    }

    public void pauseSound() {
        player.pause();
    }

    public void setSoundFromAssets(Context context) throws IOException {
        if (cachedSound != null) return;
        AssetFileDescriptor afd = context.getAssets().openFd(DATA_LOCATION + "/sound/" + name + ".mp3");
        player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//        player.setOnPreparedListener(mediaPlayer -> player.start());
        player.prepareAsync();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getFileFromUri(Uri uri) {
        String[] split = uri.getPath().split(":");
        String[] directories = split[1].split("/");
        return directories[directories.length - 1];
    }

    public String getName() {
        return name;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    @Override
    public float getPositionX() {
        return x;
    }

    @Override
    public float getPositionY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float convertBiasHorizontal(float maxWidth) {
        return (x + width / 2) / maxWidth;
    }

    @Override
    public float convertBiasVertical(float maxHeight) {
        return (y + height / 2) / maxHeight;
    }

    @Override
    public void accept(ElementVisitor elementVisitor) {
        elementVisitor.draw(this);
    }
}
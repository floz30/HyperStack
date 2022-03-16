package fr.uge.hyperstack.model.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/** A mp3 file */
public class Sound {
    private final String name;
    private transient Bitmap cachedSound = null;

    public static final String DATA_LOCATION = "data";

    public Sound(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // TODO return MediaPlayer
    public Bitmap getSound(Context context) throws IOException {
        if (cachedSound != null) return cachedSound;
        AssetFileDescriptor afd = context.getAssets().openFd(DATA_LOCATION + "/sound/" + name + ".mp3");
        MediaPlayer player = new MediaPlayer();
        player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
        player.prepare();
        player.start();
        InputStream is = context.getAssets().open(DATA_LOCATION + "/sound/" + name + ".mp3");
        try {
            Bitmap b = BitmapFactory.decodeStream(is);
            cachedSound = b;
            return b;
        } finally {
            is.close();
        }
    }

    public static List<Sound> loadList(Context context) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(DATA_LOCATION + "/sounds.json")));
        StringBuilder sb = new StringBuilder();
        for (String line = br.readLine(); line != null; line = br.readLine())
            sb.append(line + "\n");
        String text = sb.toString();
        return new Gson().fromJson(text, new TypeToken<List<Sound>>() {}.getType());
    }

}

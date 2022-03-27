package fr.uge.hyperstack.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.media.Sound;
import fr.uge.hyperstack.utils.Permission;

public class SoundListActivity extends AppCompatActivity {
    private static final int READ_FILE_PERMISSION_CODE = 1;

    private RecyclerView recyclerView;
    private SoundListAdapter soundItemAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_list);
        recyclerView = findViewById(R.id.soundList);
        try {
            soundItemAdapter = new SoundListAdapter(this, Sound.loadSoundList(this.getApplicationContext()));
        } catch (IOException e) {
            Log.e("aled", "????", e);
        }
        recyclerView.setAdapter(soundItemAdapter);
        updateLayoutManager(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_FILE_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(this, "File Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "File Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void updateLayoutManager(View v) {
        recyclerView.setLayoutManager(createLayoutManager());
    }

    public void loadSound(Sound sound) throws IOException {
        // TODO retourner sur le slide + afficher le player pour le son
//        sound.setSoundFromAssets(this);
        Intent intent = new Intent();
        intent.putExtra("sound", sound);
        setResult(Permission.SOUND_TAKEN_FROM_APP_REQUEST_CODE, intent);
        finish();
    }

    public static class SoundListAdapter extends RecyclerView.Adapter<SoundListAdapter.ViewHolder> {
        private final SoundListActivity activity;
        private final List<Sound> soundList;

        public SoundListAdapter(SoundListActivity activity, List<Sound> soundList) {
            this.activity = activity;
            this.soundList = soundList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_name, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Sound sound = soundList.get(position);
            holder.update(sound);
        }

        @Override
        public int getItemCount() {
            return soundList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView soundText;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.soundText = itemView.findViewById(R.id.soundName);
            }

            private void update(Sound sound) {
                soundText.setText(sound.getName());
                soundText.setOnClickListener(view -> {
                    try {
                        activity.loadSound(sound);
                    } catch (IOException e) {
                        Log.e("soundIO", ":)", e);
                    }
                });
            }
        }
    }
}

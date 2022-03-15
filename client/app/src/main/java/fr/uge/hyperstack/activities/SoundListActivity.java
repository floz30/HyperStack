package fr.uge.hyperstack.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.sound.Sound;

public class SoundListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SoundListAdapter soundItemAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_list);
        recyclerView = findViewById(R.id.soundList);
        try {
            soundItemAdapter = new SoundListAdapter(this, Sound.loadList(this.getApplicationContext()));
        } catch (IOException e) {
            Log.e("aled", "????", e);
        }
        recyclerView.setAdapter(soundItemAdapter);
        updateLayoutManager(null);
    }

    private RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void updateLayoutManager(View v) {
        recyclerView.setLayoutManager(createLayoutManager());
    }

    public void loadSound(Sound sound) throws IOException {
        // TODO retourner sur le slide + afficher le player pour le son
        sound.getSound(this.getApplicationContext());
    }

    public class SoundListAdapter extends RecyclerView.Adapter<SoundListAdapter.ViewHolder> {
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

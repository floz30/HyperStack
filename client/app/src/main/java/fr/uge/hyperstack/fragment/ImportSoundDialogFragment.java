package fr.uge.hyperstack.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.activities.SoundListActivity;
import fr.uge.hyperstack.model.media.Sound;
import fr.uge.hyperstack.utils.Permission;

public class ImportSoundDialogFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.modal_fragment_import, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.modalImportRecyclerView);
        recyclerView.setAdapter(new ImportSoundItemAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    private void loadSoundList() {
        Intent intent = new Intent(requireActivity(), SoundListActivity.class);
        requireActivity().startActivityForResult(intent, Permission.SOUND_TAKEN_FROM_APP_REQUEST_CODE);
    }

    public class ImportSoundItemAdapter extends RecyclerView.Adapter<ImportSoundDialogFragment.ImportSoundItemAdapter.ViewHolder> {
        private final Activity activity;
        private final List<ImportSoundDialogFragment.ImportSoundItemAdapter.ImportSoundItem> items;


        public ImportSoundItemAdapter(Activity activity) {
            super();
            this.activity = activity;
            this.items = new ArrayList<>();
            items.add(new ImportSoundItem(R.drawable.ic_download, "Importer un son"));
            items.add(new ImportSoundItem(R.drawable.ic_sound, "Prendre un son"));
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_fragment_import_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ImportSoundDialogFragment.ImportSoundItemAdapter.ImportSoundItem current = items.get(position);
            holder.bind(current);
            holder.labelTextView.setOnClickListener(view -> {
                // TODO position en enum
                switch (position) {
                    case 0:
                        try {
                            importSoundFromUser();
                        } catch (IOException e) {
                            Log.e("SoundIO", "File not found", e);
                        }
                        break;
                    case 1:
                        loadSoundList();
                        break;
                    default:
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        private void importSoundFromUser() throws IOException {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setDataAndType(Uri.parse(MediaStore.Audio.Media.DATA), "audio/*");
                    activity.startActivityForResult(Intent.createChooser(intent, "SoundImport"), Permission.SOUND_IMPORT_REQUEST_CODE);
                }
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Permission.SOUND_IMPORT_REQUEST_CODE);
            }
        }

        private class ImportSoundItem {
            private final int iconId;
            private final String label;

            ImportSoundItem(int iconId, String label) {
                this.iconId = iconId;
                this.label = label;
            }
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView iconImageView;
            private final TextView labelTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.iconImageView = itemView.findViewById(R.id.importItemIcon);
                this.labelTextView = itemView.findViewById(R.id.importItemTextView);
            }

            /**
             * Lie les données de la présentation à l'interface graphique.
             *
             * @param item
             */
            private void bind(ImportSoundDialogFragment.ImportSoundItemAdapter.ImportSoundItem item) {
                iconImageView.setImageResource(item.iconId);
                labelTextView.setText(item.label);
            }
        }
    }

}

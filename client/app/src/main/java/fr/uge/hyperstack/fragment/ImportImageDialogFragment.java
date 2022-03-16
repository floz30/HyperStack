package fr.uge.hyperstack.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.utils.Permission;

public class ImportImageDialogFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.modal_fragment_import, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.modalImportRecyclerView);
        recyclerView.setAdapter(new ImportImageItemAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    public class ImportImageItemAdapter extends RecyclerView.Adapter<ImportImageItemAdapter.ImportImageItemViewHolder> {

        private final ImportImageItem[] items = {
                new ImportImageItem(R.drawable.ic_download, "Importer une image", "", 1),
                new ImportImageItem(R.drawable.ic_download, "Importer une vidéo", "", 2),
                new ImportImageItem(R.drawable.ic_camera, "Prendre une photo", MediaStore.ACTION_IMAGE_CAPTURE, Permission.IMAGE_CAPTURE_REQUEST_CODE),
                new ImportImageItem(R.drawable.ic_video, "Prendre une vidéo", MediaStore.ACTION_VIDEO_CAPTURE, Permission.VIDEO_CAPTURE_REQUEST_CODE)
        };

        private class ImportImageItem {
            private final int iconId;
            private final String label;
            private final String action;
            private final int requestCode;

            ImportImageItem(int iconId, String label, String action, int requestCode) {
                this.iconId = iconId;
                this.label = label;
                this.action = action;
                this.requestCode = requestCode;
            }
        }

        @NonNull
        @Override
        public ImportImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ImportImageItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.modal_fragment_import_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ImportImageItemViewHolder holder, int position) {
            ImportImageItem current = items[position];
            holder.bind(current);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }

        private class ImportImageItemViewHolder extends RecyclerView.ViewHolder {
            private final ImageView iconImageView;
            private final TextView labelTextView;

            public ImportImageItemViewHolder(@NonNull View itemView) {
                super(itemView);
                this.iconImageView = itemView.findViewById(R.id.importItemIcon);
                this.labelTextView = itemView.findViewById(R.id.importItemTextView);
            }

            /**
             * Lie les données de la présentation à l'interface graphique.
             *
             * @param item
             */
            private void bind(ImportImageItem item) {
                iconImageView.setImageResource(item.iconId);
                labelTextView.setText(item.label);

                labelTextView.setOnClickListener(view -> {
                    Intent cameraIntent = new Intent(item.action);
                    getActivity().startActivityForResult(cameraIntent, item.requestCode);
                });
            }
        }
    }


}

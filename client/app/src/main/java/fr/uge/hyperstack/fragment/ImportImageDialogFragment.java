package fr.uge.hyperstack.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;

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
        private final List<ImportImageItem> items;

        private class ImportImageItem {
            private final int iconId;
            private final String label;

            ImportImageItem(int iconId, String label) {
                this.iconId = iconId;
                this.label = label;
            }
        }

        public ImportImageItemAdapter() {
            super();
            this.items = new ArrayList<>();
            items.add(new ImportImageItem(R.drawable.ic_download, "Importer une image"));
            items.add(new ImportImageItem(R.drawable.ic_download, "Importer une vidéo"));
            items.add(new ImportImageItem(R.drawable.ic_camera, "Prendre une photo"));
            items.add(new ImportImageItem(R.drawable.ic_video, "Prendre une vidéo"));
        }

        @NonNull
        @Override
        public ImportImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ImportImageItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.modal_fragment_import_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ImportImageItemViewHolder holder, int position) {
            ImportImageItem current = items.get(position);
            holder.bind(current);
            // TODO : ajouter un click listener
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ImportImageItemViewHolder extends RecyclerView.ViewHolder {
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
            }
        }
    }


}

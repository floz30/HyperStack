package fr.uge.hyperstack.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.activities.SoundListActivity;

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

    public static class ImportSoundItemAdapter extends RecyclerView.Adapter<ImportSoundDialogFragment.ImportSoundItemAdapter.ViewHolder> {
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
            holder.labelTextView.setOnClickListener(view -> this.loadSoundList());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        private void loadSoundList() {
            Intent intent = new Intent(activity, SoundListActivity.class);
            activity.startActivity(intent);
        }

        private static class ImportSoundItem {
            private final int iconId;
            private final String label;

            ImportSoundItem(int iconId, String label) {
                this.iconId = iconId;
                this.label = label;
            }
        }

        private static class ViewHolder extends RecyclerView.ViewHolder {
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

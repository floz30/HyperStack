package fr.uge.hyperstack.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.BuildConfig;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.utils.Permission;

public class ImportImageDialogFragment extends BottomSheetDialogFragment {
    private final Context context;
    private String fileLocation;

    public ImportImageDialogFragment(Context context) {
        this.context = context;
    }

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

    public String getFileLocation() {
        return fileLocation;
    }

    private File createImageFile(String format) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, format, storageDir);
        fileLocation = image.getAbsolutePath();
        return image;
    }


    public class ImportImageItemAdapter extends RecyclerView.Adapter<ImportImageItemAdapter.ImportImageItemViewHolder> {

        private final ImportImageItem[] items = {
                new ImportImageItem(R.drawable.ic_download, "Importer une image", Intent.ACTION_GET_CONTENT, Permission.IMAGE_IMPORT_REQUEST_CODE, ".jpg", true),
                new ImportImageItem(R.drawable.ic_download, "Importer une vidéo", Intent.ACTION_GET_CONTENT, Permission.VIDEO_IMPORT_REQUEST_CODE, ".mp4", true),
                new ImportImageItem(R.drawable.ic_camera, "Prendre une photo", MediaStore.ACTION_IMAGE_CAPTURE, Permission.IMAGE_CAPTURE_REQUEST_CODE, ".jpg", false),
                new ImportImageItem(R.drawable.ic_video, "Prendre une vidéo", MediaStore.ACTION_VIDEO_CAPTURE, Permission.VIDEO_CAPTURE_REQUEST_CODE, ".mp4", false)
        };

        private class ImportImageItem {
            private final int iconId;
            private final String label;
            private final String action;
            private final int requestCode;
            private final String format;
            private final boolean isImportation;

            ImportImageItem(int iconId, String label, String action, int requestCode, String format, boolean isImportation) {
                this.iconId = iconId;
                this.label = label;
                this.action = action;
                this.requestCode = requestCode;
                this.format = format;
                this.isImportation = isImportation;
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
                    if (item.isImportation) {
                        Intent intent = new Intent();
                        intent.setAction(item.action);
                        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/* video/*");
                        getActivity().startActivityForResult(Intent.createChooser(intent, "Importation d'un média"), item.requestCode);
                    } else {
                        Intent intent = new Intent(item.action);
                        try {
                            File file = createImageFile(item.format);
                            Uri uri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            getActivity().startActivityForResult(intent, item.requestCode);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }


}

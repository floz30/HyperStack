package fr.uge.hyperstack.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import fr.uge.hyperstack.R;

/**
 * Fenêtre de dialogue demandant les informations nécessaires pour la création d'une nouvelle
 * présentation. L'utilisateur peut valider ou annuler via deux boutons.
 * <p>
 * Cette fenêtre gère correctement les événements du cycle de vie de l'application.
 */
public class CreationStackDialogFragment extends DialogFragment {
    private TwoButtonsDialogListener listener;
    private String title;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_creation_stack, null);
        EditText input = view.findViewById(R.id.inputTitleCreationDialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        title = input.getText().toString().trim();
                        listener.onDialogPositiveClick(CreationStackDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogNegativeClick(CreationStackDialogFragment.this);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (TwoButtonsDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(requireActivity().toString() + " must implement NoticeDialogListener");
        }
    }


    public String getTitle() {
        return title;
    }
}

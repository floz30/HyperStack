package fr.uge.hyperstack.fragment;

import androidx.fragment.app.DialogFragment;

public interface TwoButtonsDialogListener {

    void onDialogPositiveClick(DialogFragment dialog);

    void onDialogNegativeClick(DialogFragment dialog);
}

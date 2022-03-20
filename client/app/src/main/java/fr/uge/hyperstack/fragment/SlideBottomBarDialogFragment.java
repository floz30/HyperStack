package fr.uge.hyperstack.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.adapter.SlideBottomBarAdapter;
import fr.uge.hyperstack.model.Slide;

public class SlideBottomBarDialogFragment extends BottomSheetDialogFragment {

    private final List<Slide> slides;

    public SlideBottomBarDialogFragment(List<Slide> slides) {
        this.slides = slides;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.modal_slide_bottom_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.bottomBarRecyclerView);
        recyclerView.setAdapter(new SlideBottomBarAdapter(slides));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}

package fr.uge.hyperstack.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.activities.EditActivity;
import fr.uge.hyperstack.adapter.SlideBottomBarAdapter;
import fr.uge.hyperstack.model.Slide;
import fr.uge.hyperstack.view.listener.OnSlideChangeListener;

public class SlideBottomBarDialogFragment extends BottomSheetDialogFragment implements OnSlideChangeListener {

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
        recyclerView.setAdapter(new SlideBottomBarAdapter(slides, this::onSlideChangeListener));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onSlideChangeListener(int currentSlideNumber) {
        EditActivity activity = (EditActivity) getActivity();
        activity.selectSlide(currentSlideNumber);
    }
}

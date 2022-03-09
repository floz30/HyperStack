package fr.uge.hyperstack.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.Slide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SlideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SlideFragment extends Fragment {

    private Slide slide;

    private int slideNumber;

    public SlideFragment() {
        // Required empty public constructor
    }

    public SlideFragment(Slide slide, int position) {
        this.slide = slide;
        this.slideNumber = position;
    }

    public static SlideFragment newInstance(Slide slide, int slideNumber) {
        SlideFragment fragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putSerializable("slide", slide);
        args.putInt("slideNumber", slideNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            slide = (Slide) getArguments().get("slide");
            slideNumber = getArguments().getInt("slideNumber");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_slide, container, false);

        TextView tv = view.findViewById(R.id.textViewFragment);
        String title = "Slide number " + slideNumber;
        tv.setText(title);
        return view;
    }
}
package fr.uge.hyperstack.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.uge.hyperstack.model.Slide;

public class SlideFragmentStateAdapter extends FragmentStateAdapter {

    private List<Slide> slides;


    public SlideFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        this.slides = this.initSlides();
    }

    private List<Slide> initSlides()  {
        Slide emp1 = new Slide();
        Slide emp2 = new Slide();
        Slide emp3 = new Slide();

        List<Slide> list = new ArrayList<>();
        list.add(emp1);
        list.add(emp2);
        list.add(emp3);
        return list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Slide slide = this.slides.get(position);
        return new SlideFragment(slide, position);
    }


    @Override
    public int getItemCount() {
        return this.slides.size();
    }
}

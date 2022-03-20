package fr.uge.hyperstack.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.Slide;

public class SlideBottomBarAdapter extends RecyclerView.Adapter<SlideBottomBarAdapter.SlideBottomBarViewHolder> {
    private final Slide[] slides;

    public SlideBottomBarAdapter(List<Slide> slides) {
        super();
        this.slides = slides.toArray(new Slide[0]);
    }

    @NonNull
    @Override
    public SlideBottomBarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideBottomBarViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.slide_bottom_bar_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideBottomBarViewHolder holder, int position) {
        Slide currentSlide = slides[position];
        holder.bind(currentSlide);
    }

    @Override
    public int getItemCount() {
        return slides.length;
    }

    static class SlideBottomBarViewHolder extends RecyclerView.ViewHolder {
        private final Resources resources;
        private final TextView labelTextView;

        public SlideBottomBarViewHolder(@NonNull View itemView) {
            super(itemView);
            this.labelTextView = itemView.findViewById(R.id.slideLabelBottomBarItem);
            this.resources = itemView.getContext().getResources();
        }

        public void bind(Slide slide) {
            labelTextView.setText(String.format(resources.getString(R.string.slide_number_item), slide.getSlideNumber()));
        }
    }
}

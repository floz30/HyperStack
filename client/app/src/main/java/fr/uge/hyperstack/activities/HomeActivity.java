package fr.uge.hyperstack.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.Slide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final List<Slide> slides = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        slides.add(new Slide("Cours Android"));
        slides.add(new Slide("Cours JEE"));
        slides.add(new Slide("CV"));

        RecyclerView recyclerView = findViewById(R.id.slidesRV);
        recyclerView.setAdapter(new SlideAdapter(slides));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

        private final List<Slide> slides;

        public SlideAdapter(List<Slide> slides) {
            super();
            this.slides = slides;
        }

        @NonNull
        @Override
        public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SlideViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
            holder.bind(slides.get(position));

        }

        @Override
        public int getItemCount() {
            return slides.size();
        }


        public class SlideViewHolder extends RecyclerView.ViewHolder {
            private final TextView slideNameTextView;
            private final ImageView slideImageView;

            public SlideViewHolder(@NonNull View itemView) {
                super(itemView);
                this.slideNameTextView = itemView.findViewById(R.id.slideNameTextView);
                this.slideImageView = itemView.findViewById(R.id.slideImageView);
            }

            private void bind(Slide slide) {
                slideNameTextView.setText(slide.getTitle());

                slideImageView.setImageBitmap(slide.getImage(slideImageView.getContext()));
            }
        }
    }
}
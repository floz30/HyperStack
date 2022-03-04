package fr.uge.hyperstack.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.Stack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final List<Stack> stacks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stacks.add(new Stack("Cours Android"));
        stacks.add(new Stack("Cours JEE"));
        stacks.add(new Stack("CV"));
        stacks.add(new Stack("Scala"));
        stacks.add(new Stack("Batch Processing"));

        RecyclerView recyclerView = findViewById(R.id.stacksRecyclerView);
        recyclerView.setAdapter(new StackAdapter(stacks));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public class StackAdapter extends RecyclerView.Adapter<StackAdapter.StackViewHolder> {

        private final List<Stack> stacks;

        public StackAdapter(List<Stack> stacks) {
            super();
            this.stacks = stacks;
        }

        @NonNull
        @Override
        public StackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StackViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull StackViewHolder holder, int position) {
            holder.bind(stacks.get(position));

        }

        @Override
        public int getItemCount() {
            return stacks.size();
        }


        public class StackViewHolder extends RecyclerView.ViewHolder {
            private final TextView stackTitleTextView;
            private final ImageView stackImageView;

            public StackViewHolder(@NonNull View itemView) {
                super(itemView);
                this.stackTitleTextView = itemView.findViewById(R.id.stackTitleTextView);
                this.stackImageView = itemView.findViewById(R.id.stackImageView);
            }

            private void bind(Stack stack) {
                stackTitleTextView.setText(stack.getTitle());

                stackImageView.setImageBitmap(stack.getImage(stackImageView.getContext()));
            }
        }
    }
}
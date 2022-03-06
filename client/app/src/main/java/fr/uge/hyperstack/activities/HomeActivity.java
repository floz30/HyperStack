package fr.uge.hyperstack.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.model.Stack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final List<Stack> stacks = new ArrayList<>();
    private StackAdapter stackAdapter;

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
        stackAdapter = new StackAdapter(stacks);
        recyclerView.setAdapter(stackAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.creationStack:
                createNewStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Initialise une nouvelle présentation en demandant les informations nécessaires à sa création.
     */
    private void createNewStack() {
        stackAdapter.addStack(new Stack("Toto")); // exemple à retirer
        // TODO : afficher une fenêtre de dialogue pour demander le nom de la nouvelle présentation et afficher EditActivity
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
            Stack currentStack = stacks.get(position);
            holder.bind(currentStack);
            holder.stackImageView.setOnClickListener(v -> {
                Intent editIntent = new Intent(getApplicationContext(), EditActivity.class);
                editIntent.putExtra("stack", currentStack);
                startActivity(editIntent);
            });
        }

        @Override
        public int getItemCount() {
            return stacks.size();
        }

        public void addStack(Stack stack) {
            stacks.add(stack);
            notifyItemInserted(stacks.size());
        }


        public class StackViewHolder extends RecyclerView.ViewHolder {
            private final TextView stackTitleTextView;
            private final ImageView stackImageView;

            public StackViewHolder(@NonNull View itemView) {
                super(itemView);
                this.stackTitleTextView = itemView.findViewById(R.id.stackTitleTextView);
                this.stackImageView = itemView.findViewById(R.id.stackImageView);
            }

            /**
             * Lie les données de la présentation à l'interface graphique.
             *
             * @param stack les données de la présentation.
             */
            private void bind(Stack stack) {
                stackTitleTextView.setText(stack.getTitle());

                stackImageView.setImageBitmap(stack.getImage(stackImageView.getContext()));
            }
        }
    }
}
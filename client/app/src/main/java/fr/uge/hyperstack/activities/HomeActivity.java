package fr.uge.hyperstack.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.fragment.CreationStackDialogFragment;
import fr.uge.hyperstack.fragment.TwoButtonsDialogListener;
import fr.uge.hyperstack.model.Stack;

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
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements TwoButtonsDialogListener {
    private final List<Stack> stacks = new ArrayList<>();
    private StackAdapter stackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Stack stack = new Stack("Cours Android");
        stack.addNewSlide();
        stack.addNewSlide();
        stacks.add(stack);
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
                showCreationStackDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Affiche une fenêtre de dialogue invitant à rentrer le nom de la nouvelle présentation.
     */
    private void showCreationStackDialog() {
        DialogFragment newFragment = new CreationStackDialogFragment();
        newFragment.show(getSupportFragmentManager(), "creation");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        CreationStackDialogFragment dialogContext = (CreationStackDialogFragment) dialog;
        String title = dialogContext.getTitle();
        if (title.isEmpty()) {
            // Solution temporaire
            Toast toast = Toast.makeText(getApplicationContext(), "Le nom de la présentation ne peut pas être vide.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Stack newStack = new Stack(title);
        stackAdapter.addStack(newStack);

        // On envoie vers l'écran de modification
        Intent editIntent = new Intent(getApplicationContext(), EditActivity.class);
        editIntent.putExtra("stack", newStack);
        startActivity(editIntent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Do nothing if cancelled
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
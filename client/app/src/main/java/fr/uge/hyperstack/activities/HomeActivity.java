package fr.uge.hyperstack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.adapter.StackAdapter;
import fr.uge.hyperstack.fragment.CreationStackDialogFragment;
import fr.uge.hyperstack.fragment.TwoButtonsDialogListener;
import fr.uge.hyperstack.model.Stack;

public class HomeActivity extends AppCompatActivity implements TwoButtonsDialogListener {
    private final List<Stack> stacks = new ArrayList<>();
    private StackAdapter stackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initData();

        RecyclerView recyclerView = findViewById(R.id.stacksRecyclerView);
        stackAdapter = new StackAdapter(HomeActivity.this, stacks);
        recyclerView.setAdapter(stackAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void initData() {
        Stack stack = new Stack("Cours Android");
        stack.addNewSlide();
        stack.addNewSlide();
        stacks.add(stack);
        stacks.add(new Stack("Cours JEE"));
        stacks.add(new Stack("CV"));
        stacks.add(new Stack("Scala"));
        stacks.add(new Stack("Batch Processing"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Stack savedStack = (Stack) data.getSerializableExtra("newCurrentStack");
            int stackNum = data.getIntExtra("stackNum", -1);
            stacks.set(stackNum, savedStack);
            stackAdapter.notifyItemChanged(stackNum);
        }
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
            Snackbar.make(findViewById(R.id.stacksRecyclerView), "Le nom de la présentation ne peut pas être vide.", Snackbar.LENGTH_LONG).show();
            return;
        }
        Stack newStack = new Stack(title);
        stackAdapter.addStack(newStack);

        // On envoie vers l'écran de modification
        Intent editIntent = new Intent(getApplicationContext(), EditActivity.class);
        editIntent.putExtra("stack", newStack);
        startActivityForResult(editIntent, RESULT_OK);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Do nothing if cancelled
    }


}
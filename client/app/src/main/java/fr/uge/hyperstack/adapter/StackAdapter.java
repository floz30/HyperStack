package fr.uge.hyperstack.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.uge.hyperstack.R;
import fr.uge.hyperstack.activities.EditActivity;
import fr.uge.hyperstack.model.Stack;

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.StackViewHolder> {
    private final Context context;
    private final List<Stack> stacks;


    public StackAdapter(Context context, List<Stack> stacks) {
        super();
        this.context = context;
        this.stacks = stacks;
    }

    @NonNull
    @Override
    public StackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StackViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.stack_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StackViewHolder holder, int position) {
        Stack currentStack = stacks.get(position);
        holder.bind(currentStack);
        holder.stackImageView.setOnClickListener(v -> {
            Intent editIntent = new Intent(context.getApplicationContext(), EditActivity.class);
            editIntent.putExtra("stack", currentStack);
            editIntent.putExtra("stackNum", position);
            context.startActivity(editIntent);
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


    public static class StackViewHolder extends RecyclerView.ViewHolder {
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

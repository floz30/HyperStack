package fr.uge.hyperstack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.uge.hyperstack.model.Stack;
import fr.uge.hyperstack.view.EditorView;

public class LogsViewAdapter extends RecyclerView.Adapter<LogsViewAdapter.StackViewHolder>{

    private List<Logs> logs;

    public class StackViewHolder extends RecyclerView.ViewHolder {

        private TextView timestamp;

        public StackViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp = itemView.findViewById(R.id.timestamp);
        }

        private void update(Logs logs) {
            timestamp.setText(logs.getTimestamp()+"");
        }
    }

    @NonNull
    @Override
    public StackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StackViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StackViewHolder holder, int position) {
        holder.update(logs.get(position));
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public void setLogs(List<Logs> logs) {
        this.logs = logs;
    }
}

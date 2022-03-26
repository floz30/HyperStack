package fr.uge.hyperstack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogsViewAdapter extends RecyclerView.Adapter<LogsViewAdapter.StackViewHolder>{

    private List<StackWrapper> logs;

    public class StackViewHolder extends RecyclerView.ViewHolder {

        private TextView timestamp;

        public StackViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp = itemView.findViewById(R.id.timestamp);
        }

        private void update(StackWrapper logs) {
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

    public void setLogs(List<StackWrapper> logs) {
        this.logs = logs;
    }
}

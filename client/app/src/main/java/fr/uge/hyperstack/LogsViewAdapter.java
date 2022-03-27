package fr.uge.hyperstack;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.uge.hyperstack.model.Element;

public class LogsViewAdapter extends RecyclerView.Adapter<LogsViewAdapter.StackViewHolder>{

    private List<StackWrapper> logs;
    private Activity activity;

    public class StackViewHolder extends RecyclerView.ViewHolder {

        private TextView timestamp;
        private int slideIndex;
        private Element element;

        public StackViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp = itemView.findViewById(R.id.timestamp);
        }

        private void update(StackWrapper stack, List<StackWrapper> logs, Activity activity) {
            timestamp.setText(stack.getTimestamp()+"");
            timestamp.setOnClickListener(v->{
                long ts = Long.parseLong(timestamp.getText().toString());
                ArrayList<StackWrapper> res = new ArrayList<>();
                for (StackWrapper s : logs) {
                    if (s.getTimestamp() <= ts) {
                        res.add(s);
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("selected", res);
                activity.setResult(RESULT_OK, intent);
                activity.finish();
            });
            slideIndex = stack.getSlideIndex();
            element = stack.getElement();
        }
    }

    @NonNull
    @Override
    public StackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StackViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StackViewHolder holder, int position) {
        holder.update(logs.get(position), logs, activity);
    }



    @Override
    public int getItemCount() {
        return logs.size();
    }

    public void setLogs(List<StackWrapper> logs) {
        this.logs = logs;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}

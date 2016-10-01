package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import unilever.coachingform.R;

/**
 * Created by omar.abdillah on 28/09/2016.
 */
public class QuestionButtonAdapter extends RecyclerView.Adapter<QuestionButtonAdapter.MyViewHolder>{
    Context context;
    int layoutResourceId;
    List<String> data = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        RadioButton button;
        EditText remarks;

        public MyViewHolder (View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.question);
            button = (RadioButton) view.findViewById(R.id.radio_button);
            remarks = (EditText) view.findViewById(R.id.remarks);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dtspull_sebelum_question, parent, false);

        return new MyViewHolder(itemView);
    }
    public QuestionButtonAdapter(ArrayList<String> questions) {
        this.data = questions;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String input = data.get(position);
        holder.text.setText(input);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

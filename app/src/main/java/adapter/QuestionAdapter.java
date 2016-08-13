package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unilever.coachingform.R;

/**
 * Created by omar.abdillah on 07/08/2016.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    private ArrayList<String> questions = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView q;
        public RadioButton radio_1,radio_2,radio_3,radio_4,radio_5,radio_6,radio_7,radio_8,radio_9,radio_10,radio_11,radio_12;

        public MyViewHolder (View view) {
            super(view);
            q = (TextView) view.findViewById(R.id.questions);
            radio_1 = (RadioButton) view.findViewById(R.id.radio_1);
            radio_2 = (RadioButton) view.findViewById(R.id.radio_2);
            radio_3 = (RadioButton) view.findViewById(R.id.radio_3);
            radio_4 = (RadioButton) view.findViewById(R.id.radio_4);
            radio_5 = (RadioButton) view.findViewById(R.id.radio_5);
            radio_6 = (RadioButton) view.findViewById(R.id.radio_6);
            radio_7 = (RadioButton) view.findViewById(R.id.radio_7);
            radio_8 = (RadioButton) view.findViewById(R.id.radio_8);
            radio_9 = (RadioButton) view.findViewById(R.id.radio_9);
            radio_10 = (RadioButton) view.findViewById(R.id.radio_10);
            radio_11 = (RadioButton) view.findViewById(R.id.radio_11);
            radio_12 = (RadioButton) view.findViewById(R.id.radio_12);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list, parent, false);

        return new MyViewHolder(itemView);
    }

    public QuestionAdapter(ArrayList<String> questions) {
        this.questions = questions;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String input = questions.get(position);
        holder.q.setText(input);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

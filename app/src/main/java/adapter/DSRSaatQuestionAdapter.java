package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import unilever.coachingform.R;

/**
 * Created by omar.abdillah on 16/08/2016.
 */
public class DSRSaatQuestionAdapter extends RecyclerView.Adapter<DSRSaatQuestionAdapter.MyViewHolder> {
    private ArrayList<String> questions = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView q;
        public RadioButton radio_1,radio_2,radio_3,radio_4,radio_5,radio_6,radio_7,radio_8,radio_9,radio_10;

        public MyViewHolder (View view) {
            super(view);
            q = (TextView) view.findViewById(R.id.question);
            radio_1 = (RadioButton) view.findViewById(R.id.dsr_saat_1);
            radio_2 = (RadioButton) view.findViewById(R.id.dsr_saat_2);
            radio_3 = (RadioButton) view.findViewById(R.id.dsr_saat_3);
            radio_4 = (RadioButton) view.findViewById(R.id.dsr_saat_4);
            radio_5 = (RadioButton) view.findViewById(R.id.dsr_saat_5);
            radio_6 = (RadioButton) view.findViewById(R.id.dsr_saat_6);
            radio_7 = (RadioButton) view.findViewById(R.id.dsr_saat_7);
            radio_8 = (RadioButton) view.findViewById(R.id.dsr_saat_8);
            radio_9 = (RadioButton) view.findViewById(R.id.dsr_saat_9);
            radio_10 = (RadioButton) view.findViewById(R.id.dsr_saat_10);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dsr_saat_question, parent, false);

        return new MyViewHolder(itemView);
    }

    public DSRSaatQuestionAdapter(ArrayList<String> questions) {
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

package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.CoacheeHistory;
import model.Coaching;
import unilever.coachingform.R;

/**
 * Created by omar.abdillah on 17/08/2016.
 */
public class CoacheeHistoryAdapter  extends ArrayAdapter<CoacheeHistory> {
    Context context;
    int layoutResourceId;
    List<CoacheeHistory> data = new ArrayList<>();

    public CoacheeHistoryAdapter(Context context,int layoutResourceId,List<CoacheeHistory> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CoacheeHistoryHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CoacheeHistoryHolder();
            holder.date = (TextView)row.findViewById(R.id.date);
            holder.coach = (TextView)row.findViewById(R.id.coach);
            holder.action = (TextView)row.findViewById(R.id.action);
            row.setTag(holder);
        }
        else
        {
            holder = (CoacheeHistoryHolder)row.getTag();
        }

        CoacheeHistory input = data.get(position);
        holder.date.setText(input.getDate());
        holder.coach.setText(input.getCoachName());
        holder.action.setText(input.getAction());

        return row;
    }

    static class CoacheeHistoryHolder {
        TextView date;
        TextView coach;
        TextView action;
    }
}

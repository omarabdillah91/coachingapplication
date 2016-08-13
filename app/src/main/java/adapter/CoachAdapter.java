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

import model.Coaching;
import unilever.coachingform.R;

/**
 * Created by omar.abdillah on 13/08/2016.
 */
public class CoachAdapter extends ArrayAdapter<Coaching> {
    Context context;
    int layoutResourceId;
    List<Coaching> data = new ArrayList<>();

    public CoachAdapter(Context context,int layoutResourceId,List<Coaching> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CoachingHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CoachingHolder();
            holder.date = (TextView)row.findViewById(R.id.date);
            holder.coache = (TextView)row.findViewById(R.id.coache);
            holder.status = (TextView)row.findViewById(R.id.status);
            row.setTag(holder);
        }
        else
        {
            holder = (CoachingHolder)row.getTag();
        }

        Coaching input = data.get(position);
        holder.date.setText(input.date);
        holder.coache.setText(input.coachee);
        holder.status.setText(input.status);

        return row;
    }

    static class CoachingHolder {
        TextView date;
        TextView coache;
        TextView status;
    }
}

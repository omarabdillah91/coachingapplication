package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import unilever.coachingform.R;

/**
 * Created by omar.abdillah on 07/08/2016.
 */
public class CustomerHeaderAdapter extends BaseAdapter {
    private Context context;
    private final String[] customer;

    public CustomerHeaderAdapter(Context context, String[] customer) {
        this.context = context;
        this.customer = customer;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.dsr_customer_header, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.text);
            textView.setText(customer[position]);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return customer.length;
    }

    @Override
    public Object getItem(int position) {
        return customer[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}

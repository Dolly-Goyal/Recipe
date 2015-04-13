package com.recipetresure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 12-04-2015.
 */
public class UserDataAdapter extends ArrayAdapter<String> {
    protected Context mContext;
    protected List<String> mRecipeName;
    protected List<Date> mDates;
    //Calendar mCalender = Calendar.getInstance();

    public UserDataAdapter(Context context, List<String> RecipeName, List<Date> dates) {
        super(context, R.layout.single_row_1, RecipeName);
        mContext = context;
        mRecipeName = RecipeName;
        mDates = dates;
        System.out.println("adapter called");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.single_row_1, null);
            holder = new ViewHolder();

            holder.RecipeName = (TextView) convertView.findViewById(R.id.dataRecipeName);
            holder.dates = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Date date = mDates.get(position);
        SimpleDateFormat sDate = new SimpleDateFormat("dd/mm/yyyy");
        String nDate = sDate.format(date);
        holder.RecipeName.setText(nDate);
        holder.dates.setText(mRecipeName.get(position));
        return convertView;
    }

    public static class ViewHolder {
        TextView RecipeName;
        TextView dates;
    }
}

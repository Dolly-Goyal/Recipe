package com.recipetresure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    protected Context mContext;
    protected List<String> mRecipeTitle;
    protected List<String> mPersonId;
    protected int[] mImage;

    public CustomAdapter(Context context, List<String> recipeTitle,List<String> personId) {
        super(context, R.layout.single_row, recipeTitle);
        mContext = context;
        mRecipeTitle = recipeTitle;
        mPersonId = personId;
        System.out.println("adapter called");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.single_row, null);
            holder = new ViewHolder();

            holder.recipeTitle = (TextView) convertView.findViewById(R.id.recipeTitle);
            holder.personId = (TextView) convertView.findViewById(R.id.personId);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.recipeTitle.setText(mRecipeTitle.get(position));
        holder.personId.setText(mPersonId.get(position));
        return convertView;
    }
    public static class ViewHolder {
        TextView recipeTitle;
        TextView personId;
        ImageView image;
    }
}

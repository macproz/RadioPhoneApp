package com.yf.phoneapp.bluetoothadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yf.radiophoneapp.R;

import java.util.HashMap;

/**
 * Created by lishanfeng on 16/8/7.
 */
public class RadioListAdapter extends BaseAdapter {

    private Context context;
    private String[]  radioNums;

    HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    class ViewHolder{
        TextView tvNums;
        CheckBox cb_state;
    }

    public RadioListAdapter(Context context, String[] showNums){
        this.radioNums = showNums;
        this.context = context;

    }

    @Override
    public int getCount() {
        return radioNums.length;
    }

    @Override
    public Object getItem(int position) {
        return radioNums[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
       String curritem =  radioNums[position];
        LayoutInflater inflater = LayoutInflater.from(context);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.radiocounts_item, null);
            holder = new ViewHolder();
            holder.cb_state = (CheckBox) convertView.findViewById(R.id.cb_right);
            holder.tvNums = (TextView) convertView.findViewById(R.id.radionums_left);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tvNums.setText(curritem);
        final CheckBox box = (CheckBox) convertView.findViewById(R.id.cb_right);
        holder.cb_state = box;
        holder.cb_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(String key: states.keySet()){
                    states.put(String.valueOf(position), box.isChecked());
                    notifyDataSetChanged();
                }
            }
        });

        boolean res = false;
        if(states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false){
            res = false;
            states.put(String.valueOf(position), false);
        }else{
            res = true;
            holder.cb_state.setChecked(box.isChecked()? true : false);

        }
        return convertView;
    }

}

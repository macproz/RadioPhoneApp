package com.yf.phoneapp.bluetoothadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yf.radiophoneapp.AmCountsView;
import com.yf.radiophoneapp.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lishanfeng on 16/8/7.
 */
public class RadioListAdapter extends BaseAdapter {
    private Context context;
    private List source;
    LayoutInflater inflater;

    class ViewHolder{
        TextView tvNums;
        CheckBox cb_state;
    }

    public RadioListAdapter(Context context, List showNums){
        this.source = showNums;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public Object getItem(int position) {
        return source.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position1, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        View view = null;
        final int position = position1;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.radiocounts_item, null);
            holder = new ViewHolder();
            holder.cb_state = (CheckBox) convertView.findViewById(R.id.cb_right);
            holder.tvNums = (TextView) convertView.findViewById(R.id.radionums_left);
            convertView.setTag(holder);
        }else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        holder.cb_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean curr = !AmCountsView.isSelected.get(position);
                for(Integer p: AmCountsView.isSelected.keySet()){
                    AmCountsView.isSelected.put(p, false);
                }
                AmCountsView.isSelected.put(position, curr);
                RadioListAdapter.this.notifyDataSetChanged();
                AmCountsView.beSelectedData.clear();
                if(curr)AmCountsView.beSelectedData.add(source.get(position));
            }
        });
       holder.tvNums.setText(source.get(position).toString());
       holder.cb_state.setChecked(AmCountsView.isSelected.get(position));
       return convertView;
    }

}

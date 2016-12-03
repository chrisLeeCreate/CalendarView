package com.boxfish.lsw.c;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by lishaowei on 16/12/3
 */
public class CellViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private final TextView tvDay;
    private final TextView tvStatus;

    public CellViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        tvDay = (TextView) itemView.findViewById(R.id.tv_day);
        tvStatus = (TextView) itemView.findViewById(R.id.tv_status);
    }

    public void initData(ScheduleDate scheduleDate) {
        if (scheduleDate.isSpace) {
            itemView.setBackgroundColor(Color.TRANSPARENT);
            tvStatus.setText("");
            tvDay.setText("");
        } else {
            int dayOfMonth = scheduleDate.dateTime.getDayOfMonth();
            tvDay.setText(String.valueOf(dayOfMonth));
        }
    }

}

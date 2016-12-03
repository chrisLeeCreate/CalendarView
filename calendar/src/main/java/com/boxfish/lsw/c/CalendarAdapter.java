package com.boxfish.lsw.c;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by lishaowei on 16/12/3
 */
public class CalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int day_type = 1;
    private final int month_type = 0;
    private ArrayList monthList;
    OnCellClickListener onCellClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == day_type) {
            View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_view, parent, false);
            return new CellViewHolder(viewHolder);
        } else {
            return new MonthTitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.month_title, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object itemData = monthList.get(position);
        if (itemData instanceof ScheduleDate) {
            final ScheduleDate scheduleDate = (ScheduleDate) itemData;
            CellViewHolder myViewHolder = (CellViewHolder) holder;
            myViewHolder.initData(scheduleDate);
            myViewHolder.itemView.setOnClickListener(view -> {
                if (onCellClickListener != null) {
                    onCellClickListener.clickCell(scheduleDate);
                }
            });
        } else {
            MonthTitleHolder monthTitleHolder = (MonthTitleHolder) holder;
            MonthTitileModel monthTitileModel = (MonthTitileModel) itemData;
            monthTitleHolder.setData(monthTitileModel);
        }
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    public void setData(ArrayList monthList) {
        this.monthList = monthList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (monthList.get(position) instanceof ScheduleDate) {
            return day_type;
        } else {
            return month_type;
        }
    }

    public Object getItem(int position) {
        return monthList.get(position);
    }

    public interface OnCellClickListener {
        void clickCell(ScheduleDate scheduleDate);
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }
}

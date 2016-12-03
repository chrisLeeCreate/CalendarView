package com.boxfish.lsw.c;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
 * Created by lishaowei on 16/12/3
 */
public class MonthTitleHolder extends RecyclerView.ViewHolder {
    public MonthTitleHolder(View itemView) {
        super(itemView);
    }

    public void setData(MonthTitileModel monthTitileModel) {
        ((TextView) this.itemView).setText(monthTitileModel.month);
    }
}

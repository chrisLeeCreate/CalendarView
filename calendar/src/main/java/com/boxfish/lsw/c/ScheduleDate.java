package com.boxfish.lsw.c;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Created by lishaowei on 16/12/3
 */
public class ScheduleDate {
    public DateTime dateTime;

    public int todoCount;
    public boolean isSpace;
    public boolean isSelected;
    public boolean isFinished = false;
    public boolean isBeforeNow;

    public ScheduleDate(DateTime dateTime, boolean isSelected, int todoCount) {
        this.dateTime = dateTime;
        this.isSelected = isSelected;
        this.todoCount = todoCount;
        DateTime nowDate = new DateTime();
        if (Days.daysBetween(dateTime, nowDate).getDays() > 0) {
            isBeforeNow = true;
        } else {
            isBeforeNow = false;
        }

    }

    public ScheduleDate() {
        this.dateTime = new DateTime();
        isSpace = true;
    }
    public ScheduleDate(DateTime dateTime) {
        this.dateTime = new DateTime();
    }
}

package com.boxfish.lsw.c;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import org.joda.time.DateTime;
import org.joda.time.Months;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lishaowei on 16/12/3
 */
public class CalendarView extends RecyclerView {
    private int minMonth = new DateTime().getMonthOfYear();
    private int maxMonth = new DateTime().getMonthOfYear();
    private int minYear = new DateTime().getYear();
    private int maxYear = new DateTime().getYear();
    private CalendarAdapter calendarAdapter;
    OnCanlendarDayClickListener onCanlendarDayClickListener;

    public CalendarView(Context context) {
        super(context);
        initView();
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 7);
        setLayoutManager(gridLayoutManager);
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        calendarAdapter = new CalendarAdapter();
        setAdapter(calendarAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Object item = calendarAdapter.getItem(position);
                if (item instanceof ScheduleDate) {
                    return 1;
                } else {
                    return 7;
                }
            }
        });
        calendarAdapter.setOnCellClickListener(scheduleDate -> {
            if (onCanlendarDayClickListener != null) {
                onCanlendarDayClickListener.clickListener(scheduleDate);
            }
        });
    }

    public interface OnCanlendarDayClickListener {
        void clickListener(ScheduleDate scheduleDate);
    }

    public void setOnCanlendarDayClickListener(OnCanlendarDayClickListener onCanlendarDayClickListener) {
        this.onCanlendarDayClickListener = onCanlendarDayClickListener;
    }

    public void setStartByNowMonth(int monthNum) {
        this.minYear = new DateTime().getYear();
        this.minMonth = new DateTime().getMonthOfYear();
        this.maxMonth = new DateTime().plusMonths(monthNum).getMonthOfYear();
        this.maxYear = new DateTime().plusMonths(monthNum).getYear();
        setCalendarData();
    }

    public void setCalendarData(List<ScheduleDate> selectList) {
        Observable.create(new Observable.OnSubscribe<ArrayList>() {
            @Override
            public void call(Subscriber<? super ArrayList> subscriber) {
                DateTime startDate = new DateTime(minYear, minMonth, 1, 0, 0);
                ArrayList monthList = new ArrayList();
                DateTime minDate = new DateTime(minYear, minMonth, 1, 0, 0);
                DateTime maxDate = new DateTime(maxYear, maxMonth, 1, 0, 0);
                int monthSpace = Months.monthsBetween(minDate, maxDate).getMonths();
                for (int i = 0; i <= monthSpace; i++) {
                    monthList.add(new MonthTitileModel(startDate.year().getAsShortText(Locale.getDefault()), startDate.monthOfYear().getAsShortText(Locale.getDefault())));
                    int dayOfWeek = startDate.getDayOfWeek();
                    for (int j = 1; j < dayOfWeek; j++) {
                        ScheduleDate scheduleDate = new ScheduleDate();
                        scheduleDate.isSpace = true;
                        monthList.add(scheduleDate);
                    }
                    for (int j = 0; j < 42; j++) {
//                        if (selectList != null) {
//                            setSelectCell(startDate, monthList, selectList);
//                        } else {
                        monthList.add(new ScheduleDate(startDate, false, 0));
//                        }
                        DateTime tempDate = startDate.plusDays(1);
                        if (startDate.getMonthOfYear() != tempDate.getMonthOfYear()) {
//                    addSurplusItem(monthList, startDate);
                            startDate = tempDate;
                            break;
                        } else {
                            startDate = tempDate;
                        }
                    }
                }
                subscriber.onNext(monthList);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(monthList -> {
                    calendarAdapter.setData(monthList);
                });
    }

    private void setCalendarData() {
        setCalendarData(null);
    }
}

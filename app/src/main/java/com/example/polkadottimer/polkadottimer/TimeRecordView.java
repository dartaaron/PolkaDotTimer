package com.example.polkadottimer.polkadottimer;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.polkadottimer.polkadottimer.MainActivity.TimeRecord;


public class TimeRecordView extends FrameLayout {

    private TimeRecord timeRecord;

    private View actionArea;
    private TextView label;

    public TimeRecordView(Context context) {
        super(context);
        init(context);
    }

    public TimeRecord getTimeRecord() {
        return timeRecord;
    }

    public void setTimeRecord(TimeRecord timeRecord) {
        this.timeRecord = timeRecord;
        initTimeRecord();
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        actionArea.setOnClickListener(l);
    }

    public TimeRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_time_record, this);

        actionArea = findViewById(R.id.action_area);
        label = (TextView) findViewById(R.id.time_label);
    }

    private void initTimeRecord() {
        if (timeRecord != null) {
            label.setText(TimeUtil.convertTime(timeRecord.time, false));
        } else {
            label.setText(" ");
        }
    }

}

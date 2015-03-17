package com.example.polkadottimer.polkadottimer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    // private TextView display;

    private View hrsSection;
    private TextView hrsLabel;
    private TextView minLabel;
    private TextView secLabel;
    private TextView milLabel;

    private View tab1;
    private View tab2;
    private View tab3;

    private View content1;
    private View content2;
    private View content3;

    private GridView shortGrid;
    private GridView longGrid;
    private GridView favGrid;

    private long time;
    private long curTime;
    private boolean started = false;
    private boolean setted = false;
    private CountDownTimer timer;

    private static ArrayList<TimeRecord> shortRecordsList;
    private static ArrayList<TimeRecord> longRecordsList;
    private static ArrayList<TimeRecord> favRecordsList;

    public static class TimeRecord {

        public String title;
        public long time;

        public TimeRecord() {

        }

        public TimeRecord(String title, long time) {
            this.time = time;
            this.title = title;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        View displayArea = findViewById(R.id.display_area);
        displayArea.setOnClickListener(startStopListener);

        hrsSection = findViewById(R.id.hours_section);
        hrsLabel = (TextView) findViewById(R.id.hours_label);
        minLabel = (TextView) findViewById(R.id.minutes_label);
        secLabel = (TextView) findViewById(R.id.seconds_label);
        milLabel = (TextView) findViewById(R.id.millis_label);

        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);

        content1 = findViewById(R.id.content1);
        content2 = findViewById(R.id.content2);
        content3 = findViewById(R.id.content3);

        tab1.setOnClickListener(tabListener);
        tab2.setOnClickListener(tabListener);
        tab3.setOnClickListener(tabListener);

        initTimeArrays();

        shortGrid = (GridView) findViewById(R.id.short_grid);
        longGrid = (GridView) findViewById(R.id.long_grid);
        favGrid = (GridView) findViewById(R.id.fav_grid);

        shortGrid.setAdapter(new TimeRecordAdapter(this, shortRecordsList));
        longGrid.setAdapter(new TimeRecordAdapter(this, longRecordsList));
        favGrid.setAdapter(new TimeRecordAdapter(this, favRecordsList));

        shortGrid.setOnItemClickListener(timeRecordClickListener);
        longGrid.setOnItemClickListener(timeRecordClickListener);
        favGrid.setOnItemClickListener(timeRecordClickListener);

        loadFavList(this);
    }

    private void initTimeArrays() {
        shortRecordsList = new ArrayList<TimeRecord>();
        shortRecordsList.add(new TimeRecord("10s", 10000));
        shortRecordsList.add(new TimeRecord("15s", 15000));
        shortRecordsList.add(new TimeRecord("20s", 20000));
        shortRecordsList.add(new TimeRecord("25s", 25000));
        shortRecordsList.add(new TimeRecord("30s", 30000));
        shortRecordsList.add(new TimeRecord("35s", 35000));
        shortRecordsList.add(new TimeRecord("40s", 40000));
        shortRecordsList.add(new TimeRecord("45s", 45000));
        shortRecordsList.add(new TimeRecord("50s", 50000));
        shortRecordsList.add(new TimeRecord("55s", 55000));
        shortRecordsList.add(new TimeRecord("1m", 60000));
        shortRecordsList.add(new TimeRecord("1m30s", 90000));
        shortRecordsList.add(new TimeRecord("2m", 120000));
        shortRecordsList.add(new TimeRecord("2m30s", 150000));
        shortRecordsList.add(new TimeRecord("3m", 180000));
        shortRecordsList.add(new TimeRecord("3m30s", 210000));
        shortRecordsList.add(new TimeRecord("4m", 240000));
        shortRecordsList.add(new TimeRecord("4m30s", 270000));
        shortRecordsList.add(new TimeRecord("5m", 300000));
        shortRecordsList.add(new TimeRecord("6m", 360000));
        shortRecordsList.add(new TimeRecord("7m", 420000));
        shortRecordsList.add(new TimeRecord("8m", 480000));
        shortRecordsList.add(new TimeRecord("9m", 540000));
        shortRecordsList.add(new TimeRecord("10m", 600000));
        shortRecordsList.add(new TimeRecord("15m", 900000));

        longRecordsList = new ArrayList<TimeRecord>();
        longRecordsList.add(new TimeRecord("15m", 900000));
        longRecordsList.add(new TimeRecord("20m", 1200000));
        longRecordsList.add(new TimeRecord("25m", 1500000));
        longRecordsList.add(new TimeRecord("30m", 1800000));
        longRecordsList.add(new TimeRecord("35m", 2100000));
        longRecordsList.add(new TimeRecord("40m", 2400000));
        longRecordsList.add(new TimeRecord("45m", 2700000));
        longRecordsList.add(new TimeRecord("50m", 3000000));
        longRecordsList.add(new TimeRecord("55m", 3300000));
        longRecordsList.add(new TimeRecord("1h", 3600000));
        longRecordsList.add(new TimeRecord("1h10m", 4200000));
        longRecordsList.add(new TimeRecord("1h20m", 4800000));
        longRecordsList.add(new TimeRecord("1h30m", 5400000));
        longRecordsList.add(new TimeRecord("1h40m", 6000000));
        longRecordsList.add(new TimeRecord("1h50m", 6600000));
        longRecordsList.add(new TimeRecord("2h", 7200000));
        longRecordsList.add(new TimeRecord("3h", 10800000));
        longRecordsList.add(new TimeRecord("4h", 14400000));
        longRecordsList.add(new TimeRecord("5h", 18000000));
    }

    public class TimeRecordAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<TimeRecord> records;

        public TimeRecordAdapter(Context context) {
            this.context = context;
        }

        public TimeRecordAdapter(Context context, ArrayList<TimeRecord> records) {
            this.context = context;
            this.records = records;
        }

        public void setRecords(ArrayList<TimeRecord> records) {
            this.records = records;
        }

        public int getCount() {
            return records == null ? 0 : records.size();
        }

        public Object getItem(int position) {
            return records == null ? null : records.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TimeRecordView view;
            if (convertView == null) {
                view = new TimeRecordView(context);
            } else {
                view = (TimeRecordView) convertView;
            }

            view.setTimeRecord((TimeRecord) getItem(position));
            return view;
        }

    }

    View.OnClickListener tabListener = new OnClickListener() {
        public void onClick(View v) {
            content1.setVisibility(View.GONE);
            content2.setVisibility(View.GONE);
            content3.setVisibility(View.GONE);

            tab1.setBackgroundColor(Color.BLACK);
            tab2.setBackgroundColor(Color.BLACK);
            tab3.setBackgroundColor(Color.BLACK);

            if (tab1 == v) {
                content1.setVisibility(View.VISIBLE);
                tab1.setBackgroundColor(Color.WHITE);
            }
            if (tab2 == v) {
                content2.setVisibility(View.VISIBLE);
                tab2.setBackgroundColor(Color.WHITE);
            }
            if (tab3 == v) {
                content3.setVisibility(View.VISIBLE);
                tab3.setBackgroundColor(Color.WHITE);
            }
        }
    };

    AdapterView.OnItemClickListener timeRecordClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            TimeRecord record = (TimeRecord) parent.getAdapter().getItem(position);
            if (!started) {
                curTime = 0;
                setted = true;
                time = record.time;
                showTime(time);
            }
        }
    };

    View.OnClickListener startStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (setted) {
                if (started) {
                    timer.cancel();
                    started = false;
                    timer = null;
                } else {
                    if (curTime != 0) {
                        timer = new CountDownTimer(curTime, 10) {
                            public void onTick(long millisUntilFinished) {
                                curTime = millisUntilFinished;
                                showTime(millisUntilFinished);
                            }

                            public void onFinish() {
                                started = false;
                                curTime = 0;
                                timer = null;
                                showTime(time);
                            }
                        }.start();
                    } else {
                        timer = new CountDownTimer(time, 10) {
                            public void onTick(long millisUntilFinished) {
                                curTime = millisUntilFinished;
                                showTime(millisUntilFinished);
                            }

                            public void onFinish() {
                                started = false;
                                curTime = 0;
                                timer = null;
                                showTime(time);
                            }
                        }.start();
                    }
                    started = true;
                }
            }
        }
    };

    public static long HOUR = 60 * 60 * 1000;
    public static long MINUTE = 60 * 1000;
    public static long SECOND = 1000;

    private void showTime(long millis) {
        hrsLabel.setText(TimeUtil.getHours(millis, true));
        minLabel.setText(TimeUtil.getMinutes(millis, true));
        secLabel.setText(TimeUtil.getSeconds(millis, true));
        milLabel.setText(TimeUtil.getMillisDec(millis, true));
        if (TimeUtil.getHours(millis) == 0) {
            hrsSection.setVisibility(View.INVISIBLE);
        } else {
            hrsSection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                addCurrentTimeToFavorite(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addCurrentTimeToFavorite(Context context) {
        if (!setted) {
            return;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String json = prefs.getString("fav_records", "");

        ArrayList<TimeRecord> list = new ArrayList<TimeRecord>();
        boolean contains = false;

        if (!"".equals(json)) {
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    list.add(convertJsonObjectToTimeRecord(item));
                }
            } catch (JSONException e) {
                System.out.println(e);
                return;
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e);
                return;
            }

            for (TimeRecord record : list) {
                if (record.time == time) {
                    contains = true;
                }
            }
        }

        if (!contains) {
            list.add(0, new TimeRecord(TimeUtil.convertTime(time, false), time));
            favRecordsList = list;
            ((TimeRecordAdapter) favGrid.getAdapter()).setRecords(list);
            ((TimeRecordAdapter) favGrid.getAdapter()).notifyDataSetChanged();
            favGrid.invalidateViews();

            JSONArray array = new JSONArray();
            for (TimeRecord record : list) {
                try {
                    array.put(convertTimeRecordToJsonObject(record));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Editor e = prefs.edit();
            e.putString("fav_records", array.toString());
            e.commit();
        }
    }

    private void loadFavList(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String json = prefs.getString("fav_records", "");

        ArrayList<TimeRecord> list = new ArrayList<TimeRecord>();

        if (!"".equals(json)) {
            try {
                JSONArray array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = (JSONObject) array.get(i);
                    list.add(convertJsonObjectToTimeRecord(item));
                }
            } catch (JSONException e) {
                System.out.println(e);
                return;
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e);
                return;
            }
        }

        favRecordsList = list;
        ((TimeRecordAdapter) favGrid.getAdapter()).setRecords(list);
        ((TimeRecordAdapter) favGrid.getAdapter()).notifyDataSetChanged();
        favGrid.invalidateViews();
    }

    private TimeRecord convertJsonObjectToTimeRecord(JSONObject item) throws JSONException {
        TimeRecord record = new TimeRecord();

        record.time = item.getLong("time");
        record.title = item.getString("title");

        return record;
    }

    private JSONObject convertTimeRecordToJsonObject(TimeRecord record) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        if (record != null) {
            jsonObject.put("time", record.time);
            jsonObject.put("title", record.title);
        }

        return jsonObject;
    }

}

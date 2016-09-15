package com.incresol.lu.conversions;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Incresol-078 on 26-08-2016.
 */
public class TimeZoneActivity extends android.app.Fragment implements View.OnClickListener {

    View timeZoneView;
    public static EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right;
    String spinnerText_left, spinnerText_right,autoTextTopValue,autoTextBelowValue;
    Button button_convert;
    public static  Calendar myCalendar=Calendar.getInstance();
    ImageView datepicker,timepicker;
    ScrollView scrollView_timezone;
    static LinearLayout layout_timer,layout_slider;
    // Calendar calendar_top,calendar_below=Calendar.getInstance();
    public static boolean timePickerBoolean = false;
    public static boolean datePickerBoolean = false;
    public static TimePickerDialog mTimePickerDialog;
    public static DatePickerDialog mDatePickerDialog;
    int year1,month1,day_of_month1;
    int HOUR,MINUTE;

    TimeZone tzleft;
    TimeZone tzright;
    static Context context;
    public int currentimageindex=0;
    ImageView imageslider;
    ProgressBar mProgressBar;
    TextView set_timer,textView_top,textView_top_result,textView_below,textView_below_result;
    int TimeCounter=30;

    int[] IMAGE_IDS = {R.drawable.icon_1_timezone, R.drawable.time_zone};
    String[] timeZones;

    AutoCompleteTextView autoCompleteTextView_1,autoCompleteTextView_2;

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("** onResume **");
        timePickerBoolean = false;
        datePickerBoolean = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("** onPause **");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("** onCreateView **");
        timeZoneView=inflater.inflate(R.layout.activity_timezone_conversion,container,false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Time Zone Conversion");
        MainActivity.home=0;
        editText_left = (EditText) timeZoneView.findViewById(R.id.editText_left);
        editText_right = (EditText) timeZoneView.findViewById(R.id.editText_right);
        context = getActivity();
        autoCompleteTextView_1=(AutoCompleteTextView)timeZoneView.findViewById(R.id.autoCompleteTextView_1);
        autoCompleteTextView_2=(AutoCompleteTextView)timeZoneView.findViewById(R.id.autoCompleteTextView_2);

        // spinner_left = (Spinner) timeZoneView.findViewById(R.id.spinner_left);
        spinner_right = (Spinner) timeZoneView.findViewById(R.id.spinner_right);
        button_convert=(Button)timeZoneView.findViewById(R.id.convert);
        datepicker=(ImageView)timeZoneView.findViewById(R.id.datepicker);
        timepicker=(ImageView)timeZoneView.findViewById(R.id.timepicker);
        timepicker.setOnClickListener(this);
        datepicker.setOnClickListener(this);
        layout_timer=(LinearLayout)timeZoneView.findViewById(R.id.layout_timer);
        layout_slider=(LinearLayout)timeZoneView.findViewById(R.id.layout_slider);
        imageslider = (ImageView)timeZoneView.findViewById(R.id.imageslider);
        set_timer=(TextView) timeZoneView.findViewById(R.id.set_timer);
        mProgressBar=(ProgressBar)timeZoneView.findViewById(R.id.progressBar);
        textView_top=(TextView)timeZoneView.findViewById(R.id.textView_top);
        textView_top_result=(TextView)timeZoneView.findViewById(R.id.textView_top_result);
        textView_below=(TextView)timeZoneView.findViewById(R.id.textView_below);
        textView_below_result=(TextView)timeZoneView.findViewById(R.id.textView_below_result);
        scrollView_timezone=(ScrollView)timeZoneView.findViewById(R.id.scrollView_timezone);
        if (ThemesActivity.imageId != null) {
            MainActivity.themeColorChange(ThemesActivity.imageId, "timezone", scrollView_timezone);
        } else {
            MainActivity.themeColorChange("blue", "timezone", scrollView_timezone);
        }

         timeZones = getResources().getStringArray(R.array.timezone_array);
        List<String> stringList = new ArrayList<String>(Arrays.asList(timeZones));
        AutoSuggestAdapter autoSuggestAdapter = new AutoSuggestAdapter(getActivity(), android.R.layout.simple_list_item_1, stringList);

        autoCompleteTextView_1.setAdapter(autoSuggestAdapter);
        autoCompleteTextView_1.setThreshold(1);
        autoCompleteTextView_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoTextTopValue=autoCompleteTextView_1.getText().toString();

                if(autoTextTopValue.equalsIgnoreCase("India(IST)")){
                    String GMTIndiaValue="GMT+5:30";
                tzleft=TimeZone.getTimeZone(GMTIndiaValue);
                }
                else{
                    tzleft=TimeZone.getTimeZone(autoTextTopValue);
                }
                myCalendar.setTimeZone(tzleft);
                updateLabel();
                int hourOfTheDay=myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute=myCalendar.get(Calendar.MINUTE);
                editText_right.setText( (hourOfTheDay<10?("0"+hourOfTheDay):(hourOfTheDay)) + ":" + (minute<10?("0"+minute):(minute)) );
               // editText_right.setText(""+myCalendar.get(Calendar.HOUR_OF_DAY)+":"+myCalendar.get(Calendar.MINUTE));
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(timeZoneView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

        autoCompleteTextView_2.setAdapter(autoSuggestAdapter);
        autoCompleteTextView_2.setThreshold(1);

        autoCompleteTextView_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(timeZoneView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });


        button_convert.setOnClickListener(this);
        return timeZoneView;
    }

    static DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//            year1=year;
//            month1=monthOfYear;
//            day_of_month1=dayOfMonth;
            datePickerBoolean = false;
            myCalendar.set(Calendar.YEAR,year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateLabel();

        }
    };

    static TimePickerDialog.OnTimeSetListener timePickerSetListener=new TimePickerDialog.OnTimeSetListener(){
    @Override
    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
        timePickerBoolean = false;
        myCalendar.set(Calendar.HOUR_OF_DAY,selectedHour);
        myCalendar.set(Calendar.MINUTE,selectedMinute);

        int hourOfTheDay=myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute=myCalendar.get(Calendar.MINUTE);
        editText_right.setText( (hourOfTheDay<10?("0"+hourOfTheDay):(hourOfTheDay)) + ":" + (minute<10?("0"+minute):(minute)) );
    }
    };

    public static void updateLabel(){
        String dateFormat="EE MMM dd yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        editText_left.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    public static void hideDatePickerDialog(){
        mDatePickerDialog.dismiss();
        datePickerBoolean = false;
    }
    public static void hideTimePickerDialog(){
        mTimePickerDialog.dismiss();
        timePickerBoolean = false;
    }
    public static void showDatePickerDialog(){
        datePickerBoolean = true;
        mDatePickerDialog = new DatePickerDialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog,dateSetListener,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
        /*mDatePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                datePickerBoolean = true;
            }
        });*/
mDatePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
    @Override
    public void onCancel(DialogInterface dialog) {
        System.out.println("Date picker cancel ================**************");
        datePickerBoolean = false;
    }
});
        mDatePickerDialog.show();
    }
    public static void showTimePickerDialog(){
        timePickerBoolean = true;
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);
        mTimePickerDialog = new TimePickerDialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog,timePickerSetListener,myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE),true);
        /*mTimePickerDialog = new TimePickerDialog(context,android.R.style.Theme_DeviceDefault_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timePickerBoolean = false;
                myCalendar.set(Calendar.HOUR_OF_DAY,selectedHour);
                myCalendar.set(Calendar.MINUTE,selectedMinute);

                int hourOfTheDay=myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute=myCalendar.get(Calendar.MINUTE);
                editText_right.setText( (hourOfTheDay<10?("0"+hourOfTheDay):(hourOfTheDay)) + ":" + (minute<10?("0"+minute):(minute)) );
            }
        }, hour, minute, true);//Yes 24 hour time*/
//                mTimePicker.setTitle("Select Time");

        /*mTimePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                timePickerBoolean = true;
            }
        });*/
        mTimePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                timePickerBoolean=false;
                System.out.println("Time picker cancel ================**************");
            }
        });
        mTimePickerDialog.show();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.datepicker:
                 showDatePickerDialog();
                break;
            case R.id.timepicker:
                showTimePickerDialog();
                break;
            case R.id.convert:

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(timeZoneView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                /*spinnerText_left = spinner_left.getSelectedItem().toString();
                spinnerText_right = spinner_right.getSelectedItem().toString();*/

                //autoTextTopValue=autoCompleteTextView_1.getText().toString();
                autoTextBelowValue=autoCompleteTextView_2.getText().toString();



if(Arrays.asList(timeZones).contains(autoTextTopValue)&&Arrays.asList(timeZones).contains(autoTextBelowValue)){
    if ((editText_left.getText().length()!=0) && (editText_right.getText().length()!= 0)) {

        if(autoTextBelowValue.equalsIgnoreCase("India(IST)")){
            String GMTIndiaValue="GMT+5:30";
            tzright=TimeZone.getTimeZone(GMTIndiaValue);
        }else {
            tzright = TimeZone.getTimeZone(autoTextBelowValue);
        }
              /* // myCalendar.setTimeZone(tzleft);*/
        SimpleDateFormat simpleDateFormat_top = new SimpleDateFormat("EE MMM dd HH:mm:ss yyyy zzz");
        simpleDateFormat_top.setTimeZone(tzleft);
        //System.out.println("date in tzleft -> "+simpleDateFormat_top.format(myCalendar.getTime()));


        Calendar convertTo= (Calendar) myCalendar.clone();
        convertTo.setTimeZone(tzright);
        SimpleDateFormat simpleDateFormat_top_right = new SimpleDateFormat("EE MMM dd HH:mm:ss yyyy zzz");
        simpleDateFormat_top_right.setTimeZone(tzright);
        System.out.println("date in tzright -> "+simpleDateFormat_top_right.format(convertTo.getTime()));

        textView_top.setText(autoTextTopValue);
        textView_top_result.setText("" + simpleDateFormat_top.format(myCalendar.getTime()));

        textView_below.setText(autoTextBelowValue);
        textView_below_result.setText("" + simpleDateFormat_top_right.format(convertTo.getTime()));

    }else{
        MainActivity.toastMessage("Please select a Data and Time");
    }
}else{
    MainActivity.toastMessage("Please enter a valid Time Zone");
}

        }

    }


    public void timerOn(){
        final int I=0;

        final Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(getActivity() == null)
                    return;

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        layout_timer.setVisibility(View.VISIBLE);
                        Log.i("in thread",""+TimeCounter);
                        if (TimeCounter == I) {
                            timer.cancel();
                            layout_timer.setVisibility(View.GONE);
                            animoTimer();
                        }
                        mProgressBar.setProgress(TimeCounter);
                        set_timer.setText("" + String.valueOf(TimeCounter));

                        Random random=new Random();
                        set_timer.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256),random.nextInt(256)));

                        TimeCounter--;
                    }
                });
            }
        },0,1000);
    }

    //image slider code starts here

    private void animoTimer(){

        final Handler mHandler = new Handler();

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {

                AnimateandSlideShow();

            }
        };

        int delay = 1000; // delay for 1 sec.

        int period = 3000; // repeat every 4 sec.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }

        }, delay, period);


    }

    private void AnimateandSlideShow() {
        layout_slider.setVisibility(View.VISIBLE);

        if(getActivity() == null)
            return;

        //Animation fadeInAnimation= AnimationUtils.loadAnimation(getActivity(),R.anim.fadein);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 2000.0f,
                0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(6000);  // animation duration
        animation.setRepeatCount(1);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )

        imageslider.startAnimation(animation);
        imageslider.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);

        currentimageindex++;

    }

}

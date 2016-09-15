package com.incresol.lu.conversions;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Incresol-078 on 05-07-2016.
 */
public class TimeConversionActivity extends android.app.Fragment implements View.OnClickListener {

    View timeView;
    TextView textView_yugaResult;
    EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right,spinner_yuga;
    String spinnerText_left, spinnerText_right;
    Button button_convert;
    static LinearLayout layout_main_timeActivity;
    ScrollView scrollView_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        timeView=inflater.inflate(R.layout.activity_time_conversion,container,false);

        MainActivity.home=0;
        ((MainActivity) getActivity())
                .setActionBarTitle("Time Conversion");
        editText_left = (EditText) timeView.findViewById(R.id.editText_left);
        editText_right = (EditText) timeView.findViewById(R.id.editText_right);
        spinner_left = (Spinner) timeView.findViewById(R.id.spinner_left);
        spinner_right = (Spinner) timeView.findViewById(R.id.spinner_right);
        spinner_yuga=(Spinner) timeView.findViewById(R.id.spinner_yuga);
        textView_yugaResult=(TextView)timeView.findViewById(R.id.textView_yugaResult);
        button_convert=(Button)timeView.findViewById(R.id.convert);
        layout_main_timeActivity=(LinearLayout)timeView.findViewById(R.id.layout_main);
        scrollView_time=(ScrollView)timeView.findViewById(R.id.scrollView_time);
        if(ThemesActivity.imageId != null){
           MainActivity.themeColorChange(ThemesActivity.imageId,"time",scrollView_time);
        }else{
            MainActivity.themeColorChange("blue","time",scrollView_time);
        }
        ArrayAdapter<CharSequence> arrayAdapter_left=ArrayAdapter.createFromResource(getActivity(), R.array.time_left_arrays,android.R.layout.simple_spinner_item);
        arrayAdapter_left.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_left.setAdapter(arrayAdapter_left);
        spinner_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(timeView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                if(position==0){
                    editText_left.setText("");
                }
                editText_right.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> arrayAdapter_right=ArrayAdapter.createFromResource(getActivity(), R.array.time_right_arrays,android.R.layout.simple_spinner_item);
        arrayAdapter_right.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_right.setAdapter(arrayAdapter_right);
        spinner_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(timeView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                editText_right.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editText_left.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("edttextleft -> "+editText_left.getText().toString());
                if(editText_left.hasFocus()) {
                    if (s.length() != 0) {
                        spinnerText_left = spinner_left.getSelectedItem().toString();
                        spinnerText_right = spinner_right.getSelectedItem().toString();
                        String val=editText_left.getText().toString();
                        if(val.contains("E") || val.contains("-")) {
                            editText_left.setText("");
                        }else {
                            double value = Double.parseDouble(val);
                            timeCalculationsPart(spinnerText_left, spinnerText_right, value);
                        }
                    } else {
                        editText_right.setText("");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        ArrayAdapter<CharSequence> arrayAdapter_yuga=ArrayAdapter.createFromResource(getActivity(), R.array.yuga_arrays,android.R.layout.simple_spinner_item);
        arrayAdapter_yuga.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_yuga.setAdapter(arrayAdapter_yuga);
        spinner_yuga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        textView_yugaResult.setText("");
                        break;
                    case 1:
                        textView_yugaResult.setText("1,728,000 years");
                        break;
                    case 2:
                        textView_yugaResult.setText("1,296,000 years");
                        break;
                    case 3:
                        textView_yugaResult.setText("864,000 years");
                        break;
                    case 4:
                        textView_yugaResult.setText("432,000 years");
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_convert.setOnClickListener(this);
        return timeView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.convert:

                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(timeView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                spinnerText_left = spinner_left.getSelectedItem().toString();
                spinnerText_right = spinner_right.getSelectedItem().toString();

                if(spinnerText_left.equalsIgnoreCase("select")&&spinnerText_right.equalsIgnoreCase("select")){
                    ((MainActivity)getActivity()).toastMessage("Please select the metric units");
                }else if(spinnerText_left.equalsIgnoreCase("select")){
                    ((MainActivity)getActivity()).toastMessage("Please select any metric unit");
                }else if(spinnerText_right.equalsIgnoreCase("select")){
                    ((MainActivity)getActivity()).toastMessage("Please select any metric unit");
                }else if (editText_left.getText().length() != 0) {
                    String val = editText_left.getText().toString();
                    if(val.contains("E") || val.contains("-")) {
                        editText_left.setText("");
                    }else {
                        Double value=Double.parseDouble(val);
                        timeCalculationsPart(spinnerText_left, spinnerText_right, value);
                    }
                } else {
                    ((MainActivity)getActivity()).toastMessage("Please enter any value");
                }

                break;
        }
    }


    public void timeCalculationsPart(String spinner_left, String spinner_right,double value) {

        if (spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("Century")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("Decade")){
            editText_right.setText(""+Double.toString(value*10));
            return;
        }else if(spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(""+Double.toString(value*100));
            return;
        }else if(spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(""+Double.toString(value*1200));
            return;
        }else if(spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("Week")){
           editText_right.setText(""+Double.toString(value*5214.29));
            return;
        }else if(spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(""+Double.toString(value*36500));
            return;
        }else if(spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(""+Double.toString(value*876000));
            return;
        }else if(spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(""+Double.toString(value*5.256e+7));
            return;
        }else if(spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(""+Double.toString(value*3.154e+9));
            return;
        }else if(spinner_left.equalsIgnoreCase("Century") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(""+Double.toString(value*3.154e+12));
            return;
        }else if (spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("Decade")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("Century")){
            editText_right.setText(""+Double.toString(value/10));
            return;
        }else if(spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(""+Double.toString(value*10));
            return;
        }else if(spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(""+Double.toString(value*120));
            return;
        }else if(spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("Week")){
            editText_right.setText(""+Double.toString(value*521.429));
            return;
        }else if(spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(""+Double.toString(value*3650));
            return;
        }else if(spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(""+Double.toString(value*87600));
            return;
        }else if(spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(""+Double.toString(value*5.256e+6));
            return;
        }else if(spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(""+Double.toString(value*3.154e+8));
            return;
        }else if(spinner_left.equalsIgnoreCase("Decade") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(""+Double.toString(value*3.154e+11));
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("Century")){
            editText_right.setText(""+Double.toString(value/100));
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("Decade")){
            editText_right.setText(""+Double.toString(value/10));
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(""+Double.toString(value*12));
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("Week")){
            editText_right.setText(""+Double.toString(value*52.1429));
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(""+Double.toString(value*365));
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(""+Double.toString(value*8760));
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(""+Double.toString(value*525600));
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(""+Double.toString(value*3.154e+7));
            return;
        }else if(spinner_left.equalsIgnoreCase("Year") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(""+Double.toString(value*3.154e+10));
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("Century")){
            editText_right.setText(""+String.format("%.6f",value/1200));
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("Decade")){
            editText_right.setText(""+String.format("%.6f",value/120));
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(""+String.format("%.6f",value/12));
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("Week")){
            editText_right.setText(""+String.format("%.6f",value*4.34524));
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(""+String.format("%.6f",value*30.4167));
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(""+String.format("%.6f",value*730.001));
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(""+Double.toString(value*43800));
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(""+Double.toString(value*2.628e+6));
            return;
        }else if(spinner_left.equalsIgnoreCase("Month") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(""+Double.toString(value*2.628e+9));
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("Century")){
            editText_right.setText(""+String.format("%.6f",value/5214.29));
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("Decade")){
            editText_right.setText(""+String.format("%.6f",value/521.429));
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(""+String.format("%.6f",value/52.1429));
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(""+String.format("%.6f",value/4.34524));
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("Week")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(""+Double.toString(value*7));
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(""+Double.toString(value*168));
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(""+Double.toString(value*10080));
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(""+Double.toString(value*604800));
            return;
        }else if(spinner_left.equalsIgnoreCase("Week") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(""+Double.toString(value*6.048e+8));
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("Century")){
            editText_right.setText(""+Double.toString(value*2.7397e-5));
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("Decade")){
            editText_right.setText(""+String.format("%.6f",value/3650));
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(""+String.format("%.6f",value/365));
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(""+String.format("%.6f",value/30.4167));
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("Week")){
            editText_right.setText(""+String.format("%.6f",value/7));
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(""+Double.toString(value*24));
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(""+Double.toString(value*1440));
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(""+Double.toString(value*86400));
            return;
        }else if(spinner_left.equalsIgnoreCase("Day") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(""+Double.toString(value*8.64e+7));
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("Century")){
            editText_right.setText(""+Double.toString(value*1.1416e-6));
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("Decade")){
            editText_right.setText(""+Double.toString(value*1.1416e-5));
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(""+String.format("%.6f",value/8760));
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(""+String.format("%.6f",value/730.001));
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("Week")){
            editText_right.setText(""+String.format("%.6f",value/168));
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(""+String.format("%.6f",value/24));
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(""+Double.toString(value*60));
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(""+Double.toString(value*3600));
            return;
        }else if(spinner_left.equalsIgnoreCase("Hour") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(""+Double.toString(value*3.6e+6));
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("Century")){
            editText_right.setText(""+Double.toString(value*1.9026e-8));
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("Decade")){
            editText_right.setText(""+Double.toString(value*1.9026e-7));
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(""+Double.toString(value*1.9026e-6));
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(""+Double.toString(value*2.2831e-5));
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("Week")){
            editText_right.setText(""+Double.toString(value*9.9206e-5));
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(""+String.format("%.6f",value/1440));
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(""+String.format("%.6f",value/60));
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(""+Double.toString(value*60));
            return;
        }else if(spinner_left.equalsIgnoreCase("Minute") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(""+Double.toString(value*60000));
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("Century")){
            editText_right.setText(""+Double.toString(value*3.171e-10));
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("Decade")){
            editText_right.setText(""+Double.toString(value*3.171e-9));
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(""+Double.toString(value*3.171e-8));
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(""+Double.toString(value*3.8052e-7));
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("Week")){
            editText_right.setText(""+Double.toString(value*1.6534e-6));
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(""+Double.toString(value*1.1574e-5));
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(""+String.format("%.6f",value/3600));
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(""+String.format("%.6f",value/60));
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Second") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(""+Double.toString(value*1000));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("Century")){
            editText_right.setText(""+Double.toString(value*3.171e-13));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("Decade")){
            editText_right.setText(""+Double.toString(value*3.171e-12));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("Year")){
            editText_right.setText(""+Double.toString(value*3.171e-11));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("Month")){
            editText_right.setText(""+Double.toString(value*3.8052e-10));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("Week")){
            editText_right.setText(""+Double.toString(value*1.6534e-9));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("Day")){
            editText_right.setText(""+Double.toString(value*1.1574e-8));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("Hour")){
            editText_right.setText(""+Double.toString(value*2.7778e-7));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("Minute")){
            editText_right.setText(""+Double.toString(value*1.6667e-5));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("Second")){
            editText_right.setText(""+Double.toString(value/1000));
            return;
        }else if(spinner_left.equalsIgnoreCase("MilliSecond") && spinner_right.equalsIgnoreCase("MilliSecond")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }
    }
}

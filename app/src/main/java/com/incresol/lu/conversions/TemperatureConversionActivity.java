package com.incresol.lu.conversions;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Incresol-078 on 07-07-2016.
 */
public class TemperatureConversionActivity extends android.app.Fragment implements View.OnClickListener {

    View storageView;
    EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right;
    String spinnerText_left, spinnerText_right;
    Button button_convert;
    Boolean negative = false;
    static LinearLayout layout_main_temperatureActivity;
    ScrollView scrollView_temp;

    //timer and slider <code></code>
    public int currentimageindex=0;
    ImageView imageslider;
    ProgressBar mProgressBar;
    TextView set_timer;
    int TimeCounter=30;
    static LinearLayout layout_timer,layout_slider;
    int[] IMAGE_IDS = {R.drawable.icon_1_temp,R.drawable.icon_2_temp,R.drawable.icon_3_temp};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        storageView=inflater.inflate(R.layout.activity_temperature_conversion,container,false);
        ((MainActivity)getActivity()).setActionBarTitle("Temperature Conversion");
        MainActivity.home=0;
        editText_left = (EditText) storageView.findViewById(R.id.editText_left);
        editText_right = (EditText) storageView.findViewById(R.id.editText_right);
        spinner_left = (Spinner) storageView.findViewById(R.id.spinner_left);
        spinner_right = (Spinner) storageView.findViewById(R.id.spinner_right);
        button_convert=(Button)storageView.findViewById(R.id.convert);
        layout_main_temperatureActivity=(LinearLayout)storageView.findViewById(R.id.layout_main);
        scrollView_temp=(ScrollView)storageView.findViewById(R.id.scrollView_temp);

        //code for timer and image slider
        layout_timer=(LinearLayout)storageView.findViewById(R.id.layout_timer);
        layout_slider=(LinearLayout)storageView.findViewById(R.id.layout_slider);
        imageslider = (ImageView)storageView.findViewById(R.id.imageslider);
        set_timer=(TextView) storageView.findViewById(R.id.set_timer);
        mProgressBar=(ProgressBar)storageView.findViewById(R.id.progressBar);


        if(ThemesActivity.imageId != null){
           MainActivity.themeColorChange(ThemesActivity.imageId,"temp",scrollView_temp);
        }else{
            MainActivity.themeColorChange("blue","temp",scrollView_temp);
        }
        ArrayAdapter<CharSequence> arrayAdapter_left=ArrayAdapter.createFromResource(getActivity(),R.array.temperature_left_arrays,android.R.layout.simple_spinner_item);
        arrayAdapter_left.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_left.setAdapter(arrayAdapter_left);
        spinner_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(storageView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                if(position==0){
                    editText_left.setText("");
                }
                editText_right.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> arrayAdapter_right=ArrayAdapter.createFromResource(getActivity(),R.array.temperature_right_arrays,android.R.layout.simple_spinner_item);
        arrayAdapter_right.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_right.setAdapter(arrayAdapter_right);
        spinner_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(storageView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
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
                        if(val.length()>=2 && val.startsWith("-")) {
                            Log.i("value lenght========>>",""+val.length());
                            //val=val.replace("-","0");
                            double value = Double.parseDouble(val.substring(1));
                            negative=true;
                            temperatureCalculationsPart(spinnerText_left, spinnerText_right, value, negative);
                        }else if(val.equalsIgnoreCase("-")){
                            editText_right.setText("");
                        }else {
                            negative=false;
                            double value = Double.parseDouble(val);
                            temperatureCalculationsPart(spinnerText_left, spinnerText_right, value, negative);
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

        button_convert.setOnClickListener(this);
        ((MainActivity) getActivity()).timerOn(IMAGE_IDS,layout_slider,layout_timer,imageslider,set_timer,mProgressBar);
        return storageView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.convert:
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(storageView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

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
                        negative=false;
                        Double value=Double.parseDouble(val);
                        temperatureCalculationsPart(spinnerText_left, spinnerText_right, value,negative);
                } else {
                    ((MainActivity)getActivity()).toastMessage("Please enter any value");
                }

                break;
        }
    }



    public void temperatureCalculationsPart(String spinner_left, String spinner_right,double value,boolean negative) {
        if (spinner_left.equalsIgnoreCase("Celsius") && spinner_right.equalsIgnoreCase("Celsius")) {
            if (negative) {
                editText_right.setText(editText_left.getText().toString());
            } else {
                editText_right.setText(editText_left.getText().toString());
            }
            return;
        }else if (spinner_left.equalsIgnoreCase("Celsius") && spinner_right.equalsIgnoreCase("Fahrenheit")) {
            if (negative) {
                editText_right.setText(""+Double.toString(((value*-1)* 1.8000)+32.00));
            } else {
                editText_right.setText(""+Double.toString((value* 1.8000)+32.00));
            }
            return;
        }else if (spinner_left.equalsIgnoreCase("Celsius") && spinner_right.equalsIgnoreCase("Kelvin")) {
            if (negative) {
                editText_right.setText(""+Double.toString((value*-1) + 273.15));
            } else {
                editText_right.setText(""+Double.toString(value + 273.15));
            }
            return;
        }else if (spinner_left.equalsIgnoreCase("Fahrenheit") && spinner_right.equalsIgnoreCase("Fahrenheit")) {
            if (negative) {
                editText_right.setText(editText_left.getText().toString());
            } else {
                editText_right.setText(editText_left.getText().toString());
            }
            return;
        }else if (spinner_left.equalsIgnoreCase("Fahrenheit") && spinner_right.equalsIgnoreCase("Celsius")) {
            if (negative) {
                editText_right.setText(""+String.format("%.3f",(((value*-1)-32.00)/1.8000)));
            } else {
                editText_right.setText(""+String.format("%.3f",((value-32.00)/1.8000)));
            }
            return;
        }else if (spinner_left.equalsIgnoreCase("Fahrenheit") && spinner_right.equalsIgnoreCase("Kelvin")) {
            if (negative) {
                editText_right.setText(""+String.format("%.3f",((((value*-1)-32)/1.8000))+273.15));
            } else {
                editText_right.setText(""+String.format("%.3f",(((value-32)/1.8000))+273.15));
            }
            return;
        }else if (spinner_left.equalsIgnoreCase("Kelvin") && spinner_right.equalsIgnoreCase("Kelvin")) {
            if (negative) {
                editText_right.setText(editText_left.getText().toString());
            } else {
                editText_right.setText(editText_left.getText().toString());
            }
            return;
        }else if (spinner_left.equalsIgnoreCase("Kelvin") && spinner_right.equalsIgnoreCase("Celsius")) {
            if (negative) {
                editText_right.setText(""+String.format("%.3f",(value*-1) - 273.15));
            } else {
                editText_right.setText(""+String.format("%.3f",value - 273.15));
            }
            return;
        }else if (spinner_left.equalsIgnoreCase("Kelvin") && spinner_right.equalsIgnoreCase("Fahrenheit")) {
            if (negative) {
                editText_right.setText(""+String.format("%.3f",((((value*-1)-273.15)*1.8000))+32.00));
            } else {
                editText_right.setText(""+String.format("%.3f",(((value-273.15)*1.8000))+32.00));
            }
            return;
        }
    }
}

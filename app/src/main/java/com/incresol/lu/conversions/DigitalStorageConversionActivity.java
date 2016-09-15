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
public class DigitalStorageConversionActivity extends android.app.Fragment implements View.OnClickListener {

    View storageView;
    EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right;
    String spinnerText_left, spinnerText_right;
    Button button_convert;
    static LinearLayout layout_main_digitalActivity;
    ScrollView scrollView_digital;

    //timer and slider <code></code>
    public int currentimageindex=0;
    ImageView imageslider;
    ProgressBar mProgressBar;
    TextView set_timer;
    int TimeCounter=30;
    static LinearLayout layout_timer,layout_slider;
    int[] IMAGE_IDS = {R.drawable.icon_1_digital,R.drawable.icon_2_digitel,R.drawable.icon_3_digitel};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        storageView=inflater.inflate(R.layout.activity_digitalstorage_conversion,container,false);
        layout_main_digitalActivity=(LinearLayout)storageView.findViewById(R.id.layout_main);
        scrollView_digital=(ScrollView)storageView.findViewById(R.id.scrollView_digital);
        MainActivity.home=0;
        if(ThemesActivity.imageId != null){
           MainActivity.themeColorChange(ThemesActivity.imageId,"digital",scrollView_digital);
        }else{
            MainActivity.themeColorChange("blue","digital",scrollView_digital);
        }

        ((MainActivity)getActivity()).setActionBarTitle("Digital Storage Conversion");

        editText_left = (EditText) storageView.findViewById(R.id.editText_left);
        editText_right = (EditText) storageView.findViewById(R.id.editText_right);
        spinner_left = (Spinner) storageView.findViewById(R.id.spinner_left);
        spinner_right = (Spinner) storageView.findViewById(R.id.spinner_right);
        button_convert=(Button)storageView.findViewById(R.id.convert);


        //code for timer and image slider
        layout_timer=(LinearLayout)storageView.findViewById(R.id.layout_timer);
        layout_slider=(LinearLayout)storageView.findViewById(R.id.layout_slider);
        imageslider = (ImageView)storageView.findViewById(R.id.imageslider);
        set_timer=(TextView) storageView.findViewById(R.id.set_timer);
        mProgressBar=(ProgressBar)storageView.findViewById(R.id.progressBar);

        ArrayAdapter<CharSequence> arrayAdapter_left=ArrayAdapter.createFromResource(getActivity(),R.array.storage_left_arrays,android.R.layout.simple_spinner_item);
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

        ArrayAdapter<CharSequence> arrayAdapter_right=ArrayAdapter.createFromResource(getActivity(),R.array.storage_right_arrays,android.R.layout.simple_spinner_item);
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
                        if(val.contains("E") || val.contains("-")) {
                            editText_left.setText("");
                        }else {
                            double value = Double.parseDouble(val);
                            storageCalculationsPart(spinnerText_left, spinnerText_right, value, 0);
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
                    if(val.contains("E") || val.contains("-")) {
                        editText_left.setText("");
                    }else {
                        Double value=Double.parseDouble(val);
                        storageCalculationsPart(spinnerText_left, spinnerText_right, value,0);
                    }
                } else {
                    ((MainActivity)getActivity()).toastMessage("Please enter any value");
                }

                break;
        }
    }


    public void storageCalculationsPart(String spinner_left, String spinner_right,double value,int from) {
        if (spinner_left.equalsIgnoreCase("Bit") && spinner_right.equalsIgnoreCase("Bit")) {
            if (from == 1) {
                editText_left.setText(editText_right.getText().toString());
            } else {
                editText_right.setText(editText_left.getText().toString());
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Bit") && spinner_right.equalsIgnoreCase("Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*8));
            }else {
                editText_right.setText(""+Double.toString(value/8));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Bit") && spinner_right.equalsIgnoreCase("Kilo Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*8000));
            }else {
                editText_right.setText(""+String.format("%.6f",value/8000));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Bit") && spinner_right.equalsIgnoreCase("Mega Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*8e+6));
            }else {
                editText_right.setText(""+Double.toString(value*1.25e-7));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Bit") && spinner_right.equalsIgnoreCase("Giga Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*8e+9));
            }else {
                editText_right.setText(""+Double.toString(value*1.25e-10));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Bit") && spinner_right.equalsIgnoreCase("Tera Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*8e+12));
            }else {
                editText_right.setText(""+Double.toString(value*1.25e-13));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Bit") && spinner_right.equalsIgnoreCase("Peta Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*8e+15));
            }else {
                editText_right.setText(""+Double.toString(value*1.25e-16));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Byte") && spinner_right.equalsIgnoreCase("Bit")){
            if (from==1){
                editText_left.setText(""+Double.toString(value/8));
            }else {
                editText_right.setText(""+Double.toString(value*8));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Byte") && spinner_right.equalsIgnoreCase("Byte")){
            if (from==1){
                editText_left.setText(editText_right.getText());
            }else {
                editText_right.setText(editText_left.getText());
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Byte") && spinner_right.equalsIgnoreCase("Kilo Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1024));
            }else {
                editText_right.setText(""+Double.toString(value/1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Byte") && spinner_right.equalsIgnoreCase("Mega Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+6));
            }else {
                editText_right.setText(""+Double.toString(value*1e-6));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Byte") && spinner_right.equalsIgnoreCase("Giga Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+9));
            }else {
                editText_right.setText(""+Double.toString(value*1e-9));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Byte") && spinner_right.equalsIgnoreCase("Tera Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+12));
            }else {
                editText_right.setText(""+Double.toString(value*1e-12));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Byte") && spinner_right.equalsIgnoreCase("Peta Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+15));
            }else {
                editText_right.setText(""+Double.toString(value*1e-15));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilo Byte") && spinner_right.equalsIgnoreCase("Bit")){
            if (from==1){
                editText_left.setText(""+String.format("%.6f",value/8000));
            }else {
                editText_right.setText(""+Double.toString(value*8000));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilo Byte") && spinner_right.equalsIgnoreCase("Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value/1024));
            }else {
                editText_right.setText(""+Double.toString(value*1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilo Byte") && spinner_right.equalsIgnoreCase("Kilo Byte")){
            if (from==1){
                editText_left.setText(editText_right.getText());
            }else {
                editText_right.setText(editText_left.getText());
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilo Byte") && spinner_right.equalsIgnoreCase("Mega Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1024));
            }else {
                editText_right.setText(""+Double.toString(value/1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilo Byte") && spinner_right.equalsIgnoreCase("Giga Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+6));
            }else {
                editText_right.setText(""+Double.toString(value*1e-6));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilo Byte") && spinner_right.equalsIgnoreCase("Tera Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+9));
            }else {
                editText_right.setText(""+Double.toString(value*1e-9));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilo Byte") && spinner_right.equalsIgnoreCase("Peta Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+12));
            }else {
                editText_right.setText(""+Double.toString(value*1e-12));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Mega Byte") && spinner_right.equalsIgnoreCase("Bit")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1.25e-7));
            }else {
                editText_right.setText(""+Double.toString(value*8e+6));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Mega Byte") && spinner_right.equalsIgnoreCase("Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-6));
            }else {
                editText_right.setText(""+Double.toString(value*1e+6));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Mega Byte") && spinner_right.equalsIgnoreCase("Kilo Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value/1024));
            }else {
                editText_right.setText(""+Double.toString(value*1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Mega Byte") && spinner_right.equalsIgnoreCase("Mega Byte")){
            if (from==1){
                editText_left.setText(editText_right.getText());
            }else {
                editText_right.setText(editText_left.getText());
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Mega Byte") && spinner_right.equalsIgnoreCase("Giga Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1024));
            }else {
                editText_right.setText(""+Double.toString(value/1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Mega Byte") && spinner_right.equalsIgnoreCase("Tera Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+6));
            }else {
                editText_right.setText(""+Double.toString(value*1e-6));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Mega Byte") && spinner_right.equalsIgnoreCase("Peta Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+9));
            }else {
                editText_right.setText(""+Double.toString(value*1e-9));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Giga Byte") && spinner_right.equalsIgnoreCase("Bit")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1.25e-10));
            }else {
                editText_right.setText(""+Double.toString(value*8e+9));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Giga Byte") && spinner_right.equalsIgnoreCase("Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-9));
            }else {
                editText_right.setText(""+Double.toString(value*1e+9));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Giga Byte") && spinner_right.equalsIgnoreCase("Kilo Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-6));
            }else {
                editText_right.setText(""+Double.toString(value*1e+6));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Giga Byte") && spinner_right.equalsIgnoreCase("Mega Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value/1024));
            }else {
                editText_right.setText(""+Double.toString(value*1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Giga Byte") && spinner_right.equalsIgnoreCase("Giga Byte")){
            if (from==1){
                editText_left.setText(editText_right.getText());
            }else {
                editText_right.setText(editText_left.getText());
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Giga Byte") && spinner_right.equalsIgnoreCase("Tera Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1024));
            }else {
                editText_right.setText(""+Double.toString(value/1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Giga Byte") && spinner_right.equalsIgnoreCase("Peta Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e+6));
            }else {
                editText_right.setText(""+Double.toString(value*1e-6));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Tera Byte") && spinner_right.equalsIgnoreCase("Bit")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1.25e-13));
            }else {
                editText_right.setText(""+Double.toString(value*8e+12));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Tera Byte") && spinner_right.equalsIgnoreCase("Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-12));
            }else {
                editText_right.setText(""+Double.toString(value*1e+12));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Tera Byte") && spinner_right.equalsIgnoreCase("Kilo Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-9));
            }else {
                editText_right.setText(""+Double.toString(value*1e+9));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Tera Byte") && spinner_right.equalsIgnoreCase("Mega Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-6));
            }else {
                editText_right.setText(""+Double.toString(value*1e+6));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Tera Byte") && spinner_right.equalsIgnoreCase("Giga Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value/1024));
            }else {
                editText_right.setText(""+Double.toString(value*1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Tera Byte") && spinner_right.equalsIgnoreCase("Tera Byte")){
            if (from==1){
                editText_left.setText(editText_right.getText());
            }else {
                editText_right.setText(editText_left.getText());
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Tera Byte") && spinner_right.equalsIgnoreCase("Peta Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1024));
            }else {
                editText_right.setText(""+Double.toString(value/1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Peta Byte") && spinner_right.equalsIgnoreCase("Bit")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1.25e-16));
            }else {
                editText_right.setText(""+Double.toString(value*8e+15));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Peta Byte") && spinner_right.equalsIgnoreCase("Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-15));
            }else {
                editText_right.setText(""+Double.toString(value*1e+15));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Peta Byte") && spinner_right.equalsIgnoreCase("Kilo Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-12));
            }else {
                editText_right.setText(""+Double.toString(value*1e+12));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Peta Byte") && spinner_right.equalsIgnoreCase("Mega Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-9));
            }else {
                editText_right.setText(""+Double.toString(value*1e+9));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Peta Byte") && spinner_right.equalsIgnoreCase("Giga Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value*1e-6));
            }else {
                editText_right.setText(""+Double.toString(value*1e+6));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Peta Byte") && spinner_right.equalsIgnoreCase("Tera Byte")){
            if (from==1){
                editText_left.setText(""+Double.toString(value/1024));
            }else {
                editText_right.setText(""+Double.toString(value*1024));
            }
            return;
        }else if(spinner_left.equalsIgnoreCase("Peta Byte") && spinner_right.equalsIgnoreCase("Peta Byte")){
            if (from==1){
                editText_left.setText(editText_right.getText());
            }else {
                editText_right.setText(editText_left.getText());
            }
            return;
        }
    }
}

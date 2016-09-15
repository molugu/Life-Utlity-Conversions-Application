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
 * Created by Incresol-078 on 30-06-2016.
 */
public class MassConversionActivity extends android.app.Fragment implements View.OnClickListener{
    View weightView;
    EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right;
    String spinnerText_left, spinnerText_right;
    Button button_convert;
    static LinearLayout layout_main_massActivity;
    ScrollView scrollView_mass;

    //timer and slider <code></code>
    public int currentimageindex=0;
    ImageView imageslider;
    ProgressBar mProgressBar;
    TextView set_timer;
    int TimeCounter=30;
    static LinearLayout layout_timer,layout_slider;
    int[] IMAGE_IDS = {R.drawable.icon_2_bmi,R.drawable.icon_2_weight,R.drawable.icon_1_weight};

    ArrayAdapter<CharSequence> arrayAdapter_right;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            weightView=inflater.inflate(R.layout.activity_mass_conversion,container,false);
        ((MainActivity) getActivity()).setActionBarTitle("Mass Conversion");
        editText_left = (EditText) weightView.findViewById(R.id.editText_left);
        editText_right = (EditText) weightView.findViewById(R.id.editText_right);
        spinner_left = (Spinner) weightView.findViewById(R.id.spinner_left);
        spinner_right = (Spinner) weightView.findViewById(R.id.spinner_right);
        button_convert=(Button)weightView.findViewById(R.id.convert);
        layout_main_massActivity=(LinearLayout)weightView.findViewById(R.id.layout_main);
        MainActivity.home=0;
        //code for timer and image slider
        layout_timer=(LinearLayout)weightView.findViewById(R.id.layout_timer);
        layout_slider=(LinearLayout)weightView.findViewById(R.id.layout_slider);
        imageslider = (ImageView)weightView.findViewById(R.id.imageslider);
        set_timer=(TextView) weightView.findViewById(R.id.set_timer);
        mProgressBar=(ProgressBar)weightView.findViewById(R.id.progressBar);
        scrollView_mass=(ScrollView)weightView.findViewById(R.id.scrollView_mass);

        if(ThemesActivity.imageId != null){
            MainActivity.themeColorChange(ThemesActivity.imageId,"mass",scrollView_mass);
        }
        else{
            MainActivity.themeColorChange("blue","mass",scrollView_mass);
        }
        ArrayAdapter<CharSequence> arrayAdapter_left = ArrayAdapter.createFromResource(getActivity(), R.array.weight_left_arrays, android.R.layout.simple_spinner_item);
        arrayAdapter_left.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter_right = ArrayAdapter.createFromResource(getActivity(), R.array.weight_right_arrays,android.R.layout.simple_spinner_item);
        arrayAdapter_right.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_left.setAdapter(arrayAdapter_left);
        spinner_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(weightView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                if(position==0){
                    editText_left.setText("");
                }
                if(position==8){
                   // ((MainActivity) getActivity()).toastMessage("Litre will only be converted to Kg.");
                    arrayAdapter_right = ArrayAdapter.createFromResource(getActivity(), R.array.kg_array,android.R.layout.simple_spinner_item);
                    arrayAdapter_right.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    spinner_right.setAdapter(arrayAdapter_right);

                }else{
                    arrayAdapter_right = ArrayAdapter.createFromResource(getActivity(), R.array.weight_right_arrays,android.R.layout.simple_spinner_item);
                    arrayAdapter_right.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_right.setAdapter(arrayAdapter_right);
                }
                editText_right.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinner_right.setAdapter(arrayAdapter_right);
        spinner_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(weightView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
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
                    if (editText_left.getText().toString().length() != 0) {
                        spinnerText_left = spinner_left.getSelectedItem().toString();
                        spinnerText_right = spinner_right.getSelectedItem().toString();
                        String val = editText_left.getText().toString();
                        if(val.contains("E") || val.contains("-")) {
                            editText_left.setText("");
                        }else {
                            Double value = Double.parseDouble(val);
                            weightCalculation(spinnerText_left, spinnerText_right, value);
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
        return weightView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.convert:
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(weightView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                spinnerText_left = spinner_left.getSelectedItem().toString();
                spinnerText_right = spinner_right.getSelectedItem().toString();

                if(spinnerText_left.equalsIgnoreCase("select")&&spinnerText_right.equalsIgnoreCase("select")){
                    ((MainActivity) getActivity()).toastMessage("Please select the metric units");
                }else if(spinnerText_left.equalsIgnoreCase("select")){
                    ((MainActivity) getActivity()).toastMessage("Please select any metric unit");
                }else if(spinnerText_right.equalsIgnoreCase("select")){
                    ((MainActivity) getActivity()).toastMessage("Please select any metric unit");
                }else if (editText_left.getText().length() != 0) {
                    String val = editText_left.getText().toString();
                    if(val.contains("E") || val.contains("-")) {
                        editText_left.setText("");
                    }else {
                        Double value=Double.parseDouble(val);
                        weightCalculation(spinnerText_left, spinnerText_right, value);
                    }
                } else {
                    ((MainActivity) getActivity()).toastMessage("Please enter any value");
                }

                break;
        }
    }

    public void weightCalculation(String spinnerText_left,String spinnerText_right,double value){
        if(spinnerText_left.equalsIgnoreCase("Tonne") && spinnerText_right.equalsIgnoreCase("Tonne")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Tonne") && spinnerText_right.equalsIgnoreCase("Kg")){
            editText_right.setText(""+Double.toString(value * 1000));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Tonne") && spinnerText_right.equalsIgnoreCase("Gram")){
            editText_right.setText(""+Double.toString(value /1e-06));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Tonne") && spinnerText_right.equalsIgnoreCase("Milligram")){
            editText_right.setText(""+Double.toString(value *1e+9));
            return;
        }
        else if(spinnerText_left.equalsIgnoreCase("Tonne") && spinnerText_right.equalsIgnoreCase("Microgram")){
            editText_right.setText(""+Double.toString(value /1e-12));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Tonne") && spinnerText_right.equalsIgnoreCase("Pound")){
            editText_right.setText(""+Double.toString(value * 2204.6));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Tonne") && spinnerText_right.equalsIgnoreCase("Quintal")){
            editText_right.setText(""+Double.toString(value * 10));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Tonne") && spinnerText_right.equalsIgnoreCase("Wt. on moon")){
            editText_right.setText(""+Double.toString(value * 0.1653414)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Tonne") && spinnerText_right.equalsIgnoreCase("Wt. on mars")){
            editText_right.setText(""+Double.toString(value * 0.3782874)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Kg") && spinnerText_right.equalsIgnoreCase("Tonne")){
            editText_right.setText(""+Double.toString(value / 1000));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Kg") && spinnerText_right.equalsIgnoreCase("Kg")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }
        else if(spinnerText_left.equalsIgnoreCase("Kg") && spinnerText_right.equalsIgnoreCase("Gram")){
            editText_right.setText(""+Double.toString(value * 1000));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Kg") && spinnerText_right.equalsIgnoreCase("Milligram")){
            editText_right.setText(""+Double.toString(value /1e-06));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Kg") && spinnerText_right.equalsIgnoreCase("Microgram")){
            editText_right.setText(""+Double.toString(value *1e+09));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Kg") && spinnerText_right.equalsIgnoreCase("Pound")){
            editText_right.setText(""+Double.toString(value * 2.2046));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Kg") && spinnerText_right.equalsIgnoreCase("Quintal")){
            editText_right.setText(""+Double.toString(value / 100));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Kg") && spinnerText_right.equalsIgnoreCase("Wt. on moon")){
            editText_right.setText(""+Double.toString(value * 0.1653414)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Kg") && spinnerText_right.equalsIgnoreCase("Wt. on mars")){
            editText_right.setText(""+Double.toString(value * 0.3782874)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Gram") && spinnerText_right.equalsIgnoreCase("Tonne")){
            editText_right.setText(""+Double.toString(value /1e+6));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Gram") && spinnerText_right.equalsIgnoreCase("Kg")){
            editText_right.setText(""+Double.toString(value / 1000));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Gram") && spinnerText_right.equalsIgnoreCase("Gram")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Gram") && spinnerText_right.equalsIgnoreCase("Milligram")){
           editText_right.setText(""+Double.toString(value / 0.001));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Gram") && spinnerText_right.equalsIgnoreCase("Microgram")){
            editText_right.setText(""+Double.toString(value / 1e-06));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Gram") && spinnerText_right.equalsIgnoreCase("Pound")){
            editText_right.setText(""+Double.toString(value * 0.0022046));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Gram") && spinnerText_right.equalsIgnoreCase("Quintal")){
            editText_right.setText(""+Double.toString(value / 100000));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Gram") && spinnerText_right.equalsIgnoreCase("Wt. on moon")){
            editText_right.setText(""+Double.toString(value * 0.1653414)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Gram") && spinnerText_right.equalsIgnoreCase("Wt. on mars")){
            editText_right.setText(""+Double.toString(value * 0.3782874)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Milligram") && spinnerText_right.equalsIgnoreCase("Tonne")){
            editText_right.setText(""+Double.toString(value /1e+09));
            return;
        }
        else if(spinnerText_left.equalsIgnoreCase("Milligram") && spinnerText_right.equalsIgnoreCase("Kg")){
            editText_right.setText(""+Double.toString(value / 1e+06));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Milligram") && spinnerText_right.equalsIgnoreCase("Gram")){
            editText_right.setText(""+Double.toString(value /1000));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Milligram") && spinnerText_right.equalsIgnoreCase("Milligram")){
           editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Milligram") && spinnerText_right.equalsIgnoreCase("Microgram")){
            editText_right.setText(""+Double.toString(value /0.001));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Milligram") && spinnerText_right.equalsIgnoreCase("Pound")){
            editText_right.setText(""+Double.toString(value * 2.2046228e-06));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Milligram") && spinnerText_right.equalsIgnoreCase("Quintal")){
            editText_right.setText(""+Double.toString(value / 1e+08));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Milligram") && spinnerText_right.equalsIgnoreCase("Wt. on moon")){
            editText_right.setText(""+Double.toString(value * 0.1653414)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Milligram") && spinnerText_right.equalsIgnoreCase("Wt. on mars")){
            editText_right.setText(""+Double.toString(value * 0.3782874)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Microgram") && spinnerText_right.equalsIgnoreCase("Tonne")){
            editText_right.setText(""+Double.toString(value /1e+12));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Microgram") && spinnerText_right.equalsIgnoreCase("Kg")){
            editText_right.setText(""+Double.toString(value / 1e+09));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Microgram") && spinnerText_right.equalsIgnoreCase("Gram")){
            editText_right.setText(""+Double.toString(value /1000000));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Microgram") && spinnerText_right.equalsIgnoreCase("Milligram")){
            editText_right.setText(""+Double.toString(value /1000));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Microgram") && spinnerText_right.equalsIgnoreCase("Microgram")){
            editText_right.setText(editText_left.getText().toString());
        }else if(spinnerText_left.equalsIgnoreCase("Microgram") && spinnerText_right.equalsIgnoreCase("Pound")){
            editText_right.setText(""+Double.toString(value * 2.2046228e-09));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Microgram") && spinnerText_right.equalsIgnoreCase("Quintal")){
            editText_right.setText(""+Double.toString(value / 1e+11));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Microgram") && spinnerText_right.equalsIgnoreCase("Wt. on moon")){
            editText_right.setText(""+Double.toString(value * 0.1653414)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Microgram") && spinnerText_right.equalsIgnoreCase("Wt. on mars")){
            editText_right.setText(""+Double.toString(value * 0.3782874)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Pound") && spinnerText_right.equalsIgnoreCase("Tonne")){
            editText_right.setText(""+Double.toString(value / 2204.6));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Pound") && spinnerText_right.equalsIgnoreCase("Kg")){
            editText_right.setText(""+String.format("%.3f",value / 2.2046));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Pound") && spinnerText_right.equalsIgnoreCase("Gram")){
            editText_right.setText(""+String.format("%.3f",value /0.0022046));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Pound") && spinnerText_right.equalsIgnoreCase("Milligram")){
            editText_right.setText(""+String.format("%.3f",value / 2.2046228e-06));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Pound") && spinnerText_right.equalsIgnoreCase("Microgram")){
            editText_right.setText(""+String.format("%.3f",value / 2.2046228e-09));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Pound") && spinnerText_right.equalsIgnoreCase("Pound")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Pound") && spinnerText_right.equalsIgnoreCase("Quintal")){
            editText_right.setText(""+String.format("%.4f",value / 220.462));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Pound") && spinnerText_right.equalsIgnoreCase("Wt. on moon")){
            editText_right.setText(""+Double.toString(value * 0.1653414)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Pound") && spinnerText_right.equalsIgnoreCase("Wt. on mars")){
            editText_right.setText(""+Double.toString(value * 0.3782874)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Quintal") && spinnerText_right.equalsIgnoreCase("Tonne")){
            editText_right.setText(""+Double.toString(value / 10));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Quintal") && spinnerText_right.equalsIgnoreCase("Kg")){
            editText_right.setText(""+Double.toString(value * 100));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Quintal") && spinnerText_right.equalsIgnoreCase("Gram")){
            editText_right.setText(""+Double.toString(value * 100000));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Quintal") && spinnerText_right.equalsIgnoreCase("Milligram")){
            editText_right.setText(""+Double.toString(value * 1e+08));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Quintal") && spinnerText_right.equalsIgnoreCase("Microgram")){
            editText_right.setText(""+Double.toString(value * 1e+11));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Quintal") && spinnerText_right.equalsIgnoreCase("Pound")){
            editText_right.setText(""+Double.toString(value * 220.462));
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Quintal") && spinnerText_right.equalsIgnoreCase("Quintal")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Quintal") && spinnerText_right.equalsIgnoreCase("Wt. on moon")){
            editText_right.setText(""+Double.toString(value * 0.1653414)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Quintal") && spinnerText_right.equalsIgnoreCase("Wt. on mars")){
           editText_right.setText(""+Double.toString(value * 0.3782874)+" "+spinnerText_left);
            return;
        }else if(spinnerText_left.equalsIgnoreCase("Litre") && spinnerText_right.equalsIgnoreCase("Kg")){
            editText_right.setText(""+Double.toString(value * 0.91));
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Onresume in weight ","====>");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Onpause in weight ","====>");
    }

}

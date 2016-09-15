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
 * Created by Incresol-078 on 01-07-2016.
 */
public class AreaConversionActivity extends android.app.Fragment implements View.OnClickListener {

    View areaView;
    EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right;
    String spinnerText_left, spinnerText_right;
    Button button_convert;
   static LinearLayout layout_main_areaActivity;
    ScrollView scrollView_area;

    //timer and slider <code></code>
    public int currentimageindex=0;
    ImageView imageslider;
    ProgressBar mProgressBar;
    TextView set_timer;
    int TimeCounter=30;
    static LinearLayout layout_timer,layout_slider;
    int[] IMAGE_IDS = {R.drawable.icon_2_area,R.drawable.icon_1_area,R.drawable.icon_3_area};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        areaView=inflater.inflate(R.layout.activity_area_conversion,container,false);
        layout_main_areaActivity=(LinearLayout)areaView.findViewById(R.id.layout_main);
        scrollView_area=(ScrollView)areaView.findViewById(R.id.scrollView_area);
        MainActivity.home=0;
        if(ThemesActivity.imageId != null){
            MainActivity.themeColorChange(ThemesActivity.imageId,"area",scrollView_area);
        }
        else{
            MainActivity.themeColorChange("blue","area",scrollView_area);
        }

        ((MainActivity) getActivity())
                .setActionBarTitle("Area Conversion");
        editText_left = (EditText) areaView.findViewById(R.id.editText_left);
        editText_right = (EditText) areaView.findViewById(R.id.editText_right);
        spinner_left = (Spinner) areaView.findViewById(R.id.spinner_left);
        spinner_right = (Spinner) areaView.findViewById(R.id.spinner_right);
        button_convert=(Button)areaView.findViewById(R.id.convert);


        //code for timer and image slider
        layout_timer=(LinearLayout)areaView.findViewById(R.id.layout_timer);
        layout_slider=(LinearLayout)areaView.findViewById(R.id.layout_slider);
        imageslider = (ImageView)areaView.findViewById(R.id.imageslider);
        set_timer=(TextView) areaView.findViewById(R.id.set_timer);
        mProgressBar=(ProgressBar)areaView.findViewById(R.id.progressBar);


        ArrayAdapter<CharSequence> arrayAdapter_left = ArrayAdapter.createFromResource(getActivity(), R.array.area_left_arrays, android.R.layout.simple_spinner_item);
        arrayAdapter_left.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_left.setAdapter(arrayAdapter_left);
        spinner_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(areaView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                if(position==0){
                    editText_left.setText("");
                }
                editText_right.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> arrayAdapter_right = ArrayAdapter.createFromResource(getActivity(), R.array.area_right_arrays, android.R.layout.simple_spinner_item);
        arrayAdapter_right.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_right.setAdapter(arrayAdapter_right);
        spinner_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(areaView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
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
                            areaCalculationsPart(spinnerText_left, spinnerText_right, value);
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
        return areaView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.convert:
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(areaView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

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
                        areaCalculationsPart(spinnerText_left, spinnerText_right, value);
                    }
                } else {
                    ((MainActivity)getActivity()).toastMessage("Please enter any value");
                }

                break;
        }
    }


    public void areaCalculationsPart(String spinner_left, String spinner_right,double value) {
        if (spinner_left.equalsIgnoreCase("Acre") && spinner_right.equalsIgnoreCase("Acre")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if (spinner_left.equalsIgnoreCase("Acre") && spinner_right.equalsIgnoreCase("Hectare")) {
            editText_right.setText(""+String.format("%.3f",value/2.4711));
            return;
        }else if (spinner_left.equalsIgnoreCase("Acre") && spinner_right.equalsIgnoreCase("Sq.Foot")) {
           editText_right.setText(""+Double.toString(value * 43560));
            return;
        }else if (spinner_left.equalsIgnoreCase("Acre") && spinner_right.equalsIgnoreCase("Sq.Metre")) {
            editText_right.setText(""+String.format("%.3f",value / 0.00024711));
            return;
        }else if (spinner_left.equalsIgnoreCase("Acre") && spinner_right.equalsIgnoreCase("Sq.Yard")) {
            editText_right.setText(""+Double.toString(value * 4840.0));
            return;
        }else if (spinner_left.equalsIgnoreCase("Acre") && spinner_right.equalsIgnoreCase("Sq.Inch")) {
            editText_right.setText(""+Double.toString(value  * 6272600));
            return;
        }else if (spinner_left.equalsIgnoreCase("Acre") && spinner_right.equalsIgnoreCase("Sq.Cm")) {
            editText_right.setText(""+Double.toString(value * 4.047e+7));
            return;
        }else if (spinner_left.equalsIgnoreCase("Acre") && spinner_right.equalsIgnoreCase("Sq.mm")) {
            editText_right.setText(""+Double.toString(value * 4.047e+09));
            return;
        }else if (spinner_left.equalsIgnoreCase("Acre") && spinner_right.equalsIgnoreCase("Guntha")) {
            editText_right.setText(""+String.format("%.4f",value / 0.02499455));
            return;
        }else if (spinner_left.equalsIgnoreCase("Hectare") && spinner_right.equalsIgnoreCase("Acre")) {
            editText_right.setText(""+Double.toString(value*2.4711));
            return;
        }else if (spinner_left.equalsIgnoreCase("Hectare") && spinner_right.equalsIgnoreCase("Hectare")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if (spinner_left.equalsIgnoreCase("Hectare") && spinner_right.equalsIgnoreCase("Sq.Yard")) {
            editText_right.setText(""+Double.toString(value* 11960));
            return;
        }else if (spinner_left.equalsIgnoreCase("Hectare") && spinner_right.equalsIgnoreCase("Sq.Metre")) {
            editText_right.setText(""+Double.toString(value*10000));
            return;
        }else if (spinner_left.equalsIgnoreCase("Hectare") && spinner_right.equalsIgnoreCase("Sq.Foot")) {
            editText_right.setText(""+Double.toString(value* 107640));
            return;
        }else if (spinner_left.equalsIgnoreCase("Hectare") && spinner_right.equalsIgnoreCase("Sq.Inch")) {
            editText_right.setText(""+Double.toString(value* 15500000));
            return;
        }else if (spinner_left.equalsIgnoreCase("Hectare") && spinner_right.equalsIgnoreCase("Sq.Cm")) {
            editText_right.setText(""+Double.toString(value / 1e-08));
            return;
        }else if (spinner_left.equalsIgnoreCase("Hectare") && spinner_right.equalsIgnoreCase("Sq.mm")) {
            editText_right.setText(""+Double.toString(value / 1e-10));
            return;
        }else if (spinner_left.equalsIgnoreCase("Hectare") && spinner_right.equalsIgnoreCase("Guntha")) {
            editText_right.setText(""+String.format("%.4f",value * 98.8435306909));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Yard") && spinner_right.equalsIgnoreCase("Acre")) {
            editText_right.setText(""+Double.toString(value / 4840.0));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Yard") && spinner_right.equalsIgnoreCase("Hectare")) {
            editText_right.setText(""+Double.toString(value / 11960));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Yard") && spinner_right.equalsIgnoreCase("Sq.Yard")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Yard") && spinner_right.equalsIgnoreCase("Sq.Metre")) {
            editText_right.setText(""+String.format("%.4f",value / 1.1960));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Yard") && spinner_right.equalsIgnoreCase("Sq.Foot")) {
            editText_right.setText(""+Double.toString(value  * 9.0000));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Yard") && spinner_right.equalsIgnoreCase("Sq.Inch")) {
            editText_right.setText(""+Double.toString(value  * 1296.0));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Yard") && spinner_right.equalsIgnoreCase("Sq.Cm")) {
            editText_right.setText(""+String.format("%.4f",value  / 0.00011960));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Yard") && spinner_right.equalsIgnoreCase("Sq.mm")) {
            editText_right.setText(""+Double.toString(value  * 836127.36));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Yard") && spinner_right.equalsIgnoreCase("Guntha")) {
            editText_right.setText(""+String.format("%.4f",value /121));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Metre") && spinner_right.equalsIgnoreCase("Acre")) {
            editText_right.setText(""+Double.toString(value * 0.00024711));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Metre") && spinner_right.equalsIgnoreCase("Hectare")) {
            editText_right.setText(""+Double.toString(value / 10000));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Metre") && spinner_right.equalsIgnoreCase("Sq.Yard")) {
            editText_right.setText(""+Double.toString(value * 1.1960));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Metre") && spinner_right.equalsIgnoreCase("Sq.Foot")) {
            editText_right.setText(""+Double.toString(value * 10.7639));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Metre") && spinner_right.equalsIgnoreCase("Sq.Metre")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Metre") && spinner_right.equalsIgnoreCase("Sq.Inch")) {
            editText_right.setText(""+Double.toString(value * 1550));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Metre") && spinner_right.equalsIgnoreCase("Sq.Cm")) {
            editText_right.setText(""+Double.toString(value * 10000));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Metre") && spinner_right.equalsIgnoreCase("Sq.mm")) {
            editText_right.setText(""+Double.toString(value * 1000000));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Metre") && spinner_right.equalsIgnoreCase("Guntha")) {
            editText_right.setText(""+Double.toString(value * 0.00988342));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Foot") && spinner_right.equalsIgnoreCase("Acre")) {
            editText_right.setText(""+Double.toString(value / 43560));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Foot") && spinner_right.equalsIgnoreCase("Hectare")) {
            editText_right.setText(""+Double.toString(value/107640));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Foot") && spinner_right.equalsIgnoreCase("Sq.Yard")) {
            editText_right.setText(""+Double.toString(value * 0.111111));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Foot") && spinner_right.equalsIgnoreCase("Sq.Foot")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Foot") && spinner_right.equalsIgnoreCase("Sq.Metre")) {
            editText_right.setText(""+Double.toString(value * 0.092903));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Foot") && spinner_right.equalsIgnoreCase("Sq.Inch")) {
            editText_right.setText(""+Double.toString(value * 144));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Foot") && spinner_right.equalsIgnoreCase("Sq.Cm")) {
            editText_right.setText(""+Double.toString(value * 929.03));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Foot") && spinner_right.equalsIgnoreCase("Sq.mm")) {
            editText_right.setText(""+Double.toString(value * 92903));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Foot") && spinner_right.equalsIgnoreCase("Guntha")) {
            editText_right.setText(""+Double.toString(value * 0.0009182));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Inch") && spinner_right.equalsIgnoreCase("Acre")) {
            editText_right.setText(""+Double.toString(value / 6272600));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Inch") && spinner_right.equalsIgnoreCase("Hectare")) {
            editText_right.setText(""+Double.toString(value/15500000));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Inch") && spinner_right.equalsIgnoreCase("Sq.Yard")) {
            editText_right.setText(""+Double.toString(value / 1296.0));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Inch") && spinner_right.equalsIgnoreCase("Sq.Foot")) {
            editText_right.setText(""+String.format("%.4f",value /144));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Inch") && spinner_right.equalsIgnoreCase("Sq.Inch")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Inch") && spinner_right.equalsIgnoreCase("Sq.Metre")) {
            editText_right.setText(""+Double.toString(value /1550));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Inch") && spinner_right.equalsIgnoreCase("Sq.Cm")) {
            editText_right.setText(""+Double.toString(value * 6.4516));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Inch") && spinner_right.equalsIgnoreCase("Sq.mm")) {
            editText_right.setText(""+Double.toString(value * 645.16));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Inch") && spinner_right.equalsIgnoreCase("Guntha")) {
            editText_right.setText(""+Double.toString(value / 156813.8623));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Cm") && spinner_right.equalsIgnoreCase("Acre")) {
            editText_right.setText(""+Double.toString(value * 2.4710538e-08));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Cm") && spinner_right.equalsIgnoreCase("Hectare")) {
            editText_right.setText(""+Double.toString(value/1e+08));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Cm") && spinner_right.equalsIgnoreCase("Sq.Yard")) {
            editText_right.setText(""+Double.toString(value * 0.00011960));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Cm") && spinner_right.equalsIgnoreCase("Sq.Metre")) {
            editText_right.setText(""+Double.toString(value / 10000));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Cm") && spinner_right.equalsIgnoreCase("Sq.Foot")) {
            editText_right.setText(""+String.format("%.5f",value /929.03));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Cm") && spinner_right.equalsIgnoreCase("Sq.Inch")) {
            editText_right.setText(""+String.format("%.4f",value/6.4516));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Cm") && spinner_right.equalsIgnoreCase("Sq.Cm")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Cm") && spinner_right.equalsIgnoreCase("Sq.mm")) {
            editText_right.setText(""+Double.toString(value*100));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.Cm") && spinner_right.equalsIgnoreCase("Guntha")) {
            editText_right.setText(""+Double.toString(value/1011700.3141));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.mm") && spinner_right.equalsIgnoreCase("Acre")) {
            editText_right.setText(""+Double.toString(value * 2.4710538e-10));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.mm") && spinner_right.equalsIgnoreCase("Hectare")) {
            editText_right.setText(""+Double.toString(value/1e+10));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.mm") && spinner_right.equalsIgnoreCase("Sq.Yard")) {
            editText_right.setText(""+Double.toString(value * 1.196e-6));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.mm") && spinner_right.equalsIgnoreCase("Sq.Metre")) {
            editText_right.setText(""+Double.toString(value /1000000));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.mm") && spinner_right.equalsIgnoreCase("Sq.Foot")) {
            editText_right.setText(""+Double.toString(value /92903));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.mm") && spinner_right.equalsIgnoreCase("Sq.Inch")) {
            editText_right.setText(""+String.format("%.4f",value/645.16));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.mm") && spinner_right.equalsIgnoreCase("Sq.Cm")) {
            editText_right.setText(""+Double.toString(value/100));
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.mm") && spinner_right.equalsIgnoreCase("Sq.mm")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if (spinner_left.equalsIgnoreCase("Sq.mm") && spinner_right.equalsIgnoreCase("Guntha")) {
            editText_right.setText(""+Double.toString(value /101170031.4133));
            return;
        }else if (spinner_left.equalsIgnoreCase("Guntha") && spinner_right.equalsIgnoreCase("Acre")) {
            editText_right.setText(""+String.format("%.4f",value * 0.02499455));
            return;
        }else if (spinner_left.equalsIgnoreCase("Guntha") && spinner_right.equalsIgnoreCase("Hectare")) {
            editText_right.setText(""+String.format("%.4f",value/98.8435306909));
            return;
        }else if (spinner_left.equalsIgnoreCase("Guntha") && spinner_right.equalsIgnoreCase("Sq.Yard")) {
            editText_right.setText(""+Double.toString(value*121));
            return;
        }else if (spinner_left.equalsIgnoreCase("Guntha") && spinner_right.equalsIgnoreCase("Sq.Metre")) {
            editText_right.setText(""+Double.toString(value /0.00988342));
            return;
        }else if (spinner_left.equalsIgnoreCase("Guntha") && spinner_right.equalsIgnoreCase("Sq.Foot")) {
            editText_right.setText(""+Double.toString(value /0.0009182));
            return;
        }else if (spinner_left.equalsIgnoreCase("Guntha") && spinner_right.equalsIgnoreCase("Sq.Inch")) {
            editText_right.setText(""+Double.toString(value*156813.8623));
            return;
        }else if (spinner_left.equalsIgnoreCase("Guntha") && spinner_right.equalsIgnoreCase("Sq.Cm")) {
            editText_right.setText(""+Double.toString(value*1011700.3141));
            return;
        }else if (spinner_left.equalsIgnoreCase("Guntha") && spinner_right.equalsIgnoreCase("Sq.mm")) {
            editText_right.setText(""+Double.toString(value *101170031.4133));
            return;
        }else if (spinner_left.equalsIgnoreCase("Guntha") && spinner_right.equalsIgnoreCase("Guntha")) {
            editText_right.setText(editText_left.getText().toString());
            return;
        }
    }
}

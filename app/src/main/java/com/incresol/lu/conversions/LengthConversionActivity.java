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
import android.view.animation.AlphaAnimation;
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
 * Created by Incresol-078 on 28-06-2016.
 */
public class LengthConversionActivity extends android.app.Fragment implements View.OnClickListener {

    View myView;
    EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right;
    String spinnerText_left, spinnerText_right;
    public static double meterfeet = 0.3048;
    Button button_convert;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    static LinearLayout layout_main_lengthActivity,layout_timer,layout_slider;
    ScrollView scrollView_length;

    public int currentimageindex=0;
    ImageView imageslider;
    ProgressBar mProgressBar;
    TextView set_timer;
    int TimeCounter=30;

    int[] IMAGE_IDS = {R.drawable.icon_1_length, R.drawable.icon_2_length, R.drawable.icon_3_length};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView = inflater.inflate(R.layout.activity_length_conversion, container, false);
        ((MainActivity) getActivity())
                .setActionBarTitle("Length Conversion");
        editText_left = (EditText) myView.findViewById(R.id.editText_left);
        editText_right = (EditText) myView.findViewById(R.id.editText_right);
        spinner_left = (Spinner) myView.findViewById(R.id.spinner_left);
        spinner_right = (Spinner) myView.findViewById(R.id.spinner_right);
        button_convert=(Button)myView.findViewById(R.id.convert);
        layout_main_lengthActivity=(LinearLayout)myView.findViewById(R.id.layout_main);
        layout_timer=(LinearLayout)myView.findViewById(R.id.layout_timer);
        layout_slider=(LinearLayout)myView.findViewById(R.id.layout_slider);
        imageslider = (ImageView)myView.findViewById(R.id.imageslider);
        scrollView_length=(ScrollView) myView.findViewById(R.id.scrollView_length);
        MainActivity.home=0;

        if(ThemesActivity.imageId != null){
            MainActivity.themeColorChange(ThemesActivity.imageId,"length",scrollView_length);
        }
        else{
            MainActivity.themeColorChange("blue","length",scrollView_length);
        }

        ArrayAdapter<CharSequence> arrayAdapter_left = ArrayAdapter.createFromResource(getActivity(), R.array.length_left_arrays, android.R.layout.simple_spinner_item);
        arrayAdapter_left.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_left.setAdapter(arrayAdapter_left);
        spinner_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    editText_left.setText("");
                }
                editText_right.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> arrayAdapter_right = ArrayAdapter.createFromResource(getActivity(), R.array.length_right_arrays,android.R.layout.simple_spinner_item );
        arrayAdapter_right.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_right.setAdapter(arrayAdapter_right);
        spinner_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                        String val = editText_left.getText().toString();
                        if(val.contains("E") || val.contains("-")) {
                            editText_left.setText("");
                        }else {
                            Double value=Double.parseDouble(val);
                            calculationsPart(spinnerText_left, spinnerText_right, value);
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
        set_timer=(TextView) myView.findViewById(R.id.set_timer);
        mProgressBar=(ProgressBar)myView.findViewById(R.id.progressBar);
        ((MainActivity) getActivity()).timerOn(IMAGE_IDS,layout_slider,layout_timer,imageslider,set_timer,mProgressBar);
        return myView;
    }

    @Override
    public void onClick(View v) {
        v.startAnimation(buttonClick);
        switch (v.getId()) {

            case R.id.convert:
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(myView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                spinnerText_left = spinner_left.getSelectedItem().toString();
                spinnerText_right = spinner_right.getSelectedItem().toString();

                if(spinnerText_left.equalsIgnoreCase("select")&&spinnerText_right.equalsIgnoreCase("select")){
                    ((MainActivity) getActivity()).toastMessage("Please select the metric units");
                }else if(spinnerText_left.equalsIgnoreCase("select")){
                    ((MainActivity) getActivity()).toastMessage("Please select any metric unit");
                }else if(spinnerText_right.equalsIgnoreCase("select")){
                    ((MainActivity) getActivity()).toastMessage("Please select any metric unit");
                }
                else if (editText_left.getText().length() != 0) {

                    String val = editText_left.getText().toString();
                    if(val.contains("E") || val.contains("-")) {
                        editText_left.setText("");
                    }else {
                        Double value=Double.parseDouble(val);
                        calculationsPart(spinnerText_left, spinnerText_right, value);
                    }
                } else {
                    ((MainActivity) getActivity()).toastMessage("Please enter any value");
                }

                break;
        }
    }



    public void calculationsPart(String spinner_left, String spinner_right,double value) {
        if(spinner_left.equalsIgnoreCase("Meter") && spinner_right.equalsIgnoreCase("Meter")){
                editText_right.setText(editText_left.getText().toString());
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Meter") && spinner_right.equalsIgnoreCase("Foot")){
                editText_right.setText(""+String.format("%.2f",value /meterfeet));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Meter") && spinner_right.equalsIgnoreCase("Inch")){
                editText_right.setText(""+String.format("%.2f",value * 39.370));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Meter") && spinner_right.equalsIgnoreCase("Centimetre")){
            editText_right.setText(""+String.format("%.2f",value / 0.0100));
            return;
        }else if(spinner_left.equalsIgnoreCase("Meter") && spinner_right.equalsIgnoreCase("Millimetre")){
            editText_right.setText(""+String.format("%.3f",value / 0.001));
            return;
        }else if(spinner_left.equalsIgnoreCase("Meter") && spinner_right.equalsIgnoreCase("Kilometre")){
            editText_right.setText(""+String.format("%.3f",value / 1000));
            return;
        }else if(spinner_left.equalsIgnoreCase("Meter") && spinner_right.equalsIgnoreCase("Mile")){
            editText_right.setText(""+String.format("%.3f",value / 1609.34));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Foot") && spinner_right.equalsIgnoreCase("Meter")){
           editText_right.setText(""+String.format("%.2f",value * meterfeet));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Foot") && spinner_right.equalsIgnoreCase("Foot")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Foot") && spinner_right.equalsIgnoreCase("Inch")){
           editText_right.setText(""+String.format("%.2f",value * 12));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Foot") && spinner_right.equalsIgnoreCase("Centimetre")){
            editText_right.setText(""+String.format("%.2f",value /0.0328));
            return;
        }else if(spinner_left.equalsIgnoreCase("Foot") && spinner_right.equalsIgnoreCase("Millimetre")){
            editText_right.setText(""+Double.toString(value /0.0032808));
            return;
        }else if(spinner_left.equalsIgnoreCase("Foot") && spinner_right.equalsIgnoreCase("Kilometre")){
            editText_right.setText(""+Double.toString(value /3280.8));
            return;
        }else if(spinner_left.equalsIgnoreCase("Foot") && spinner_right.equalsIgnoreCase("Mile")){
            editText_right.setText(""+Double.toString(value /5280));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Inch") && spinner_right.equalsIgnoreCase("Meter")){
            editText_right.setText(""+String.format("%.2f",value / 39.370));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Inch") && spinner_right.equalsIgnoreCase("Foot")){
            editText_right.setText(""+String.format("%.2f",value * 0.0833));
            return;
        } else if(spinner_left.equalsIgnoreCase("Inch") && spinner_right.equalsIgnoreCase("Inch")){
            editText_right.setText(editText_left.getText().toString());
            return;
        } else if(spinner_left.equalsIgnoreCase("Inch") && spinner_right.equalsIgnoreCase("Centimetre")){
            editText_right.setText(""+String.format("%.2f",value * 2.54));
            return;
        } else if(spinner_left.equalsIgnoreCase("Inch") && spinner_right.equalsIgnoreCase("Millimetre")){
            editText_right.setText(""+Double.toString(value /0.039370));
            return;
        }else if(spinner_left.equalsIgnoreCase("Inch") && spinner_right.equalsIgnoreCase("Kilometre")){
           editText_right.setText(""+Double.toString(value /39370));
            return;
        }else if(spinner_left.equalsIgnoreCase("Inch") && spinner_right.equalsIgnoreCase("Mile")){
            editText_right.setText(""+Double.toString(value /63360));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Centimetre") && spinner_right.equalsIgnoreCase("Meter")){
            editText_right.setText(""+String.format("%.2f",value / 100));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Centimetre") && spinner_right.equalsIgnoreCase("Foot")){
            editText_right.setText(""+String.format("%.2f",value * 0.0328));
            return;
        } else if(spinner_left.equalsIgnoreCase("Centimetre") && spinner_right.equalsIgnoreCase("Inch")){
            editText_right.setText(""+String.format("%.2f",value / 2.54));
            return;
        } else if(spinner_left.equalsIgnoreCase("Centimetre") && spinner_right.equalsIgnoreCase("Centimetre")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Centimetre") && spinner_right.equalsIgnoreCase("Millimetre")){
            editText_right.setText(""+Double.toString(value / 0.10000));
            return;
        }else if(spinner_left.equalsIgnoreCase("Centimetre") && spinner_right.equalsIgnoreCase("Kilometre")){
            editText_right.setText(""+Double.toString(value / 100000));
            return;
        }else if(spinner_left.equalsIgnoreCase("Centimetre") && spinner_right.equalsIgnoreCase("Mile")){
            editText_right.setText(""+Double.toString(value / 160934));
            return;
        }
        else if(spinner_left.equalsIgnoreCase("Millimetre") && spinner_right.equalsIgnoreCase("Meter")){
            editText_right.setText(""+Double.toString(value /1000));
            return;
        }else if(spinner_left.equalsIgnoreCase("Millimetre") && spinner_right.equalsIgnoreCase("Foot")){
            editText_right.setText(""+Double.toString(value * 0.0032808));
            return;
        }else if(spinner_left.equalsIgnoreCase("Millimetre") && spinner_right.equalsIgnoreCase("Inch")){
            editText_right.setText(""+String.format("%.3f",value * 0.039370));
            return;
        }else if(spinner_left.equalsIgnoreCase("Millimetre") && spinner_right.equalsIgnoreCase("Centimetre")){
            editText_right.setText(""+Double.toString(value / 10.000));
            return;
        }else if(spinner_left.equalsIgnoreCase("Millimetre") && spinner_right.equalsIgnoreCase("Millimetre")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Millimetre") && spinner_right.equalsIgnoreCase("Kilometre")){
            editText_right.setText(""+Double.toString(value /1000000));
            return;
        }else if(spinner_left.equalsIgnoreCase("Millimetre") && spinner_right.equalsIgnoreCase("Mile")){
            editText_right.setText(""+Double.toString(value *6.2137e-7));
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilometre") && spinner_right.equalsIgnoreCase("Meter")){
            editText_right.setText(""+Double.toString(value /0.001));
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilometre") && spinner_right.equalsIgnoreCase("Foot")){
            editText_right.setText(""+Double.toString(value * 3280.8));
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilometre") && spinner_right.equalsIgnoreCase("Inch")){
            editText_right.setText(""+Double.toString(value * 39370));
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilometre") && spinner_right.equalsIgnoreCase("Centimetre")){
            editText_right.setText(""+Double.toString(value * 100000));
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilometre") && spinner_right.equalsIgnoreCase("Millimetre")){
            editText_right.setText(""+Double.toString(value / 1e-06));
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilometre") && spinner_right.equalsIgnoreCase("Kilometre")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }else if(spinner_left.equalsIgnoreCase("Kilometre") && spinner_right.equalsIgnoreCase("Mile")){
            editText_right.setText(""+Double.toString(value * 0.621371));
            return;
        }else if(spinner_left.equalsIgnoreCase("Mile") && spinner_right.equalsIgnoreCase("Kilometre")){
            editText_right.setText(""+Double.toString(value * 1.60934));
            return;
        }else if(spinner_left.equalsIgnoreCase("Mile") && spinner_right.equalsIgnoreCase("Meter")){
            editText_right.setText(""+Double.toString(value * 1609.34));
            return;
        }else if(spinner_left.equalsIgnoreCase("Mile") && spinner_right.equalsIgnoreCase("Foot")){
            editText_right.setText(""+Double.toString(value * 5280));
            return;
        }else if(spinner_left.equalsIgnoreCase("Mile") && spinner_right.equalsIgnoreCase("Inch")){
            editText_right.setText(""+Double.toString(value * 63360));
            return;
        }else if(spinner_left.equalsIgnoreCase("Mile") && spinner_right.equalsIgnoreCase("Centimetre")){
            editText_right.setText(""+Double.toString(value * 160934));
            return;
        }else if(spinner_left.equalsIgnoreCase("Mile") && spinner_right.equalsIgnoreCase("Millimetre")){
            editText_right.setText(""+Double.toString(value * 1.609e+6));
            return;
        }else if(spinner_left.equalsIgnoreCase("Mile") && spinner_right.equalsIgnoreCase("Mile")){
            editText_right.setText(editText_left.getText().toString());
            return;
        }
    }
}

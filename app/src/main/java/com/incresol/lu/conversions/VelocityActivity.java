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
 * Created by Incresol-078 on 08-07-2016.
 */
public class VelocityActivity extends android.app.Fragment implements View.OnClickListener {

    View velocityView;
    EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right;
    String spinnerText_left, spinnerText_right, value;
    double value_converted;
    int pressed = 0;

    LinearLayout layout_planet_info, layout_sun,
            layout_mercury, layout_venus,
            layout_earth, layout_mars,
            layout_jupiter, layout_saturn,
            layout_uranus, layout_neptune;
    static LinearLayout layout_main_velocityActivity;
    Button button_sun, button_mercury, button_venus,
            button_earth, button_mars, button_jupiter,
            button_saturn, button_uranus, button_neptune,button_convert;
    TextView textView_sun_value,
            textView_mercury_value,
            textView_venus_value,
            textView_earth_value,
            textView_mars_value,
            textView_jupiter_value,
            textView_saturn_value,
            textView_uranus_value,
            textView_neptune_value;
    ScrollView scrollView_velocity;

    //timer and slider <code></code>
    public int currentimageindex=0;
    ImageView imageslider;
    ProgressBar mProgressBar;
    TextView set_timer;
    int TimeCounter=30;
    static LinearLayout layout_timer,layout_slider;
    int[] IMAGE_IDS = {R.drawable.icon_1_astro, R.drawable.icon_2_astro};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        velocityView = inflater.inflate(R.layout.activity_velocity, container, false);
        layout_main_velocityActivity=(LinearLayout) velocityView.findViewById(R.id.layout_main);
        scrollView_velocity=(ScrollView)velocityView.findViewById(R.id.scrollView_velocity);
        if(ThemesActivity.imageId != null){
           MainActivity.themeColorChange(ThemesActivity.imageId,"astro",scrollView_velocity);
        }else{
            MainActivity.themeColorChange("blue","astro",scrollView_velocity);
        }
        ((MainActivity) getActivity()).setActionBarTitle("Astronomical Unit Conversion");
        MainActivity.home=0;
        editText_left = (EditText) velocityView.findViewById(R.id.editText_left);
        editText_right = (EditText) velocityView.findViewById(R.id.editText_right);
        spinner_right = (Spinner) velocityView.findViewById(R.id.spinner_right);
        button_convert=(Button)velocityView.findViewById(R.id.convert);

        layout_planet_info = (LinearLayout) velocityView.findViewById(R.id.layout_planet_info);
        layout_sun = (LinearLayout) velocityView.findViewById(R.id.layout_sun);
        layout_mercury = (LinearLayout) velocityView.findViewById(R.id.layout_mercury);
        layout_venus = (LinearLayout) velocityView.findViewById(R.id.layout_venus);
        layout_earth = (LinearLayout) velocityView.findViewById(R.id.layout_earth);
        layout_mars = (LinearLayout) velocityView.findViewById(R.id.layout_mars);
        layout_jupiter = (LinearLayout) velocityView.findViewById(R.id.layout_jupiter);
        layout_saturn = (LinearLayout) velocityView.findViewById(R.id.layout_saturn);
        layout_uranus = (LinearLayout) velocityView.findViewById(R.id.layout_uranus);
        layout_neptune = (LinearLayout) velocityView.findViewById(R.id.layout_neptune);

        button_sun = (Button) velocityView.findViewById(R.id.button_sun);
        button_mercury = (Button) velocityView.findViewById(R.id.button_mercury);
        button_venus = (Button) velocityView.findViewById(R.id.button_venus);
        button_earth = (Button) velocityView.findViewById(R.id.button_earth);
        button_mars = (Button) velocityView.findViewById(R.id.button_mars);
        button_jupiter = (Button) velocityView.findViewById(R.id.button_jupiter);
        button_saturn = (Button) velocityView.findViewById(R.id.button_saturn);
        button_uranus = (Button) velocityView.findViewById(R.id.button_uranus);
        button_neptune = (Button) velocityView.findViewById(R.id.button_neptune);

        textView_sun_value = (TextView) velocityView.findViewById(R.id.textView_sun_value);
        textView_mercury_value = (TextView) velocityView.findViewById(R.id.textView_mercury_value);
        textView_venus_value = (TextView) velocityView.findViewById(R.id.textView_venus_value);
        textView_earth_value = (TextView) velocityView.findViewById(R.id.textView_earth_value);
        textView_mars_value = (TextView) velocityView.findViewById(R.id.textView_mars_value);
        textView_jupiter_value = (TextView) velocityView.findViewById(R.id.textView_jupiter_value);
        textView_saturn_value = (TextView) velocityView.findViewById(R.id.textView_saturn_value);
        textView_uranus_value = (TextView) velocityView.findViewById(R.id.textView_uranus_value);
        textView_neptune_value = (TextView) velocityView.findViewById(R.id.textView_neptune_value);

        //code for timer and image slider
        layout_timer=(LinearLayout)velocityView.findViewById(R.id.layout_timer);
        layout_slider=(LinearLayout)velocityView.findViewById(R.id.layout_slider);
        imageslider = (ImageView)velocityView.findViewById(R.id.imageslider);
        set_timer=(TextView) velocityView.findViewById(R.id.set_timer);
        mProgressBar=(ProgressBar)velocityView.findViewById(R.id.progressBar);


        ArrayAdapter<CharSequence> arrayAdapter_right = ArrayAdapter.createFromResource(getActivity(), R.array.auconversion_right_arrays, android.R.layout.simple_spinner_item);
        arrayAdapter_right.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_right.setAdapter(arrayAdapter_right);
        spinner_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(velocityView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
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
                System.out.println("edttextleft -> " + editText_left.getText().toString());
                if (editText_left.hasFocus()) {
                    if (editText_left.getText().toString().length() != 0) {
                        spinnerText_right = spinner_right.getSelectedItem().toString();
                        String val = editText_left.getText().toString();

                        Double value = Double.parseDouble(val);
                        auCalculation(spinnerText_right, value);
                    } else {
                        editText_right.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button_sun.setOnClickListener(this);
        button_mercury.setOnClickListener(this);
        button_venus.setOnClickListener(this);
        button_earth.setOnClickListener(this);
        button_mars.setOnClickListener(this);
        button_jupiter.setOnClickListener(this);
        button_saturn.setOnClickListener(this);
        button_uranus.setOnClickListener(this);
        button_neptune.setOnClickListener(this);
        button_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(velocityView.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                spinnerText_right = spinner_right.getSelectedItem().toString();

                if(spinnerText_right.equalsIgnoreCase("select")){
                    ((MainActivity)getActivity()).toastMessage("Please select a metric unit");
                }else if (editText_left.getText().length() != 0) {
                    String val = editText_left.getText().toString();

                    Double value = Double.parseDouble(val);
                    auCalculation(spinnerText_right, value);
                } else {
                    ((MainActivity)getActivity()).toastMessage("Please enter an au value");
                }
            }
        });

        ((MainActivity) getActivity()).timerOn(IMAGE_IDS,layout_slider,layout_timer,imageslider,set_timer,mProgressBar);
        return velocityView;
    }


    public void auCalculation(String spinnerText_right, double value) {
        if (spinnerText_right.equalsIgnoreCase("km")) {
            editText_right.setText("" + Double.toString(value * 1.496e+8));
            return;
        } else if (spinnerText_right.equalsIgnoreCase("miles")) {
            editText_right.setText("" + Double.toString(value * 9.296e+7));
            return;
        }
    }

    public void changeColor_Content(int value){
        if (layout_planet_info.getVisibility() != View.VISIBLE) {
            layout_planet_info.setVisibility(View.VISIBLE);
        }
        switch (value){
            case 1:
                button_sun.setBackgroundResource(R.drawable.button_planet);
                layout_sun.setVisibility(View.VISIBLE);
                break;
            case 2:
                button_mercury.setBackgroundResource(R.drawable.button_planet);
                layout_mercury.setVisibility(View.VISIBLE);
                break;
            case 3:
                button_venus.setBackgroundResource(R.drawable.button_planet);
                layout_venus.setVisibility(View.VISIBLE);
                break;
            case 4:
                button_earth.setBackgroundResource(R.drawable.button_planet);
                layout_earth.setVisibility(View.VISIBLE);
                break;
            case 5:
                button_mars.setBackgroundResource(R.drawable.button_planet);
                layout_mars.setVisibility(View.VISIBLE);
                break;
            case 6:
                button_jupiter.setBackgroundResource(R.drawable.button_planet);
                layout_jupiter.setVisibility(View.VISIBLE);
                break;
            case 7:
                button_saturn.setBackgroundResource(R.drawable.button_planet);
                layout_saturn.setVisibility(View.VISIBLE);
                break;
            case 8:
                button_uranus.setBackgroundResource(R.drawable.button_planet);
                layout_uranus.setVisibility(View.VISIBLE);
                break;
            case 9:
                button_neptune.setBackgroundResource(R.drawable.button_planet);
                layout_neptune.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        if (pressed == 0) {
            layout_planet_info.setVisibility(View.VISIBLE);
        }
        if(pressed != 0 ){
            changeColor_Content(pressed);
        }
        switch (v.getId()) {
            case R.id.button_sun:
                if (pressed == 1) {
                    pressed = 0;
                    layout_planet_info.setVisibility(View.GONE);
                    button_sun.setBackgroundResource(R.drawable.button_planet);
                }else{
                pressed = 1;
                button_sun.setBackgroundResource(R.drawable.button_planet_secondary);
                layout_sun.setVisibility(View.GONE);
                textView_mercury_value.setText(R.string.suntomercury);
                textView_venus_value.setText(R.string.suntovenus);
                textView_earth_value.setText(R.string.suntoearth);
                textView_mars_value.setText(R.string.suntomars);
                textView_jupiter_value.setText(R.string.suntojupiter);
                textView_saturn_value.setText(R.string.suntosaturn);
                textView_uranus_value.setText(R.string.suntouranus);
                textView_neptune_value.setText(R.string.suntoneptune);
        }
                break;

            case R.id.button_mercury:
                if(pressed == 2){
                    pressed = 0;
                    layout_planet_info.setVisibility(View.GONE);
                    button_mercury.setBackgroundResource(R.drawable.button_planet);
                }else{
                pressed = 2;
                button_mercury.setBackgroundResource(R.drawable.button_planet_secondary);
                layout_mercury.setVisibility(View.GONE);
                textView_sun_value.setText(R.string.suntomercury);
                textView_venus_value.setText(R.string.mercurytovenus);
                textView_earth_value.setText(R.string.mercurytoearth);
                textView_mars_value.setText(R.string.mercurytomars);
                textView_jupiter_value.setText(R.string.mercurytojupiter);
                textView_saturn_value.setText(R.string.mercurytosaturn);
                textView_uranus_value.setText(R.string.mercurytouranus);
                textView_neptune_value.setText(R.string.mercurytoneptune);
                }
                break;


            case R.id.button_venus:
                if(pressed == 3){
                    pressed = 0;
                    layout_planet_info.setVisibility(View.GONE);
                    button_venus.setBackgroundResource(R.drawable.button_planet);
                }else {
                    pressed = 3;
                    button_venus.setBackgroundResource(R.drawable.button_planet_secondary);
                    layout_venus.setVisibility(View.GONE);
                    textView_sun_value.setText(R.string.suntovenus);
                    textView_mercury_value.setText(R.string.mercurytovenus);
                    textView_earth_value.setText(R.string.venustoearth);
                    textView_mars_value.setText(R.string.venustomars);
                    textView_jupiter_value.setText(R.string.venustojupiter);
                    textView_saturn_value.setText(R.string.venustosaturn);
                    textView_uranus_value.setText(R.string.venustouranus);
                    textView_neptune_value.setText(R.string.venustoneptune);

                }break;


            case R.id.button_earth:
                if(pressed == 4){
                    pressed = 0;
                    layout_planet_info.setVisibility(View.GONE);
                    button_earth.setBackgroundResource(R.drawable.button_planet);
                }else {
                    pressed = 4;
                    button_earth.setBackgroundResource(R.drawable.button_planet_secondary);
                    layout_earth.setVisibility(View.GONE);
                    textView_sun_value.setText(R.string.suntoearth);
                    textView_mercury_value.setText(R.string.mercurytoearth);
                    textView_venus_value.setText(R.string.venustoearth);
                    textView_mars_value.setText(R.string.earthtomars);
                    textView_jupiter_value.setText(R.string.earthtojupiter);
                    textView_saturn_value.setText(R.string.earthtosaturn);
                    textView_uranus_value.setText(R.string.earthtouranus);
                    textView_neptune_value.setText(R.string.earthtoneptune);
                }break;

            case R.id.button_mars:
                if(pressed == 5){
                    pressed = 0;
                    layout_planet_info.setVisibility(View.GONE);
                    button_mars.setBackgroundResource(R.drawable.button_planet);
                }else {
                    pressed = 5;
                    button_mars.setBackgroundResource(R.drawable.button_planet_secondary);
                    layout_mars.setVisibility(View.GONE);
                    textView_sun_value.setText(R.string.suntomars);
                    textView_mercury_value.setText(R.string.mercurytomars);
                    textView_venus_value.setText(R.string.venustomars);
                    textView_earth_value.setText(R.string.earthtomars);
                    textView_jupiter_value.setText(R.string.marstojupiter);
                    textView_saturn_value.setText(R.string.marstosaturn);
                    textView_uranus_value.setText(R.string.marstouranus);
                    textView_neptune_value.setText(R.string.marstoneptune);
                }break;

            case R.id.button_jupiter:
                if(pressed == 6){
                    pressed = 0;
                    layout_planet_info.setVisibility(View.GONE);
                    button_mars.setBackgroundResource(R.drawable.button_planet);
                }else {
                    pressed = 6;
                    button_jupiter.setBackgroundResource(R.drawable.button_planet_secondary);
                    layout_jupiter.setVisibility(View.GONE);
                    textView_sun_value.setText(R.string.suntojupiter);
                    textView_mercury_value.setText(R.string.mercurytojupiter);
                    textView_venus_value.setText(R.string.venustojupiter);
                    textView_earth_value.setText(R.string.earthtojupiter);
                    textView_mars_value.setText(R.string.marstojupiter);
                    textView_saturn_value.setText(R.string.jupitertosaturn);
                    textView_uranus_value.setText(R.string.jupitertouranus);
                    textView_neptune_value.setText(R.string.jupitertoneptune);
                }
                    break;

            case R.id.button_saturn:
                if(pressed == 7){
                    pressed = 0;
                    layout_planet_info.setVisibility(View.GONE);
                    button_mars.setBackgroundResource(R.drawable.button_planet);
                }else {
                    pressed = 7;
                    button_saturn.setBackgroundResource(R.drawable.button_planet_secondary);
                    layout_saturn.setVisibility(View.GONE);
                    textView_sun_value.setText(R.string.suntosaturn);
                    textView_mercury_value.setText(R.string.mercurytosaturn);
                    textView_venus_value.setText(R.string.venustosaturn);
                    textView_earth_value.setText(R.string.earthtosaturn);
                    textView_mars_value.setText(R.string.marstosaturn);
                    textView_jupiter_value.setText(R.string.jupitertosaturn);
                    textView_uranus_value.setText(R.string.saturntouranus);
                    textView_neptune_value.setText(R.string.saturntoneptune);
                }
                    break;


            case R.id.button_uranus:
                if(pressed == 8){
                    pressed = 0;
                    layout_planet_info.setVisibility(View.GONE);
                    button_mars.setBackgroundResource(R.drawable.button_planet);
                }else {
                    pressed = 8;
                    button_uranus.setBackgroundResource(R.drawable.button_planet_secondary);
                    layout_uranus.setVisibility(View.GONE);
                    textView_sun_value.setText(R.string.suntouranus);
                    textView_mercury_value.setText(R.string.mercurytouranus);
                    textView_venus_value.setText(R.string.venustouranus);
                    textView_earth_value.setText(R.string.earthtouranus);
                    textView_mars_value.setText(R.string.marstouranus);
                    textView_jupiter_value.setText(R.string.jupitertouranus);
                    textView_saturn_value.setText(R.string.saturntouranus);
                    textView_neptune_value.setText(R.string.uranustoneptune);
                }
                break;

            case R.id.button_neptune:
                if(pressed == 9){
                    pressed = 0;
                    layout_planet_info.setVisibility(View.GONE);
                    button_mars.setBackgroundResource(R.drawable.button_planet);
                }else {
                    pressed = 9;
                    button_neptune.setBackgroundResource(R.drawable.button_planet_secondary);
                    layout_neptune.setVisibility(View.GONE);
                    textView_sun_value.setText(R.string.suntoneptune);
                    textView_mercury_value.setText(R.string.mercurytoneptune);
                    textView_venus_value.setText(R.string.venustoneptune);
                    textView_earth_value.setText(R.string.earthtoneptune);
                    textView_mars_value.setText(R.string.marstoneptune);
                    textView_jupiter_value.setText(R.string.jupitertoneptune);
                    textView_saturn_value.setText(R.string.saturntoneptune);
                    textView_uranus_value.setText(R.string.uranustoneptune);
                }
                break;

        }
    }
}

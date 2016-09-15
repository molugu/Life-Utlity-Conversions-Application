package com.incresol.lu.conversions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Incresol-078 on 02-08-2016.
 */
public class PlanetoryDistancesActivity extends android.app.Fragment implements View.OnClickListener {

    View planetView;
    EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right;
    String spinnerText_left, spinnerText_right;
    Button button_convert;

    TextView name_planet_01, name_planet_02,
            planets_distance, age_planet_01, age_planet_02,
            mass_planet_01, mass_planet_02,
            weight_planet_01, weight_planet_02,
            made_planet_01, made_planet_02,
            sat_planet_01, sat_planet_02;

    LinearLayout layout_planetary_details,main_layout_planetary;
    ScrollView scrollView_planets;
    GifImageView planetary_model_gif;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    int portrait;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        planetView = inflater.inflate(R.layout.activity_planetory_distances, container, false);
        MainActivity.home=0;
        main_layout_planetary=(LinearLayout)planetView.findViewById(R.id.main_layout_planetary);
        scrollView_planets=(ScrollView)  planetView.findViewById(R.id.scrollView_planet);
        if(ThemesActivity.imageId != null){
            MainActivity.themeColorChange(ThemesActivity.imageId,"astro",scrollView_planets);
        }else{
            MainActivity.themeColorChange("blue","astro",scrollView_planets);
        }

        ((MainActivity) getActivity())
                .setActionBarTitle("Planetary Distances");

        editText_left = (EditText) planetView.findViewById(R.id.editText_left);
        editText_right = (EditText) planetView.findViewById(R.id.editText_right);
        spinner_left = (Spinner) planetView.findViewById(R.id.spinner_left);
        spinner_right = (Spinner) planetView.findViewById(R.id.spinner_right);
        button_convert = (Button) planetView.findViewById(R.id.convert);

        name_planet_01 = (TextView) planetView.findViewById(R.id.planet01_name);
        name_planet_02 = (TextView) planetView.findViewById(R.id.planet02_name);
        age_planet_01 = (TextView) planetView.findViewById(R.id.age_planet_01);
        age_planet_02 = (TextView) planetView.findViewById(R.id.age_planet_02);
        mass_planet_01 = (TextView) planetView.findViewById(R.id.mass_planet_01);
        mass_planet_02 = (TextView) planetView.findViewById(R.id.mass_planet_02);
        weight_planet_01 = (TextView) planetView.findViewById(R.id.weight_planet_01);
        weight_planet_02 = (TextView) planetView.findViewById(R.id.weight_planet_02);
        made_planet_01 = (TextView) planetView.findViewById(R.id.made_planet_01);
        made_planet_02 = (TextView) planetView.findViewById(R.id.made_planet_02);
        sat_planet_01 = (TextView) planetView.findViewById(R.id.sat_planet_01);
        sat_planet_02 = (TextView) planetView.findViewById(R.id.sat_planet_02);
        planets_distance = (TextView) planetView.findViewById(R.id.planets_distance);
        layout_planetary_details = (LinearLayout) planetView.findViewById(R.id.layout_planetary_details);
        layout_planetary_details.setVisibility(View.GONE);
        planetary_model_gif=(GifImageView) planetView.findViewById(R.id.planetary_model_gif);


        ArrayAdapter<CharSequence> arrayAdapter_left = ArrayAdapter.createFromResource(getActivity(), R.array.planets_left_arrays, android.R.layout.simple_spinner_item);
        arrayAdapter_left.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_left.setAdapter(arrayAdapter_left);
        spinner_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                layout_planetary_details.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> arrayAdapter_right = ArrayAdapter.createFromResource(getActivity(), R.array.planets_right_arrays, android.R.layout.simple_spinner_item);
        arrayAdapter_right.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_right.setAdapter(arrayAdapter_right);
        spinner_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                layout_planetary_details.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_convert.setOnClickListener(this);
        planetary_model_gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_dialog=new Intent(getActivity(),Custom_Image_Dialog.class);
                startActivity(intent_dialog);

            }
        });

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        return planetView;
    }


    /*Dialog dialog;

    private void showDialog() {
        // custom dialog
        planetary_model_gif.setVisibility(View.INVISIBLE);
       // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Log.i("inside ","show dialog");
       // dialog = new Dialog(getActivity());
         dialog=new Dialog(getActivity(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
         dialog.setContentView(R.layout.custom_dialog);
        // set the custom dialog components - text, image and button
        ImageButton close = (ImageButton) dialog.findViewById(R.id.btnClose);

        // Close Button
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planetary_model_gif.setVisibility(View.VISIBLE);
                dialog.dismiss();
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE&&portrait==0)
                {
                    Log.i("inside if config","===----------------");
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }*/




    public void setText(int location, int side,String spinnerText_right) {
        switch (side) {
            case 1:
                switch (location) {
                    case 1:
                        name_planet_01.setText(R.string.sun_name);
                        age_planet_01.setText(R.string.sun_age);
                        mass_planet_01.setText(R.string.sun_mass);
                        weight_planet_01.setText(R.string.sun_weight);
                        made_planet_01.setText(R.string.sun_made);
                        sat_planet_01.setText(R.string.sun_sat);

                        switch (spinnerText_right){
                            case "Mercury":planets_distance.setText(R.string.suntomercury);
                                           break;
                            case "Venus":planets_distance.setText(R.string.suntovenus);
                                         break;
                            case "Earth":planets_distance.setText(R.string.suntoearth);
                                         break;
                            case "Mars":planets_distance.setText(R.string.suntomars);
                                        break;
                            case "Jupiter":planets_distance.setText(R.string.suntojupiter);
                                break;
                            case "Saturn":planets_distance.setText(R.string.suntosaturn);
                                break;
                            case "Neptune":planets_distance.setText(R.string.suntoneptune);
                                break;
                            case "Uranus":planets_distance.setText(R.string.suntouranus);
                                break;
                        }

                        break;
                    case 2:
                        name_planet_01.setText(R.string.mercury_name);
                        age_planet_01.setText(R.string.mercury_age);
                        mass_planet_01.setText(R.string.mercury_mass);
                        weight_planet_01.setText(R.string.mercury_weight);
                        made_planet_01.setText(R.string.mercury_made);
                        sat_planet_01.setText(R.string.mercury_sat);

                        switch (spinnerText_right){
                            case "Sun":planets_distance.setText(R.string.suntomercury);
                                break;
                            case "Venus":planets_distance.setText(R.string.mercurytovenus);
                                break;
                            case "Earth":planets_distance.setText(R.string.mercurytoearth);
                                break;
                            case "Mars":planets_distance.setText(R.string.mercurytomars);
                                break;
                            case "Jupiter":planets_distance.setText(R.string.mercurytojupiter);
                                break;
                            case "Saturn":planets_distance.setText(R.string.mercurytosaturn);
                                break;
                            case "Neptune":planets_distance.setText(R.string.mercurytoneptune);
                                break;
                            case "Uranus":planets_distance.setText(R.string.mercurytouranus);
                                break;
                        }


                        break;
                    case 3:
                        name_planet_01.setText(R.string.venus_name);
                        age_planet_01.setText(R.string.venus_age);
                        mass_planet_01.setText(R.string.venus_mass);
                        weight_planet_01.setText(R.string.venus_weight);
                        made_planet_01.setText(R.string.venus_made);
                        sat_planet_01.setText(R.string.venus_sat);

                        switch (spinnerText_right){
                            case "Sun":planets_distance.setText(R.string.suntovenus);
                                break;
                            case "Mercury":planets_distance.setText(R.string.mercurytovenus);
                                break;
                            case "Earth":planets_distance.setText(R.string.venustoearth);
                                break;
                            case "Mars":planets_distance.setText(R.string.venustomars);
                                break;
                            case "Jupiter":planets_distance.setText(R.string.venustojupiter);
                                break;
                            case "Saturn":planets_distance.setText(R.string.venustosaturn);
                                break;
                            case "Neptune":planets_distance.setText(R.string.venustoneptune);
                                break;
                            case "Uranus":planets_distance.setText(R.string.venustouranus);
                                break;
                        }

                        break;
                    case 4:
                        name_planet_01.setText(R.string.earth_name);
                        age_planet_01.setText(R.string.earth_age);
                        mass_planet_01.setText(R.string.earth_mass);
                        weight_planet_01.setText(R.string.earth_weight);
                        made_planet_01.setText(R.string.earth_made);
                        sat_planet_01.setText(R.string.earth_sat);

                        switch (spinnerText_right){
                            case "Sun":planets_distance.setText(R.string.suntoearth);
                                break;
                            case "Mercury":planets_distance.setText(R.string.mercurytoearth);
                                break;
                            case "Venus":planets_distance.setText(R.string.venustoearth);
                                break;
                            case "Mars":planets_distance.setText(R.string.earthtomars);
                                break;
                            case "Jupiter":planets_distance.setText(R.string.earthtojupiter);
                                break;
                            case "Saturn":planets_distance.setText(R.string.earthtosaturn);
                                break;
                            case "Neptune":planets_distance.setText(R.string.earthtoneptune);
                                break;
                            case "Uranus":planets_distance.setText(R.string.earthtouranus);
                                break;
                        }

                        break;
                    case 5:
                        name_planet_01.setText(R.string.mars_name);
                        age_planet_01.setText(R.string.mars_age);
                        mass_planet_01.setText(R.string.mars_mass);
                        weight_planet_01.setText(R.string.mars_weight);
                        made_planet_01.setText(R.string.mars_made);
                        sat_planet_01.setText(R.string.mars_sat);

                        switch (spinnerText_right){
                            case "Sun":planets_distance.setText(R.string.suntomars);
                                break;
                            case "Mercury":planets_distance.setText(R.string.mercurytomars);
                                break;
                            case "Venus":planets_distance.setText(R.string.venustomars);
                                break;
                            case "Earth":planets_distance.setText(R.string.earthtomars);
                                break;
                            case "Jupiter":planets_distance.setText(R.string.marstojupiter);
                                break;
                            case "Saturn":planets_distance.setText(R.string.marstosaturn);
                                break;
                            case "Neptune":planets_distance.setText(R.string.marstoneptune);
                                break;
                            case "Uranus":planets_distance.setText(R.string.marstouranus);
                                break;
                        }

                        break;
                    case 6:
                        name_planet_01.setText(R.string.jupiter_name);
                        age_planet_01.setText(R.string.jupiter_age);
                        mass_planet_01.setText(R.string.jupiter_mass);
                        weight_planet_01.setText(R.string.jupiter_weight);
                        made_planet_01.setText(R.string.jupiter_made);
                        sat_planet_01.setText(R.string.jupiter_sat);

                        switch (spinnerText_right){
                            case "Sun":planets_distance.setText(R.string.suntojupiter);
                                break;
                            case "Mercury":planets_distance.setText(R.string.mercurytojupiter);
                                break;
                            case "Venus":planets_distance.setText(R.string.venustojupiter);
                                break;
                            case "Earth":planets_distance.setText(R.string.earthtojupiter);
                                break;
                            case "Mars":planets_distance.setText(R.string.marstojupiter);
                                break;
                            case "Saturn":planets_distance.setText(R.string.jupitertosaturn);
                                break;
                            case "Neptune":planets_distance.setText(R.string.jupitertoneptune);
                                break;
                            case "Uranus":planets_distance.setText(R.string.jupitertouranus);
                                break;
                        }

                        break;
                    case 7:
                        name_planet_01.setText(R.string.saturn_name);
                        age_planet_01.setText(R.string.saturn_age);
                        mass_planet_01.setText(R.string.saturn_mass);
                        weight_planet_01.setText(R.string.saturn_weight);
                        made_planet_01.setText(R.string.saturn_made);
                        sat_planet_01.setText(R.string.saturn_sat);

                        switch (spinnerText_right){
                            case "Sun":planets_distance.setText(R.string.suntosaturn);
                                break;
                            case "Mercury":planets_distance.setText(R.string.mercurytosaturn);
                                break;
                            case "Venus":planets_distance.setText(R.string.venustosaturn);
                                break;
                            case "Earth":planets_distance.setText(R.string.earthtosaturn);
                                break;
                            case "Mars":planets_distance.setText(R.string.marstosaturn);
                                break;
                            case "Jupiter":planets_distance.setText(R.string.jupitertosaturn);
                                break;
                            case "Neptune":planets_distance.setText(R.string.saturntoneptune);
                                break;
                            case "Uranus":planets_distance.setText(R.string.saturntouranus);
                                break;
                        }

                        break;
                    case 8:
                        name_planet_01.setText(R.string.uranus_name);
                        age_planet_01.setText(R.string.uranus_age);
                        mass_planet_01.setText(R.string.uranus_mass);
                        weight_planet_01.setText(R.string.uranus_weight);
                        made_planet_01.setText(R.string.uranus_made);
                        sat_planet_01.setText(R.string.uranus_sat);

                        switch (spinnerText_right){
                            case "Sun":planets_distance.setText(R.string.suntouranus);
                                break;
                            case "Mercury":planets_distance.setText(R.string.mercurytouranus);
                                break;
                            case "Venus":planets_distance.setText(R.string.venustouranus);
                                break;
                            case "Earth":planets_distance.setText(R.string.earthtouranus);
                                break;
                            case "Mars":planets_distance.setText(R.string.marstouranus);
                                break;
                            case "Jupiter":planets_distance.setText(R.string.jupitertouranus);
                                break;
                            case "Neptune":planets_distance.setText(R.string.uranustoneptune);
                                break;
                            case "Saturn":planets_distance.setText(R.string.saturntouranus);
                                break;
                        }

                        break;
                    case 9:
                        name_planet_01.setText(R.string.neptune_name);
                        age_planet_01.setText(R.string.neptune_age);
                        mass_planet_01.setText(R.string.neptune_mass);
                        weight_planet_01.setText(R.string.neptune_weight);
                        made_planet_01.setText(R.string.neptune_made);
                        sat_planet_01.setText(R.string.neptune_sat);

                        switch (spinnerText_right){
                            case "Sun":planets_distance.setText(R.string.suntoneptune);
                                break;
                            case "Mercury":planets_distance.setText(R.string.mercurytoneptune);
                                break;
                            case "Venus":planets_distance.setText(R.string.venustoneptune);
                                break;
                            case "Earth":planets_distance.setText(R.string.earthtoneptune);
                                break;
                            case "Mars":planets_distance.setText(R.string.marstoneptune);
                                break;
                            case "Jupiter":planets_distance.setText(R.string.jupitertoneptune);
                                break;
                            case "Uranus":planets_distance.setText(R.string.uranustoneptune);
                                break;
                            case "Saturn":planets_distance.setText(R.string.saturntoneptune);
                                break;
                        }

                        break;

                }
                break;
            case 2:
                switch (location) {
                    case 1:
                        name_planet_02.setText(R.string.sun_name);
                        age_planet_02.setText(R.string.sun_age);
                        mass_planet_02.setText(R.string.sun_mass);
                        weight_planet_02.setText(R.string.sun_weight);
                        made_planet_02.setText(R.string.sun_made);
                        sat_planet_02.setText(R.string.sun_sat);
                        break;
                    case 2:
                        name_planet_02.setText(R.string.mercury_name);
                        age_planet_02.setText(R.string.mercury_age);
                        mass_planet_02.setText(R.string.mercury_mass);
                        weight_planet_02.setText(R.string.mercury_weight);
                        made_planet_02.setText(R.string.mercury_made);
                        sat_planet_02.setText(R.string.mercury_sat);
                        break;
                    case 3:
                        name_planet_02.setText(R.string.venus_name);
                        age_planet_02.setText(R.string.venus_age);
                        mass_planet_02.setText(R.string.venus_mass);
                        weight_planet_02.setText(R.string.venus_weight);
                        made_planet_02.setText(R.string.venus_made);
                        sat_planet_02.setText(R.string.venus_sat);
                        break;
                    case 4:
                        name_planet_02.setText(R.string.earth_name);
                        age_planet_02.setText(R.string.earth_age);
                        mass_planet_02.setText(R.string.earth_mass);
                        weight_planet_02.setText(R.string.earth_weight);
                        made_planet_02.setText(R.string.earth_made);
                        sat_planet_02.setText(R.string.earth_sat);
                        break;
                    case 5:
                        name_planet_02.setText(R.string.mars_name);
                        age_planet_02.setText(R.string.mars_age);
                        mass_planet_02.setText(R.string.mars_mass);
                        weight_planet_02.setText(R.string.mars_weight);
                        made_planet_02.setText(R.string.mars_made);
                        sat_planet_02.setText(R.string.mars_sat);
                        break;
                    case 6:
                        name_planet_02.setText(R.string.jupiter_name);
                        age_planet_02.setText(R.string.jupiter_age);
                        mass_planet_02.setText(R.string.jupiter_mass);
                        weight_planet_02.setText(R.string.jupiter_weight);
                        made_planet_02.setText(R.string.jupiter_made);
                        sat_planet_02.setText(R.string.jupiter_sat);
                        break;
                    case 7:
                        name_planet_02.setText(R.string.saturn_name);
                        age_planet_02.setText(R.string.saturn_age);
                        mass_planet_02.setText(R.string.saturn_mass);
                        weight_planet_02.setText(R.string.saturn_weight);
                        made_planet_02.setText(R.string.saturn_made);
                        sat_planet_02.setText(R.string.saturn_sat);
                        break;
                    case 8:
                        name_planet_02.setText(R.string.uranus_name);
                        age_planet_02.setText(R.string.uranus_age);
                        mass_planet_02.setText(R.string.uranus_mass);
                        weight_planet_02.setText(R.string.uranus_weight);
                        made_planet_02.setText(R.string.uranus_made);
                        sat_planet_02.setText(R.string.uranus_sat);
                        break;
                    case 9:
                        name_planet_02.setText(R.string.neptune_name);
                        age_planet_02.setText(R.string.neptune_age);
                        mass_planet_02.setText(R.string.neptune_mass);
                        weight_planet_02.setText(R.string.neptune_weight);
                        made_planet_02.setText(R.string.neptune_made);
                        sat_planet_02.setText(R.string.neptune_sat);
                        break;

                }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        scrollView_planets.post(new Runnable() {
            public void run() {

                ViewGroup vg =(ViewGroup)button_convert.getParent();
                scrollView_planets.smoothScrollTo(0,vg.getTop());
            }});
        switch (v.getId()) {

            case R.id.convert:

                spinnerText_left = spinner_left.getSelectedItem().toString();
                spinnerText_right = spinner_right.getSelectedItem().toString();
                if (!spinnerText_left.equalsIgnoreCase("select") && !spinnerText_right.equalsIgnoreCase("select")) {
                    if (!spinnerText_left.equalsIgnoreCase(spinnerText_right)) {
                        layout_planetary_details.setVisibility(View.VISIBLE);

                        switch (spinnerText_left){
                            case "Sun":
                                setText(1,1,spinnerText_right);
                                break;
                            case "Mercury":
                                setText(2,1,spinnerText_right);
                                break;
                            case "Venus":
                                setText(3,1,spinnerText_right);
                                break;
                            case "Earth":
                                setText(4,1,spinnerText_right);
                                break;
                            case "Mars":
                                setText(5,1,spinnerText_right);
                                break;
                            case "Jupiter":
                                setText(6,1,spinnerText_right);
                                break;
                            case "Saturn":
                                setText(7,1,spinnerText_right);
                                break;
                            case "Uranus":
                                setText(8,1,spinnerText_right);
                                break;
                            case "Neptune":
                                setText(9,1,spinnerText_right);
                                break;
                        }
                        switch (spinnerText_right){
                            case "Sun":
                                setText(1,2,spinnerText_left);
                                break;
                            case "Mercury":
                                setText(2,2,spinnerText_left);
                                break;
                            case "Venus":
                                setText(3,2,spinnerText_left);
                                break;
                            case "Earth":
                                setText(4,2,spinnerText_left);
                                break;
                            case "Mars":
                                setText(5,2,spinnerText_left);
                                break;
                            case "Jupiter":
                                setText(6,2,spinnerText_left);
                                break;
                            case "Saturn":
                                setText(7,2,spinnerText_left);
                                break;
                            case "Uranus":
                                setText(8,2,spinnerText_left);
                                break;
                            case "Neptune":
                                setText(9,2,spinnerText_left);
                                break;
                        }

                        } else {
                        Toast toast=Toast.makeText(getActivity(),"Please select two different planets",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,300);
                        toast.show();
                    }
                } else {
                    Toast toast=Toast.makeText(getActivity(),"Please select the planets",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,300);
                    toast.show();
                }
                break;

        }
    }

    @Override
    public void onPause() {
        super.onPause();
       /* if(dialog!=null){
        dialog.dismiss();}*/
    }
}

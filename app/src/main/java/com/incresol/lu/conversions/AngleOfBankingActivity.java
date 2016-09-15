package com.incresol.lu.conversions;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Incresol-078 on 01-07-2016.
 */
public class AngleOfBankingActivity extends android.app.Fragment implements View.OnClickListener{

    View aobView;
    EditText editText_velocity, editText_radius;
    TextView result;
    Button button_aob;
    String string_velocity,string_radius;
    double velocity,radius,aob;
    static LinearLayout layout_main_aobActivity;
    ScrollView scrollView_aob;

    //timer and slider <code></code>
    public int currentimageindex=0;
    ImageView imageslider,information_imageView;
    ProgressBar mProgressBar;
    TextView set_timer;
    int TimeCounter=30;
    static LinearLayout layout_timer,layout_slider;
    int[] IMAGE_IDS = {R.drawable.icon_1_angle, R.drawable.icon_2_angle};
    ImageButton close;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aobView = inflater.inflate(R.layout.activity_angleofbanking, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Angle Of Banking");
        MainActivity.home=0;
        editText_velocity = (EditText) aobView.findViewById(R.id.editText_velocity);
        editText_radius = (EditText) aobView.findViewById(R.id.editText_radius);
        editText_velocity.setHint("velocity");
        editText_radius.setHint("radius");
        result=(TextView) aobView.findViewById(R.id.result);
        layout_main_aobActivity=(LinearLayout)aobView.findViewById(R.id.layout_main);
        scrollView_aob=(ScrollView)aobView.findViewById(R.id.scrollView_aob);

        //code for timer and image slider
        layout_timer=(LinearLayout)aobView.findViewById(R.id.layout_timer);
        layout_slider=(LinearLayout)aobView.findViewById(R.id.layout_slider);
        imageslider = (ImageView)aobView.findViewById(R.id.imageslider);
        set_timer=(TextView) aobView.findViewById(R.id.set_timer);
        mProgressBar=(ProgressBar)aobView.findViewById(R.id.progressBar);
        information_imageView=(ImageView)aobView.findViewById(R.id.information_imageView);

        if(ThemesActivity.imageId != null){
            MainActivity.themeColorChange(ThemesActivity.imageId,"aob",scrollView_aob);
        }else{
            MainActivity.themeColorChange("blue","aob",scrollView_aob);
        }
        button_aob=(Button)aobView.findViewById(R.id.calculate_aob);
        button_aob.setOnClickListener(this);
        ((MainActivity) getActivity()).timerOn(IMAGE_IDS,layout_slider,layout_timer,imageslider,set_timer,mProgressBar);
information_imageView.setOnClickListener(this);

        return aobView;
    }

    @Override
    public void onClick(View v) {
        result.setText("");
        switch(v.getId()){
            case R.id.calculate_aob:
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(aobView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                string_velocity=editText_velocity.getText().toString();
                string_radius=editText_radius.getText().toString();
                if(string_radius.length()!=0 && string_velocity.length()!=0){
                    velocity=Double.parseDouble(string_velocity);
                    radius=Double.parseDouble(string_radius);
                    Log.i("velocity and radius>>>",""+velocity+"  "+radius);
                    double value=((Math.pow((velocity*1000/3600),2))/(radius*9.81));
                    Log.i(" value=====>",""+value);
                    double returnValue= Math.atan(value);
                    Log.i("Tan Tita value=====>",""+returnValue);
                    double aob=  Math.toDegrees(returnValue);
                    result.setText(""+String.format("%.2f",aob)+" Degrees");
                }
                else{
                    Toast.makeText(getActivity(),"Please enter the values",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.information_imageView:
                Intent intent_dialog=new Intent(getActivity(),Custom_aob_info_dialog.class);
                startActivity(intent_dialog);
                break;

        }
    }


}

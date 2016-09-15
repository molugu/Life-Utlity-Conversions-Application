package com.incresol.lu.conversions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;


/**
 * Created by Incresol-078 on 22-07-2016.
 */
public class ThemesActivity extends android.app.Fragment implements View.OnClickListener {

    View themeView;
    static LinearLayout layout_main_themesActivity;
    ImageView imageView_purple,imageView_blue,imageView_green,imageView_pink,imageView_yellow,imageView_orange;
    static String imageId= null;
    Context context;
    SharedPreferences sharedPreferences_themeColor;
    public static final String ThemeColorPreferences="ThemeColor";
    public static final String ThemeColorName="ThemeColorName";
    ScrollView scrollView_themes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        themeView=inflater.inflate(R.layout.activity_themes,container,false);
        ((MainActivity)getActivity()).setActionBarTitle("Themes");
        layout_main_themesActivity=(LinearLayout) themeView.findViewById(R.id.layout_main);
        scrollView_themes=(ScrollView)themeView.findViewById(R.id.scrollView_themes);
        themeChange();
        MainActivity.home=0;
        imageView_blue=(ImageView) themeView.findViewById(R.id.imageView_blue);
        imageView_purple=(ImageView) themeView.findViewById(R.id.imageView_purple);
        imageView_pink=(ImageView) themeView.findViewById(R.id.imageView_pink);
        imageView_green=(ImageView) themeView.findViewById(R.id.imageView_green);
        imageView_orange=(ImageView) themeView.findViewById(R.id.imageView_orange);
        imageView_yellow=(ImageView) themeView.findViewById(R.id.imageView_yellow);
        context = this.getActivity();
        imageView_blue.setOnClickListener(this);
        imageView_purple.setOnClickListener(this);
        imageView_pink.setOnClickListener(this);
        imageView_green.setOnClickListener(this);
        imageView_orange.setOnClickListener(this);
        imageView_yellow.setOnClickListener(this);

        return themeView;
    }

    public void themeChange(){
        if(ThemesActivity.imageId != null) {
            MainActivity.themeColorChange(ThemesActivity.imageId,"plain",scrollView_themes);
        }else{
            MainActivity.themeColorChange("blue","plain",scrollView_themes);
        }
    }

    @Override
    public void onClick(View v) {
sharedPreferences_themeColor=context.getSharedPreferences(ThemeColorPreferences,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_themeColor=sharedPreferences_themeColor.edit();
        switch (v.getId()){

            case R.id.imageView_blue:

                imageId = "blue";
                themeChange();
                    break;
            case R.id.imageView_green:
                imageId ="green";
                themeChange();
                break;
            case R.id.imageView_orange:
                imageId = "orange";
                themeChange();
                break;
            case R.id.imageView_pink:
                imageId = "pink";
                themeChange();
                break;
            case R.id.imageView_yellow:
                imageId = "yellow";
                themeChange();
                break;
            case R.id.imageView_purple:
                imageId = "purple";
                themeChange();
                break;

        }
        Log.i("image id===>",imageId);
        editor_themeColor.putString(ThemeColorName,imageId);
editor_themeColor.commit();

    }

}

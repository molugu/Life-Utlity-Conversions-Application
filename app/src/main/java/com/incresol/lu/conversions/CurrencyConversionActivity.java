package com.incresol.lu.conversions;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Incresol-078 on 12-08-2016.
 */
public class CurrencyConversionActivity extends android.app.Fragment implements View.OnClickListener {

    View currencyView;
    EditText editText_left, editText_right;
    Spinner spinner_left, spinner_right;
    String spinnerText_left, spinnerText_right;
    Button button_convert;
    static LinearLayout layout_main_currencyActivity;
    ScrollView scrollView_currency;

    //timer and slider <code></code>
    public int currentimageindex = 0;
    ImageView imageslider;
    ProgressBar mProgressBar;
    TextView set_timer;
    int TimeCounter = 30;
    static LinearLayout layout_timer, layout_slider;
    int[] IMAGE_IDS = {R.drawable.currency_icon, R.drawable.currency_icon_1, R.drawable.currency_icon_2};
    Dialog dialog;

    Double value;

    public static final String CurrencyValuesPreferences="CurrencyValues";
   WebView webview;

    ConnectivityManager cm;
    NetworkInfo activeNetwork;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        currencyView = inflater.inflate(R.layout.activity_currency_conversion, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Currency Conversion");
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        MainActivity.home=0;
        editText_left = (EditText) currencyView.findViewById(R.id.editText_left);
        editText_right = (EditText) currencyView.findViewById(R.id.editText_right);
        spinner_left = (Spinner) currencyView.findViewById(R.id.spinner_left);
        spinner_right = (Spinner) currencyView.findViewById(R.id.spinner_right);
        button_convert = (Button) currencyView.findViewById(R.id.convert);
        layout_main_currencyActivity = (LinearLayout) currencyView.findViewById(R.id.layout_main);
        scrollView_currency = (ScrollView) currencyView.findViewById(R.id.scrollView_currency);

        //code for timer and image slider
        layout_timer = (LinearLayout) currencyView.findViewById(R.id.layout_timer);
        layout_slider = (LinearLayout) currencyView.findViewById(R.id.layout_slider);
        imageslider = (ImageView) currencyView.findViewById(R.id.imageslider);
        set_timer = (TextView) currencyView.findViewById(R.id.set_timer);
        mProgressBar = (ProgressBar) currencyView.findViewById(R.id.progressBar);

        webview = (WebView) currencyView.findViewById(R.id.browser);
        cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            MainActivity.toastMessage("Please switch on the internet");
        }

        if (ThemesActivity.imageId != null) {
            MainActivity.themeColorChange(ThemesActivity.imageId, "currency", scrollView_currency);
        } else {
            MainActivity.themeColorChange("blue", "currency", scrollView_currency);
        }


        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.currency, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_left.setAdapter(arrayAdapter);
        spinner_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(currencyView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (position == 0) {
                    editText_left.setText("");
                }

                switch(position){

                    case 1:spinnerText_left="EUR";
                        break;
                    case 2:spinnerText_left="USD";
                        break;
                    case 3:spinnerText_left="INR";
                        break;
                    case 4:spinnerText_left="AUD";
                        break;
                    case 5:spinnerText_left="CAD";
                        break;
                    case 6:spinnerText_left="SGD";
                        break;
                    case 7:spinnerText_left="JPY";
                        break;
                    case 8:spinnerText_left="MYR";
                        break;
                    case 9:spinnerText_left="GBP";
                        break;
                    case 10:spinnerText_left="CHF";
                        break;
                    case 11:spinnerText_left="CNY";
                        break;
                    case 12:spinnerText_left="SAR";
                        break;
                    case 13:spinnerText_left="KWD";
                        break;
                    case 14:spinnerText_left="BRL";
                        break;
                    case 15:spinnerText_left="COP";
                        break;
                    case 16:spinnerText_left="HKD";
                        break;
                }
                editText_right.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_right.setAdapter(arrayAdapter);
        spinner_right.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(currencyView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                editText_right.setText("");
                switch(position){

                    case 1:spinnerText_right="EUR";
                        break;
                    case 2:spinnerText_right="USD";
                        break;
                    case 3:spinnerText_right="INR";
                        break;
                    case 4:spinnerText_right="AUD";
                        break;
                    case 5:spinnerText_right="CAD";
                        break;
                    case 6:spinnerText_right="SGD";
                        break;
                    case 7:spinnerText_right="JPY";
                        break;
                    case 8:spinnerText_right="MYR";
                        break;
                    case 9:spinnerText_right="GBP";
                        break;
                    case 10:spinnerText_right="CHF";
                        break;
                    case 11:spinnerText_right="CNY";
                        break;
                    case 12:spinnerText_right="SAR";
                        break;
                    case 13:spinnerText_right="KWD";
                        break;
                    case 14:spinnerText_right="BRL";
                        break;
                    case 15:spinnerText_right="COP";
                        break;
                    case 16:spinnerText_right="HKD";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        button_convert.setOnClickListener(this);

        set_timer=(TextView) currencyView.findViewById(R.id.set_timer);
        mProgressBar=(ProgressBar)currencyView.findViewById(R.id.progressBar);
        ((MainActivity) getActivity()).timerOn(IMAGE_IDS,layout_slider,layout_timer,imageslider,set_timer,mProgressBar);

        return currencyView;
    }

    class MyJavaScriptInterface extends WebViewClient{

        @Override
        public void onPageFinished(WebView view, String urlConection) {

            URL url;
            URLConnection conexion;
            try {
                url = new URL(urlConection);
                conexion = url.openConnection();
                conexion.setConnectTimeout(3000);
                conexion.connect();
                int size = conexion.getContentLength();
            }catch(Exception e){
                e.printStackTrace();
                if(dialog.isShowing()){
                    dialog.dismiss();}
            }


            String htmlContent = "";
            HttpGet httpGet = new HttpGet(urlConection);
            HttpResponse response;
            try {
                URL aURL = new URL(urlConection);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                htmlContent = convertToString(is);
                Log.i("html out put",htmlContent);
                Pattern pattern = Pattern.compile( "<span class="+"bld"+">(.*?)</span>" );
                Matcher m = pattern.matcher(htmlContent);
                if( m.find() ) {
                    String theStringYouAreLookingFor = m.group( 1 );
                    editText_right.setText(theStringYouAreLookingFor);
                }else{
                    editText_right.setText("Couldn't convert!");
                }


            } catch (Exception e) {
                e.printStackTrace();
                if(dialog.isShowing()){
                    dialog.dismiss();}
            }
            if(dialog.isShowing()){
                dialog.dismiss();}
        }

        @Override
        public void onPageStarted(WebView view, String urlConection, Bitmap favicon) {
            super.onPageStarted(view, urlConection, favicon);

            dialog=new Dialog(getActivity(),android.R.style.Theme_DeviceDefault_Light_Dialog);
            dialog.setContentView(R.layout.custom_progressbar);
            dialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        public String convertToString(InputStream inputStream){
            StringBuffer string = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    string.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String resultance=string.toString();

            return resultance;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.convert:
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(currencyView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

               /* spinnerText_left = spinner_left.getSelectedItem().toString();
                spinnerText_right = spinner_right.getSelectedItem().toString();*/
                activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected) {

                    if (spinnerText_left.equalsIgnoreCase("Select Currency") && spinnerText_right.equalsIgnoreCase("Select Currency")) {
                        ((MainActivity) getActivity()).toastMessage("Please select the currencies");
                    } else if (spinnerText_left.equalsIgnoreCase("Select Currency")) {
                        ((MainActivity) getActivity()).toastMessage("Please select any currency");
                    } else if (spinnerText_right.equalsIgnoreCase("Select Currency")) {
                        ((MainActivity) getActivity()).toastMessage("Please select any currency");
                    } else if (editText_left.getText().length() != 0) {

                        if (spinnerText_right.equalsIgnoreCase(spinnerText_left)){
                            editText_right.setText(editText_left.getText().toString());
                        }else {


                            String val = editText_left.getText().toString();
                            value = Double.parseDouble(val);

                            webview.getSettings().setJavaScriptEnabled(true);
                            webview.setWebViewClient(new MyJavaScriptInterface());

                            webview.loadUrl("https://www.google.com/finance/converter?a=" + value + "&from=" + spinnerText_left + "&to=" + spinnerText_right);
                        }
                    } else {
                        ((MainActivity) getActivity()).toastMessage("Please enter any value");
                    }
                }else{
                    MainActivity.toastMessage("Please Turn on the internet");
                }

                break;
        }

    }
}
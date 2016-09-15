package com.incresol.lu.conversions;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static DrawerLayout drawer;
    static LinearLayout linearLayout_welcomeNote, content_main;
    static Context context;
    boolean doubleBackToExitPressedOnce = false;
    boolean inFragment = false;
    SharedPreferences sharedPreferences_themeColor, SP_currency_values;
    public static final String ThemeColorPreferences = "ThemeColor";
    public static final String ThemeColorName = "ThemeColorName";
    boolean timerStarted = false;

    public static final String CurrencyValuesPreferences = "CurrencyValues";
    public static final String First_time = "First_time";
    public static final String LastUpdatedSeconds = "LastUpdatedSeconds";
    public static final String USDINR = "USDINR";
    public static final String USDGBP = "USDGBP";
    public static final String USDAUD = "USDAUD";
    public static final String USDEUR = "USDEUR";
    public static final String USDCAD = "USDCAD";
    public static final String USDSGD = "USDSGD";

    ImageView bmi, length, mass, area, time, digital, temperature, aob, astronomical, planetary_distances, currency, timezone;
    Button themes, aboutus;

    final FragmentManager fragmentManager = getFragmentManager();
    FrameLayout frameLayout;
    LinearLayout contentMailLayout;

    public void initui() {
        setContentView(R.layout.activity_main);
//        contentMailLayout = (LinearLayout)findViewById(R.id.content_main_layout);
        if (frameLayout == null) {
            frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        }
//        contentMailLayout.addView(frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        context = getApplicationContext();
        linearLayout_welcomeNote = (LinearLayout) findViewById(R.id.welcomenote);
        bmi = (ImageView) findViewById(R.id.bmi);
        length = (ImageView) findViewById(R.id.length);
        mass = (ImageView) findViewById(R.id.mass);
        area = (ImageView) findViewById(R.id.area);
        time = (ImageView) findViewById(R.id.time);
        digital = (ImageView) findViewById(R.id.digital);
        temperature = (ImageView) findViewById(R.id.temperature);
        aob = (ImageView) findViewById(R.id.aob);
        astronomical = (ImageView) findViewById(R.id.astronomical);
        planetary_distances = (ImageView) findViewById(R.id.planetary_distances);
        currency = (ImageView) findViewById(R.id.currency);
        themes = (Button) findViewById(R.id.themes);
        aboutus = (Button) findViewById(R.id.aboutus);
        timezone = (ImageView) findViewById(R.id.timezone);

        /*if(ThemesActivity.imageId != null){
            themeColorChange(ThemesActivity.imageId,"plain",content_main);
        }else{
            themeColorChange("blue","plain",linearLayout_welcomeNote);
        }*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(drawerView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkThemeColor();


        bmi.setOnClickListener(this);
        length.setOnClickListener(this);
        mass.setOnClickListener(this);
        area.setOnClickListener(this);
        time.setOnClickListener(this);
        digital.setOnClickListener(this);
        temperature.setOnClickListener(this);
        aob.setOnClickListener(this);
        astronomical.setOnClickListener(this);
        planetary_distances.setOnClickListener(this);
        currency.setOnClickListener(this);
        themes.setOnClickListener(this);
        aboutus.setOnClickListener(this);
        timezone.setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        System.out.println("** onConfigurationChanged **");

        if (!inFragment) {
            initui();
        }

        Fragment myFragment = (Fragment) getFragmentManager().findFragmentByTag("TimeZoneActivity");
        if (myFragment != null && myFragment.isVisible()) {
            System.out.println("VINAY KRISHNA");

            if (TimeZoneActivity.datePickerBoolean == true) {
                System.out.println("TimeZoneActivity.datePickerBoolean ========>>>"+TimeZoneActivity.datePickerBoolean);
                if (TimeZoneActivity.mDatePickerDialog != null && TimeZoneActivity.mDatePickerDialog.isShowing()) {
                    System.out.println("TimeZoneActivity.mDatePickerDialog != null && TimeZoneActivity.mDatePickerDialog.isShowing() ========>>>"+TimeZoneActivity.datePickerBoolean);
                    TimeZoneActivity.hideDatePickerDialog();
                    TimeZoneActivity.showDatePickerDialog();
                    return;
                }
                return;
            }
            else if (TimeZoneActivity.timePickerBoolean == true) {
                System.out.println("TimeZoneActivity.timePickerBoolean ========>>>"+TimeZoneActivity.timePickerBoolean);
                if (TimeZoneActivity.mTimePickerDialog != null && TimeZoneActivity.mTimePickerDialog.isShowing()) {
                    System.out.println("TimeZoneActivity.mTimePickerDialog != null && TimeZoneActivity.mTimePickerDialog.isShowing()"+TimeZoneActivity.timePickerBoolean);
                    TimeZoneActivity.hideTimePickerDialog();
                    TimeZoneActivity.showTimePickerDialog();
                    return;
                }
                return;
            }
        }
       // System.out.println("Class Name -> " + this.getClass().getSimpleName() + "\n fragment -> " + myFragment.getActivity().getClass().getSimpleName());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initui();

        //setContentView(R.layout.activity_main);


    }

    @Override
    public void onClick(View v) {
        inFragment = true;
        switch (v.getId()) {
            case R.id.bmi:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new BMICalculationActivity(), "BMICalculationActivity").commit();
                break;
            case R.id.length:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new LengthConversionActivity(), "LengthConversionActivity").commit();
                break;
            case R.id.mass:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new MassConversionActivity(), "MassConversionActivity").commit();
                break;
            case R.id.area:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AreaConversionActivity(), "AreaConversionActivity").commit();
                break;
            case R.id.time:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new TimeConversionActivity(), "TimeConversionActivity").commit();
                break;
            case R.id.digital:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new DigitalStorageConversionActivity(), "DigitalStorageConversionActivity").commit();
                break;
            case R.id.temperature:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new TemperatureConversionActivity(), "TemperatureConversionActivity").commit();
                break;
            case R.id.aob:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AngleOfBankingActivity(), "AngleOfBankingActivity").commit();
                break;
            case R.id.astronomical:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new VelocityActivity(), "VelocityActivity").commit();
                break;
            case R.id.planetary_distances:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new PlanetoryDistancesActivity(), "PlanetoryDistancesActivity").commit();
                break;
            case R.id.currency:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new CurrencyConversionActivity(), "CurrencyConversionActivity").commit();
                break;
            case R.id.themes:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ThemesActivity(), "ThemesActivity").commit();
                break;
            case R.id.aboutus:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AboutUsActivity(), "AboutUsActivity").commit();
                break;
            case R.id.timezone:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new TimeZoneActivity(), "TimeZoneActivity").commit();
                break;

        }

    }

    public static void themeColorChange(String color, String location, LinearLayout linearLayout) {
        AssetManager manager = context.getAssets();
        try {
            InputStream ip = manager.open(location + "/" + location + "_" + color + ".png");
            Drawable d = Drawable.createFromStream(ip, null);
            linearLayout.setBackground(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void themeColorChange(String color, String location, ScrollView scrollView) {
        AssetManager manager = context.getAssets();
        try {
            InputStream ip = manager.open(location + "/" + location + "_" + color + ".png");
            Drawable d = Drawable.createFromStream(ip, null);
            scrollView.setBackground(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkThemeColor() {
        sharedPreferences_themeColor = getSharedPreferences(ThemeColorPreferences, Context.MODE_PRIVATE);
        String imgId = this.sharedPreferences_themeColor.getString(ThemeColorName, "empty");
        Log.i("in main image id==> ", imgId);
        if (imgId.equalsIgnoreCase("empty")) {
            ThemesActivity.imageId = "blue";
        } else {
            ThemesActivity.imageId = imgId;

        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerVisible(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } /*else {
            super.onBackPressed();
        }*/

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        } else {

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homemenu, menu);
        return true;
    }

    public static int home = 1;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.homeicon) {

            if (home == 1) {
                return true;
            } else {
                inFragment = false;
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }

            home = 1;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        linearLayout_welcomeNote.setVisibility(View.GONE);
        int id = item.getItemId();
        inFragment = true;
//        FragmentManager fragmentManager = getFragmentManager();
        if (id == R.id.nav_bmi_calculation) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new BMICalculationActivity(),"BMICalculationActivity").commit();
        } else if (id == R.id.nav_length_conversions) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new LengthConversionActivity(),"LengthConversionActivity").commit();
        } else if (id == R.id.nav_mass_conversions) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MassConversionActivity(),"MassConversionActivity").commit();
        } else if (id == R.id.nav_area_conversions) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AreaConversionActivity(),"AreaConversionActivity").commit();
        } else if (id == R.id.nav_time_conversions) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TimeConversionActivity(),"TimeConversionActivity").commit();
        } else if (id == R.id.nav_digitalStorage_conversions) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new DigitalStorageConversionActivity(),"DigitalStorageConversionActivity").commit();
        } else if (id == R.id.nav_temperature_conversions) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TemperatureConversionActivity(),"TemperatureConversionActivity").commit();
        } else if (id == R.id.nav_angle_of_banking) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AngleOfBankingActivity(),"AngleOfBankingActivity").commit();
        } else if (id == R.id.nav_velocity) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new VelocityActivity(),"VelocityActivity").commit();
        } else if (id == R.id.nav_planetory) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new PlanetoryDistancesActivity(),"PlanetoryDistancesActivity").commit();
        } else if (id == R.id.nav_currency) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new CurrencyConversionActivity(),"CurrencyConversionActivity").commit();
        } else if (id == R.id.nav_aboutus) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AboutUsActivity(),"AboutUsActivity").commit();
        } else if (id == R.id.nav_themes) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ThemesActivity(),"ThemesActivity").commit();
        } else if (id == R.id.nav_timezone) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TimeZoneActivity(), "TimeZoneActivity").commit();
        }
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("in run of drawer close","===***&&&&&");
                drawer.closeDrawer(GravityCompat.START);
            }
        }, 800);*/
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public static void toastMessage(String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("OnPause========>", "in pause method");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("OnResume========>", "in resume method");
    }


    public int currentimageindex = 0;
    int TimeCounter;
    Timer timer;

    public void timerOn(final int[] IMAGE_IDS, final LinearLayout layout_slider, final LinearLayout layout_timer, final ImageView imageslider, final TextView set_timer, final ProgressBar mProgressBar) {
        if (timer != null && timerStarted) {
            timer.cancel();
        }
        timer = new Timer();
        TimeCounter = 30;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (this == null)
                    return;
                runOnUiThread(new Runnable() {
                    public void run() {
                        timerStarted = true;
                        layout_timer.setVisibility(View.VISIBLE);
                        Log.i("in thread", "" + TimeCounter);
                        if (TimeCounter == 0) {
                            timer.cancel();
                            layout_timer.setVisibility(View.GONE);
                            animoTimer(IMAGE_IDS, imageslider, layout_slider);
                        } else if (TimeCounter > 0) {
                            TimeCounter--;
                        }

                        mProgressBar.setProgress(TimeCounter);
                        set_timer.setText("" + String.valueOf(TimeCounter));

                        Random random = new Random();
                        set_timer.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));


                    }
                });
            }
        }, 0, 1000);
    }

    //image slider code starts here

    private void animoTimer(final int[] IMAGE_IDS, final ImageView imageslider, final LinearLayout layout_slider) {

        final Handler mHandler = new Handler();

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            public void run() {

                AnimateandSlideShow(IMAGE_IDS, imageslider, layout_slider);

            }
        };

        int delay = 1000; // delay for 1 sec.

        int period = 3000; // repeat every 4 sec.

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);

            }

        }, delay, period);


    }

    private void AnimateandSlideShow(final int[] IMAGE_IDS, final ImageView imageslider, final LinearLayout layout_slider) {
        layout_slider.setVisibility(View.VISIBLE);

        if (this == null)
            return;

        //Animation fadeInAnimation= AnimationUtils.loadAnimation(getActivity(),R.anim.fadein);
        TranslateAnimation animation = new TranslateAnimation(0.0f, 2000.0f,
                0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(6000);  // animation duration
        animation.setRepeatCount(1);  // animation repeat count
        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )

        imageslider.startAnimation(animation);
        imageslider.setImageResource(IMAGE_IDS[currentimageindex % IMAGE_IDS.length]);

        currentimageindex++;

    }


}

package com.incresol.lu.conversions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Incresol-078 on 01-09-2016.
 */
public class Custom_Image_Dialog extends Activity {
    pl.droidsonroids.gif.GifImageView planetary_model_gif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        planetary_model_gif=(pl.droidsonroids.gif.GifImageView) findViewById(R.id.planetary_model_gif);
//        planetary_model_gif.setVisibility(View.INVISIBLE);


        ImageButton close = (ImageButton) findViewById(R.id.btnClose);

        // Close Button
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

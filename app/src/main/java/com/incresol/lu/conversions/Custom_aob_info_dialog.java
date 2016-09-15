package com.incresol.lu.conversions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Incresol-078 on 13-09-2016.
 */
public class Custom_aob_info_dialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aob_info_dialog);



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
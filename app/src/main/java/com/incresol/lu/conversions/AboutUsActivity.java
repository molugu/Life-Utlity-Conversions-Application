package com.incresol.lu.conversions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Incresol-078 on 19-07-2016.
 */
public class AboutUsActivity extends android.app.Fragment implements View.OnClickListener {
    View aboutusView;
    ImageView imageView_location;
    Intent intent_location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        aboutusView=inflater.inflate(R.layout.activity_aboutus,container,false);
        ((MainActivity) getActivity()).setActionBarTitle("About Us");
        imageView_location=(ImageView)aboutusView.findViewById(R.id.imageView_location);
        imageView_location.setOnClickListener(this);
        MainActivity.home=0;
        return aboutusView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_location:
                Uri gmmIntentUri = Uri.parse("geo:17,78?q=Incresol software services,Hyderabad");
                intent_location = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent_location.setPackage("com.google.android.apps.maps");
                if (intent_location.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent_location);
                }
        }
    }
}

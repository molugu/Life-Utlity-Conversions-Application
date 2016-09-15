package com.incresol.lu.conversions;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BMICalculationActivity extends android.app.Fragment implements View.OnClickListener {

    private RadioGroup radioGroup;
    private RadioButton radioSexButton;
    private EditText editText_feet,editText_cms,editText_lbs;
    private Button calculate_BMI;
    private TextView result,bmi_classify_header,textWeight,bmiStatus,textView_underweight_value,textView_normal_value,textView_overweight_value,textView_obese_value;
    private Spinner heightSpinner,weightSpinner;
    private LinearLayout layout_bmiClassification;
        static   LinearLayout  layout_main_bmiActivity;

    public int hfeet;
    public int hcms;
    public int hinc;
    public int wlbs;
    public int wkgs;
    public float bmifeet;
    public float bmicms;
    public int selectedId;
    public String bmifeetstatus;
    public String bmicmsstatus;
    public String spintext,spintext_weight;

    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.activity_bmi_calculation,container,false);
        layout_main_bmiActivity=(LinearLayout)myView.findViewById(R.id.layout_main);
        if(ThemesActivity.imageId != null){
            MainActivity.themeColorChange(ThemesActivity.imageId,"bmi",layout_main_bmiActivity);
        }
        else{
            MainActivity.themeColorChange("blue","bmi",layout_main_bmiActivity);
        }
        ((MainActivity) getActivity())
                .setActionBarTitle("BMI Calculator");
        MainActivity.home=0;
        editText_feet=(EditText)myView.findViewById(R.id.editText_feet);
        editText_lbs=(EditText)myView.findViewById(R.id.editText_lbs);
        editText_cms=(EditText)myView.findViewById(R.id.editText_cms);
        radioGroup=(RadioGroup)myView.findViewById(R.id.radioGroup);
        result=(TextView)myView.findViewById(R.id.result);
        bmiStatus=(TextView)myView.findViewById(R.id.bmiStatus);
       // textWeight=(TextView)myView.findViewById(R.id.textWeight);
        heightSpinner=(Spinner)myView.findViewById(R.id.heightspin);
        weightSpinner=(Spinner)myView.findViewById(R.id.weightspin);
        calculate_BMI=(Button)myView.findViewById(R.id.calculate_BMI);
        layout_bmiClassification=(LinearLayout)myView.findViewById(R.id.bmi_Classification);
        layout_bmiClassification.setVisibility(View.GONE);
        bmi_classify_header=(TextView)myView.findViewById(R.id.bmi_classify_header);
        bmi_classify_header.setVisibility(View.GONE);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.height_arrays, R.layout.spinner_text_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightSpinner.setAdapter(adapter);
        heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    editText_feet.setVisibility(View.VISIBLE);
                    editText_cms.setHint("In");
                    editText_cms.setText("");
                    editText_feet.setText("");
                    result.setVisibility(View.INVISIBLE);
                    bmiStatus.setVisibility(View.INVISIBLE);
                }else{
                    editText_feet.setVisibility(View.GONE);
                    editText_cms.setHint("Cm");
                    editText_cms.setText("");
                    result.setVisibility(View.INVISIBLE);
                    bmiStatus.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<CharSequence> adapter_weight = ArrayAdapter.createFromResource(getActivity(),
                R.array.weight_arrays, R.layout.spinner_text_item);
        adapter_weight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(adapter_weight);
        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    editText_lbs.setHint("Kg");
                    editText_lbs.setText("");
                    result.setVisibility(View.INVISIBLE);
                    bmiStatus.setVisibility(View.INVISIBLE);
                }else{
                    editText_lbs.setHint("Lbs");
                    editText_lbs.setText("");
                    result.setVisibility(View.INVISIBLE);
                    bmiStatus.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculate_BMI.setOnClickListener(this);



        return myView;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.calculate_BMI:

                InputMethodManager inputMethodManager=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                spintext=heightSpinner.getSelectedItem().toString();
                spintext_weight=weightSpinner.getSelectedItem().toString();

                Log.i("inside switch",spintext);
                EditText inches = (EditText)myView.findViewById(R.id.editText_cms);
                EditText lbs = (EditText)myView.findViewById(R.id.editText_lbs);
                EditText feet = (EditText) myView.findViewById(R.id.editText_feet);
                layout_bmiClassification.setVisibility(View.GONE);
                bmi_classify_header.setVisibility(View.GONE);
                result.setVisibility(View.GONE);
                bmiStatus.setVisibility(View.GONE);

                result = (TextView )  myView.findViewById(R.id.result);
                bmiStatus=(TextView)myView.findViewById(R.id.bmiStatus);
                selectedId = radioGroup.getCheckedRadioButtonId();
                if(spintext.equals("Ft/In")&& spintext_weight.equalsIgnoreCase("Lbs")){
                    if((inches.getText().toString().trim().length() != 0)&& (lbs.getText().toString().trim().length() !=0)&&(feet.getText().toString().trim().length() !=0) && (!lbs.getText().toString().equals("0"))&&(!feet.getText().toString().equals("0"))) {
                        hinc = Integer.parseInt(inches.getText().toString());
                        wlbs = Integer.parseInt(lbs.getText().toString());
                        hfeet = Integer.parseInt(feet.getText().toString());
                        displayclassification(selectedId);

                        if (selectedId == R.id.radiomale) {
                            bmifeet = lbsbmi(hfeet, hinc, wlbs);
                            bmifeetstatus = staBMI(bmifeet);
                            result.setVisibility(View.VISIBLE);
                            bmiStatus.setVisibility(View.VISIBLE);
                            result.setText("Your BMI is :" + String.format("%.2f", bmifeet));
                            bmiStatus.setText(bmifeetstatus);
                        }
                        if (selectedId == R.id.radiofemale) {
                            bmifeet = lbsbmi(hfeet, hinc, wlbs);
                            bmicmsstatus = fstaBMI(bmifeet);
                            result.setVisibility(View.VISIBLE);
                            bmiStatus.setVisibility(View.VISIBLE);
                            result.setText("Your BMI is :" + String.format("%.2f", bmifeet));
                            bmiStatus.setText(bmicmsstatus);
                        }


                    }else{
                        Toast.makeText(getActivity(), "Please enter valid values", Toast.LENGTH_LONG).show();
                    }

                }if(spintext.equals("Ft/In")&& spintext_weight.equalsIgnoreCase("Kg")){
                if((inches.getText().toString().trim().length() != 0)&& (lbs.getText().toString().trim().length() !=0)&&(feet.getText().toString().trim().length() !=0) && (!lbs.getText().toString().equals("0"))&&(!feet.getText().toString().equals("0"))) {
                    hinc = Integer.parseInt(inches.getText().toString());
                    wlbs = Integer.parseInt(lbs.getText().toString());
                    hfeet = Integer.parseInt(feet.getText().toString());
                    displayclassification(selectedId);

                    if (selectedId == R.id.radiomale) {
                        bmifeet = ftkgsbmi(hfeet, hinc, wlbs);
                        bmifeetstatus = staBMI(bmifeet);
                        result.setVisibility(View.VISIBLE);
                        bmiStatus.setVisibility(View.VISIBLE);
                        result.setText("Your BMI is :" + String.format("%.2f", bmifeet));
                        bmiStatus.setText(bmifeetstatus);
                    }
                    if (selectedId == R.id.radiofemale) {
                        bmifeet = ftkgsbmi(hfeet, hinc, wlbs);
                        bmicmsstatus = fstaBMI(bmifeet);
                        result.setVisibility(View.VISIBLE);
                        bmiStatus.setVisibility(View.VISIBLE);
                        result.setText("Your BMI is :" + String.format("%.2f", bmifeet));
                        bmiStatus.setText(bmicmsstatus);
                    }


                }else{
                    Toast.makeText(getActivity(), "Please enter valid values", Toast.LENGTH_LONG).show();
                }

            }else if(spintext.equals("Cm") && spintext_weight.equalsIgnoreCase("Kg")){
                    if((inches.getText().toString().trim().length() != 0)&& (lbs.getText().toString().trim().length() !=0)&&(!inches.getText().toString().equals("0")) && (!lbs.getText().toString().equals("0"))) {
                        hinc = Integer.parseInt(inches.getText().toString());
                        wlbs = Integer.parseInt(lbs.getText().toString());
                        displayclassification(selectedId);
                        bmi_classify_header.setVisibility(View.VISIBLE);
                        layout_bmiClassification.setVisibility(View.VISIBLE);
                        if (selectedId == R.id.radiomale) {

                            bmicms = kgscbmi(hinc, wlbs);
                            bmifeetstatus = staBMI(bmicms);
                            result.setVisibility(View.VISIBLE);
                            bmiStatus.setVisibility(View.VISIBLE);
                            result.setText("Your BMI is :" + String.format("%.2f", bmicms));
                            bmiStatus.setText(bmifeetstatus);
                        }

                        if (selectedId == R.id.radiofemale) {

                            bmicms = kgscbmi(hinc, wlbs);
                            bmicmsstatus = fstaBMI(bmicms);
                            result.setVisibility(View.VISIBLE);
                            bmiStatus.setVisibility(View.VISIBLE);
                            result.setText("Your BMI is :" + String.format("%.2f", bmicms));
                            bmiStatus.setText(bmicmsstatus);
                        }


                    }else{
                        Toast.makeText(getActivity(), "Please enter valid values", Toast.LENGTH_LONG).show();
                    }

                }else if(spintext.equals("Cm") && spintext_weight.equalsIgnoreCase("Lbs")){
                if((inches.getText().toString().trim().length() != 0)&& (lbs.getText().toString().trim().length() !=0)&&(!inches.getText().toString().equals("0")) && (!lbs.getText().toString().equals("0"))) {
                    hinc = Integer.parseInt(inches.getText().toString());
                    wlbs = Integer.parseInt(lbs.getText().toString());
                    displayclassification(selectedId);
                    bmi_classify_header.setVisibility(View.VISIBLE);
                    layout_bmiClassification.setVisibility(View.VISIBLE);
                    if (selectedId == R.id.radiomale) {

                        bmicms = cmslbsbmi(hinc, wlbs);
                        bmifeetstatus = staBMI(bmicms);
                        result.setVisibility(View.VISIBLE);
                        bmiStatus.setVisibility(View.VISIBLE);
                        result.setText("Your BMI is :" + String.format("%.2f", bmicms));
                        bmiStatus.setText(bmifeetstatus);
                    }

                    if (selectedId == R.id.radiofemale) {

                        bmicms = cmslbsbmi(hinc, wlbs);
                        bmicmsstatus = fstaBMI(bmicms);
                        result.setVisibility(View.VISIBLE);
                        bmiStatus.setVisibility(View.VISIBLE);
                        result.setText("Your BMI is :" + String.format("%.2f", bmicms));
                        bmiStatus.setText(bmicmsstatus);
                    }


                }else{
                    Toast.makeText(getActivity(), "Please enter valid values", Toast.LENGTH_LONG).show();
                }

            }
                break;
        }

    }


    private void displayclassification(int selectedId){

        radioSexButton = (RadioButton) myView.findViewById(selectedId);
        textView_underweight_value = (TextView) myView.findViewById(R.id.textView_underweight_value);
        textView_normal_value = (TextView) myView.findViewById(R.id.textView_normal_value);
        textView_overweight_value = (TextView) myView.findViewById(R.id.textView_overweight_value);
        textView_obese_value = (TextView) myView.findViewById(R.id.textView_obese_value);

        if (selectedId == R.id.radiomale) {
            layout_bmiClassification.setVisibility(View.VISIBLE);
            bmi_classify_header.setVisibility(View.VISIBLE);
            textView_underweight_value.setText(R.string.mUnderweight);
            textView_normal_value.setText(R.string.mNormal);
            textView_overweight_value.setText(R.string.mOverweight);
            textView_obese_value.setText(R.string.mObese);
        } else if (selectedId == R.id.radiofemale) {
            layout_bmiClassification.setVisibility(View.VISIBLE);
            bmi_classify_header.setVisibility(View.VISIBLE);
            textView_underweight_value.setText(R.string.fUnderweight);
            textView_normal_value.setText(R.string.fNormal);
            textView_overweight_value.setText(R.string.fOverweight);
            textView_obese_value.setText(R.string.fObese);
        }



    }

    private float lbsbmi(int feet, int inches, int lbs ) {
        int inc= (feet*12) + (inches);
        return (float) (703*lbs / (inc * inc));
    }
    private float ftkgsbmi(int feet, int inches, int lbs){
        int inc= (feet*12) + (inches);
        double convertedlbs=lbs * 2.2046;
        return (float) (703*convertedlbs / (inc * inc));
    }
    private float kgscbmi (float cms,  float kgs) {

        float meters = cms/100 ;
        return (float) ( kgs / (meters * meters));
    }
    private float cmslbsbmi (float cms,  float kgs) {

        float meters = cms/100 ;
        double convertedkg=kgs / 2.2046;
        return (float)( convertedkg / (meters * meters));
    }


    private String staBMI(float bmiValue)
    {
        bmiStatus=(TextView)myView.findViewById(R.id.bmiStatus);
        if (bmiValue < 18.5) {
            bmiStatus.setTextColor(getResources().getColor(R.color.orange));
            return "Underweight!";
        }
        else if (bmiValue < 25) {
            bmiStatus.setTextColor(getResources().getColor(R.color.green));
            return "Normal!";
        } else if (bmiValue < 30) {
            bmiStatus.setTextColor(getResources().getColor(R.color.orange1));
            return "Overweight!";
        } else {
            bmiStatus.setTextColor(getResources().getColor(R.color.red));
            return "Obese!";
        }

    }

    private String fstaBMI(float bmiValue)
    {
        if (bmiValue < 16.5) {
            bmiStatus.setTextColor(getResources().getColor(R.color.orange));
            return "Underweight!";
        }
        else if (bmiValue < 22) {
            bmiStatus.setTextColor(getResources().getColor(R.color.green));
            return "Normal!";

        } else if (bmiValue < 27) {
            bmiStatus.setTextColor(getResources().getColor(R.color.orange1));
            return "Overweight!";
        } else {
            bmiStatus.setTextColor(getResources().getColor(R.color.red));
            return "Obese!";
        }

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}

package com.example.android.dailycalories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private EditText mHeightText;
    private EditText mWeightText;
    private EditText mAge;
    private Button mButton;
    private RadioGroup mHeightRadio;
    private RadioGroup mWeightRadio;
    private RadioGroup mGenderRadio;
    private Spinner mSpinner;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeightText = (EditText)findViewById(R.id.height_text);
        mWeightText = (EditText)findViewById(R.id.weight_text);
        mAge = (EditText)findViewById(R.id.age);
        mButton = (Button)findViewById(R.id.button);
        mHeightRadio = (RadioGroup)findViewById(R.id.height_radio);
        mWeightRadio = (RadioGroup)findViewById(R.id.weight_radio);
        mGenderRadio = (RadioGroup)findViewById(R.id.gender_radio);
        mSpinner = (Spinner)findViewById(R.id.exercise_spinner);
        mResult = (TextView)findViewById(R.id.result);
        mButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(mHeightText.getText().length()<=0){
                    Toast.makeText(getBaseContext(), "please enter your height", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mWeightText.getText().length()<=0){
                    Toast.makeText(getBaseContext(), "please enter your weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mAge.getText().length()<=0){
                    Toast.makeText(getBaseContext(), "please enter your age", Toast.LENGTH_SHORT).show();
                    return;
                }
                //get inputs
                int age = Integer.parseInt(mAge.getText().toString());
                String heightt = ((RadioButton) findViewById(mHeightRadio.getCheckedRadioButtonId())).getText().toString();
                double height = Double.parseDouble(mHeightText.getText().toString());
                if(heightt.equals("cm")){
                    height = height * 0.393701;
                }
                String weightt = ((RadioButton) findViewById(mWeightRadio.getCheckedRadioButtonId())).getText().toString();
                double weight = Double.parseDouble(mWeightText.getText().toString());
                if(weightt.equals("kg")){
                    weight = weight * 2.20462;
                }
                String gender = ((RadioButton) findViewById(mGenderRadio.getCheckedRadioButtonId())).getText().toString();
                boolean isFemale = gender.equals("female");
                double exercise = 1.725;
                switch (mSpinner.getSelectedItemPosition()){
                    case 0: exercise=1.2;break;
                    case 1: exercise=1.375;break;
                    case 2: exercise=1.55;break;
                }
                // calculate daily calories
                double dailyCalories;
                if(isFemale){
//                    655 + (4.35 x weight in pounds) + (4.7 x height in inches) – (4.7 x age in years)
                    dailyCalories = 655+(4.35*weight)+(4.7*height)-(4.7*age);
                    dailyCalories = dailyCalories*exercise;
                } else {
//                    66 + (6.23 x weight in pounds) + (12.7 x height in inches) – (6.8 x age in years)
                    dailyCalories = 66+(6.23*weight)+(12.7*height)-(6.8*age);
                    dailyCalories = dailyCalories*exercise;
                }
                long x = Math.round(dailyCalories);
                mResult.setText("you need "+x+" calories to maintain your weight. \n"+
                        "to lose weight, you need "+(x-500)+" calories. \n"+
                        "if you are underweight, you need "+(x+500)+" calories");
            }
        });
    }
}

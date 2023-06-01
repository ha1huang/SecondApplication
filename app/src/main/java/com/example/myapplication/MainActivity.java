package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.inline.InlineContentView;

import com.example.myview.StepNumView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //    private Button btnOne;
//    private TextView title, content;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        btnOne = (Button) findViewById(R.id.button);
//        btnOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast();
//            }
//        });
//    }
//    private void showToast() {
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.toast, null);
//        title = (TextView) view.findViewById(R.id.title);
//        content = (TextView) view.findViewById(R.id.content);
//        title.setText("自定义toast");
//        content.setText("这里可以写你喜欢的东西");
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(view);
//        toast.show();
//    }
    private StepNumView stepNumView;
    private SensorManager sensorManager;
    private Button btnOne;
    private EditText editText;
    private String target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepNumView = findViewById(R.id.step_view);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepSensor != null) {
            sensorManager.registerListener((SensorEventListener) this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        btnOne = (Button) findViewById(R.id.button);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTargetNumb();
            }
        });

        EditText editText = findViewById(R.id.edit_target);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 在用户输入时检测文本框中输入的内容
                String text = s.toString();
                target = text;
//                text = text.replace("\n", "");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private void setTargetNumb() {
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.activity_main, null);
//        editText = (EditText) view.findViewById(R.id.edit_target);
        stepNumView.setTarget(Integer.parseInt(target));
        stepNumView.invalidate();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float stepCount = event.values[0];
        int steps = (int) stepCount;
        stepNumView.setStepNum(steps);
        stepNumView.invalidate();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener((SensorEventListener) this);
    }
}
package se.stephenro.luxi;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.grantland.widget.AutofitTextView;

/**
 * Created by Anthony on 11/5/2015.
 * Speed programing test!
 */
public class LightSensorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LightSensorActivity.class.getSimpleName();

    String maxPrefix = "Max: ";
    String meanPrefix = "Mean: ";
    String minPrefix = "Min: ";

    @Bind(R.id.lightSensorValue) AutofitTextView luxValue;
    @Bind(R.id.lightSensorMax) TextView luxMax;
    @Bind(R.id.lightSensorMean) TextView luxMean;
    @Bind(R.id.lightSensorMin) TextView luxMin;

    @Bind(R.id.lightSensorButton) Button resetButton;

    // Light Sensor
    SensorManager sensorManager;
    Sensor luxSensor;
    SensorEventListener luxSensorListener;
    SummaryStatistics summaryStatistics = new SummaryStatistics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightsensor);
        ButterKnife.bind(this);

        resetButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        monitorLuxLevels();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(luxSensorListener);
    }

    /**
     * This Monitors the lux levels
     * TODO make this an ASync task
     */
    private void monitorLuxLevels() {
        sensorManager = (SensorManager) getApplicationContext().getSystemService(SENSOR_SERVICE);
        luxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        luxSensorListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                int lux = ((int) event.values[0]);
                luxValue.setText(String.valueOf(lux));

                summaryStatistics.addValue(lux);

                luxMax.setText(maxPrefix + ((int) summaryStatistics.getMax()));
                luxMean.setText(meanPrefix + ((int) summaryStatistics.getMean()));
                luxMin.setText(minPrefix + ((int) summaryStatistics.getMin()));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        };

        sensorManager.registerListener(
                luxSensorListener,
                luxSensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lightSensorButton:
                summaryStatistics.clear();
                break;
        }
    }
}

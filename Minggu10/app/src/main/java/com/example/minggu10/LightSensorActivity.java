package com.example.minggu10;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;

public class LightSensorActivity extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView txtBrignessInfo;
    private MediaPlayer mPlayer;
    private GraphView mGraphLight;
    private LineGraphSeries<DataPoint> mSeriesLight;
    private double graphLastAccelValue = 5d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.hide();
        setContentView(R.layout.activity_light_sensor);
        txtBrignessInfo = findViewById(R.id.txtValuesLightSensor);
//        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        if (lightSensor == null) {
//            txtBrignessInfo.setText("Sensor not detected!");
//        } else {
//            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
//        }

        mGraphLight = initGraphView(R.id.graph_light, "Sensor of Light");
        mSeriesLight = initSeries(Color.RED, "Lux");
        mGraphLight.addSeries(mSeriesLight);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

            if (lightSensor != null) {
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        } else {
            Toast.makeText(this, "Sensor isn't Detected!", Toast.LENGTH_SHORT).show();
        }
    }

    TextView txtValue;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
//            txtValue = (TextView) findViewById(R.id.txtValuesLightSensor);
//            txtValue.setText("" + sensorEvent.values[0]);
//        }

        int sensorType = sensorEvent.sensor.getType();

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                txtBrignessInfo.setText(getResources().getString(R.string.label_brightness, sensorEvent.values[0]));

                graphLastAccelValue += 0.15d;
                mSeriesLight.appendData(new DataPoint(graphLastAccelValue, sensorEvent.values[0]), true, 33);
                if (sensorEvent.values[0] == 0) {
                    mPlayer = new MediaPlayer();
                    try {
                        AssetFileDescriptor as = this.getAssets().openFd("cahaya_gelap.mp3");
                        mPlayer.setDataSource(as.getFileDescriptor(), as.getStartOffset(), as.getLength());
                        as.close();
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "error at playing sound text!", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(this, "Cahaya Gelap", Toast.LENGTH_SHORT).show();
                } else if (sensorEvent.values[0] > 150) {
                    mPlayer = new MediaPlayer();
                    try {
                        AssetFileDescriptor as = this.getAssets().openFd("cahaya_terang.mp3");
                        mPlayer.setDataSource(as.getFileDescriptor(), as.getStartOffset(), as.getLength());
                        as.close();
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(this, "Cahaya Terang!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sensorManager.unregisterListener(this, lightSensor);
    }

    public GraphView initGraphView(int id, String title) {
        GraphView graph = (GraphView) findViewById(id);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMinY(5);
        graph.getGridLabelRenderer().setLabelVerticalWidth(100);
        graph.setTitle(title);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        return graph;
    }

    public LineGraphSeries<DataPoint> initSeries(int color, String title) {
        LineGraphSeries<DataPoint> series;
        series = new LineGraphSeries<>();
        series.setDrawDataPoints(true);
        series.setDrawBackground(true);
        series.setColor(color);
        series.setTitle(title);
        series.setBackgroundColor(Color.argb(100, 204, 119, 119));
        return series;
    }
}
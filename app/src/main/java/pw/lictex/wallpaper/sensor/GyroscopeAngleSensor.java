package pw.lictex.wallpaper.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by kpx on 1.7-2018.
 */

public class GyroscopeAngleSensor extends AngleSensor {
    public GyroscopeAngleSensor(SensorManager manager) {
        super(manager);
    }

    @Override
    public SensorEventListener[] initSensorListeners() {
        return new SensorEventListener[]{new SensorEventListener() {
            long time = 0;

            @Override
            public void onSensorChanged(SensorEvent event) {
                long timeElps = System.currentTimeMillis() - time;
                time = System.currentTimeMillis();
                float x = event.values[0];
                float y = event.values[1];//横屏好像就不太对了
                float z = event.values[2];
                if (Math.abs(x) < 0.1)
                    x = 0;//大概能避免漂移吧...
                if (Math.abs(y) < 0.1)
                    y = 0;
                if (Math.abs(z) < 0.1)
                    z = 0;
                float v = (timeElps > 100 ? 100 : timeElps) / 1000f;
                float angleX = (float) Math.toDegrees(x) * v;
                float angleY = (float) Math.toDegrees(y) * v;
                float angleZ = (float) Math.toDegrees(z) * v;
                onResult(angleX, angleY, angleZ);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }};
    }

    @Override
    public Sensor[] initSensors(SensorManager manager) {
        return new Sensor[]{manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)};
    }
}

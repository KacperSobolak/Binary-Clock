package com.example.kacper.binary_clock;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import android.os.Handler;
import android.util.Log;
import java.util.List;
import java.io.IOException;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    boolean a = false;
    boolean b = true;

    Gpio b1;
    Gpio b2;
    Gpio b4;
    Gpio b8;
    Gpio b16;
    Gpio b32;
    //If Gpio h_m is on
    //-clock show hour
    //else
    //clock show minutes
    Gpio h_m;
    boolean h;
    int hour = 13;
    int minutes = 13;

    Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try {
            h = false;
            PeripheralManagerService service = new PeripheralManagerService();
            b1 = service.openGpio("GPIO_32");
            b2 = service.openGpio("GPIO_37");
            b4 = service.openGpio("GPIO_35");
            b8 = service.openGpio("GPIO_33");
            b16 = service.openGpio("GPIO_10");
            b32 = service.openGpio("GPIO_128");
            h_m = service.openGpio("GPIO_175");
            b32.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            b16.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            b8.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            b4.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            b2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            b1.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            h_m.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            handler.post(Clock);
        }
        catch (IOException e){
            Log.e(TAG, "Error on PeripheralIO API", e);
        }
    }

    private Runnable Clock = new Runnable() {
        @Override
        public void run() {
            try{
                b1.setValue(a);
                b2.setValue(a);
                b4.setValue(a);
                b8.setValue(a);
                b16.setValue(a);
                b32.setValue(a);
                h_m.setValue(h);
                int number;
                if (h == false){
                    number = minutes;
                    if (number - 32 >= 0){
                        b32.setValue(true);
                        number = number - 32;
                    }
                    if (number - 16 >= 0){
                        b16.setValue(true);
                        number = number - 16;
                    }
                    if (number - 8 >= 0){
                        b8.setValue(true);
                        number = number - 8;
                    }
                    if (number - 4 >= 0){
                        b4.setValue(true);
                        number = number - 4;
                    }
                    if (number - 2 >= 0){
                        b2.setValue(true);
                        number = number - 2;
                    }
                    if (number - 1 >= 0){
                        b1.setValue(true);
                        number = number - 1;
                    }
                }
                else if (h){
                    number = hour;
                    if (number - 16 >= 0){
                        b16.setValue(true);
                        number = number - 16;
                    }
                    if (number - 8 >= 0){
                        b8.setValue(true);
                        number = number - 8;
                    }
                    if (number - 4 >= 0){
                        b4.setValue(true);
                        number = number - 4;
                    }
                    if (number - 2 >= 0){
                        b2.setValue(true);
                        number = number - 2;
                    }
                    if (number - 1 >= 0){
                        b1.setValue(true);
                        number = number - 1;
                    }
                }
                handler.postDelayed(Clock, 1000);
            }catch (IOException e){
                Log.e(TAG, "Error on PeripheralIO API", e);
            }

        }
    };
}
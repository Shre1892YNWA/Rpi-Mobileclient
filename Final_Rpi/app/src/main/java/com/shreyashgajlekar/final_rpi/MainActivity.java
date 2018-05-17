package com.shreyashgajlekar.final_rpi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private static final String TAG = "MainActivity";
    private static final int blink = 1000;
    private static final String Gpio11 = "BCM4";
    private static final String Gpio12 = "BCM17";
    private static final String Gpio21 = "BCM27";
    private static final String Gpio22 = "BCM22";
    private static final String Gpio31 = "BCM5";
    private static final String Gpio32 = "BCM6";
    private static final String Gpio41 = "BCM26";
    private static final String Gpio42 = "BCM16";
    private static final String ECHO_PIN_NAME = "BCM20";
    private static final String TRIGGER_PIN_NAME = "BCM21";

    private Handler mCallbackHandler;
    private Handler ultrasonicTriggerHandler;

    private static final int INTERVAL_BETWEEN_TRIGGERS = 300;
    private Runnable triggerRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                readDistanceAsnyc();
                ultrasonicTriggerHandler.postDelayed(triggerRunnable, INTERVAL_BETWEEN_TRIGGERS);
            } catch (IOException e) {
                Log.e(TAG, "Error on PeripheralIO API", e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Gpio mEcho;
    private Gpio mTrigger;
    private Gpio gpio11;
    private Gpio gpio12;
    private Gpio gpio21;
    private Gpio gpio22;
    private Gpio gpio31;
    private Gpio gpio32;
    private Gpio gpio41;
    private Gpio gpio42;
    private Handler mHandler = new Handler();
    private DatabaseReference databaseReference11 = FirebaseDatabase.getInstance().getReference().child("M11");
    private DatabaseReference databaseReference12 = FirebaseDatabase.getInstance().getReference().child("M12");
    private DatabaseReference databaseReference21 = FirebaseDatabase.getInstance().getReference().child("M21");
    private DatabaseReference databaseReference22 = FirebaseDatabase.getInstance().getReference().child("M22");
    private DatabaseReference databaseReference31 = FirebaseDatabase.getInstance().getReference().child("M31");
    private DatabaseReference databaseReference32 = FirebaseDatabase.getInstance().getReference().child("M32");
    private DatabaseReference databaseReference41 = FirebaseDatabase.getInstance().getReference().child("M41");
    private DatabaseReference databaseReference42 = FirebaseDatabase.getInstance().getReference().child("M42");
    private DatabaseReference databaseReference43 = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    PeripheralManager manager = PeripheralManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase.goOnline();
        // Prepare handler for GPIO callback
        HandlerThread handlerThread = new HandlerThread("callbackHandlerThread");
        handlerThread.start();
        mCallbackHandler = new Handler(handlerThread.getLooper());

        // Prepare handler to send triggers
        HandlerThread triggerHandlerThread = new HandlerThread("triggerHandlerThread");
        triggerHandlerThread.start();
        ultrasonicTriggerHandler = new Handler(triggerHandlerThread.getLooper());

        try {
            // Step 1. Create GPIO connection.
            mEcho = manager.openGpio(ECHO_PIN_NAME);
            // Step 2. Configure as an input.
            mEcho.setDirection(Gpio.DIRECTION_IN);
            // Step 3. Enable edge trigger events.
            mEcho.setEdgeTriggerType(Gpio.EDGE_BOTH);
            // Step 4. Set Active type to HIGH, then it will trigger TRUE (HIGH, active) events
            mEcho.setActiveType(Gpio.ACTIVE_HIGH);
            // Step 5. Register an event callback.
            mEcho.registerGpioCallback(mCallbackHandler, mCallback);
        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }

        try {
            // Step 1. Create GPIO connection.
            mTrigger = manager.openGpio(TRIGGER_PIN_NAME);

            // Step 2. Configure as an output with default LOW (false) value.
            mTrigger.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

        } catch (IOException e) {
            Log.e(TAG, "Error on PeripheralIO API", e);
        }

        ultrasonicTriggerHandler.post(triggerRunnable);
        try{
            gpio11 = manager.openGpio(Gpio11);
            gpio11.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mHandler.post(mBlinkRunnable11);
        } catch (IOException e) {
            Log.e(TAG,"Error on PeripheralIO API",e);
        }
        try{
            gpio12 = manager.openGpio(Gpio12);
            gpio12.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mHandler.post(mBlinkRunnable12);
        } catch (IOException e) {
            Log.e(TAG,"Error on PeripheralIO API",e);
        }
        try{
            gpio21 = manager.openGpio(Gpio21);
            gpio21.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mHandler.post(mBlinkRunnable21);
        } catch (IOException e) {
            Log.e(TAG,"Error on PeripheralIO API",e);
        }
        try{
            gpio22 = manager.openGpio(Gpio22);
            gpio22.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mHandler.post(mBlinkRunnable22);
        } catch (IOException e) {
            Log.e(TAG,"Error on PeripheralIO API",e);
        }
        try{
            gpio31 = manager.openGpio(Gpio31);
            gpio31.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mHandler.post(mBlinkRunnable31);
        } catch (IOException e) {
            Log.e(TAG,"Error on PeripheralIO API",e);
        }
        try{
            gpio32 = manager.openGpio(Gpio32);
            gpio32.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mHandler.post(mBlinkRunnable32);
        } catch (IOException e) {
            Log.e(TAG,"Error on PeripheralIO API",e);
        }
        try{
            gpio41 = manager.openGpio(Gpio41);
            gpio41.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mHandler.post(mBlinkRunnable41);
        } catch (IOException e) {
            Log.e(TAG,"Error on PeripheralIO API",e);
        }
        try{
            gpio42 = manager.openGpio(Gpio42);
            gpio42.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            mHandler.post(mBlinkRunnable42);
        } catch (IOException e) {
            Log.e(TAG,"Error on PeripheralIO API",e);
        }
    }
    long time1, time2;

    protected void readDistanceAsnyc() throws IOException, InterruptedException {
        // Just to be sure, set the trigger first to false
        mTrigger.setValue(false);
        Thread.sleep(0,2000);

        // Hold the trigger pin high for at least 10 us
        mTrigger.setValue(true);
        Thread.sleep(0,10000); //10 microsec

        // Reset the trigger pin
        mTrigger.setValue(false);

    }


    // Step 5. Register an event callback.
    private GpioCallback mCallback = new GpioCallback() {
        @Override
        public boolean onGpioEdge(Gpio gpio) {
            try {

                if (!gpio.getValue()){
                    // The end of the pulse on the ECHO pin

                    time2 = System.nanoTime();

                    long pluseWidth = time2 - time1;
                    Log.d(TAG, "pluseWidth: " + pluseWidth);
                    double distance = (pluseWidth / 1000000000.0) * 340.0 / 2.0 * 100.0;
                    databaseReference43.child("dis").setValue(distance);
                    //double distance = (pluseWidth / 1000.0 ) / 58.23 ; //cm
                    Log.i(TAG, "distance: " + distance + " cm");
                    if (distance<10)
                    {
                        databaseReference11.child("M11").setValue("False");
                        databaseReference12.child("M12").setValue("False");
                        databaseReference21.child("M21").setValue("False");
                        databaseReference22.child("M22").setValue("False");
                        databaseReference31.child("M31").setValue("False");
                        databaseReference32.child("M32").setValue("False");
                        databaseReference41.child("M41").setValue("False");
                        databaseReference42.child("M42").setValue("False");
                    }

                    //Log.i(TAG, "Echo ENDED!");

                } else {
                    // The pulse arrived on ECHO pin
                    time1 = System.nanoTime();

                    //2nd try
                    //mHandler.sendEmptyMessage(1);
                    //Log.i(TAG, "Echo ARRIVED!");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Step 6. Return true to keep callback active.
            return true;
        }


    };



    @Override
    protected void onDestroy(){
        super.onDestroy();
        firebaseDatabase.goOffline();
        if(gpio11!=null){
            try{
                gpio11.close();
            }catch (IOException e){
                Log.e(TAG,"Error on PeripheralIO API",e);
            }
        }
        if(gpio12!=null){
            try{
                gpio12.close();
            }catch (IOException e){
                Log.e(TAG,"Error on PeripheralIO API",e);
            }
        }
        if(gpio21!=null){
            try{
                gpio21.close();
            }catch (IOException e){
                Log.e(TAG,"Error on PeripheralIO API",e);
            }
        }
        if(gpio22!=null){
            try{
                gpio22.close();
            }catch (IOException e){
                Log.e(TAG,"Error on PeripheralIO API",e);
            }
        }
        if(gpio31!=null){
            try{
                gpio31.close();
            }catch (IOException e){
                Log.e(TAG,"Error on PeripheralIO API",e);
            }
        }
        if(gpio32!=null){
            try{
                gpio32.close();
            }catch (IOException e){
                Log.e(TAG,"Error on PeripheralIO API",e);
            }
        }
        if(gpio41!=null){
            try{
                gpio41.close();
            }catch (IOException e){
                Log.e(TAG,"Error on PeripheralIO API",e);
            }
        }
        if(gpio42!=null){
            try{
                gpio42.close();
            }catch (IOException e){
                Log.e(TAG,"Error on PeripheralIO API",e);
            }
        }
    }

    private Runnable mBlinkRunnable11 = new Runnable() {
        @Override
        public void run() {
          if(gpio11 == null){
              return;
          }
            ValueEventListener ledlistener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String databaseValue = dataSnapshot.getValue().toString();
                    if(databaseValue.equals("M11=True")) {
                        try {
                            gpio11.setValue(gpio11.getValue());
                        } catch (IOException ioe) {
                            Log.e(TAG, "Error on peripheral IO API", ioe);
                        }
                    }
                        else{
                            try {
                                gpio11.setValue(!gpio11.getValue());
                            } catch (IOException ioe){
                                Log.e(TAG, "Error on Peripheral IO API", ioe);
                            }
                        }
                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
          databaseReference11.addValueEventListener(ledlistener);
        }
    };
    private Runnable mBlinkRunnable12 = new Runnable() {
        @Override
        public void run() {
            if(gpio12 == null){
                return;
            }
            ValueEventListener ledlistener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String databaseValue = dataSnapshot.getValue().toString();
                    if(databaseValue.equals("M12=True")) {
                        try {
                            gpio12.setValue(gpio12.getValue());
                        } catch (IOException ioe) {
                            Log.e(TAG, "Error on peripheral IO API", ioe);
                        }
                    }
                    else{
                        try {
                            gpio12.setValue(!gpio12.getValue());
                        } catch (IOException ioe){
                            Log.e(TAG, "Error on Peripheral IO API", ioe);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference12.addValueEventListener(ledlistener);
        }
    };
    private Runnable mBlinkRunnable21 = new Runnable() {
        @Override
        public void run() {
            if(gpio21 == null){
                return;
            }
            ValueEventListener ledlistener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String databaseValue = dataSnapshot.getValue().toString();
                    if(databaseValue.equals("M21=True")) {
                        try {
                            gpio21.setValue(gpio21.getValue());
                        } catch (IOException ioe) {
                            Log.e(TAG, "Error on peripheral IO API", ioe);
                        }
                    }
                    else{
                        try {
                            gpio21.setValue(!gpio21.getValue());
                        } catch (IOException ioe){
                            Log.e(TAG, "Error on Peripheral IO API", ioe);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference21.addValueEventListener(ledlistener);
        }
    };
    private Runnable mBlinkRunnable22 = new Runnable() {
        @Override
        public void run() {
            if(gpio22 == null){
                return;
            }
            ValueEventListener ledlistener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String databaseValue = dataSnapshot.getValue().toString();
                    if(databaseValue.equals("M22=True")) {
                        try {
                            gpio22.setValue(gpio22.getValue());
                        } catch (IOException ioe) {
                            Log.e(TAG, "Error on peripheral IO API", ioe);
                        }
                    }
                    else{
                        try {
                            gpio22.setValue(!gpio22.getValue());
                        } catch (IOException ioe){
                            Log.e(TAG, "Error on Peripheral IO API", ioe);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference22.addValueEventListener(ledlistener);
        }
    };
    private Runnable mBlinkRunnable31 = new Runnable() {
        @Override
        public void run() {
            if(gpio31 == null){
                return;
            }
            ValueEventListener ledlistener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String databaseValue = dataSnapshot.getValue().toString();
                    if(databaseValue.equals("M31=True")) {
                        try {
                            gpio31.setValue(gpio31.getValue());
                        } catch (IOException ioe) {
                            Log.e(TAG, "Error on peripheral IO API", ioe);
                        }
                    }
                    else{
                        try {
                            gpio31.setValue(!gpio31.getValue());
                        } catch (IOException ioe){
                            Log.e(TAG, "Error on Peripheral IO API", ioe);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference31.addValueEventListener(ledlistener);
        }
    };
    private Runnable mBlinkRunnable32 = new Runnable() {
        @Override
        public void run() {
            if(gpio32 == null){
                return;
            }
            ValueEventListener ledlistener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String databaseValue = dataSnapshot.getValue().toString();
                    if(databaseValue.equals("M32=True")) {
                        try {
                            gpio32.setValue(gpio32.getValue());
                        } catch (IOException ioe) {
                            Log.e(TAG, "Error on peripheral IO API", ioe);
                        }
                    }
                    else{
                        try {
                            gpio32.setValue(!gpio32.getValue());
                        } catch (IOException ioe){
                            Log.e(TAG, "Error on Peripheral IO API", ioe);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference32.addValueEventListener(ledlistener);
        }

    };
    private Runnable mBlinkRunnable41 = new Runnable() {
        @Override
        public void run() {
            if(gpio41 == null){
                return;
            }
            ValueEventListener ledlistener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String databaseValue = dataSnapshot.getValue().toString();
                    if(databaseValue.equals("M41=True")) {
                        try {
                            gpio41.setValue(gpio41.getValue());
                        } catch (IOException ioe) {
                            Log.e(TAG, "Error on peripheral IO API", ioe);
                        }
                    }
                    else{
                        try {
                            gpio41.setValue(!gpio41.getValue());
                        } catch (IOException ioe){
                            Log.e(TAG, "Error on Peripheral IO API", ioe);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference41.addValueEventListener(ledlistener);
        }

    };
    private Runnable mBlinkRunnable42 = new Runnable() {
        @Override
        public void run() {
            if(gpio42 == null){
                return;
            }
            ValueEventListener ledlistener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String databaseValue = dataSnapshot.getValue().toString();
                    if(databaseValue.equals("M42=True")) {
                        try {
                            gpio42.setValue(gpio42.getValue());
                        } catch (IOException ioe) {
                            Log.e(TAG, "Error on peripheral IO API", ioe);
                        }
                    }
                    else{
                        try {
                            gpio42.setValue(!gpio42.getValue());
                        } catch (IOException ioe){
                            Log.e(TAG, "Error on Peripheral IO API", ioe);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            databaseReference42.addValueEventListener(ledlistener);
        }
    };
}

package gig.com.andcoincap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import gig.com.andcoincap.base.BaseActivity;

public class Splash extends BaseActivity {

    Timer timer = new Timer();
    TimerTask splashit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /**
         * The reason timer is used is to avoid the app to freeze
         * which might cause crash. Timer is asynchronous so we fluid
         * */
        timer.scheduleAtFixedRate(new TimerTask() {
            int current = 0 ;
            @Override
            public void run() {
                if (current == 0) { current = 1 ; } if (current == 1) { current = 2 ;
                } else if (current == 2) {
                    Intent i=new Intent(Splash.this,  MainActivity.class);
                    startActivity(i); finish(); timer.cancel();
                }
            }
        }, 3000, 3000);

    }
}

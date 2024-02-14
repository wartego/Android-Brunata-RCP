package pl.sda.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.Objects;

public class LogoActivity extends AppCompatActivity {
    private boolean keep = true;
    private final int DELAY = 750;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SplashScreen splashScreen = SplashScreen.installSplashScreen(LogoActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        //Objects.requireNonNull(getSupportActionBar()).hide();


       // splashScreen.setKeepOnScreenCondition(() -> keep);
        //Handler handler = new Handler();
       // handler.postDelayed(() -> keep = false, DELAY);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent logoIntent = new Intent(LogoActivity.this, MainActivity.class);
                startActivity(logoIntent);
                finish();
            }
        },3000);
    }
}
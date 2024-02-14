package pl.sda.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import pl.sda.myapplication.databinding.ActivityMainBinding;
import pl.sda.myapplication.ui.home.sendRequest.HttpSendRequest;
import pl.sda.myapplication.ui.home.sendRequest.LoginAndPassword;
import pl.sda.myapplication.ui.home.sendRequest.TypeEnum;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private String login, password, ip;

    Button sendButton;
    TextView currentlogin , currentPassword, currentIP;
    TextView textView;

    private RadioButton selectedRadioButton;
    private RadioGroup radioGroup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //






        //
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        getPreferencedFromSettingsLogin();
        //
        updateProperties();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupID);



    }

    public void getPreferencedFromSettingsLogin() {
        //SharedPreferences
        SharedPreferences myPreferences = getSharedPreferences(LoginActivity.SHARED_PREFS,MODE_PRIVATE);
        login = myPreferences.getString(LoginActivity.LOGIN,"");
        password = myPreferences.getString(LoginActivity.PASSWORD,"");
        ip = myPreferences.getString(LoginActivity.IP_ADDRESS,"");

//        Log.i("MY GAT", "login: " + login);
//        Log.i("MY GAT", "Password: "  + password);
//        Log.i("MY GAT", "Password: "  + ip);


        //
        currentlogin = findViewById(R.id.textLoginCurrent);
        currentPassword = findViewById(R.id.textPasswordCurrent);
        currentIP = findViewById(R.id.textIPCurrent);

        currentlogin.setText(login);
        currentPassword.setText(password);
        currentIP.setText(ip);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void onLoginSettingClick(MenuItem item){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    @SuppressLint("SetTextI18n")
    public void actionOnSendButton(View view) {
        sendButton = (Button) findViewById(R.id.button3);

        textView = (TextView) findViewById(R.id.textViewResponse);




        sendButton.setOnClickListener(v -> {
            int selectedIdRadio = radioGroup.getCheckedRadioButtonId();
            selectedRadioButton = (RadioButton) findViewById(selectedIdRadio);

            TypeEnum selectedRadioText = TypeEnum.valueOf(selectedRadioButton.getText().toString());

            LoginAndPassword loginAndPassword = new LoginAndPassword(login,password, ip, selectedRadioText);
            String respondFromPost;
//            HttpSendRequest httpSendRequest = new HttpSendRequest();
//            httpSendRequest.setLoginAndPassword(loginAndPassword);
         //   setContentView(R.layout.fragment_gallery);
            // httpSendRequest.getSendRequest();

            Callable<String> returnPost = new HttpSendRequest(loginAndPassword);
            FutureTask<String> futureTask = new FutureTask<>(returnPost);
            Thread thread = new Thread(futureTask);
            thread.start();


            waitingForRespondFromRCP(futureTask);


//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }, 1000);

        });
    }

    private void waitingForRespondFromRCP(FutureTask<String> futureTask) {
        Thread thread = new Thread() {

            @Override
            public void run() {
//                while (!isInterrupted()) {
//                   // Thread.sleep(5000);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String respondFromPost;
//                            try {
//                              //  Thread.sleep(5000);
//                                respondFromPost = futureTask.get();
//                                Log.i("New tag", respondFromPost);
//                                textView.setText(respondFromPost);
//                            } catch (InterruptedException | ExecutionException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    });
//                }

                
                long start = System.currentTimeMillis();
                long end = start + 15 * 1000;
                while (System.currentTimeMillis() < end){
                    Optional<String> respondFromPost;
                    //  Thread.sleep(5000);opt
                    try {
                        Log.i("Waiting for repspond" , "Started waiting for repond");
                        respondFromPost  = Optional.ofNullable(futureTask.get());
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Log.i("New tag", respondFromPost.orElse("No respond"));
                    textView.setText(respondFromPost.orElse("No respond from server"));

                }
                Log.i("OUT OF RESPOND" , "NOT RESPOND OR RECEIVED RESPOND");

            }
        };

        thread.start();

    }

    public void updateProperties(){

       Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getPreferencedFromSettingsLogin();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
    }



}
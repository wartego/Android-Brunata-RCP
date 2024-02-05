package pl.sda.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import pl.sda.myapplication.databinding.ActivityMainBinding;
import pl.sda.myapplication.ui.home.sendRequest.HttpSendRequest;
import pl.sda.myapplication.ui.home.sendRequest.LoginAndPassword;
import pl.sda.myapplication.ui.home.sendRequest.TypeEnum;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    Button sendButton;
    EditText login;
    EditText password;
    String loginText;
    String passwordText;

    TextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
    public void actionnnn(View view) {
        sendButton = (Button) findViewById(R.id.button3);

        textView = (TextView) findViewById(R.id.textViewResponse);



        LoginAndPassword loginAndPassword = new LoginAndPassword("Tomasz.Rochala","Tomasz.Rochala", TypeEnum.LOGOUT);


        sendButton.setOnClickListener(v -> {
            String respondFromPost;
//            HttpSendRequest httpSendRequest = new HttpSendRequest();
//            httpSendRequest.setLoginAndPassword(loginAndPassword);
         //   setContentView(R.layout.fragment_gallery);
            // httpSendRequest.getSendRequest();

            Callable<String> returnPost = new HttpSendRequest(loginAndPassword);
            FutureTask<String> futureTask = new FutureTask<>(returnPost);
            Thread thread = new Thread(futureTask);
            thread.start();


            try {
                Thread.sleep(5000);
                respondFromPost = futureTask.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            Log.i("New tag", respondFromPost);
                textView.setText(respondFromPost);



//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }, 1000);

        });
    }



}
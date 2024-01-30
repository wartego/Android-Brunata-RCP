package pl.sda.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import java.io.IOException;

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

    public void actionnnn(View view) {
        final String[] respondFromPost = new String[1];
        sendButton = (Button) findViewById(R.id.button3);
        login = (EditText) findViewById(R.id.textLogin);
        password = (EditText) findViewById(R.id.textPassword);

        LoginAndPassword loginAndPassword = new LoginAndPassword("Tomasz.Rochala","Tomasz.Rochala", TypeEnum.LOGIN);

        sendButton.setOnClickListener(v -> {
            HttpSendRequest httpSendRequest = new HttpSendRequest();
            //setContentView(R.layout.fragment_gallery);
            try {
               // httpSendRequest.getSendRequest();
                respondFromPost[0] = httpSendRequest.postSendRequest(loginAndPassword);

                textView = (TextView) findViewById(R.id.textViewResponse);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(10000);

                Log.i("New tag", respondFromPost[0]);
                textView.setText( respondFromPost[0]);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }, 1000);

        });
    }
}
package pl.sda.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
        private Button buttonSave, buttonCancel;
        private EditText textLogin, textPassword, textIP;
        public static final String SHARED_PREFS = "sharedPrefs";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String IP_ADDRESS = "ip";

        public String loginFromFile , passwordFromFile, ipFromFile;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            textLogin = (EditText) findViewById(R.id.textLogin);
            textPassword = (EditText) findViewById(R.id.textPassword);
            textIP = (EditText) findViewById(R.id.textIP);
            buttonSave = (Button) findViewById(R.id.buttonSave);
            buttonCancel = (Button) findViewById(R.id.buttonCancel);

            buttonSave.setOnClickListener(v -> {
                Log.i("My tag","TRY TO SAVE");
                saveDataToFile();
            });

            loadData();
            updateViews();


        }

        private void saveDataToFile() {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LOGIN, textLogin.getText().toString());
            editor.putString(PASSWORD, textPassword.getText().toString());
            editor.putString(IP_ADDRESS, textIP.getText().toString());
            editor.apply();


            setMainView();
        }

    public void loadData(){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
            loginFromFile = sharedPreferences.getString(LOGIN,"");
            passwordFromFile = sharedPreferences.getString(PASSWORD,"");
            ipFromFile = sharedPreferences.getString(IP_ADDRESS,"");
        }

        public void updateViews(){
            textLogin.setText(loginFromFile);
            textPassword.setText(passwordFromFile);
            textIP.setText(ipFromFile);
        }


    public void actionCancelButton(View view) {
       setMainView();
    }

    private void setMainView() {
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(myIntent);
    }
}

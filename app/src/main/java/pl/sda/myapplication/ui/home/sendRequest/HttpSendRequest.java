package pl.sda.myapplication.ui.home.sendRequest;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;


public class HttpSendRequest implements Callable {

    private String responseText;
    private final LoginAndPassword loginAndPassword;

    public HttpSendRequest(LoginAndPassword loginAndPassword) {
        this.loginAndPassword = loginAndPassword;
    }
//    public void getSendRequest() throws IOException {
//
//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    try{
//                        System.out.println("hidsds");
//                        URL url = new URL("http://141.144.226.27:5555/api/login");
//                        // final String URL = ;
//                        HttpURLConnection client = null;
//
//                        client = (HttpURLConnection) url.openConnection();
//                        System.out.println("sended request");
//                        int code = client.getResponseCode();
//                        if (code !=  202) {
//                            throw new IOException("Invalid response from server: " + code);
//                        } else {
//                            String value = client.getResponseMessage();
//                        }
//
//
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//
//    }

 /// public String postSendRequest(LoginAndPassword loginAndPassword) throws IOException {


//       Thread thread = new Thread(() -> {
//            try {
//                try{
//                    System.out.println("hidsds");
//                    URL url = new URL("http://192.168.1.5:5555/api/login");
//                    // final String URL = ;
//                    HttpURLConnection client = null;
//
//                    client = (HttpURLConnection) url.openConnection();
//                    client.setRequestMethod("POST");
//                    client.setRequestProperty("Accept", "application/json");
//                    client.setRequestProperty("Content-Type", "application/json");
//
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("login", loginAndPassword.getLogin());
//                    jsonObject.put("password",loginAndPassword.getPassword());
//                    jsonObject.put("typeEnum","LOGIN");
//
//                    OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream());
//                    os.write(jsonObject.toString());
//                    os.flush();
//                    System.out.println(jsonObject);
//
//                    System.out.println("sended request");
//                    int code = client.getResponseCode();
//                    if (code !=  202) {
//                        throw new IOException("Invalid response from server: " + code);
//                    } else {
//                        //String value = client.getResponseMessage();
//                        // read the response
//                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                        String inputLine;
//                        StringBuilder response = new StringBuilder();
//
//                        while ((inputLine = in.readLine()) != null) {
//                            response.append(inputLine);
//                        }
//                        //responseText = response.toString();
//                        Log.i("My tag" , response.toString());
//                    }
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//       });
//
//        thread.start();
//        return "CZESCerwer";
//    }
    //}


    @Override
    public String call() {

        try {
            try{

                String ipAddress = loginAndPassword.getIp();

                URL url = new URL(String.format("http://%s:5555/api/login",ipAddress));
                // final String URL = ;
                HttpURLConnection client = null;
                Log.i("MY TAG","SENDED REQUEST");

                client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("POST");
                client.setRequestProperty("Accept", "application/json");
                client.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("login", loginAndPassword.getLogin());
                jsonObject.put("password",loginAndPassword.getPassword());
                jsonObject.put("typeEnum",loginAndPassword.getTypeEnum());

                OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream());
                os.write(jsonObject.toString());
                os.flush();
                System.out.println(jsonObject);

                System.out.println("sended request");
                int code = client.getResponseCode();
                if (code !=  202) {
                    throw new IOException("Invalid response from server: " + code);
                } else {
                    //String value = client.getResponseMessage();
                    // read the response
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine + "\n");
                    }
                    responseText = response.toString();
                    Log.i("My tag" , response.toString());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        synchronized (this){
//            this.notify();
//        }

        return responseText;
    }


}

package com.example.assignment3;

import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private Button Btnlogin;
    private Button btnRegister;
    private static String url = "http://a3-comp3910.rhcloud.com/application/users/userlogin";

    public final static String TAG_NAME = "userName";
    public final static String TAG_PASSWORD = "password";
    public final static String TAG_TOKEN = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // navigateToRegister();

        // Intent loginIntent = new Intent(this, ListQuestions.class);

        Btnlogin = (Button) findViewById(R.id.login_button);
        Btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("click");
                new JSONParse().execute();
            }
        });

        final Context context = this;
        btnRegister = (Button) findViewById(R.id.register_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Register.class);
                startActivity(intent);

            }
        });

    }

    private class JSONParse extends AsyncTask<String, String, HttpResponse> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            usernameEdit = (EditText) findViewById(R.id.username_entry);
            passwordEdit = (EditText) findViewById(R.id.passwordentry);
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Checking login ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected HttpResponse doInBackground(String... args) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
                    10000);
            HttpResponse response;

            try {

                String username = URLEncoder.encode(usernameEdit.getText()
                        .toString(), "UTF-8");
                String password = URLEncoder.encode(passwordEdit.getText()
                        .toString(), "UTF-8");

                String newurl = url + "?username=" + username + "&password="
                        + password;
                System.out.println(newurl);
                HttpGet httpget = new HttpGet(newurl);
                response = httpClient.execute(httpget);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(HttpResponse response) {
            pDialog.dismiss();
            // Getting JSON Array from URL
            try {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String responseStr = EntityUtils.toString(response
                            .getEntity());
                    Intent in = new Intent(getApplicationContext(),
                            ListQuestionsActivity.class);
                    in.putExtra(TAG_TOKEN, responseStr);
                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), "Error",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

}

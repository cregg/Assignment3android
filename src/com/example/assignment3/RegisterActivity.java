package com.example.assignment3;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity created to allow a user to register as a student.
 * 
 * @author craigleclair
 * @version 1.0
 */
public class RegisterActivity extends Activity {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText passwordConfirmEdit;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText studentNumberEdit;
    private static final String TAG_USERNAME = "userName";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_FIRST = "firstName";
    private static final String TAG_LAST = "lastName";
    private static final String TAG_STUDENT_NUMBER = "studentNo";
    private static final String TAG_TOKEN = "token";

    private static String url = "http://a3-comp3910.rhcloud.com/application/users/newuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button Btnsubmit = (Button) findViewById(R.id.submit_button);
        Btnsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new UserRegister().execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    /**
     * Inner Class used to Register a user.
     * 
     * @author Graeme Funk
     * 
     */
    private class UserRegister extends AsyncTask<String, String, HttpResponse> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            usernameEdit = (EditText) findViewById(R.id.username_entry);
            passwordEdit = (EditText) findViewById(R.id.password_entry);
            passwordConfirmEdit = (EditText) findViewById(R.id.passwordconfirm);
            firstNameEdit = (EditText) findViewById(R.id.first_name);
            lastNameEdit = (EditText) findViewById(R.id.last_name);
            studentNumberEdit = (EditText) findViewById(R.id.student_number);

            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Creating User ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected HttpResponse doInBackground(String... args) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response;
            JSONObject json = new JSONObject();

            try {
                HttpPost httpPost = new HttpPost(url);
                json.put(TAG_USERNAME, usernameEdit.getText());
                json.put(TAG_PASSWORD, passwordEdit.getText());
                json.put(TAG_FIRST, firstNameEdit.getText());
                json.put(TAG_LAST, lastNameEdit.getText());
                json.put(TAG_STUDENT_NUMBER, studentNumberEdit.getText());
                System.out.println("not null");
                StringEntity se = new StringEntity(json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
                        "application/json"));
                httpPost.setEntity(se);
                response = httpClient.execute(httpPost);
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
							LoginActivity.class);
					in.putExtra(TAG_TOKEN,  responseStr);
					in.putExtra(TAG_USERNAME, usernameEdit.getText());
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

}

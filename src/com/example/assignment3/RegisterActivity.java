package com.example.assignment3;

import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    
    private class UserRegister extends AsyncTask<String, String, HttpResponse> {
        private ProgressDialog pDialog;
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           usernameEdit = (EditText) findViewById(R.id.username_entry);
           passwordEdit = (EditText) findViewById(R.id.passwordentry);    
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
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
			HttpResponse response;
			JSONObject json = new JSONObject();

			try {
				HttpPost httpPost = new HttpPost(url);
				json.put(TAG_USERNAME, usernameEdit.getText());
				json.put(TAG_PASSWORD, passwordEdit.getText());
				json.put(TAG_FIRST, firstNameEdit.getText());
				json.put(TAG_LAST, lastNameEdit.getText());
				json.put(TAG_STUDENT_NUMBER, studentNumberEdit.getText());
				StringEntity se = new StringEntity(json.toString());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				httpPost.setEntity(se);
				response = httpClient.execute(httpPost);
				return response;
			} catch(Exception e) {
				e.printStackTrace();
			}
			return null;
       }
        @Override
        protected void onPostExecute(HttpResponse response) {
            pDialog.dismiss();
           // Getting JSON Array from URL
            if(response.getStatusLine().getStatusCode() == 200) {
	            Intent in = new Intent(getApplicationContext(), LoginActivity.class);
	            startActivity(in);
            } else {
				Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            
        }
    }

}

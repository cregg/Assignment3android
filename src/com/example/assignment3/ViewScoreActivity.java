package com.example.assignment3;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class which is used to display the score of a specific quiz which the user
 * has taken
 * 
 * @author craigleclair
 * @version 1.0
 */
public class ViewScoreActivity extends Activity {

	TextView score;
	TextView average;
	String token;
	private static String TAG_SCORE = "score";
	private static final String TAG_TOKEN = "token";
	private static String logout_url = "http://a3-comp3910.rhcloud.com/application/quizzes/logout";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_score);

		String response = getIntent().getStringExtra(TAG_SCORE);
		token = getIntent().getStringExtra(TAG_TOKEN);
		String[] tokens = response.split(",");

		score = (TextView) findViewById(R.id.score_result);
		average = (TextView) findViewById(R.id.avg_result);

		score.setText(tokens[0] + " / " + tokens[1]);
		average.setText(tokens[2]);

		Button button = (Button) findViewById(R.id.back2quizzes);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(getApplicationContext(),
						ListQuizzesActivity.class);
				in.putExtra(TAG_TOKEN, token);
				startActivity(in);
			}
		});

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		new UserLogout().execute();
		return true;
	}

	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_score, menu);
		return true;
	}

	/**
	 * Class for handling logging out.
	 * 
	 * @author Graeme
	 * 
	 */
	private class UserLogout extends AsyncTask<String, String, HttpResponse> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ViewScoreActivity.this);
			pDialog.setMessage("Logging out ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected HttpResponse doInBackground(String... args) {

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response;
			JSONObject jsonLogout = new JSONObject();

			try {
				HttpPost httpPost = new HttpPost(logout_url);
				jsonLogout.put(TAG_TOKEN, token);
				StringEntity se = new StringEntity(jsonLogout.toString());
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
			try {
				if (response.getStatusLine().getStatusCode() == 200) {
					Intent in = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(in);
				} else {
					Toast.makeText(getApplicationContext(),
							"Error logging out", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

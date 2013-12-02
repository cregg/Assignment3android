package com.example.assignment3;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListQuizzesActivity extends Activity {
	ListView list;
	private String week;
	private ViewGroup buttons;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

	private static String url = "http://a3-comp3910.rhcloud.com/application/quizzes/";

	JSONArray android = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_quizes);
		Button firstButton = (Button) findViewById(R.id.quiz1);

		buttons = (ViewGroup) firstButton.getParent();

		for (int i = 0; i < buttons.getChildCount() - 1; i++) {
			Button selectedButton = (Button) buttons.getChildAt(i);
			week = selectedButton.getText().toString().substring(4);
			selectedButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					new ChooseQuiz(week).execute();
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_quizes, menu);
		return true;

	}

	private class ChooseQuiz extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;
		private String currentWeek;

		public ChooseQuiz(String week) {
			this.currentWeek = week;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ListQuizzesActivity.this);
			pDialog.setMessage("Retrieving Quiz ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected JSONObject doInBackground(String... args) {
			// JSONParser parser = new JSONParser();
			JSONParser jParser = new JSONParser();

			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl(url + "1");
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			Intent in = new Intent(getApplicationContext(), ListQuestionsActivity.class);
			in.putExtra("jsonObject", json.toString());
			startActivity(in);
		}
	}

}

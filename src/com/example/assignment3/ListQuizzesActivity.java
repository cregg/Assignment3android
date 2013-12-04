package com.example.assignment3;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ListQuizzesActivity extends Activity {
    ListView list;
    private String week;
    private String userName;
    private String token;

    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    private static String url = "http://a3-comp3910.rhcloud.com/application/quizzes/";
    private static String TAG_USERNAME = "userName";
    private static String TAG_TOKEN = "token";
    private static String TAG_RESULT = "result";
    private static final String TAG_SCORE = "score";

    JSONArray android = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_quizes);
        Button button1 = (Button) findViewById(R.id.quiz1);
        Button button2 = (Button) findViewById(R.id.quiz2);
        Button button3 = (Button) findViewById(R.id.quiz3);
        Button button4 = (Button) findViewById(R.id.quiz4);
        Button button5 = (Button) findViewById(R.id.quiz5);
        Button button6 = (Button) findViewById(R.id.quiz6);
        Button button7 = (Button) findViewById(R.id.quiz7);
        Button button8 = (Button) findViewById(R.id.quiz8);
        Button button9 = (Button) findViewById(R.id.quiz9);
        Button button10 = (Button) findViewById(R.id.quiz10);
        Button button11 = (Button) findViewById(R.id.quiz11);
        Button button12 = (Button) findViewById(R.id.quiz12);
        Button button13 = (Button) findViewById(R.id.quiz13);
        Button button14 = (Button) findViewById(R.id.quiz14);

        token = getIntent().getStringExtra(TAG_TOKEN);
        userName = getIntent().getStringExtra(TAG_USERNAME);

        week = getWeekNum(button1);
        setListener(button1, week);
        week = getWeekNum(button2);
        setListener(button2, week);
        week = getWeekNum(button3);
        setListener(button3, week);
        week = getWeekNum(button4);
        setListener(button4, week);
        week = getWeekNum(button5);
        setListener(button5, week);
        week = getWeekNum(button6);
        setListener(button6, week);
        week = getWeekNum(button7);
        setListener(button7, week);
        week = getWeekNum(button8);
        setListener(button8, week);
        week = getWeekNum(button9);
        setListener(button9, week);
        week = getWeekNum(button10);
        setListener(button10, week);
        week = getWeekNum(button11);
        setListener(button11, week);
        week = getWeekNum(button12);
        setListener(button12, week);
        week = getWeekNum(button13);
        setListener(button13, week);
        week = getWeekNum(button14);
        setListener(button14, week);
    }

    private void setListener(Button b, String value) {
        final String weekNum = value;
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(weekNum).execute();

            }
        });
    }

    private String getWeekNum(Button b) {
        return b.getText().toString().substring(5);
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
			System.out.println(url + currentWeek);
			try {
				String tokenEnc = URLEncoder.encode(token, "UTF-8");
				JSONObject json = jParser.getJSONFromUrl(url + currentWeek
						+ "?token=" + tokenEnc);
				return json;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			try {
				String str = json.getString(TAG_RESULT);
				System.out.println(str);
				System.out.println(str == null);
				if (str != null) {
					Intent in = new Intent(getApplicationContext(),
							ViewScoreActivity.class);
					in.putExtra(TAG_SCORE, str);
					in.putExtra(TAG_USERNAME, userName);
					startActivity(in);
				}
				else {
					Intent in = new Intent(getApplicationContext(),
							ListQuestionsActivity.class);
					in.putExtra(TAG_TOKEN, token);
					in.putExtra(TAG_USERNAME, userName);
					in.putExtra("jsonObject", json.toString());
					startActivity(in);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

}

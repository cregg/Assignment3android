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
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListQuestionsActivity extends Activity {
	private ProgressDialog pDialog;
	ListView list;
	private String week;
	private String weekNo;
	private String quizID;
	private ViewGroup buttons;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	
	private static final String TAG_QUIZ = "quiz";
	private static final String TAG_QUIZID = "quizID";
	private static final String TAG_WEEKNO = "weekNo";
	private static final String TAG_QUESTIONS = "questions";
	private static final String TAG_ANSWERS = "answers";
	private static final String TAG_ANSWERID = "answerID";
	private static final String TAG_ANSWER = "answer";
	private static final String TAG_ANSWER1 = "answer1";
	private static final String TAG_ANSWER2 = "answer2";
	private static final String TAG_ANSWER3 = "answer3";
	private static final String TAG_ANSWER4 = "answer4";
	private static final String TAG_QUESTIONID = "questionID";
	private static final String TAG_QUESTION = "question";
	
	JSONArray android = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);
        try {
        JSONObject json = new JSONObject(getIntent().getStringExtra("jsonObject"));
        populateView(json);
        } catch(Exception e) {
        	e.printStackTrace();
        }
     
        Button submitButton = (Button) findViewById(R.id.submit_answers);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new SubmitAnswer().execute();
            }
        });
    }
    
    protected void populateView(JSONObject json) {
		try {
			JSONObject o = json.getJSONObject(TAG_QUIZ);
			quizID = o.getString(TAG_QUIZID);
			weekNo = o.getString(TAG_WEEKNO);

			// Getting JSON Array from URL
			android = json.getJSONArray(TAG_QUESTIONS);

			for (int i = 0; i < android.length(); i++) {
				JSONObject c = android.getJSONObject(i);
				JSONObject q = c.getJSONObject(TAG_QUESTION);
				String question = q.getString(TAG_QUESTION);

				JSONArray qObject = c.getJSONArray(TAG_ANSWERS);

				JSONObject a = qObject.getJSONObject(0);
				String answer1 = a.getString(TAG_ANSWER);
				a = qObject.getJSONObject(1);
				String answer2 = a.getString(TAG_ANSWER);
				a = qObject.getJSONObject(2);
				String answer3 = a.getString(TAG_ANSWER);
				a = qObject.getJSONObject(3);
				String answer4 = a.getString(TAG_ANSWER);

				HashMap<String, String> map = new HashMap<String, String>();

				map.put(TAG_QUESTION, question);
				map.put(TAG_ANSWER1, answer1);
				map.put(TAG_ANSWER2, answer2);
				map.put(TAG_ANSWER3, answer3);
				map.put(TAG_ANSWER4, answer4);

				oslist.add(map);
				list = (ListView) findViewById(R.id.list);

				ListAdapter adapter = new SimpleAdapter(
						ListQuestionsActivity.this, oslist,
						R.layout.list_member, new String[] { TAG_QUESTION,
								TAG_ANSWER1, TAG_ANSWER2, TAG_ANSWER3,
								TAG_ANSWER4 }, new int[] { R.id.question,
								R.id.answer1, R.id.answer2, R.id.answer3,
								R.id.answer4 });

				if(list == null)
					System.out.println("null");
				 
				 
				list.setAdapter(adapter);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_questions, menu);
        return true;
    }

    private class SubmitAnswer extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListQuestionsActivity.this);
            pDialog.setMessage("Computing Scores ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {
            // JSONParser parser = new JSONParser();
            // String newUrl = url + week;
            JSONObject json = null; // parser.getJSONFromUrl(newUrl);
            return json;

        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                Intent in = new Intent(getApplicationContext(), ViewScore.class);
                startActivity(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

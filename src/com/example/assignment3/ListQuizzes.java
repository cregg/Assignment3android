package com.example.assignment3;

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

public class ListQuizzes extends Activity {
    private String week;
    private ViewGroup buttons;
    private static String url = "http://a3-comp3910.rhcloud.com/application/quizzes/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_quizes);
        Button firstButton = (Button) findViewById(R.id.quiz1);

        buttons = (ViewGroup) firstButton.getParent();

        for (int i = 0; i < buttons.getChildCount() - 1; i++) {
            Button selectedButton = (Button) buttons.getChildAt(i);
            week = selectedButton.toString().replace(" ", "");
            selectedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ChooseQuiz().execute();
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListQuizzes.this);
            pDialog.setMessage("Checking login ...");
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
                Intent in = new Intent(getApplicationContext(),
                        ListQuestionsActivity.class);
                startActivity(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

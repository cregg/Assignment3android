package com.example.assignment3;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ListQuestionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        Button submitButton = (Button) findViewById(R.id.submit_answers);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new SubmitAnswer().execute();
            }
        });
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

package com.example.assignment3;

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
    private String week1;
    private String week2;
    private String week3;
    private String week4;
    private String week5;
    private String week6;
    private String week7;
    private String week8;
    private String week9;
    private String week10;
    private String week11;
    private String week12;
    private String week13;
    private String week14;
    private String userName;
    private String token;

    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    private static String url = "http://a3-comp3910.rhcloud.com/application/quizzes/";
    private static String TAG_USERNAME = "userName";
    private static String TAG_TOKEN = "token";

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

        week1 = button1.getText().toString().substring(5);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week1).execute();

            }
        });

        week2 = button2.getText().toString().substring(5);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week2).execute();

            }
        });

        week3 = button3.getText().toString().substring(5);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week3).execute();

            }
        });

        week4 = button4.getText().toString().substring(5);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week4).execute();

            }
        });

        week5 = button5.getText().toString().substring(5);
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week5).execute();

            }
        });

        week6 = button6.getText().toString().substring(5);
        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week6).execute();

            }
        });

        week7 = button7.getText().toString().substring(5);
        button7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week7).execute();

            }
        });

        week8 = button8.getText().toString().substring(5);
        button8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week8).execute();

            }
        });

        week9 = button9.getText().toString().substring(5);
        button9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week9).execute();

            }
        });

        week10 = button10.getText().toString().substring(5);
        button10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week10).execute();

            }
        });

        week11 = button11.getText().toString().substring(5);
        button11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week11).execute();

            }
        });

        week12 = button12.getText().toString().substring(5);
        button12.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week12).execute();

            }
        });

        week13 = button13.getText().toString().substring(5);
        button13.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week13).execute();

            }
        });

        week14 = button14.getText().toString().substring(5);
        button14.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseQuiz(week14).execute();

            }
        });
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
            JSONObject json = jParser.getJSONFromUrl(url + currentWeek);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            Intent in = new Intent(getApplicationContext(),
                    ListQuestionsActivity.class);
            in.putExtra(TAG_TOKEN, token);
            in.putExtra(TAG_USERNAME, userName);
            in.putExtra("jsonObject", json.toString());
            startActivity(in);
        }
    }

}

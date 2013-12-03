package com.example.assignment3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity class which displays the questions and their answers.
 * 
 * @author craigleclair/Graeme Funk
 * @version 1.0
 */
public class ListQuestionsActivity extends Activity {
    private ProgressDialog pDialog;
    ListView list;
    private String week;
    private String weekNo;
    private String quizID;
    private String userName;
    private String token;
    private ViewGroup buttons;
    private RadioGroup tyorke;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    HashMap<Integer, Integer> answerMap = new HashMap<Integer, Integer>();
    HashMap<String, String> answerMapString = new HashMap<String, String>();

    private static String url = "http://a3-comp3910.rhcloud.com/application/quizzes/mark";

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
    private static final String TAG_USERNAME = "userName";
    private static final String TAG_TOKEN = "token";
    private static final String TAG_SCORE = "score";

    JSONArray android = null;
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);

        userName = getIntent().getStringExtra(TAG_USERNAME);
        token = getIntent().getStringExtra(TAG_TOKEN);

        try {
            json = new JSONObject(getIntent().getStringExtra("jsonObject"));
            populateView(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button submitButton = (Button) findViewById(R.id.submit_answers);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println(answerMapString);
                new SubmitAnswer().execute();
            }
        });
    }

    /**
     * Creates a Hash map containing the question string as a key and the answer
     * string as a value.
     * 
     * @return HashMap<Question, Answer>
     */
    public HashMap<String, String> getAnswerMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        Adapter listAdapter = list.getAdapter();
        System.out.println(listAdapter.getCount());
        for (int i = 0; i < listAdapter.getCount(); i++) {
            RelativeLayout v = (RelativeLayout) listAdapter.getView(i, null,
                    tyorke);

            TextView questionView = (TextView) v.getChildAt(0);
            String question = (String) questionView.getText();
            RadioGroup tyorke = (RadioGroup) v.getChildAt(1);
            System.out.println(tyorke.getChildCount());
            int answerId = tyorke.getCheckedRadioButtonId();
            System.out.println(answerId);
            RadioButton answerButton = (RadioButton) findViewById(answerId);
            String answer = (String) answerButton.getText();

            map.put(question, answer);
        }

        return map;
    }

    public void setClick(View v) {
        RadioButton rb = (RadioButton) v;
        String answer = (String) rb.getText();
        RelativeLayout rl = (RelativeLayout) v.getParent().getParent();
        TextView questionView = (TextView) rl.getChildAt(0);
        String question = (String) questionView.getText();
        answerMapString.put(question, answer);

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

                if (list == null)
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

    private class SubmitAnswer extends AsyncTask<String, String, HttpResponse> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pDialog = new ProgressDialog(ListQuestionsActivity.this);
                pDialog.setMessage("Computing Scores ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected HttpResponse doInBackground(String... args) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response;
            JSONObject json = new JSONObject();
            JSONObject temp;
            JSONArray n;

            answerMap.put(1, 4);
            answerMap.put(2, 5);
            answerMap.put(3, 9);
            answerMap.put(4, 15);

            try {
                HttpPost httpPost = new HttpPost(url);
                temp = new JSONObject();
                n = new JSONArray();
                json.put(TAG_TOKEN, token);
                json.put(TAG_USERNAME, userName);
                temp = new JSONObject();
                temp.put(TAG_QUIZID, Integer.parseInt(quizID));
                temp.put(TAG_WEEKNO, Integer.parseInt(weekNo));

                json.put(TAG_QUIZ, temp);

                System.out.println("iterate map");
                Iterator it = answerMap.entrySet().iterator();
                while (it.hasNext()) {
                    temp = new JSONObject();
                    Map.Entry pairs = (Map.Entry) it.next();
                    temp.put(TAG_QUESTIONID, pairs.getKey());
                    temp.put(TAG_ANSWERID, pairs.getValue());
                    temp.put(TAG_ANSWER, "howw old");
                    n.put(temp);
                }
                json.put(TAG_ANSWERS, n);
                StringEntity se = new StringEntity(json.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
                        "application/json"));
                httpPost.setEntity(se);
                System.out.println(json.toString());

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
                    String responseStr = EntityUtils.toString(response
                            .getEntity());
                    Intent in = new Intent(getApplicationContext(),
                            ViewScoreActivity.class);
                    in.putExtra(TAG_SCORE, responseStr);
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

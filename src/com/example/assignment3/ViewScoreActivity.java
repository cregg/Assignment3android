package com.example.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private static String TAG_SCORE = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);
        String response = getIntent().getStringExtra(TAG_SCORE);
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
                startActivity(in);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_score, menu);
        return true;
    }

}

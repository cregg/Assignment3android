package com.example.assignment3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Class which is used to display the score of a specific quiz which the user
 * has taken
 * 
 * @author craigleclair
 * @version 1.0
 */
public class ViewScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_score, menu);
        return true;
    }

}

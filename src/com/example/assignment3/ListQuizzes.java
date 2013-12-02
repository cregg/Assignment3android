package com.example.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ListQuizzes extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_quizes);

        Button[] btns = new Button[14];

        btns[0] = (Button) findViewById(R.id.quiz1);
        btns[1] = (Button) findViewById(R.id.quiz2);
        btns[2] = (Button) findViewById(R.id.quiz3);
        btns[3] = (Button) findViewById(R.id.quiz4);
        btns[4] = (Button) findViewById(R.id.quiz5);
        btns[5] = (Button) findViewById(R.id.quiz6);
        btns[6] = (Button) findViewById(R.id.quiz7);
        btns[7] = (Button) findViewById(R.id.quiz8);
        btns[8] = (Button) findViewById(R.id.quiz9);
        btns[9] = (Button) findViewById(R.id.quiz10);
        btns[10] = (Button) findViewById(R.id.quiz11);
        btns[11] = (Button) findViewById(R.id.quiz12);
        btns[12] = (Button) findViewById(R.id.quiz13);

        for (int i = 0; i < btns.length - 1; i++) {
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(getApplicationContext(),
                            ListQuestionsActivity.class);
                    startActivity(in);
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

}

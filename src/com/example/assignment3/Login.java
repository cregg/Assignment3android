package com.example.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        navigateToRegister();

        Intent loginIntent = new Intent(this, ListQuestions.class);
        EditText usernameEdit = (EditText) findViewById(R.id.username_entry);
        EditText passwordEdit = (EditText) findViewById(R.id.password_entry);

        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

    }

    public void navigateToRegister() {
        Intent registerIntent = new Intent(this, Register.class);

        Button registerButton = (Button) findViewById(R.id.register_button);

        // registerButton.setOnClickListener(new OnClickListener() {
        //
        // })
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

}

package com.example.noa.laboratorio_81_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText usernameField,passwordField;
    private TextView status,role,method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = (EditText)findViewById(R.id.editTextUser);
        passwordField = (EditText)findViewById(R.id.editTextPassword);
        status = (TextView)findViewById(R.id.textViewSessionStatusDesc);
        role = (TextView)findViewById(R.id.textViewSessionRoleDesc);
        method = (TextView)findViewById(R.id.textViewSessionMethodDesc);
    }

    public void login(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        method.setText("Get Metodo");
        new SigninActivity(this,status,role,0).execute(username,password);
    }

    public void loginPost(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        method.setText("Post Metodo");
        /*
        No fue implementada en este laboratorio. D:
        new JSON(this,status,role,1).execute(username,password);
         */
        new SigninActivity(this,status,role,1).execute(username,password);
    }
}



















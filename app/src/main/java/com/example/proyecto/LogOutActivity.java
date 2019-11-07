package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Models.User;

/**
 *
 */
public class LogOutActivity extends AppCompatActivity {

    private Button btnLogOut;
    private TextView textViewUser;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        btnLogOut = (Button)findViewById(R.id.btnLogOut);
        textViewUser= (TextView)findViewById(R.id.textViewUser);

        Bundle bundle=getIntent().getExtras();
        user= (User) bundle.get("usuario");
        textViewUser.setText(user.getFullname());


        LogOut();

    }

    private void LogOut() {

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                user=null;
                startActivity(intent);
            }
        });
    }
}

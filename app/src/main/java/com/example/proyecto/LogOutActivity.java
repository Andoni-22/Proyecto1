package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Models.User;

/**
 * Activity Sign Up launcher
 *
 * This activity is launched if SignUp button is clicked
 *
 * @author Rubén Zarantón and Andoni Fiat
 */
public class LogOutActivity extends AppCompatActivity {

    private Button btnLogOut;
    private TextView textViewUser;
    private User user;

    /**
     * Load components in this activity
     *
     * @param savedInstanceState
     */
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

    /**
     * Method that removes user data and launches the MainActivity
     */
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

package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Interfaces.Signable;
import Logic.ClientFactory;

/**
 * @autor Andoni Fiat
 */
public class MainActivity extends AppCompatActivity {
    //Signable sign = new ClientFactory().getClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        logInLauncher();
        signUpLauncher();
    }

    /**
     * Method that launch activity_logOut layout
     */
    private void logInLauncher() {

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean esta;
                esta = comprobarLogin();

                if(esta==true){
                    Intent intent = new Intent(getApplicationContext(), LogOutActivity.class);
                    startActivity(intent);
                }else{
                    editTextPassword.setError("Datos incorrectos");
                }
            }

            /**
             * method that comfirm the login
             * @return true if the user and password are correct
             */
            private boolean comprobarLogin() {
                return true;
            }

        });
    }

    /**
     * Method that launch activity_signup layout
     */
    private void signUpLauncher() {
        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }


}

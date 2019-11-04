package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Interfaces.Signable;
import Logic.ClientFactory;
import Models.Enum.TypeMessage;
import Models.Message;
import Models.User;

/**
 * @autor Andoni Fiat
 */
public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnSignUp;
    private EditText editTextPassword;
    private EditText editTextUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        logInLauncher();
        signUpLauncher();
    }

    /**
     * Method that launch activity_logOut layout
     */
    private void logInLauncher() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean esta;
                esta = comprobarLogin();

                if(esta==true){
                    Intent intent = new Intent(getApplicationContext(), LogOutActivity.class);
                    startActivity(intent);
                }else{
                    editTextPassword.setError("Incorrect login");
                }
            }
            /**
             * method that comfirm the login
             * @return true if the user and password are correct
             */
            private boolean comprobarLogin() {
                boolean correct = false;
                int opc = 0;
                User user = new User();
                MyThread thread = new MyThread();
                Message msg = new Message();
                user.setLogin(editTextUser.getText().toString());
                user.setPassword(editTextPassword.getText().toString());

                msg.setData(user);
                msg.setType(TypeMessage.LOGIN);

                thread.androidThread(opc = 1, user);

                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user = thread.getResult();
                if(user != null){
                    correct = true;
                }
                return correct;
            }

        });
    }
    /**
     * Method that launch activity_signup layout
     */
    private void signUpLauncher() {

        btnSignUp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }


}

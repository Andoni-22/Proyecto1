package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Interfaces.Signable;
import Logic.Client;
import Logic.ClientFactory;
import Models.User;

/**
 * @author Andoni Fiat
 */
public class SignupActivity extends AppCompatActivity{
   Signable client = ClientFactory.getClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        createUser();
    }

    private void createUser() {

        Button btnCreateUser = (Button) findViewById(R.id.btnCreateUser);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comprobarDatos();
            }

            private void comprobarDatos() {

                String email,password,comfirmPwd;
                boolean correct = false;
                Client cli = new Client();


                EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
                EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
                email = editTextEmail.getText().toString();


                correct=cli.emailChecker(email);
                if(correct!=true){
                    editTextEmail.setError("Invalid email format");
                }

                EditText editTextFullname = (EditText) findViewById(R.id.editTextFullname);

                EditText editTextPWD = (EditText) findViewById(R.id.editTextPWD);
                password = editTextPWD.getText().toString();

                EditText editTextComfirmPWD = (EditText) findViewById(R.id.editTextComfirmPWD);
                comfirmPwd = editTextComfirmPWD.getText().toString();
                if(!password.equalsIgnoreCase(comfirmPwd)){
                    editTextComfirmPWD.setError("Password does not match");
                }


                if(editTextUsername.getText().toString().isEmpty()){
                    editTextUsername.setError("Datos incorrectos");
                }
                if(editTextEmail.getText().toString().isEmpty()){
                    editTextEmail.setError("Datos incorrectos");
                }
                if(editTextFullname.getText().toString().isEmpty()){
                    editTextFullname.setError("Datos incorrectos");
                }
                if(editTextPWD.getText().toString().isEmpty()){
                    editTextPWD.setError("Datos incorrectos");
                }
                if(editTextComfirmPWD.getText().toString().isEmpty()){
                    editTextComfirmPWD.setError("Datos incorrectos");
                }
            }

        });


        }


}


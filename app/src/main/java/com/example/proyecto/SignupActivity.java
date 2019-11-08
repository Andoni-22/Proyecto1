package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Interfaces.Signable;
import Logic.Client;
import Logic.ClientFactory;

import Models.Enum.TypeMessage;
import Models.Message;
import Models.User;

import validators.Validators;

/**
 * Activity Sign Up launcher
 *
 * This activity is launched if SignUp button is clicked
 *
 * @author Ruben Zarantón and Yeray Ramos
 */
public class SignupActivity extends AppCompatActivity {
    private Button btnCreateUser;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextFullname;
    private EditText editTextPWD;
    private EditText editTextComfirmPWD;
    private MyThread thread;
    private User user = new User();

    /**
     * Load components in this activity
     *
     * @param savedInstanceState stored application data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextFullname = (EditText) findViewById(R.id.editTextFullname);
        editTextPWD = (EditText) findViewById(R.id.editTextPWD);
        editTextComfirmPWD = (EditText) findViewById(R.id.editTextComfirmPWD);
        btnCreateUser = (Button) findViewById(R.id.btnCreateUser);

        createUser();
    }

    /**
     * Method that creates user and passes user info to LogOut
     */
    private void createUser() {


        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correct, esta = false;

                correct = comprobarDatos();

                if (correct == true) {
                    esta = comprobarSignUp();
                }
                if (esta == true) {
                    Intent intent = new Intent(getApplicationContext(), LogOutActivity.class);
                    intent.putExtra("usuario", user);
                    startActivity(intent);
                }
            }

            /**
             * In this method we check that the signup data have a correct format
             */
            private boolean comprobarDatos() {

                String email, password, comfirmPwd;
                boolean correct = false, functional = true;
                Validators vali = new Validators();

                email = editTextEmail.getText().toString().trim();
                password = editTextPWD.getText().toString().trim();
                comfirmPwd = editTextComfirmPWD.getText().toString().trim();
                if (editTextUsername.getText().length() > 40) {
                    editTextUsername.setError("Username can´t be more than 40 characters");
                    functional = false;
                } else if (editTextUsername.getText().toString().isEmpty()) {
                    editTextUsername.setError("Not data found");
                    functional = false;
                }

                if (editTextEmail.getText().length() > 40) {
                    editTextEmail.setError("Email can´t be more than 40 characters");
                    functional = false;
                } else if (editTextEmail.getText().toString().isEmpty()) {
                    editTextEmail.setError("Not data found");
                    functional = false;
                } else if (!editTextEmail.getText().toString().isEmpty()) {
                    correct = vali.emailChecker(email);
                    if (correct != true) {
                        editTextEmail.setText("");
                        editTextEmail.setError("Invalid email format");
                        functional = false;
                    }
                }
                if (editTextFullname.getText().length() > 40) {
                    editTextFullname.setError("Full name can´t be more than 40 characters");
                    functional = false;
                } else if (editTextFullname.getText().toString().isEmpty()) {
                    editTextFullname.setError("Not data found");
                    functional = false;
                }
                if (editTextPWD.getText().length() > 40) {
                    editTextPWD.setError("Password can´t be more than 40 characters");
                    functional = false;
                } else if (editTextPWD.getText().toString().isEmpty()) {
                    editTextPWD.setError("Not data found");
                    functional = false;
                } else if (!editTextPWD.getText().toString().isEmpty()) {
                    correct = vali.passwordChecker(password);
                    if (correct != true) {
                        editTextPWD.setError("Min 8 characters, 1 Upper, 1 lower, 1 especial and 1 number");
                        editTextPWD.setText("");
                        editTextComfirmPWD.setText("");
                        functional = false;
                    }
                    if (!password.equalsIgnoreCase(comfirmPwd)) {
                        editTextComfirmPWD.setError("Password does not match");
                        editTextComfirmPWD.setText("");
                        editTextPWD.setText("");
                        functional = false;
                    }
                }
                if (editTextComfirmPWD.getText().toString().isEmpty()) {
                    editTextComfirmPWD.setError("Not data found");
                    functional = false;
                }
                return functional;
            }

        });
    }

    /**
     * Method that sets user, message and constructs new thread with it
     *
     * @return true if everything is ok&&user is able to connect
     */
    private boolean comprobarSignUp() {

        boolean correct = false;

        user.setFullname(editTextFullname.getText().toString());
        user.setEmail(editTextEmail.getText().toString().trim());
        user.setLogin(editTextUsername.getText().toString().trim());
        user.setPassword(editTextPWD.getText().toString());

        Message mensajeSalida = new Message();
        mensajeSalida.setData(user);
        mensajeSalida.setType(TypeMessage.SIGNUP);

        thread = new MyThread(mensajeSalida);

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (thread.getMensaje().getType() == TypeMessage.OK) {
            user = (User) thread.getMensaje().getData();
            correct = true;
        } else if (thread.getMensaje().getType() == TypeMessage.SUPEMAILERROR) {
            Toast.makeText(getApplicationContext(), "EmailError", Toast.LENGTH_LONG).show();
            editTextEmail.requestFocus();
            editTextEmail.selectAll();
        } else if (thread.getMensaje().getType() == TypeMessage.SUPLOGERROR) {
            Toast.makeText(getApplicationContext(), "LOGIN already used", Toast.LENGTH_LONG).show();
            editTextUsername.requestFocus();
            editTextUsername.selectAll();
        }


        return correct;
    }
}


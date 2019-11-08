package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Interfaces.Signable;
import Logic.ClientFactory;
import Models.Enum.TypeMessage;
import Models.Message;
import Models.User;

/**
 * Activity that launches application
 *
 * This activity is used to display different layouts
 *
 * @autor Andoni Fiat y Francisco Romero
 * @version 2019.0711
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnSignUp;
    private EditText editTextPassword;
    private EditText editTextUser;
    private MyThread thread ;
    private User user;

    /**
     * Load components in the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean network = false;
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        editTextUser.requestFocus();

        signUpLauncher();

        network = isInternet();

        if(network == false){
            Toast.makeText(getApplicationContext(),"NOT INTERNETEN CONECTION",Toast.LENGTH_SHORT).show();
            btnLogin.setEnabled(false);
            btnSignUp.setEnabled(false);
        }

        logInLauncher();

    }

    /**
     * Method that checks the net connection
     *
     * @return true or false if the acces to the net is available or not
     */
    private boolean isInternet() {
        boolean ret = false;
        ConnectivityManager connectivityManager;
        try{
            connectivityManager = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if((networkInfo != null) && (networkInfo.isAvailable()) && networkInfo.isConnected()){
                ret = true;
            }
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Connectivity Exception", Toast.LENGTH_SHORT).show();
        }
        return ret;
    }

    /**
     * Method that launch activity_logOut layout
     */
    private void logInLauncher() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean esta, network,correct;

                correct = comprobarFormato();

                if(correct == true){
                    esta = comprobarLogin();
                    if(esta==true){
                        Intent intent = new Intent(getApplicationContext(), LogOutActivity.class);
                        intent.putExtra("usuario", user);
                        Toast.makeText(getApplicationContext(),"LOGIN CORRECTLY",Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Incorrect login", Toast.LENGTH_LONG);
                    }
                }
            }

            /**
             * Method that checks user and password text formats
             *
             * @return true if user&&password have the FORMAT, else false
             */
            private boolean comprobarFormato() {
                boolean correct = true;
                if(editTextUser.getText().toString().isEmpty()){
                    editTextUser.setError("Not data found");
                    correct = false;
                }
                if(editTextPassword.getText().toString().isEmpty()){
                    editTextPassword.setError("Not data found");
                    correct = false;
                }

                return correct;
            }


            /**
             * method that sends the message to thread to start it
             *
             * @return true if the user and password are correct
             */
            private boolean comprobarLogin() {
                boolean correct = false;
                user=new User();

                user.setLogin(editTextUser.getText().toString());
                user.setPassword(editTextPassword.getText().toString());

                Message mensaje = new Message();
                mensaje.setData(user);
                mensaje.setType(TypeMessage.LOGIN);

                thread=new MyThread(mensaje);

                try {
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {
                    Toast.makeText(getApplicationContext(), "Conexión interrumpida", Toast.LENGTH_LONG).show();
                }
                if(thread.getMensaje().getType()==TypeMessage.OK){
                    user = (User) thread.getMensaje().getData();
                    correct = true;
                }else if(thread.getMensaje().getType()==TypeMessage.LOGINERROR){
                    Toast.makeText(getApplicationContext(), "LoginError", Toast.LENGTH_LONG).show();
                    editTextUser.requestFocus();
                    editTextUser.selectAll();
                }else if(thread.getMensaje().getType()==TypeMessage.PASSWERROR){
                    Toast.makeText(getApplicationContext(), "PasswordError", Toast.LENGTH_LONG).show();
                    editTextPassword.requestFocus();
                    editTextPassword.selectAll();
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

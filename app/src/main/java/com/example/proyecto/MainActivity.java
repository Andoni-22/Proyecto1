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
 * @autor Andoni Fiat
 */
public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnSignUp;
    private EditText editTextPassword;
    private EditText editTextUser;
    private MyThread thread ;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean network = false;
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        signUpLauncher();

        network = isInternet();
        if(network == false){
            Toast.makeText(getApplicationContext(),"NOT INTERNETEN CONECTION",Toast.LENGTH_SHORT).show();
            btnLogin.setEnabled(false);
            btnSignUp.setEnabled(false);
        }
        logInLauncher();
    }
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
                boolean esta, network;

                esta = comprobarLogin();

                if(esta==true){
                    Intent intent = new Intent(getApplicationContext(), LogOutActivity.class);
                    intent.putExtra("usuario", user);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Incorrect login", Toast.LENGTH_LONG);


                }
            }
            /**
             * method that comfirm the login
             * @return true if the user and password are correct
             */
            private boolean comprobarLogin() {
                boolean correct = false;

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
                    Toast.makeText(getApplicationContext(), "Conexión interrumpida", Toast.LENGTH_LONG);
                }
                if(thread.getMensaje().getType()==TypeMessage.OK){
                    user = (User) thread.getMensaje().getData();
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

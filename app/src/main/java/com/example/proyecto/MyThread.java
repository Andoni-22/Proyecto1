package com.example.proyecto;

import android.util.Log;

import Interfaces.Signable;
import Logic.ClientFactory;
import Models.Enum.TypeMessage;
import Models.Message;
import Models.User;
import exceptions.LoginErrorException;
import exceptions.PasswErrorException;
import exceptions.ServerErrorException;
import exceptions.SupEmailErrorException;
import exceptions.SupLogErrorException;

/**
 * Class that defines a new thread
 *
 * @author Andoni Fiat,Rubén Zarantón, Francisco Romero and Yeray Ramos
 */
public class MyThread extends Thread {

    private Message message = new Message();
    private Message mensaje;
    private User rest;

    /**
     * Constructor
     * @param mensaje object that have the login or signup data
     */
    public  MyThread(Message mensaje){
        this.mensaje=mensaje;
    }

    /**
     * method that starts the new thread
     */
    public void run(){
        Signable sign = ClientFactory.getClient();

        try {
            switch (mensaje.getType()){
                //LOGIN
                case LOGIN:
                    rest = sign.login((User)mensaje.getData());
                    break;
                //Sign up
                case SIGNUP:
                    rest = sign.signUp((User)mensaje.getData());
                    break;
            }

            mensaje.setType(TypeMessage.OK);
            mensaje.setData(rest);

        } catch (LoginErrorException e) {
            Log.e("Error","Error en el login");
            mensaje.setType(TypeMessage.LOGINERROR);
        } catch (PasswErrorException e) {
            Log.e("Error","Error en el passwd");
            mensaje.setType(TypeMessage.PASSWERROR);
        } catch (SupEmailErrorException e) {
            Log.e("Error","Error en el email");
            mensaje.setType(TypeMessage.SUPEMAILERROR);
        } catch (SupLogErrorException e) {
            Log.e("Error","Error en el login");
            mensaje.setType(TypeMessage.SUPLOGERROR);
        } catch (ServerErrorException e) {
            Log.e("Error","Error en el servidor");
            mensaje.setType(TypeMessage.LOGINERROR);
        }

    }

    public Message getMensaje() {
        return mensaje;
    }

    public void setMensaje(Message mensaje) {
        this.mensaje = mensaje;
    }
}

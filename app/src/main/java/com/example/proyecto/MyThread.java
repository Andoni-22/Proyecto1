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
 * @author Andoni Fiat
 */
public class MyThread extends Thread {

    private Message message = new Message();
    private Message mensaje;
    private User rest;

    /**
     * method that we use to get the data on the trhead
     * @param mensaje object that have the login or signup data
     */
    public  MyThread(Message mensaje){
        this.mensaje=mensaje;
    }

    /**
     * method that we use to conect whith the server
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
        mensaje.setType(TypeMessage.OK);
        mensaje.setData(rest);

    }

    public Message getMensaje() {
        return mensaje;
    }

    public void setMensaje(Message mensaje) {
        this.mensaje = mensaje;
    }
}

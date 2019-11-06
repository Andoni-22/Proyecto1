package com.example.proyecto;

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
    private int opc;
    private User user;
    private User rest;
    Signable sign = ClientFactory.getClient();
    Message message = new Message();

    /**
     * method that we use to get the data on the trhead
     * @param opc integer that can be "1" or "2"
     * @param user object that have the login or signup data
     */
    public MyThread(int opc, User user){
        this.opc = opc;
        this.user = user;
    }

    /**
     * method that we use to conect whith the server
     */
    public void run(){

        try {
            switch (opc){
                //LOGIN
                case 1:
                    rest = sign.login(user);
                    break;
                //Sign up
                case 2:
                    rest = sign.signUp(user);
                    break;
            }
        } catch (LoginErrorException e) {
            e.printStackTrace();
        } catch (PasswErrorException e) {
            e.printStackTrace();
        } catch (SupEmailErrorException e) {
            e.printStackTrace();
        } catch (SupLogErrorException e) {
            e.printStackTrace();
        } catch (ServerErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that return the object
     * @return user, if user is null is because something go wrong
     */
    public User getResult(){
        return rest;
    }
}

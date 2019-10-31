package com.example.proyecto;

import Interfaces.Signable;
import Logic.ClientFactory;
import Models.Enum.TypeMessage;
import Models.Message;
import Models.User;

public class MyThread extends Thread {
    Signable sign = ClientFactory.getClient();
    Message message = new Message();
    int opc = 0;
    User user = new User();
    User rest = new User();
    public  void androidThread(int opc, User user){
        this.opc = opc;
        this.user = user;
    }

    public void run(){
        //TODO try-multiCatch para controlar excepciones
        switch (opc){
            case 1:
                rest = sign.login(user);
                break;
            case 2:
                rest = sign.signUp(user);
                break;
        }
    }
    //TODO metodo que retorne el resultado
}

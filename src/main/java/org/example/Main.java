package org.example;

import org.example.Image.ImageTableWorkers;
import org.example.Mail.MailTableWorkers;


public class Main {

    public static void main(String[] args) {

    IDManager imageIDManager = new IDManager("image","URL");

    for(int i = 1 ; i < 3 ; i ++){
        ImageTableWorkers imageTableWorkers = new ImageTableWorkers(imageIDManager);
        Thread thread = new Thread(imageTableWorkers,"Image worker: " + i);
        thread.start();
    }

    IDManager mailIDManager = new IDManager("mails","sent");

    for(int i = 1 ; i < 3 ; i ++){
        MailTableWorkers mailTableWorkers = new MailTableWorkers(mailIDManager);
        Thread thread = new Thread(mailTableWorkers,"Mail worker: " + i);
        thread.start();
    }


    }





}
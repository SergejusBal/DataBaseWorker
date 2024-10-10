package org.example.Mail;

import org.example.IDManager;
public class MailTableWorkers implements Runnable{
    IDManager idManager;

    public MailTableWorkers(IDManager idManager) {
        this.idManager = idManager;
    }

    @Override
    public void run() {
        while (true) {
            SendMail sendMail = new SendMail();
            MailRepository mailRepository = new MailRepository();

            Integer reservedID = idManager.getID();
            Mail mail;
            while ((mail = mailRepository.getMail(reservedID)) == null) {
                if(idManager.setLastID()){
                    idManager.removeReserved(reservedID);
                    reservedID = idManager.getID();
                    continue;
                }
                IDManager.waitForData(90000);
            }

            IDManager.waitForData(1000);

           // mail = mailRepository.getMail(reservedID);
            sendMail.sendMail(mail.getEmailTo(),"Test", mail.getContent());

            System.out.println(Thread.currentThread().getName() + " ID: " + reservedID);

            mailRepository.setTime(reservedID);

            idManager.removeReserved(reservedID);

        }

    }










}

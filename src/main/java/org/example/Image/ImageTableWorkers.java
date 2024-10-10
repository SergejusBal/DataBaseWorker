package org.example.Image;

import org.example.IDManager;

public class ImageTableWorkers implements Runnable{

    IDManager idManager;

    public ImageTableWorkers(IDManager idManager) {
        this.idManager = idManager;
    }

    @Override
    public void run() {
        while (true) {
            UploadImage uploadImage = new UploadImage();
            ImageReposiroty imageReposiroty = new ImageReposiroty();

            Integer reservedID = idManager.getID();

            byte[] image;
            while ((image = imageReposiroty.getImage(reservedID)) == null) {
                if(idManager.setLastID()){
                idManager.removeReserved(reservedID);
                reservedID = idManager.getID();
                continue;
                }
                IDManager.waitForData(90000);
            }

            IDManager.waitForData(1000);

           // byte[] image = imageReposiroty.getImage(reservedID);
            String url = uploadImage.uploadImage(image);
            imageReposiroty.setURL(reservedID, url);
            System.out.println(Thread.currentThread().getName() + " ID: " + reservedID + " URL: " + url);
            idManager.removeReserved(reservedID);

        }
    }
}

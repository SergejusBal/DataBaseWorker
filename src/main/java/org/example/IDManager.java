package org.example;

import org.example.Mail.Mail;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class IDManager {
    private final Set<Integer> reserved;
    private final AtomicInteger IDcounter;
    private final String tableName;
    private final String column;

    public IDManager(String tableName, String column) {
         int lastID = getLastID(tableName,column);
         this.IDcounter = new AtomicInteger(lastID);
         this.column = column;
         this.tableName = tableName;
         reserved = new HashSet<Integer>();
    }

    private synchronized void setReserved(Integer id) {
        reserved.add(id);
    }

    private synchronized boolean checkValue(Integer id){
        return reserved.contains(id);
    }

    public synchronized void removeReserved(Integer id) {
        reserved.remove(id);
    }

    public synchronized Integer getID(){
        Integer currentValue = IDcounter.get();
        boolean isReserved = checkValue(currentValue);
        if(!isReserved) {
            setReserved(currentValue);
            IDcounter.set(currentValue + 1);
            return currentValue;
        }
          IDcounter.set(currentValue + 1);
          return getID();
    }

    public synchronized boolean setLastID(){
      //  waitForData(1000);
        int getLastID = getLastID(tableName,column);
        if(checkValue(getLastID)) return false;
        if(getLastID == 1) return false;
        IDcounter.set(getLastID);
        return true;
    }

    public static void waitForData(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getLastID(String tableName, String column){
        String sql = "SELECT * FROM " + tableName + " WHERE " + column + " IS NULL LIMIT 1";
        int lastID;
        try {
            Connection connection = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet =  preparedStatement.executeQuery();

            if(!resultSet.next()) return 1;

            lastID = resultSet.getInt("id");

        }catch (SQLException e) {

            System.out.println(e.getMessage());
            return 1;
        }

        return lastID;

    }


}

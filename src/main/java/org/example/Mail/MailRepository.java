package org.example.Mail;

import org.example.DataBaseInfo;

import java.sql.*;
import java.time.LocalDateTime;

public class MailRepository {

    public String setTime(int id){

        String sql = "UPDATE mails SET sent = ? WHERE id = ?;";
        try {
            Connection connection = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, LocalDateTime.now().toString());
            preparedStatement.setInt(2,id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        }catch (SQLException e) {

            System.out.println(e.getMessage());

            return "Database connection failed";
        }

        return "Sent time was successfully updated";

    }

    public Mail getMail(int id){

        String sql = "SELECT * FROM mails WHERE id = ?";
        Mail mail;
        try {
            Connection connection = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,id);

            ResultSet resultSet =  preparedStatement.executeQuery();

            if(!resultSet.next()) return null;

            mail = new Mail();

            mail.setContent(resultSet.getString("content"));
            mail.setEmailTo(resultSet.getString("mail_to"));


        }catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;
        }

        return mail;

    }





}

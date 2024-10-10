package org.example.Image;

import org.example.DataBaseInfo;

import java.sql.*;

public class ImageReposiroty {

    public String setURL(int id, String URL){

       // String sql = "UPDATE mails SET URL = ? AND image = ? WHERE id = ?;";
        String sql = "UPDATE image SET URL = ? WHERE id = ?;";
        try {
            Connection connection = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
          //  preparedStatement.setString(1, null );
            preparedStatement.setString(1, URL);
            preparedStatement.setInt(2,id);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        }catch (SQLException e) {

            System.out.println(e.getMessage());

            return "Database connection failed";
        }

        return "Image was successfully updated";
    }

    public byte[] getImage(int id){

        byte[] imageFromData;

        String sql = "SELECT * FROM image WHERE id = ?;";

        try {
            Connection connection = DriverManager.getConnection(DataBaseInfo.URL, DataBaseInfo.USERNAME, DataBaseInfo.PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) return null;

            imageFromData = resultSet.getBytes("image");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return null;
        }

        return imageFromData;

    }


}

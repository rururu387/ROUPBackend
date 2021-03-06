package launchClasses;

import DBInteract.*;
import DataProcessing.RawDataAdapter;
import com.google.gson.*;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) {
        System.out.println("Goose");

        DBManager manager = new DBManager();

        try {
            String userName = manager.getUserNameDyId(88);
            System.out.println(userName + "!!!\n\n");
        } catch (Exception e) {
            System.out.println(e);
        }

        UserGroup userGroup = null;
        try {
            userGroup = manager.getGroupInfo(1);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(userGroup);
        System.out.println("------------");

        FrontPageInfoClass frontPageInfo = null;
        RawDataAdapter RDAdapter = new RawDataAdapter(manager);
        try
        {
            frontPageInfo = RDAdapter.getFrontPageInfo(1);
        } catch (Exception e)
        {
            System.out.println(e);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (frontPageInfo != null)
        {
            String frontPageJson = gson.toJson(frontPageInfo);
            System.out.println(frontPageJson);
        }

        try
        {
            boolean registerStatus = manager.registerUser("New Goose", "123");
            System.out.println("Register processed with no errors!");
            if (registerStatus == false)
            {
                System.out.println("User with such login already exists");
            }

        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e);
        }

        try
        {
            boolean var = manager.isUserValid("New Goose", "123");
            System.out.println("Is user registered: " + var);
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            System.out.println(e);
        }

        try
        {
            int id = manager.getUserIdByLogin("Goose");
            System.out.println("User's Goose id: " + id);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }

        LocalDateTime beginObservation, endObservation;

        String pattern = "yyyy-MM-dd HH:mm:ss";
        String beginTimeString = "2021-03-15 00:00:00";
        String endTimeString = "2021-03-17 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        beginObservation = LocalDateTime.from(formatter.parse(beginTimeString));
        endObservation = LocalDateTime.from(formatter.parse(endTimeString));

        ArrayList<UserActivityInfo> userActivityInfo = RDAdapter.getUserTableInfo("Goose", 1, beginObservation, endObservation);
        //TODO - print here correctly
        String str = gson.toJson(userActivityInfo);
        System.out.println(str);

        /*String url = "jdbc:mysql://localhost/test?serverTimezone=Europe/Moscow&useSSL=false";
        String login = "root";
        String password = "root";*/

        //DriverManager driverManager = null;
        /*ResultSet resultSet;
        try
        {
            Connection driverManager = DriverManager.getConnection(url, login, password);
            String queryPattern = "SELECT * FROM users WHERE user_name = ?";
            PreparedStatement statement = driverManager.prepareStatement(queryPattern);
            statement.setString(1, "goose");

            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                System.out.println(resultSet.getInt(1) + resultSet.getString(2) + Arrays.toString(resultSet.getBytes(3)));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }*/
    }
}

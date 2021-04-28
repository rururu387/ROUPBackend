package launchClasses;

import DBInteract.*;
import DataProcessing.RawDataAdapter;
import com.google.gson.*;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Goose");

        DBManager manager = new DBManager();

        try
        {
            String userName = manager.getUserNameDyId(88);
            System.out.println(userName + "!!!\n\n");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        UserGroup userGroup = null;
        try
        {
            userGroup = manager.getGroupInfo(1);
        }catch (Exception e)
        {
            System.out.println(e);
        }

        System.out.println(userGroup);
        System.out.println("------------");

        FrontPageInfoClass frontPageInfo = null;
        RawDataAdapter RDAdapter = new RawDataAdapter(manager);
        try
        {
            frontPageInfo = RDAdapter.getFrontPageInfo(1);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (frontPageInfo != null)
        {
            String frontPageJson = gson.toJson(frontPageInfo);
            System.out.println(frontPageJson);
        }

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

package launchClasses;

import DBInteract.*;
import DataProcessing.RawDataAdapter;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
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

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonDeserializationContext) throws JsonParseException {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss, dd.MM.yyyy");
                return new JsonPrimitive(dateTimeFormatter.format(localDateTime)); //LocalDateTime.parse(json.getAsString(), dateTimeFormatter);
            }
        });
        Gson gson = gsonBuilder.create();

        if (frontPageInfo != null)
        {
            String frontPageJson = gson.toJson(frontPageInfo);
            System.out.println(frontPageJson);
        }

        try
        {
            boolean registerStatus = manager.registerUser("New Goose", "123");
            System.out.println("Register processed with no errors!");
            if (!registerStatus)
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
        String beginTimeString = "2021-03-16 22:00:00";
        String endTimeString = "2021-04-18 22:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        beginObservation = LocalDateTime.from(formatter.parse(beginTimeString));
        endObservation = LocalDateTime.from(formatter.parse(endTimeString));

        Clock clock = Clock.systemDefaultZone();
        Instant start = clock.instant();
        ArrayList<UserActivityInfo> userActivityInfo = RDAdapter.getUserTableInfo("Goose", 4, beginObservation, endObservation);
        Instant end = clock.instant();
        System.out.println(start);
        System.out.println(end);

        //TODO - print here correctly
        String str = gson.toJson(userActivityInfo);
        System.out.println(str);
    }
}

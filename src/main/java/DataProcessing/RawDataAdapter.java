package DataProcessing;

import DBInteract.DBManager;
import DBInteract.FrontPageInfoClass;
import DBInteract.UserData;
import DBInteract.UserActivityInfo;
import exceptions.PrimaryKeyNotUniqueException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RawDataAdapter
{
    DBManager manager;

    RawDataAdapter()
    {
        manager = new DBManager();
    }

    public RawDataAdapter(DBManager manager)
    {
        this.manager = manager;
    }

    /*groupID: int
        groupName: string
        description: string
        userList: [{login: string, role: int},{...}]
        programList: [{name:string},{...}]
    //If there is no group with such id returns null*/
    public FrontPageInfoClass getFrontPageInfo(int groupId) throws PrimaryKeyNotUniqueException, SQLException
    {
        String groupName = null;
        String groupDescription = null;
        try
        {
            var arrayList = manager.getGroupHeaderById(groupId);
            if (arrayList == null)
            {
                return null;
            }

            groupName = arrayList[0];
            groupDescription = arrayList[1];
        }
        catch (SQLException | PrimaryKeyNotUniqueException e)
        {
            System.out.println(e);
            return null;
        }

        Set<UserData> userSet = new HashSet<>();
        try
        {
            userSet = manager.getUserDataByGroupId(groupId);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }

        Set<String> programNamesSet = new HashSet<>();
        if (userSet.size() == 0)
        {
            return new FrontPageInfoClass(groupId, groupName, groupDescription, userSet, programNamesSet);
        }

        try
        {
            programNamesSet = manager.getAllProgramNamesByUserId(userSet.stream().map((element)->element.getId()));
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return new FrontPageInfoClass(groupId, groupName, groupDescription, userSet, programNamesSet);
    }

    public enum UserRole
    {
        //Can invite and kick users in group, view their activities
        Administrator,

        //Can view users' activities
        Analyst,

        //Can provide information about their computers to people above, view own information
        Contributor
    }

    public static UserRole castIntToRole(Integer role)
    {
        if (role == null)
            return null;

        switch (role)
        {
            case 1:
            {
                return UserRole.Administrator;
            }
            case 2:
            {
                return UserRole.Analyst;
            }
            case 3:
            {
                return UserRole.Contributor;
            }
            default:
            {
                return null;
            }
        }
    }

    public static Integer castRoleToInt(UserRole role)
    {
        if (role == null)
        {
            return null;
        }

        switch (role)
        {
            case Administrator:
            {
                return 1;
            }
            case Analyst:
            {
                return 2;
            }
            case Contributor:
            {
                return 3;
            }
            default:
            {
                return null;
            }
        }
    }

    public ArrayList<UserActivityInfo> getUserTableInfo(String userName, int timeUnit, LocalDateTime startDate, LocalDateTime endDate)
    {
        try
        {
            return getUserTableInfo(manager.getUserIdByLogin(userName), timeUnit, startDate, endDate);
        } catch (SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<UserActivityInfo> getUserTableInfo(int userId, int timeUnit, LocalDateTime startDate, LocalDateTime endDate)
    {
        ArrayList<UserActivityInfo> activityInfo = null;
        try
        {
            activityInfo = manager.getUserActivity(userId, startDate, endDate);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        //Sum up packages here (to change time scale in our table)
        timeUnit = 1;
        switch(timeUnit)
        {
            case 1:
            {

            }
        }
        return activityInfo;
    }
}

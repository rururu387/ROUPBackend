package DataProcessing;

import DBInteract.DBManager;
import DBInteract.FrontPageInfoClass;
import DBInteract.UserData;
import DBInteract.UserActivityInfo;
import exceptions.PrimaryKeyNotUniqueException;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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

    public ArrayList<UserActivityInfo> getUserTableInfo(int userId, int timeScale, LocalDateTime startDate, LocalDateTime endDate)
    {
        ArrayList<UserActivityInfo> dividedActivityInfo = new ArrayList<>();

        LocalDateTime scaledTimeStart = startDate;
        LocalDateTime scaledTimeEnd = startDate.plusHours(timeScale);
        ArrayList<UserActivityInfo> activityInfoInTimeScale = null;

        while (endDate.isAfter(scaledTimeEnd) || endDate.isEqual(scaledTimeEnd))
        {
            try
            {
                activityInfoInTimeScale = manager.getUserActivity(userId, scaledTimeStart, scaledTimeEnd);
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }

            if (activityInfoInTimeScale.size() == 0)
            {
                scaledTimeStart = scaledTimeEnd;
                scaledTimeEnd = scaledTimeEnd.plusHours(timeScale);
                continue;
            }

            String name = null;
            double cpuUsage = 0;
            BigInteger ramUsage = new BigInteger("0");
            int threadAmount = 0;
            int activeWindowTime = 0;
            int windowTime = 0;
            int packagesAmount = 0;
            LocalDateTime creationTime = null;

            ListIterator<UserActivityInfo> activityInfoListIterator = activityInfoInTimeScale.listIterator();

            UserActivityInfo hourInfo;
            int hoursProcessed = 0;
            while (activityInfoListIterator.hasNext())
            {
                hourInfo = activityInfoListIterator.next();

                if (name == null)
                {
                    name = hourInfo.getName();
                    creationTime = hourInfo.getCreationDate();
                }

                if (name.equals("idea64.exe"))
                {
                    System.out.println("On chrome!");
                }

                if(!hourInfo.getName().equals(name))
                {
                    dividedActivityInfo.add(new UserActivityInfo(
                            name,
                            creationTime,
                            (cpuUsage / hoursProcessed),
                            ramUsage.divide(BigInteger.valueOf(hoursProcessed)).longValue(),
                            (threadAmount / hoursProcessed),
                            activeWindowTime,
                            windowTime,
                            packagesAmount
                    ));

                    if (!hourInfo.getName().equals(name))
                    {
                        name = hourInfo.getName();
                        cpuUsage = 0;
                        ramUsage = new BigInteger("0");
                        threadAmount = 0;
                        activeWindowTime = 0;
                        windowTime = 0;
                        packagesAmount = 0;
                        creationTime = hourInfo.getCreationDate();
                        hoursProcessed = 0;
                    }
                }

                cpuUsage += hourInfo.getCpuUsage();
                ramUsage = ramUsage.add(BigInteger.valueOf(hourInfo.getRamUsage()));
                threadAmount += hourInfo.getThreadAmount();
                activeWindowTime += hourInfo.getActiveWindowTime();
                windowTime += hourInfo.getWindowTime();
                packagesAmount += hourInfo.getPackagesAmount();
                hoursProcessed++;
            }

            dividedActivityInfo.add(new UserActivityInfo(
                    name,
                    creationTime,
                    cpuUsage / hoursProcessed,
                    ramUsage.divide(BigInteger.valueOf(hoursProcessed)).longValue(),
                    threadAmount / hoursProcessed,
                    activeWindowTime,
                    windowTime,
                    packagesAmount
            ));

            scaledTimeStart = scaledTimeEnd;
            scaledTimeEnd = scaledTimeEnd.plusHours(timeScale);
            /*if (hoursProcessed != 0)
            {
                dividedActivityInfo.add(new UserActivityInfo(
                        name,
                        creationTime,
                        cpuUsage / hoursProcessed,
                        ramUsage.divide(BigInteger.valueOf(hoursProcessed)).longValue(),
                        threadAmount / hoursProcessed,
                        activeWindowTime,
                        windowTime,
                        packagesAmount
                ));
            }*/
        }
        return dividedActivityInfo;
    }
}

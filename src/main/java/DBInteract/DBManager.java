package DBInteract;

import DataProcessing.Encryptor;
import DataProcessing.RawDataAdapter;
import exceptions.PrimaryKeyNotUniqueException;

import javax.xml.transform.Result;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBManager
{
    Connection connection = null;

    public DBManager()
    {
        connection = getConnection();
    }

    public Connection getConnection()
    {
        String url = "jdbc:mysql://localhost/test?serverTimezone=Europe/Moscow&useSSL=false";
        String login = "root";
        String password = "root";
        try
        {
            connection = DriverManager.getConnection(url, login, password);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }

        return connection;
    }

    public String[] getGroupHeaderById(int groupId) throws PrimaryKeyNotUniqueException, SQLException
    {
        if (connection == null)
        {
            return null;
        }

        String groupQueryPattern = "SELECT group_name, group_description FROM usergroup WHERE group_id = ?";
        ResultSet resultSet = null;
        String groupNameAndDescription[] = new String[2];
        try (PreparedStatement statement = connection.prepareStatement(groupQueryPattern))
        {
            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();
            if (!resultSet.next())
            {
                return null;
            }

            groupNameAndDescription[0] = resultSet.getString(1);
            groupNameAndDescription[1] = resultSet.getString(2);

            if (resultSet.next())
            {
                throw new PrimaryKeyNotUniqueException("usergroup", "group_id");
            }
        }
        return groupNameAndDescription;
    }

    public Set<UserData> getUserDataByGroupId(int groupId) throws SQLException
    {
        ResultSet resultSet = null;
        Set<UserData> userInfo = new HashSet<>();
        String usersQueryPattern = "SELECT users.user_id, user_name, privilege " +
                "FROM usergroup " +
                "JOIN groupmembers ON usergroup.group_id = groupmembers.group_id " +
                "JOIN users ON groupmembers.user_id = users.user_id " +
                "WHERE usergroup.group_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(usersQueryPattern))
        {
            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                userInfo.add(new UserData(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));
            }
        }
        return userInfo;
    }

    public Set<String> getAllProgramNamesByUserId(Stream<Integer> userIds) throws SQLException
    {
        ResultSet resultSet = null;
        Set<String> programNames = new HashSet<>();
        String programsQueryPattern = "SELECT program_name FROM program WHERE user_id IN (";
        programsQueryPattern += userIds.map(Object::toString).collect(Collectors.joining(", "))+ ")";
        //String programsQueryPattern = String.format("SELECT program_name FROM program WHERE user_id IN (%s)", String.join(", ", userIds.;

        try (PreparedStatement statement = connection.prepareStatement(programsQueryPattern))
        {
            resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                programNames.add(resultSet.getString(1));
            }
        }
        return programNames;
    }

    public String getUserNameDyId(int id) throws PrimaryKeyNotUniqueException, SQLException
    {
        ResultSet resultSet = null;
        String retVal = null;
        String queryString = "SELECT user_name FROM users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryString))
        {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (!resultSet.next())
            {
                return null;
            }
            retVal = resultSet.getString(1);
            if (resultSet.next())
            {
                throw new PrimaryKeyNotUniqueException("users", "user_name");
            }
        }
        return retVal;
    }

    public boolean isUserExists(String login) throws SQLException
    {
        ResultSet resultSet = null;
        String queryString = "SELECT 1 FROM users WHERE user_name = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryString))
        {
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (!resultSet.next())
                return false;
        }

        return true;
    }

    public boolean registerUser(String login, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        byte[] securedPassword = Encryptor.getPBKDF2SecurePassword(login, password);
        if (isUserExists(login))
        {
            return false;
        }

        String queryString = "INSERT INTO users (user_name, password) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(queryString))
        {
            statement.setString(1, login);
            statement.setBytes(2, securedPassword);

            int rowsAdded = statement.executeUpdate();
        }
        return true;
    }

    public boolean isUserValid(String login, String password) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        byte[] securedPassword = Encryptor.getPBKDF2SecurePassword(login, password);
        ResultSet resultSet = null;

        String queryString = "SELECT 1 FROM users WHERE user_name = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryString))
        {
            statement.setString(1, login);
            statement.setBytes(2, securedPassword);

            resultSet = statement.executeQuery();

            if (!resultSet.next())
            {
                return false;
            }
        }
        return true;
    }

    public RawDataAdapter.UserRole getUserRole(String login, int groupId) throws SQLException
    {
        Integer userId = getUserIdByLogin(login);
        if (userId == null)
        {
            return null;
        }

        ResultSet resultSet = null;

        String queryString = "SELECT privilege FROM groupmembers WHERE group_id = ? AND user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryString))
        {
            statement.setInt(1, groupId);
            statement.setInt(2, userId);

            resultSet = statement.executeQuery();
            if (!resultSet.next())
            {
                return null;
            }

            return RawDataAdapter.castIntToRole(resultSet.getInt(1));
        }
    }

    public boolean isUserInGroup(String login, int groupId) throws SQLException
    {
        ResultSet resultSet = null;
        Integer userId = getUserIdByLogin(login);

        String queryString = "SELECT 1 FROM groupmembers WHERE group_id = ? AND userId = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryString))
        {
            statement.setInt(1, groupId);
            statement.setInt(2, userId);

            resultSet = statement.executeQuery();

            return resultSet.next();
        }
    }

    public Integer getUserIdByLogin(String login) throws SQLException
    {
        ResultSet resultSet = null;
        String queryString = "SELECT user_id FROM users WHERE user_name = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryString))
        {
            statement.setString(1, login);

            resultSet = statement.executeQuery();
            if (!resultSet.next())
            {
                return null;
            }
            return resultSet.getInt(1);
        }
    }

    public ArrayList<UserActivityInfo> getUserActivity(int userId, LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        ArrayList<UserActivityInfo> rawHourlyData = new ArrayList<>();
        ResultSet resultSet = null;
        String queryString = "SELECT program.program_name, creation_date, cpu_usage, ram_usage, thread_amount, time_act_sum, time_sum, data_pack_count FROM hourinfo JOIN program ON hourinfo.program_id = program.program_id JOIN users ON program.user_id = users.user_id WHERE users.user_id = ? AND creation_date >= ? AND creation_date < ? ORDER BY program.program_name ASC, creation_date ASC";
        try (PreparedStatement statement = connection.prepareStatement(queryString))
        {
            statement.setInt(1, userId);
            statement.setTimestamp(2, Timestamp.valueOf(startDate));
            statement.setTimestamp(3, Timestamp.valueOf(endDate));
            resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                rawHourlyData.add(new UserActivityInfo(
                    resultSet.getString(1), //name
                    resultSet.getTimestamp(2).toLocalDateTime(), //creation date
                    resultSet.getDouble(3), //cpu usage
                    resultSet.getLong(4), //ram usage
                    resultSet.getInt(5), //thread amount
                    resultSet.getInt(6), //time act sum
                    resultSet.getInt(7), //time sum
                    resultSet.getInt(8) //data pack count
                ));
            }
        }
        return rawHourlyData;
    }

      /*groupID: int
        groupName: string
        description: string
        userList: [{login: string, role: int},{...}]
        programList: [{name:string},{...}]
    //If there is no group with such id returns null*/
    /*public FrontPageInfoClass getFrontPageInfo(int groupId) throws PrimaryKeyNotUniqueException, SQLException
    {
        if (connection == null)
        {
            return null;
        }

        String groupQueryPattern = "SELECT group_name, group_description FROM usergroup WHERE group_id = ?";
        ResultSet resultSet = null;
        String groupName = null;
        String groupDescription = null;
        try (PreparedStatement statement = connection.prepareStatement(groupQueryPattern))
        {
            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();
            if (!resultSet.next())
            {
                return null;
            }

            groupName = resultSet.getString(1);
            groupDescription = resultSet.getString(2);

            if (resultSet.next())
            {
                throw new PrimaryKeyNotUniqueException("usergroup", "group_id");
            }
        }

        ArrayList<UserData> userInfo = new ArrayList<>();
        ArrayList<String> userIds = new ArrayList<>();
        String usersQueryPattern = "SELECT user_name, privilege, users.user_id " +
                "FROM usergroup " +
                "JOIN groupmembers ON usergroup.group_id = groupmembers.group_id " +
                "JOIN users ON groupmembers.user_id = users.user_id " +
                "WHERE usergroup.group_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(usersQueryPattern))
        {
            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();

            boolean isUsersExist = false;
            while (resultSet.next())
            {
                isUsersExist = true;

                userInfo.add(new UserData(
                        resultSet.getString(1),
                        resultSet.getInt(2)
                        ));
                userIds.add(String.valueOf(resultSet.getInt(3)));
            }
            if (!isUsersExist)
            {
                return new FrontPageInfoClass(groupId, groupName, groupDescription, null, null);
            }
        }


        String programsQueryPattern = String.format("SELECT program_name FROM program WHERE user_id IN (%s)", String.join(", ", userIds));


        Set<String> programNames = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement(programsQueryPattern))
        {
            resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                programNames.add(resultSet.getString(1));
            }
        }

        return new FrontPageInfoClass(groupId, groupName, groupDescription, userInfo, programNames);
    }*/

    //Get UserGroup by id, returns null if no group exists. Fails if 2 groups with same id.
    public UserGroup getGroupInfo(int groupID) throws PrimaryKeyNotUniqueException, SQLException
    {
        if (connection == null)
        {
            return null;
        }

        UserGroup retVal = null;
        String queryPattern = "SELECT * FROM usergroup WHERE group_id = ?";
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(queryPattern))
        {
            statement.setInt(1, groupID);
            resultSet = statement.executeQuery();


            if(!resultSet.next())
            {
                return null;
            }
            retVal = new UserGroup(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getTimestamp(3).toLocalDateTime(),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );

            if (resultSet.next())
            {
                throw new PrimaryKeyNotUniqueException("usergroup", "group_id");
            }
        }
        return retVal;
    }
}

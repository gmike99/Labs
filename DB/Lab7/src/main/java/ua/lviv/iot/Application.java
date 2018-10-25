package ua.lviv.iot;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ua.lviv.iot.model.AddCompanyEntity;
import ua.lviv.iot.model.FootballPlayerEntity;

import java.sql.*;
import java.util.Scanner;

public class Application {

    private static SessionFactory mSessionFactory;

    //------------------------------------------------------------

    private static final String url =
            "jdbc:mysql://localhost:3306/lab6?serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "admin";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet rs = null;

    private static Session mSession = null;

    //-------------------------------------------------------------

    static {
        try { // Create the SessionFactory from hibernate.cfg.xml
            mSessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return mSessionFactory.openSession(); //return opened session
    }

    public static void main(String args[]) {

//        mSession = getSession();

        try {
            //This will load the MySQL driver, each DB has its own driver //
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Get a connection to database //
            connection = DriverManager.getConnection(url, user, password);
            // Create a statement
            // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();


        startApp();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver is not loaded");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {
            //close connection ,statement and resultset
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } // ignore
            if (statement != null) try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private static void startApp() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select your command: ");
        System.out.println("-----View--------: \n" +
                "Players: v1\n" +
                "Clubs: v2\n" +
                "Companies: v3\n" +
                "-----Insert--------: \n" +
                "Players: i1\n" +
                "Clubs: i2\n" +
                "Companies: i3\n" +
                "-----Update--------: \n" +
                "Players: u1\n" +
                "Clubs: u2\n" +
                "Companies: u3\n" +
                "-----Delete--------: \n" +
                "Players: d1\n" +
                "Clubs: d2\n" +
                "Companies: d3\n" +
                "-----Insert PlayerCompanies--------: \n" +
                "ipc");

        String cmd = sc.next();

        if (cmd.equals("v1")) {
            readPlayers(mSession);
        } else if (cmd.equals("v2")) {
            readClubs();
        } else if (cmd.equals("v3")) {
            readCompanies();
        } else if (cmd.equals("i1")) {
            insertFootballPlayer();
        } else if (cmd.equals("i2")) {
            insertFootballClub();
        } else if (cmd.equals("i3")) {
            insertCompany();
        } else if (cmd.equals("u1")) {
            updateFootballPlayer();
        } else if (cmd.equals("u2")) {
            updateFootballClub();
        } else if (cmd.equals("u3")) {
            updateCompany();
        } else if (cmd.equals("d1")) {
            deleteFootballPlayer();
        } else if (cmd.equals("d2")) {
            deleteFootballClub();
        } else if (cmd.equals("d3")) {
            deleteCompany();
        } else if (cmd.equals("ipc")) {
            callProcedureForInsertToPlayersCompanies();
        } else {
            System.out.println("Invalid cmd, try again");
        }

        System.out.println("Procceed? (y/N)");
        cmd = sc.next();
        if (cmd.equals("y")) {
            startApp();
        } else {
            System.exit(0);
        }

    }



    private static void readPlayers(Session session) throws SQLException{

        Query query = session.createQuery("FROM " + "FootballPlayerEntity ");
        System.out.format("\nTable Person --------------------\n");
        System.out.format("%-18s %-18s %-12s %-10s %-16s\n",
                "Last Name", "First Name", "Years Of XP", "Player ID", "Club Name");
        for (Object obj : query.list()) {
            FootballPlayerEntity player = (FootballPlayerEntity) obj;
            System.out.format("%3s %-12s %-12s\n", player.getLastName(), player.getFirstName(),
                    player.getYearsOfExperience(), player.getPlayerId());
            for (AddCompanyEntity addCompanyEntity : player.getCompanies()) {
                System.out.format("\t\t%s // %s\n", addCompanyEntity.getCompanyName());
            }
        }

    }

    private static void readClubs() throws SQLException {
        rs = statement.executeQuery("SELECT * FROM football_club");

        //Process the result set
        System.out.format("\n---------------------Table FootballClub --------------------\n");
        System.out.format("%-20s %-16s %-16s %-16s\n", "Club Name", "Club Owner Name", "Coach Name", "Country");
        while (rs.next()) {
            String clubName = rs.getString("club_name");
            String clubOwnerName = rs.getString("club_owner_name");
            String coachName = rs.getString("coach_name");
            String country = rs.getString("country");
            // Simply Print the results
            System.out.format("%-20s %-16s %-16s %-16s\n", clubName, clubOwnerName, coachName, country);
        }
    }

    private static void readCompanies() throws SQLException {

        rs = statement.executeQuery("SELECT * FROM add_company");

        //Process the result set
        System.out.format("\n-------------------Table Company--------------------\n");
        System.out.format("%-16s %-16s %-16s %-16s\n", "Name", "Budget", "Industry", "Country");
        while (rs.next()) {
            String companyName = rs.getString("company_name");
            double budget = rs.getDouble("budget");
            String industry = rs.getString("industry");
            String country = rs.getString("counry");
            // Simply Print the results
            System.out.format("%-16s %-16f %-16s %-16s\n", companyName, budget, industry, country);
        }

    }


    private static void insertFootballPlayer() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a player Last Name: ");
        String lastName = input.next();
        System.out.println("Input a player First Name: ");
        String firstName = input.next();
        System.out.println("Input a player Experience: ");
        int exp = input.nextInt();
        System.out.println("Input a player Club: ");
        String club = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO football_player VALUES (?, ?, ? , ?)");
        preparedStatement.setString(1, lastName);
        preparedStatement.setString(2, firstName);
        preparedStatement.setInt(3, exp);
        preparedStatement.setString(4, club);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: " + n);

    }

    private static void insertFootballClub() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a Club Name: ");
        String name = input.next();
        System.out.println("Input a Club Owner: ");
        String owner = input.next();
        System.out.println("Input a Club Coach: ");
        String coach = input.next();
        System.out.println("Input a Club Country: ");
        String country = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO football_club VALUES (?, ?, ? , ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, owner);
        preparedStatement.setString(3, coach);
        preparedStatement.setString(4, country);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: " + n);

    }

    private static void insertCompany() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a Company Name: ");
        String name = input.next();
        System.out.println("Input a Company Budget: ");
        double budget = input.nextDouble();
        System.out.println("Input a Company Industry: ");
        String industry = input.next();
        System.out.println("Input a Company Country: ");
        String country = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO football_club VALUES (?, ?, ? , ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setDouble(2, budget);
        preparedStatement.setString(3, industry);
        preparedStatement.setString(4, country);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: " + n);

    }


    private static void updateFootballPlayer() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input Last Name for the player you want to update: ");
        String lastName = input.next();
        System.out.println("Input First Name for the player you want to update: ");
        String firstName = input.next();
        System.out.println("Input new Last Name: ");
        String newLastName = input.next();
        System.out.println("Input new First Name: ");
        String newFirstName = input.next();

        CallableStatement callableStatement;
        callableStatement = connection.prepareCall("{CALL update_football_player(?, ?, ?, ?)}");
        callableStatement.setString(1,lastName);
        callableStatement.setString(2, firstName);
        callableStatement.setString(3, newLastName);
        callableStatement.setString(4, newFirstName);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next()) {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: " + msg);
        }
    }

    private static void updateFootballClub() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input name for the club you want to update: ");
        String oldName = input.next();
        System.out.println("Input new club name: ");
        String newName = input.next();


        CallableStatement callableStatement;
        callableStatement = connection.prepareCall("{CALL update_football_club(?, ?)}");
        callableStatement.setString(1,oldName);
        callableStatement.setString(2, newName);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next()) {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: " + msg);
        }
    }


    private static void updateCompany() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input name for the company you want to update: ");
        String oldName = input.next();
        System.out.println("Input new company name: ");
        String newName = input.next();


        CallableStatement callableStatement;
        callableStatement = connection.prepareCall("{CALL update_company(?, ?)}");
        callableStatement.setString(1,oldName);
        callableStatement.setString(2, newName);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next()) {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: " + msg);
        }
    }

    private static void deleteFootballPlayer() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a last name of player to delete: ");
        String lastName = input.next();
        System.out.println("Input a first name of player to delete: ");
        String firstName = input.next();


        //PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM football_player WHERE last_name=? AND first_name=?");
        preparedStatement.setString(1, lastName);
        preparedStatement.setString(2, firstName);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: " + n);
    }

    private static void deleteFootballClub() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a name of club to delete: ");
        String name = input.next();

        //PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM football_club WHERE club_name=?");
        preparedStatement.setString(1, name);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: " + n);
    }

    private static void deleteCompany() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a name of club to delete: ");
        String name = input.next();



        //PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM add_company WHERE company_name=?");
        preparedStatement.setString(1, name);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: " + n);
    }

    private static void callProcedureForInsertToPlayersCompanies() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput Last Name For Player: ");
        String lastName = input.next();
        System.out.println("Input First Name For Player: ");
        String firstName = input.next();
        System.out.println("Input Company Name: ");
        String companyName = input.next();

        CallableStatement callableStatement;
        callableStatement = connection.prepareCall("{CALL insert_playerscompanies(?, ?, ?)}");
        callableStatement.setString(1, lastName);
        callableStatement.setString(2, firstName);
        callableStatement.setString(3, companyName);
        ResultSet rs = callableStatement.executeQuery();

        while (rs.next()) {
            String msg = rs.getString(1);
            // Simply Print the results
            System.out.format("\nResult: " + msg);
        }
    }

}


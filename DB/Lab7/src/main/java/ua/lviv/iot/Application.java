package ua.lviv.iot;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ua.lviv.iot.model.AddCompanyEntity;
import ua.lviv.iot.model.FootballClubEntity;
import ua.lviv.iot.model.FootballPlayerEntity;


import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Scanner;

public class Application {

    private static SessionFactory mSessionFactory;


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

    public static void main(final String[] args) throws Exception {
        // get opened session
        Session session = getSession();
        try {
            readPlayerOfCompany(session);
            System.out.println("\nFinish work!");
        } finally {
            session.close();
            System.exit(0);
        }
    }

    private static void readTableData(Session session) {
        Query query = session.createQuery("from " + "FootballPlayerEntity");
        System.out.format("\n--------------------------FootballPlayer----------------------------\n");
        System.out.format("%-12s %-12s %-10s %-18s %-6s\n", "Last Name", "First Name",
                "YearsOfXp", "Club Name", "ID");
        for (Object obj: query.list()) {
            FootballPlayerEntity playerEntity = (FootballPlayerEntity) obj;
            System.out.format("%-12s %-12s %-10s %-18s %-6s\n", playerEntity.getLastName(),
                    playerEntity.getFirstName(), playerEntity.getYearsOfExperience(),
                    playerEntity.getClubByClub().getClubName(), playerEntity.getPlayerId());
        }

        query = session.createQuery("FROM " + "FootballClubEntity");
        System.out.format("\n--------------------------FootballClub----------------------------\n");
        System.out.format("%-18s %-16s %-10s %-12s\n", "ClubName", "ClubOwnerName",
                "CoachName", "Country");
        for (Object obj: query.list()) {
            FootballClubEntity clubEntity = (FootballClubEntity) obj;
            System.out.format("%-18s %-16s %-10s %-12s\n", clubEntity.getClubName(),
                    clubEntity.getClubOwnerName(), clubEntity.getCoachName(),
                    clubEntity.getCountry());
        }

        query = session.createQuery("FROM " + "AddCompanyEntity");
        System.out.format("\n--------------------------AddCompany----------------------------\n");
        System.out.format("%-18s %-16s %-14s %-12s\n", "CompanyName", "Budget",
                "Industry", "Country");
        for (Object obj: query.list()) {
            AddCompanyEntity companyEntity = (AddCompanyEntity) obj;
            System.out.format("%-18s %-16s %-14s %-12s\n", companyEntity.getCompanyName(),
                    companyEntity.getBudget(), companyEntity.getIndustry(),
                    companyEntity.getCounry());
        }
    }

    private static void readPlayerOfCompany(Session session) {
        Query query = session.createQuery("FROM " + "AddCompanyEntity ");
        System.out.format("\n--------------------------AddCompany----------------------------\n");
        System.out.format("%-18s %-12s\n", "CompanyName", "Players");
        for (Object obj: query.list()) {
            AddCompanyEntity companyEntity = (AddCompanyEntity) obj;
            System.out.format("%-18s: ", companyEntity.getCompanyName());
            for (FootballPlayerEntity player: companyEntity.getPlayers()) {
                System.out.format("%s, %s;    ", player.getLastName(), player.getFirstName());
            }
            System.out.println();
        }
    }

    private static void readCompanyOfPlayer(Session session) {
        Query query = session.createQuery("FROM " + "FootballPlayerEntity ");
        System.out.format("\n--------------------------FootballPlayer----------------------------\n");
        System.out.format("%-22s %-12s\n", "PlayerName", "Companies");
        for (Object obj: query.list()) {
            FootballPlayerEntity playerEntity = (FootballPlayerEntity) obj;
            System.out.format("%-22s: ", playerEntity.getLastName() + " " + playerEntity.getFirstName());
            for (AddCompanyEntity companyEntity: playerEntity.getCompanies()) {
                System.out.format("%s;    ", companyEntity.getCompanyName());
            }
            System.out.println();
        }
    }

    private static void insertCompany(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new Company Name:  ");
        String newCompany = input.next();

        session.beginTransaction();
        AddCompanyEntity companyEntity = new AddCompanyEntity();
        companyEntity.setCompanyName(newCompany);
        session.save(companyEntity);
        session.getTransaction().commit();

        System.out.println("Company inserted");
    }

    private static void updateCompany(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input an old Company Name to update:  ");
        String oldCompany = input.next();
        System.out.println("Input a new Company Name to update:  ");
        String newCompany = input.next();

        AddCompanyEntity companyEntity = session.load(AddCompanyEntity.class, oldCompany);
        if (companyEntity != null) {
            session.beginTransaction();
            Query query = session.createQuery("update AddCompanyEntity set companyName=:newName where companyName=:oldName");
            query.setParameter("newName", newCompany);
            query.setParameter("oldName", oldCompany);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Company updated");
        } else {
            System.out.println("There is no such city");
        }
    }

    private static void deleteCompany(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new Company Name to delete:  ");
        String deletedCompany = input.next();

        AddCompanyEntity companyEntity = session.load(AddCompanyEntity.class, deletedCompany);

        if (companyEntity != null) {
            session.beginTransaction();
            Query query = session.createQuery("delete AddCompanyEntity where companyName=:delCompany");
            query.setParameter("delCompany", deletedCompany);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Company deleted");
        } else {
            System.out.println("There is no such company");
        }
    }

    public static void insertPlayerscompaniesWithProcedure(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput PersonID: ");
        Integer personId = input.nextInt();
        System.out.println("Input Company Name: ");
        String companyName = input.next();

        //from JPA 2.1
        StoredProcedureQuery query = session
                .createStoredProcedureQuery("insert_playerscompanies")
                .registerStoredProcedureParameter("player_id_in", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("company_name", String.class, ParameterMode.IN)
                .setParameter("player_id_in", personId)
                .setParameter("company_name", companyName);
        query.execute();
        String str = (String) query.getResultList().get(0);
        System.out.println(str);
        System.out.println("executed");
        if (str.equals("OK")) {
            Query query2 = session.createQuery("from " + "FootballPlayerEntity ");
            for (Object obj : query2.list()) {
                session.refresh(obj);
            }
            query2 = session.createQuery("from " + "AddCompanyEntity ");
            for (Object obj : query2.list()) {
                session.refresh(obj);
            }
        }
    }

    public static void deletePlayercompanyRelationShip(Session session) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput PersonID: ");
        Integer personId = input.nextInt();
        System.out.println("Input Company Name: ");
        String companyName = input.next();

        //from JPA 2.1
        StoredProcedureQuery query = session
                .createStoredProcedureQuery("delete_playerscompanies")
                .registerStoredProcedureParameter("player_id_in", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("company_name", String.class, ParameterMode.IN)
                .setParameter("player_id_in", personId)
                .setParameter("company_name", companyName);
        query.execute();
        String str = (String) query.getResultList().get(0);
        System.out.println(str);
        System.out.println("executed");
        if (str.equals("OK")) {
            Query query2 = session.createQuery("from " + "FootballPlayerEntity ");
            System.out.println("just");
            for (Object obj : query2.list()) {
                session.refresh(obj);

            }
            query2 = session.createQuery("from " + "AddCompanyEntity ");
            for (Object obj : query2.list()) {
                session.refresh(obj);
            }
        }
    }


    private static void startApp() {
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
                "ipc\n" +
                "------Delete PlayerCompanies--------- \n" +
                "dpc");


        String cmd = sc.next();

        if (cmd.equals("v1")) {
            readTableData(getSession());
        } else if (cmd.equals("v2")) {
            readTableData(getSession());
        } else if (cmd.equals("v3")) {
            readTableData(getSession());
        } else if (cmd.equals("i1")) {
            insertCompany(getSession());
        } else if (cmd.equals("i2")) {
            insertCompany(getSession());
        } else if (cmd.equals("i3")) {
            insertCompany(getSession());
        } else if (cmd.equals("u1")) {
            updateCompany(getSession());
        } else if (cmd.equals("u2")) {
            updateCompany(getSession());
        } else if (cmd.equals("u3")) {
            updateCompany(getSession());
        } else if (cmd.equals("d1")) {
            deleteCompany(getSession());
        } else if (cmd.equals("d2")) {
            deleteCompany(getSession());
        } else if (cmd.equals("d3")) {
            deleteCompany(getSession());
        } else if (cmd.equals("ipc")) {
            insertPlayerscompaniesWithProcedure(getSession());
        } else if (cmd.equals("dpc")) {
            deletePlayercompanyRelationShip(getSession());
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
}





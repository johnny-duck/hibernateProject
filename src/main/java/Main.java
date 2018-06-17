
public class Main {
    public static void main(String[] args) {

        Client c = new Client(DbUtilities.getSession());
        c.start();
        DbUtilities.closeSession();
    }
}

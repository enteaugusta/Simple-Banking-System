package banking;

import org.sqlite.SQLiteDataSource;

public class Main {
    
    public static void main(String[] args) {
        if (args.length > 1 && args[0].equals("-fileName") && args[1] != null) {
            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
            sqLiteDataSource.setUrl("jdbc:sqlite:" + args[1]);
            Database database = new Database(sqLiteDataSource);
            Controller controller = new Controller(database);
            controller.runBankingSystem();
        } else {
            System.out.println("No Database found.");
        }
    
    }
}

package banking;

public interface AccountTable {
    String table_name = "card";
    
    String create_table = "CREATE TABLE "+ table_name +"\n ("
            +"id INTEGER,\n"
            +"number TEXT,\n"
            +"pin  TEXT,\n"
            +"balance INTEGER DEFAULT 0"
            +")";


    String insert = "INSERT INTO " + table_name + " (number,pin,balance) "
            +"VALUES(?,?,?)";
    
    String update_balance = "UPDATE " + table_name + " SET balance = balance + ? WHERE number = ?";

    String get_account_number = "SELECT * FROM " + table_name + " WHERE number = ?";
    
    String get_balance = "SELECT balance FROM " + table_name + " WHERE number = ?";
    
    String do_tranfer = "UPDATE " + table_name + " SET balance = balance - ? WHERE number = ?";
    
    String delete = "DELETE FROM " + table_name + " WHERE number = ?";
}

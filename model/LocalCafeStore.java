package cafeshopmanagementsystem.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Volatile in-memory store used by the desktop demo. */
public final class LocalCafeStore {
    public static final class Account {
        public String password;
        public String question;
        public String answer;
        public Account(String password, String question, String answer) {
            this.password = password;
            this.question = question;
            this.answer = answer;
        }
    }

    public static final List<productData> products = new ArrayList<productData>();
    public static final List<productData> order = new ArrayList<productData>();
    public static final List<customersData> receipts = new ArrayList<customersData>();
    public static final Map<String, Account> accounts = new LinkedHashMap<String, Account>();
    private static int nextId = 1;
    private static int nextCustomerId = 1;

    static {
        accounts.put("admin", new Account("admin123", "What is your favorite Color?", "blue"));
        Date today = new Date(System.currentTimeMillis());
        products.add(new productData(nextId++, "CAF-001", "Café americano", "Drinks", 25, 35.0, "Available", "", today));
        products.add(new productData(nextId++, "CAF-002", "Capuchino", "Drinks", 20, 48.0, "Available", "", today));
        products.add(new productData(nextId++, "CAF-003", "Croissant", "Meals", 15, 42.0, "Available", "", today));
    }

    public static int nextId() { return nextId++; }
    public static int nextCustomerId() { return nextCustomerId++; }
    private LocalCafeStore() { }
}

package Product;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountAdmin implements Serializable {
    private String adminAccount;
    private String adminPassword;
    private final ArrayList<AccountAdmin> listAccountAdmin = new ArrayList<>();

    public AccountAdmin(String adminAccount, String adminPassword) {
        this.adminAccount = adminAccount;
        this.adminPassword = adminPassword;
    }

    public AccountAdmin() {
        listAccountAdmin.add(new AccountAdmin("admin", "admin"));
        listAccountAdmin.add(new AccountAdmin("admin1", "admin1"));
        listAccountAdmin.add(new AccountAdmin("admin2", "admin2"));
        listAccountAdmin.add(new AccountAdmin("admin3", "admin3"));
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public ArrayList<AccountAdmin> getListAccountAdmin() {
        return listAccountAdmin;
    }
}

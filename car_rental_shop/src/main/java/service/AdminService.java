package service;

import dao.AdminDao;
import model.admin;

public class AdminService {
    private final AdminDao adminDao;

    public AdminService() {
        this.adminDao = new AdminDao();
    }

    public String addAdmin(admin adminObj) {
        return adminDao.addAdmin(adminObj);
    }

    public void showAllAdmins() {
        adminDao.showAllAdmins();
    }

    public void deleteAdmin(String id) {
        adminDao.deleteAdmin(id);
    }

    public void updateAdmin(String id, admin adminObj) {
        adminDao.updateAdmin(id, adminObj);
    }
}

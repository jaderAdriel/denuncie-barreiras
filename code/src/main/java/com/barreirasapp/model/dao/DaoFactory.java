package com.barreirasapp.model.dao;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.model.dao.impl.UserDaoJDBC;

public class DaoFactory {
    public static UserDao createUserDao() {
        return new UserDaoJDBC(DatabaseConnection.getConnection());
    }
}

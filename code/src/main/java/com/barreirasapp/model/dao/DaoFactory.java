package com.barreirasapp.model.dao;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.model.dao.impl.SessionDaoJDBC;
import com.barreirasapp.model.dao.impl.UserDaoJDBC;

public class DaoFactory {
    public static UserDao createUserDao() {
        return new UserDaoJDBC(DatabaseConnection.getConnection());
    }
    public static SessionDao createSessionDao() {
        return new SessionDaoJDBC(DatabaseConnection.getConnection());
    }
}

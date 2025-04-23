package com.barreirasapp.model.dao;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.model.dao.impl.PersonDaoJDBC;

public class DaoFactory {
    public static PersonDao createPersonDao() {
        return new PersonDaoJDBC(DatabaseConnection.getConnection());
    }
}

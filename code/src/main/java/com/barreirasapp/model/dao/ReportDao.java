package com.barreirasapp.model.dao;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.entities.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ReportDao extends GenericDao<Report> {
}

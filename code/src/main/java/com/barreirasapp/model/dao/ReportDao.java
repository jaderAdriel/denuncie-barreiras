package com.barreirasapp.model.dao;


import com.barreirasapp.model.entities.Report;

import java.util.List;

public interface ReportDao extends GenericDao<Report> {
    List<Report> findAllByReporterId(Integer id);
}

package com.barreirasapp.model.dao;


import com.barreirasapp.model.entities.Report;

import java.util.List;

public interface ReportDao extends GenericDao<Report, Integer> {
    List<Report> findAllByReporterId(Integer id);
}

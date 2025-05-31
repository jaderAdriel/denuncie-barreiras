package com.barreirasapp.repositories;


import com.barreirasapp.entities.Report;

import java.util.List;

public interface ReportRepository extends GenericRepository<Report, Integer> {
    List<Report> findAllByReporterId(Integer id);
    List<Report> findAllValid();
}

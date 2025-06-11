package com.barreirasapp.repositories;


import com.barreirasapp.entities.Moderator;
import com.barreirasapp.entities.Report;

import java.util.List;

public interface ReportRepository extends GenericRepository<Report, Integer> {
    void insertReview(Report report);
    List<Report> findAllByReporterId(Integer id);
    List<Report> findAllByEntity(String cnpj);
    List<Report> findAllByBarrierScenario(Integer barrierScenario);

    List<Report> findAllValid();

    List<Report> findFromParams(String[] barrierTypes, String word);
}

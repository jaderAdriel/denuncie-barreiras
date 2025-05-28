package com.barreirasapp.service;

import com.barreirasapp.dto.report.RegisterReportDTO;
import com.barreirasapp.dto.report.UpdateReportDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.model.dao.BarrierScenarioDao;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.ReportDao;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Report;
import com.barreirasapp.model.enums.EnvironmentType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReportService {
    private final ReportDao reportRepository;
    private final BarrierScenarioDao barrierScenarioRepository;

    public ReportService() {
        this.reportRepository= DaoFactory.createReportDao();
        this.barrierScenarioRepository = DaoFactory.createBarrierScenario();
    }

    public void insert(RegisterReportDTO reportDTO) throws ValidationError {

        BarrierScenario barrierScenario = null;
        System.out.println("entrou no controller");
        if(reportDTO.getRelatedScenarioId() != null){
            barrierScenario = barrierScenarioRepository.findById(reportDTO.getRelatedScenarioId());
        }

        Report report = new Report(
                reportDTO.getEnvironment(),
                reportDTO.getIncidentDetails(),
                reportDTO.getAnonymous(),
                barrierScenario
        );

        report.setReporter(reportDTO.getReporter());

        report.setType(reportDTO.getType());

        reportRepository.insert(report);
    }

    public void update(UpdateReportDTO updateReportDTO) throws ValidationError {

        Report reportToUpdate = reportRepository.findById(updateReportDTO.getId());

        if (reportToUpdate == null) {
            throw new ValidationError("Erro de integridade", Map.of("error", "Denúncia com este Id não existe"));
        }

        EnvironmentType environment = updateReportDTO.getEnvironment();
        String incidentDetails = updateReportDTO.getIncidentDetails();

        if (environment != null)
            reportToUpdate.setAmbient(environment);

        if (incidentDetails != null)
            reportToUpdate.setEventDetailing(incidentDetails);

        this.reportRepository.update(reportToUpdate);
    }

    public void deleteById(Integer updateId) throws ValidationError {

        if (reportRepository.findById(updateId) == null) {
            throw new ValidationError("Erro de integridade", Map.of("error", "Denúncia com este Id não existe"));
        }

        reportRepository.deleteById(updateId);
    }

    public List<Report> listAll() {
        return reportRepository.findAll();
    }

    public Report findById(Integer id) {
        return reportRepository.findById(id);
    }
}

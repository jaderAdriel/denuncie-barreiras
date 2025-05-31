package com.barreirasapp.services;

import com.barreirasapp.dto.report.RegisterReportDTO;
import com.barreirasapp.dto.report.UpdateReportDTO;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.repositories.BarrierScenarioRepository;
import com.barreirasapp.repositories.ReportRepository;
import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.Report;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReportService {
    private final ReportRepository reportRepository;
    private final BarrierScenarioService barrierScenarioService;

    public ReportService(ReportRepository reportRepository, BarrierScenarioService barrierScenarioService) {
        this.reportRepository = reportRepository;
        this.barrierScenarioService = barrierScenarioService;
    }

    public void insert(RegisterReportDTO reportDTO) throws ValidationError {
        BarrierScenario barrierScenario = null;

        if(reportDTO.getRelatedScenarioId() != null){
            barrierScenario = barrierScenarioService.findById(reportDTO.getRelatedScenarioId()).orElseThrow();
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

        Report reportToUpdate = reportRepository.findById(updateReportDTO.getId())
                .orElseThrow(() -> new ValidationError("Erro de integridade", Map.of("error", "Denúncia com este Id não existe")));

        reportToUpdate.setReportReview(
                updateReportDTO.getReviewAuthor(),
                LocalDateTime.now(),
                updateReportDTO.getReviewIsValid(),
                updateReportDTO.getReviewComment()
        );

        this.reportRepository.update(reportToUpdate);
    }

    public void deleteById(Integer id) throws ValidationError {
        reportRepository.deleteById(id);
    }

    public List<Report> listAll() {
        return reportRepository.findAll();
    }

    public List<Report> listAllValid() {
        return reportRepository.findAllValid();
    }

    public Optional<Report> findById(Integer id) throws ValidationError {
        return reportRepository.findById(id);
    }
}

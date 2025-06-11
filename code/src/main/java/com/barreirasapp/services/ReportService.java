package com.barreirasapp.services;

import com.barreirasapp.dto.RegisterReportReviewDTO;
import com.barreirasapp.dto.report.RegisterReportDTO;
import com.barreirasapp.entities.*;
import com.barreirasapp.exceptions.ValidationError;
import com.barreirasapp.repositories.ReportRepository;
import com.barreirasapp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReportService {
    private static final Logger log = LoggerFactory.getLogger(ReportService.class);
    private final ReportRepository reportRepository;
    private final BarrierScenarioService barrierScenarioService;
    private final UserRepository userRepository;
    private final EntityService entityService;

    public ReportService(ReportRepository reportRepository, BarrierScenarioService barrierScenarioService, UserRepository userRepository, EntityService entityService) {
        this.reportRepository = reportRepository;
        this.barrierScenarioService = barrierScenarioService;
        this.userRepository = userRepository;
        this.entityService = entityService;
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

        String cnpj = reportDTO.getEntityCnpj();
        if (!cnpj.isEmpty()) {
            Optional<Entity> entity = entityService.findByCnpj(cnpj);
            entity.ifPresent(report::setEntity);
        }

        report.setReporter(reportDTO.getReporter());

        report.setType(reportDTO.getType());

        reportRepository.insert(report);
    }

    public void insertReview(RegisterReportReviewDTO registerReportReviewDTO) throws ValidationError {

        Report reportToUpdate = reportRepository.findById(registerReportReviewDTO.getReportId())
                .orElseThrow(() -> new ValidationError("Erro de integridade", Map.of("error", "Denúncia com este Id não existe")));

        User user = registerReportReviewDTO.getReviewAuthor();

        Moderator moderator = userRepository.findModeratorById(user.getId()).orElseThrow();
        System.out.println(moderator.toString() + moderator.getId());
        reportToUpdate.setReportReview(
                moderator,
                LocalDateTime.now(),
                registerReportReviewDTO.getReportIsValid(),
                registerReportReviewDTO.getComment()
        );

        this.reportRepository.insertReview(reportToUpdate);
    }

    public void deleteById(Integer id) {
        reportRepository.deleteById(id);
    }

    public List<Report> listAll() {
        return reportRepository.findAll();
    }

    public List<Report> listAllFromUser(User user) {
        return reportRepository.findAllByReporterId(user.getId());
    }

    public List<Report> listAllRelatedToBarrierScenario(BarrierScenario barrierScenario) {
        return reportRepository.findAllByBarrierScenario(barrierScenario.getId());
    }

    public List<Report> listAllValid() {
        return reportRepository.findAllValid();
    }

    public Optional<Report> findById(Integer id) throws ValidationError {
        return reportRepository.findById(id);
    }

    public List<Report> findAllByEntity(String cnpj) {
        return reportRepository.findAllByEntity(cnpj);
    }

    public List<Report> filterAndSearch(String[] barrierTypes, String word) {
        return reportRepository.findFromParams(barrierTypes, word);
    }
}

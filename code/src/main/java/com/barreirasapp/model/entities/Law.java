package com.barreirasapp.model.entities;

import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.dao.LawDao;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Law {
    private String code;
    private LocalDate date;
    private String officialLink;
    private String title;
    private String description;

    public Law(String code, LocalDate date, String officialLink, String title, String description) {
        this.code = code;
        this.date = date;
        this.officialLink = officialLink;
        this.title = title;
        this.description = description;
    }

    public void setOfficialLink(String officialLink) {
        this.officialLink = officialLink;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getOfficialLink() {
        return officialLink;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void save() {
        LawDao dao = DaoFactory.createLawDao();
        dao.insert(this);
    }

    public void update() {
        LawDao dao = DaoFactory.createLawDao();
        dao.update(this);
    }

    public void delete() {
        LawDao dao = DaoFactory.createLawDao();
        dao.deleteById(this.getCode());
    }

    public static Optional<Law> find(String lawCode) {
        LawDao dao = DaoFactory.createLawDao();
        return Optional.ofNullable(dao.findById(lawCode));
    }

    public static List<Law> findAll() {
        LawDao dao = DaoFactory.createLawDao();
        return dao.findAll();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Law law = (Law) o;
        return Objects.equals(code, law.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}

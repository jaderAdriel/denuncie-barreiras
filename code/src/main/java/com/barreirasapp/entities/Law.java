package com.barreirasapp.entities;

import java.time.LocalDate;
import java.util.Objects;

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

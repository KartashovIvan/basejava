package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Company> companies;

    public CompanySection() {
    }

    public CompanySection(Company... companies) {
        this(Arrays.asList(companies));
    }

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "organizations must not be null");
        this.companies = companies;
    }
//
//    public void addCompany(String name, String website, String title, String description, LocalDate startDate, LocalDate endDate) {
//        companies.add(new Company(name, website, title, description, startDate, endDate));
//    }
//
//    public void addEducation(String name, String website, String title, LocalDate startDate, LocalDate endDate) {
//        companies.add(new Company(name, website, title, null, startDate, endDate));
//    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }

    @Override
    public String toString() {
        return companies + "";
    }
}

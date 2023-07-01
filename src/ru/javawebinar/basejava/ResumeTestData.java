package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;

import static ru.javawebinar.basejava.model.SectionType.EDUCATION;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Ivan");
        String [] contactsArray = {"89998887766","skype","mail@mail.ru","Профиль LinkedIn","Профиль github",null,"Домашняя страница"};
        String personalityTraits = "Пунктуальность, организованность, коммуникабельность, дружелюбность, умение работать в команде, быстро обучаемый, трудолюбивый";
        String position = "Стать разработчиком Java";


        TextSection personal = new TextSection(personalityTraits);
        TextSection objective = new TextSection(position);

        ListSection achievement = new ListSection();
        achievement.addContent("Ведение проектов на работе");
        achievement.addContent("Настройка мониторинга");
        achievement.addContent("Ежегодное повышение квалификации");
        ListSection qualification = new ListSection();
        qualification.addContent("Java 8+");
        qualification.addContent("Git");
        qualification.addContent("SQL");

        CompanySection experience = new CompanySection();
        experience.addCompany("СО ЕЭС", "url","Специалис", "Мониторинг и подержка работы IT инфраструктуры", LocalDate.of(2019,1,19),LocalDate.of(2020,6,30));
        experience.addCompany("ЗАО Раском", "url","Инженер участка", "Сопровождение и подержка работы сети", LocalDate.of(2020,7,1),LocalDate.of(2021,12,23));
        experience.addCompany("НРД", "url","Главный специалист сопровождения", "2-я линия подержки", LocalDate.of(2021,12,24),LocalDate.now());
        CompanySection education = new CompanySection();
        education.addEducation("Stepic","stepic.org","Интерактивный тренажер по SQL",LocalDate.of(2023,1,1),LocalDate.of(2023,3,1));
        Period period = new Period("Java: основы многопоточности",LocalDate.of(2023,4,1),LocalDate.now());

        AbstractSection [] sections =  {personal, objective, achievement, qualification, experience, education};

        for(int i =0;i < ContactType.values().length;i++){
            resume.contacts.put(ContactType.values()[i],contactsArray[i]);
        }

        for(int i =0;i < SectionType.values().length;i++){
            resume.sections.put(SectionType.values()[i],sections[i]);
        }

        ((CompanySection)resume.sections.get(EDUCATION)).getCompanies().get(0).addPeriod(period);

        System.out.println(resume);
    }
}

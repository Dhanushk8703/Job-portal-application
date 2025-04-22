package com.jobportalrestapi.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobportalrestapi.app.model.Education;
import com.jobportalrestapi.app.model.EducationDTO;
import com.jobportalrestapi.app.model.JobSeeker;
import com.jobportalrestapi.app.model.JobSeekerDTO;
import com.jobportalrestapi.app.model.Skill;
import com.jobportalrestapi.app.model.SkillDTO;
import com.jobportalrestapi.app.model.WorkExperience;
import com.jobportalrestapi.app.model.WorkExperienceDTO;
import com.jobportalrestapi.app.repository.JobSeekerRepository;

@Service
public class JobSeekerService {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    public void registerJobSeeker(JobSeekerDTO dto) {
        JobSeeker jobSeeker = new JobSeeker();
        jobSeeker.setFirstName(dto.getFirstName());
        jobSeeker.setLastName(dto.getLastName());
        jobSeeker.setPhoneNumber(dto.getPhoneNumber());
        jobSeeker.setDob(dto.getDob());

        List<Education> educationList = new ArrayList<>();
        for (EducationDTO eduDto : dto.getEducationList()) {
            Education education = new Education();
            education.setDegree(eduDto.getDegree());
            education.setSpecialization(eduDto.getSpecialization());
            education.setFromDate(eduDto.getFromDate());
            education.setToDate(eduDto.getToDate());
            education.setUniversity(eduDto.getUniversity());
            education.setEducationType(eduDto.getEducationType());
            education.setInstitution(eduDto.getInstitution());
            education.setRollNumber(eduDto.getRollNumber());
            education.setPercentage(eduDto.getPercentage());
            education.setMarksheet(eduDto.getMarksheet());
            education.setJobSeeker(jobSeeker);
            educationList.add(education);
        }

        // Process Work Experience List
        List<WorkExperience> workExperienceList = new ArrayList<>();
        for (WorkExperienceDTO expDto : dto.getWorkExperienceList()) {
            WorkExperience exp = new WorkExperience();
            exp.setCompanyName(expDto.getCompanyName());
            exp.setCompanySector(expDto.getCompanySector());
            exp.setJobTitle(expDto.getJobTitle());
            exp.setJobLocation(expDto.getJobLocation());
            exp.setPositionType(expDto.getPositionType());
            exp.setStartDate(expDto.getStartDate());
            exp.setEndDate(expDto.getEndDate());
            exp.setSalary(expDto.getSalary());
            exp.setJobDescription(expDto.getJobDescription());
            exp.setJobSeeker(jobSeeker);
            workExperienceList.add(exp);
        }

        // Process Skill List
        List<Skill> skillList = new ArrayList<>();
        for (SkillDTO skillDto : dto.getSkillList()) {
            Skill skill = new Skill();
            skill.setSkillSet(skillDto.getSkillSet());
            skill.setProficiency(skillDto.getProficiency());
            skill.setCertificateName(skillDto.getCertificateName());
            skill.setCertificateFile(skillDto.getCertificateFile());
            skill.setJobSeeker(jobSeeker);
            skillList.add(skill);
        }

        jobSeeker.setEducationList(educationList);
        jobSeeker.setWorkExperiences(workExperienceList);
        jobSeeker.setSkills(skillList);

        jobSeekerRepository.save(jobSeeker);
    }
}
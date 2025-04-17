package com.jobportalrestapi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportalrestapi.app.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {}
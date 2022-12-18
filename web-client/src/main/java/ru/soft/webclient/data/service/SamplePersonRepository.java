package ru.soft.webclient.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.soft.webclient.data.entity.SamplePerson;

import java.util.UUID;

public interface SamplePersonRepository extends JpaRepository<SamplePerson, UUID> {

}
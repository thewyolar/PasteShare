package ru.pasteshare.serviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pasteshare.serviceapi.model.Paste;

import java.util.UUID;

@Repository
public interface PasteRepository extends JpaRepository<Paste, UUID> {
}

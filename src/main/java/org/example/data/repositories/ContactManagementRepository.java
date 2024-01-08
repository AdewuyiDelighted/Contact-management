package org.example.data.repositories;

import org.example.data.model.ContactManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ContactManagementRepository extends JpaRepository<ContactManagement,Long> {
    Optional<ContactManagement> findByEmail(String email);


}

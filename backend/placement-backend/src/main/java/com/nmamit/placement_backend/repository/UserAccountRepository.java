package com.nmamit.placement_backend.repository;

import com.nmamit.placement_backend.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    // JPA query method to find a user account by college email

    Optional<UserAccount> findByCollegeEmail(String collegeEmail);
    
    boolean existsByCollegeEmail(String collegeEmail);

}

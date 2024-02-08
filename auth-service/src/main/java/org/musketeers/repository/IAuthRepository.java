package org.musketeers.repository;



import org.musketeers.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAuthRepository extends JpaRepository<Auth, UUID> {
}
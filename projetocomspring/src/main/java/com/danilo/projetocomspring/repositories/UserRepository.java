package com.danilo.projetocomspring.repositories;

import com.danilo.projetocomspring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

// User será a key de acesso, long será o retorno, provavelmente o Id.
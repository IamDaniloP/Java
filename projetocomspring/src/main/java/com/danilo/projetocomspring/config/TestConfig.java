package com.danilo.projetocomspring.config;

import com.danilo.projetocomspring.entities.User;
import com.danilo.projetocomspring.repositories.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
  @Autowired
  private UserRepository userRepository;

  @Override
  public void run(String... args) throws Exception {
    User u1 = new User(null, "MB", "mb@gmail.com", "111111111", "12345");
    User u2 = new User(null, "AC", "ac@gmail.com", "222222222", "54321");

    userRepository.saveAll(Arrays.asList(u1, u2));
  }
}

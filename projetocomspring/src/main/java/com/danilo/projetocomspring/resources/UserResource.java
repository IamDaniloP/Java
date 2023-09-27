package com.danilo.projetocomspring.resources;

import com.danilo.projetocomspring.entities.User;

import com.danilo.projetocomspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

  @Autowired
  private UserService service;

  @GetMapping
  public ResponseEntity<List<User>> findAll() {
    List<User> list = service.findAll();
    return ResponseEntity.ok().body(list);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    User obj = service.findById(id);
    return ResponseEntity.ok().body(obj);
  }
}

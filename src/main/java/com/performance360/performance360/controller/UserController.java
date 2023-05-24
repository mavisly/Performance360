package com.performance360.performance360.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.performance360.performance360.model.User;
import com.performance360.performance360.model.UserLogin;
import com.performance360.performance360.repository.UserRepository;
import com.performance360.performance360.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/all")
    public ResponseEntity <List<User>> getAll(){     
        return ResponseEntity.ok(userRepository.findAll());  
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(resposta -> ResponseEntity.ok(resposta))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/logar")
    public ResponseEntity<UserLogin> login(@RequestBody Optional<UserLogin> userLogin) {
        return userService.autenticarUser(userLogin)
            .map(resposta -> ResponseEntity.ok(resposta))
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {

        return userService.cadastrarUser(user)
            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
            .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    @PutMapping("/atualizar")
    public ResponseEntity<User> putUser(@Valid @RequestBody User user) {
        return userService.atualizarUser(user)
            .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}

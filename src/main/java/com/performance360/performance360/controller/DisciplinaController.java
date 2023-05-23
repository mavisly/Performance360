package com.performance360.performance360.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.performance360.performance360.model.Disciplina;
import com.performance360.performance360.repository.DisciplinaRepository;

import jakarta.validation.Valid;

@RestController //informa que a classe recebera requisições compostas por url, verbo e request body
@RequestMapping("/disciplinas") //define a url
@CrossOrigin(origins = "*", allowedHeaders = "*")//permite o recebimento de requisições de fora do domínio
public class DisciplinaController {
	
@Autowired
private DisciplinaRepository disciplinaRepository;
	
@GetMapping //mapeamento das requisições
public ResponseEntity<List<Disciplina>> getAll(){
	return ResponseEntity.ok(disciplinaRepository.findAll());
}

@GetMapping("/{id}")//url
public ResponseEntity<Disciplina> getById(@PathVariable Long id){ //a anotação insere o valor enviado no endpoint
	return disciplinaRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta)) //evita o NullPointerException, se encontra o objeto, retorna ok (code 200)
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); //se não, retorna not_found
}

@GetMapping("/titulo/{titulo}")
public ResponseEntity<List<Disciplina>> getByTitulo(@PathVariable String titulo){
	return ResponseEntity.ok(disciplinaRepository.findAllByTituloContainingIgnoreCase(titulo));
}

@PostMapping
public ResponseEntity<Disciplina> post(@Valid @RequestBody Disciplina disciplina){
	return ResponseEntity.status(HttpStatus.CREATED)
			.body(disciplinaRepository.save(disciplina));
}

@PutMapping
public ResponseEntity<Disciplina> put(@Valid @RequestBody Disciplina disciplina){
	return disciplinaRepository.findById(disciplina.getId())
			.map(resposta -> ResponseEntity.status(HttpStatus.OK)
					.body(disciplinaRepository.save(disciplina)))
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}

@ResponseStatus(HttpStatus.NO_CONTENT)
@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
	Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
	
	if (disciplina.isEmpty())
		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	
	disciplinaRepository.deleteById(id);
}
}

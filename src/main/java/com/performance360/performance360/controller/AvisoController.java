package com.performance360.performance360.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.performance360.performance360.model.Aviso;
import com.performance360.performance360.repository.AvisoRepository;



@RestController
@RequestMapping("/mural")
@CrossOrigin(origins="*", allowedHeaders="*")
public class AvisoController {

	@Autowired
	private AvisoRepository avisoRepository;
	
	@GetMapping
	public ResponseEntity<List<Aviso>> getAll(){
		return ResponseEntity.ok(avisoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Aviso> getById(@PathVariable Long id){
		return avisoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("titulo/{titulo}")
	public ResponseEntity<List<Aviso>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(avisoRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Aviso> post(@Valid @RequestBody Aviso aviso){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(avisoRepository.save(aviso));
	}
	
	@PutMapping
	public ResponseEntity<Aviso> put(@Valid @RequestBody Aviso aviso){
		return avisoRepository.findById(aviso.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(avisoRepository.save(aviso)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Aviso> aviso = avisoRepository.findById(id);
		
		if (aviso.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		avisoRepository.deleteById(id);
	}
}

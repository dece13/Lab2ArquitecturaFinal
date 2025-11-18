package co.edu.javeriana.as.personapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.as.personapp.adapter.PersonaInputAdapterRest;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.model.request.PersonaRequest;
import co.edu.javeriana.as.personapp.model.response.PersonaResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/persona")
public class PersonaControllerV1 {
	
	@Autowired
	private PersonaInputAdapterRest personaInputAdapterRest;
	
	@ResponseBody
	@GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PersonaResponse> personas(@PathVariable String database) {
		log.info("Into personas REST API");
		return personaInputAdapterRest.historial(database.toUpperCase());
	}
	
	@ResponseBody
	@GetMapping(path = "/{database}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonaResponse persona(@PathVariable String database, @PathVariable Integer id) {
		log.info("Into persona by id REST API");
		return personaInputAdapterRest.buscarPorId(database.toUpperCase(), id);
	}
	
	@ResponseBody
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonaResponse crearPersona(@RequestBody PersonaRequest request) {
		log.info("Into crearPersona REST API");
		return personaInputAdapterRest.crearPersona(request);
	}
	
	@ResponseBody
	@PutMapping(path = "/{database}/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonaResponse actualizarPersona(@PathVariable String database, @PathVariable Integer id, @RequestBody PersonaRequest request) {
		log.info("Into actualizarPersona REST API");
		return personaInputAdapterRest.actualizarPersona(database.toUpperCase(), id, request);
	}
	
	@ResponseBody
	@DeleteMapping(path = "/{database}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean eliminarPersona(@PathVariable String database, @PathVariable Integer id) {
		log.info("Into eliminarPersona REST API");
		return personaInputAdapterRest.eliminarPersona(database.toUpperCase(), id);
	}
}

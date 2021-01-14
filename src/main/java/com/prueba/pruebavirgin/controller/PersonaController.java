package com.prueba.pruebavirgin.controller;

import com.prueba.pruebavirgin.dto.PersonaDto;
import com.prueba.pruebavirgin.entity.Persona;
import com.prueba.pruebavirgin.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping(value = "/personas", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonaDto> listarPersonas(){
        return personaService.listarPersonas();
    }

    @GetMapping(value = "/personas/{cedula}")
    public ResponseEntity<?> listarPersonaPorCedula(@PathVariable Integer cedula){
        return personaService.listarPersonaPorCedula(cedula);
    }

    @PostMapping(value = "/personas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> guardarPersona(@Valid @RequestBody Persona persona, BindingResult result){
        return personaService.guardarPersona(persona, result);
    }

    @PutMapping(value = "/personas/{cedula}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarPersona(@Valid @RequestBody Persona persona, BindingResult result, @PathVariable Integer cedula){
        return personaService.actualizarPersona(persona, result, cedula);
    }

    @DeleteMapping(value = "/personas/{cedula}")
    public ResponseEntity<?> eliminarPersona(@PathVariable Integer cedula){
        return personaService.eliminarPersona(cedula);
    }
}

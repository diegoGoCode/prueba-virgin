package com.prueba.pruebavirgin.service;

import com.prueba.pruebavirgin.dao.PersonaDao;
import com.prueba.pruebavirgin.dto.PersonaDto;
import com.prueba.pruebavirgin.entity.Persona;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonaService {

    @Autowired
    private PersonaDao personaDao;

    public List<PersonaDto> listarPersonas(){
        List<Persona> personas = personaDao.findAll();
        List<PersonaDto> personasDto = personas
                .stream()
                .map(persona -> new ModelMapper().map(persona, PersonaDto.class))
                .collect(Collectors.toList());
        return personasDto;
    }

    public ResponseEntity<?> listarPersonaPorCedula(Integer cedula){
        Map<String, Object> respuesta = new HashMap<>();
        PersonaDto personaDto = null;
        Boolean existePersona = true;
        try {
            Persona persona = personaDao.findById(cedula).orElse(null);
            if(persona != null){
                personaDto = new ModelMapper().map(persona, PersonaDto.class);
            }else{
                existePersona = false;
            }
        }catch (DataAccessException e){
            respuesta.put("mensaje", "error al consultar la persona con la cedula: ".concat(cedula.toString()));
            respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(existePersona == false){
            respuesta.put("mensaje", "la persona con la cedula: ".concat(cedula.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PersonaDto>(personaDto, HttpStatus.OK);
    }

    public ResponseEntity<?> guardarPersona(Persona persona, BindingResult result){
        Map<String, Object> respuesta = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: "+err.getField()+" "+err.getDefaultMessage())
                    .collect(Collectors.toList());

            respuesta.put("errores", errors);
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        try{
            persona = personaDao.save(persona);
        }catch (DataAccessException e){
            respuesta.put("mensaje", "error al guardar la persona");
            respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        respuesta.put("mensaje", "persona guardada con exito");
        respuesta.put("persona", persona);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    public ResponseEntity<?> actualizarPersona(Persona persona, BindingResult result, Integer cedula){
        Map<String, Object> respuesta = new HashMap<>();
        Persona personaActual = personaDao.findById(cedula).orElse(null);

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(err -> "El campo: "+err.getField()+" "+err.getDefaultMessage()).collect(Collectors.toList());
            respuesta.put("errores", errors);
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }

        if(personaActual == null){
            respuesta.put("mensaje", "la persona con la cedula: ".concat(cedula.toString()).concat(" no existe"));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
        }

        try{
            personaActual.setTipoDocumento(persona.getTipoDocumento());
            personaActual.setNombres(persona.getNombres());
            personaActual.setApellidos(persona.getApellidos());
            personaActual.setFechaNacimiento(persona.getFechaNacimiento());
            personaActual.setDireccion(persona.getDireccion());
            personaActual.setCedula(persona.getCedula());
            personaActual = personaDao.save(personaActual);
        }catch (DataAccessException e){
            respuesta.put("mensaje", "error al actualizar la persona");
            respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        respuesta.put("mensaje", "persona actulizada con exito");
        respuesta.put("personaActualizada", personaActual);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    public ResponseEntity<?> eliminarPersona(Integer cedula){
        Map<String, Object> respuesta = new HashMap<>();
        try {
            personaDao.deleteById(cedula);
        }catch (DataAccessException e){
            respuesta.put("mensaje", "error al eliminar la persona");
            respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "persona eliminada con exito");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    }
}

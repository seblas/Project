package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.project.domain.Field;
import pl.coderslab.project.repository.FieldRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public Field saveField(Field field) {
        return fieldRepository.save(field);
    }

    public Optional<Field> findById(Long id) {
        return fieldRepository.findById(id);
    }

    public List<Field> findAll() {
        return fieldRepository.findAll();
    }

    public List<Field> findAllSortedByStreet() {
        return fieldRepository.findAllSortedByStreet();
    }

    public void deleteFieldById(Long id) {
        if (!fieldRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Field not found with id " + id);
        }
        fieldRepository.deleteById(id);
    }

    public void deleteField(Field field) {
        if(!fieldRepository.existsById(field.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Field " + field + "not found");
        }
        fieldRepository.delete(field);
    }

    public Field updateField(Long id, Field field) {
        Optional<Field> optionalField = fieldRepository.findById(id);
        if(optionalField.isPresent()) {
            field.setId(id);
            return fieldRepository.save(field);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Field not found with id " + id);
        }
    }
}

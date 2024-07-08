package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.project.domain.Field;
import pl.coderslab.project.domain.User;
import pl.coderslab.project.repository.FieldRepository;

public class FieldService {

    @Autowired
    private FieldRepository fieldRepository;

    public Field saveField(Field field) {
        return fieldRepository.save(field);
    }
}

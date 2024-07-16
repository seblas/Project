package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.project.domain.Role;
import pl.coderslab.project.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }


    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void deleteRoleById(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id " + id);
        }
        roleRepository.deleteById(id);
    }

    public void deleteRole(Role role) {
        if (!roleRepository.existsById(role.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role " + role + "not found");
        }
        roleRepository.delete(role);
    }

    public Role updateRole(Long id, Role role) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            role.setId(id);
            return roleRepository.save(role);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with id " + id);
        }
    }
}

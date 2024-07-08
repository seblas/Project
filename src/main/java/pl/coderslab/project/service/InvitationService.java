package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.project.domain.Invitation;
import pl.coderslab.project.repository.InvitationRepository;
import pl.coderslab.project.repository.InvitationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InvitationService {

    private final InvitationRepository invitationRepository;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    public Invitation saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    public Optional<Invitation> findById(Long id) {
        return invitationRepository.findById(id);
    }

    public List<Invitation> findAll() {
        return invitationRepository.findAll();
    }

    public void deleteInvitationById(Long id) {
        if (!invitationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invitation not found with id " + id);
        }
        invitationRepository.deleteById(id);
    }

    public void deleteInvitation(Invitation invitation) {
        if(!invitationRepository.existsById(invitation.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invitation " + invitation + "not found");
        }
        invitationRepository.delete(invitation);
    }

    public Invitation updateInvitation(Long id, Invitation invitation) {
        Optional<Invitation> optionalInvitation = invitationRepository.findById(id);
        if(optionalInvitation.isPresent()) {
            invitation.setId(id);
            return invitationRepository.save(invitation);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invitation not found with id " + id);
        }
    }
}

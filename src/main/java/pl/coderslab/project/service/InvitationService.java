package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.project.domain.*;
import pl.coderslab.project.repository.InvitationRepository;
import pl.coderslab.project.repository.InvitationRepository;

import java.util.Base64;
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

    public Optional<Invitation> findByInvitationHash(String hash) {
        return invitationRepository.findByInvitationHash(hash);
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

    public Invitation setInvitation(Game game, User user) {
        Field field = game.getField();
        Address address = field.getAddress();
        System.out.println("User id: " + user.getId());
        String hash = Base64.getEncoder().encodeToString((game.getId() + ":" + user.getId()).getBytes());
        System.out.println("Hash: " + hash);
        hash = Base64.getEncoder().encodeToString((game.getId() + ":" + user.getId() + ":" + System.currentTimeMillis()).getBytes());
        String message = "Zapraszam Cię do gry\n" +
                "Boisko: " + field.getName() + "\n" +
                "Adres: " + address.getCode() + " " + address.getCityStreetAndNumber() + "\n" +
                "Czas gry: od " + game.getStartTime().toLocalDate() + " godz. " + game.getStartTime().toLocalTime() +
                " do " + game.getEndTime().toLocalDate() + " godz. " + game.getEndTime().toLocalTime() + "\n" +
                "Koszt: " + (int) game.getCost() + " zł\n\n";

                if(!game.getDescription().isEmpty()) {
                    message += "Dodatkowe informacje: \n" + game.getDescription() + "\n\n";
                }
                message += "Jeżeli chcesz zagrać to do " + game.getRecruitmentEndTime().toLocalDate() +
                " godz. " + game.getRecruitmentEndTime().toLocalTime() + " kliknij link: \n" +
                "http://localhost:8080/game/" + hash;
        Invitation invitation = new Invitation();
        invitation.setGame(game);
        invitation.setMessage(message);
        invitation.setUser(user);
        invitation.setInvitationHash(hash);
        return invitation;
    }
}

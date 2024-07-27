package pl.coderslab.project.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.project.domain.Game;
import pl.coderslab.project.domain.Invitation;
import pl.coderslab.project.domain.User;
import pl.coderslab.project.service.EmailService;
import pl.coderslab.project.service.GameService;
import pl.coderslab.project.service.InvitationService;
import pl.coderslab.project.service.UserService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/game")
@Controller
public class GameController {

    private static final Map<Integer, String> LEVELS = new LinkedHashMap<>() {{ // LinkedHashMap aby była zachowana kolejność
        put(1, "słaby");
        put(2, "średni");
        put(3, "dobry");
        put(4, "bardzo dobry");
        put(5, "zawodowiec");
    }};

    private final GameService gameService;
    private final InvitationService invitationService;
    private final UserService userService;
    private final EmailService emailService;

    public GameController(GameService gameService, InvitationService invitationService, UserService userService, EmailService emailService) {
        this.gameService = gameService;
        this.invitationService = invitationService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @RequestMapping("/{hash}")
    @ResponseBody
    public String confirmGame(@PathVariable("hash") String hash) {
        System.out.println("Hash: " + hash);
        Optional<Invitation> optionalInvitation = invitationService.findByInvitationHash(hash);
        System.out.println("Optional<Invitation>: " + optionalInvitation.isPresent());
        if(optionalInvitation.isPresent()) {
            Optional<Game> optionalGame = gameService.findById(optionalInvitation.get().getGame().getId());
            if(optionalGame.isPresent()) {
                Game game = optionalGame.get();
                System.out.println("Znaleziona gra: " + game);
                User player = optionalInvitation.get().getUser();
                System.out.println("Znaleziony user o id: " + player.getId());
                Set<User> acceptedPlayers = game.getAcceptedPlayers();

                if(game.isActive()) System.out.println("Gra jest aktywna");
                if(game.getRecruitmentEndTime().isAfter(LocalDateTime.now().plusMinutes(1))) System.out.println("Czas jest ok");
                if(acceptedPlayers.size()<game.getPlayersToFind()) System.out.println("Ilość graczy ok");


                if(game.isActive() && game.getRecruitmentEndTime().isAfter(LocalDateTime.now())
                        && acceptedPlayers.size()<game.getPlayersToFind()) {
                    acceptedPlayers.add(userService.findById(player.getId()).get());
                    game.setAcceptedPlayers(acceptedPlayers);
                    if(acceptedPlayers.size()==game.getPlayersToFind()) {
                        game.setActive(false);
                    }
                    gameService.saveGame(game);
                    String to = game.getCreator().getEmail();
                    String message = "Gracz " + player.getName() + " dołączył do gry";
                    if(!game.isActive()) {
                        message += "\nJest już pełen skład, lista dołączonych graczy:\n";
                        for(User user : game.getAcceptedPlayers()) {
                            int playerAge = (int) Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
                            message += user.getName() + ", " + playerAge + " lat, poziom " + LEVELS.get(user.getLevel()) + "\n";
                        }
                    }
                    String subject = "Gra nr " + game.getId() + ": gracz dołączył";
                    emailService.sendEmail(to, subject, message);
                    return "Zgłoszenie przyjęte, zapraszamy na boisko";
                }
            }
        }
        return "Gra nie jest już dostępna";
    }
}

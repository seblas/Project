package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.project.domain.Game;
import pl.coderslab.project.repository.GameRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public void deleteGameById(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found with id " + id);
        }
        gameRepository.deleteById(id);
    }

    public void deleteGame(Game game) {
        if(!gameRepository.existsById(game.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game " + game + "not found");
        }
        gameRepository.delete(game);
    }

    public Game updateGame(Long id, Game game) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if(optionalGame.isPresent()) {
            game.setId(id);
            return gameRepository.save(game);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found with id " + id);
        }
    }
}

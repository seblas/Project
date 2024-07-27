package pl.coderslab.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.coderslab.project.domain.Game;
import pl.coderslab.project.domain.Role;
import pl.coderslab.project.domain.User;
import pl.coderslab.project.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DistanceService distanceService;

    @Autowired
    public UserService(UserRepository userRepository, DistanceService distanceService) {
        this.userRepository = userRepository;
        this.distanceService = distanceService;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    public void deleteUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + user + "not found");
        }
        userRepository.delete(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id);
        }
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findByGame(Game game) {
        LocalDate startDate = LocalDate.now().minusYears(game.getMaxAge());
        System.out.println("Pierwsza data: " + startDate);
        LocalDate endDate = LocalDate.now().minusYears(game.getMinAge());
        System.out.println("Druga data: " + endDate);
        List<User> users = userRepository.findUserByLevelAndAge(game.getMinLevel(), game.getMaxLevel(),
                startDate, endDate);
        System.out.println("Pierwsze filtrowanie graczy (poziom i wiek)");
        users.forEach(System.out::println);

        List<User> filteredUsers = Stream.concat(
                // gracze z adresem
                users.stream()
                        .filter(user -> user.getAddress() != null && user.getDistance() > 0)
                        .filter(user -> distanceService.calculateDistance(user.getAddress().toString(),
                                game.getField().getAddress().toString()) <= user.getDistance()),
                // gracze bez adresu
                users.stream()
                        .filter(user -> user.getAddress() == null || user.getDistance() == 0)
        ).collect(Collectors.toList());
        return filteredUsers;
    }
}

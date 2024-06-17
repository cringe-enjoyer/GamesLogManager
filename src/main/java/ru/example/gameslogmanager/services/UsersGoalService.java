package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGoal;
import ru.example.gameslogmanager.repositories.UsersGoalRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersGoalService {

    private final UsersGoalRepository usersGoalRepository;

    @Autowired
    public UsersGoalService(UsersGoalRepository usersGoalRepository) {
        this.usersGoalRepository = usersGoalRepository;
    }

    public List<UsersGoal> getGoalsByUser(User user) {
        return usersGoalRepository.findAllByUser(user);
    }

    @Transactional(readOnly = false)
    public void save(UsersGoal goal) {
        usersGoalRepository.save(goal);
    }

    @Transactional(readOnly = false)
    public void delete(UsersGoal goal) {
        usersGoalRepository.delete(goal);
    }

    public Optional<UsersGoal> getUserGoalById(Integer id) {
        return usersGoalRepository.findById(id);
    }

    public Optional<UsersGoal> getUserGoalByUserAndYear(User user, int year) {
        return usersGoalRepository.findByUserAndYear(user, year);
    }
}

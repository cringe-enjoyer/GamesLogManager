package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGoal;
import ru.example.gameslogmanager.repositories.UsersGoalRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsersGoalService {

    private final UsersGoalRepository usersGoalRepository;

    @Autowired
    public UsersGoalService(UsersGoalRepository usersGoalRepository) {
        this.usersGoalRepository = usersGoalRepository;
    }

    public List<UsersGoal> findGoalsByUser(User user) {
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
}

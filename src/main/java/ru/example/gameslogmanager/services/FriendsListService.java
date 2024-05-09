package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.FriendStatus;
import ru.example.gameslogmanager.models.FriendsList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.models.UsersGame;
import ru.example.gameslogmanager.repositories.FriendsListRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FriendsListService {

    private final FriendsListRepository friendsListRepository;
    private final UsersGameService usersGameService;

    @Autowired
    public FriendsListService(FriendsListRepository friendsListRepository, UsersGameService usersGameService) {
        this.friendsListRepository = friendsListRepository;
        this.usersGameService = usersGameService;
    }

    @Transactional
    public void sendRequest(User user, User friend, FriendStatus status) {
        Optional<FriendsList> friendConnection = getFriendConnectionByUserAndFriend(user, friend);
        if (friendConnection.isPresent() && status != null) {
            FriendsList friendsList = friendConnection.get();
            friendsList.setStatus(status);
            friendsListRepository.save(friendsList);
            return;
        }

        friendsListRepository.save(new FriendsList(user, friend, FriendStatus.SENT));
    }
    
    public Optional<FriendsList> getFriendConnectionByUserAndFriend(User user, User friend) {
        return friendsListRepository.findByUserAndFriend(user, friend);
    }

    @Transactional
    public void deleteFriendConnectionByUserAndFriend(User user, User friend) {
        Optional<FriendsList> friendConnection = getFriendConnectionByUserAndFriend(user, friend);
        if (friendConnection.isPresent()) {
            FriendsList friendsList = friendConnection.get();
            friendsListRepository.delete(friendsList);
        }
    }

    @Transactional
    public void deleteFriendConnectionById(long id) {
        friendsListRepository.deleteById(id);
    }

    public List<FriendsList> getFriendsLists(User user) {
        return friendsListRepository.findAllByUser(user);
    }

    public Optional<FriendsList> getFriendsListsById(long friendsListId) {
        return friendsListRepository.findById(friendsListId);
    }

    public Map<User, UsersGame> getFriendsNews(User user) {
        Map<User, UsersGame> result = new HashMap<>();
        getFriendsLists(user).forEach(friendsList ->
                result.put(friendsList.getFriend(),
                        usersGameService.getLastFinishedGame(friendsList.getFriend()).orElse(null))
        );

        return result;
    }
}

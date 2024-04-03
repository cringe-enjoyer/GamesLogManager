package ru.example.gameslogmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.gameslogmanager.models.FriendStatus;
import ru.example.gameslogmanager.models.FriendsList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.repositories.FriendsListRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FriendsListService {

    private final FriendsListRepository friendsListRepository;

    @Autowired
    public FriendsListService(FriendsListRepository friendsListRepository) {
        this.friendsListRepository = friendsListRepository;
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
}
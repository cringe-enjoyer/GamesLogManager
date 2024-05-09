package ru.example.gameslogmanager.dto;

public class FriendsNewsResponse {
    private FriendDTO friend;
    private UsersGameDTO usersGame;

    public FriendsNewsResponse() {
    }

    public FriendsNewsResponse(FriendDTO friend, UsersGameDTO usersGame) {
        this.friend = friend;
        this.usersGame = usersGame;
    }

    public FriendDTO getFriend() {
        return friend;
    }

    public void setFriend(FriendDTO friend) {
        this.friend = friend;
    }

    public UsersGameDTO getUsersGame() {
        return usersGame;
    }

    public void setUsersGame(UsersGameDTO usersGame) {
        this.usersGame = usersGame;
    }

    @Override
    public String toString() {
        return "FriendsNewsResponse{" +
                "friend=" + friend +
                ", usersGame=" + usersGame +
                '}';
    }
}

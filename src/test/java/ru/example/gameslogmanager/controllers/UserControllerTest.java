package ru.example.gameslogmanager.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.example.gameslogmanager.dto.FriendsListDTO;
import ru.example.gameslogmanager.dto.GamesListDTO;
import ru.example.gameslogmanager.dto.UserDTO;
import ru.example.gameslogmanager.models.FriendStatus;
import ru.example.gameslogmanager.models.FriendsList;
import ru.example.gameslogmanager.models.GamesList;
import ru.example.gameslogmanager.models.User;
import ru.example.gameslogmanager.services.FriendsListService;
import ru.example.gameslogmanager.services.SteamService;
import ru.example.gameslogmanager.services.UserService;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    UserService userService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    SteamService steamService;
    @Mock
    FriendsListService friendsListService;
    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowSettings() {
        when(userService.getUserById(anyInt())).thenReturn(Optional.of(
                new User("login", "password", "email", "steamId", "nickname")));
        when(modelMapper.map(any(User.class), any())).thenReturn(
                new UserDTO("login", "password", "email", "steamId", "nickname"));

        UserDTO result = userController.showSettings(0);
        UserDTO expected = new UserDTO("login", "password", "email", "steamId", "nickname");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testSaveSettings() {
        when(modelMapper.map(any(UserDTO.class), any())).thenReturn(new User("kemia_mulveyio8v@gothic.ksr",
                "0JWS3RpoSlAloqO", "kemia_mulveyio8v@gothic.ksr", "6487592", "dysont"));

        ResponseEntity<HttpStatus> result = userController.saveSettings(new UserDTO("kemia_mulveyio8v@gothic.ksr",
                "0JWS3RpoSlAloqO", "kemia_mulveyio8v@gothic.ksr", "6487592", "dysont"),
                0);
        verify(userService).save(any());
        Assertions.assertEquals(new ResponseEntity<HttpStatus>(HttpStatus.OK), result);
    }

/*    @Test
    void testImportSteamLibrary() {
        when(userService.getUserById(anyInt())).thenReturn(Optional.of(
                new User("login", "password", "email", "steamId", "nickname")));
        when(modelMapper.map(any(GamesList.class), any())).thenReturn(new GamesListDTO("listName",
                new UserDTO("login", "password", "email", "steamId", "nickname")));
        when(steamService.importSteamLibrary(any(), anyString())).thenReturn(new GamesList());

        GamesListDTO result = userController.importSteamLibrary(0, Map.of("list", "listName"));
        Assertions.assertEquals(new GamesListDTO("listName", new UserDTO("login", "password",
                "email", "steamId", "nickname")), result);
    }*/

    /*@Test
    void testShowFriendsList() {
        User user = new User("jacquleen_rickardswbo@union.dm", "KcIDEjV1EFPMTZPgHK",
                "jacquleen_rickardswbo@union.dm", "978585458", "wrist");
        user.setFriendsLists(Set.of(new FriendsList(new User("jacquleen_rickardswbo@union.dm",
                "KcIDEjV1EFPMTZPgHK", "jacquleen_rickardswbo@union.dm", "978585458",
                "wrist"),
                new User("jamael_grubbg@databases.xb", "cHZSU00ElbG3i8",
                        "shunta_wiltseu@savage.aa", "54854678523", "Samirah"), FriendStatus.ADDED)));
        when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));
        when(modelMapper.map(any(FriendsList.class), any())).thenReturn(new FriendsListDTO(
                new UserDTO("jacquleen_rickardswbo@union.dm", "KcIDEjV1EFPMTZPgHK",
                        "jacquleen_rickardswbo@union.dm", "978585458", "wrist"),
                new UserDTO("jamael_grubbg@databases.xb", "cHZSU00ElbG3i8",
                        "shunta_wiltseu@savage.aa", "54854678523", "Samirah"),
                FriendStatus.ADDED));

        Set<FriendsListDTO> result = userController.showFriendsList(0);
        Assertions.assertEquals(Set.of(new FriendsListDTO(
                new UserDTO("jacquleen_rickardswbo@union.dm", "KcIDEjV1EFPMTZPgHK",
                        "jacquleen_rickardswbo@union.dm", "978585458", "wrist"),
                new UserDTO("jamael_grubbg@databases.xb", "cHZSU00ElbG3i8",
                        "shunta_wiltseu@savage.aa", "54854678523", "Samirah"),
                FriendStatus.ADDED)), result);
    }*/

    @Test
    void testSendFriendRequest() {
        when(userService.getUserById(anyInt())).thenReturn(Optional.of(new User("nhung_dempsterjf9@metals.lfl",
                "ZJaCVWB3CXb", "nhung_dempsterjf9@metals.lfl", "312584", "manikain4i")));
        when(userService.getUserByLogin(anyString())).thenReturn(Optional.of(new User(
                "britne_mcsherry6cf9@rooms.tj", "cUip6o48WdHLO8atViIxQs",
                "britne_mcsherry6cf9@rooms.tj", "2548542", "zolaa9")));

        HttpEntity<HttpStatus> result = userController.sendFriendRequest(0, new UserDTO(
                "nhung_dempsterjf9@metals.lfl", "ZJaCVWB3CXb", "nhung_dempsterjf9@metals.lfl",
                "312584", "manikain4i"));
        verify(friendsListService).sendRequest(any(), any(), any());
        Assertions.assertEquals(new HttpEntity<HttpStatus>(HttpStatus.OK), result);
    }

/*    @Test
    void testDecideFriendRequest() {
        when(userService.getUserById(anyInt())).thenReturn(Optional.of(new User("jacquleen_rickardswbo@union.dm",
                "KcIDEjV1EFPMTZPgHK", "jacquleen_rickardswbo@union.dm", "978585458",
                "wrist")));
        when(userService.getUserByLogin(anyString())).thenReturn(Optional.of(new User("jamael_grubbg@databases.xb",
                "cHZSU00ElbG3i8", "shunta_wiltseu@savage.aa", "54854678523", "Samirah")));

        HttpEntity<HttpStatus> result = userController.decideFriendRequest(0, new FriendsListDTO(new UserDTO(
                "jacquleen_rickardswbo@union.dm", "KcIDEjV1EFPMTZPgHK",
                "jacquleen_rickardswbo@union.dm", "978585458", "wrist"), new UserDTO(
                "jamael_grubbg@databases.xb", "cHZSU00ElbG3i8", "shunta_wiltseu@savage.aa",
                "54854678523", "Samirah"), FriendStatus.ADDED));
        verify(friendsListService).sendRequest(any(), any(), any());
        Assertions.assertEquals(new HttpEntity<HttpStatus>(HttpStatus.OK, null), result);
    }*/
}
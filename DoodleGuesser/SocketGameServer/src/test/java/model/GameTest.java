package model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import restauthshared.client.IAuthRestClient;
import socketgameserver.messaging.IServerMessageGenerator;
import socketgameserver.model.Game;
import socketgameshared.messaging.messages.enums.PlayerUpdate;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GameTest {

    @Mock private IServerMessageGenerator mockMsgGen;
    @Mock private IAuthRestClient mockRest;
    private Game game;

    @Before
    public void initGameTest() {
        mockMsgGen = Mockito.mock(IServerMessageGenerator.class);
        mockRest = Mockito.mock(IAuthRestClient.class);

        game = new Game(mockMsgGen, mockRest);
    }

    @Test
    public void loginTest() {
        String token = "1234";
        String sessionId = "123";
        String username = "Test";
        String password = "pass";
        given(mockRest.login(username, password)).willReturn(token);
        game.loginPlayer(username, password, sessionId);
        verify(mockMsgGen, times(1)).notifyLoginResult(sessionId, token, true, null);
        verify(mockMsgGen, times(1)).notifyPlayerListUpdate(sessionId, username, PlayerUpdate.JOINED);
        assert(game.getNumberOfPlayers() == 1);
    }

    @Test
    public void registerTest() {
        //TODO aparte class maken voor het generen van test data
        String username = "testuser";
        String password = "@welkom1";
        String sessionId = "123456";
        given(mockRest.register(username, password)).willReturn(true);
        game.registerNewPlayer(username, password, sessionId);
        verify(mockMsgGen, times(1)).notifyRegisterResult(sessionId, true, null);
    }

    /*@Test
    public void disconnectTest() {
        String sessionId = "123456";
        String username = "testuser";
        String password = "@welkom1";
        String token = "1234";

        IPlayer p = new Player(sessionId, username);
        game.getPlayers().add(p);
        //given(game.pl.get(0)).willReturn(p);

        game.processClientDisconnect(sessionId);
        verify(mockMsgGen, times(1)).notifyPlayerListUpdate(sessionId, username, PlayerUpdate.LEFT);
    }*/
}

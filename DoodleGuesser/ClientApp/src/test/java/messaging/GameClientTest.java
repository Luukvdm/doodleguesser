package messaging;

import clientapp.messaging.GameClient;
import clientapp.messaging.IClientMessageGenerator;
import clientapp.messaging.clientgui.IClientGameGUI;
import clientapp.messaging.clientgui.IClientLoginGUI;
import clientapp.messaging.clientgui.IClientRegisterGui;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import socketgameshared.messaging.messages.enums.GameState;
import socketgameshared.messaging.messages.enums.PlayerRole;
import socketgameshared.messaging.messages.enums.PlayerUpdate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GameClientTest {

    private IClientMessageGenerator mockMsgGen;
    private IClientGameGUI mockGameGUI;
    private IClientLoginGUI mockLoginGUI;
    private IClientRegisterGui mockRegisterGUI;
    private GameClient gameClient;

    @Before
    public void setupClient() {
        mockMsgGen = Mockito.mock(IClientMessageGenerator.class);
        mockGameGUI = Mockito.mock(IClientGameGUI.class);
        mockLoginGUI = Mockito.mock(IClientLoginGUI.class);
        mockRegisterGUI = Mockito.mock(IClientRegisterGui.class);

        gameClient = new GameClient(mockMsgGen);

        gameClient.registerClientGameGUI(mockGameGUI);
        gameClient.registerClientLoginGUI(mockLoginGUI);
        gameClient.registerClientRegisterGUI(mockRegisterGUI);
    }

    @Test
    public void registerPlayerTest() {
        String username = "user";
        String password = "pass";
        gameClient.registerPlayer(username, password);
        verify(mockMsgGen, times(1)).registerPlayerOnServer(username, password);
    }

    @Test
    public void loginPlayerTest() {
        String username = "user";
        String password = "pass";
        gameClient.loginPlayer(username, password);
        verify(mockMsgGen, times(1)).login(username, password);
    }

    @Test
    public void startPathTest() {
        double x = 1;
        double y = 100;
        double colorR = 0, colorG = 128, colorB = 255;
        gameClient.startPath(x, y, colorR, colorG, colorB);
        verify(mockMsgGen, times(1)).startPath(x, y, colorR, colorG, colorB);
    }

    @Test
    public void addPointToPathTest() {
        double x = 1;
        double y = 1;
        gameClient.addPointToPath(x, y);
        verify(mockMsgGen, times(1)).addPointToPath(x, y);
    }

    @Test
    public void clearCanvasTest() {
        gameClient.clearCanvas();
        verify(mockMsgGen, times(1)).clearCanvas();
    }

    @Test
    public void makeGuessTest() {
        String guess = "cat";
        gameClient.makeGuess(guess);
        verify(mockMsgGen, times(1)).makeGuess(guess);
    }

    @Test
    public void handlePlayerRegistrationResponseTest() {
        boolean succes = true;
        String reason = "Succes!!!";
        gameClient.handlePlayerRegistrationResponse(succes, reason);
        verify(mockRegisterGUI, times(1)).processRegistrationResponse(succes, reason);
    }

    @Test
    public void handleLoginResponseTest() {
        String token = "123456";
        boolean succes = false;
        String reason = "Succes!!!";
        gameClient.handleLoginResponse(token, succes, reason);
        verify(mockLoginGUI, times(1)).processLoginResponse(token, succes, reason);
    }

    @Test
    public void handlePlayerListUpdateTest() {
        String playerName = "user";
        PlayerUpdate playerUpdate = PlayerUpdate.JOINED;
        gameClient.handlePlayerListUpdate(playerName, playerUpdate);
        verify(mockGameGUI, times(1)).processPlayerListUpdate(playerName, playerUpdate);
    }

    @Test
    public void handleStartPathTest() {
        double x = 200;
        double y = 15;
        double colorR = 0, colorG = 128, colorB = 255;
        gameClient.handleStartPath(x, y, colorR, colorG, colorB);
        verify(mockGameGUI, times(1)).processStartPath(x, y, colorR, colorG, colorB);
    }

    @Test
    public void handleAddPointToPathTest() {
        double x = 126;
        double y = 601;
        gameClient.handleAddPointToPath(x, y);
        verify(mockGameGUI, times(1)).processAddPointToPath(x, y);
    }

    @Test
    public void handleClearCanvasTest() {
        gameClient.handleClearCanvas();
        verify(mockGameGUI, times(1)).processClearCanvas();
    }

    @Test
    public void handleStartNewRoundTest() {
        PlayerRole playerRole = PlayerRole.GUESSER;
        String word = null;
        gameClient.handleStartNewRound(playerRole, word);
        verify(mockGameGUI, times(1)).processStartNewRound(playerRole, word);
    }

    @Test
    public void handleUpdateGameStateTest() {
        GameState state = GameState.GAME_ENDED;
        gameClient.handleUpdateGameState(state);
        verify(mockGameGUI, times(1)).processUpdateGameState(state);
    }

    @Test
    public void handleGuessResultTest() {
        boolean guessedRight = true;
        String guess = "cat";
        gameClient.handleGuessResult(guessedRight, guess);
        verify(mockGameGUI, times(1)).processGuessResult(guessedRight, guess);
    }

    @Test
    public void handleUpdateChatTest() {
        String playerName = "user12";
        String message = "Hello World!";
        gameClient.handleUpdateChat(playerName, message);
        verify(mockGameGUI, times(1)).processUpdateChat(playerName, message);
    }
}

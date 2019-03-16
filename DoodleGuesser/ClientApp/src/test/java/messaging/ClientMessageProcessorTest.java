package messaging;

import clientapp.messaging.ClientMessageHandlerFactory;
import clientapp.messaging.ClientMessageProcessor;
import clientapp.messaging.IClientMessageProcessor;
import clientapp.messaging.IGameClient;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import socketgameshared.messaging.messages.AddPointToPathMessage;
import socketgameshared.messaging.messages.ClearCanvasMessage;
import socketgameshared.messaging.messages.StartPathMessage;
import socketgameshared.messaging.messages.enums.GameState;
import socketgameshared.messaging.messages.enums.PlayerRole;
import socketgameshared.messaging.messages.enums.PlayerUpdate;
import socketgameshared.messaging.messages.fromserver.*;

import java.util.Random;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClientMessageProcessorTest {

    private IClientMessageProcessor msgProc;
    private ClientMessageHandlerFactory msgFact;
    private IGameClient mockGameClient;

    private Random r;
    private Gson gson;

    @Before
    public void setupMessageProcessor() {
        mockGameClient = Mockito.mock(IGameClient.class);
        msgFact = new ClientMessageHandlerFactory();
        msgProc = new ClientMessageProcessor(msgFact);
        msgProc.registerGameClient(mockGameClient);

        r = new Random();
        gson = new Gson();
    }

    private void processMessage(Object obj) {
        String sessionId = Integer.toString(r.nextInt(99999));
        String data = gson.toJson(obj);

        msgProc.processMessage(sessionId, obj.getClass().toString(), data);
    }

    @Test
    public void addPointToPathMessageTest() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        AddPointToPathMessage msg = new AddPointToPathMessage(x, y);
        processMessage(msg);
        verify(mockGameClient, times(1)).handleAddPointToPath(x, y);
    }

    @Test
    public void clearCanvasMessageTest() {
        ClearCanvasMessage msg = new ClearCanvasMessage();
        processMessage(msg);
        verify(mockGameClient, times(1)).handleClearCanvas();
    }

    @Test
    public void startPathMessageTest() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        double colorR = r.nextDouble(), colorG = r.nextDouble(), colorB = r.nextDouble();
        StartPathMessage msg = new StartPathMessage(x, y, colorR, colorG, colorB);
        processMessage(msg);
        verify(mockGameClient, times(1)).handleStartPath(x, y, colorR, colorG, colorB);
    }

    @Test
    public void guessResultMessageTest() {
        boolean guessedRight = r.nextBoolean();
        String guess = Integer.toString(r.nextInt(99999));;
        GuessResultMessage msg = new GuessResultMessage(guessedRight, guess);
        processMessage(msg);
        verify(mockGameClient, times(1)).handleGuessResult(guessedRight, guess);
    }

    @Test
    public void loginResultMessageTest() {
        String token = Integer.toString(r.nextInt(99999));
        boolean succes = r.nextBoolean();
        String message = Integer.toString(r.nextInt(99999));
        LoginResultMessage msg = new LoginResultMessage(token, succes, message);
        processMessage(msg);
        verify(mockGameClient, times(1)).handleLoginResponse(token, succes, message);
    }

    @Test
    public void playerListUpdateMessageTest() {
        String playerName = Integer.toString(r.nextInt(99999));
        PlayerUpdate playerUpdate = PlayerUpdate.LEFT;
        PlayerListUpdateMessage msg = new PlayerListUpdateMessage(playerName, playerUpdate);
        processMessage(msg);
        verify(mockGameClient, times(1)).handlePlayerListUpdate(playerName, playerUpdate);
    }

    @Test
    public void registrationResultMessageTest() {
        boolean result = r.nextBoolean();
        String message = Integer.toString(r.nextInt(99999));
        RegistrationResultMessage msg = new RegistrationResultMessage(result, message);
        processMessage(msg);
        verify(mockGameClient, times(1)).handlePlayerRegistrationResponse(result, message);
    }

    @Test
    public void startNewRoundMessageTest() {
        PlayerRole role = PlayerRole.DRAWER;
        String word = Integer.toString(r.nextInt(99999));
        StartNewRoundMessage msg = new StartNewRoundMessage(role, word);
        processMessage(msg);
        verify(mockGameClient, times(1)).handleStartNewRound(role, word);
    }

    @Test
    public void updateChatMessageTest() {
        String playerName = Integer.toString(r.nextInt(99999));
        String message = Integer.toString(r.nextInt(99999));
        UpdateChatMessage msg = new UpdateChatMessage(playerName, message);
        processMessage(msg);
        verify(mockGameClient, times(1)).handleUpdateChat(playerName, message);
    }

    @Test
    public void updateGameStateMessageTest() {
        GameState state = GameState.WAITING_FOR_PLAYERS;
        UpdateGameStateMessage msg = new UpdateGameStateMessage(state);
        processMessage(msg);
        verify(mockGameClient, times(1)).handleUpdateGameState(state);
    }
}

package messaging;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import socketgameserver.messaging.IServerMessageProcessor;
import socketgameserver.messaging.ServerMessageHandlerFactory;
import socketgameserver.messaging.ServerMessageProcessor;
import socketgameserver.model.IGame;
import socketgameshared.messaging.messages.AddPointToPathMessage;
import socketgameshared.messaging.messages.ClearCanvasMessage;
import socketgameshared.messaging.messages.StartPathMessage;
import socketgameshared.messaging.messages.fromclient.GuessWordMessage;
import socketgameshared.messaging.messages.fromclient.LoginMessage;
import socketgameshared.messaging.messages.fromclient.RegisterPlayerMessage;

import java.util.Random;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ServerMessageProcessorTest {

    private IServerMessageProcessor msgProc;
    private ServerMessageHandlerFactory msgFact;
    private IGame mockGame;

    private Random r;
    private Gson gson;

    @Before
    public void setupMessageProcessor() {
        mockGame = Mockito.mock(IGame.class);
        msgFact = new ServerMessageHandlerFactory();
        msgProc = new ServerMessageProcessor(msgFact);
        msgProc.registerGame(mockGame);

        r = new Random();
        gson = new Gson();
    }

    private String processMessage(Object obj) {
        String sessionId = Integer.toString(r.nextInt(99999));
        String data = gson.toJson(obj);

        msgProc.processMessage(sessionId, obj.getClass().toString(), data);
        return sessionId;
    }

    @Test
    public void addPointToPathMessageTest() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        AddPointToPathMessage msg = new AddPointToPathMessage(x, y);
        String sessionId = processMessage(msg);
        verify(mockGame, times(1)).addPointToPath(sessionId, x, y);
    }

    @Test
    public void clearCanvasMessageTest() {
        ClearCanvasMessage msg = new ClearCanvasMessage();
        String sessionId = processMessage(msg);
        verify(mockGame, times(1)).clearCanvas(sessionId);
    }

    @Test
    public void startPathMessageTest() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        double colorR = r.nextDouble(), colorG = r.nextDouble(), colorB = r.nextDouble();
        StartPathMessage msg = new StartPathMessage(x, y, colorR, colorG, colorB);
        String sessionId = processMessage(msg);
        verify(mockGame, times(1)).startPath(sessionId, x, y, colorR, colorG, colorB);
    }

    @Test
    public void guessWordMessageTest() {
        String guess = Integer.toString(r.nextInt(99999));
        GuessWordMessage msg = new GuessWordMessage(guess);
        String sessionId = processMessage(msg);
        verify(mockGame, times(1)).guessWord(sessionId, guess);
    }

    @Test
    public void loginMessageTest() {
        String username = Integer.toString(r.nextInt(99999));
        String password = Integer.toString(r.nextInt(99999));
        LoginMessage msg = new LoginMessage(username, password);
        String sessionId = processMessage(msg);
        verify(mockGame, times(1)).loginPlayer(username, password, sessionId);
    }

    @Test
    public void registerPlayerMessage() {
        String username = Integer.toString(r.nextInt(99999));
        String password = Integer.toString(r.nextInt(99999));
        RegisterPlayerMessage msg = new RegisterPlayerMessage(username, password);
        String sessionId = processMessage(msg);
        verify(mockGame, times(1)).registerNewPlayer(username, password, sessionId);
    }
}

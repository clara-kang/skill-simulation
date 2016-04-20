package communication; /**
 * Created by Clara on 2016-04-19.
 */

import logic.DamageCalculation;

import javax.websocket.*;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

@ServerEndpoint(value = "/game")
public class SimulationServerEndpoint {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        if (message.equals("quit")) {
            try {
                session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game ended"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (message.equals("start")) {
            try {
                DamageCalculation.calculateDamage(session);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        logger.info(String.format("received: %s !!!", message));
        return message;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }
}

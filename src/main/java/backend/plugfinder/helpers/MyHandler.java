package backend.plugfinder.helpers;

import backend.plugfinder.controllers.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyHandler extends TextWebSocketHandler {
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        URI uri = session.getUri();
        String query = uri.getQuery();

        String user_id = null;
        String[] params = query.split("&");
        for (String param : params) {
            String[] key = param.split("=");
            if (key[0].equals("userId")) {
                user_id = key[1];
                break;
            }
        }

        if (user_id != null) {
            sessions.put(user_id, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // eliminar la sesión al desconectar
        String userId = session.getAttributes().get("userId").toString();
        sessions.remove(userId);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        MessageDto messageDto;
        try {
            messageDto = new ObjectMapper().readValue(message.getPayload(), MessageDto.class);
        } catch (JsonProcessingException e) {
            // handle the exception
            return;
        }

        JSONObject json = new JSONObject(message);
        String toUserId = json.getString("target_id");

        WebSocketSession toSession = sessions.get(toUserId);
        if (toSession != null && toSession.isOpen()) {
            try {
                toSession.sendMessage((WebSocketMessage<?>) messageDto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

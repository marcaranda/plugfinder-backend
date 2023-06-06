package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.MessageDto;
import backend.plugfinder.helpers.OurException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simp_messaging_template;
    @Autowired
    private ChatController chat_controller;

    @MessageMapping("/message")
    public MessageDto send_message(@Payload MessageDto message) throws OurException {
        message.setTime(String.valueOf(LocalDateTime.now()));
        simp_messaging_template.convertAndSendToUser(String.valueOf(message.getTarget_id()), "/private", message);
        chat_controller.save_message(message);
        return message;
    }
}

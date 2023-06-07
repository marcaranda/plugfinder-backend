package backend.plugfinder.controllers;

import backend.plugfinder.controllers.dto.MessageDto;
import backend.plugfinder.helpers.OurException;
import backend.plugfinder.services.ChatService;
import backend.plugfinder.services.models.MessageModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatService chat_service;

    @PostMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public String save_message(@RequestBody MessageDto message) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        return chat_service.send_message(model_mapper.map(message, MessageModel.class));
    }

    @GetMapping
    @PreAuthorize("@securityService.not_userAPI()")
    public ArrayList<MessageDto> get_messages(@RequestParam("source_id") long source_id, @RequestParam("target_id") long target_id) throws OurException {
        ModelMapper model_mapper = new ModelMapper();
        ArrayList<MessageDto> messages = (ArrayList<MessageDto>) chat_service.get_messages(source_id, target_id).stream()
                .map(elementB -> model_mapper.map(elementB, MessageDto.class))
                .collect(Collectors.toList());
        return messages;
    }

    @PostMapping(path = "/{user_id}/signin")
    @PreAuthorize("@securityService.not_userAPI()")
    public String signin(@PathVariable("user_id") long user_id) throws OurException {
        return chat_service.signin(user_id);
    }

    @PostMapping(path = "/{user_id}/disconnect")
    @PreAuthorize("@securityService.not_userAPI()")
    public String disconnect(@PathVariable("user_id") long user_id) throws OurException {
        return chat_service.disconnect(user_id);
    }
}

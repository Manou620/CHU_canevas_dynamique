package com.chu.canevas.controller;

import com.chu.canevas.dto.Personnel.PresentPersonnelDto;
import com.chu.canevas.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ScanService scanService;

    @MessageMapping("/update-number")
    public void updateNumber() {
        messagingTemplate.convertAndSend("/topic/number", new Date());
    }

    @MessageMapping("/list-present")
    public void getEmployeePresentList() {
        List<PresentPersonnelDto> presentPersonnel = scanService.getPresentPersonnel();
        messagingTemplate.convertAndSend("/topic/present-list", presentPersonnel);
    }

}

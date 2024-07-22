package com.b1.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
}

package com.app.events.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.TicketDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.TicketMapper;
import com.app.events.model.Ticket;
import com.app.events.service.TicketService;

@RestController
@CrossOrigin(origins = "*")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGULAR')")
	@GetMapping(value = "/api/tickets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDTO> getTicket(@PathVariable("id") Long id) throws ResourceNotFoundException {
		Ticket ticket = ticketService.findOne(id);
		return new ResponseEntity<>(TicketMapper.toDTO(ticket), HttpStatus.OK);
	}

	@GetMapping(value = "/api/ticketsForEvent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TicketDTO>> getTicketForSector(@PathVariable("id") Long eventId)
			throws ResourceNotFoundException {

		Collection<Ticket> tickets = ticketService.findAllByEventId(eventId);
		List<TicketDTO> retVal = tickets.stream().map(ticket -> TicketMapper.toDTO(ticket))
				.collect(Collectors.toList());
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@GetMapping(value = "/api/ticket/reservationsUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_REGULAR')")
	public ResponseEntity<Collection<TicketDTO>> getTicketReservationsUser(@PathVariable("id") Long userId)
			throws ResourceNotFoundException {
		Collection<Ticket> tickets = ticketService.findAllReservationsByUserId(userId);
		List<TicketDTO> retVal = tickets.stream().map(ticket -> TicketMapper.toDTO(ticket))
				.collect(Collectors.toList());
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	// @PostMapping(value = "/api/tickets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// public ResponseEntity<TicketDTO> createTicket(TicketDTO ticketDTO) throws Exception {

	// 	try {
	// 		TicketDTO savedTicket = ticketService.create(ticket);
	// 		return new ResponseEntity<TicketDTO>(savedTicket, HttpStatus.CREATED);
	// 	} catch (Exception e) {
	// 		return new ResponseEntity<TicketDTO>(HttpStatus.BAD_REQUEST);
	// 	}
	// }

	@PutMapping(value = "/api/reserveTicket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_REGULAR')")
	public ResponseEntity<Collection<TicketDTO>> reserveTicket(@RequestBody TicketBuyReservationDTO ticketDTO) throws Exception {
		Collection<Ticket> tickets = ticketService.reserveTicket(ticketDTO.getTicketIDs(), ticketDTO.getUserId());
		List<TicketDTO> retVal = tickets.stream().map(
										ticket -> TicketMapper.toDTO(ticket)
									).collect(Collectors.toList());
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@PutMapping(value = "/api/ticketPaymentCreation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_REGULAR')")
	public ResponseEntity<Map<String, Object>> ticketPaymentCreation(@RequestBody TicketDTO ticketDTO)
			throws Exception {
		Map<String, Object> res = ticketService.ticketPaymentCreation(ticketDTO.getId(), ticketDTO.getUserId());
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping(value = "/api/buyTicket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_REGULAR')")
	public ResponseEntity<List<TicketDTO>> buyTicket(@RequestBody TicketBuyReservationDTO ticketDTO) throws Exception {
		Collection<Ticket> tickets = ticketService.buyTickets(
								ticketDTO.getTicketIDs(), ticketDTO.getUserId(),
								ticketDTO.getPayPalPaymentID(), ticketDTO.getPayPalPayerID());

		List<TicketDTO> retVal = tickets.stream().map(
										ticket -> TicketMapper.toDTO(ticket)
									).collect(Collectors.toList());
		return new ResponseEntity<>(retVal, HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/tickets/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Ticket> deleteTicket(@PathVariable("id") Long id) {
		ticketService.delete(id);
		return new ResponseEntity<Ticket>(HttpStatus.NO_CONTENT);
	}

}

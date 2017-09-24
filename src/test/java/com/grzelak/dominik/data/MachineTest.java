package com.grzelak.dominik.data;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MachineTest {
    @Test
    public void checkTickets() throws Exception {
        TicketContainer ticketContainer = new TicketContainer(5);
        Machine m = new Machine(ticketContainer, System.out);
        Ticket ticket = ticketContainer.getOne_timeD().get(0);
        assertTrue(m.checkTickets(ticket,5));
        assertFalse(m.checkTickets(ticket,6));
    }

    @Test
    public void checkCoin() throws Exception {
        TicketContainer ticketContainer = new TicketContainer(5);
        Machine m = new Machine(ticketContainer, System.out);
        Coins c = Coins.GR_20;
        assertEquals(c, m.checkCoin(20));
        assertNotEquals(c, m.checkCoin(50));
        assertNull(m.checkCoin(25));
    }

    @Test
    public void executeTransaction() throws Exception {
        TicketContainer ticketContainer = new TicketContainer(5);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Machine m = new Machine(ticketContainer, new PrintStream(baos));
        Ticket ticket = ticketContainer.getOne_timeN().get(1);
        Coins.GR_50.setAmount(5);
        Coins.GR_10.setAmount(0);
        m.executeTransaction(ticket, 1, 500, ticket.getPrice(), Arrays.asList(Coins.values()));

        assertEquals(baos.toString().trim(), "Nie mozna wyplacic reszty.\nTransakcja anulowana!\n".trim());
    }

    @Test
    public void payRest() throws Exception {
        TicketContainer ticketContainer = new TicketContainer(5);
        Machine m = new Machine(ticketContainer, System.out);
        Coins.GR_50.setAmount(1);
        assertTrue(m.payRest(50));

        Coins.GR_10.setAmount(0);
        assertFalse(m.payRest(10));
    }

}
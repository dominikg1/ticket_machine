package com.grzelak.dominik.data;

import java.util.ArrayList;
import java.util.List;

public class TicketContainer {

    private int ticket_amount;

    private List<Ticket> temporaryN, temporaryD; //bilety czasowe normalne oraz ze znizkami
    private List<Ticket> one_timeN, one_timeD; //bilety jednorazowe normalne oraz ze znizkami

    List<Ticket> getTemporaryN() {
        return temporaryN;
    }
    List<Ticket> getOne_timeN() {
        return one_timeN;
    }
    List<Ticket> getTemporaryD() {
        return temporaryD;
    }
    List<Ticket> getOne_timeD() {
        return one_timeD;
    }

    public TicketContainer(int ticket_amount) {
        this.ticket_amount = ticket_amount;
        temporaryN = new ArrayList<>();
        one_timeN = new ArrayList<>();
        temporaryD = new ArrayList<>();
        one_timeD = new ArrayList<>();
        createTickets();
    }

    private void createTickets() { //bilety do wyboru
        temporaryN.add(new Ticket("30min", 300, 1, ticket_amount));
        temporaryN.add(new Ticket("60min", 450, 1, ticket_amount));
        temporaryN.add(new Ticket("90min", 600, 1, ticket_amount));
        temporaryN.add(new Ticket("24h", 1100, 1, ticket_amount));

        one_timeN.add(new Ticket("Normalny", 300, 1, ticket_amount));
        one_timeN.add(new Ticket("Specjalny", 320, 1, ticket_amount));

        temporaryD.add(new Ticket("30min Ulgowy", 300, 0.65, ticket_amount));
        temporaryD.add(new Ticket("60min Ulgowy", 450, 0.65, ticket_amount));
        temporaryD.add(new Ticket("90min Ulgowy", 600, 0.65, ticket_amount));
        temporaryD.add(new Ticket("24h Ulgowy", 1100, 0.65, ticket_amount));

        one_timeD.add(new Ticket("Normalny Ulgowy", 300, 0.65, ticket_amount));
        one_timeD.add(new Ticket("Specjalny Ulgowy", 320, 0.65, ticket_amount));
    }

    void printTickets(List<Ticket> tickets) { //wyswietlenie biletow wybranego rodzaju
        System.out.println();
        for(int i = 0; i < tickets.size(); i++){
            System.out.println(i+1 + ". " + tickets.get(i));
        }
    }
}

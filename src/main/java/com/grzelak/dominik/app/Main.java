package com.grzelak.dominik.app;

import com.grzelak.dominik.data.Machine;
import com.grzelak.dominik.data.TicketContainer;

public class Main {
    public static void main(String[] args) {
        Machine machine = new Machine(new TicketContainer(10), System.out);

        machine.controlLoop();
    }
}

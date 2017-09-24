package com.grzelak.dominik.data;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Machine {

    private static final String WRONG_CHOICE = "Bledny wybor. Wybierz jeszcze raz: ";
    private TicketContainer ticketContainer;

    private Scanner sc;
    PrintStream printStream;

    public Machine(TicketContainer ticketContainer, PrintStream printStream) {
        sc = new Scanner(System.in);
        this.ticketContainer = ticketContainer;
        this.printStream = printStream;
    }

    public void controlLoop() { //petla glowna programu
        Ticket chosen;
        int quantity;
        int choose;

        do {
            printStream.print("\nAutomat do biletow\n1. Kup bilet\n0. Koniec\nTwoj wybor: ");
            choose = getInt(0, 1);
            switch (choose) {
                case 1:
                    chosen = chooseTicket();
                    printStream.print("\nIlosc: ");
                    quantity = getInt(1, 1000);

                    if (checkTickets(chosen, quantity)) {
                        ticketPaying(chosen, quantity);
                    }
                    break;

                case 0:
                    printStream.println("Dziekujemy za skorzystanie z naszych uslug");
                    break;
            }
        }while(choose != 0);

        sc.close();
    }
    int getInt(int from, int to) {
        while (true) {
            if (sc.hasNextInt()) {
                Integer value = sc.nextInt();
                if(value >= from && value <= to)
                    return value;
            } else if (sc.hasNext()) {
                sc.next();
            }
            printStream.println(WRONG_CHOICE);
        }
    }

     Ticket chooseTicket() { //funkcja odpowiedzialna za wybor odpowiedniego biletu
        boolean discount = false;
        printStream.print("\nJaki bilet wybierasz:\n1. Ulgowy\n2. Normalny\nTwoj wybor: ");
        int choose = getInt(1, 2);
        if(choose == 1)
            discount = true;

        printStream.print("\nRodzaj biletu:\n1. Czasowy\n2. Jednorazowy\nTwoj wybor: ");
        choose = getInt(1, 2);

        List<Ticket> tickets = new ArrayList<>();
        if(choose == 1 && !discount){
            tickets = ticketContainer.getTemporaryN();
        }else if(choose == 2 && !discount){
            tickets = ticketContainer.getOne_timeN();
        }else if(choose == 1){
            tickets = ticketContainer.getTemporaryD();
        }else if(choose == 2){
            tickets = ticketContainer.getOne_timeD();
        }
        ticketContainer.printTickets(tickets);
        printStream.print("Twoj wybor: ");

        return tickets.get(getInt(1, tickets.size())-1);
    }

     boolean checkTickets(Ticket ticket, int quantity) { //funkcja sprawdzajaca wystarczajaca ilosc biletow
        if(quantity <= ticket.getQuantity()) {
            return true;
        }
        printStream.println("Nie posiadamy wystarczajacej ilosci biletow.\nPozostala ilosc biletow tego rodzaju to: " + ticket.getQuantity() + "\nPrzepraszamy. Transakcja anulowana\n");
        return false;
    }

     void ticketPaying(Ticket ticket, int quantity){ //funkcja symulujaca placenie za bilet z mozliwoscia anulowania transakcji
        ArrayList<Coins> coins = new ArrayList<>();
        Coins c;
        int toPay = ticket.getPrice()*quantity;
        int paid = 0;
        int coin;
        boolean cancel = false;

        while(toPay > paid){
            printStream.print("\nDo zaplaty: " + (double)(toPay-paid)/100 + "PLN\nWrzuc monete (10/20/50/100/200/500, 0 - ANULUJ): ");
            coin = getInt(0, 500);
            if(coin != 0){
                if((c = checkCoin(coin)) != null){
                    paid += coin;
                    coins.add(c);
                }
            } else {
                printStream.println("Transakcja zostala anulowana. Zwrocono: " + paid);
                cancel = true;
                break;
            }
        }
        if(!cancel)
            executeTransaction(ticket, quantity, paid, toPay, coins);
    }

     Coins checkCoin(int coin) { //funkcja sprawdzajaca czy wrzucono prawidlowa monete
        for(Coins c: Coins.values()){
            if(c.getPln() == coin)
                return c;
        }
        printStream.println("\nNieprawidlowa moneta. Zwracam monete: " + coin);
        return null;
    }

     void executeTransaction(Ticket ticket, int quantity, int paid, int toPay, List<Coins> coins){ //funkcja drukujaca bilety przy mozliwosci wyplacenia reszty
        if(payRest(paid-toPay)){
            ticket.setQuantity(ticket.getQuantity()-quantity);
            for (Coins coin : coins) coin.setAmount(coin.getAmount() + 1);
            for(int i = 0; i < quantity; i++)
                printStream.println("Drukuje bilet nr" + (i+1) + ": " + ticket);
            printStream.println("Odbierz reszte: " + (double)(paid-toPay)/100);
        }else{
            printStream.println("Nie mozna wyplacic reszty.\nTransakcja anulowana!\n");
        }
    }

    /**
    * funkcja sprawdzajaca czy automat jest w stanie wydac poprawnie reszte, jesli nie - anuluje transakcje
    * @param rest reszta
    */
     boolean payRest(int rest) {
        if(rest == 0)
            return true;
        ArrayList<Coins> coins = new ArrayList<>();

        for(Coins c: Coins.values()){
            while(c.getPln() <= rest){
                if(c.getAmount() > 0){
                    rest -= c.getPln();
                    c.setAmount(c.getAmount()-1);
                    coins.add(c);

                    if(rest == 0)
                        return true;
                }else
                    break;
            }
        }
        for (Coins coin : coins) {
            coin.setAmount(coin.getAmount() + 1);
        }
        return false;
    }

}

package org.sube.project.card;

import org.sube.project.accounts.UserType;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.types.TransactionRecharge;

import java.util.Scanner;

public class CardManager {

    private static int NORMAL_TICKET=1180;

    public static void addBalance(Card card, double amount) {
        card.setBalance(card.getBalance() + amount);
        card.getTransactionHistory().add(new TransactionRecharge(amount));

    }

    public static boolean payTicket(Card card){
        if(card.getCardType()==CardType.NORMAL_CARD){
            card.setBalance((card.getBalance())-NORMAL_TICKET);
            return true;
        }
        if(card.getCardType()==CardType.RETIRED){
            card.setBalance((card.getBalance())-NORMAL_TICKET*0.45);
            return true;
        }
        if(card.getCardType()==CardType.STUDENT || card.getCardType()==CardType.TEACHER){
            card.setBalance((card.getBalance())-NORMAL_TICKET*0.80);
            return true;
        }
        if(card.getCardType()==CardType.DISABLED_PERSON){
            card.setBalance((card.getBalance())-NORMAL_TICKET*0.50);
            return true;
        }
        return false;
    }

}

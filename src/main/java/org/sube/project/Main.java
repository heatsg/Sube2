package org.sube.project;

import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.card.Card;
import org.sube.project.card.CardType;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;
import org.sube.project.front.Sube;

public class Main {
    public static void main(String[] args) {
        Sube sube = new Sube();
        sube.showUI();
//        Card c1=new Card(100.0,true, CardType.NORMAL_CARD);
//        User u1= new User("bro","broapellido",10,"12345678","masculino",c1, UserType.NORMAL_USER,true,"123");
//        System.out.println(u1.toString());
//        System.out.println(c1.toString());
    }
}
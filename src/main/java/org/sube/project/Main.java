package org.sube.project;

import org.sube.project.accounts.User;
import org.sube.project.accounts.UserType;
import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.card.Card;
import org.sube.project.card.CardType;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;
import org.sube.project.card.transaction.types.TransactionPayment;
import org.sube.project.card.transaction.types.TransactionRecharge;
import org.sube.project.front.Sube;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Sube.getInstance().showUI(true);

        User u1 = new User("bro", "broapellido", 10, "12345678", "masculino", UserType.NORMAL_USER, true, "123");
        Card c1 = new Card(CardType.NORMAL_CARD, u1.getDocumentNumber());
        Transaction t1 = new TransactionPayment(1000.0, LocalDateTime.now().toString());
        Transaction t2 = new TransactionRecharge(1000.0, LocalDateTime.now().toString());
        System.out.println("|Id  |Nombre      |Apellido    |Documento |Edad |Genero    |Usuario   |Estado |");
        System.out.println(u1.toString());
        System.out.println("|Numero de tarjeta |Documento |Balance |Estado |Tarjeta    |");
        System.out.println(c1.toString());
        System.out.println("|Id  |Coste  |Transaccion |Fecha                         |");
        System.out.println(t1.toString());
        System.out.println(t2.toString());
    }
}
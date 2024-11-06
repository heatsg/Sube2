package org.sube.project;

import org.sube.project.accounts.authentication.UserAuthentication;
import org.sube.project.card.Card;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;
import org.sube.project.front.Sube;

public class Main {
    public static void main(String[] args) {
        Sube sube = new Sube();
        sube.showUI();
    }
}
package org.sube.project.util.sortedprint;

import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.util.TYPE;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MapToList {
    public static <K, T> List<T> TransportMapToList(Map<K, T> map) {
        List<T> list = new ArrayList<>();

        for (Map.Entry<K, T> entry : map.entrySet())
            list.add(entry.getValue());

        return list;
    }

    public static List<User> OrderList(List<User> list, UserOrder order) {
        switch (order) {
            case SURNAME -> list.sort(Comparator.comparing(User::getSurname));
            case DOCUMENT_NUMBER -> list.sort(Comparator.comparing(User::getDocumentNumber));
            case AGE -> list.sort(Comparator.comparing(User::getAge));
        }
        return list;
    }

    public static List<Card> OrderList(List<Card> list, CardOrder order) {
        switch (order) {
            case ID -> list.sort(Comparator.comparing(Card::getId));
            case BALANCE -> list.sort(Comparator.comparing(Card::getBalance));
        }
        return list;
    }

    public static List<Transaction> OrderList(List<Transaction> list, TransactionOrder order) {
        switch (order) {
            case ID -> list.sort(Comparator.comparing(Transaction::getId));
            case AMOUNT -> list.sort(Comparator.comparing(Transaction::getAmount));
            case DATE -> list.sort(Comparator.comparing(Transaction::getDateTime));
        }
        return list;
    }

    public static <K, T> void printList(Map<K, T> map) {
        System.out.println(TransportMapToList(map));
    }

    public static void printHeader(TYPE type){
        switch (type) {
            case USER -> System.out.println("|Id  |Nombre      |Apellido    |Documento |Edad |Genero    |Usuario   |Estado |");
            case CARD -> System.out.println("|Numero de tarjeta |Documento |Balance |Estado |Tarjeta    |");
            case TRANSACTION -> System.out.println("|Id  |Coste  |Transaccion |Fecha                         |");
        }
    }

    public static void printOrdered(Map<String, User> map, UserOrder order) {
        printHeader(TYPE.USER);
        System.out.println(OrderList(TransportMapToList(map), order));
    }

    public static void printOrdered(Map<String, Card> map, CardOrder order) {
        printHeader(TYPE.CARD);
        System.out.println(OrderList(TransportMapToList(map), order));
    }

    public static void printOrdered(Map<String, Transaction> map, TransactionOrder order) {
        printHeader(TYPE.TRANSACTION);
        System.out.println(OrderList(TransportMapToList(map), order));
    }
}

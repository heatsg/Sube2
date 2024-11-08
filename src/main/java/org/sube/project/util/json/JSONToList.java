package org.sube.project.util.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;
import org.sube.project.card.transaction.types.TransactionPayment;
import org.sube.project.card.transaction.types.TransactionRecharge;
import org.sube.project.util.Path;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JSONToList {

    /// paso el archivo respectivo a una lista para luego poder filtrarlo con el sort de arraylist

    public static List<User> UserToList(Path path) {
        JSONArray jarr;
        List<User> list = new ArrayList<>();
        try {
            jarr = JSONManager.readJSONArray(path);
            for (int i = 0; i < jarr.length(); i++) {
                list.add(new User(jarr.getJSONObject(i)));
            }
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return list;
    }

    public static List<Card> CardToList(Path path) {
        JSONArray jarr;
        List<Card> list = new ArrayList<>();
        try {
            jarr = JSONManager.readJSONArray(path);
            for (int i = 0; i < jarr.length(); i++) {
                list.add(new Card(jarr.getJSONObject(i)));
            }
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return list;
    }

    public static List<Transaction> TransactionToList(String cardId) {
        JSONArray jarr;
        List<Transaction> list = new ArrayList<>();
        try {
            jarr = JSONManager.readJSONArray(Path.CARD);
            for (int i = 0; i < jarr.length(); i++) {

                if(jarr.getJSONObject(i).getString("id").equals(cardId)){

                    JSONArray jarr2=jarr.getJSONObject(i).getJSONArray("transactionHistory");

                    for (int j = 0; j < jarr2.length(); j++) {

                        if (jarr2.getJSONObject(i).getString("transactionType").equals(TransactionType.PAYMENT.toString())) {

                            list.add(new TransactionPayment(jarr.getJSONObject(i)));

                        } else
                            list.add(new TransactionRecharge(jarr.getJSONObject(i)));
                    }
                }

            }
        } catch (JSONException jx) {
            System.out.println(jx.getMessage());
        }
        return list;
    }


    /// funciones de ordenado

    public static List<User> orderByUserId(List<User> list) {
        list.sort(Comparator.comparingInt(User::getId));
        return list;
    }

    public static List<User> orderBySurname(List<User> list) {
        list.sort(Comparator.comparing(User::getSurname));
        return list;
    }

    public static List<User> orderByDocumentNumber(List<User> list) {
        list.sort(Comparator.comparing(User::getDocumentNumber));
        return list;
    }

    public static List<Card> orderByCardId(List<Card> list) {
        list.sort(Comparator.comparing(Card::getId));
        return list;
    }

    public static List<Card> orderByBalance(List<Card> list) {
        list.sort(Comparator.comparingDouble(Card::getBalance));
        return list;
    }

    public static List<Transaction> orderByTransactionId(List<Transaction> list) {
        list.sort(Comparator.comparing(Transaction::getId));
        return list;
    }

    public static List<Transaction> orderByAmount(List<Transaction> list) {
        list.sort(Comparator.comparing(Transaction::getAmount));
        return list;
    }

    public static List<Transaction> orderByDate(List<Transaction> list) {
        list.sort(Comparator.comparing(Transaction::getDateTime));
        return list;
    }

    /// funcion de mostrado

    public static <T> void printList(List<T> list){
        System.out.println(list);
    }

    /// funciones de mostrado de usuario

    public static void printUserList(){
        System.out.println("|Id  |Nombre      |Apellido    |Documento |Edad |Genero    |Usuario   |Estado |");
        JSONToList.printList(JSONToList.UserToList(Path.USER));
    }

    public static void printUserListOrderedById(){
        System.out.println("|Id  |Nombre      |Apellido    |Documento |Edad |Genero    |Usuario   |Estado |");
        JSONToList.printList(JSONToList.orderByUserId(JSONToList.UserToList(Path.USER)));
    }

    public static void printUserListOrderedBySurname(){
        System.out.println("|Id  |Nombre      |Apellido    |Documento |Edad |Genero    |Usuario   |Estado |");
        JSONToList.printList(JSONToList.orderBySurname(JSONToList.UserToList(Path.USER)));
    }

    public static void printUserListOrderedByDocumentNumber(){
        System.out.println("|Id  |Nombre      |Apellido    |Documento |Edad |Genero    |Usuario   |Estado |");
        JSONToList.printList(JSONToList.orderByDocumentNumber(JSONToList.UserToList(Path.USER)));
    }

    /// funciones de mostrado de las tarjetas

    public static void printCardList(){
        System.out.println("|Numero de tarjeta |Documento |Balance |Estado |Tarjeta    |");
        JSONToList.printList(JSONToList.CardToList(Path.CARD));
    }

    public static void printCardListOrderedById(){
        System.out.println("|Numero de tarjeta |Documento |Balance |Estado |Tarjeta    |");
        JSONToList.printList(JSONToList.orderByCardId(JSONToList.CardToList(Path.CARD)));
    }

    public static void printCardListOrderedByBalance(){
        System.out.println("|Numero de tarjeta |Documento |Balance |Estado |Tarjeta    |");
        JSONToList.printList(JSONToList.orderByBalance(JSONToList.CardToList(Path.CARD)));
    }

    /// funciones de mostrado de las transacciones

    public static void printTransactionList(String cardId){
        System.out.println("|Id  |Coste  |Transaccion |Fecha                         |");
        JSONToList.printList(JSONToList.TransactionToList(cardId));
    }

    public static void printTransactionListOrderedById(String cardId){
        System.out.println("|Id  |Coste  |Transaccion |Fecha                         |");
        JSONToList.printList(JSONToList.orderByTransactionId(JSONToList.TransactionToList(cardId)));
    }

    public static void printTransactionListOrderedByAmount(String cardId){
        System.out.println("|Id  |Coste  |Transaccion |Fecha                         |");
        JSONToList.printList(JSONToList.orderByAmount(JSONToList.TransactionToList(cardId)));
    }

    public static void printTransactionListOrderedByDate(String cardId){
        System.out.println("|Id  |Coste  |Transaccion |Fecha                         |");
        JSONToList.printList(JSONToList.orderByDate(JSONToList.TransactionToList(cardId)));
    }


}

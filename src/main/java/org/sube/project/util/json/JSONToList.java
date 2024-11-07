package org.sube.project.util.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.card.transaction.TransactionType;
import org.sube.project.card.transaction.types.TransactionPayment;
import org.sube.project.card.transaction.types.TransactionRecharge;
import org.sube.project.util.PATH;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JSONToList {

    /// paso el archivo respectivo a una lista para luego poder filtrarlo con el sort de arraylist

    public static List<User> UserToList(PATH path) {
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

    public static List<Card> CardToList(PATH path) {
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

    public static List<Transaction> TransactionToList(PATH path) {
        JSONArray jarr;
        List<Transaction> list = new ArrayList<>();
        try {
            jarr = JSONManager.readJSONArray(path);
            for (int i = 0; i < jarr.length(); i++) {
                if(jarr.getJSONObject(i).getString("transactionType").equals(TransactionType.PAYMENT.toString())){
                    list.add(new TransactionPayment(jarr.getJSONObject(i)));
                }
                else list.add(new TransactionRecharge(jarr.getJSONObject(i)));
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

    /// funciones de mostrado

    public static <T> void printList(List<T> list){
        System.out.println(list);
    }

    public static void printUserList(){
        JSONToList.printList(JSONToList.UserToList(PATH.USER));
    }

    public static void printUserListOrderedById(){
        JSONToList.printList(JSONToList.orderByUserId(JSONToList.UserToList(PATH.USER)));
    }

    public static void printUserListOrderedBySurname(){
        JSONToList.printList(JSONToList.orderBySurname(JSONToList.UserToList(PATH.USER)));
    }

    public static void printUserListOrderedByDocumentNumber(){
        JSONToList.printList(JSONToList.orderByDocumentNumber(JSONToList.UserToList(PATH.USER)));
    }

    public static void printCardList(){
        JSONToList.printList(JSONToList.CardToList(PATH.CARD));
    }

    public static void printCardListOrderedById(){
        JSONToList.printList(JSONToList.orderByCardId(JSONToList.CardToList(PATH.CARD)));
    }

    public static void printCardListOrderedByBalance(){
        JSONToList.printList(JSONToList.orderByBalance(JSONToList.CardToList(PATH.CARD)));
    }

    public static void printTransactionList(){
        JSONToList.printList(JSONToList.TransactionToList(PATH.TRANSACTION));
    }

    public static void printTransactionListOrderedById(){
        JSONToList.printList(JSONToList.orderByTransactionId(JSONToList.TransactionToList(PATH.TRANSACTION)));
    }

    public static void printTransactionListOrderedByAmount(){
        JSONToList.printList(JSONToList.orderByAmount(JSONToList.TransactionToList(PATH.TRANSACTION)));
    }

    public static void printTransactionListOrderedByDate(){
        JSONToList.printList(JSONToList.orderByDate(JSONToList.TransactionToList(PATH.TRANSACTION)));
    }


}

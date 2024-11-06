package org.sube.project.util.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.sube.project.accounts.User;
import org.sube.project.card.Card;
import org.sube.project.card.transaction.Transaction;
import org.sube.project.util.PATH;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JSONToList {

    /// paso el archivo respectivo a una lista para luego poder filtrarlo con el sort de arraylist

    public static List<User> UserToList(PATH path){
        JSONArray jarr;
        List<User> list=new ArrayList<>();
        try {
            jarr=JSONManager.readJSONArray(path);
            for (int i = 0; i < jarr.length(); i++) {
                list.add(new User(jarr.getJSONObject(i)));
            }
        }catch (JSONException jx){
            System.out.println(jx.getMessage());
        }
        return list;
    }

    public static List<Card> CardToList(PATH path){
        JSONArray jarr;
        List<Card> list=new ArrayList<>();
        try {
            jarr=JSONManager.readJSONArray(path);
            for (int i = 0; i < jarr.length(); i++) {
                list.add(new Card(jarr.getJSONObject(i)));
            }
        }catch (JSONException jx){
            System.out.println(jx.getMessage());
        }
        return list;
    }

    public static List<Transaction> TransactionToList(PATH path){
        JSONArray jarr;
        List<Transaction> list=new ArrayList<>();
        try {
            jarr=JSONManager.readJSONArray(path);
            for (int i = 0; i < jarr.length(); i++) {
                list.add(new Transaction(jarr.getJSONObject(i)));
            }
        }catch (JSONException jx){
            System.out.println(jx.getMessage());
        }
        return list;
    }


    /// funciones de ordenado

    public static List<User> orderByUserId(List<User> list){
        list.sort(Comparator.comparingInt(User::getId));
        return list;
    }

    public static List<User> orderBySurname(List<User> list){
        list.sort(Comparator.comparing(User::getSurname));
        return list;
    }

    public static List<User> orderByDocumentNumber(List<User> list){
        list.sort(Comparator.comparing(User::getDocumentNumber));
        return list;
    }

    public static List<Card> orderByCardId(List<Card> list){
        list.sort(Comparator.comparing(Card::getId));
        return list;
    }

    public static List<Card> orderByBalance(List<Card> list){
        list.sort(Comparator.comparingDouble(Card::getBalance));
        return list;
    }

    public static List<Transaction> orderByTransactionId(List<Transaction> list){
        list.sort(Comparator.comparing(Transaction::getId));
        return list;
    }

    public static List<Transaction> orderByAmount(List<Transaction> list){
        list.sort(Comparator.comparing(Transaction::getAmount));
        return list;
    }

    public static List<Transaction> orderByDate(List<Transaction> list){
        list.sort(Comparator.comparing(Transaction::getDateTime));
        return list;
    }




}

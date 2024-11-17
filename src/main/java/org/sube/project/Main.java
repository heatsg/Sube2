package org.sube.project;


import org.sube.project.front.Sube;

public class Main {
    public static void main(String[] args) {
        Sube.getInstance().showUI(true);

        /*JSONArray jsonArray = JSONManager.readJSONArray(Path.USER);
        User user = new User("test", "asd", 40, "99999999", "Masculino", UserType.ADMIN, true, "password");

        jsonArray.put(user.toJSON());
        JSONManager.write(Path.USER, jsonArray);*/

        /*JSONArray jsonArray = JSONManager.readJSONArray(Path.CARD);
        Card card = new Card();

        jsonArray.put(card.toJSON());
        JSONManager.write(Path.CARD, jsonArray);*/
    }
}
package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {

    private static final Model instance = new Model();
    private final Map<Integer,User> model;

    public static Model getInstance() {

        return instance;

    }

    private Model() {

        model = new HashMap<>();
        model.put(1, new User("Иван", "Иванов", 1000));
        model.put(2, new User("Петр", "Петров", 2000));
        model.put(3, new User("Михаил", "Сидоров", 3000));
        model.put(4, new User("Владимир", "Маяковский", 4000));

    }

    public void add(User user, int id) {

        model.put(id, user);

    }

    public Map<Integer, User> getFromList() {

        return model;

    }

    public void delete(int id) {

        model.remove(id);

    }

    public void update(User user, int id) {

        model.replace(id, user);

    }

}

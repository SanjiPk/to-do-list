package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import db.exception.*;

public class Database {

    public static ArrayList<Entity> entities = new ArrayList<>();
    private static int index = 1;
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {
    }

    public static void add(Entity e) {
        Validator validator = validators.get(e.getEntityCode());

        try {
            validator.validate(e);
            e.id = index;
            entities.add(e.copy());
            index++;
        } catch (InvalidEntityException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static Entity get(int id) {
        for (Entity e : entities)
            if (e.id == id)
                return e.copy();
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) {
        for (Entity e : entities)
            if (e.id == id) {
                entities.remove(e);
                return;
            }
        throw new EntityNotFoundException();
    }

    public static void update(Entity e) {
        Validator validator = validators.get(e.getEntityCode());

        try {
            validator.validate(e);
            for (int i = 0; i < entities.size(); i++)
                if (entities.get(i).id == e.id) {
                    entities.set(i, e.copy());
                    return;
                }
            throw new EntityNotFoundException();
        } catch (InvalidEntityException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode) || validators.containsValue(validator))
            throw new IllegalArgumentException("The code and validator must be uniq.");

        validators.put(entityCode, validator);
    }
}
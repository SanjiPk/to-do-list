package db;

import java.util.ArrayList;
import db.exception.*;

public class Database {

    public static ArrayList<Entity> entities = new ArrayList<>();
    private Database(){}

    public static void add(Entity e) {
        entities.add(e);
        e.id = entities.size();
    }

    public static Entity get(int id) {
        for (Entity e : entities)
            if (e.id == id)
                return e;
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
        for (int i = 0; i < entities.size(); i++)
            if (entities.get(i).id == e.id) {
                entities.set(i, e);
                return;
            }
        throw new EntityNotFoundException();
    }
}
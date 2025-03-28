package db;

import java.util.ArrayList;
import db.exception.*;

public class Database {

    public static ArrayList<Entity> entities = new ArrayList<>();
    private static int index = 1;

    private Database(){}

    public static void add(Entity e) {
        e.id = index;
        entities.add(e.copy());
        index++;
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
        for (int i = 0; i < entities.size(); i++)
            if (entities.get(i).id == e.id) {
                entities.set(i, e.copy());
                return;
            }
        throw new EntityNotFoundException();
    }
}
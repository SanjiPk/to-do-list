package example;

import java.util.Objects;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Human))
            throw new IllegalArgumentException("Type of entity is not human !!!");

        Human human = (Human) entity;

        if (Objects.isNull(human) || human.name.trim().isEmpty())
            throw new InvalidEntityException("Name can not be null or empty.");
        if (human.age <= 0)
            throw new InvalidEntityException("Age must be bigger than 0.");
    }
}
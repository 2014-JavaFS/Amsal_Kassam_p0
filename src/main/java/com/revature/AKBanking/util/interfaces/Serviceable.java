package com.revature.AKBanking.util.interfaces;

import com.revature.AKBanking.util.exceptions.InvalidInputException;

public interface Serviceable<Model> {
    Model[] findAllWithID(String id);
    Model create(Model newObject) throws InvalidInputException;
}

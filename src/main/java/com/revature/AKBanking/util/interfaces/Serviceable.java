package com.revature.AKBanking.util.interfaces;

import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;

public interface Serviceable<Model> {
    Model findById(String id) throws DataNotFoundException;
    Model create(Model newObject) throws InvalidInputException;
}

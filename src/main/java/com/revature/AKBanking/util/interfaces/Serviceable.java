package com.revature.AKBanking.util.interfaces;

import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;

import java.util.List;

public interface Serviceable<Model> {
    Model findById(String id) throws DataNotFoundException;
    List<Model> findAll();
    Model create(Model newObject) throws InvalidInputException;
}

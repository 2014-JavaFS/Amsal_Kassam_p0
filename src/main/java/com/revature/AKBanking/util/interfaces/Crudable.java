package com.revature.AKBanking.util.interfaces;

public interface Crudable<Model> extends Serviceable<Model>{
    boolean update(Model updatedModel);
    boolean deleteAll();
    boolean delete(Model modelToDelete);
}

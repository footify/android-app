package eu.epitech.croucour.footify;


import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.UserEntity;

/**
 * Created by cleme_000 on 25/02/2016.
 */
public class MyService {
    protected Manager _manager;

    public MyService(Manager manager, UserEntity userEntity) {
        this._manager = manager;
    }

    public MyService(Manager manager) {
        this._manager = manager;
    }
}

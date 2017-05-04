package eu.epitech.croucour.footify.BabyFoot;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;

/**
 * Created by croucour on 29/04/17.
 */

public class BabyFootPresenter {

    private final IBabyFootView _view;
    private final Manager _manager;
    private final BabyFootService _babyFootService;

    BabyFootPresenter(IBabyFootView view, Manager manager, TokenEntity tokenEntity) {
        _view = view;
        _manager = manager;
        _babyFootService = new BabyFootService(view, manager, tokenEntity);
    }

    public void getHistoric(String babyFootEntity_id) {
        _babyFootService.getHistoric(babyFootEntity_id);
    }

    public void getRanking(String babyFootEntity_id) {
        _babyFootService.getRanking(babyFootEntity_id);
    }

    public void getBabyFoot(String babyFoot_id) {
        _babyFootService.getBabyFoot(babyFoot_id);
    }

    public void getPub(String bar_id) {
        _babyFootService.getPub(bar_id);
    }
}

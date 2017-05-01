package eu.epitech.croucour.footify.Home;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.ServiceGeneratorApi;

/**
 * Created by croucour on 29/04/17.
 */

public class HomeService {

    private final IHomeView _view;
    private final IHomeService _api;

    interface IHomeService {

    }

    HomeService(IHomeView view, Manager manager, TokenEntity tokenEntity) {
        this._view = view;

        this._api = ServiceGeneratorApi.createService(IHomeService.class, "api", tokenEntity, manager);
    }
}

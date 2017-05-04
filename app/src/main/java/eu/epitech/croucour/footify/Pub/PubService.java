package eu.epitech.croucour.footify.Pub;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Home.IHomeView;
import eu.epitech.croucour.footify.ServiceGeneratorApi;

/**
 * Created by croucour on 29/04/17.
 */

public class PubService {

    private final IPubView _view;
    private final IPubService _api;

    interface IPubService {

    }

    PubService(IPubView view, Manager manager, TokenEntity tokenEntity) {
        this._view = view;

        this._api = ServiceGeneratorApi.createService(IPubService.class, "api", tokenEntity, manager);
    }
}

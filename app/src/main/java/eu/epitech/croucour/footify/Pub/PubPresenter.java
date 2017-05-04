package eu.epitech.croucour.footify.Pub;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Home.IHomeView;

/**
 * Created by croucour on 29/04/17.
 */

public class PubPresenter {

    private final IPubView _view;
    private final Manager _manager;
    private final PubService _pubService;

    PubPresenter(IPubView view, Manager manager, TokenEntity tokenEntity) {
        _view = view;
        _manager = manager;
        _pubService = new PubService(view, manager, tokenEntity);
    }
}

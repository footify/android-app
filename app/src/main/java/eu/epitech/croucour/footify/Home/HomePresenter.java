package eu.epitech.croucour.footify.Home;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;

/**
 * Created by croucour on 29/04/17.
 */

public class HomePresenter {

    private final IHomeView _view;
    private final Manager _manager;
    private final HomeService _homeService;

    HomePresenter(IHomeView view, Manager manager, TokenEntity tokenEntity) {
        _view = view;
        _manager = manager;
        _homeService = new HomeService(view, manager, tokenEntity);
    }
}

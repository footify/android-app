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

    public void getBabyFoot(String babyFootId) {
        _homeService.getBabyFoot(babyFootId);
    }

    public void getPub(String bar_id) {
        _homeService.getPubs(bar_id);
    }

    public void getProfile() {
        _homeService.getProfile();
    }

    public void getUserAndShow(String user_id) {
        _homeService.getUserAndShow(user_id);
    }

    public void getFriendHistoric() {
        _homeService.getFriendHistoric();
    }

    public void getPubAndShow(String pub_id) {
        _homeService.getPubAndShow(pub_id);
    }
}

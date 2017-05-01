package eu.epitech.croucour.footify.DecoderQrCode;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Home.IHomeView;

/**
 * Created by croucour on 29/04/17.
 */

public class DecoderQrCodePresenter {

    private final IDecoderQrCodeView _view;
    private final Manager _manager;
    private final DecoderQrCodeService _homeService;

    DecoderQrCodePresenter(IDecoderQrCodeView view, Manager manager, TokenEntity tokenEntity) {
        _view = view;
        _manager = manager;
        _homeService = new DecoderQrCodeService(view, manager, tokenEntity);
    }
}

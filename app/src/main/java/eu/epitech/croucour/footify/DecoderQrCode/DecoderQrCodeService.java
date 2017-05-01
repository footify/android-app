package eu.epitech.croucour.footify.DecoderQrCode;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Home.IHomeView;
import eu.epitech.croucour.footify.ServiceGeneratorApi;

/**
 * Created by croucour on 29/04/17.
 */

public class DecoderQrCodeService {

    private final IDecoderQrCodeView _view;
    private final IDecoderQrCodeService _api;

    interface IDecoderQrCodeService {

    }

    DecoderQrCodeService(IDecoderQrCodeView view, Manager manager, TokenEntity tokenEntity) {
        this._view = view;

        this._api = ServiceGeneratorApi.createService(IDecoderQrCodeService.class, "api", tokenEntity, manager);
    }
}

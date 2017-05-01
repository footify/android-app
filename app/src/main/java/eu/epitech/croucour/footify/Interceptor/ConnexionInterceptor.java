package eu.epitech.croucour.footify.Interceptor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cleme_000 on 03/03/2016.
 */
public class ConnexionInterceptor implements Interceptor {
    private final Manager _manager;
    private final TokenEntity _tokenEntity;

    public ConnexionInterceptor(TokenEntity tokenEntity, Manager manager) {
        _manager = manager;
        _tokenEntity = tokenEntity;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        Request build = requestBuilder.build();

        return chain.proceed(build);
    }

    public static boolean haveInternetConnection(Context pContext){
        NetworkInfo network = ((ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return !(network == null || !network.isConnected());
    }

}

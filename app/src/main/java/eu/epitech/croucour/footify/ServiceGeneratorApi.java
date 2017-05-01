package eu.epitech.croucour.footify;


import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Interceptor.AuthInterceptor;
import eu.epitech.croucour.footify.Interceptor.ConnexionInterceptor;
import eu.epitech.croucour.footify.Interceptor.TokenAuthenticator;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cleme_000 on 29/02/2016.
 */
public class ServiceGeneratorApi {

    /**
     * DEDIEE
     */
    public static final String API_BASE_URL = "spred.tv";
//    public final static String CLIENT = "dfbDgOnQjjqRgISU";
//    public final static String SECRET = "eXmWuVBcoo6llNDUcvFf6pXaoJrjS6cu";

    public final static String CLIENT = "iZzvsqXU2tO30A93";
    public final static String SECRET = "YXZyjptxwhsiHdGKapbX6NpeoGz8YWDW";
    public final static String PORT = ":80";


    public static  <S> S createService(Class<S> serviceClass, String subDomain, Manager manager) {
        return createService(serviceClass, subDomain, null, manager);
    }

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static <S> S createService(Class<S> serviceClass, String subDomain, TokenEntity tokenEntity, final Manager manager) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.authenticator(new TokenAuthenticator(tokenEntity, manager));
        client.addInterceptor(new ConnexionInterceptor(tokenEntity, manager));
        client.addNetworkInterceptor(new AuthInterceptor(tokenEntity, manager));
        client.addInterceptor(logging);

        String url = subDomain == null ? API_BASE_URL+"/v1/" : subDomain + "." + API_BASE_URL+PORT+"/v1/";
        url = "http://"+url;

        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client.build()).build();

        return retrofit.create(serviceClass);
    }
}

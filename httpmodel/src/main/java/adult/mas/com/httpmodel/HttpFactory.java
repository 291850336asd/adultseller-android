package adult.mas.com.httpmodel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sunmeng on 17/8/1.
 */

public class HttpFactory {

    private HttpFactory(){
    }


    public static <T> T getAPIService(Class<T> apiServiceClass, String baseURL) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(genericClient())
                .build();
        return retrofit.create(apiServiceClass);
    }

    private static OkHttpClient genericClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(HttpModelConstant.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(HttpModelConstant.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(HttpModelConstant.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }
}

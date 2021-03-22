package com.crays.gmagro;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ASyncWS extends AsyncTask<String, Integer, String> {
    private static String urlBase = "http://sio.jbdelasalle.com/~crayssiguier/ppe3/ws.php?";
    private static OkHttpClient httpClient;
    private static CookieJar cookieJar;
    public ASyncWS() {
        if (httpClient == null) {
            CookieJar cookieJar = new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }
                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            };
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cookieJar(cookieJar);
            httpClient = builder.build();
        }
    }


    @Override
    protected String doInBackground(String[] params) {
        String urlFinal;
        urlFinal = urlBase + params[0];
        Log.d("ASYNCWS:GET", urlFinal);
        String reponse = "";

        Request request = new Request.Builder()
                .url(urlFinal)
                .build();
        Response httpResponse = null;
        try {
            httpResponse = httpClient.newCall(request).execute();
            reponse = httpResponse.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("ASYNCWS:RESPONSE", reponse);
        return reponse;
    }
}
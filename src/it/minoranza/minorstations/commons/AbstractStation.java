package it.minoranza.minorstations.commons;

import it.minoranza.commons.Station;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractStation {

    protected final Station station;
    protected final String API_KEY;
    //protected final String urlMain,urlLocation;

    protected AbstractStation(final Station station, final String API_KEY){
        this.API_KEY=API_KEY;
        this.station=station;

    }

    public final Station getStation() throws MalformedURLException{
        return station;
    }

    public String request(final String param,final String urlMain,final String urlTail) throws IOException {

        final StringBuilder build=new StringBuilder();
        build.append(urlMain);
        build.append(API_KEY);
        build.append(urlTail);
        build.append(param);

        URL url=new URL(build.toString().trim().replaceAll(" +", " ").replaceAll(" ","%20"));

        System.out.println(build.toString());

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        if(connection.getResponseCode()!=200) //città non trovata
            throw new IOException();

        StringWriter reader= new StringWriter();
        InputStream in=connection.getInputStream();

        IOUtils.copy(in,reader);

        return reader.toString();
    }

    public abstract JSONArray stardardizePlaces(final JSONArray array);

    public abstract JSONArray getSugg(final String query) throws IOException;

    public abstract JSONObject getWeather(final String id) throws IOException;

}

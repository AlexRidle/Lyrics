package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Lyrics {

    public String getLyrics(String artist, String title) throws IOException {
        String lyrics;
        artist = artist.toLowerCase();
        title = title.toLowerCase();
        artist = artist.replaceAll("[^A-Za-z0-9]+", "");
        title = title.replaceAll("[^A-Za-z0-9]+", "");
        if (artist.startsWith("the")) {
            artist = artist.substring(0, 2);
        }
        lyrics = getURLSource("http://azlyrics.com/lyrics/"+artist+"/"+title+".html");
        lyrics = lyrics.substring(lyrics.indexOf(
                "<!-- Usage of azlyrics.com content by any third-party lyrics provider is" +
                        " prohibited by our licensing agreement. Sorry about that. -->") + 133);
        lyrics = lyrics.substring(0, lyrics.indexOf("<!-- MxM banner -->"));
        lyrics = lyrics.replaceAll("<br>","\n")
                .replaceAll("</br>","")
                .replaceAll("</div>","")
                .replaceAll("<i>","")
                .replaceAll("</i>","")
                .replaceAll("&quot;","");
        return lyrics;
    }

    private String getURLSource(String url) throws IOException {
        URL urlObject = new URL(url);
        URLConnection connection = urlObject.openConnection();
        String redirect = connection.getHeaderField("Location");
        if (redirect != null){
            connection = new URL(redirect).openConnection();
        }
        return toString(connection.getInputStream());
    }

    private String toString(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            return stringBuilder.toString();
        }
    }
}

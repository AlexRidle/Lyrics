package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Lyrics lyrics = new Lyrics();
            System.out.println(lyrics.getLyrics("Dragonforce","through the fire and the flames"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

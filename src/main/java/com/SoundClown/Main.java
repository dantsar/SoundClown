package com.SoundClown;

import User.User;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        User Andy = new User();
        Andy.set_id("120Azx");
        Andy.set_username("Andy");
        Andy.set_password("123");

        System.out.println(Andy.get_id());
        System.out.println(Andy.get_username());
        System.out.println(Andy.get_password());
    }
}


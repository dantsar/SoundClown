package SoundClown;

import SoundClown.webapp.Server;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to SoundClown, the circus of sound!!!!!!!!!!!!!!!");

        Server server = new Server(5000);
    }
}

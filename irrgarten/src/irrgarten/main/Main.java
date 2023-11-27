/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.main;

import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;
import irrgarten.Game;

public class Main {

    public static void main(String[] args) {

        TextUI vista = new TextUI();

        Game game = new Game(1);
        Controller controller = new Controller(game, vista);
        controller.play();

    }
}

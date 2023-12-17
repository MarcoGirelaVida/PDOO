/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten.main;

import irrgarten.UI.*;
import irrgarten.controller.Controller;
import irrgarten.Game;

public class Main {

    public static void main(String[] args) {

        UI vista = new GuiUI();

        Game game = new Game(1, 1);
        Controller controller = new Controller(game, vista);
        controller.play();

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;
package animales;
import java.util.ArrayList;
/**
 *
 * @author marco
 */
public class Perro {
    public static final int NUMERO_PERROS = 0;
    private String raza;
    private ArrayList<Persona> dueños;
    private ArrayList<Aloja> pernoctas;
    
    public Perro(String raza) {
        this.raza = raza;
    }
    
    protected static void incremetar() {}
    
    public void pasear(){}
    
    private void lamerse(){}
}


public
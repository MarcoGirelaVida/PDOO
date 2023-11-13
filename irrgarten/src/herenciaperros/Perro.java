/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herenciaperros;
package animales;
import java.util.ArrayList;
/**
 *
 * @author marco
 */
public class Perro {
    public int id;
    public String nombre;
    public String raza;
    public int edad;
    public Veterinario vet;
    
    public Perro (String nombre, String raza) {
        this.nombre = nombre;
        this.raza = raza;
        id = 0;
        edad = 1;
    }
    
    public void sit(){
        System.out.println("SIT");
    }
    
    public void ven(){
        System.out.println("Allá voy");
    }
    
    public void avisar() {
        System.out.println("GUAU!!!");
    }
}

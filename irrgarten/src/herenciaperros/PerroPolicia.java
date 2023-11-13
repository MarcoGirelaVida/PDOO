/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herenciaperros;
/**
 *
 * @author marco
 */
public class PerroPolicia extends Perro{
    
    // Observa que puedes añadir nuevos atributos y sobrecargar el contructor
    // para hacer un mix entre el constructor original y el de la clase hiva
    public String especializacion;
    
    public PerroPolicia(String nombre, String raza, String esp) {
        super(nombre, raza);
        especializacion = esp;
    }
    
}

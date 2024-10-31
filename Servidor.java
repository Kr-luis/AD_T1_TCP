import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private static final int PUERTO = 3000;
    private List<Pregunta> preguntas;

    public Servidor() {
        preguntas = new ArrayList<>();
        
        preguntas.add(new Pregunta("¿Cuál es la capital de Francia?", "París"));
        preguntas.add(new Pregunta("¿Cuántos planetas hay en el sistema solar?", "8"));
        preguntas.add(new Pregunta("¿Cuál es el idioma oficial de Brasil?", "Portugués"));
        preguntas.add(new Pregunta("¿Quién escribió 'Cien años de soledad'?", "Gabriel García Márquez"));
        preguntas.add(new Pregunta("¿Cuál es el metal más ligero?", "Litio"));
        preguntas.add(new Pregunta("¿Cuál es el océano más grande?", "Pacífico"));
        preguntas.add(new Pregunta("¿Cuál es el país más poblado?", "China"));
        preguntas.add(new Pregunta("¿Quién pintó la Mona Lisa?", "Leonardo da Vinci"));
        preguntas.add(new Pregunta("¿Cuál es el río más largo del mundo?", "Amazonas"));
        preguntas.add(new Pregunta("¿Qué gas es necesario para la respiración humana?", "Oxígeno"));
        preguntas.add(new Pregunta("¿Cuál es el continente más grande?", "Asia"));
        preguntas.add(new Pregunta("¿Quién es el autor de 'Don Quijote de la Mancha'?", "Miguel de Cervantes"));
        preguntas.add(new Pregunta("¿Cuál es el planeta más cercano al sol?", "Mercurio"));
        preguntas.add(new Pregunta("¿Cuál es el país más grande del mundo?", "Rusia"));
        preguntas.add(new Pregunta("¿Cuál es el animal terrestre más rápido?", "Guepardo"));
        preguntas.add(new Pregunta("¿Qué vitamina es producida por el cuerpo cuando está expuesto al sol?", "Vitamina D"));
        preguntas.add(new Pregunta("¿Cuál es el metal más abundante en la corteza terrestre?", "Aluminio"));
        preguntas.add(new Pregunta("¿Cuál es el órgano más grande del cuerpo humano?", "Piel"));
        preguntas.add(new Pregunta("¿Cuál es el país donde se originó el tango?", "Argentina"));
        preguntas.add(new Pregunta("¿Quién desarrolló la teoría de la relatividad?", "Albert Einstein"));
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);
            while (true) {
                Socket cliente = serverSocket.accept();
                new HiloCliente(cliente, preguntas).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }
}
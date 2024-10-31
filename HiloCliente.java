import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.Normalizer;
import java.util.Collections;
import java.util.List;

class HiloCliente extends Thread {
    private Socket socket;
    private List<Pregunta> preguntas;

    public HiloCliente(Socket socket, List<Pregunta> preguntas) {
        this.socket = socket;
        this.preguntas = preguntas;
    }

    private String normalizarTexto(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            boolean repetirTest = true;

            while (repetirTest) {
                Collections.shuffle(preguntas);
                List<Pregunta> preguntasSeleccionadas = preguntas.subList(0, 5);
                int puntaje = 0;

                for (Pregunta pregunta : preguntasSeleccionadas) {
                    out.println("Pregunta: " + pregunta.getPregunta());
                    String respuesta = in.readLine();

                    if (respuesta != null && pregunta.esCorrecta(normalizarTexto(respuesta))) {
                        puntaje++;
                        out.println("Correcto");
                    } else {
                        out.println("Incorrecto");
                    }
                }

                out.println("Puntaje final: " + puntaje + "/5");
                out.println("¿Quieres hacer el test nuevamente? (si/no)");
                String respuesta = in.readLine();

                if (respuesta != null && respuesta.equalsIgnoreCase("si")) {
                    repetirTest = true;
                } else {
                    repetirTest = false;
                    out.println("Gracias por participar. Conexión terminada.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



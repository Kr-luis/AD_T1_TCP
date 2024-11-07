import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

class HiloCliente extends Thread {
    private Socket socket;
    private List<Pregunta> preguntas;
    private static final int PUNTOS_POR_PREGUNTA = 4;

    public HiloCliente(Socket socket, List<Pregunta> preguntas) {
        this.socket = socket;
        this.preguntas = preguntas;
    }

    private String normalizarTexto(String texto) {
        return java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
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

                    String resultado;
                    if (respuesta != null && pregunta.esCorrecta(normalizarTexto(respuesta))) {
                        puntaje += PUNTOS_POR_PREGUNTA;
                        resultado = "Correcto";
                        out.println(resultado);
                    } else {
                        resultado = "Incorrecto";
                        out.println(resultado);
                    }

                    RegistroServidor.registrarRespuesta(socket.getInetAddress().getHostAddress(), 
                                                        resultado + ": " + respuesta);
                }

                out.println("Puntaje final: " + puntaje + "/" + (5 * PUNTOS_POR_PREGUNTA));
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

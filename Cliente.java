import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final String HOST = "localhost";
    private static final int PUERTO = 3000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                System.out.println(mensaje);

                if (mensaje.startsWith("Pregunta:")) {
                    System.out.print("Respuesta: ");
                    String respuesta = scanner.nextLine();
                    out.println(respuesta);
                } else if (mensaje.startsWith("¿Quieres hacer el test nuevamente?")) {
                    System.out.print("Respuesta (si/no): ");
                    String respuesta = scanner.nextLine();
                    out.println(respuesta);

                    if (respuesta.equalsIgnoreCase("no")) {
                        System.out.println("Gracias por participar. Conexión terminada.");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

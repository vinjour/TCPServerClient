import java.net.*;
import java.io.*;


public class TCPClient {

    public static void main(String[] args) {
        if (args.length < 2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();

            String text;

            do {

                text = ("Enter message : ");
                System.out.print(text);

                //On récupère le message

                InputStream input = socket.getInputStream();

                String time = reader.readLine();

                System.out.println(time);

            } while (!reader.equals("bye"));

            socket.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
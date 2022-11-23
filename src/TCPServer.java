import java.io.*;
import java.net.*;


public class TCPServer {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java TCPServer <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            Socket socket = serverSocket.accept();
            System.out.println("New client connected\n");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InetAddress IPclient = socket.getInetAddress();
            int clientPort = socket.getPort();

            while (true) {
                String text = reader.readLine();

                System.out.println("From client at: " + IPclient + ":" + clientPort);
                System.out.println(text+"\n");
                writer.println(text);
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

import java.io.*;
import java.net.*;


public class ConnectionThread extends Thread {
    private Socket socket = null;
    public InputStream input = null;

    public ConnectionThread(Socket socket, InputStream input) {
        super("TCPMultiServer");
        this.socket = socket;
        this.input = input;
    }

    public void run() {

        try () {

            boolean connected = true;

            System.out.println("New client connected\n");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InetAddress IPclient = socket.getInetAddress();
            int clientPort = socket.getPort();

            while (connected) {

                String text = reader.readLine();

                System.out.println("From client at: " + IPclient + ":" + clientPort);
                System.out.println(text+"\n");
                writer.println(text);

                if (!reader.equals("bye")) {
                    break;
                }
            }
            connected = false;
            socket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void launch() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            boolean connected = true;

            socket = serverSocket.accept();
            System.out.println("New client connected\n");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InetAddress IPclient = socket.getInetAddress();
            int clientPort = socket.getPort();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

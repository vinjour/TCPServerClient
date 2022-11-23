import java.io.*;
import java.net.*;


public class ConnectionThread extend Thread {
    private Socket socket = null;

    public ConnectionThread(Socket socket) {
        super("TCPServer");
        this.socket = socket;
    }

    System.out.println("Server is listening on port " + port);

    Socket socket = serverSocket.accept();
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
    }
}

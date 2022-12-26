import java.io.*;
import java.net.*;


public class TCPMultiServer {

    ServerSocket serverSocket;
    static Socket clientSocket;
    static ConnectionThread client;

    static int serverPort;

    public TCPMultiServer(int serverPort, ConnectionThread clientThread) {
        try {
            serverSocket = new ServerSocket(serverPort);

        } catch (SocketException ex) {
            System.err.println(ex);
            System.exit(1);

        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }

        System.out.println("Server is listening on port " + serverPort);
        client = clientThread;
    }

    public void launch() throws IOException {
        boolean listening = true;

        while (listening) {

            clientSocket = serverSocket.accept();
            System.out.println("New client connected : " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + "\n");
            client.clientHandler();
        }
        serverSocket.close();
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.err.println("Usage: java TCPServer <port>");
            System.exit(1);
        }

        serverPort = Integer.parseInt(args[0]);
        ConnectionThread client = new ConnectionThread(clientSocket);

        TCPMultiServer tcpMultiServer = new TCPMultiServer(serverPort, client);
        tcpMultiServer.launch();
    }
}

import java.io.*;
import java.net.*;


public class TCPServer {

    ServerSocket serverSocket;
    Socket socket;

    public TCPServer(int serverPort) {
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }

        System.out.println("Server is listening on port " + serverPort);
    }

    public void launch() throws IOException {
        boolean keepListening = true;

        while (keepListening) {

            socket = serverSocket.accept();
            System.out.println("New client connected\n");
            String sentence = getBufferedReader(socket);
            getPrintWriter(socket, sentence);
        }
        serverSocket.close();
    }


    private static void getPrintWriter(Socket socket, String text2) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(text2);
    }

    private String getBufferedReader(Socket socket) throws IOException {


        InputStream input = socket.getInputStream();
        InetAddress IPclient = socket.getInetAddress();
        int clientPort = socket.getPort();

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String sentence = reader.readLine();

        System.out.println("From client at: " + IPclient + ":" + clientPort);
        System.out.println(sentence+"\n");
        return sentence;
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.err.println("Usage: java TCPServer <port>");
            System.exit(1);
        }

        int serverPort = Integer.parseInt(args[0]);
        TCPServer tcpServer = new TCPServer(serverPort);
        tcpServer.launch();
    }
}

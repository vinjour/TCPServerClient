import java.io.*;
import java.net.*;


public class TCPServer {

    ServerSocket serverSocket;
    Socket clientSocket;
    InputStream input;
    OutputStream output;
    BufferedReader reader;
    PrintWriter writer;

    InetAddress clientAddress;
    int clientPort;

    public TCPServer(int serverPort) {
        try {
            serverSocket = new ServerSocket(serverPort);

        } catch (SocketException ex) {
            System.err.println(ex);
            System.exit(1);

        }catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
        
        System.out.println("Server is listening on port " + serverPort);
    }

    public void launch() throws IOException {
        boolean listening = true;

        while (listening) {

            clientSocket = serverSocket.accept();
            System.out.println("New client connected\n");
            String sentence = getBufferedReader(clientSocket);
            getPrintWriter(clientSocket, sentence);
        }
        serverSocket.close();
    }


    private void getPrintWriter(Socket socket, String text2) throws IOException {
        output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        writer.println(text2);
    }

    private String getBufferedReader(Socket socket) throws IOException {


        input = socket.getInputStream();
        clientAddress = socket.getInetAddress();
        clientPort = socket.getPort();

        reader = new BufferedReader(new InputStreamReader(input));
        String sentence = reader.readLine();

        System.out.println("From client at: " + clientAddress + ":" + clientPort);
        System.out.println(sentence+"\n");
        return sentence;
    }

    public static void main(String args[]) throws Exception {

        if (args.length != 1) {
            System.err.println("Usage: java TCPServer <port>");
            System.exit(1);
        }

        int serverPort = Integer.parseInt(args[0]);
        TCPServer tcpServer = new TCPServer(serverPort);
        tcpServer.launch();
    }
}

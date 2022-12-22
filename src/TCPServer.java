import java.io.*;
import java.net.*;


public class TCPServer {

    ServerSocket serverSocket;
    Socket clientSocket;
    InputStream receiveData;
    OutputStream sendData;
    BufferedReader readMsgReceived;
    PrintWriter writer;

    static int serverPort;

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
            String sentence = receiveMessage(clientSocket);
            sendMessage(clientSocket, sentence);
        }
        serverSocket.close();
    }

    private String receiveMessage(Socket socket) throws IOException {
        InetAddress clientAddress = socket.getInetAddress();
        int clientPort = socket.getPort();

        receiveData = socket.getInputStream();
        readMsgReceived = new BufferedReader(new InputStreamReader(receiveData));
        String sentence = readMsgReceived.readLine();

        System.out.println("From client at: " + clientAddress + ":" + clientPort);
        System.out.println(sentence+"\n");
        return sentence;
    }

    private void sendMessage(Socket socket, String sentence) throws IOException {
        sendData = socket.getOutputStream();
        writer = new PrintWriter(sendData, true);
        writer.println(sentence);
    }

    public static void main(String args[]) throws Exception {

        if (args.length != 1) {
            System.err.println("Usage: java TCPServer <port>");
            System.exit(1);
        }

        serverPort = Integer.parseInt(args[0]);

        TCPServer tcpServer = new TCPServer(serverPort);
        tcpServer.launch();
    }
}

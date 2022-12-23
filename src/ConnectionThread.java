import java.io.*;
import java.net.*;


public class ConnectionThread extends Thread {

    static Socket clientSocket;
    static ServerSocket serverSocket;
    static InputStream receiveData;
    OutputStream sendData;
    BufferedReader readMsgReceived;
    PrintWriter writer;

    int clientPort;

    public ConnectionThread(Socket clientSocket, InputStream receiveData) {
        super("TCPMultiServer");
        this.clientSocket = clientSocket;
        this.receiveData = receiveData;
    }

    public void run(ServerSocket serverSocket) throws IOException {
        boolean listening = true;
        clientSocket = serverSocket.accept();

        while (listening) {

            String sentence = receiveMessage(clientSocket);
            sendMessage(clientSocket, sentence);
        }
        serverSocket.close();
    }

    private String receiveMessage(Socket socket) throws IOException {
        InetAddress clientAddress = socket.getInetAddress();
        int clientPort = socket.getPort();

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

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.err.println("Usage: java TCPServer <port>");
            System.exit(1);
        }

        ConnectionThread connectionThread = new ConnectionThread(clientSocket, receiveData);
        connectionThread.run(serverSocket);
    }
}

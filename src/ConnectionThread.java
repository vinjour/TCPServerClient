import java.io.*;
import java.net.*;


public class ConnectionThread extends Thread {

    static Socket clientSocket;
    static InputStream receiveData;
    OutputStream sendData;
    BufferedReader readMsgReceived = null;
    PrintWriter writer = null;

    InetAddress clientAddress;
    int clientPort;

    public ConnectionThread(Socket clientSocket) {
        super("TCPMultiServer");
        this.clientSocket = clientSocket;
    }

    public void clientHandler() throws IOException {
        boolean listening = true;

        while (receiveData != null) {

            String sentence = receiveMessage();
            sendMessage(sentence);
        }
    }

    private String receiveMessage() throws IOException {

        readMsgReceived = new BufferedReader(new InputStreamReader(receiveData));
        String sentence = readMsgReceived.readLine();
        System.out.println("From : " + clientAddress + ":" + clientPort);
        System.out.println(sentence+"\n");
        return sentence;
    }

    private void sendMessage(String sentence) throws IOException {

        sendData = clientSocket.getOutputStream();
        writer = new PrintWriter(sendData, true);
        writer.println(sentence);
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.err.println("Usage: java TCPServer <port>");
            System.exit(1);
        }

        ConnectionThread connectionThread = new ConnectionThread(clientSocket);
        connectionThread.clientHandler();
    }
}

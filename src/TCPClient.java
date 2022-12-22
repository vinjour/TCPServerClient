import java.net.*;
import java.io.*;


public class TCPClient {

    Socket clientSocket;
    BufferedReader readMsgToSend;
    BufferedReader readMsgReceived;
    OutputStream sendData;
    PrintWriter writer;
    InputStream receiveData;

    static String serverAddress;
    static int serverPort;
    static int TIMER_SIZE = 10000;  // Milliseconds

    public TCPClient(String serverAddress, int serverPort) throws IOException {
        try {
            clientSocket = new Socket(serverAddress, serverPort);

        } catch (SocketException ex) {
            System.err.println(ex);
            System.exit(1);

        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }

    private void run() throws IOException {

        boolean connected = true;

        while (connected) {

            sendMessage();
            clientSocket.setSoTimeout(TIMER_SIZE);
            receiveMessage();
        }
        connected = false;
        clientSocket.close();
    }

    private void sendMessage() throws IOException {
        System.out.print("Enter message : ");

        readMsgToSend = new BufferedReader(new InputStreamReader(System.in));
        String SendMessage = readMsgToSend.readLine();
        sendData = clientSocket.getOutputStream();
        writer = new PrintWriter(sendData, true);
        writer.println(SendMessage);
    }

    private void receiveMessage() throws IOException {
        receiveData = clientSocket.getInputStream();
        readMsgReceived = new BufferedReader(new InputStreamReader(receiveData));
        String ReceivedMessage = readMsgReceived.readLine();

        System.out.println("\nFrom server at: " + serverAddress + ":" + serverPort);
        System.out.println(ReceivedMessage + "\n");
    }

    public static void main(String args[]) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: java TCPClient <IPaddress> <port>");
            System.exit(1);
        }

        serverAddress = args[0];
        serverPort = Integer.parseInt(args[1]);

        TCPClient tcpClient = new TCPClient(serverAddress, serverPort);
        tcpClient.run();
    }
}

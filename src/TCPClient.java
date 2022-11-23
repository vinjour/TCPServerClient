import java.net.*;
import java.io.*;


public class TCPClient {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: java TCPclient <IPaddress> <port>");
            System.exit(1);
        }

        String IPaddress = args[0];
        int port = Integer.parseInt(args[1]);
        boolean connected = true;

        BufferedReader readMsgToSend = new BufferedReader(new InputStreamReader(System.in));

        try (Socket socket = new Socket(IPaddress, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            //On récupère le message
            InputStream input = socket.getInputStream();
            BufferedReader readMsgReceived = new BufferedReader(new InputStreamReader(input));


            while (connected) {
                System.out.print("Enter message : ");

                String SendMessage = readMsgToSend.readLine();

                writer.println(SendMessage);

                String ReceivedMessage = readMsgReceived.readLine();
                System.out.println("\nFrom server at: " + IPaddress + ":" + port);
                System.out.println(ReceivedMessage + "\n");

                if (readMsgToSend.equals("bye")) {
                    break;
                }
            }
            connected = false;
            socket.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}

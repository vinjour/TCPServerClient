import java.net.*;
import java.io.*;


public class TCPClient {

    public static void main(String[] args) {

        String IPaddress = args[0];
        int port = Integer.parseInt(args[1]);
        BufferedReader readMsgToSend = new BufferedReader(new InputStreamReader(System.in));

        try (Socket socket = new Socket(IPaddress, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            //On récupère le message
            InputStream input = socket.getInputStream();
            BufferedReader readMsgReceived = new BufferedReader(new InputStreamReader(input));


            do {

                System.out.print("Enter message : ");

                String SendMessage = readMsgToSend.readLine();

                writer.println(SendMessage);

                String ReceivedMessage = readMsgReceived.readLine();
                System.out.println("From server at: " + IPaddress + ":" + port);
                System.out.println(ReceivedMessage);

            } while (!readMsgReceived.equals("bye"));

            socket.close();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
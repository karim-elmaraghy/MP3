/*
    The first version, which we call the NaiveClient, displays every character it receives immediately. 
    Both clients terminate after receiving and printing the newline character.
*/

import java.io.*;     // For BufferedReader
import java.net.*;    // For Socket

public class NaiveClient {
    // Create client and start it with the server's host name and port number
    public static void main(String[] args) {
        // Check that the correct number of command line arguments are provided
        if (args.length != 2) {
            System.err.println("Should be in the form of: <host> <port>");
            System.exit(1);
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try (
            Socket socket = new Socket(host, port);

            // Create an InputStreamReader to read each character from the socket's input stream
            InputStreamReader input = new InputStreamReader(socket.getInputStream());
        )
        {
            int character;
            // Read characters one by one from the input stream until the end of the stream is reached
            // -1 indicates end of stream
            while ((character = input.read()) != -1) {
                // Print immediately, without buffering
                System.out.print((char) character);

                // Stop reading when newline is received
                if (character == '\n') {
                    break;
                }
            }
        } catch (IOException ex) {
            // Connection failed or an I/O error occurred
            ex.printStackTrace();
        }
    }   
}

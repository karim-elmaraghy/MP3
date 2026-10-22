/*
    The second version, which we call the BufferClient, buffers all incoming characters and only displays everything when it receives a newline character.
    Both clients terminate after receiving and printing the newline character.
*/

import java.io.*;     // For BufferedReader
import java.net.*;    // For Socket

public class BufferClient {
    // Create client and start it with the server's host name and port number
    public static void main(String[] args) {
        // Check that the correct number of command line arguments are provided
        if (args.length != 2) {
            System.err.println("Should be in the form of: <host> <port>");
            System.exit(1);
        }

        // Extract the host and port from the command line arguments
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try (
            // Create a socket to connect to the server
            Socket socket = new Socket(host, port);

            // Buffer the input stream to read text until a newline character
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        )
        {
            // Read a line of text from the server, which will block until a newline character is received
            String line = input.readLine();
            // Print the entire line at once, after receiving the newline character
            if (line != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            // Connection failed or an I/O error occurred
            ex.printStackTrace();
        }
    }   
}

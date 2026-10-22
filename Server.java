
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {

        // Step 1: Check the command-line arguments
        if (args.length != 2) {
            System.out.println("Usage: java Server <port> <maxClients>");
            return;
        }

        int port;
        int maxClients;

        try {
            port = Integer.parseInt(args[0]);
            maxClients = Integer.parseInt(args[1]);

            if (port < 1 || port > 65535 || maxClients <= 0) {
                System.out.println("Invalid port or maximum clients.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid integers.");
            return;
        }

        // Step 2: Get the string and N from the user
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String str = scanner.nextLine();

        System.out.print("Enter a positive integer N: ");
        int n;

        try {
            n = Integer.parseInt(scanner.nextLine());

            if (n <= 0) {
                System.out.println("N must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer N.");
            return;
        }

        // Step 3: Create a thread pool for multiple clients
        ExecutorService pool = Executors.newFixedThreadPool(maxClients);

        // Step 4: Start the TCP server
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server listening on port " + port);

            while (true) {

                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client connected: "
                        + clientSocket.getInetAddress());

                // Step 5: Give the client to a worker thread
                pool.execute(() -> {
                    handleClient(clientSocket, str, n);
                });
            }

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());

        } finally {
            pool.shutdown();
        }
    }

    // Step 6: Handle communication with one client
    private static void handleClient(Socket clientSocket,
                                     String str, int n) {

        try (Socket socket = clientSocket;
             PrintWriter out = new PrintWriter(
                     socket.getOutputStream(), false)) {

            for (int i = 0; i < n; i++) {

                // Last transmission must end with newline
                if (i == n - 1) {
                    out.print(str + "\n");
                } else {
                    out.print(str);
                }

                // Send immediately
                out.flush();

                // Wait one second between transmissions
                if (i < n - 1) {
                    Thread.sleep(1000);
                }
            }

            System.out.println("Finished sending to client: "
                    + socket.getInetAddress());

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

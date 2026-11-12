# Java TCP Streaming Demo

A small networking project comparing immediate character display with line-buffered display over a TCP connection.

## How it works

The server sends a chosen string repeatedly, flushing after each transmission and waiting one second between sends. Only the final transmission ends with a newline. A fixed thread pool handles concurrent client connections.

| File | Role |
| --- | --- |
| `Server.java` | TCP server with configurable port and worker count |
| `NaiveClient.java` | Displays characters as they arrive |
| `BufferClient.java` | Waits for a complete line before displaying it |

Both clients stop after the first newline. This demonstrates how application-level buffering changes when users see streamed data.

## Build and run

Install a JDK and compile from the repository directory:

```sh
javac Server.java NaiveClient.java BufferClient.java
```

Start the server in one terminal:

```sh
java Server 5000 4
```

When prompted, enter `Hello ` as the string and `3` as the repeat count. In separate terminals, run:

```sh
java NaiveClient localhost 5000
java BufferClient localhost 5000
```

Each client receives `Hello Hello Hello ` followed by a newline. The naive client displays each chunk as it arrives; the buffered client displays the completed line after the final transmission. Stop the server with Ctrl+C.

## Scope

An educational socket-programming exercise, originally named **MP3**. It uses plain TCP with no authentication or encryption. The worker count controls concurrent processing; this is not a production server or a throughput benchmark.

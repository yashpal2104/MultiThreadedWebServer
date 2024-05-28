package MultiThreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

// HW -->
//  1. Server -->(READ) --> Client  (Time will take)
// 2. In threadpool method make size 100 and use initial is 10  --> Read about JMETER
// 3. ThreadPool vs EventLoop blog
// 4. Executor Service and Completable Future (READ ABOUT IN CS FUNDAMENTAL)

public class Server {

    public Consumer<Socket> getConsumer(){
        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
            toClient.println("Hello from the Server");
            toClient.close();
            clientSocket.close();
            }catch (IOException ex){
            ex.printStackTrace();
            }
        };
    }


    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        try{
            ServerSocket serverSocket =new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening on port "+port);
            while(true){
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

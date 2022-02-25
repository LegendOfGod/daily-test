package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * @author lqb
 * @date 2022/2/24 14:17
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        String readLine = "";
        String inTemp = "";

        String turnLine = "\n";
        final String client = "Client:";
        final String server = "Server:";

        //创建socket
        int port = 4000;
        ServerSocket serverSocket = new ServerSocket(port);
        //accept会阻塞直到打开获得连接
        Socket socket = serverSocket.accept();

        //流
        //系统输入流
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        //socket输入流
        BufferedReader serverSocketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //socket输出流
        PrintWriter serverSocketOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        while (!Objects.equals(readLine, "bye")){

            inTemp = serverSocketIn.readLine();
            System.out.println(client+turnLine+inTemp);
            System.out.println(server);

            readLine = systemIn.readLine();
            serverSocketOut.println(readLine);
            serverSocketOut.flush();
        }

        systemIn.close();
        serverSocketIn.close();
        serverSocketOut.close();
        socket.close();
        serverSocket.close();
    }
}

package socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @author lqb
 * @date 2022/2/24 14:57
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        String readline = "";
        String inTemp = "";

        String turnLine = "\n";
        final String client = "Client:";
        final String server = "Server:";

        int port = 4000;
        byte[] ipAddressTemp = {127, 0, 0, 1};
        InetAddress ipAddress = InetAddress.getByAddress(ipAddressTemp);

        //首先直接创建socket,端口号1~1023为系统保存，一般设在1023之外
        Socket socket = new Socket(ipAddress, port);

        //系统输入流
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        //socket输入
        BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //socket输出
        PrintWriter socketOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

        while(!Objects.equals(readline, "bye")){

            readline = systemIn.readLine();
            socketOut.println(readline);

            inTemp = socketIn.readLine();
            System.out.println(client+turnLine+inTemp);

        }
        systemIn.close();
        socketIn.close();
        socketOut.close();
        socket.close();
    }
}

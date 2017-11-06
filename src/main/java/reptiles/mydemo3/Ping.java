package reptiles.mydemo3;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Ping {
    public static boolean ping(String host,int port) throws Exception {
    	 Socket socket = new Socket();
    	socket.connect(new InetSocketAddress(host, port));
		return false;
    }
    
    public static void main(String[] args) throws Exception {
        String ipAddress = "60.163.89.245";
        System.out.println(ping(ipAddress,808));
    }
}

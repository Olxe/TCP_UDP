import java.io.IOException;
import java.net.*;

public class ClientUDP {
    public final static int port = 2345;

    public static void main(String[] args) {
        System.out.println("Client UDP");

        String str = "NO";
        byte[] buffer = str.getBytes();

        try {
            DatagramSocket client = new DatagramSocket();

            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

            packet.setData(buffer);

            client.send(packet);

            byte[] buffer2 = new byte[8196];
            DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, address, port);
            client.receive(packet2);
            System.out.println("Received : " + new String(packet2.getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

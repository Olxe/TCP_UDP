import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerUDP {
    public final static int port = 2345;

    public static void main(String[] args) {
        System.out.println("Server UDP");

        try {
            DatagramSocket server = new DatagramSocket(port);

            while(true) {
                byte[] buffer = new byte[8192];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                server.receive(packet);

                String str = new String(packet.getData());
                System.out.println("Received from " + packet.getAddress() + " on " + packet.getPort() + " : " + str);

                packet.setLength(buffer.length);

                byte[] buffer2 = new SimpleDateFormat("HH:mm:ss").format(new Date()).getBytes();
                DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, packet.getAddress(), packet.getPort());

                server.send(packet2);
                packet2.setLength(buffer2.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

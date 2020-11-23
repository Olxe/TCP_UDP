import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientUDP {
    public final static int port = 2345;

    public static void main(String[] args) {
        System.out.println("Client UDP");

        byte[] buffer = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()).getBytes();

        try {
            DatagramSocket client = new DatagramSocket();

            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

            packet.setData(buffer);

            client.send(packet);

            byte[] buffer2 = new byte[8196];
            DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, address, port);
            client.receive(packet2);

            String str = new String(packet2.getData()).replaceAll("\0", "");
            System.out.println(str);
            JSONObject obj = getJSONObject(str);
            if(obj != null) {
                try {
                    Date T1client = new SimpleDateFormat("HH:mm:ss.SSS").parse(obj.get("T1client").toString());
                    Date T1server = new SimpleDateFormat("HH:mm:ss.SSS").parse(obj.get("T1server").toString());
                    Date T2server = new SimpleDateFormat("HH:mm:ss.SSS").parse(obj.get("T2server").toString());
                    Date T2Client = new SimpleDateFormat("HH:mm:ss.SSS").parse(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));

                    double delai = T2Client.getTime() - T1client.getTime() - (T2server.getTime() - T1server.getTime());
                    System.out.println(delai);

                    double ecart = (((T1server.getTime() + T2server.getTime()) / 2.0) - ((T1client.getTime() + T2Client.getTime()) / 2.0));
                    System.out.println(ecart);
                }
                catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getJSONObject(String json) {
        JSONParser parser = new JSONParser();

        try {
            System.out.println(json);
            Object obj = parser.parse(json);
            return (JSONObject) obj;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

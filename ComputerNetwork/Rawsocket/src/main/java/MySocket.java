import com.savarese.rocksaw.net.RawSocket;

import java.io.*;
import java.net.InetAddress;

/**
 * Created by muffy on 22/05/2017.
 */
public class MySocket extends RawSocket {
    private int portNumber;

    public MySocket(int portNumber) {
        this.portNumber = portNumber;
    }

    public void send(InetAddress address, Packet packet) throws IOException{
        byte[] packetBytes = convertToBytes(packet);
        super.write(address, packetBytes);
    }

    public Packet receive(InetAddress address) throws IOException, ClassNotFoundException {
        byte[] data = new byte[2000];
        super.read(data);
        return (Packet)convertFromBytes(data);
    }
    private byte[] convertToBytes(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        return bos.toByteArray();
    }

    private Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }

}

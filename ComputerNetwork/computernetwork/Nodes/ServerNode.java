package Nodes;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cody on 03/06/2017.
 */
public class ServerNode extends NetworkNode{
    class ConnectionListener extends Thread {
        private volatile boolean isRunning = true;
        private volatile boolean closed = false;

        public void terminate() {
            isRunning = false;
//            while(!closed);
        }

        @Override
        public void run() {
            super.run();

            while(isRunning && !server.isClosed()) {
                try {
                    Socket incoming = server.accept();
                    connections.put(
                            incoming.getRemoteSocketAddress(),
                            incoming);

                    log("new connection from: " + incoming.getRemoteSocketAddress().toString());

                    if(incomingConnectionCallback != null)
                        incomingConnectionCallback.receiveNewData(
                                incoming.getRemoteSocketAddress(),
                                null
                        );
                } catch (IOException e) {
                    if(! (e instanceof SocketTimeoutException))
                        e.printStackTrace();
                }

                try {
                    sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            isRunning = false;
            closed = true;
        }
    }

    class DataListener extends Thread {
        private volatile boolean isRunning = true;
        private volatile boolean closed = false;

        public void terminate() {
            isRunning = false;
//            while(!closed);
        }

        private byte[] read(Socket incoming) throws IOException {
            InputStream stream = incoming.getInputStream();
            int dataLength = stream.read();
            byte[] data = new byte[dataLength];

            stream.read(data);

            return data;
        }

        @Override
        public void run() {
            super.run();

            while(isRunning) {
                Enumeration<Socket> it = connections.elements();
                while(it.hasMoreElements()) {
                    Socket curConnection = it.nextElement();
                    if (curConnection.isClosed())
                        continue;
                    try {
                        if(curConnection.getInputStream().available() != 0) {
                            Callback callback = callbacks.get(
                                    (SocketAddress)curConnection.getRemoteSocketAddress()
                            );

                            log("received new data from: " +
                            curConnection.getRemoteSocketAddress().toString());

                            if(callback != null)
                                callback.receiveNewData(
                                        (SocketAddress) curConnection.getRemoteSocketAddress(),
                                        read(curConnection));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            closed = true;
            isRunning = false;
        }
    }
    private ConcurrentHashMap<SocketAddress, Callback> callbacks = new ConcurrentHashMap<>();
    private DataListener dataListener;

    private static final int SLEEP_TIME = 500;
    private ServerSocket server;
    private int port;
    private ConnectionListener connectionListener;
    private Callback incomingConnectionCallback = null;

    public ServerNode(int port, String name) throws IOException {
        super(name);

        if(port != 0)
            this.port = port;


        try {
            server = new ServerSocket(port);
            server.setSoTimeout(5);
            log("created socket server " + InetAddress.getLocalHost().toString() + "@" + server.getLocalPort());
        } catch (IOException e) {
            logE(" failed to create server socket: " + e.toString());
            throw(e);
        }

        if(port == 0){
            this.port = server.getLocalPort();
//            System.out.println("********************" + this.port);
        }



        dataListener = new DataListener();
        dataListener.start();

        connectionListener = new ConnectionListener();
        connectionListener.start();
    }

    public int getPort() {
        return port;
    }

    public void close() {
        log("closing");

        dataListener.terminate();
        connectionListener.terminate();

        try {
            server.close();
        } catch (IOException e) {
            logE("can't close server socket");
        }

        super.close();

        log("done closing");
    }

    public void addCallback(SocketAddress address, Callback callback) {
        callbacks.put(address, callback);
    }

    public void removeCallBack(SocketAddress address) {
        callbacks.remove(address);
    }

    public void setIncomingConnectionCallback(Callback incomingConnectionCallback) {
        this.incomingConnectionCallback = incomingConnectionCallback;
    }

    public void removeIncomingConnectionCallback() {
        this.incomingConnectionCallback = null;
    }
}

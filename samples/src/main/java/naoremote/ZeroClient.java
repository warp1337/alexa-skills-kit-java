package naoremote;

import org.zeromq.ZMQ;

public class ZeroClient {

    private ZMQ.Context context;
    private ZMQ.Socket socket;

    ZeroClient() {
        this.context = ZMQ.context(1);
        this.socket = context.socket(ZMQ.REQ);
        this.socket.connect("tcp://54.229.65.240:8081");
    }

    public String ZSend(String input) {
        this.socket.send(input.getBytes(ZMQ.CHARSET), 0);
        byte[] reply = socket.recv(0);
        return new String(reply, ZMQ.CHARSET);
    }

    public void CleanUp() {
        this.socket.close();
        this.context.term();
    }

}
package com.main.sams.server.client;

import com.main.sams.server.server.RequestType;
import com.main.sams.server.server.SocketPackage;

import java.io.*;

import com.google.gson.Gson;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    private static Client instance = null;
    private static final int PORT = 8080;

    private Socket clientSocket = null;
    private BufferedWriter out = null;
    private BufferedReader in = null;
    private System.Logger logger = null;
    private Gson gson = null;

    private Client(String hostname) {
        try {
            logger = System.getLogger("Client");
            gson = new Gson();
            clientSocket = new Socket(hostname, PORT);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Client constructor: Client failed to connect to server");
            System.exit(1);
        }
    }

    public static Client getInstance(String hostname) {
        if (instance == null) {
            instance = new Client(hostname);
        }
        return instance;
    }

    public void cleanUpClient() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Client cleanUpClient: Failed to close client");
        }
    }

    public void sendRequest(String json) {
        try {
            out.write(json + "\n");
            out.flush();
            logger.log(System.Logger.Level.INFO, "Sent request to server");
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Client sendRequest: Failed to send request to server " + e.getMessage());
        }
    }

    public SocketPackage receiveResponse() {
        try {
            String json = in.readLine();
            System.out.println(json);
            SocketPackage socketPackage = gson.fromJson(json, SocketPackage.class);
            System.out.println(socketPackage);
            if (socketPackage.getRequestType() == RequestType.SERVER_REJECTED) {
                logger.log(System.Logger.Level.ERROR, "Client receiveResponse: Server rejected request");
                return null;
            }
            return socketPackage;
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Client receiveResponse: Failed to receive response from server");
            return null;
        }
    }
}

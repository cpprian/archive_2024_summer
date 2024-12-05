package com.main.sams.server.server;

import java.io.Serializable;

/**
 * SocketPackage is a class that is used to send data between the server and the client.
 * It contains a type of request, and can store two objects.
 *
 * @author cpprian
 * @version 1.0
 * @since 2023-01-06
 */
public class SocketPackage implements Serializable {
    private RequestType requestType;
    private int id1;
    private int id2;
    private String dataPackage;

    public SocketPackage(RequestType requestType, String dataPackage, int id1, int id2) {
        this.requestType = requestType;
        this.dataPackage = dataPackage;
        this.id1 = id1;
        this.id2 = id2;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getDataPackage() {
        return dataPackage;
    }

    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

    @Override
    public String toString() {
        return "SocketPackage [id=" + id1 + ", myObjects1=" + dataPackage + ", " + " requestType="
                + requestType + "]";
    }
}

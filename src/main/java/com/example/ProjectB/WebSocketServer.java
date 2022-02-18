package com.example.ProjectB;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.example.ProjectB.ServerEndpointT;
import org.glassfish.tyrus.server.Server;


public class WebSocketServer {






    public static void runServer() {

        Server server = new Server("localhost", 8025, "/websockets",  ServerEndpointT.class);



        try {

            server.start();


            //is not important
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Please press a key to stop the server.");

            reader.readLine();

        } catch (Exception e) {

//            throw new RuntimeException(e);
            System.out.println();
        } finally {

            server.stop();

        }

    }

}

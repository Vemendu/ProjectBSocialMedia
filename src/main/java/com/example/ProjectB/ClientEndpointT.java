package com.example.ProjectB;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@ClientEndpoint
public class ClientEndpointT {



    private Logger logger = Logger.getLogger(this.getClass().getName());

    private static CountDownLatch latch;


    @OnOpen

    public void onOpen(Session session) {

        logger.info("Connected ... " + session.getId());

        try {

            session.getBasicRemote().sendText("start");

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

    }



    @OnMessage

    public String onMessage(String message, Session session) {

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {

            logger.info("Received ...." + message);

            String userInput = bufferRead.readLine();
//            session.getBasicRemote().sendText("to the session: "+userInput);
            return userInput;

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

    }



    @OnClose

    public void onClose(Session session, CloseReason closeReason) {

        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));

    }

    public static void main(String[] args) {

        latch = new CountDownLatch(1);



        ClientManager client = ClientManager.createClient();

        try {

            client.connectToServer(ClientEndpointT.class, new URI("ws://localhost:8025/websockets/game"));

            latch.await();



        } catch (DeploymentException | URISyntaxException | InterruptedException e) {

            throw new RuntimeException(e);

        }

    }





}

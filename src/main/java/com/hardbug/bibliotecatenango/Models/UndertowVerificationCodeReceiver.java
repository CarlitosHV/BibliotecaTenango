package com.hardbug.bibliotecatenango.Models;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.util.Headers;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


public class UndertowVerificationCodeReceiver implements VerificationCodeReceiver {

    private String code;
    private final CountDownLatch latch = new CountDownLatch(1);

    @Override
    public String getRedirectUri() throws IOException {
        return "http://localhost:8888/callback";
    }

    @Override
    public String waitForCode() throws IOException {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return code;
    }

    @Override
    public void stop() throws IOException {
        latch.countDown();
    }

    public void startServer() {
        Undertow server = Undertow.builder()
                .addHttpListener(8888, "localhost")
                .setHandler(new PathHandler()
                        .addExactPath("/callback", this::handleCallback))
                .build();

        server.start();
    }

    private void handleCallback(HttpServerExchange exchange) {
        code = exchange.getQueryParameters().get("code").getFirst();
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Authorization code received. You can close this page now.");
        latch.countDown();
    }
}


package com.zxf.security.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.removeAllMappings;

/**
 * Create by Mr.ZXF
 * on 2019-03-20 20:47
 */
public class MockServer {

    public static void main(String[] args) {
        WireMock.configureFor("192.168.0.125",8080);
        removeAllMappings();

        System.out.println("=========");
    }
}

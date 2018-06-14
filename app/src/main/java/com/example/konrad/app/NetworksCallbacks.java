package com.example.konrad.app;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public interface NetworksCallbacks {

    void connectResult(BufferedReader reader, PrintWriter writer);
    void loginResult(boolean result);


}

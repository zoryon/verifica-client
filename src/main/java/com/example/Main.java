package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.example.lib.ResponseMessages;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        @SuppressWarnings("resource")
        Socket socket = new Socket("localhost", 3000);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner keyboard = new Scanner(System.in);

        String res;
        do {
            // waiting --> get client's guess from keyboard
            // then --> sending the client's guess
            sendReq(out, keyboard);

            // waiting --> catching the server's response (the result of the client's guess)
            res = catchRes(in);

            // waiting --> catching the server's response (the number of needed attempts)
            String attempts = catchRes(in);

            switch (res) {
                case "<":
                    System.out.println(ResponseMessages.getOVER_RES());
                    break;
                case ">":
                    System.out.println(ResponseMessages.getUNDER_RES());
                    break;
                case "=":
                    System.out.println(ResponseMessages.getCORRECT_RES());
                    break;
                default:
                    System.out.println("Server error");
                    break;
            }

            System.out.println("Hai tentato " + attempts + " volte");
        } while (!isCorrect(res));
    }

    public static String newLine() {
        return "\n";
    }

    public static String catchRes(BufferedReader in) throws IOException {
        return in.readLine();
    }

    public static void sendReq(DataOutputStream out, Scanner keyboard) throws IOException {
        System.out.print("Inserisci il tuo numero: ");
        out.writeBytes(keyboard.nextLine() + newLine());
    }

    public static boolean isCorrect(String res) {
        return res.equals(ResponseMessages.getGUESS_IS_CORRECT());
    }
}
package SocP;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client{
    public static void main(String[] args)throws IOException {

        //get the IP and the port of the server
        Socket socket = new Socket("127.0.0.1", 431);
        Scanner input = new Scanner(System.in);

        //used to read data from the server
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //used to write data to the server
        PrintStream out = new PrintStream(socket.getOutputStream());
        System.out.println(in.readLine());

        while(true){
            System.out.print("> ");
            String request = input.nextLine();
            if(request.equals("quit")) break;
            out.println(request);
            System.out.println(in.readLine());
        }
    }
}

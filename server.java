package SocP;

//import all necessary things
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class server{
    //declare the port to be used
    private static final int SERVER_PORT = 431;

    public static void main(String[] args)throws IOException {

        //Start the connection with the client
        ServerSocket client = new ServerSocket(SERVER_PORT);
        Socket socket= client.accept();

        //used to read from client
        BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //used to write to the client
        PrintWriter clientOutput = new PrintWriter(socket.getOutputStream(), true);

        //greeting for the user
        String greeting = ("HELLO CCCS 431 Server written by Sumit, Anca and Filip ... READY");
        clientOutput.println(greeting);

        //continuous loop to get the requests from the client
        while(true) {
            //allow the BufferReader to read a STRING line from client
            String line = clientInput.readLine();

            //if the client says hi, reply back with IP address
            if (line.equals("HELLO")) {
                String message = ("ALOHA, " + socket.getInetAddress());
                clientOutput.println("[SERVER] " + message);

            //Show the client all the available commands
            } else if (line.equals("HELP")) {

                String help = ("You can use the following commands: " + "\n\r"
                        + "DOW for day of the week" + "\n\r"
                        + "TIME for time" + "\n\r"
                        +  "DATE for the current date" +"\n\r"
                        +"DATETIME to have the date and time formatted" +"\n\r"
                        +"BYE to close the server" + "\n\r" + ".");
                clientOutput.println(help);

            //Display the day of the week to the client
            } else if (line.equals("DOW")) {
                String dow = new SimpleDateFormat("EEEE").format(new Date());
                clientOutput.println("[SERVER] Today we are " + dow);

            //Display the current time to the client
            } else if (line.equals("TIME")) {
                LocalTime time = LocalTime.now();
                clientOutput.println("[SERVER] The time is " + time);

            //Display the current date to the client
            } else if (line.equals("DATE")) {
                LocalDate date = LocalDate.now();
                clientOutput.println("[SERVER] The date is: " + date);

            //Show the date and time in another format
            }else if(line.equals("DATETIME")){
                while(true) {
                    String question = ("Would you like the date and time formatted?(y/n) ");
                    clientOutput.println(question);
                    String answer = clientInput.readLine();
                    //if the client input is y, create a local date and time, change the pattern and display it
                    if (answer.equals("y")) {
                        LocalDateTime lDT = LocalDateTime.now();
                        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        String formattedDate = lDT.format(newFormat);
                        clientOutput.println("Date and Time in new format: " + formattedDate);
                        break;
                    //if the user input is n, move on to another input
                    }else if(answer.equals("n")){
                        clientOutput.println("Select another command or type HELP for more info");
                        break;
                    }else if(answer.equals("c")){
                        break;
                    }else {
                        clientOutput.println("Please enter y, n, or c to cancel.");
                    }
                }
            //close the server and all sockets
            }else if (line.equals("BYE")) {
                String goodbye = ("[SERVER] Goodbye " + socket.getInetAddress() + ". Connection closed.");
                clientOutput.println(goodbye);
                client.close();
                socket.close();

            //if none of the allowed commands are typed tell the user to use the HELP command to get more info
            } else {
                String error = ("You need to send a known command. Type HELP to see the commands.");
                clientOutput.println(error);
            }
        }
    }
}
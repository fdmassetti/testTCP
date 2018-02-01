/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Federico Massetti
 */
public class ServerConnessioneTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // porta del server maggiore di 1024 
        int port = 2000;
        //oggetto ServerSocket necessario per accettare richieste dal client
        ServerSocket sSocket = null;
        //oggetto da usare per realizzare la connessione TCP
        Socket connection;
        String mesIn, mesOut;
        boolean continua = true;

        while(continua){
            try{
                // il server si mette in ascolto sulla porta voluta
                sSocket = new ServerSocket(port);
                System.out.println("In attesa di connessioni!");
                //si è stabilita la connessione
                connection = sSocket.accept();
                System.out.println("Connessione avvenuta");
                System.out.println("Server socket" + connection.getLocalSocketAddress());
                System.out.println("Client socket" + connection.getRemoteSocketAddress());
                                                                                                                                
                BufferedReader inS = new BufferedReader(new InputStreamReader(connection.getInputStream()));    // Creazione di input e output degli streams, byte oriented;
                PrintStream outS = new PrintStream(connection.getOutputStream());
                mesIn = inS.readLine();
                System.out.println("Il client dice " + mesIn);
                
                switch(mesIn) {
                    case "Start" :
                        mesOut = "Ciao, benvenuto";
                        break;
                   case "close" :
                        mesOut = "Chiusura del connessione";
                        continua = false;
                        break;
                    default:
                        mesOut = "Scusa, non so come risponderti.";
                }
                System.out.println("Risposta da mandare: " + mesOut);
                outS.println(mesOut);
                outS.flush();
                
            }
               catch(IOException e){
                   System.err.println("Errore di I/O!");
            }
            
            //chiusura della connessione con il client
            try {
                if (sSocket!=null) {
                    sSocket.close();
                }
            } catch (IOException ex) {
                System.err.println("Errore nella chiusura della connessione!");
            }
            System.out.println("Connessione chiusa!");
        }
      }
}
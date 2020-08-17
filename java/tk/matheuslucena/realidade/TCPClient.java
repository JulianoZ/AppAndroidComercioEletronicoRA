package tk.matheuslucena.realidade;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

 public final class TCPClient{
     private static String ip = "10.128.70.58";

     public static String send_recv(String message) {
         //Socket socket = null;
         String response = new String();
         try {

             Socket socket = new Socket();
             SocketAddress socketAddress = new InetSocketAddress(ip, 1209);
             socket.connect(socketAddress, 1000);
             if(socket.isConnected()) {
                 //send
                 DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                 DOS.write(message.getBytes());

                 //receive
                 InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
                 BufferedReader reader = new BufferedReader(streamReader);
                 response = reader.readLine();

             }

             Log.d("TCP", "Resposta: " + response);

             return response;
         } catch (UnknownHostException e) {
             response= e.toString();
             Log.e("TCP", "Erro", e);
         } catch (IOException e) {
             response= e.toString();
             Log.e("TCP", "IOException", e);
             e.printStackTrace();
         }
         return "error";
     }
}

package cd.Dockerlab;
 
import java.util.Map;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
 
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
 
public class app {
  public static void main(String[] args) throws IOException {
    InetSocketAddress addr = new InetSocketAddress(3000);
    HttpServer server = HttpServer.create(addr, 0);
 
    server.createContext("/", new Gestionnaire());
    server.setExecutor(Executors.newCachedThreadPool());
    server.start();
    System.out.println("Le serveur en ecoute sur le port: "+addr.getPort());
  }
}
 
class Gestionnaire implements HttpHandler {
 
  public void handle(HttpExchange exchange) throws IOException {
    String methodeRequete = exchange.getRequestMethod();
    if (methodeRequete.equalsIgnoreCase("GET")) {
      Headers reponseEntete = exchange.getResponseHeaders();
      reponseEntete.set("Content-Type", "text/plain");
      exchange.sendResponseHeaders(200, 0);
 
      OutputStream reponse = exchange.getResponseBody();
      Headers requeteEntete = exchange.getRequestHeaders();
      Set<String> keySet = requeteEntete.keySet();
      Iterator<String> iter = keySet.iterator();
      /*while (iter.hasNext()) {
        String key = iter.next();
        List values = requeteEntete.get(key);
        String s = key + " = " + values.toString() + "";
         
      }*/
      //Parser variable d'environnement 
      Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                              envName,
                              env.get(envName));
        }
      String b = "Hello World - " + env;
      reponse.write(b.getBytes());
      reponse.close();
    }
  }
}
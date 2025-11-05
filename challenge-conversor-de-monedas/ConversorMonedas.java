import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorMonedas {
    
    // = = = = = = = = = = 
    // Método para obtener la conversión a través de una solicitud a la API
    // = = = = = = = = = = 
    public void solicitudAPI(String origen, String conversion, Double valorConversion) throws IOException, InterruptedException {
        // Realización de la solicitud a la API
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://v6.exchangerate-api.com/v6/08e6d6cd041b105cfd402191/pair/" 
            + origen + "/" + conversion + "/" + valorConversion))
            .build();
        
        // Recibimiento de la respuesta de la API
        HttpResponse<String> response = client
            .send(request, HttpResponse.BodyHandlers.ofString());
        
        if(response.statusCode() == 404){
            System.out.println("ERROR. Asegúrate de ingresar códigos válidos de divisas.");
        } else {
            // Conversión de JSON a JsonObject con GSON
            JsonObject jsonFromApi = new Gson().fromJson(response.body(), JsonObject.class);
            // Obtener únicamente la sección del objeto con los valores de monedas
            JsonElement conversionFromApi = jsonFromApi.get("conversion_result");   
            // Conversión final
            System.out.println(valorConversion + " " + origen + " vale(n) " + conversionFromApi + " " + conversion);
        }
    }


    // = = = = = = = = = = 
    // Método para solicitar todos los códigos de divisas soportados por la API
    // = = = = = = = = = = 
    public void codigosAPI() throws IOException, InterruptedException {
        // Realización de la solicitud a la API
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://v6.exchangerate-api.com/v6/08e6d6cd041b105cfd402191/codes"))
            .build();

        // Recibimiento de la respuesta de la API
        HttpResponse<String> response = client
            .send(request, HttpResponse.BodyHandlers.ofString());
        
        // Guardar respuesta como JsonObject
        JsonObject jsonFromApi = new Gson().fromJson(response.body(), JsonObject.class);
        // Transformar sección "supported_codes" en JsonArray
        JsonArray codigos = jsonFromApi.getAsJsonArray("supported_codes");
        
        // Imprimir cada elemento del JsonArray
        for(int i = 0; i < codigos.size(); i++) {
            System.out.println(codigos.get(i));    
        }
        
    }
}
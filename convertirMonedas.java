import java.net.http.*;
import java.net.URI;
import java.util.*;
import java.io.IOException;
import java.util.regex.*;

public class convertirMonedas {
    private static final String API_KEY = "16bc37bf39a45d848e3d8c82";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            HttpClient client = HttpClient.newHttpClient();

            try {
                // Obtener las monedas soportadas
                Set<String> supportedCodes = fetchSupportedCodes(client);

                if (supportedCodes.isEmpty()) {
                    System.out.println("No se pudieron obtener las monedas soportadas. Verifica tu API Key.");
                    return;
                }

                System.out.println("=== Conversor de Monedas ===");

                boolean continuar = true;
                while (continuar) {
                    // Mostrar monedas soportadas
                    System.out.println("\nMonedas disponibles: " + supportedCodes);

                    // Pedir monto
                    double amount = -1;
                    while (amount <= 0) {
                        System.out.print("Introduce la cantidad (número positivo): ");
                        try {
                            amount = Double.parseDouble(scanner.nextLine());
                            if (amount <= 0) {
                                System.out.println("⚠ El monto debe ser mayor que 0.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("⚠ Entrada inválida, introduce un número.");
                        }
                    }

                    // Pedir moneda origen
                    String fromCurrency = "";
                    while (fromCurrency.isEmpty() || !supportedCodes.contains(fromCurrency)) {
                        System.out.print("Introduce la moneda de origen (ej. USD): ");
                        fromCurrency = scanner.nextLine().trim().toUpperCase();
                        if (!supportedCodes.contains(fromCurrency)) {
                            System.out.println("⚠ Moneda no soportada.");
                        }
                    }

                    // Pedir moneda destino
                    String toCurrency = "";
                    while (toCurrency.isEmpty() || !supportedCodes.contains(toCurrency)) {
                        System.out.print("Introduce la moneda de destino (ej. CLP): ");
                        toCurrency = scanner.nextLine().trim().toUpperCase();
                        if (!supportedCodes.contains(toCurrency)) {
                            System.out.println("⚠ Moneda no soportada.");
                        }
                    }

                    // Hacer la conversión
                    double result = convertCurrency(client, amount, fromCurrency, toCurrency);

                    if (result >= 0) {
                        System.out.printf("%n%.2f %s = %.2f %s%n",
                                amount, fromCurrency, result, toCurrency);
                    } else {
                        System.out.println("⚠ No se pudo realizar la conversión.");
                    }

                    // Preguntar si quiere otra conversión
                    System.out.print("\n¿Deseas hacer otra conversión? (s/n): ");
                    String respuesta = scanner.nextLine().trim().toLowerCase();
                    if (!respuesta.equals("s")) {
                        continuar = false;
                    }
                }

                System.out.println("\nGracias por usar el Conversor de Monedas. 👋");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Obtener lista de monedas soportadas
    private static Set<String> fetchSupportedCodes(HttpClient client) throws IOException, InterruptedException {
        String url = BASE_URL + "/codes";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Set<String> codes = new HashSet<>();
        Matcher matcher = Pattern.compile("\\[\"([A-Z]{3})\",\"[^\"]+\"\\]").matcher(response.body());
        while (matcher.find()) {
            codes.add(matcher.group(1));
        }

        return codes;
    }

    // Hacer la conversión
    private static double convertCurrency(HttpClient client, double amount, String from, String to)
            throws IOException, InterruptedException {
        String url = BASE_URL + "/pair/" + from + "/" + to + "/" + amount;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Matcher matcher = Pattern.compile("\"conversion_result\":([0-9.]+)").matcher(response.body());
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }

        return -1;
    }
}
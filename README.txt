# 💱 Conversor de Monedas en Java

Este es un proyecto simple en Java que permite convertir entre distintas monedas en tiempo real utilizando la API de [ExchangeRate-API](https://www.exchangerate-api.com/).

## 🚀 Características

- Conversión de cualquier moneda soportada por la API.
- Lista las monedas disponibles antes de convertir.
- Valida que los datos ingresados sean correctos.
- Permite realizar múltiples conversiones sin reiniciar el programa.
- Interfaz de consola amigable.

## 📦 Requisitos

- Java JDK 17 o superior (probado con JDK 24).
- Conexión a internet.
- Una **API Key gratuita** de [ExchangeRate-API](https://www.exchangerate-api.com/).

## ⚙️ Instalación y uso

1. Clona este repositorio o descarga el código:
   ```bash
   git clone (URL del projecto)
   cd conversor-monedas-java
   ```
2. Abre el archivo CurrencyConverter.java y reemplaza la API Key con una obtenida en la página anteriormente mencionada.

private static final String API_KEY = "TU_API_KEY_AQUI";

3. Compila el programa:

   ```bash 
javac CurrencyConverter.java
   ```
4. Ejecuta el programa:

   ```bash
java CurrencyConverter
   ```
5. ¡Empieza a convertir monedas! ✨

📋 Ejemplo de uso
=== Conversor de Monedas ===

Monedas disponibles: [USD, EUR, CLP, JPY, GBP, ...]
Introduce la cantidad (número positivo): 100
Introduce la moneda de origen (ej. USD): USD
Introduce la moneda de destino (ej. CLP): CLP

100.00 USD = 92100.00 CLP

¿Deseas hacer otra conversión? (s/n): n

Gracias por usar el Conversor de Monedas. 👋

🛠️ Tecnologías utilizadas
- Java 24

- ExchangeRate-API

- HttpClient (librería estándar de Java para peticiones HTTP)

📄 Licencia
Este proyecto es de uso libre. Puedes modificarlo y adaptarlo como quieras.

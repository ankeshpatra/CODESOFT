import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter extends JFrame {

    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JButton convertButton;
    private JLabel resultLabel;

    private final String[] currencies = {"USD", "EUR", "GBP", "INR", "JPY", "CAD", "AUD", "CNY", "AED", "AMD", "ALL", "AFN"};
    private final String apiKey = "11DPZXwA6rKlMDo9SEGb6qNBaHbYQhfH";

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("From Currency:"));
        fromCurrency = new JComboBox<>(currencies);
        add(fromCurrency);

        add(new JLabel("To Currency:"));
        toCurrency = new JComboBox<>(currencies);
        toCurrency.setSelectedIndex(1);
        add(toCurrency);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        convertButton = new JButton("Convert");
        add(convertButton);

        resultLabel = new JLabel("Converted Amount: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(resultLabel);

        convertButton.addActionListener(e -> convertCurrency());
    }

    private void convertCurrency() {
        String from = (String) fromCurrency.getSelectedItem();
        String to = (String) toCurrency.getSelectedItem();
        String amountText = amountField.getText();

        try {
            double amount = Double.parseDouble(amountText);
            String urlStr = "https://api.apilayer.com/exchangerates_data/convert?from=" + from + "&to=" + to + "&amount=" + amount + "&apikey=" + apiKey;
            URI uri = URI.create(urlStr);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            double result = extractConversionResult(responseBody);
            resultLabel.setText("Converted Amount: " + String.format("%.2f", result) + " " + to);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private double extractConversionResult(String jsonResponse) {
        try {
            String resultKey = "\"result\":";
            int resultPos = jsonResponse.indexOf(resultKey);
            if (resultPos != -1) {
                String resultString = jsonResponse.substring(resultPos + resultKey.length()).trim();
                resultString = resultString.split(",")[0].replaceAll("[^0-9.]", "");
                return Double.parseDouble(resultString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CurrencyConverter app = new CurrencyConverter();
            app.setVisible(true);
        });
    }
}
package com.example.json_parser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private TextView xmlTextView, jsonTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xmlTextView = findViewById(R.id.xmlTextView);
        jsonTextView = findViewById(R.id.jsonTextView);

        Button parseXmlButton = findViewById(R.id.parseXmlButton);
        parseXmlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseXML();
            }
        });

        Button parseJsonButton = findViewById(R.id.parseJsonButton);
        parseJsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJSON();
            }
        });
    }

    private void parseXML() {
        try {
            InputStream is = getAssets().open("city_data.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            NodeList cityList = doc.getElementsByTagName("city");
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < cityList.getLength(); i++) {
                Element city = (Element) cityList.item(i);
                String cityName = city.getElementsByTagName("City_Name").item(0).getTextContent();
                String latitude = city.getElementsByTagName("Latitude").item(0).getTextContent();
                String longitude = city.getElementsByTagName("Longitude").item(0).getTextContent();
                String temperature = city.getElementsByTagName("Temperature").item(0).getTextContent();
                String humidity = city.getElementsByTagName("Humidity").item(0).getTextContent();
                result.append("City: ").append(cityName).append(", Latitude: ").append(latitude)
                        .append(", Longitude: ").append(longitude).append(", Temperature: ")
                        .append(temperature).append(", Humidity: ").append(humidity).append("\n");
            }
            xmlTextView.setText(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJSON() {
        try {
            InputStream is = getAssets().open("city_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray cityArray = jsonObject.getJSONArray("cities");
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject city = cityArray.getJSONObject(i);
                String cityName = city.getString("City_Name");
                String latitude = city.getString("Latitude");
                String longitude = city.getString("Longitude");
                String temperature = city.getString("Temperature");
                String humidity = city.getString("Humidity");
                result.append("City: ").append(cityName).append(", Latitude: ").append(latitude)
                        .append(", Longitude: ").append(longitude).append(", Temperature: ")
                        .append(temperature).append(", Humidity: ").append(humidity).append("\n");
            }
            jsonTextView.setText(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

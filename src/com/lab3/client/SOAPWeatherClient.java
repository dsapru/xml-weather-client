/*
 * A client application that will connect to the National Weather Service web
 * site using HTTP and XML and/or SOAP and display current weather conditions, 
 * using latitude and longitude as inputs.
 * Deepit Raj Sapru 
 * dxs2895
 * 1001522895
 * References: 
 * 1) https://graphical.weather.gov/xml/
 * 2) https://javabrains.io/courses/javaee_jaxws/
 * 3) https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 * 3) https://youtu.be/6hqDMS-oJ9k?list=PLqq-6Pq4lTTZTYpk_1DOowOGWJMIH5T39
 * 4) https://www.youtube.com/watch?v=E76xW1JTVXY
 * 5) https://www.javatpoint.com/java-sql-date
 * 6) https://stackoverflow.com/questions/837703/how-can-i-get-a-java-io-inputstream-from-a-java-lang-string
*/

package com.lab3.client;

import gov.weather.graphical.xml.DWMLgen.schema.DWML_xsd.ProductType;
import gov.weather.graphical.xml.DWMLgen.schema.DWML_xsd.UnitType;
import gov.weather.graphical.xml.DWMLgen.schema.DWML_xsd.WeatherParametersType;
import gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLBindingStub;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class SOAPWeatherClient {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the Latitude:");
        String Latitude = input.next();
        System.out.println("Enter the Longitude:");
        String Longitude = input.next();
        SOAPWeatherClient wc = new SOAPWeatherClient();
        wc.getweatherInfo(Latitude, Longitude);

    }


    protected void getweatherInfo(String lat, String longi) {
        Scanner input = new Scanner(System.in);
        BigDecimal latitude = new BigDecimal(Double.parseDouble(lat));
        BigDecimal longitude = new BigDecimal(Double.parseDouble(longi));
        ProductType p = ProductType.fromValue("time-series");
        UnitType unit = UnitType.fromValue("e");
        Date d = new Date(System.currentTimeMillis());
        Calendar start_time = Calendar.getInstance();
        start_time.setTime(d);
        Calendar end_time = Calendar.getInstance();
        start_time.setTime(d);
        WeatherParametersType wt = new WeatherParametersType();
        String Result;
        String url = "https://graphical.weather.gov:443/xml/SOAP_server/ndfdXMLserver.php"; // url taken from
        // graphical.weather.gov
        URL Link;
        try {
            Link = new URL(url);
            NdfdXMLBindingStub stub = new NdfdXMLBindingStub(Link, null); // stub object to invoke func webservice for
            // request on SOAP
            Result = stub.NDFDgen(latitude, longitude, p, start_time, end_time, unit, wt);
            // System.out.println(Result);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // DOM for XML data
            try {
                DocumentBuilder build = factory.newDocumentBuilder();
                StringBuilder strBuild = new StringBuilder();
                strBuild.append(Result);
                ByteArrayInputStream BArrIS = new ByteArrayInputStream(strBuild.toString().getBytes("UTF-8"));
                Document doc = build.parse(BArrIS);

                String temperature = doc.getElementsByTagName("temperature").item(0).getTextContent();
                String humidity = doc.getElementsByTagName("humidity").item(0).getTextContent();
                String rainProb = doc.getElementsByTagName("probability-of-precipitation").item(0).getTextContent();
                String cloudAmount = doc.getElementsByTagName("cloud-amount").item(0).getTextContent();
                String windSpeed = doc.getElementsByTagName("wind-speed").item(0).getTextContent();
                String windDirection = doc.getElementsByTagName("direction").item(0).getTextContent();
                String waves = doc.getElementsByTagName("waves").item(0).getTextContent();
                System.out.println(temperature + "Fahrenheit" + humidity + "Percent" + rainProb + "Percent"
                        + cloudAmount + "Percent" + windSpeed + "Knots" + windDirection + "Degrees" + waves);

                System.out.println("Do you want to Refresh? (y/n)");
                String refresh = input.next();
                switch (refresh) {
                    case "y":
                        getweatherInfo(lat, longi);

                    case "n":
                        System.out.println("Exiting..");
                        System.exit(0);

                    default:
                        System.out.println("Invalid input");

                }

                // waves only for coastal area
            } catch (ParserConfigurationException | IOException | SAXException e2) {
                e2.printStackTrace();// invalid character encoding in BArrIS
            }
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (RemoteException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }

    }

}

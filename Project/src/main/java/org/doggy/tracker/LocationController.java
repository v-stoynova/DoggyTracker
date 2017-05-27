package org.doggy.tracker;

import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/location")
public class LocationController {

	@RequestMapping(method = RequestMethod.GET)
    public String location(@RequestParam(value = "User") String User,
            @RequestParam(value = "Pass") String pass,
            @RequestParam(value = "IMEI") String imei,
            @RequestParam(value = "GPS") List<String> params, Model model, HttpServletResponse response)  throws ServletException, IOException{
		
		String lat = params.get(3);
		String lon = params.get(5);
		
		float latitude = Float.parseFloat(lat);
		float longitude = Float.parseFloat(lon);
		
		int remainder = (int)latitude / 100;
		latitude = remainder + ((latitude - remainder * 100)/60);
		
		remainder = (int)longitude / 100;
		longitude = remainder + ((longitude - remainder * 100)/60);
		
		PrintWriter writer = response.getWriter();
		
        String htmlRespone = "<html> <head>";
        htmlRespone += "<meta name='viewport' content='initial-scale=1.0, user-scalable=no'/><title>Location</title><style> #map { height: 25%; margin: 10%;  padding: 10%; }";
        htmlRespone += " </style></head><body><div id='map'></div>";
        htmlRespone += "<script type='text/javascript'> function initMap() { var myLatLng = {lat: " + latitude + ", lng: " + longitude + "};";      
        htmlRespone += "var map = new google.maps.Map(document.getElementById('map'), { zoom: 20,  center: myLatLng}); var marker = new google.maps.Marker({ position: myLatLng, map: map});} </script>";    
        htmlRespone += "<script async='async' defer='defer' src='https://maps.googleapis.com/maps/api/js?key= AIzaSyAFDPa0zKd2Lua8zEJhJe8yVcuzbVAqeIw&callback=initMap'> </script></body>";
        htmlRespone += "</html>";
         
        // return response
        writer.println(htmlRespone);
		
        return "location";
    }

}

// location?IMEI=861694038972483&User=Testdev2&Pass=M2IP1382&Description="Testdev2"861694038972483BAT-0,42.64260GSM:"0578","0890"&GPS=$GNRMC,085606.510,A,23.36640,N,02317.3632,E,0.23,0.00,171116,,,A*76&
//get for the map
//Store string in coockie with JS
package com.foodtruck.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

@Component
public class FoodTruckDataReader {

	// Java code to illustrate reading a 
	// CSV file line by line 
	public  List<FoodTruckInfo> readDataLineByLine( FileReader filereader) 
	{ 
		 List<FoodTruckInfo> foodTrucksList = new ArrayList<>();
	    try { 
	  
	         // class with CSV file as a parameter. 
	        // create csvReader object passing 
	        // file reader as a parameter 
	        CSVReader csvReader = new CSVReader(filereader); 
	        String[] nextRecord; 
	        // skip the header
	        csvReader.readNext();
	        // we are going to read data line by line 
	        while ((nextRecord = csvReader.readNext()) != null) { 
	                 foodTrucksList.add(readFoodTrucks(nextRecord)) ;
		    } 
	    } 
	    catch (Exception e) { 
	        e.printStackTrace(); 
	    } 
	    return foodTrucksList;
	} 
	
	public List<FoodTruckInfo> readBooksFromCSVFile(String csvFile) throws IOException {
	        String line = "";
	        String cvsSplitBy = ",";
	        List<FoodTruckInfo> foodTrucksList = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        	// skip the header 
	        	br.readLine();
	            while ((line = br.readLine()) != null) {
	                // use comma as separator
	                String[] foodTrucks = line.split(cvsSplitBy);
	                foodTrucksList.add(readFoodTrucks(foodTrucks)) ;
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return foodTrucksList;
	}
	
	
	public FoodTruckInfo readFoodTrucks(String[] foodTrucks) throws IOException {
	    FoodTruckInfo foodTruck = new FoodTruckInfo();
	      List<String> wordList = Arrays.asList(foodTrucks);  
	      Iterator<String> iterator = wordList.iterator();
	      int columnIndex=0;
	    while (iterator.hasNext()) {
	       
	    	    String cell = iterator.next();
	    	    columnIndex++;
	            if ( cell!= null && cell.trim().length() >=0 && !cell.trim().equals("")) {
	            switch (columnIndex) {
	            case 1:
	            	foodTruck.setLocationId(Integer.parseInt(cell));
	                break;
	            case 2:
	            	foodTruck.setApplicant(cell);
	                break;
	            case 3:
	            	foodTruck.setFacilityType(cell);
	                break;
	            case 4:
	            	foodTruck.setCnn(Integer.parseInt(cell));
	                break;
	            case 5:
	            	foodTruck.setLocationDescription(cell);
	                break;
	            case 6:
	            	foodTruck.setAddress(cell);
	                break;
	            case 7:
	            	foodTruck.setBlockLot(cell);
	                break;
	            case 8:
	            	foodTruck.setBlock(cell);
	                break;
	            case 9:
	            	foodTruck.setLot(cell);
	                break;
	            case 10:
	            	foodTruck.setPermit(cell);
	                break;
	            case 11:
	            	foodTruck.setStatus(cell);
	                break;
	            case 12:
	            	foodTruck.setFoodItems(cell);
	                break;
	            case 13:
	            	foodTruck.setX(Double.parseDouble(cell));
	                break;
	                
	            case 14:
	            	foodTruck.setY(Double.parseDouble(cell));
	                break;
	            
		        case 15:
	            	foodTruck.setLatitude(Float.parseFloat(cell));
	                break;
	            
		        case 16:
	            	foodTruck.setLongitude(Float.parseFloat(cell));
	                break;
	                
		        case 17:
	            	foodTruck.setSchedule(cell);
	                break;
	                
		        case 18:
	            	foodTruck.setDaysHours(cell);
	                break;
		        case 19:
	            	foodTruck.setNOISent( cell);
	                break;
		        case 20:
	            	foodTruck.setApproved(cell) ;
	                break;
		        case 21:
	            	foodTruck.setReceived(cell) ;
	                break;
	            
		        case 22:
	            	foodTruck.setPriorPermit(Integer.parseInt(cell));
	                break;     
	           
		        case 23:
	            	foodTruck.setExpirationDate(cell);
	                break;   
	                
		        case 24:
	            	foodTruck.setLocation(new Location(foodTruck.getLongitude(), foodTruck.getLatitude()));
	                break; 
	         } //switch
	        } // if not null
	     	    
	    }  
	    return foodTruck;
	}
	
}

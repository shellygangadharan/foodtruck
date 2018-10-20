package com.foodtruck.demo;

import java.io.FileReader;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class FoodTruckPopulator implements CommandLineRunner {

	
	private FoodTruckRepository foodTruckRepository;
	private FoodTruckDataReader foodTruckDataReader;
	
	public FoodTruckPopulator(FoodTruckRepository foodTruckRepository, FoodTruckDataReader foodTruckDataReader) {
		this.foodTruckRepository=  foodTruckRepository;
		this.foodTruckDataReader = foodTruckDataReader;
	}
	
	DateFormat formatter=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	@Override
	public void run(String... args) throws Exception {
		List<FoodTruckInfo> foodTrucks = new ArrayList<>();
		this.foodTruckRepository.deleteAll();
		FileReader fileReader = new FileReader("Mobile_Food_Facility_Permit.csv"); 		
		foodTrucks = readDataLineByLine(fileReader);
		this.foodTruckRepository.saveAll(foodTrucks) ;
	}

	public  List<FoodTruckInfo> readDataLineByLine( FileReader filereader) 
	{ 
		return foodTruckDataReader.readDataLineByLine(filereader) ;
	}
	
}

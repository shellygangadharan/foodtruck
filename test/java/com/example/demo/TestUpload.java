package com.example.demo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import com.foodtruck.demo.FoodTruckController;
import com.foodtruck.demo.FoodTruckInfo;
import com.foodtruck.demo.FoodTruckPopulator;
import com.foodtruck.demo.FoodTruckRepository;
import com.foodtruck.demo.UploadService;


public class TestUpload {

	
	private FoodTruckRepository foodTruckRepository;
	
	private FoodTruckPopulator populator;

	private UploadService upload = new UploadService();
	
	private MongoOperations mongoOperations = Mockito.mock(MongoOperations.class);
	  
	
	@Before
	public void setUp() throws Exception {
		mongoOperations = Mockito.mock(MongoOperations.class);
		foodTruckRepository =  Mockito.mock(FoodTruckRepository.class);
		populator =  Mockito.mock(FoodTruckPopulator.class);
	}
	

	@After
	public void tearDown() throws Exception {
		mongoOperations = null;
		foodTruckRepository = null;
		populator =  null;
	}

	@Test(expected = RuntimeException.class)
	public void testWrongLongitudeAndLatitude() {
		
		FoodTruckController controller = new FoodTruckController(foodTruckRepository, populator);	
		controller.setMongoOperations(mongoOperations);
		float longitude = -222;
		float latitude= -189;
		double distance =0.5;
		Point basePoint = new Point(longitude, latitude);
    	Distance radius = new Distance(distance, Metrics.MILES) ;
    	Circle area = new Circle(basePoint, radius);
    	Query query = new Query();
    	query.addCriteria(Criteria.where("location").withinSphere(area));
    	List<FoodTruckInfo> foods = new ArrayList<>() ;
		when(mongoOperations.find(query, FoodTruckInfo.class)).thenReturn(foods);
		controller.findByDistance(longitude, latitude, distance) ;
	}
	
	@Test(expected = RuntimeException.class)
	public void testWrongLongitude() {
		FoodTruckController controller = new FoodTruckController(foodTruckRepository, populator);	
		controller.setMongoOperations(mongoOperations);
		float longitude = -222;
		float latitude= -30;
		double distance =0;
		Point basePoint = new Point(longitude, latitude);
    	Distance radius = new Distance(distance, Metrics.MILES) ;
    	Circle area = new Circle(basePoint, radius);
    	Query query = new Query();
    	query.addCriteria(Criteria.where("location").withinSphere(area));
    	List<FoodTruckInfo> foods = new ArrayList<>() ;
		when(mongoOperations.find(query, FoodTruckInfo.class)).thenReturn(foods);
		controller.findByDistance(longitude, latitude, distance) ;
	}

	@Test(expected = RuntimeException.class)
	public void testWrongLatitude() {
			
		FoodTruckController controller = new FoodTruckController(foodTruckRepository, populator);	
		controller.setMongoOperations(mongoOperations);
		float longitude = -122;
		float latitude= -289;
		double distance =1;
		Point basePoint = new Point(longitude, latitude);
    	Distance radius = new Distance(distance, Metrics.MILES) ;
    	Circle area = new Circle(basePoint, radius);
    	Query query = new Query();
    	query.addCriteria(Criteria.where("location").withinSphere(area));
    	List<FoodTruckInfo> foods = new ArrayList<>() ;
		when(mongoOperations.find(query, FoodTruckInfo.class)).thenReturn(foods);
		controller.findByDistance(longitude, latitude, distance) ;
	}
	
	@Test
	public void testWithCorrectdata() {
		boolean success = false;
		
		FoodTruckController controller = new FoodTruckController(foodTruckRepository, populator);	
		controller.setMongoOperations(mongoOperations);
		float longitude = -179;
		float latitude= 30;
		double distance =1;
		Point basePoint = new Point(longitude, latitude);
    	Distance radius = new Distance(distance, Metrics.MILES) ;
    	Circle area = new Circle(basePoint, radius);
    	Query query = new Query();
    	query.addCriteria(Criteria.where("location").withinSphere(area));
    	List<FoodTruckInfo> foods = new ArrayList<>() ;
		when(mongoOperations.find(query, FoodTruckInfo.class)).thenReturn(foods);
		controller.findByDistance(longitude, latitude, distance) ;
		success = true;
		assertTrue(success);
	}
	

	/* @Test
	public void testUploadGoodData() {
			FoodTruckController controller = new FoodTruckController(foodTruckRepository, populator);	
		// This should not throw an error
			File initialFile = new File("C:\\Users\\shell\\Documents\\workspace-sts-3.9.1.RELEASE\\demo-1\\src\\main\\resources\\Mobile_Food_Facility_Permit.csv");
			 MockMultipartFile file = null;
		    InputStream targetStream = null;
			try {
				targetStream = new FileInputStream(initialFile);
			     file = new MockMultipartFile("C:\\Users\\shell\\Documents\\workspace-sts-3.9.1.RELEASE\\demo-1\\src\\main\\resources\\Mobile_Food_Facility_Permit.csv", targetStream);
			     
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail(e.getMessage());
			}
		    
			try {
				boolean success = false;
				controller.setUploadService(upload);
				List<FoodTruckInfo> foods = new ArrayList<>() ;
				//when( populator.readDataLineByLine(filereader).thenReturn(foods);
				when( foodTruckRepository.saveAll(foods)).thenReturn(foods);
				ResponseEntity<String>  res= controller.uploadTruckData(file) ;
				if (res.getStatusCode().equals(HttpStatus.OK)) {
					success = true;					
				}
				targetStream.close();
				assertTrue(success);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail(e.getMessage());
			}
		
	}
	
	@Test
	public void testUploadBadData() {
			FoodTruckController controller = new FoodTruckController(foodTruckRepository, populator);	
		// This should not throw an error
			File initialFile = new File("C:\\Users\\shell\\Documents\\workspace-sts-3.9.1.RELEASE\\demo-1\\src\\main\\resources\\Mobile_Food_Facility_Permit_Incorrect.csv");
			 MockMultipartFile file = null;
		    InputStream targetStream = null;
			try {
				targetStream = new FileInputStream(initialFile);
			     file = new MockMultipartFile("C:\\Users\\shell\\Documents\\workspace-sts-3.9.1.RELEASE\\demo-1\\src\\main\\resources\\Mobile_Food_Facility_Permit_Incorrect.csv", targetStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail(e.getMessage());
			}
		    
			try {
				boolean success = false;
				controller.setUploadService(upload);
				
				ResponseEntity<String>  res= controller.uploadTruckData(file) ;
				if (res.getStatusCode().equals(HttpStatus.OK)) {
					success = true;					
				}
				targetStream.close();
				assertFalse(success);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail(e.getMessage());
			}
		
	} */
}

package com.foodtruck.demo;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.* ;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/foodTrucks")
public class FoodTruckController {

	@Autowired
	private MongoOperations mongoOperations;
	@Autowired
	private UploadService uploadService;
	private FoodTruckRepository foodTruckRepository;
	private FoodTruckPopulator populator;

    public FoodTruckController(FoodTruckRepository foodTruckRepository, FoodTruckPopulator pop) {
        this.foodTruckRepository = foodTruckRepository;
        this.populator = pop;
    }
	

    @GetMapping("/all")
    public List<FoodTruckInfo> getAll(){
        List<FoodTruckInfo> foodTrucks = this.mongoOperations.findAll(FoodTruckInfo.class);

        return foodTrucks;
    }

   
    @PostMapping
    public void insert(@RequestBody FoodTruckInfo foodTruckInfo){
        this.mongoOperations.insert(foodTruckInfo);
    }

    @PutMapping
    public void update(@RequestBody  FoodTruckInfo foodTruckInfo){
        this.mongoOperations.save(foodTruckInfo);
    }
    
    @GetMapping("/address/{longitude}/{latitude}/{distance}")
    public List<FoodTruckInfo> findByDistance(@PathVariable("longitude") float longitude,@PathVariable("latitude") float latitude,@PathVariable("distance") double distance){
    	
    	if (distance < 0) {
    		throw new RuntimeException( " Distance has to be greater than zero") ;
    	}
    	if ( latitude > 90 || latitude <-90) {
    		throw new RuntimeException( " Latitude should be between -90 and 90") ;
    	}
    	if ( longitude > 180 || longitude <-180) {
    		throw new RuntimeException( " Longitude should be between -180 and 180") ;
    	}
    	
    	Point basePoint = new Point(longitude, latitude);
    	Distance radius = new Distance(distance, Metrics.MILES) ;
    	Circle area = new Circle(basePoint, radius);
    	Query query = new Query();
    	query.addCriteria(Criteria.where("location").withinSphere(area));
    	
    	List<FoodTruckInfo> trucks =  mongoOperations.find(query,FoodTruckInfo.class);
    	return trucks;
    }


    @PostMapping(value = "/uploadFile")
    public ResponseEntity<String> uploadTruckData(@RequestParam("file") MultipartFile file) throws IOException {

        String message = "";
        File fileInfo = uploadService.convertMultiPartToFile(file);
        try {
        	List<FoodTruckInfo> foodTrucks = new ArrayList<>();
    		this.foodTruckRepository.deleteAll();
    		FileReader fileReader = new FileReader(fileInfo);
    		foodTrucks = populator.readDataLineByLine(fileReader);
    		
    		this.foodTruckRepository.saveAll(foodTrucks) ;
            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
   
}

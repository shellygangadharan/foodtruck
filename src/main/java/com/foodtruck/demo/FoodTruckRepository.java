package com.foodtruck.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
//import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface FoodTruckRepository extends MongoRepository<FoodTruckInfo,String>{

}

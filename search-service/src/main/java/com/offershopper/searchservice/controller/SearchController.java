package com.offershopper.searchservice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.offershopper.searchservice.spellcheck.SpellCheck;
@CrossOrigin
@RestController
public class SearchController {

  MongoClient mongoClient;
  MongoDatabase database;
  MongoCollection<Document> collection;
  Boolean caseSensitive = false;
  Boolean diacriticSensitive = false;

  @GetMapping("/search-key/{searchKey}")
  public ResponseEntity<List<Document>> getSearchResults(@PathVariable(value = "searchKey") String searchKey) {
    searchKey.toLowerCase();
    try {
      searchKey = SpellCheck.spellChecker(searchKey);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
    mongoLogger.setLevel(Level.SEVERE);

    List<Document> searchResults = new ArrayList<Document>();

    mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    database = mongoClient.getDatabase("offershopperdb");
    collection = database.getCollection("offers");

    collection.createIndex(new Document("offerCategories", "text").append("offerTitle", "text").append("keywords", "text"),
        new IndexOptions());

    try {

      MongoCursor<Document> cursor = null;
      
      
      
      Document findCommand = new Document("$text",
          new Document("$search", searchKey).append("$caseSensitive", new Boolean(caseSensitive))
          .append("$diacriticSensitive", new Boolean(diacriticSensitive)));

      Document projectCommand =  new Document(
          "score", new Document("$meta", "textScore"));
      

      Document sortCommand = new Document(
          "score", new Document("$meta", "textScore"));

      
      
      cursor = collection 
          .find(findCommand)
          .projection(projectCommand)
          .sort(sortCommand)
          .iterator();


      while (cursor.hasNext()) {
        Document article = cursor.next();
        System.out.println(article);
        System.out.println("here-2");
        searchResults.add(article);
      }

      cursor.close();
      // return ResponseEntity.status(HttpStatus.FOUND).body(searchResults);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      collection.dropIndexes();
      mongoClient.close();
      return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }

  }

  @GetMapping("offerCategories/{offerCategories}/search-key/{searchKey}")
  public ResponseEntity<List<Document>> searchByofferCategoriesAndSearchkey(@PathVariable(value = "offerCategories") String offerCategories,
      @PathVariable(value = "searchKey") String searchKey) {
    searchKey.toLowerCase();
   try {
      searchKey = SpellCheck.spellChecker(searchKey);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
    mongoLogger.setLevel(Level.SEVERE);

    List<Document> searchResults = new ArrayList<Document>();

    mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    database = mongoClient.getDatabase("offershopperdb");
    collection = database.getCollection("offers");

    collection.createIndex(new Document("offerCategories", "text").append("offerTitle", "text").append("keywords", "text"),
        new IndexOptions());

    try {

      MongoCursor<Document> cursor = null;


      
      Document findCommand = new Document("$text",
          new Document("$search", searchKey).append("$caseSensitive", new Boolean(caseSensitive))
          .append("$diacriticSensitive", new Boolean(diacriticSensitive)))
          .append("offerCategories", offerCategories);

      Document projectCommand =  new Document(
          "score", new Document("$meta", "textScore"));
      

      Document sortCommand = new Document(
          "score", new Document("$meta", "textScore")
      );

      
      
      cursor = collection 
          .find(findCommand)
          .projection(projectCommand)
          .sort(sortCommand)
          .iterator();
      
      


      while (cursor.hasNext()) {
        Document article = cursor.next();
        System.out.println(article);
        searchResults.add(article);
      }

      cursor.close();
      // return ResponseEntity.status(HttpStatus.FOUND).body(searchResults);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      collection.dropIndexes();
      mongoClient.close();
      return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }

  }

  @GetMapping("offerCategories/{offerCategories}")
  public ResponseEntity<List<Document>> searchByofferCategories(@PathVariable(value = "offerCategories") String offerCategories) {

    Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
    mongoLogger.setLevel(Level.SEVERE);

    List<Document> searchResults = new ArrayList<Document>();

    mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    database = mongoClient.getDatabase("offershopperdb");
    collection = database.getCollection("offers");

    collection.createIndex(new Document("offerCategories", "text"), new IndexOptions());

    try {

      MongoCursor<Document> cursor = null;
      
      
      Document findCommand = new Document("$text",
          new Document("$search", offerCategories).append("$caseSensitive", new Boolean(caseSensitive))
          .append("$diacriticSensitive", new Boolean(diacriticSensitive)));

      Document projectCommand =  new Document(
          "score", new Document("$meta", "textScore"));
      

      Document sortCommand = new Document(
          "score", new Document("$meta", "textScore")
      );

      
      
      cursor = collection 
          .find(findCommand)
          .projection(projectCommand)
          .sort(sortCommand)
          .iterator();


      while (cursor.hasNext()) {
        Document article = cursor.next();
        System.out.println(article);
        System.out.println("here-2");
        searchResults.add(article);
      }

      cursor.close();
      // return ResponseEntity.status(HttpStatus.FOUND).body(searchResults);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      collection.dropIndexes();
      mongoClient.close();
      return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }

  }
  
  
  
  
}

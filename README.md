# CommunityDayCodingContest
Community day coding contest

## Java CA coding contest


Write a program that instantiates objects. Implement the following interface: eu.javaca.playground.Instantiable: 

``` public String instantiate(Class any, Target target);  ```

The "instantiate" method can take "any" class type and returns a serialized version of an instance of class "any" (as a JSON string, controlled by the target which is an Enum value "JSON").

Your solution will be evaluated based on the following criteria:

1. Completeness. Firstly, it has to work, but the richer your solution is, the better. Think of various collections, data types, inheritance, generics, etc

2. Corner case handling. Eg: one of the fields is a List<Animal>. The instantiated solution contains at least one example of each sub-types of Animal (let’s say, Dog, Cat, Koala) in the serialized list. Support for parameterized classes, JAXBElement (for xml and so on)

3. Design. How elegant your solution is. How easy it is to read, extend, understand.


Example:

``` 
AboutPerson { 

      String name; 

      Integer age; 

      List<Hobbies> hobbies; 

      Book favouriteBook; 

      Workplace<Person> workplace;  

} 
```


Possible JSON result:

``` 
{ 

    "name":"zmiusdlm mamdfu",  
    "age": 21,  
    "hobbies": [{ 
        "type": "WATER_SPORT",  
        "proficiencyLevel": 4,  
        "timePracticing": 10,  
        "name": "Swimming",  
        "requiresSuit": true 
        }, { 
        "name":"Chess",  
        "eloRating": 1560 
    }], 
    "favouriteBook": {"name": "Lumea Sofiei", "ISBN": "308283402880"}, 
    "workplace": null 
} 
```
## Rules 

### Solution can be implemented in the following languages: Java, Scala, Kotlin

### Solution must be posted in a fork repo to this one on the 5th of July, 23:00.


## Motivation

Stretch goals: 
1. Implement XML target. 
2. Take into account validation annotations.

The solution is not just didactic but has very good use-cases. Eg:

A library that can generate examples based on POJOs or DTOs that does not just default to empty arrays or default values.

Generating XML testing files for SOAP services

Generating JSON files for REST services 

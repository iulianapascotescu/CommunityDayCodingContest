# CommunityDayCodingContest
Community day coding contest

## Java CA coding contest


Write a program that instantiates objects. Implement the following interface: eu.javaca.playground.Instantiable: 

``` public String instantiate(Class any, Target target);  ```

The "instantiate" method can take "any" class type and returns a serialized version of an instance of class "any" (as a JSON string or XML, controlled by the target).

Your solution will be evaluated based on the following criteria:

Completeness. The richer your solution is, the better

Corner case handling. Eg: one of the fields is a List<Animal>. The instantiated solution contains at least one example of each sub types of Animal (let’s say, Dog, Cat, Koala) in the serialized list. Support for parameterized classes, JAXBElement (for xml and so on)

Design. How elegant your solution is. How easy it is to read, extend, understand.


Example:

``` 
MyClass { 

      String name; 

      Integer age; 

      List<Hobbies> hobbies; 

      Book favouriteBook; 

      Workplace<Person> workplace;  

} 
```


Possible JSON results:

``` 
Ex1:
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

### Solution must be posted as a fork to this repo on the 5th of July, 23:00.


### Stretch goal: Take into account validation annotations (XML, javax.validation, etc).

The solution is not just didactic but has very good use-cases. Eg:

A library that can generate examples based on POJOs or DTOs that does not just default to empty arrays or default values.

Generating XML testing files for SOAP services

Generating JSON files for REST services 

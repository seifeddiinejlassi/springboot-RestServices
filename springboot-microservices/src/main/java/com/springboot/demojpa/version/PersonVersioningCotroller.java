package com.springboot.demojpa.version;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningCotroller {

    //Option 1 : URI versioning
    @GetMapping("v1/person")
    public PersonV1 personV1(){
        return new PersonV1("David Beckham");
    }

    @GetMapping("v2/person")
    public PersonV2 personV2(){
        return new PersonV2(new Name("David", "Beckham"));
    }

    // Option 2 : request parameter versioning
    @GetMapping(value = "person/param",params = "version=1")
    public PersonV1 versionV1(){
        return new PersonV1("David Beckham");
    }

    @GetMapping(value = "person/param",params = "version=2")
    public PersonV2 versionV2(){
        return new PersonV2(new Name("David", "Beckham"));
    }

    // Option 3 : header versioning
    @GetMapping(value = "person/header",headers = "X_API_VERSION=1")
    public PersonV1 headerV1(){
        return new PersonV1("David Beckham");
    }

    @GetMapping(value = "person/header",headers = "X_API_VERSION=2")
    public PersonV2 headerV2(){
        return new PersonV2(new Name("David", "Beckham"));
    }

    // Option 4 : accept header versioning (media type versioning)
    @GetMapping(value = "person/producers",
            produces = "application/com.sjlassi.app-v1+json")
    public PersonV1 producesV1(){
        return new PersonV1("David Beckham");
    }

    @GetMapping(value = "person/header",
            produces = "application/com.sjlassi.app-v1+json")
    public PersonV2 producesV2(){
        return new PersonV2(new Name("David", "Beckham"));
    }
}

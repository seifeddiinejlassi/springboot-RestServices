package com.springboot.demojpa.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    //field1,field2
    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean(){
        SomeBean someBean = new SomeBean("val1", "val2", "val3");
        
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("filed1","field2");
        
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("SomeBeanFilter",filter);
        
        MappingJacksonValue mapping = new MappingJacksonValue(someBean);
        mapping.setFilters(filters);
        
        return mapping;
    }

    //field2,field3
    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveListOfSomeBean(){
        List<SomeBean> someBeans = Arrays.asList(new SomeBean("val1", "val2", "val3"),
                new SomeBean("val12", "val22", "val32"));

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("filed2","field3");

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("SomeBeanFilter",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(someBeans);
        mapping.setFilters(filters);

        return mapping;
    }
}

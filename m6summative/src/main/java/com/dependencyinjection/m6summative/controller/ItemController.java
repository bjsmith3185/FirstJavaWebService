package com.dependencyinjection.m6summative.controller;

import com.dependencyinjection.m6summative.dto.Item;
import com.dependencyinjection.m6summative.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ServiceLayer service;

    @RequestMapping(value="/item", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Item createItem(@RequestBody Item item) {
        return service.saveItem(item);
    }

    @RequestMapping(value="/item/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Item getItemById(@PathVariable(name="id") int id ) {
        return service.findItem(id);
    }

    @RequestMapping(value="/items", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getAllItems() {
        return service.findAllItems();
    }

    @RequestMapping(value="/item/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(@RequestBody Item item, @PathVariable(name = "id") int id ) {
        if (id != item.getItemId()) {
            throw new IllegalArgumentException("Item ID on path must match the ID in the Item object.");
        }
        service.updateItem(item);
    }

    @RequestMapping(value="/item/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable(name="id") int id ) {
        service.removeItem(id);
    }


}

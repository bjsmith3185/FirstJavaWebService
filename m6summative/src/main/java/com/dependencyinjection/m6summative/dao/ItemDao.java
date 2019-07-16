package com.dependencyinjection.m6summative.dao;


import com.dependencyinjection.m6summative.dto.Item;

import java.util.List;

public interface ItemDao {

    Item getItem(int id );

    List<Item> getAllItems();

    Item addItem( Item item );

    void updateItem( Item item );

    void deleteItem( int id );

}

package com.dependencyinjection.m6summative.dao;


import com.dependencyinjection.m6summative.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("itemDao")
public class ItemDaoJdbsTemplateImpl implements ItemDao {

    // Prepared statement strings
    private static final String INSERT_ITEM_SQL =
            "insert into item (name, description, daily_rate) values (?, ?, ?)";

    private static final String SELECT_ITEM_SQL =
            "select * from item where item_id = ?";

    private static final String SELECT_ALL_ITEM_SQL =
            "select * from item";

    private static final String DELETE_ITEM_SQL =
            "delete from item where item_id = ?";

    private static final String UPDATE_ITEM_SQL =
            "update item set name = ?, description = ?, daily_rate = ? where item_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemDaoJdbsTemplateImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item getItem(int id) {

        try {

            return jdbcTemplate.queryForObject(SELECT_ITEM_SQL, this::mapRowToItem, id);

        } catch (EmptyResultDataAccessException e) {
            // if nothing is returned just catch the exception and return null
            return null;
        }

    }

    @Override
    public List<Item> getAllItems() {

        return jdbcTemplate.query(SELECT_ALL_ITEM_SQL, this::mapRowToItem);
    }

    @Override
    @Transactional
    public Item addItem(Item item) {

        jdbcTemplate.update(INSERT_ITEM_SQL,
                item.getName(),
                item.getDescription(),
                item.getDailyRate() );

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        item.setItemId(id);

        return item;
    }

    @Override
    public void updateItem(Item item) {

        jdbcTemplate.update(UPDATE_ITEM_SQL,
                item.getName(),
                item.getDescription(),
                item.getDailyRate(),
                item.getItemId() );
    }

    @Override
    public void deleteItem(int id) {

        jdbcTemplate.update(DELETE_ITEM_SQL, id);
    }



    // Helper methods
    private Item mapRowToItem(ResultSet rs, int rowNum) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setDailyRate(rs.getDouble("daily_rate"));

        return item;
    }
}

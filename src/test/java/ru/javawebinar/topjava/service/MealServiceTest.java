package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import static ru.javawebinar.topjava.MealTestData.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
    dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL1_ID,USER_ID1);
        MATCHER.assertEquals(MEAL1,meal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL1_ID,USER_ID1);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL2),service.getAll(USER_ID2));
    }

    @Test
    public void testGetBetweenDates() throws Exception {

    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(),"admin_supper",700);
        Meal saved = service.save(newMeal,USER_ID1);
       // service.save(MEAL1,USER_ID1);
        newMeal.setId(saved.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL1,newMeal),service.getAll(USER_ID1));
    }
}
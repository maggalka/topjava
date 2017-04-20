package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public class MealTestData {

    public static final int MEAL1_ID = 5000;
    public static final int MEAL2_ID = 5001;

    public static final Meal MEAL1 = new Meal(MEAL1_ID,LocalDateTime.of(2017, 4, 18, 10, 0),"admin_breakfast",500);
    public static final Meal MEAL2 = new Meal(MEAL2_ID,LocalDateTime.of(2017,4,18,14,0),"user_supper",1000);

    public static final int USER_ID1 = 100001;
    public static final int USER_ID2 = 100000;

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();

}

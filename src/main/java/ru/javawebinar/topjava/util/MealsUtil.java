package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> meals = mealListFilling();
        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

    public static final List<Meal> mealListFilling() {
        return Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 29, 9, 0), "Завтрак", 300),
                new Meal(LocalDateTime.of(2015, Month.MAY, 29, 14, 0), "Обед", 1500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 29, 21, 0), "Ужин", 600),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 600),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 900),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 700),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1200),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 600)
        );
    }

    public static List<MealWithExceed> mapListOfMealsToListOfMealsWithExceed(List<Meal> meals, int caloriesPerDay){
        final Map<LocalDate,Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(),meal.getCalories(), Integer::sum));
        return meals.stream()
                .map(m -> createWithExceed(m, caloriesSumByDate.get(m.getDate())>caloriesPerDay))
                .collect(Collectors.toList());
    }

}
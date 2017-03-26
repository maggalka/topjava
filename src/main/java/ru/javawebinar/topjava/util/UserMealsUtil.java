package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> userMealWithExceeds =  getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        userMealWithExceeds.stream().
                forEach(System.out::println);

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        //List<LocalDate> allDates = mealList.stream().map(meal -> meal.getDateTime()).map(dateTime -> dateTime.toLocalDate()).distinct().peek(m -> System.out.println(m)).collect(Collectors.toList());

        List<LocalDate> allDates = mealList.stream()
                .map(UserMeal::getDateTime)
                .map(LocalDateTime::toLocalDate)
                .distinct()
                .collect(Collectors.toList());
        for (LocalDate date : allDates)
        {
            List<UserMeal> mealForThisDate = mealList.stream()
                    .filter(m -> (m.getDateTime().toLocalDate().equals(date)))
                    .filter( userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(),startTime,endTime))
                    .collect(Collectors.toList());
            int calories = mealList.stream()
                    .filter(userMeal -> (userMeal.getDateTime().toLocalDate().equals(date)))
                    .mapToInt(UserMeal::getCalories)
                    .sum();
            boolean isExceeded=false;
            if (calories>caloriesPerDay)
                isExceeded=true;
            for (UserMeal meal : mealForThisDate)
                userMealWithExceeds.add(new UserMealWithExceed(meal,isExceeded));

        }
        return userMealWithExceeds;
    }
}


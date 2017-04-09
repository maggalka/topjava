package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    @Autowired
    private InMemoryUserRepositoryImpl userRepository;

    public Meal create(Meal meal){
        checkNew(meal);
        return service.save(meal);
    }

    public void update(Meal meal, int id){
        checkIdConsistent(meal,id);
        service.update(meal);
    }

    public void delete(int id, int userId){
        service.delete(id,userId);
    }

    public Meal get(int id, int userId){
        return service.get(id, userId);
    }

    public List<MealWithExceed> getAll(int userId){
        return getFilteredbyTime(userId, LocalTime.MIN, LocalTime.MAX);
    }

    public List<MealWithExceed> getFilteredbyTime(int userId, LocalTime startTime, LocalTime endTime){

        return getFilteredByDayAndTime(userId,LocalDate.MIN,LocalDate.MAX,startTime,endTime);
    }

    public List<MealWithExceed> getFilteredByDay(int userId, LocalDate startDate, LocalDate endDate){
        return getFilteredByDayAndTime(userId,startDate,endDate,LocalTime.MIN,LocalTime.MAX);
    }

    public List<MealWithExceed> getFilteredByDayAndTime(int userId, LocalDate startDate, LocalDate endDate,
                                                        LocalTime startTime, LocalTime endTime){
        if (startDate==null) startDate=LocalDate.MIN;
        if (endDate==null) endDate=LocalDate.MAX;
        if (startTime==null) startTime=LocalTime.MIN;
        if (endTime==null) endTime=LocalTime.MAX;
        int caloriesPerDayForUserId = userRepository.get(userId).getCaloriesPerDay();
        return MealsUtil.getFilteredWithExceeded(service.getByDate(userId,startDate,endDate),
                startTime,endTime,caloriesPerDayForUserId);
    }


}
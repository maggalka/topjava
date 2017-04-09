package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealServiceImpl implements MealService {

   // @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFound(repository.delete(id, userId), "id " + id + "for userId " + userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFound(repository.get(id, userId), "id " + id + "for userId " + userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return checkNotFound(new ArrayList<>(repository.getAll(userId)), "userId " + userId);
    }

    @Override
    public List<Meal> getByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return checkNotFound(new ArrayList<>(repository.getByDate(userId,startDate,endDate)),
                "userId " + userId+", startDate = "+startDate+" , endDate = "+endDate);
    }

    @Override
    public void update(Meal meal) {
        repository.save(meal);
    }

//    public void setRepository(ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl repository) {
//    }
}
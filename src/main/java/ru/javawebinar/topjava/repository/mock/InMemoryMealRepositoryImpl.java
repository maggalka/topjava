package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        if ((repository.containsKey(id))&&(isMealUserIdMatchedUserId(id, userId)))
        {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if ((repository.containsKey(id))&&(isMealUserIdMatchedUserId(id, userId)))
            return repository.get(id);
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        if (repository.values().isEmpty())
            return Collections.emptyList();
        List<Meal> allMealOfUserId = repository.values().stream()
                .filter(m -> isMealUserIdMatchedUserId(m.getId(), userId))
                .collect(Collectors.toList());
        Collections.sort(allMealOfUserId,(m1,m2)->m2.getDateTime().compareTo(m1.getDateTime()));
        return allMealOfUserId;
    }

    @Override
    public Collection<Meal> getByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(),startDate,endDate))
                .collect(Collectors.toList());
    }

    private boolean isMealUserIdMatchedUserId(int id, int userId) {
        int mealUserId = repository.get(id).getUserId();
        return userId == mealUserId;
    }
}


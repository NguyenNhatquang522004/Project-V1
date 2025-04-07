package com.example.my_app.custom.Helper;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class Helper {

    public <T, D> Optional<T> handleFindOne(JpaRepository<T, D> repository, D id) {
        return repository.findById(id);

    }

    public <T, ID> Optional<T> handleFindList(Collection<T> data, Function<T, ID> extract, ID id) {
        return data.stream().filter(value -> extract.apply(value).equals(id)).findFirst();
    }

    public <T> Optional<T> handlefind(String email, Function<String, Optional<T>> finder) throws Exception {
        return finder.apply(email);
    }
}

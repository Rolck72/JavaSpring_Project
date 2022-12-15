package com.example.myproject.services;

import com.example.myproject.repositories.PersonRepository;
import com.example.myproject.models.Person;
import com.example.myproject.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// данный класс помогает на аутентифицировать наших пользователей
@Service
public class PersonDetailsService implements UserDetailsService {

    // подключаем наш интерфейс для работы с БД
    private final PersonRepository personRepository;

    @Autowired // автоматическое подклюние
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    // если польозоаватель пустой то мы выбрасываем исключение
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Получаем пользователя из таблицы person по логину с формы аутентификации
        Optional<Person> person = personRepository.findByLogin(username);

        // Если пользователь не был найден
        if(person.isEmpty()){
            // Выбрасываем исключение что данный пользователь не найден
            // Данное исключение будет поймано спринг секьюрити и сообщение будет выведено на страницу
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        // Возвращаем объект пользователя
        return new PersonDetails(person.get());
    }
}

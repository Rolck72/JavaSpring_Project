package com.example.myproject.security;


// данный класс работаем с пользоватем и определяет какие права доступны

import com.example.myproject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {

    private final Person person;     // внедням наш класс

    @Autowired
    public PersonDetails(Person person) {
        this.person = person;
    }

    // предоставляет информацию о пользователе
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // добавляем коллукцию с одной ролью
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }


    // позволяет получить пароль пользователя
    @Override
    public String getPassword() {
        return this.person.getPassword();    // получам пароль класса person
    }

    // позволяет получить логин пользователя
    @Override
    public String getUsername() {
        return this.person.getLogin();      // login person
    }

    // указывает аккаунте действителен
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // указывает не заблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // пароль является действительным/валидным
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // аккаунт активный
    @Override
    public boolean isEnabled() {
        return true;
    }

    // получаем сразу всегл пользователя
    public Person getPerson(){
        return this.person;
    }

}

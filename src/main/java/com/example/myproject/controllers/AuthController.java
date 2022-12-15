package com.example.myproject.controllers;

import com.example.myproject.services.PersonService;
import com.example.myproject.services.ProductService;
import com.example.myproject.util.PersonValidator;
import com.example.myproject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
// http:localhost:8080/auth/login
// данный класс будет являтся контороллером
@Controller
@RequestMapping("/auth")   //  @RequestMapping HTTP-метод
public class AuthController {

    private final PersonValidator personValidator;
    private final PersonService personService;
    private final ProductService productService;

    @Autowired
    public AuthController(PersonValidator personValidator, PersonService personService, ProductService productService) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.productService = productService;
    }

    //===============================================
    @GetMapping("/indexMain")
    public String indexMain() {

        return "indexMain";
    }
    //===============================================

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("person", new Person());
        return "registration";
    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        personService.register(person);
        return "redirect:/auth/login";
    }

    //=================================================================================================

    @GetMapping("/updatePassword")
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public String changeUserPassword(Model model) {
        model.addAttribute("person", new Person());
        return "renamePassword";
    }
    @PostMapping("/updatePassword")
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public String updatePassword(@ModelAttribute("person")  Person person, BindingResult bindingResult){
        Person person_db = personService.getPersonFindByLogin(person);
        int id = person_db.getId();
        String password= person.getPassword();

        personService.updatePassword(id,password);
        return "redirect:/index";
    }

    public ProductService getProductService() {
        return productService;
    }


}

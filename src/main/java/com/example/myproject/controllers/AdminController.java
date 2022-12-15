package com.example.myproject.controllers;

import com.example.myproject.models.Person;
import com.example.myproject.repositories.CategoryRepository;
import com.example.myproject.repositories.OrderRepository;
import com.example.myproject.security.PersonDetails;
import com.example.myproject.services.OrderService;
import com.example.myproject.services.PersonService;
import com.example.myproject.util.ProductValidator;
import com.example.myproject.models.Image;
import com.example.myproject.models.Product;
import com.example.myproject.services.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Value("${upload.path}")
    private String uploadPath;


    private final ProductValidator productValidator;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;
   private final OrderRepository orderRepository;
    private final PersonService personService;

    private final OrderService orderService;

    public AdminController(ProductValidator productValidator, ProductService productService, CategoryRepository categoryRepository, OrderRepository orderRepository, PersonService personService, OrderService orderService) {
        this.productValidator = productValidator;
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.personService = personService;
        this.orderService = orderService;
    }


    @GetMapping()
    public String admin(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        String role = personDetails.getPerson().getRole();

        if(role.equals("ROLE_USER")){
            return "redirect:/index";
        }
        // Добавляем лист всех наших продуктов
        model.addAttribute("products", productService.getAllProduct());
        return "admin/admin";
    }

    // Метод по отображению формы добавление
    @GetMapping("/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category",categoryRepository.findAll());

        return "product/addProduct";
    }

    // Метод по добавлению обьекта с формы в таблицу product
    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product")
                                 @Valid Product product, BindingResult bindingResult,
                             @RequestParam("file_one")   MultipartFile file_one,
                             @RequestParam("file_two")   MultipartFile file_two,
                             @RequestParam("file_three") MultipartFile file_three,
                             @RequestParam("file_four")  MultipartFile file_four,
                             @RequestParam("file_five")  MultipartFile file_five) throws IOException {

                  productValidator.validate(product, bindingResult);
                  if(bindingResult.hasErrors()){
                      return "product/addProduct";
                  }
            // Проверака на пустоту файла
            if(file_one != null){
                // Дирректория по сохранению файла
                File uploadDir = new File(uploadPath);
                // Если данной дирректории по пути не сущетсвует
                if(uploadDir.exists()){
                    // Создаем данную дирректорию
                    uploadDir.mkdir();
                }
                // Создаем уникальное имя файла
                // UUID представляет неищменный универсальный уникальный идентификатор
                String uuidFile = UUID.randomUUID().toString();
                // file_one.getOriginalFilename() - наименование файла с формы
                String resultFileName = uuidFile + "."+ file_one.getOriginalFilename();
                // Загружаем файл по указаннопу пути
                file_one.transferTo(new File(uploadPath + "/" + resultFileName));
                Image image = new Image();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageProduct(image);

            }
  // ======================================================================================
            if(file_two != null){
                // Дирректория по сохранению файла
                File uploadDir = new File(uploadPath);
                // Если данной дирректории по пути не сущетсвует
                if(uploadDir.exists()){
                    // Создаем данную дирректорию
                    uploadDir.mkdir();
                }
                // Создаем уникальное имя файла
                // UUID представляет неищменный универсальный уникальный идентификатор
                String uuidFile = UUID.randomUUID().toString();
                // file_one.getOriginalFilename() - наименование файла с формы
                String resultFileName = uuidFile + "."+ file_two.getOriginalFilename();
                // Загружаем файл по указаннопу пути
                file_two.transferTo(new File(uploadPath + "/" + resultFileName));
                Image image = new Image();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageProduct(image);

            }

     // ======================================================================================
            if(file_three != null){
                // Дирректория по сохранению файла
                File uploadDir = new File(uploadPath);
                // Если данной дирректории по пути не сущетсвует
                if(uploadDir.exists()){
                    // Создаем данную дирректорию
                    uploadDir.mkdir();
                }
                // Создаем уникальное имя файла
                // UUID представляет неищменный универсальный уникальный идентификатор
                String uuidFile = UUID.randomUUID().toString();
                // file_one.getOriginalFilename() - наименование файла с формы
                String resultFileName = uuidFile + "."+ file_three.getOriginalFilename();
                // Загружаем файл по указаннопу пути
                file_three.transferTo(new File(uploadPath + "/" + resultFileName));
                Image image = new Image();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageProduct(image);

            }

     // ======================================================================================

            if(file_four != null){
                // Дирректория по сохранению файла
                File uploadDir = new File(uploadPath);
                // Если данной дирректории по пути не сущетсвует
                if(uploadDir.exists()){
                    // Создаем данную дирректорию
                    uploadDir.mkdir();
                }
                // Создаем уникальное имя файла
                // UUID представляет неищменный универсальный уникальный идентификатор
                String uuidFile = UUID.randomUUID().toString();
                // file_one.getOriginalFilename() - наименование файла с формы
                String resultFileName = uuidFile + "."+ file_four.getOriginalFilename();
                // Загружаем файл по указаннопу пути
                file_four.transferTo(new File(uploadPath + "/" + resultFileName));
                Image image = new Image();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageProduct(image);

            }
            // ======================================================================================

            if(file_five != null){
                // Дирректория по сохранению файла
                File uploadDir = new File(uploadPath);
                // Если данной дирректории по пути не сущетсвует
                if(uploadDir.exists()){
                    // Создаем данную дирректорию
                    uploadDir.mkdir();
                }
                // Создаем уникальное имя файла
                // UUID представляет неищменный универсальный уникальный идентификатор
                String uuidFile = UUID.randomUUID().toString();
                // file_one.getOriginalFilename() - наименование файла с формы
                String resultFileName = uuidFile + "."+ file_five.getOriginalFilename();
                // Загружаем файл по указаннопу пути
                file_five.transferTo(new File(uploadPath + "/" + resultFileName));
                Image image = new Image();
                image.setProduct(product);
                image.setFileName(resultFileName);
                product.addImageProduct(image);

            }


            productService.saveProduct(product);
            return "redirect:/admin";
        }


    // ==========================================================================================================
    // Метод по удалению товара по id
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id)
    {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    // ==========================================================================================================
    // Метод по получению товара по id и отображение шаблона редактирования

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("editProduct", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    // ==========================================================================================================
    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("editProduct") Product product, @PathVariable("id") int id){
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

    //===============================================================================================================


    @GetMapping("/person")
    public String person(Model model){;
        model.addAttribute("person", personService.getAllPerson());
        return "person/person";
    }

    // ==========================================================================================================
    // Метод возвращает страницу с подробной информацией о пользователе
    @GetMapping("/person/info/{id}")
    public String infoPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.getPersonById(id));
        return "person/personInfo";
    }

    // ==========================================================================================================
    // Метод возвращает страницу с формой редактирования пользователя и помещает в модель объект редактируемого пользователя по id
    @GetMapping("/person/edit/{id}")
    public String editPerson(@PathVariable("id")int id, Model model){
        model.addAttribute("editPerson", personService.getPersonById(id));
        return "person/editPerson";
    }

    // ==========================================================================================================
    // Метод принимает объект с формы и обновляет пользователя
    @PostMapping("/person/edit/{id}")
    public String editPerson(@ModelAttribute("editPerson") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "person/editPerson";
        }
        personService.updatePerson(id, person);
        return "redirect:/admin/person";
    }

    // ==========================================================================================================

    // Метод по удалению пользователей
    @GetMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personService.deletePerson(id);
        return "redirect:/admin/person";
    }
    // ==========================================================================================================
    // Метод по нажатию на кнопку поиска и сортировки и отображение шаблона
    @GetMapping("/person/sorting_and_searching_and_filters")
    public String sorting_and_searching_and_filters(){
        return "/person/SortingAndSearchingAndFilters";
    }

    @PostMapping("/person/sorting_and_searсhing_and_filters")
    public String sorting_and_searching_and_filters(@RequestParam("SortingAndSearchingAndFiltersOptions")
                                                    String sortingAndSearchingAndFiltersOptions, @RequestParam("value") String value, Model model){
        if(sortingAndSearchingAndFiltersOptions.equals("email")){
            model.addAttribute("person",personService.getPersonEmail(value));
        } else if (sortingAndSearchingAndFiltersOptions.equals("phone_number")) {
            model.addAttribute("person",personService.getPersonPhoneNumber(value));
        } else if (sortingAndSearchingAndFiltersOptions.equals("last_name_and_sort_birthday")) {
            model.addAttribute("person",personService.getPersonLastNameOrderByBirthday(value));
        } else if (sortingAndSearchingAndFiltersOptions.equals("last_name_start")) {
            model.addAttribute("person",personService.getPersonLastNameStartingWith(value));
        }
        return "person/SortingAndSearchingAndFilters";
    }


    // 5 ==========================================================================================================
    // Метод по выводу заказов
    @GetMapping("/ordersList")
    public String showOrders(Model model){
        model.addAttribute("persons", personService.getAllPerson());
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/ordersList";
    }


    // 6 ==========================================================================================================
    // Метод по удалению заказа

    @GetMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id){
        orderService.deleteOrder(id);
        return "redirect:/admin";
    }

    // 7 ==========================================================================================================
    // Метод по поиску заказа

    @GetMapping("/order/search")
    public String searchOrder(){return "admin/ordersList";}


     @PostMapping("/orderList/search")
    public String searchOrder(@RequestParam("search") String search, Model model){
        if(search.length()>4){
            search =search.substring(search.length() -4);
        }
        model.addAttribute("order", orderService.findByLastFourCharacters(search));
        return "admin/searchPerson";
     }

    // ==========================================================================================================






   // ==========================================================================================================

//    @PostMapping("/orderList/search")
//    public String numberSearch(@RequestParam("search") String search, Model model){
//        model.addAttribute("search_number", orderRepository.findByNumber(search));
//        model.addAttribute("value_search",search);
//        model.addAttribute("orders", orderRepository.findAll());
//        return "admin/orderList";
//    }
// 7 ==========================================================================================================
    // Метод по поиску заказа

}


package com.example.myproject.controllers;
import com.example.myproject.repositories.ProductRepository;
import com.example.myproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/product")
public class MainController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public MainController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    // Данный метод предназначен для отображении товаров без прохождения аутентификации и авторизации
    @GetMapping("")
    public String getAllProduct(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "product/product";
    }

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search,
                                @RequestParam("ot") String ot, @RequestParam("do") String Do,
                                @RequestParam(value = "price", required = false, defaultValue = "") String price,
                                @RequestParam(value = "category", required = false, defaultValue = "")
                                String category, Model model){
        System.out.println(search);
        System.out.println(ot);
        System.out.println(Do);
        System.out.println(price);
        System.out.println(category);
        // Если диапазон цен от и до не пустой
        if(!ot.isEmpty() & !Do.isEmpty()) {
            // Если сортировка по цене выбрана
            if (!price.isEmpty()) {
                // Если в качестве сортировки выбрана сортировкам по возрастанию
                if (price.equals("sorted_by_ascending_price")) {
                    // Если категория товара не пустая
                    if (!category.isEmpty()) {
                        // Если категория равная мебели
                        if (category.equals("furniture")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                            // Если категория равная бытовой техники
                        } else if (category.equals("appliances")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                            // Если категория равная одежде
                        } else if (category.equals("clothes")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        }
                        // Если категория не выбрана
                    } else {
                        model.addAttribute("search_product", productRepository.findByTitleOrderByPrice(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                    }
                    // Если в качестве сортировки выбрана сортировкам по убыванию
                } else if (price.equals("sorted_by_descending_price")) {

                    if (!category.isEmpty()) {
                        // Если категория равная мебели
                        if (category.equals("furniture")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                            // Если категория равная бытовой техники
                        } else if (category.equals("appliances")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                            // Если категория равная одежде
                        } else if (category.equals("clothes")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        }
                        // Если категория не выбрана
                    }
                    else {
                        model.addAttribute("search_product", productRepository.findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                    }
                }
            }
            else {
                model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
            }
        } else {
            model.addAttribute("search_product",productRepository.findByTitleContainingIgnoreCase(search));
        }
        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);
        model.addAttribute("products", productService.getAllProduct());
        return "/product/product";


    }
//==============================================================================================================================================

    @PostMapping("/searchUser")
    public String productSearchUser(@RequestParam("searchUser") String searchUser,
                                    @RequestParam("otUser") String otUser, @RequestParam("doUser") String doUser,
                                    @RequestParam(value = "price", required = false, defaultValue = "") String price,
                                    @RequestParam(value = "category", required = false, defaultValue = "")
                                    String category, Model model){
        System.out.println(searchUser);
        System.out.println(otUser);
        System.out.println(doUser);
        System.out.println(price);
        System.out.println(category);
        // Если диапазон цен от и до не пустой
        if(!otUser.isEmpty() & !doUser.isEmpty()) {
            // Если сортировка по цене выбрана
            if (!price.isEmpty()) {
                // Если в качестве сортировки выбрана сортировкам по возрастанию
                if (price.equals("sorted_by_ascending_price")) {
                    // Если категория товара не пустая
                    if (!category.isEmpty()) {
                        // Если категория равная мебели
                        if (category.equals("furniture")) {
                            model.addAttribute("searchUser_productUser", productRepository.findByTitleAndCategoryOrderByPrice(searchUser.toLowerCase(), Float.parseFloat(otUser), Float.parseFloat(doUser), 1));
                            // Если категория равная бытовой техники
                        } else if (category.equals("appliances")) {
                            model.addAttribute("searchUser_productUser", productRepository.findByTitleAndCategoryOrderByPrice(searchUser.toLowerCase(), Float.parseFloat(otUser), Float.parseFloat(doUser), 2));
                            // Если категория равная одежде
                        } else if (category.equals("clothes")) {
                            model.addAttribute("searchUser_productUser", productRepository.findByTitleAndCategoryOrderByPrice(searchUser.toLowerCase(), Float.parseFloat(otUser), Float.parseFloat(doUser), 3));
                        }
                        // Если категория не выбрана
                    } else {
                        model.addAttribute("searchUser_productUser", productRepository.findByTitleOrderByPrice(searchUser.toLowerCase(), Float.parseFloat(otUser), Float.parseFloat(doUser)));
                    }
                    // Если в качестве сортировки выбрана сортировкам по убыванию
                } else if (price.equals("sorted_by_descending_price")) {

                    if (!category.isEmpty()) {
                        // Если категория равная мебели
                        if (category.equals("furniture")) {
                            model.addAttribute("searchUser_productUser", productRepository.findByTitleAndCategoryOrderByPriceDesc(searchUser.toLowerCase(), Float.parseFloat(otUser), Float.parseFloat(doUser), 1));
                            // Если категория равная бытовой техники
                        } else if (category.equals("appliances")) {
                            model.addAttribute("searchUser_productUser", productRepository.findByTitleAndCategoryOrderByPriceDesc(searchUser.toLowerCase(), Float.parseFloat(otUser), Float.parseFloat(doUser), 2));
                            // Если категория равная одежде
                        } else if (category.equals("clothes")) {
                            model.addAttribute("searchUser_productUser", productRepository.findByTitleAndCategoryOrderByPriceDesc(searchUser.toLowerCase(), Float.parseFloat(otUser), Float.parseFloat(doUser), 3));
                        }
                    }
                    else { // Если категория не выбрана
                        model.addAttribute("searchUser_productUser", productRepository.findByTitleOrderByPriceDesc(searchUser.toLowerCase(), Float.parseFloat(otUser), Float.parseFloat(doUser)));
                    }
                }
            }
            else {
                model.addAttribute("searchUser_productUser", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(searchUser.toLowerCase(), Float.parseFloat(otUser), Float.parseFloat(doUser)));
            }
        } else {
            model.addAttribute("searchUser_productUser",productRepository.findByTitleContainingIgnoreCase(searchUser));
        }
        model.addAttribute("value_searchUser", searchUser);
        model.addAttribute("value_price_otUser", otUser);
        model.addAttribute("value_price_doUser", doUser);
        model.addAttribute("products", productService.getAllProduct());
        return "/user/index";

    }
 }










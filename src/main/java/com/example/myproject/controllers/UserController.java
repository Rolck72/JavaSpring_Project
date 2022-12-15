package com.example.myproject.controllers;

import com.example.myproject.enumm.Status;
import com.example.myproject.models.Order;
import com.example.myproject.repositories.OrderRepository;
import com.example.myproject.repositories.ProductRepository;
import com.example.myproject.security.PersonDetails;
import com.example.myproject.models.Cart;
import com.example.myproject.models.Product;
import com.example.myproject.repositories.CartRepository;
import com.example.myproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class UserController {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    public UserController(OrderRepository orderRepository, CartRepository cartRepository, ProductService productService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    // 1 ==========================================================================================================
    @GetMapping("/index")
    public String index(Model model){

        // делаем разграничение
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Преобразовываем обьект индификации в специальный обьект класса
        // по работе с пользователями
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        String role = personDetails.getPerson().getRole();

        if(role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }
        model.addAttribute("products", productService.getAllProduct());
        return "user/index";
    }
    // 2 =========================================================================================================

    // добавить товар в корзину
    @GetMapping("/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model){
        Product product = productService.getProductId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int id_person = personDetails.getPerson().getId();
        Cart cart = new Cart(id_person, product.getId());
        cartRepository.save(cart);
        return "redirect:/cart";
    }
    // 3 ==========================================================================================================
    @GetMapping("/cart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int id_person = personDetails.getPerson().getId();
        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productList = new ArrayList<>();
        for (Cart cart: cartList) {
            productList.add(productService.getProductId(cart.getProductId()));
        }

        float price = 0;
        for (Product product: productList) {
            price += product.getPrice();
        }

        model.addAttribute("price", price);
        model.addAttribute("cart_product", productList);
        return "user/cart";
    }

    // 4 ==========================================================================================================
    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteProductFromCart(Model model, @PathVariable("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        int id_person = personDetails.getPerson().getId();
        cartRepository.deleteCartById(id, id_person);
        return "redirect:/cart";
    }

    // 5 ==========================================================================================================
    // оформление заказа
    @GetMapping("/order/create")
    public String createOrder()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productList = new ArrayList<>();

        for (Cart cart: cartList)
        {
            productList.add(productService.getProductId(cart.getProductId()));
        }
        String uuid = UUID.randomUUID().toString();

        for(Product product: productList)
        {
            Order newOrder = new Order(uuid, 1, product.getPrice(), Status.Оформлен,
                    product, personDetails.getPerson());
             orderRepository.save(newOrder);
             cartRepository.deleteCartById(product.getId(), id_person);
        }
        return "redirect:/orders";

    }


    // 6 ==========================================================================================================
    // заказ
      @GetMapping("/orders")
    public String ordersUser (Model model)
      {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
          List<Order> orderList = orderRepository.findByPerson(personDetails.getPerson());
          model.addAttribute("orders", orderList);
          return "/user/orders";
      }

     //==========================================================================================================







    // 6 ==========================================================================================================
}

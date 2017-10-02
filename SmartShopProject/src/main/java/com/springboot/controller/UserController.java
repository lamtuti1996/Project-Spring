package com.springboot.controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.common.Common;
import com.springboot.dao.CategoryProductRespository;
import com.springboot.dao.OrderRespository;
import com.springboot.entity.CartInfo;
import com.springboot.entity.CartLineInfo;
import com.springboot.entity.Customer;
import com.springboot.entity.NewsCreate;
import com.springboot.entity.ProductInfo;
import com.springboot.entity.RegisterUser;
import com.springboot.model.CategoriesNews;
import com.springboot.model.CategoriesProduct;
import com.springboot.model.News;
import com.springboot.model.OrderDetail;
import com.springboot.model.Orders;
import com.springboot.model.Products;
import com.springboot.model.Role;
import com.springboot.model.Users;
import com.springboot.utils.Util;
import com.springboot.service.CategoryNewsService;
import com.springboot.service.CategoryProductService;
import com.springboot.service.NewsService;
import com.springboot.service.OrderDetailService;
import com.springboot.service.OrderService;
import com.springboot.service.ProductService;
import com.springboot.service.RoleService;
import com.springboot.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private NewsService newsService;

	@Autowired
	private CategoryNewsService categoryNewsService;

	@Autowired
	private CategoryProductService categoryProductService;

	@Autowired
	private UserDetailsService userDetailService;
	
	@GetMapping("/login")
	public String setLogin(ModelMap model) {
		//Users u = new Users();
		//model.addAttribute("user", u);
		return "login";

	}
	
	@PostMapping("/test1")
	public String test1(ModelMap model,@RequestParam("email") String email,@RequestParam("password") String password) {
		UserDetails u=userDetailService.loadUserByUsername(email);
		if(u.getPassword().equals(password)) {
			return "redirect:/listProduct";
			
		}else {
			return "login";
		}
		

	}

	@GetMapping("/setRegister")
	public String setRegister(ModelMap model) {
		RegisterUser ru = new RegisterUser();
		model.addAttribute("rUser", ru);

		return "Register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("rUser") RegisterUser ru, BindingResult result, ModelMap model)
			throws ParseException {
		if (result.hasErrors()) {

			model.addAttribute("message", "Vui lòng sửa các lỗi sau đây !");

			System.out.println(result.getAllErrors().toString());

			return "redirect:/setRegister";
		} else {

			Users u = new Users();

			HashSet<Role> roles = new HashSet<>();
			roles.add(roleService.findRoleByName("ROLE_MEMBER"));

			u.setEmail(ru.getEmail());
			u.setPassword(ru.getPassword());
			u.setCreatedTime(Common.CurrentDateTime());
			u.setRoles(roles);
			// userService.saveUser(u);
			return "redirect:/";

		}

	}

	@PostMapping("/checkLogin")
	public String checkLogin(@Valid @ModelAttribute("user") Users u, BindingResult result, ModelMap model)
			throws ParseException {
		if (result.hasErrors()) {

			model.addAttribute("message", "Vui lòng sửa các lỗi sau đây !");

			System.out.println(result.getAllErrors().toString());
			Users user = new Users();
			model.addAttribute("user", u);
			return "Login";
		} else {
			// Users check = userService.findByEmailAndPass(u.getEmail(), u.getPassword());
			// if (check != null) {
			if (true) {
				// check.setLastAccess(Common.CurrentDateTime());
				// userService.saveUser(check);
				return "redirect:/listProduct";

			} else {

				Users user = new Users();
				model.addAttribute("user", u);
				model.addAttribute("message", "Email and Password is not  !");
				return "Login";

			}
		}

	}

	@GetMapping("/listProduct")
	public String listProduct(ModelMap model, Pageable pageable) {

		List<CategoriesProduct> categoryProduct = categoryProductService.findAll();

		Page<Products> listProduct = productService.findAll(pageable);

		PageWrapper<Products> page = new PageWrapper<Products>(listProduct, "/listProduct");
		model.addAttribute("listProduct", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("categoryProduct", categoryProduct);
		return "Home";

	}

	@GetMapping("/searchCategoryProduct")
	public String searchProductByCategoryID(ModelMap model, Pageable pageable, @RequestParam("categoryID") int id) {
		List<CategoriesProduct> categoryProduct = categoryProductService.findAll();
		Page<Products> listProduct = productService.findAddByCategoryID(id, pageable);
		PageWrapper<Products> page = new PageWrapper<Products>(listProduct, "/listProduct");
		model.addAttribute("listProduct", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("categoryProduct", categoryProduct);
		return "Home";
	}

	@GetMapping("/searcProduct/{id}")
	public String searchProduct(@PathVariable("id") int id, ModelMap model) {
		Products p = productService.findOneByID(id);
		model.addAttribute("product", p);

		return "ProductDetail";

	}

	@GetMapping("/checkQuanity")
	@ResponseBody
	public boolean checkQuanity(@RequestParam("productID") int id, @RequestParam("quanity") int quanity) {
		Products p = productService.findOneByID(id);
		if (p.getQuality() < quanity) {

			return false;
		} else {

			return true;
		}

	}

	@GetMapping("buyProduct")
	public String buyProduct(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "id", defaultValue = "-1") String xID,
			@RequestParam(value = "quanity", defaultValue = "-1") String xQuanity) {
		int id = Integer.parseInt(xID);
		int quanity = Integer.parseInt(xQuanity);
		Products p = null;
		if (id >= 0) {
			p = productService.findOneByID(id);

		}
		if (p != null) {

			CartInfo cartInfo = Util.getCartInSession(request);
			ProductInfo pInfo = new ProductInfo(p);
			cartInfo.addProduct(pInfo, 1);
		}

		return null;
	}

	@GetMapping("removeProduct")
	public String removeProduct(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "id", defaultValue = "-1") int id) {
		Products p = null;
		if (id >= 0) {
			p = productService.findOneByID(id);

		}
		if (p != null) {

			CartInfo cartInfo = Util.getCartInSession(request);
			ProductInfo pInfo = new ProductInfo(p);
			cartInfo.removeProduct(pInfo);
		}
		return null;
	}

	@GetMapping("decreaseQuanity")
	public String decreaseQuanity(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "id", defaultValue = "-1") int id) {
		if (id > 0) {
			CartInfo cartInfo = Util.getCartInSession(request);
			cartInfo.updateDecreaseQuality(id);
		}
		return null;
	}

	@GetMapping("increaseQuanity")
	public String increaseQuanity(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "id", defaultValue = "-1") int id) {
		if (id > 0) {
			CartInfo cartInfo = Util.getCartInSession(request);
			cartInfo.updateIncreaseQuality(id);
		}
		return null;
	}

	@GetMapping("listOrder")
	public String listOrder(HttpServletRequest request, ModelMap model, Pageable pageable) {

		String email = Util.getUserSession(request);
		Page<Orders> listOrder = orderService.findAllOrder(email, pageable);
		model.addAttribute("listOrder", listOrder);
		return null;
	}
	/*
	 * @GetMapping("updateProduct") public String updateProduct(HttpServletRequest
	 * request, ModelMap model,
	 * 
	 * @RequestParam(value = "id", defaultValue = "-1") int id) { Products p = null;
	 * if (id >= 0) { p = prodcutService.findOneByID(id);
	 * 
	 * } if (p != null) {
	 * 
	 * CartInfo cartInfo = Util.getCartInSession(request); ProductInfo pInfo = new
	 * ProductInfo(p); cartInfo.updateProduct(id, quantity);
	 * 
	 * } return null; }
	 */

	// GET: Show Cart
	@GetMapping("showCart")
	public String shoppingCartHandler(HttpServletRequest request, ModelMap model) {
		CartInfo myCart = Util.getCartInSession(request);

		model.addAttribute("cartForm", myCart);
		return "shoppingCart";
	}

	@GetMapping("saveCart")
	public String saveCart(HttpServletRequest request, ModelMap model) throws ParseException {
		Customer c = new Customer();
		CartInfo myCart = Util.getCartInSession(request);
		String email = Util.getUserSession(request);
		// Users u = userService.findByEmail(email);

		Orders order = new Orders();
		order.setOrderId(Common.genRand());
		// order.setUser(u);
		order.setOrderDate(Common.CurrentDateTime());
		order.setAmount(myCart.getAmountTotal());
		order.setNameCustomer(c.getName());
		order.setMobileCustomer(c.getMobile());
		order.setAddressCustomer(c.getAddress());
		order.setStatus("Successfully");
		order.setNotes("no comment");

		orderService.saveOrder(order);

		List<CartLineInfo> lines = myCart.getCartLines();
		for (CartLineInfo line : lines) {
			Products p = productService.findOneByID(line.getProducs().getProductID());
			OrderDetail oDetail = new OrderDetail();

			oDetail.setOrder(order);
			oDetail.setProduct(p);
			oDetail.setQuanity(line.getQuantity());
			oDetail.setPrice(line.getProducs().getPrice());
			oDetail.setAmount(line.getAmount());

			orderDetailService.saveOrderDetail(oDetail);

			p.setQuality(p.getQuality() - line.getQuantity());
			if (p.getQuality() == 0) {
				p.setStatus("Het Hang");
				productService.updateQualityProduct(p);
			} else {

				productService.updateQualityProduct(p);
			}

		}

		return "";
	}

	@GetMapping("/updateOrder/{idOrder}")
	public String updateOrder(ModelMap model, @PathVariable("idOrder") String idOrder) {

		Orders o = orderService.findById(idOrder);
		o.setStatus("Cancel");
		orderService.saveOrder(o);

		return null;
	}

	@GetMapping("/listNews")
	public String listNews(ModelMap model, Pageable pageable) {

		Page<News> listNews = newsService.findAll(pageable);

		PageWrapper<News> page = new PageWrapper<News>(listNews, "/listNews");
		model.addAttribute("listNews", page.getContent());
		model.addAttribute("page", page);

		return "News";

	}

	@GetMapping("/setCreateNews")
	public String setCreateNews(ModelMap model) {
		List<CategoriesNews> cnList = categoryNewsService.findAll();
		NewsCreate nc = new NewsCreate();

		model.addAttribute("listCategory", cnList);
		model.addAttribute("nc", nc);
		return "CreateNews";

	}

	@PostMapping("/createNews")
	public String createNews(@Valid @ModelAttribute("newsCreate") NewsCreate newsCreate, ModelMap model)
			throws ParseException {
		News n = new News();
		CategoriesNews cn = categoryNewsService.findByID(newsCreate.getCategoriesID());
		n.setTitle(newsCreate.getTitle());
		n.setContent(newsCreate.getContent());
		n.setCreateDate(Common.CurrentDateTime());
		n.setCategoryNews(cn);

		newsService.saveNews(n);
		return "";
	}

	@GetMapping("/admin")
	public String test(ModelMap model) {
		return "test";

	}

	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}

}

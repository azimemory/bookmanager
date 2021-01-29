package com.kh.bookmanager.cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.bookmanager.cart.model.service.CartService;
import com.kh.bookmanager.member.model.vo.Member;
import com.kh.common.exception.CustomException;

@Controller
@RequestMapping("cart/")
public class CartController{
	
	private final CartService cartService;
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@RequestMapping("cart.do")
	public String cart(Model model, HttpSession session) {
		
		Member member = (Member)session.getAttribute("logInInfo");
		String userId = "";
		
		if(member != null) {
			userId = member.getUserId();
		}
		
		Map<String, Object> commandMap = new HashMap<String, Object>();
		commandMap.put("userId",userId);
		commandMap.put("sessionId",session.getId());
		
		List<Map<String, Object>> cartList = cartService.selectCart(commandMap);
		model.addAttribute("data", cartList);
		return "/cart/cartList";
	}
	
	@RequestMapping("addCart.do")
	public String addCart(Model model, String bIdx, HttpSession session) {
		
		Member member = (Member)session.getAttribute("logInInfo");
		
		String userId = "";
		if(member != null) {
			userId = member.getUserId();
		}
		
		Map<String, Object> commandMap = new HashMap<String, Object>();
		commandMap.put("userId",userId);
		commandMap.put("bIdx",bIdx);
		commandMap.put("sessionId",session.getId());
			
		cartService.insertCart(commandMap);
		return "redirect:/cart/cart.do";
	}
	
	@RequestMapping("removeCart.do")
	@ResponseBody
	public String removeCart(String bcIdx) {
		cartService.deleteCart(bcIdx);
		return "success";
	}
}

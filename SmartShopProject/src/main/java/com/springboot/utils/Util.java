package com.springboot.utils;

import javax.servlet.http.HttpServletRequest;

import com.springboot.entity.CartInfo;


public class Util {

	// Products in Cart, stored in Session.
	public static CartInfo getCartInSession(HttpServletRequest request) {

		// Get Cart from Session.
		CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");

		// If null, create it.
		if (cartInfo == null) {
			cartInfo = new CartInfo();

			// And store to Session.
			request.getSession().setAttribute("myCart", cartInfo);
		}

		return cartInfo;
	}

	public static void setUserSession(HttpServletRequest request, String email) {
		request.getSession().setAttribute("user", email);
	}

	public static String getUserSession(HttpServletRequest request) {

		// Get Cart from Session.
		String email =  (String) request.getSession().getAttribute("users");

		return email;
	}

	public static void removeCartInSession(HttpServletRequest request) {
		request.getSession().removeAttribute("myCart");
	}

	public static void storeLastOrderedCartInSession(HttpServletRequest request, CartInfo cartInfo) {
		request.getSession().setAttribute("lastOrderedCart", cartInfo);
	}

	public static CartInfo getLastOrderedCartInSession(HttpServletRequest request) {
		return (CartInfo) request.getSession().getAttribute("lastOrderedCart");
	}
}

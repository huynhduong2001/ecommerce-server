package com.duong.ecommerce.controller;

import com.duong.ecommerce.exception.CartItemException;
import com.duong.ecommerce.exception.UserException;
import com.duong.ecommerce.model.CartItem;
import com.duong.ecommerce.model.Category;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.request.AddItemRequest;
import com.duong.ecommerce.response.ApiResponse;
import com.duong.ecommerce.service.CartItemService;
import com.duong.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItem(@PathVariable Long cartItemId,
                                                     @RequestHeader("Authorization")String jwt) throws UserException, CartItemException{
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("deleted item from cart successfully!");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItem(@PathVariable Long cartItemId,
                                                  @RequestBody AddItemRequest req,
                                                  @RequestHeader("Authorization")String jwt) throws UserException, CartItemException{
        User user = userService.findUserProfileByJwt(jwt);
        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, req);
        return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
    }
}

package com.duong.ecommerce.service;

import com.duong.ecommerce.exception.CartItemException;
import com.duong.ecommerce.exception.UserException;
import com.duong.ecommerce.model.Cart;
import com.duong.ecommerce.model.CartItem;
import com.duong.ecommerce.model.Product;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.repository.CartItemRepository;
import com.duong.ecommerce.repository.CartRepository;
import com.duong.ecommerce.request.AddItemRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{

    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository){
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        CartItem createdCartItem = cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, AddItemRequest req) throws CartItemException, UserException {

        CartItem item = findCartItemById(id);
        User user = userService.findUserById(userId);

//        if (user.getId().equals(userId)){
//            item.setQuantity(cartItem.getQuantity());
//            item.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
//            item.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
//        }
        if (user.getId().equals(userId)){
            item.setQuantity(req.getQuantity());
            item.setPrice(item.getProduct().getPrice()*req.getQuantity());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*req.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart,product,size,userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);

        User user = userService.findUserById(cartItem.getUserId());
        User reqUser = userService.findUserById(userId);

        if (user.getId().equals(reqUser.getId())){
            cartItemRepository.deleteById(cartItemId);
        }
        else {
            throw new UserException("you can't remove another users item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("cartItem not found with id "+ cartItemId);
    }
}

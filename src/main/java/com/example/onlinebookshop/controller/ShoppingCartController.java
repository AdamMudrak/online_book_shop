package com.example.onlinebookshop.controller;

import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.constants.controllerconstants.ShopCartConstants;
import com.example.onlinebookshop.dto.cartitem.request.AddCartItemDto;
import com.example.onlinebookshop.dto.cartitem.request.UpdateItemQuantityDto;
import com.example.onlinebookshop.dto.shoppingcart.response.ShoppingCartDto;
import com.example.onlinebookshop.entities.User;
import com.example.onlinebookshop.services.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@Tag(name = ShopCartConstants.SHOPPING_CART_API_NAME,
        description = ShopCartConstants.SHOPPING_CART_API_DESCRIPTION)
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = ShopCartConstants.GET_ALL_SUMMARY)
    @ApiResponse(responseCode = Constants.CODE_200, description = Constants.SUCCESSFULLY_RETRIEVED)
    @GetMapping
    public ShoppingCartDto getShoppingCartByUserEmail(@AuthenticationPrincipal User user) {
        return shoppingCartService.getShoppingCartByUserEmail(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = ShopCartConstants.ADD_ITEM_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_ADDED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ENTITY_VALUE)
    })
    @PostMapping
    public ShoppingCartDto addBookToShoppingCart(@AuthenticationPrincipal User user,
            @RequestBody @Valid AddCartItemDto addCartItemDto) {
        return shoppingCartService.addBookToShoppingCart(user.getId(), addCartItemDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = ShopCartConstants.UPDATE_ITEM_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_UPDATED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION
                            + ". Or " + Constants.INVALID_ENTITY_VALUE)
    })
    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartDto updateBookQuantity(
            @AuthenticationPrincipal User user,
            @PathVariable
            @Parameter(
            name = ShopCartConstants.CART_ITEM_ID,
            description = ShopCartConstants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE)
            @Positive
            Long cartItemId,
            @RequestBody @Valid UpdateItemQuantityDto quantity) {
        return shoppingCartService.updateBookQuantity(user.getId(), cartItemId, quantity);
    }

    @Operation(summary = ShopCartConstants.DELETE_ITEM_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_204,
                    description = Constants.CODE_204_DESCRIPTION),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ID_DESCRIPTION)
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookFromShoppingCart(@AuthenticationPrincipal User user,
                                           @PathVariable
                                           @Parameter(
                                           name = ShopCartConstants.CART_ITEM_ID,
                                           description = ShopCartConstants.VALID_ID_DESCRIPTION,
                                           example = Constants.ID_EXAMPLE)
                                           @Positive
                                           Long cartItemId) {
        shoppingCartService.deleteBookFromShoppingCart(user.getId(), cartItemId);
    }
}

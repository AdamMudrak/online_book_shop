package com.example.onlinebookshop.controllers;

import com.example.onlinebookshop.constants.Constants;
import com.example.onlinebookshop.constants.controllers.ShopCartControllerConstants;
import com.example.onlinebookshop.dtos.cartitem.request.CreateCartItemDto;
import com.example.onlinebookshop.dtos.cartitem.request.UpdateCartItemDto;
import com.example.onlinebookshop.dtos.shoppingcart.response.ShoppingCartDto;
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
@Tag(name = ShopCartControllerConstants.SHOPPING_CART_API_NAME,
        description = ShopCartControllerConstants.SHOPPING_CART_API_DESCRIPTION)
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = ShopCartControllerConstants.GET_ALL_SUMMARY)
    @ApiResponse(responseCode = Constants.CODE_200, description = Constants.SUCCESSFULLY_RETRIEVED)
    @GetMapping
    public ShoppingCartDto getShoppingCartByUserId(@AuthenticationPrincipal User user) {
        return shoppingCartService.getShoppingCartByUserId(user.getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = ShopCartControllerConstants.ADD_ITEM_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CODE_200,
                    description = Constants.SUCCESSFULLY_ADDED),
            @ApiResponse(responseCode = Constants.CODE_400,
                    description = Constants.INVALID_ENTITY_VALUE)
    })
    @PostMapping
    public ShoppingCartDto addBookToShoppingCart(@AuthenticationPrincipal User user,
            @RequestBody @Valid CreateCartItemDto createCartItemDto) {
        return shoppingCartService.addBookToShoppingCart(user.getId(), createCartItemDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = ShopCartControllerConstants.UPDATE_ITEM_SUMMARY)
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
            name = ShopCartControllerConstants.CART_ITEM_ID,
            description = ShopCartControllerConstants.VALID_ID_DESCRIPTION,
            example = Constants.ID_EXAMPLE)
            @Positive
            Long cartItemId,
            @RequestBody @Valid UpdateCartItemDto itemDto) {
        return shoppingCartService.updateBookQuantity(user.getId(), cartItemId, itemDto);
    }

    @Operation(summary = ShopCartControllerConstants.DELETE_ITEM_SUMMARY)
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
                                           name = ShopCartControllerConstants.CART_ITEM_ID,
                                           description = ShopCartControllerConstants.VALID_ID_DESCRIPTION,
                                           example = Constants.ID_EXAMPLE)
                                           @Positive
                                           Long cartItemId) {
        shoppingCartService.deleteBookFromShoppingCart(user.getId(), cartItemId);
    }
}

package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.cart.LineItemDto;
import com.tech.api.dto.productvariant.ProductVariantDto;
import com.tech.api.form.cart.AddItemForm;
import com.tech.api.mapper.ProductMapper;
import com.tech.api.storage.model.*;
import com.tech.api.storage.repository.*;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.cart.CartDto;
import com.tech.api.exception.RequestException;
import com.tech.api.form.cart.UpdateCartQuantity;
import com.tech.api.mapper.CartMapper;
import com.tech.api.storage.model.*;
import com.tech.api.storage.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CartController extends ABasicController{
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductVariantRepository variantRepository;

    @Autowired
    LineItemRepository lineItemRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/client-cart",produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CartDto> getClientCart(){
        if(!isCustomer()){
            throw new RequestException(ErrorCode.CART_ERROR_UNAUTHORIZED,"Not allow get .");
        }
        Customer customer = getCurrentCustomer();
        ApiMessageDto<CartDto> result = new ApiMessageDto<>();
        Cart cart = cartRepository.findByCustomerId(customer.getId());

        // if cart null will create one
        if(cart == null){
            cart = new Cart();
            cart.setCustomer(customer);
            cart = cartRepository.save(cart);
        }
        CartDto dto = cartMapper.fromEntityToCartDto(cart);
        List<LineItem> list = lineItemRepository.findByCartId(cart.getId());
        if(list != null && !list.isEmpty()){
            Double totalMoney = 0d;
            for (LineItem lineItem : list){
                ProductVariant variant = lineItem.getVariant();
                Product product = variant.getProductConfig().getProduct();
                LineItemDto lineItemDto = cartMapper.fromEntityToLineItemDto(lineItem);
                lineItemDto.setProductDto(productMapper.fromProductEntityToDto(product));
                dto.getLineItemDtoList().add(lineItemDto);
                if(product.getIsSaleOff()){
                    float saleOffValue = 0;
                    if(product.getSaleOff() != null){
                        saleOffValue = (float)product.getSaleOff() / 100;
                    }
                    totalMoney += variant.getPrice() * (1 - saleOffValue);
                }
                else{
                    totalMoney += variant.getPrice();
                }
            }
            dto.setTotalMoney(totalMoney);
        }
        result.setData(dto);
        result.setMessage("Get cart success");
        return result;
    }

    @PostMapping(value = "/add-item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> addItem(@Valid @RequestBody AddItemForm addItemForm, BindingResult bindingResult) {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.CART_ERROR_UNAUTHORIZED, "Not allowed to add item.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Product product = productRepository.findById(addItemForm.getProductId()).orElse(null);
        if(product == null || product.getIsSoldOut()|| !product.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "Not found product.");
        }
        ProductVariant variant = variantRepository.findById(addItemForm.getProductVariantId()).orElse(null);
        if(variant == null || !variant.getStatus().equals(Constants.STATUS_ACTIVE) || !variant.getProductConfig().getProduct().getId().equals(product.getId())){
            throw new RequestException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND, "Not found variant.");
        }
        Cart cart = cartRepository.findByCustomerId(getCurrentCustomer().getId());
        if(cart == null){
            cart = new Cart();
            cart.setCustomer(getCurrentCustomer());
            cart = cartRepository.save(cart);
        }
        LineItem lineItem = lineItemRepository.findByCartIdAndVariantId(cart.getId(),variant.getId());
        if(lineItem != null) lineItem.setQuantity(lineItem.getQuantity() + 1);
        else {
            lineItem = new LineItem();
            lineItem.setCart(cart);
            lineItem.setVariant(variant);
            lineItem.setQuantity(1);
        }
        lineItemRepository.save(lineItem);

        apiMessageDto.setMessage("Add cart success");
        return apiMessageDto;
    }

    @PutMapping(value = "/items/quantity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateQuantity(@Valid @RequestBody UpdateCartQuantity updateCartQuantity, BindingResult bindingResult) {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.PRODUCT_REVIEW_ERROR_UNAUTHORIZED, "Not allowed to update quantity.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        LineItem lineItem = lineItemRepository.findById(updateCartQuantity.getLineItemId()).orElse(null);
        if(lineItem == null){
            throw new RequestException(ErrorCode.LINE_ITEM_ERROR_NOT_FOUND, "Not found item.");
        }
        lineItem.setQuantity(updateCartQuantity.getQuantity());
        lineItemRepository.save(lineItem);
        apiMessageDto.setMessage("Update quantity success");
        return apiMessageDto;
    }

    private Customer getCurrentCustomer() {
        Customer customer = customerRepository.findCustomerByAccountId(getCurrentUserId());
        if(customer == null || !customer.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND,"Customer not found");
        }
        return customer;
    }

    @DeleteMapping(value = "/items/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable(name = "id") Long id) {
        LineItem lineItem = lineItemRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.LINE_ITEM_ERROR_NOT_FOUND, "Item not found"));

        lineItemRepository.delete(lineItem);
        return new ApiMessageDto<>("Delete item successfully");
    }
}

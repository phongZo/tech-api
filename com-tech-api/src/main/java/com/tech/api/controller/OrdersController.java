package com.tech.api.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mapbox.geojson.Point;
import com.tech.api.constant.Constants;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.form.orders.*;
import com.tech.api.service.CommonApiService;
import com.tech.api.service.MapboxService;
import com.tech.api.storage.model.*;
import com.tech.api.storage.projection.RevenueOrders;
import com.tech.api.storage.repository.*;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.dto.orders.OrdersDetailDto;
import com.tech.api.dto.orders.OrdersDto;
import com.tech.api.dto.orders.ResponseListObjOrders;
import com.tech.api.exception.RequestException;
import com.tech.api.mapper.OrdersDetailMapper;
import com.tech.api.mapper.OrdersMapper;
import com.tech.api.storage.criteria.OrdersCriteria;
import com.tech.api.utils.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/v1/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class OrdersController extends ABasicController{
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrdersDetailRepository ordersDetailRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    LineItemRepository lineItemRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductVariantRepository productVariantRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerAddressRepository customerAddressRepository;

    @Autowired
    CustomerPromotionRepository customerPromotionRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    OrdersDetailMapper ordersDetailMapper;

    @Autowired
    CommonApiService landingIsApiService;

    @Autowired
    MapboxService mapboxService;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/list",produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<OrdersDto>> list(OrdersCriteria ordersCriteria, Pageable pageable){
        if(!isAdmin()){
            throw new RequestException(ErrorCode.ORDERS_ERROR_UNAUTHORIZED,"Not allowed get list.");
        }
        ApiMessageDto<ResponseListObj<OrdersDto>> responseListObjApiMessageDto = new ApiMessageDto<>();
        Page<Orders> listOrders = ordersRepository.findAll(ordersCriteria.getSpecification(), pageable);
        ResponseListObj<OrdersDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(ordersMapper.fromEntityListToOrdersDtoList(listOrders.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(listOrders.getTotalPages());
        responseListObj.setTotalElements(listOrders.getTotalElements());

        responseListObjApiMessageDto.setData(responseListObj);
        responseListObjApiMessageDto.setMessage("Get list success");
        return responseListObjApiMessageDto;
    }

    // Store
    @GetMapping(value = "/store/my-orders",produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObjOrders> listMyOrders(OrdersCriteria ordersCriteria){
        if(!isManager() && !isEmployee()){
            throw new RequestException(ErrorCode.ORDERS_ERROR_UNAUTHORIZED,"Not allowed get list.");
        }
        if(ordersCriteria.getStoreId() == null){
            throw new RequestException(ErrorCode.ORDERS_ERROR_UNAUTHORIZED, "Not allowed to get orders");
        }
        ordersCriteria.setNotState(Constants.ORDERS_STATE_ARCHIVE);   // don't get archived orders
        Store store = storeRepository.findById(ordersCriteria.getStoreId()).orElse(null);
        if(store == null || !store.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.STORE_ERROR_NOT_FOUND,"store not found");
        }
        if(isEmployee()) ordersCriteria.setEmployeeId(getCurrentUserId());
        ApiMessageDto<ResponseListObjOrders> responseListObjApiMessageDto = new ApiMessageDto<>();
        List<Orders> listOrders = ordersRepository.findAll(ordersCriteria.getSpecification());
        List<OrdersDetail> listFirstDetail = ordersDetailRepository.findFirstByListOrders(listOrders);
        List<OrdersDto> dto = ordersMapper.fromEntityListToOrdersDtoList(listOrders);
        for (OrdersDto ordersDto : dto){
            List<OrdersDetail> detailList = new ArrayList<>();
            for (OrdersDetail detail : listFirstDetail){
                if(detail.getOrders().getId().equals(ordersDto.getId())){
                    detailList.add(detail);
                }
            }
            ordersDto.setOrdersDetailDtoList(ordersDetailMapper.fromEntityListToOrdersDetailDtoList(detailList));
        }
        ResponseListObjOrders responseListObjOrders = new ResponseListObjOrders();
        //Double sum = ordersRepository.sumMoney(listOrders);
        //responseListObjOrders.setSumMoney(sum);
        responseListObjOrders.setData(dto);
        responseListObjApiMessageDto.setData(responseListObjOrders);
        responseListObjApiMessageDto.setMessage("Get list success");
        return responseListObjApiMessageDto;
    }


    @GetMapping(value = "/revenue",produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<RevenueOrders> getRevenue(OrdersCriteria ordersCriteria){
        if(!isManager() && !isAdmin()){
            throw new RequestException(ErrorCode.ORDERS_ERROR_UNAUTHORIZED,"Not allowed get list.");
        }
        ApiMessageDto<RevenueOrders> result = new ApiMessageDto<>();
        RevenueOrders revenueOrders = null;
        if(isManager()){
            Employee employee = employeeRepository.findById(getCurrentUserId()).orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND));
            revenueOrders = ordersRepository.getRevenueOrders(ordersCriteria.getFrom(),ordersCriteria.getTo(),employee.getStore().getId());
        } else {
            revenueOrders = ordersRepository.getRevenueOrders(ordersCriteria.getFrom(),ordersCriteria.getTo(),null);
        }
        result.setData(revenueOrders);
        result.setResult(true);
        result.setMessage("Get revenue success");
        return result;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OrdersDto> get(@PathVariable("id") Long id) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.ORDERS_ERROR_UNAUTHORIZED, "Not allowed get.");
        }
        ApiMessageDto<OrdersDto> result = new ApiMessageDto<>();

        Orders orders = ordersRepository.findById(id).orElse(null);
        if(orders == null) {
            throw new RequestException(ErrorCode.ORDERS_ERROR_NOT_FOUND, "Not found orders.");
        }
        List<OrdersDetailDto> ordersDetailDtoList = ordersDetailMapper
                .fromEntityListToOrdersDetailDtoList(ordersDetailRepository.findAllByOrdersId(id));
        OrdersDto ordersDto = ordersMapper.fromEntityToOrdersDto(orders);
        ordersDto.setOrdersDetailDtoList(ordersDetailDtoList);
        result.setData(ordersDto);
        result.setMessage("Get orders success");
        return result;
    }

    @PostMapping(value = "/archive", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @JsonIgnore
    public ApiMessageDto<String> archiveOrder() {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        ordersRepository.updateArchive();
        apiMessageDto.setMessage("Success");
        return apiMessageDto;
    }


    @GetMapping(value = "/client-list",produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<OrdersDto>> clientList(OrdersCriteria ordersCriteria){
        if(!isCustomer()){
            throw new RequestException(ErrorCode.ORDERS_ERROR_UNAUTHORIZED, "Not allowed get.");
        }
        ApiMessageDto<ResponseListObj<OrdersDto>> responseListObjApiMessageDto = new ApiMessageDto<>();
        Customer customer = getCurrentCustomer();
        ordersCriteria.setCustomerId(customer.getId());
        List<Orders> listOrders = ordersRepository.findAll(ordersCriteria.getSpecification());
        List<OrdersDetail> listFirstDetail = ordersDetailRepository.findFirstByListOrders(listOrders);
        List<OrdersDto> dto = ordersMapper.fromEntityListToOrdersDtoList(listOrders);
        for (OrdersDto ordersDto : dto){
            List<OrdersDetail> detailList = new ArrayList<>();
            for (OrdersDetail detail : listFirstDetail){
                if(detail.getOrders().getId().equals(ordersDto.getId())){
                    detailList.add(detail);
                }
            }
            ordersDto.setOrdersDetailDtoList(ordersDetailMapper.fromEntityListToOrdersDetailDtoList(detailList));
        }
        ResponseListObj<OrdersDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(dto);
        responseListObjApiMessageDto.setData(responseListObj);
        responseListObjApiMessageDto.setMessage("Get list success");
        return responseListObjApiMessageDto;
    }

    @GetMapping(value = "/client-get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OrdersDto> clientGet(@PathVariable("id") Long id) {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.ORDERS_ERROR_UNAUTHORIZED, "Not allowed get.");
        }
        ApiMessageDto<OrdersDto> result = new ApiMessageDto<>();
        Customer customer = getCurrentCustomer();
        Orders orders = ordersRepository.findOrdersByIdAndCustomerId(id,customer.getId());
        if(orders == null || !orders.getStatus().equals(Constants.STATUS_ACTIVE)) {
            throw new RequestException(ErrorCode.ORDERS_ERROR_NOT_FOUND, "Not found orders.");
        }
        List<OrdersDetailDto> ordersDetailDtoList = ordersDetailMapper.fromEntityListToOrdersDetailClientDtoList(ordersDetailRepository.findAllByOrdersId(id));
        OrdersDto ordersDto = ordersMapper.fromEntityToOrdersDto(orders);
        ordersDto.setOrdersDetailDtoList(ordersDetailDtoList);
        result.setData(ordersDto);
        result.setMessage("Get orders success");
        return result;
    }

    private Customer getCurrentCustomer() {
        Long userId = getCurrentUserId();
        Customer customer = customerRepository.findCustomerByAccountId(userId);
        if(customer == null || !customer.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Not found current customer.");
        }
        return customer;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @PostMapping(value = "/client-create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> clientCreate(@Valid @RequestBody CreateOrdersClientForm createOrdersForm, BindingResult bindingResult) {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.ORDERS_ERROR_UNAUTHORIZED, "Not allowed to create.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Store store = checkStore(createOrdersForm);
        if(!store.getStatus().equals(Constants.STATUS_ACTIVE)){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Cửa hàng hiện không hoạt động");
            return apiMessageDto;
        }
        CustomerAddress address = checkAddress(createOrdersForm);
        CustomerPromotion promotion = null;
        if(createOrdersForm.getPromotionId() != null){
            promotion = checkPromotion(createOrdersForm);
        }

        List<OrdersDetail> ordersDetailList = ordersDetailMapper
                .fromCreateOrdersDetailFormListToOrdersDetailList(createOrdersForm.getCreateOrdersDetailFormList());
        Orders orders = ordersMapper.fromCreateOrdersFormToEntity(createOrdersForm);
        orders.setStore(store);
        orders.setAddress(address);
        setCustomerClient(orders,createOrdersForm);
        Double checkSaleOff = createOrdersForm.getSaleOff();
        if(checkSaleOff < 0){
            throw new RequestException(ErrorCode.ORDERS_ERROR_BAD_REQUEST, "saleOff is not accepted");
        }
        orders.setCode(generateCode());
        orders.setState(Constants.ORDERS_STATE_CREATED);


        orders.setExpectedReceiveDate(createOrdersForm.getExpectedTimeDelivery() == null ? LocalDate.from(convertToLocalDateViaInstant(new Date())).plusDays(7) : createOrdersForm.getExpectedTimeDelivery());
        Orders savedOrder = ordersRepository.save(orders);
        /*-----------------------Xử lý orders detail------------------ */
        List<GhnOrderItem> listItem = amountPriceCal(createOrdersForm.getDeliveryFee(),orders,ordersDetailList,savedOrder,promotion);  //Tổng tiền hóa đơn

        // check wallet money if not have enough money
        if(createOrdersForm.getPaymentMethod().equals(Constants.PAYMENT_METHOD_ONLINE)){
            Customer customer = getCurrentCustomer();
            if(customer.getWalletMoney() < orders.getTotalMoney()){
                ordersRepository.delete(savedOrder);
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Ví không đủ tiền");
                return apiMessageDto;
            } else {
                customer.setWalletMoney(customer.getWalletMoney() - orders.getTotalMoney());
                customerRepository.save(customer);
            }
        }
        ordersDetailRepository.saveAll(ordersDetailList);

        /*-----------------------Quay lại xử lý orders------------------ */
        if(promotion != null){
            orders.setCustomerPromotionId(promotion.getId());
            promotion.setIsInUse(true);
            customerPromotionRepository.save(promotion);
        }
        // choose store
        //selectStore(orders);

        // update each product in stock
        //updateStock(ordersDetailList,orders);
        ordersRepository.save(orders);

        // remove cart item
        Cart cart = cartRepository.findByCustomerId(getCurrentCustomer().getId());
        List<LineItem> list = lineItemRepository.findByCartId(cart.getId());
        lineItemRepository.deleteAll(list);
        cartRepository.save(cart);

        // send to GHN
        sendToGhnApi(orders, createOrdersForm, listItem);

        apiMessageDto.setMessage("Create orders success");
        return apiMessageDto;
    }

    private void sendToGhnApi(Orders orders, CreateOrdersClientForm createOrdersForm, List<GhnOrderItem> itemList) {
        String provinceName = orders.getAddress().getAddressDetails().split(",")[orders.getAddress().getAddressDetails().split(",").length - 1].substring(1);   // city
        String districtName = orders.getAddress().getAddressDetails().split(",")[orders.getAddress().getAddressDetails().split(",").length - 2].substring(1);   // district
        String wardName = orders.getAddress().getAddressDetails().split(",")[orders.getAddress().getAddressDetails().split(",").length - 3].substring(1);   // ward

        CreateOrderGhnForm form = new CreateOrderGhnForm();
        form.setPaymentTypeId(Constants.STORE_PAY_DELIVERY_FEE);
        form.setRequireNote(Constants.ALLOW_SEE_GOODS_NOT_TRIAL);
        if(orders.getPaymentMethod().equals(Constants.PAYMENT_METHOD_COD)) form.setCodeFailedAmount(Integer.parseInt(orders.getDeliveryFee().toString()));
        form.setToName(orders.getAddress().getReceiverFullName());
        form.setToPhone(orders.getAddress().getPhone());
        form.setToAddress(orders.getAddress().getAddressDetails());
        form.setToWardName(wardName);
        form.setToDistrictName(districtName);
        form.setToProvinceName(provinceName);
        if(orders.getPaymentMethod().equals(Constants.PAYMENT_METHOD_COD)) form.setCodAmount(Integer.parseInt(orders.getTotalMoney().toString()));
        else form.setCodAmount(0);
        form.setWeight(2000);
        form.setHeight(100);
        form.setLength(100);
        form.setWidth(100);
        form.setInsuranceValue(Integer.parseInt(orders.getTotalMoney().toString()) > Constants.INSURANCE_FEE ? Constants.INSURANCE_FEE : Integer.parseInt(orders.getTotalMoney().toString()));
        form.setServiceId(createOrdersForm.getServiceId());
        form.setServiceTypeId(createOrdersForm.getServiceTypeId());
        form.setItems(itemList);

        String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("token", Constants.token);
        headers.set("shopId", orders.getStore().getShopId().toString());
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        if(!result.getStatusCode().equals(HttpStatus.OK)){
            throw new RequestException(ErrorCode.ORDERS_CREATE_FAILED, "Failed to create order ghn");
        }
    }

    private Store checkStore(CreateOrdersClientForm createOrdersForm) {
        Store store = storeRepository.findById(createOrdersForm.getStoreId()).orElse(null);
        if(store == null || !store.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.STORE_ERROR_NOT_FOUND, "Not found store");
        }
        return store;
    }

    private void updateStock(List<OrdersDetail> ordersDetailList, Orders orders) {
        for (OrdersDetail detail : ordersDetailList){

            ProductVariant variant = productVariantRepository.findById(detail.getProductVariant().getId()).orElse(null);
            if(variant == null){
                throw new RequestException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND, "product variant not existed");
            }
            Product productCheck = productRepository.findById(variant.getProductConfig().getProduct().getId()).orElse(null);
            if (productCheck == null){
                throw new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "product is not existed");
            }

            // update in stock of store
            Stock stock = stockRepository.findFirstByProductVariantIdAndStoreId(variant.getId(), orders.getStore().getId());
            if(stock != null && stock.getTotal() != null && stock.getTotal() >= detail.getAmount()) {
                stock.setTotal(stock.getTotal() - detail.getAmount());
                stockRepository.save(stock);
            }

            // update general variant
            if(variant.getTotalInStock() >= detail.getAmount()){
                variant.setTotalInStock(variant.getTotalInStock() - detail.getAmount());
                productVariantRepository.save(variant);
            }

            productCheck.setTotalInStock(productCheck.getTotalInStock() - detail.getAmount());
            productRepository.save(productCheck);
        }
    }

    private Store getNearestStore(List<Store> storeList, CustomerAddress address) {
        Point point = mapboxService.getPoint(address.getAddressDetails());
        address.setLatitude(point.latitude());
        address.setLongitude(point.longitude());
        customerAddressRepository.save(address);

        double minDistance = 10000;
        Store nearestStore = null;
        for (Store store : storeList){
            double distance = mapboxService.distance(store.getLatitude(),store.getLongitude(),point.latitude(),point.longitude());
            if(minDistance < distance){
                minDistance = distance;
                nearestStore = store;
            }
        }
        return nearestStore;
    }

    private CustomerPromotion checkPromotion(CreateOrdersClientForm createOrdersForm) {
        CustomerPromotion promotion = customerPromotionRepository.findById(createOrdersForm.getPromotionId()).orElse(null);
        if(promotion == null || promotion.getExpireDate().before(new Date())){
            throw new RequestException(ErrorCode.CUSTOMER_PROMOTION_ERROR_NOT_FOUND, "Not found promotion");
        }
        if(promotion.getIsInUse()){
            throw new RequestException(ErrorCode.CUSTOMER_PROMOTION_ERROR_IN_ANOTHER_USING, "Promotion is in another other using");
        }
        return promotion;
    }

    private CustomerAddress checkAddress(CreateOrdersClientForm createOrdersForm) {
        CustomerAddress address = customerAddressRepository.findById(createOrdersForm.getCustomerAddressId()).orElse(null);
        if(address == null) {
            throw new RequestException(ErrorCode.CUSTOMER_ADDRESS_ERROR_NOT_FOUND, "Not found address");
        }
        return address;
    }

    private void setCustomerClient(Orders orders, CreateOrdersClientForm createOrdersForm) {
        Long id = getCurrentUserId();
        Customer customerCheck = customerRepository.findCustomerByAccountId(id);
        if (customerCheck == null || !customerCheck.getStatus().equals(Constants.STATUS_ACTIVE)) {
            throw new RequestException(ErrorCode.ORDERS_ERROR_NOT_FOUND, "Not found current customer");
        }
        orders.setCustomer(customerCheck);
    }

    @PutMapping(value = "/client-cancel-orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> clientCancelOrders(@PathVariable("id") Long id) {
        if (!isCustomer()) {
            throw new RequestException(ErrorCode.ORDERS_ERROR_UNAUTHORIZED, "Not allowed to cancel orders.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Orders orders = ordersRepository.findById(id).orElse(null);
        if(orders == null){
            throw new RequestException(ErrorCode.ORDERS_ERROR_NOT_FOUND, "Order Not Found");
        }
        checkState(orders);
        checkCancelTime(orders);
        Integer prevState = orders.getState();
        orders.setState(Constants.ORDERS_STATE_CANCELED);
        orders.setPrevState(prevState);

        if(orders.getCustomerPromotionId() != null){
            CustomerPromotion customerPromotion = customerPromotionRepository.findById(orders.getCustomerPromotionId()).orElse(null);
            if(customerPromotion != null){
                customerPromotion.setIsInUse(false);
                customerPromotionRepository.save(customerPromotion);
            }
        }
        if(orders.getPaymentMethod().equals(Constants.PAYMENT_METHOD_ONLINE)){
            Customer customer = customerRepository.findById(getCurrentCustomer().getId()).orElse(null);
            customer.setWalletMoney(customer.getWalletMoney() + orders.getTotalMoney());
            customerRepository.save(customer);
        }
        ordersRepository.save(orders);
        apiMessageDto.setMessage("Cancel order success");
        return apiMessageDto;
    }

    private void checkCancelTime(Orders orders) {
        Integer limitCancelTime =  Constants.LIMIT_CANCEL_ORDER_TIME;

        Date oldDate = orders.getCreatedDate();
        Date checkDate = DateUtils.addHours(oldDate, limitCancelTime);  //Cộng 3 giờ vào ngày tạo orders
        Date currentDate = new Date();
        if (!checkDate.after(currentDate)){
                throw new RequestException(ErrorCode.ORDERS_ERROR_BAD_REQUEST, "Order time was over the limit cancel time");
        }
    }

    // STORE
    @PutMapping(value = "/update-state", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateStateOrdersForm updateStateOrdersForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Orders orders = ordersRepository.findById(updateStateOrdersForm.getId()).orElse(null);
        if(orders == null){
            throw new RequestException(ErrorCode.ORDERS_ERROR_NOT_FOUND, "Orders Not Found");
        }
        checkNewState(updateStateOrdersForm,orders);
        Integer prevState = orders.getState();
        orders.setState(updateStateOrdersForm.getState());
        orders.setPrevState(prevState);

        // UPDATE SOLD AMOUNT OF PRODUCT
        if(orders.getState().equals(Constants.ORDERS_STATE_COMPLETED)){
            List<OrdersDetail> ordersDetailList = ordersDetailRepository.findAllByOrdersId(orders.getId());
            for (OrdersDetail ordersDetail : ordersDetailList){
                Product productCheck = productRepository.findById(ordersDetail.getProductVariant().getProductConfig().getProduct().getId()).orElse(null);
                if (productCheck == null){
                    throw new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "product not existed");
                }
                Integer soldAmount = productCheck.getSoldAmount() + 1;
                productCheck.setSoldAmount(soldAmount);
                productRepository.save(productCheck);
            }
            // update loyalty point and point
            Customer customer = orders.getCustomer();
            if(customer != null){
                int orderPoint = (int) (orders.getTotalMoney() / 1000);
                // up level
                if((customer.getLoyaltyPoint() + orderPoint) >= Double.parseDouble(Constants.LOYALTY_MAX_POINT.split(",")[customer.getLoyaltyLevel()])){
                    customer.setLoyaltyPoint((customer.getLoyaltyPoint() + orderPoint) - Integer.parseInt(Constants.LOYALTY_MAX_POINT.split(",")[customer.getLoyaltyLevel()]));
                    customer.setLoyaltyLevel(customer.getLoyaltyLevel() + 1);

                    // Give vouchers of level upgraded
                    List<Promotion> promotions = promotionRepository.findAllByLoyaltyLevel(customer.getLoyaltyLevel());
                    List<CustomerPromotion> customerPromotionList = new ArrayList<>();
                    for (Promotion promotion : promotions){
                        CustomerPromotion customerPromotion = new CustomerPromotion();
                        customerPromotion.setPromotion(promotion);
                        customerPromotion.setCustomer(customer);
                        customerPromotion.setIsInUse(false);

                        Date dt = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(dt);
                        c.add(Calendar.MONTH, 2);
                        dt = c.getTime();
                        customerPromotion.setExpireDate(dt);
                        customerPromotionList.add(customerPromotion);
                    }
                    customerPromotionRepository.saveAll(customerPromotionList);
                } else{
                    customer.setLoyaltyPoint(customer.getLoyaltyPoint() + orderPoint);
                }
                customer.setPoint(customer.getPoint() + orderPoint);
                customerRepository.save(customer);
            }
        }
        ordersRepository.save(orders);
        apiMessageDto.setMessage("Update orders state success");
        return apiMessageDto;
    }


    private List<GhnOrderItem> amountPriceCal(Double deliveryFee,Orders orders,List<OrdersDetail> ordersDetailList, Orders savedOrder, CustomerPromotion promotion) {
        List<GhnOrderItem> listItem = new ArrayList<>();
        int checkIndex = 0;
        double amountPrice = 0.0;
        // calculate amount item
        Integer amount = 0;
        for (OrdersDetail ordersDetail : ordersDetailList){
            amount += ordersDetail.getAmount();
            ProductVariant variant = productVariantRepository.findById(ordersDetail.getProductVariant().getId()).orElse(null);
            if(variant == null){
                throw new RequestException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND, "product variant not existed");
            }
            Product productCheck = productRepository.findById(variant.getProductConfig().getProduct().getId()).orElse(null);
            if (productCheck == null){
                throw new RequestException(ErrorCode.PRODUCT_NOT_FOUND, "product in index "+checkIndex+"is not existed");
            }
            Double productPrice = variant.getPrice();
            if(productCheck.getIsSaleOff()){
                productPrice = productPrice - (productPrice * productCheck.getSaleOff() / 100);
            }
            amountPrice = amountPrice + productPrice * (ordersDetail.getAmount()); // Tổng tiền 1 sp
            ordersDetail.setPrice(productPrice * ordersDetail.getAmount());
            ordersDetail.setOrders(savedOrder);
            checkIndex++;

            GhnOrderItem item = new GhnOrderItem();
            item.setName(productCheck.getName() + " (" + variant.getName() + ")");
            item.setPrice(ordersDetail.getPrice());
            item.setQuantity(ordersDetail.getAmount());
            listItem.add(item);
        }
        orders.setAmount(amount);
        amountPrice += deliveryFee;
        Double totalMoney = totalMoneyHaveToPay(amountPrice,orders,promotion);
        orders.setSaleOffMoney(amountPrice - totalMoney);
        orders.setTotalMoney(totalMoney);
        return listItem;
    }

    private Double totalMoneyHaveToPay(Double amountPrice,Orders orders, CustomerPromotion promotion) {
        if(promotion == null){
            return Math.round(amountPrice * 100.0) / 100.0;
        }
        Integer kind = promotion.getPromotion().getKind();
        if(kind.equals(Constants.PROMOTION_KIND_MONEY)){
            double saleOff = Double.parseDouble(promotion.getPromotion().getValue());
            amountPrice -= saleOff;
        } else if (kind.equals(Constants.PROMOTION_KIND_PERCENT)){
            int percent = Integer.parseInt(promotion.getPromotion().getValue());
            double valueAfterSale = amountPrice * ((double)percent / 100);
            if(promotion.getPromotion().getMaxValueForPercent() != null){
                if(valueAfterSale > promotion.getPromotion().getMaxValueForPercent()){
                    amountPrice -= promotion.getPromotion().getMaxValueForPercent();
                    return Math.round(amountPrice * 100.0) / 100.0;
                }
            }
            amountPrice -= amountPrice - amountPrice * ((double)percent / 100);
        }
        return Math.round(amountPrice * 100.0) / 100.0;          // Làm tròn đến thập phân thứ 2
    }

    private void checkNewState(UpdateStateOrdersForm updateStateOrdersForm,Orders orders) {
        // state mới phải lớn hơn state cũ
        if((updateStateOrdersForm.getState() <= orders.getState())){
            throw new RequestException(ErrorCode.ORDERS_ERROR_BAD_REQUEST, "Update orders state must mor than or equal old state");
        }
        // State 3 4 không thể update
        Integer state = orders.getState();
        if(state.equals(Constants.ORDERS_STATE_COMPLETED) || state.equals(Constants.ORDERS_STATE_CANCELED)){
            throw new RequestException(ErrorCode.ORDERS_ERROR_BAD_REQUEST, "Can not update orders in state 3 or 4");
        }
    }

    private String generateCode() {
        Long maxId = ordersRepository.findMaxId();
        long code = 10000000;
        if(maxId == null || maxId == 0){
            return Long.toString(code);
        }
        code += maxId;
        return Long.toString(code);
    }

    private void checkState(Orders orders) {
        Integer state = orders.getState();
        if(state.equals(Constants.ORDERS_STATE_COMPLETED) || state.equals(Constants.ORDERS_STATE_CANCELED)){
            throw new RequestException(ErrorCode.ORDERS_ERROR_BAD_REQUEST, "Can not cancel order in this state");
        }
    }

    @PostMapping(value = "/order-request-payment", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> orderRequestPayment(@Valid @RequestBody OrderPaymentForm orderPaymentForm, HttpServletRequest request) throws IOException {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Orders orders = ordersRepository.findById(orderPaymentForm.getOrderId()).orElse(null);
        if(orders == null || orders.getState().equals(Constants.ORDERS_STATE_COMPLETED) || orders.getState().equals(Constants.ORDERS_STATE_CANCELED)){
            throw new RequestException(ErrorCode.ORDERS_ERROR_NOT_FOUND);
        }

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "billpayment";
        long amount = (long) (orders.getTotalMoney() * 100);

        String vnp_TxnRef = orders.getCode();
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", orderPaymentForm.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.DATE, 1);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        apiMessageDto.setMessage("Request payment success");
        apiMessageDto.setData(paymentUrl);
        return apiMessageDto;
    }
}

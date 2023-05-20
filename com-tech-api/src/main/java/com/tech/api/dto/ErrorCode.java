package com.tech.api.dto;

public class ErrorCode {

    /**
     * General error code
     */
    public static final String GENERAL_ERROR_UNAUTHORIZED = "ERROR-GENERAL-000";
    public static final String GENERAL_ERROR_NOT_FOUND = "ERROR-GENERAL-001";
    public static final String GENERAL_ERROR_BAD_REQUEST = "ERROR-GENERAL-002";
    public static final String GENERAL_ERROR_LOGIN_FAILED = "ERROR-GENERAL-003";
    public static final String GENERAL_ERROR_NOT_MATCH = "ERROR-GENERAL-004";
    public static final String GENERAL_ERROR_WRONG_HASH = "ERROR-GENERAL-005";
    public static final String GENERAL_ERROR_LOCKED = "ERROR-GENERAL-006";
    public static final String GENERAL_ERROR_INVALID = "ERROR-GENERAL-007";

    /**
     * Category error code
     */
    public static final String CATEGORY_ERROR_UNAUTHORIZED = "ERROR-CATEGORY-000";
    public static final String CATEGORY_ERROR_NOT_FOUND = "ERROR-CATEGORY-001";

    /**
     * Group error code
     */
    public static final String GROUP_ERROR_UNAUTHORIZED = "ERROR-GROUP-000";
    public static final String GROUP_ERROR_NOT_FOUND = "ERROR-GROUP-001";
    public static final String GROUP_ERROR_EXIST = "ERROR-GROUP-002";
    public static final String GROUP_ERROR_CAN_NOT_DELETED = "ERROR-GROUP-003";

    /**
     * Import error code
     */
    public static final String IMPORT_ERROR_UNAUTHORIZED = "ERROR-IMPORT-000";
    public static final String IMPORT_ERROR_NOT_FOUND = "ERROR-IMPORT-001";

    /**
     * Promotion error code
     */
    public static final String PROMOTION_ERROR_UNAUTHORIZED = "ERROR-PROMOTION-000";
    public static final String PROMOTION_ERROR_NOT_FOUND = "ERROR-PROMOTION-001";
    public static final String PROMOTION_ERROR_BAD_REQUEST = "ERROR-PROMOTION-002";

    /**
     * Orders error code
     */
    public static final String ORDERS_ERROR_UNAUTHORIZED = "ERROR-ORDERS-000";
    public static final String ORDERS_ERROR_NOT_FOUND = "ERROR-ORDERS-001";
    public static final String ORDERS_ERROR_BAD_REQUEST = "ERROR-ORDERS-002";
    public static final String ORDERS_CREATE_FAILED = "ERROR-ORDERS-003";

    /**
     * Customer address error code
     */
    public static final String CUSTOMER_ADDRESS_ERROR_UNAUTHORIZED = "ERROR-ADDRESS-000";
    public static final String CUSTOMER_ADDRESS_ERROR_NOT_FOUND = "ERROR-ADDRESS-001";
    public static final String CUSTOMER_ADDRESS_ERROR_BAD_REQUEST = "ERROR-ADDRESS-002";

    /**
     * Customer promotion error code
     */
    public static final String CUSTOMER_PROMOTION_ERROR_UNAUTHORIZED = "ERROR-CUSTOMER-PROMOTION-000";
    public static final String CUSTOMER_PROMOTION_ERROR_NOT_FOUND = "ERROR-CUSTOMER-PROMOTION-001";
    public static final String CUSTOMER_PROMOTION_ERROR_BAD_REQUEST = "ERROR-CUSTOMER-PROMOTION-002";
    public static final String CUSTOMER_PROMOTION_ERROR_IN_ANOTHER_USING = "ERROR-CUSTOMER-PROMOTION-003";

    /**
     * Store error code
     */
    public static final String STORE_ERROR_UNAUTHORIZED = "ERROR-STORE-000";
    public static final String STORE_ERROR_NOT_FOUND = "ERROR-STORE-001";
    public static final String STORE_ERROR_BAD_REQUEST = "ERROR-STORE-002";
    public static final String STORE_ERROR_VERIFY = "ERROR-STORE-003";

    /**
     * Store error code
     */
    public static final String CART_ERROR_UNAUTHORIZED = "ERROR-CART-000";
    public static final String CART_ERROR_NOT_FOUND = "ERROR-CART-001";
    public static final String CART_ERROR_BAD_REQUEST = "ERROR-CART-002";

    /**
     * Permission error code
     */
    public static final String PERMISSION_ERROR_UNAUTHORIZED = "ERROR-PERMISSION-000";
    public static final String PERMISSION_ERROR_NOT_FOUND = "ERROR-PERMISSION-001";

    /**
     * News error code
     */
    public static final String NEWS_ERROR_UNAUTHORIZED = "ERROR-NEWS-000";
    public static final String NEWS_ERROR_NOT_FOUND = "ERROR-NEWS-001";

    /**
     * Location error code
     */
    public static final String LOCATION_ERROR_NOTFOUND = "ERROR-LOCATION-000";
    public static final String LOCATION_ERROR_INVALID = "ERROR-LOCATION-001";
    public static final String LOCATION_ERROR_INVALID_PARENT = "ERROR-LOCATION-002";

    /**
     * Rank error code
     */
    public static final String RANK_ERROR_NOT_FOUND = "ERROR-RANK-000";
    public static final String RANK_ERROR_DUPLICATE_NAME = "ERROR-RANK-001";

    /**
     * Account error code
     */
    public static final String ACCOUNT_ERROR_EXISTED = "ERROR-ACCOUNT-000";
    public static final String ACCOUNT_ERROR_PHONE_EXISTED = "ERROR-ACCOUNT-001";
    public static final String ACCOUNT_ERROR_EMAIL_EXISTED = "ERROR-ACCOUNT-002";

    /**
     * Customer error code
     */
    public static final String CUSTOMER_ERROR_UNAUTHORIZED = "ERROR_CUSTOMER-000";

    public static final String CUSTOMER_ERROR_NOT_FOUND = "ERROR-CUSTOMER-001";
    public static final String CUSTOMER_ADDRESS_ERROR_CANNOT_DELETE = "ERROR-CUSTOMER-002";
    public static final String CUSTOMER_RECHARGE_BAD_REQUEST = "ERROR-CUSTOMER-003";

    /**
     * Product review error code
     */
    public static final String PRODUCT_REVIEW_ERROR_UNAUTHORIZED = "ERROR-PRODUCT-REVIEW-000";
    public static final String PRODUCT_REVIEW_ERROR_NOT_FOUND = "ERROR-PRODUCT-REVIEW-001";
    public static final String PRODUCT_REVIEW_ERROR_BAD_REQUEST = "ERROR-PRODUCT-REVIEW-002";


    /**
     * Line Item error code
     */
    public static final String LINE_ITEM_ERROR_UNAUTHORIZED = "ERROR-ITEM-000";
    public static final String LINE_ITEM_ERROR_NOT_FOUND = "ERROR-ITEM-001";
    public static final String LINE_ITEM_ERROR_BAD_REQUEST = "ERROR-ITEM-002";


    /**
     * Orders Detail error code
     */
    public static final String ORDERS_DETAIL_ERROR_UNAUTHORIZED = "ERROR-ORDERS-DETAIL-000";

    public static final String ORDERS_DETAIL_ERROR_NOT_FOUND = "ERROR-ORDERS-DETAIL-001";
    public static final String ORDERS_DETAIL_ERROR_BAD_REQUEST = "ERROR-ORDERS-DETAIL-002";

    /**
     * Employee error code
     */
    public static final String EMPLOYEE_ERROR_UNAUTHORIZED = "ERROR-EMPLOYEE-000";

    public static final String EMPLOYEE_ERROR_NOT_FOUND = "ERROR-EMPLOYEE-001";

    /**
     * Product category error code
     */
    public static final String PRODUCT_CATEGORY_ERROR_UNAUTHORIZED = "PRODUCT-CATEGORY-ERROR-000";
    public static final String PRODUCT_CATEGORY_ERROR_EXISTED = "PRODUCT-CATEGORY-ERROR-001";
    public static final String PRODUCT_CATEGORY_ERROR_NOT_FOUND = "PRODUCT-CATEGORY-ERROR-002";

    /**
     * Variant template error code
     */
    public static final String VARIANT_TEMPLATE_NOT_FOUND = "VARIANT-TEMPLATE-ERROR-000";

    /**
     * Variant error code
     */
    public static final String VARIANT_NOT_FOUND = "VARIANT-ERROR-000";

    /**
     * Variant config error code
     */
    public static final String VARIANT_CONFIG_NOT_FOUND = "VARIANT-CONFIG-ERROR-000";

    /**
     * Product error code
     */
    public static final String PRODUCT_NOT_FOUND = "PRODUCT-ERROR-000";
    public static final String PRODUCT_UNAUTHORIZED = "PRODUCT-ERROR-001";

    /**
     * Product config error code
     */
    public static final String PRODUCT_CONFIG_NOT_FOUND = "PRODUCT-CONFIG-ERROR-000";

    /**
     * Product variant error code
     */
    public static final String PRODUCT_VARIANT_NOT_FOUND = "PRODUCT-VARIANT-ERROR-000";

    /**
     * Tag error code
     */
    public static final String TAG_EXISTED = "TAG-ERROR-000";
    public static final String TAG_NOT_FOUND = "TAG-ERROR-001";

    private ErrorCode() { throw new IllegalStateException("Utility class"); }
}

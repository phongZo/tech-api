package com.tech.api.constant;


import com.tech.api.utils.ConfigurationService;

public class Constants {
    public static final String ROOT_DIRECTORY =  ConfigurationService.getInstance().getString("file.upload-dir","/tmp/upload");

    public static final Integer MIN_OF_AMOUNT = 0;
    public static final Integer USER_KIND_ADMIN = 1;
    public static final Integer USER_KIND_CUSTOMER = 2;
    public static final Integer USER_KIND_EMPLOYEE = 3;
    public static final Integer USER_KIND_STORE_MANAGER = 4;

    public static final int LOYALTY_LEVEL_BRONZE = 0;
    public static final int LOYALTY_LEVEL_SILVER = 1;
    public static final int LOYALTY_LEVEL_GOLD = 2;
    public static final int LOYALTY_LEVEL_PLATINUM = 3;
    public static final int LOYALTY_LEVEL_DIAMOND = 4;
    public static final int LOYALTY_LEVEL_ROYAL = 5;

    public static final String LOYALTY_MAX_POINT = "4000,12000,25000,40000,60000,100000";

    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;

    public static final Integer GROUP_KIND_SUPER_ADMIN = 1;
    public static final Integer GROUP_KIND_CUSTOMER = 2;
    public static final Integer GROUP_KIND_EMPLOYEE = 3;
    public static final Integer GROUP_KIND_STORE_MANAGER = 4;

    public static final Integer PROMOTION_KIND_MONEY = 1;
    public static final Integer PROMOTION_KIND_PERCENT = 2;

    public static final Integer ADDRESS_TYPE_HOME = 1;
    public static final Integer ADDRESS_TYPE_OFFICE = 2;

    public static final Integer ORDERS_STATE_CREATED = 0;
    public static final Integer ORDERS_STATE_ACCEPTED = 1;
    public static final Integer ORDERS_STATE_SHIPPING = 2;
    public static final Integer ORDERS_STATE_COMPLETED = 3;
    public static final Integer ORDERS_STATE_CANCELED = 4;
    public static final Integer ORDERS_STATE_ARCHIVE = 5;

    public static final Integer ORDER_VAT = 10;

    public static final Integer LIMIT_CANCEL_ORDER_TIME = 3;

    public static final Integer MAX_ATTEMPT_FORGET_PWD = 5;
    public static final Integer MAX_TIME_FORGET_PWD = 5 * 60 * 1000; //5 minutes
    public static final Integer MAX_ATTEMPT_LOGIN = 5;
    public static final Integer MAX_TIME_VERIFY_ACCOUNT = 5 * 60 * 1000; //5 minutes

    public static final Integer STORE_PAY_DELIVERY_FEE = 1;
    public static final String ALLOW_SEE_GOODS_NOT_TRIAL = "CHOXEMHANGKHONGTHU";
    public static final Integer INSURANCE_FEE = 20000000;
    public static final String token = "66b81bdb-de07-11ed-943b-f6b926345ef9";

    public static final int CATEGORY_KIND_NEWS = 1;
    public static final int CATEGORY_KIND_JOB= 2;
    public static final int CATEGORY_KIND_DEPARTMENT = 3;

    public static final Integer PAYMENT_METHOD_COD = 1;
    public static final Integer PAYMENT_METHOD_ONLINE = 2;

    public static final Integer GENDER_MALE = 1;
    public static final Integer GENDER_FEMALE = 2;
    public static final Integer GENDER_OTHER = 3;

    public static final int LOCATION_KIND_PROVINCE = 1;
    public static final int LOCATION_KIND_DISTRICT = 2;
    public static final int LOCATION_KIND_WARD = 3;

    public static final String APP_WEB_CUSTOMER = "APP_WEB_CUSTOMER";
    public static final String APP_WEB_CMS = "APP_WEB_CMS";

    public static final int VARIANT_KIND_SIZE = 1;
    public static final int VARIANT_KIND_COLOR = 2;
    public static final int VARIANT_KIND_OTHER = 10;

    public static final int VARIANT_SINGLE_CHOICE = 1;
    public static final int VARIANT_MULTIPLE_CHOICE = 2;

    public static final int PRODUCT_KIND_SINGLE = 1;
    public static final int PRODUCT_KIND_GROUP = 2;

    public static final String POS_DEVICE_PERMISSION = "/product/list,/store/update-status,/product/update-sell-status,/orders/my-orders,/orders/update-state";

    private Constants(){
        throw new IllegalStateException("Utility class");
    }

}

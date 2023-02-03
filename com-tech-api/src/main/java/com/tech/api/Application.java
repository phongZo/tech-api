package com.tech.api;

import com.tech.api.storage.model.Account;
import com.tech.api.storage.model.Group;
import com.tech.api.storage.model.Permission;
import com.tech.api.storage.repository.GroupRepository;
import com.tech.api.storage.repository.PermissionRepository;
import com.tech.api.storage.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
@Slf4j
public class Application {

    @Autowired
    AccountRepository qrCodeStorageService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PermissionRepository permissionRepository;

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private void createAdminUserIfNotExist(){
        Account account = qrCodeStorageService.findAccountByUsername("admin");
        if(account == null){
            List<Permission> defaultPermission = addPermission();
            Group group = initGroupDefault(defaultPermission);



            account = new Account();
            account.setUsername("admin");
            account.setPassword(passwordEncoder.encode("admin"));
            account.setStatus(1);
            account.setKind(1);
            account.setFullName("Root account");
            account.setGroup(group);
            account.setIsSuperAdmin(true);
            qrCodeStorageService.save(account);
        }

    }

    private List<Permission> addPermission(){
        List<Permission> results = new ArrayList<>();
        //ACCOUNT
        results.add(Permission.builder().action("/v1/account/create_admin").description("Create admin").name("Create admin").nameGroup("Account").showMenu(false).build());
        results.add(Permission.builder().action("/v1/account/delete").description("Delete admin").name("Delete admin").nameGroup("Account").showMenu(false).build());
//        results.add(Permission.builder().action("/v1/account/forget_password").description("Forget Password").name("Forget Password").nameGroup("Account").showMenu(false).build());
        results.add(Permission.builder().action("/v1/account/get").description("Get account").name("Get account").nameGroup("Account").showMenu(false).build());
        results.add(Permission.builder().action("/v1/account/list").description("List account").name("List account").nameGroup("Account").showMenu(false).build());
//        results.add(Permission.builder().action("/v1/account/login").description("Login").name("Login").nameGroup("Account").showMenu(false).build());
        results.add(Permission.builder().action("/v1/account/logout").description("Logout").name("Logout").nameGroup("Account").showMenu(false).build());
        results.add(Permission.builder().action("/v1/account/profile").description("Get profile").name("Get profile").nameGroup("Account").showMenu(false).build());
        results.add(Permission.builder().action("/v1/account/request_forget_password").description("Request forget password").name("Request forget password").nameGroup("Account").showMenu(false).build());
        results.add(Permission.builder().action("/v1/account/update_admin").description("Update admin").name("Update admin").nameGroup("Account").showMenu(false).build());
        results.add(Permission.builder().action("/v1/account/update_profile").description("Update profile user").name("Update profile user").nameGroup("Account").showMenu(false).build());
        results.add(Permission.builder().action("/v1/account/verify_account").description("Verify account").name("Verify account").nameGroup("Account").showMenu(false).build());
        //CATEGORY
        results.add(Permission.builder().action("/v1/category/auto-complete").description("Category auto complete").name("Category auto complete").nameGroup("Category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/category/create").description("Create category").name("Create category").nameGroup("Category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/category/delete").description("Delete category").name("Delete category").nameGroup("Category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/category/get").description("Get category by id").name("Get category").nameGroup("Category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/category/list").description("Get list category").name("Get list category").nameGroup("Category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/category/update").description("Update category").name("Update category").nameGroup("Category").showMenu(false).build());
        //FILE
        results.add(Permission.builder().action("/v1/file/download").description("Download file").name("Download file").nameGroup("File").showMenu(false).build());
        results.add(Permission.builder().action("/v1/file/upload").description("Upload file").name("Upload file").nameGroup("File").showMenu(false).build());
        //GROUP
        results.add(Permission.builder().action("/v1/group/create").description("Create group").name("Create group").nameGroup("Group").showMenu(false).build());
        results.add(Permission.builder().action("/v1/group/delete").description("Delete group").name("Delete group").nameGroup("Group").showMenu(false).build());
        results.add(Permission.builder().action("/v1/group/get").description("Get group by id").name("Get group").nameGroup("Group").showMenu(false).build());
        results.add(Permission.builder().action("/v1/group/list").description("Get list group").name("List group").nameGroup("List group").showMenu(false).build());
        results.add(Permission.builder().action("/v1/group/list_combobox").description("Get list group combobox").name("List group combobox").nameGroup("Group").showMenu(false).build());
        results.add(Permission.builder().action("/v1/group/update").description("Update group").name("Update group").nameGroup("Group").showMenu(false).build());
        //NEWS
        results.add(Permission.builder().action("/v1/news/create").description("Create news").name("Create news").nameGroup("News").showMenu(false).build());
        results.add(Permission.builder().action("/v1/news/delete").description("Delete news").name("Delete news").nameGroup("News").showMenu(false).build());
        results.add(Permission.builder().action("/v1/news/get").description("Get news by id").name("Get news by id").nameGroup("News").showMenu(false).build());
        results.add(Permission.builder().action("/v1/news/list").description("Get list news").name("Get list news").nameGroup("News").showMenu(false).build());
        results.add(Permission.builder().action("/v1/news/update").description("Update news").name("Update news").nameGroup("News").showMenu(false).build());
        //PERMISSION
        results.add(Permission.builder().action("/v1/permission/create").description("Create permission").name("Create permission").nameGroup("Permission").showMenu(false).build());
        results.add(Permission.builder().action("/v1/permission/list").description("List permission").name("List permission").nameGroup("Permission").showMenu(false).build());
        results.add(Permission.builder().action("/v1/permission/update").description("Update permission").name("Update permission").nameGroup("Permission").showMenu(false).build());
        results.add(Permission.builder().action("/v1/permission/delete").description("Delete permission").name("Delete permission").nameGroup("Permission").showMenu(false).build());
        //LOCATION
        results.add(Permission.builder().action("/v1/locations/list").description("Chill list location").name("Chill list location").nameGroup("Locations").showMenu(false).build());
        results.add(Permission.builder().action("/v1/locations/get").description("Get location").name("Get location").nameGroup("Locations").showMenu(false).build());
        results.add(Permission.builder().action("/v1/locations/auto-complete").description("Auto complete location").name("Auto complete location").nameGroup("Locations").showMenu(false).build());
        results.add(Permission.builder().action("/v1/locations/create").description("Create location").name("Create location").nameGroup("Locations").showMenu(false).build());
        results.add(Permission.builder().action("/v1/locations/update").description("Update location").name("Update location").nameGroup("Locations").showMenu(false).build());
        results.add(Permission.builder().action("/v1/locations/delete").description("Delete location").name("Delete location").nameGroup("Locations").showMenu(false).build());
        //RANK
        results.add(Permission.builder().action("/v1/ranks/list").description("Get list rank").name("Get list rank").nameGroup("Ranks").showMenu(false).build());
        results.add(Permission.builder().action("/v1/ranks/auto-complete").description("Auto complete rank").name("Auto complete rank").nameGroup("Ranks").showMenu(false).build());
        results.add(Permission.builder().action("/v1/ranks/get").description("Get rank").name("Get rank").nameGroup("Ranks").showMenu(false).build());
        results.add(Permission.builder().action("/v1/ranks/create").description("Create rank").name("Create rank").nameGroup("Ranks").showMenu(false).build());
        results.add(Permission.builder().action("/v1/ranks/update").description("Update rank").name("Update rank").nameGroup("Ranks").showMenu(false).build());
        results.add(Permission.builder().action("/v1/ranks/delete").description("Delete rank").name("Delete rank").nameGroup("Ranks").showMenu(false).build());
        //CUSTOMER
        results.add(Permission.builder().action("/v1/customer/list").description("List customer").name("List customer").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/auto-complete").description("Auto complete customer").name("Auto complete customer").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/get").description("Get customer").name("Get customer").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/profile").description("Profile customer").name("Profile customer").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/create").description("Create customer").name("Create customer").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/update").description("Update customer admin").name("Update customer admin").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/update-profile").description("Update customer profile").name("Update customer profile").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/delete").description("Delete customer").name("Delete customer").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/address/list").description("List customer's addresses").name("List customer's addresses").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/address/get").description("Get customer's addresses").name("Get customer's addresses").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/address/create").description("Create customer's address").name("Create customer's addresses").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/address/update").description("Update customer's address").name("Update customer's addresses").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/address/delete").description("Delete customer's address").name("Delete customer's addresses").nameGroup("Customer").showMenu(false).build());
        results.add(Permission.builder().action("/v1/customer/address/default").description("Set default customer's address").name("Set default customer's address").nameGroup("Customer").showMenu(false).build());
        //EMPLOYEE
        results.add(Permission.builder().action("/v1/employee/list").description("List employee").name("List employee").nameGroup("Employee").showMenu(false).build());
        results.add(Permission.builder().action("/v1/employee/auto-complete").description("Auto complete employee").name("Auto complete employee").nameGroup("Employee").showMenu(false).build());
        results.add(Permission.builder().action("/v1/employee/get").description("Get employee").name("Get employee").nameGroup("Employee").showMenu(false).build());
        results.add(Permission.builder().action("/v1/employee/profile").description("Profile employee").name("Profile employee").nameGroup("Employee").showMenu(false).build());
        results.add(Permission.builder().action("/v1/employee/create").description("Create employee").name("Create employee").nameGroup("Employee").showMenu(false).build());
        results.add(Permission.builder().action("/v1/employee/update").description("Update employee").name("Update employee").nameGroup("Employee").showMenu(false).build());
        results.add(Permission.builder().action("/v1/employee/update-profile").description("Update employee profile").name("Update employee profile").nameGroup("Employee").showMenu(false).build());
        results.add(Permission.builder().action("/v1/employee/delete").description("Delete employee").name("Delete employee").nameGroup("Employee").showMenu(false).build());
        //STORE
        results.add(Permission.builder().action("/v1/store/list").description("Get list store").name("Get list store").nameGroup("Store").showMenu(false).build());
        results.add(Permission.builder().action("/v1/store/auto-complete").description("Auto complete store").name("Auto complete store").nameGroup("Store").showMenu(false).build());
        results.add(Permission.builder().action("/v1/store/get").description("Get store").name("Get store").nameGroup("Store").showMenu(false).build());
        results.add(Permission.builder().action("/v1/store/create").description("Create store").name("Create store").nameGroup("Store").showMenu(false).build());
        results.add(Permission.builder().action("/v1/store/update").description("Update store").name("Update store").nameGroup("Store").showMenu(false).build());
        results.add(Permission.builder().action("/v1/store/delete").description("Delete store").name("Delete store").nameGroup("Store").showMenu(false).build());
        //PRODUCT CATEGORY
        results.add(Permission.builder().action("/v1/product-category/list").description("Get list product category").name("Get list product category").nameGroup("Product category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product-category/auto-complete").description("Auto complete product category").name("Auto complete product category").nameGroup("Product category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product-category/get").description("Get product category").name("Get product category").nameGroup("Product category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product-category/create").description("Create product category").name("Create product category").nameGroup("Product category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product-category/update").description("Update product category").name("Update product category").nameGroup("Product category").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product-category/delete").description("Delete product category").name("Delete product category").nameGroup("Product category").showMenu(false).build());
        //VARIANT TEMPLATE
        results.add(Permission.builder().action("/v1/variant-template/list").description("Get list variant template").name("Get list variant template").nameGroup("Variant template").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant-template/auto-complete").description("Auto complete variant template").name("Auto complete variant template").nameGroup("Variant template").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant-template/get").description("Get variant template").name("Get variant template").nameGroup("Variant template").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant-template/create").description("Create variant template").name("Create variant template").nameGroup("Variant template").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant-template/update").description("Update variant template").name("Update variant template").nameGroup("Variant template").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant-template/delete").description("Delete variant template").name("Delete variant template").nameGroup("Variant template").showMenu(false).build());
        //VARIANT
        results.add(Permission.builder().action("/v1/variant/list").description("Get list variant").name("Get list variant").nameGroup("Variant").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant/auto-complete").description("Auto complete variant").name("Auto complete variant").nameGroup("Variant").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant/get").description("Get variant").name("Get variant").nameGroup("Variant").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant/create").description("Create variant").name("Create variant").nameGroup("Variant").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant/update").description("Update variant").name("Update variant").nameGroup("Variant").showMenu(false).build());
        results.add(Permission.builder().action("/v1/variant/delete").description("Delete variant").name("Delete variant").nameGroup("Variant").showMenu(false).build());
        //PRODUCT
        results.add(Permission.builder().action("/v1/product/list").description("Get list product").name("Get list product").nameGroup("Product").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product/auto-complete").description("Auto complete product").name("Auto complete product").nameGroup("Product").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product/get").description("Get product").name("Get product").nameGroup("Product").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product/create").description("Create product").name("Create product").nameGroup("Product").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product/update").description("Update product").name("Update product").nameGroup("Product").showMenu(false).build());
        results.add(Permission.builder().action("/v1/product/delete").description("Delete product").name("Delete product").nameGroup("Product").showMenu(false).build());
        //TAG
        results.add(Permission.builder().action("/v1/tag/list").description("Get list tag").name("Get list tag").nameGroup("Tag").showMenu(false).build());
        results.add(Permission.builder().action("/v1/tag/auto-complete").description("Auto complete tag").name("Auto complete tag").nameGroup("Tag").showMenu(false).build());
        results.add(Permission.builder().action("/v1/tag/get").description("Get tag").name("Get tag").nameGroup("Tag").showMenu(false).build());
        results.add(Permission.builder().action("/v1/tag/create").description("Create tag").name("Create tag").nameGroup("Tag").showMenu(false).build());
        results.add(Permission.builder().action("/v1/tag/update").description("Update tag").name("Update tag").nameGroup("Tag").showMenu(false).build());
        results.add(Permission.builder().action("/v1/tag/delete").description("Delete tag").name("Delete tag").nameGroup("Tag").showMenu(false).build());
        return permissionRepository.saveAll(results);
    }

    private Group initGroupDefault(List<Permission> defaultPermission){
        Group superAdminGroup = new Group();
        superAdminGroup.setKind(1);
        superAdminGroup.setName("ROLE SUPPER ADMIN");
        superAdminGroup.setId(1L);
        superAdminGroup.setPermissions(defaultPermission);
        return groupRepository.save(superAdminGroup);

    }

    @PostConstruct
    public void initialize() {
        createAdminUserIfNotExist();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}

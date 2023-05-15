package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.dto.account.ForgetPasswordDto;
import com.tech.api.dto.account.ValidateEmailDto;
import com.tech.api.dto.customer.*;
import com.tech.api.form.account.RequestValidateEmailForm;
import com.tech.api.form.customer.*;
import com.tech.api.form.promotion.CreatePromotionForm;
import com.tech.api.form.wallet.RechargeForm;
import com.tech.api.mapper.CustomerMapper;
import com.tech.api.mapper.CustomerPromotionMapper;
import com.tech.api.mapper.ProductMapper;
import com.tech.api.service.CommonApiService;
import com.tech.api.storage.criteria.CustomerCriteria;
import com.tech.api.storage.criteria.CustomerPromotionCriteria;
import com.tech.api.storage.model.Customer;
import com.tech.api.storage.model.CustomerPromotion;
import com.tech.api.storage.model.Group;
import com.tech.api.storage.model.Promotion;
import com.tech.api.storage.repository.*;
import com.tech.api.exception.RequestException;
import com.tech.api.storage.model.*;
import com.tech.api.form.customer.*;
import com.tech.api.storage.repository.*;
import com.tech.api.utils.AESUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class CustomerController extends ABasicController {
    private final AccountRepository accountRepository;
    private final GroupRepository groupRepository;
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerMapper customerMapper;
    private final CommonApiService commonApiService;

    @Autowired
    CustomerPromotionMapper customerPromotionMapper;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    CustomerPromotionRepository customerPromotionRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<CustomerAdminDto>> list(CustomerCriteria customerCriteria, BindingResult bindingResult, Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAll(customerCriteria.getSpecification(), pageable);
        List<CustomerAdminDto> customerDtoList = customerMapper.fromListCustomerEntityToListAdminDto(customerPage.getContent());
        return new ApiMessageDto<>(new ResponseListObj<>(customerDtoList, customerPage), "Get list successfully");
    }

    @PostMapping(value = "/promotion/exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> exchange(@Valid @RequestBody ExchangePromotionForm exchangePromotionForm, BindingResult bindingResult) {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.PROMOTION_ERROR_UNAUTHORIZED, "Not allowed to create.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Promotion promotion = promotionRepository.findById(exchangePromotionForm.getPromotionId()).orElse(null);
        if(promotion == null || !promotion.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.PROMOTION_ERROR_NOT_FOUND, "Not found promotion.");
        }
        Customer customer = getCurrentCustomer();
        if(customer.getPoint() < promotion.getPoint()){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Not enough point");
            return apiMessageDto;
        }
        CustomerPromotion customerPromotion = new CustomerPromotion();
        customerPromotion.setCustomer(customer);
        customerPromotion.setPromotion(promotion);
        customerPromotion.setIsInUse(false);

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, 1);
        dt = c.getTime();
        customerPromotion.setExpireDate(dt);
        customerPromotionRepository.save(customerPromotion);
        apiMessageDto.setMessage("Exchange promotion success");
        return apiMessageDto;
    }

    @GetMapping(value = "/promotion/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CustomerPromotionListObj> clientListPromotion(CustomerPromotionCriteria criteria, BindingResult bindingResult) {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_UNAUTHORIZED, "Not allowed to list promotion.");
        }
        Customer customer = getCurrentCustomer();
        ApiMessageDto<CustomerPromotionListObj> apiMessageDto = new ApiMessageDto<>();
        criteria.setCustomerId(customer.getId());
        criteria.setInUse(false);
        List<CustomerPromotion> list = customerPromotionRepository.findAll(criteria.getSpecification());
        List<CustomerPromotion> result = new ArrayList<>();
        for (CustomerPromotion customerPromotion : list){
            if(customerPromotion.getExpireDate().after(new Date())){
                result.add(customerPromotion);
            }
        }
        CustomerPromotionListObj obj = new CustomerPromotionListObj();
        obj.setPoint(customer.getPoint());
        obj.setData(customerPromotionMapper.fromListCustomerPromotionEntityToListDtoMapper(result));
        apiMessageDto.setData(obj);
        apiMessageDto.setMessage("Get list success");
        return apiMessageDto;
    }

    @PostMapping(value = "/promotion/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> approvePromotion(@RequestBody @Valid ApprovePromotionForm approvePromotionForm, BindingResult bindingResult) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_UNAUTHORIZED, "Not allowed to approve promotion.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        // check customer
        Customer customer = customerRepository.findById(approvePromotionForm.getCustomerId()).orElse(null);
        if(customer == null || !customer.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Not found customer.");
        }
        Promotion promotion = promotionRepository.findById(approvePromotionForm.getPromotionId()).orElse(null);
        if(promotion == null || !promotion.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.PROMOTION_ERROR_NOT_FOUND, "Not found promotion.");
        }
        if(approvePromotionForm.getExpiryDate().before(new Date())){
            throw new RequestException(ErrorCode.PROMOTION_ERROR_BAD_REQUEST, "Promotion expiry date not valid.");
        }
        CustomerPromotion customerPromotion = customerPromotionMapper.fromApprovePromotionFormToEntity(approvePromotionForm);
        customerPromotion.setPromotion(promotion);
        customerPromotionRepository.save(customerPromotion);
        apiMessageDto.setMessage("Approve success");
        return apiMessageDto;
    }

    private Customer getCurrentCustomer() {
        Customer customer = customerRepository.findCustomerByAccountId(getCurrentUserId());
        if(customer == null || !customer.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND,"Customer not found");
        }
        return customer;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<CustomerDto>> autoComplete(CustomerCriteria customerCriteria) {
        Page<Customer> customerPage = customerRepository.findAll(customerCriteria.getSpecification(), Pageable.unpaged());
        return new ApiMessageDto<>(
                customerMapper.fromListCustomerEntityToListDto(customerPage.getContent()),
                "Auto complete customer successfully"
        );
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CustomerAdminDto> get(@PathVariable("id") Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Customer not found"));
        return new ApiMessageDto<>(customerMapper.fromCustomerEntityToAdminDto(customer), "Get customer successfully");
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CustomerDto> profile() {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_UNAUTHORIZED, "Not allowed get profile");
        }
        Customer customer = customerRepository.findCustomerByAccountId(getCurrentUserId());
        if (customer == null || !customer.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Customer not found");
        }
        return new ApiMessageDto<>(customerMapper.fromCustomerEntityToDto(customer), "Get customer successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateCustomerForm createCustomerForm, BindingResult bindingResult) {
        if (accountRepository.findAccountByEmail(
                createCustomerForm.getEmail()
        ) != null)
            throw new RequestException(ErrorCode.ACCOUNT_ERROR_EXISTED, "Account is existed");

        Group groupCustomer = groupRepository.findFirstByKind(Constants.GROUP_KIND_CUSTOMER);
        if (groupCustomer == null) {
            throw new RequestException(ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        Customer customer = customerMapper.fromCustomerCreateFormToEntity(createCustomerForm);
        customer.getAccount().setGroup(groupCustomer);
        customer.getAccount().setKind(Constants.USER_KIND_CUSTOMER);
        //rank?
        customerRepository.save(customer);
        return new ApiMessageDto<>("Create customer successfully");
    }

    @PostMapping(value = "/request_validate_email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ValidateEmailDto> requestValidateEmail(@Valid @RequestBody RequestValidateEmailForm validateEmailForm, BindingResult bindingResult) {
        ApiMessageDto<ValidateEmailDto> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findFirstByEmail(validateEmailForm.getEmail());
        if(account != null && account.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.ACCOUNT_ERROR_EXISTED, "Email is existed");
        }
        if(account == null){
            account = new Account();
            account.setEmail(validateEmailForm.getEmail());
            account.setStatus(Constants.STATUS_PENDING);
            account.setIsNewAccount(true);

            Group group = groupRepository.findFirstByKind(Constants.GROUP_KIND_CUSTOMER);
            account.setGroup(group);
            account = accountRepository.save(account);
        }
        String otp = commonApiService.getOTPValidateEmail();
        account.setVerifyCode(otp);
        account.setVerifyTime(new Date());
        accountRepository.save(account);

        //send email
        commonApiService.sendEmail(account.getEmail(),"OTP: "+otp, "Verify account",false);

        ValidateEmailDto dto = new ValidateEmailDto();
        dto.setAccountId(account.getId());
        apiMessageDto.setResult(true);
        apiMessageDto.setData(dto);
        apiMessageDto.setMessage("Request validate email success, please check email.");
        return apiMessageDto;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> register(@Valid @RequestBody RegisterCustomerForm registerCustomerForm, BindingResult bindingResult) {
        Account account = accountRepository.findFirstByEmail(registerCustomerForm.getEmail());
        if (account == null || !account.getStatus().equals(Constants.STATUS_ACTIVE) || !account.getIsNewAccount())
            throw new RequestException(ErrorCode.ACCOUNT_ERROR_EXISTED, "Email not validated");

        Group groupCustomer = groupRepository.findFirstByKind(Constants.GROUP_KIND_CUSTOMER);
        if (groupCustomer == null) {
            throw new RequestException(ErrorCode.GROUP_ERROR_NOT_FOUND);
        }
        Customer customer = new Customer();
        customer.setAccount(account);
        customer = customerMapper.fromCustomerRegisterFormToEntity(registerCustomerForm);
        customer.getAccount().setGroup(groupCustomer);
        customer.getAccount().setKind(Constants.USER_KIND_CUSTOMER);
        //rank?
        Customer savedCustomer = customerRepository.save(customer);
        Promotion promotion = promotionRepository.findFirstByTitle("Ưu đãi hội viên mới");
        if(promotion != null){
            CustomerPromotion customerPromotion = new CustomerPromotion();
            customerPromotion.setCustomer(savedCustomer);
            customerPromotion.setPromotion(promotion);
            customerPromotion.setIsInUse(false);

            Date dt = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(dt);
            c.add(Calendar.DATE, 7);
            dt = c.getTime();
            customerPromotion.setExpireDate(dt);
            customerPromotionRepository.save(customerPromotion);
        }
        return new ApiMessageDto<>("Create customer successfully");
    }

    @PostMapping(value = "/wallet-recharge", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> WalletRecharge(@Valid @RequestBody RechargeForm rechargeForm, BindingResult bindingResult) {
        if(!isCustomer()){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_UNAUTHORIZED, "Not allow recharge");
        }
        Customer customer = customerRepository.findCustomerByAccountId(getCurrentUserId());
        if(customer == null){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Customer not found");
        }
        Double currentWalletMoney = customer.getWalletMoney();
        if(currentWalletMoney + rechargeForm.getRechargeMoney() != rechargeForm.getTotalMoney()){
            throw new RequestException(ErrorCode.CUSTOMER_RECHARGE_BAD_REQUEST, "Invalid recharge money");
        }
        currentWalletMoney += rechargeForm.getRechargeMoney();
        customer.setWalletMoney(currentWalletMoney);
        customerRepository.save(customer);
        return new ApiMessageDto<>("Recharge successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateCustomerForm updateCustomerForm, BindingResult bindingResult) {
        if (accountRepository.countAccountByPhoneOrEmail(
                updateCustomerForm.getPhone(), updateCustomerForm.getEmail()
        ) > 1)
            throw new RequestException(ErrorCode.ACCOUNT_ERROR_EXISTED, "Account is existed");
        Customer customer = customerRepository.findById(updateCustomerForm.getId())
                .orElseThrow(() -> new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Customer not found"));
        if (StringUtils.isNoneBlank(updateCustomerForm.getAvatar()) && !updateCustomerForm.getAvatar().equals(customer.getAccount().getAvatarPath()))
            commonApiService.deleteFile(customer.getAccount().getAvatarPath());
        customerMapper.fromUpdateCustomerFormToEntity(updateCustomerForm, customer);
        customerRepository.save(customer);
        accountRepository.save(customer.getAccount());
        return new ApiMessageDto<>("Update customer successfully");
    }

    @PutMapping(value = "/update-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateProfile(@Valid @RequestBody UpdateProfileCustomerForm updateProfileCustomerForm, BindingResult bindingResult) {
        if (!isCustomer()) {
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_UNAUTHORIZED, "Not allowed get list.");
        }
        Customer customer = customerRepository.findCustomerByAccountId(getCurrentUserId());
        if (customer == null || !customer.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Customer not found");
        }
        if (StringUtils.isNoneBlank(updateProfileCustomerForm.getAvatar()) && !updateProfileCustomerForm.getAvatar().equals(customer.getAccount().getAvatarPath()))
            commonApiService.deleteFile(customer.getAccount().getAvatarPath());
        customerMapper.fromUpdateProfileCustomerFormToEntity(updateProfileCustomerForm, customer);
        customerRepository.save(customer);
        accountRepository.save(customer.getAccount());
        return new ApiMessageDto<>("Update customer successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Customer not found"));
        commonApiService.deleteFile(customer.getAccount().getAvatarPath());
        customerRepository.delete(customer);
        return new ApiMessageDto<>("Delete customer successfully");
    }
}

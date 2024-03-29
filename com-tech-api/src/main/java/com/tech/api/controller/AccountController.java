package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.dto.account.AccountAdminDto;
import com.tech.api.dto.account.AccountDto;
import com.tech.api.dto.account.ForgetPasswordDto;
import com.tech.api.form.account.*;
import com.tech.api.jwt.UserJwt;
import com.tech.api.mapper.AccountMapper;
import com.tech.api.service.CommonApiService;
import com.tech.api.storage.criteria.AccountCriteria;
import com.tech.api.storage.model.Account;
import com.tech.api.storage.model.Customer;
import com.tech.api.storage.model.Employee;
import com.tech.api.storage.model.Group;
import com.tech.api.storage.repository.CustomerRepository;
import com.tech.api.storage.repository.EmployeeRepository;
import com.tech.api.storage.repository.GroupRepository;
import com.tech.api.utils.AESUtils;
import com.tech.api.utils.ConvertUtils;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.account.LoginDto;
import com.tech.api.exception.RequestException;
import com.tech.api.form.account.*;
import com.tech.api.intercepter.MyAuthentication;
import com.tech.api.jwt.JWTUtils;
import com.tech.api.storage.repository.AccountRepository;
import com.tech.api.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/v1/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AccountController extends ABasicController{
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CommonApiService commonApiService;

    @Autowired
    AccountMapper accountMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<AccountAdminDto>> getList(AccountCriteria accountCriteria, Pageable pageable){
        if(!isAdmin()){
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow get list");
        }
        ApiMessageDto<ResponseListObj<AccountAdminDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Account> accountPage = accountRepository.findAll(accountCriteria.getSpecification(),pageable);
        ResponseListObj<AccountAdminDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(accountMapper.fromEntityListToDtoList(accountPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(accountPage.getTotalPages());
        responseListObj.setTotalElements(accountPage.getTotalElements());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List account success");
        return apiMessageDto;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<LoginDto> login(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult) {

        ApiMessageDto<LoginDto> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByUsername(loginForm.getUsername());
        if (account == null || !passwordEncoder.matches(loginForm.getPassword(), account.getPassword()) || !Objects.equals(account.getStatus() , Constants.STATUS_ACTIVE)) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_LOGIN_FAILED, "Login fail, check your username or password");
        }
        if (Objects.equals(loginForm.getApp(), Constants.APP_WEB_CMS) && Objects.equals(account.getKind(), Constants.USER_KIND_CUSTOMER)) {
                throw new RequestException(ErrorCode.PERMISSION_ERROR_UNAUTHORIZED, "Login fail");
        }
        LoginDto loginDto = generateJWT(account);

        apiMessageDto.setData(loginDto);
        apiMessageDto.setMessage("Login account success");
        account.setLastLogin(new Date());
        //update lastLogin
        accountRepository.save(account);
        return apiMessageDto;
    }

    private LoginDto generateJWT(Account account) {
        //Tao xong tra ve cai gi?
        LocalDate parsedDate = LocalDate.now();
        parsedDate = parsedDate.plusDays(7);

        UserJwt qrJwt = new UserJwt();
        qrJwt.setAccountId(account.getId());
        qrJwt.setKind(account.getKind().toString());
        String appendStringRole = getAppendStringRole(account);


        qrJwt.setUsername(account.getUsername());
        qrJwt.setPemission(commonApiService.convertGroupToUri(account.getGroup().getPermissions())+appendStringRole);
        qrJwt.setUserKind(account.getKind());
        qrJwt.setIsSuperAdmin(account.getIsSuperAdmin());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(new MyAuthentication(qrJwt));

        log.info("jwt user ne: {}", qrJwt);
        String token = JWTUtils.createJWT(JWTUtils.ALGORITHMS_HMAC, "authenticationToken.getId().toString()", qrJwt, DateUtils.convertToDateViaInstant(parsedDate));
        LoginDto loginDto = new LoginDto();
        loginDto.setFullName(account.getFullName());
        loginDto.setId(account.getId());
        loginDto.setToken(token);
        loginDto.setUsername(account.getUsername());
        loginDto.setKind(account.getKind());
        if(account.getKind().equals(Constants.USER_KIND_ADMIN) && account.getIsSuperAdmin()) loginDto.setIsSuperAdmin(true);
        if(account.getKind().equals(Constants.USER_KIND_EMPLOYEE) || account.getKind().equals(Constants.USER_KIND_STORE_MANAGER)){
            Employee employee = employeeRepository.findById(account.getId()).orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND, "Not found employee"));
            loginDto.setStoreId(employee.getStore().getId());
            loginDto.setStoreName(employee.getStore().getName());
        }
        return loginDto;
    }

    @PostMapping(value = "/client-login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<LoginDto> login(@Valid @RequestBody ClientLoginForm clientLoginForm, BindingResult bindingResult) {

        ApiMessageDto<LoginDto> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByUsernameOrEmail(clientLoginForm.getUsernameOrEmail());
        if (account == null || !passwordEncoder.matches(clientLoginForm.getPassword(), account.getPassword()) || !Objects.equals(account.getStatus() , Constants.STATUS_ACTIVE)) {
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Login fail, check your username or password");
        }
        Customer customer = customerRepository.findCustomerByAccountId(account.getId());
        if(customer == null || !customer.getStatus().equals(Constants.STATUS_ACTIVE)){
            throw new RequestException(ErrorCode.CUSTOMER_ERROR_NOT_FOUND, "Not found customer");
        }
        LoginDto loginDto = generateJWT(account);
        loginDto.setCustomerId(customer.getId());
        apiMessageDto.setData(loginDto);
        apiMessageDto.setMessage("Login account success");
        account.setLastLogin(new Date());

        //update lastLogin
        accountRepository.save(account);
        return apiMessageDto;
    }

    private String getAppendStringRole (Account account) {
        String appendStringRole = "";
        if(Objects.equals(account.getKind(), Constants.USER_KIND_ADMIN)){
            appendStringRole = "/account/profile,/account/update_profile,/account/logout";
            if(account.getIsSuperAdmin()){
                appendStringRole += ",/orders/archive";
            }
        } else if(Objects.equals(account.getKind(), Constants.USER_KIND_CUSTOMER)) {
            appendStringRole = "/product/client-recommend-list,/customer/profile,/customer/update-profile,/account/logout";
        } else if(Objects.equals(account.getKind(), Constants.USER_KIND_EMPLOYEE)) {
            appendStringRole = "/product/list-to-create-order,/employee/profile,/employee/update-profile,/account/logout";
        } else if(Objects.equals(account.getKind(), Constants.USER_KIND_STORE_MANAGER)) {
            appendStringRole = "/employee/profile,/employee/update-profile,/account/logout";
        } else {
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED);
        }
        return appendStringRole;
    }

    @PostMapping(value = "/create_admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> createAdmin(@Valid @RequestBody CreateAccountAdminForm createAccountAdminForm, BindingResult bindingResult) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow create.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Long accountCheck = accountRepository
                .countAccountByUsername(createAccountAdminForm.getUsername());
        if (accountCheck > 0) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Username is existed");
        }

        Group group = groupRepository.findById(createAccountAdminForm.getGroupId()).orElse(null);
        if (group == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Group does not exist!");
        }

        Account account = accountMapper.fromCreateAccountAdminFormToAdmin(createAccountAdminForm);
        account.setGroup(group);
        account.setPassword(passwordEncoder.encode(createAccountAdminForm.getPassword()));
        account.setKind(Constants.USER_KIND_ADMIN);

        accountRepository.save(account);
        apiMessageDto.setMessage("Create account admin success");
        return apiMessageDto;

    }

    @PutMapping(value = "/update_admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateAdmin(@Valid @RequestBody UpdateAccountAdminForm updateAccountAdminForm, BindingResult bindingResult) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allowed to update");
        }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(updateAccountAdminForm.getId()).orElse(null);
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Not found");
        }

        accountMapper.mappingFormUpdateAdminToEntity(updateAccountAdminForm, account);
        if (StringUtils.isNoneBlank(updateAccountAdminForm.getPassword())) {
            account.setPassword(passwordEncoder.encode(updateAccountAdminForm.getPassword()));
        }
        account.setFullName(updateAccountAdminForm.getFullName());
        if (StringUtils.isNoneBlank(updateAccountAdminForm.getAvatarPath())) {
            if(!updateAccountAdminForm.getAvatarPath().equals(account.getAvatarPath())){
                //delete old image
                commonApiService.deleteFile(account.getAvatarPath());
            }
            account.setAvatarPath(updateAccountAdminForm.getAvatarPath());
        }

        accountRepository.save(account);

        apiMessageDto.setMessage("Update account admin success");
        return apiMessageDto;

    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AccountDto> profile() {
        long id = getCurrentUserId();
        Account account = accountRepository.findById(id).orElse(null);
        if(account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Not found account");
        }
        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(accountMapper.fromEntityToAccountDto(account));
        apiMessageDto.setMessage("Get Account success");
        return apiMessageDto;

    }

    @PutMapping(value = "/update_profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateProfileAdmin(@Valid @RequestBody UpdateProfileAdminForm updateProfileAdminForm, BindingResult bindingResult) {

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        long id = getCurrentUserId();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Not found");
        }
        if(!passwordEncoder.matches(updateProfileAdminForm.getOldPassword(), account.getPassword())){
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_MATCH, "Old password not match");
        }

        if (StringUtils.isNoneBlank(updateProfileAdminForm.getPassword())) {
            account.setPassword(passwordEncoder.encode(updateProfileAdminForm.getPassword()));
        }
        if (StringUtils.isNoneBlank(updateProfileAdminForm.getAvatar())) {
            if(!updateProfileAdminForm.getAvatar().equals(account.getAvatarPath())){
                //delete old image
                commonApiService.deleteFile(account.getAvatarPath());
            }
            account.setAvatarPath(updateProfileAdminForm.getAvatar());
        }
        accountMapper.mappingFormUpdateProfileToEntity(updateProfileAdminForm, account);
        accountRepository.save(account);

        apiMessageDto.setMessage("Update admin account success");
        return apiMessageDto;

    }

    @Transactional
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> logout() {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Logout success");
        return apiMessageDto;
    }


    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AccountAdminDto> get(@PathVariable("id") Long id) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allowed to get.");
        }
        Account account = accountRepository.findById(id).orElse(null);
        ApiMessageDto<AccountAdminDto> apiMessageDto = new ApiMessageDto<>();
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Not found account");
        }
        apiMessageDto.setData(accountMapper.fromEntityToAccountAdminDto(account));
        apiMessageDto.setMessage("Get shop profile success");
        return apiMessageDto;

    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        if(!isAdmin()){
            throw new RequestException(ErrorCode.GENERAL_ERROR_UNAUTHORIZED, "Not allow delete");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Account not found");
        }
        commonApiService.deleteFile(account.getAvatarPath());
        accountRepository.deleteById(id);
        apiMessageDto.setMessage("Delete Account success");
        return apiMessageDto;
    }

    @PostMapping(value = "/request_forget_password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ForgetPasswordDto> requestForgetPassword(@Valid @RequestBody RequestForgetPasswordForm forgetForm, BindingResult bindingResult){
        ApiMessageDto<ForgetPasswordDto> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(forgetForm.getEmail());
        if (account == null) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Account not found.");
        }

        String otp = commonApiService.getOTPForgetPassword();
        account.setAttemptCode(0);
        account.setResetPwdCode(otp);
        account.setResetPwdTime(new Date());
        accountRepository.save(account);

        //send email
        commonApiService.sendEmail(account.getEmail(),"OTP: "+otp, "Reset password",false);

        ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
        String hash = AESUtils.encrypt (account.getId()+";"+otp, true);
        forgetPasswordDto.setIdHash(hash);

        apiMessageDto.setResult(true);
        apiMessageDto.setData(forgetPasswordDto);
        apiMessageDto.setMessage("Request forget password success, please check email.");
        return  apiMessageDto;
    }

    @PostMapping(value = "/forget_password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<Long> forgetPassword(@Valid @RequestBody ForgetPasswordForm forgetForm, BindingResult bindingResult){
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();

        String[] hash = AESUtils.decrypt(forgetForm.getIdHash(),true).split(";",2);
        Long id = ConvertUtils.convertStringToLong(hash[0]);
        if(Objects.equals(id,0)){
            throw new RequestException(ErrorCode.GENERAL_ERROR_WRONG_HASH, "Wrong hash");
        }

        Account account = accountRepository.findById(id).orElse(null);
        if (account == null ) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "account not found.");
        }

        if(account.getAttemptCode() >= Constants.MAX_ATTEMPT_FORGET_PWD){
            throw new RequestException(ErrorCode.GENERAL_ERROR_LOCKED, "Account locked");
        }

        if(!account.getResetPwdCode().equals(forgetForm.getOtp()) ||
                (new Date().getTime() - account.getResetPwdTime().getTime() >= Constants.MAX_TIME_FORGET_PWD)){
            //tang so lan
            account.setAttemptCode(account.getAttemptCode()+1);
            accountRepository.save(account);

            throw new RequestException(ErrorCode.GENERAL_ERROR_INVALID, "Code invalid");
        }

        account.setResetPwdTime(null);
        account.setResetPwdCode(null);
        account.setAttemptCode(null);
        account.setPassword(passwordEncoder.encode(forgetForm.getNewPassword()));
        accountRepository.save(account);

        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Change password success.");
        return  apiMessageDto;
    }

    @PostMapping(value = "/verify_account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> verify(@RequestBody @Valid VerifyForm verifyForm, BindingResult bindingResult){
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        Account account = accountRepository.findById(verifyForm.getId()).orElse(null);
        if (account == null ) {
            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Account is not found");
        }

        if(!account.getVerifyCode().equals(verifyForm.getOtp()) ||
           (new Date().getTime() - account.getVerifyTime().getTime() >= Constants.MAX_TIME_VERIFY_ACCOUNT)){

            throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_MATCH, "Otp not match");
        }

        account.setVerifyTime(null);
        account.setVerifyCode(null);
        account.setStatus(Constants.STATUS_ACTIVE);
        accountRepository.save(account);

        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Verify account success.");

        return apiMessageDto;
    }
}

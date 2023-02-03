package com.tech.api.controller;

import com.tech.api.constant.Constants;
import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ResponseListObj;
import com.tech.api.form.employee.CreateEmployeeForm;
import com.tech.api.form.employee.UpdateEmployeeForm;
import com.tech.api.mapper.EmployeeMapper;
import com.tech.api.service.CommonApiService;
import com.tech.api.storage.criteria.EmployeeCriteria;
import com.tech.api.storage.model.Account;
import com.tech.api.storage.model.Group;
import com.tech.api.dto.ErrorCode;
import com.tech.api.dto.employee.EmployeeAdminDto;
import com.tech.api.dto.employee.EmployeeDto;
import com.tech.api.exception.RequestException;
import com.tech.api.form.employee.UpdateProfileEmployeeForm;
import com.tech.api.storage.model.Category;
import com.tech.api.storage.model.Employee;
import com.tech.api.storage.repository.AccountRepository;
import com.tech.api.storage.repository.CategoryRepository;
import com.tech.api.storage.repository.EmployeeRepository;
import com.tech.api.storage.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/employee")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController extends ABasicController {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final AccountRepository accountRepository;
    private final GroupRepository groupRepository;
    private final EmployeeRepository employeeRepository;
    private final CategoryRepository categoryRepository;
    private final EmployeeMapper employeeMapper;
    private final CommonApiService commonApiService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<EmployeeAdminDto>> list(EmployeeCriteria employeeCriteria, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(employeeCriteria.getSpecification(), pageable);
        List<EmployeeAdminDto> employeeAdminDtoList = employeeMapper.fromEmployeeEntityListToAdminDtoList(employeePage.getContent());
        return new ApiMessageDto<>(new ResponseListObj<>(employeeAdminDtoList, employeePage), "Get list successfully");
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<List<EmployeeDto>> autoComplete(EmployeeCriteria employeeCriteria) {
        Page<Employee> employeePage = employeeRepository.findAll(employeeCriteria.getSpecification(), Pageable.unpaged());
        return new ApiMessageDto<>(
                employeeMapper.fromListEmployeeEntityToListDto(employeePage.getContent()),
                "Get list successfully"
        );
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<EmployeeAdminDto> get(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND, "Employee not found"));
        return new ApiMessageDto<>(employeeMapper.fromEmployeeEntityToAdminDto(employee), "Get employee successfully");
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<EmployeeDto> profile() {
        Employee employee = employeeRepository.findById(getCurrentUserId())
                .orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND, "Employee not found"));
        return new ApiMessageDto<>(employeeMapper.fromEmployeeEntityToDto(employee), "Get employee successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateEmployeeForm createEmployeeForm, BindingResult bindingResult) {
        if (accountRepository.countAccountByUsernameOrEmailOrPhone(
                createEmployeeForm.getUsername(), createEmployeeForm.getEmail(), createEmployeeForm.getPhone()
        ) > 0)
            throw new RequestException(ErrorCode.ACCOUNT_ERROR_EXISTED, "Account is existed");
        Group groupEmployee = groupRepository.findById(createEmployeeForm.getGroupId())
                .orElseThrow(() -> new RequestException(ErrorCode.GENERAL_ERROR_NOT_FOUND, "Group does not exist!"));
        Category department = categoryRepository.findById(createEmployeeForm.getDepartmentId())
                .orElseThrow(() -> new RequestException(ErrorCode.CATEGORY_ERROR_NOT_FOUND, "Department not found"));
        Category job = categoryRepository.findById(createEmployeeForm.getJobId())
                .orElseThrow(() -> new RequestException(ErrorCode.CATEGORY_ERROR_NOT_FOUND, "Job not found"));
        Employee employee = employeeMapper.fromCreateEmployeeFormToEntity(createEmployeeForm);
        employee.setDepartment(department);
        employee.setJob(job);
        employee.getAccount().setGroup(groupEmployee);
        employee.getAccount().setKind(Constants.GROUP_KIND_EMPLOYEE);
        employeeRepository.save(employee);
        return new ApiMessageDto<>("Create employee successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateEmployeeForm updateEmployeeForm, BindingResult bindingResult) {
        if (accountRepository.countAccountByPhoneOrEmail(
                updateEmployeeForm.getPhone(), updateEmployeeForm.getEmail()
        ) > 1)
            throw new RequestException(ErrorCode.ACCOUNT_ERROR_EXISTED, "Account is existed");
        Employee employee = employeeRepository.findById(updateEmployeeForm.getId())
                .orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND, "Employee not found"));
        if (!Objects.equals(updateEmployeeForm.getDepartmentId(), employee.getDepartment().getId())) {
            Category department = categoryRepository.findById(updateEmployeeForm.getDepartmentId())
                    .orElseThrow(() -> new RequestException(ErrorCode.CATEGORY_ERROR_NOT_FOUND, "Department not found"));
            employee.setDepartment(department);
        }
        if (!Objects.equals(updateEmployeeForm.getJobId(), employee.getJob().getId())) {
            Category job = categoryRepository.findById(updateEmployeeForm.getJobId())
                    .orElseThrow(() -> new RequestException(ErrorCode.CATEGORY_ERROR_NOT_FOUND, "Job not found"));
            employee.setJob(job);
        }

        if (StringUtils.isNoneBlank(updateEmployeeForm.getAvatar()) && !updateEmployeeForm.getAvatar().equals(employee.getAccount().getAvatarPath()))
            commonApiService.deleteFile(employee.getAccount().getAvatarPath());
        employeeMapper.fromUpdateEmployeeFormToEntity(updateEmployeeForm, employee);
        employeeRepository.save(employee);
        accountRepository.save(employee.getAccount());
        return new ApiMessageDto<>("Update employee successfully");
    }

    @PutMapping(value = "/update-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateProfile(@Valid @RequestBody UpdateProfileEmployeeForm updateProfileEmployeeForm, BindingResult bindingResult) {
        if (!isEmployee()) {
            throw new RequestException(ErrorCode.EMPLOYEE_ERROR_UNAUTHORIZED, "Not allowed to update profile");
        }
        Account accountEmployee = employeeRepository.findById(getCurrentUserId())
                .orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND, "Employee not found"))
                .getAccount();
        if (StringUtils.isNoneBlank(updateProfileEmployeeForm.getPassword(), updateProfileEmployeeForm.getOldPassword())) {
            if (!passwordEncoder.matches(updateProfileEmployeeForm.getOldPassword(), accountEmployee.getPassword())) {
                throw new RequestException(ErrorCode.GENERAL_ERROR_NOT_MATCH, "Old password not match");
            }
            accountEmployee.setPassword(passwordEncoder.encode(updateProfileEmployeeForm.getPassword()));
        }
        if (StringUtils.isNoneBlank(updateProfileEmployeeForm.getAvatar())) {
            if(!updateProfileEmployeeForm.getAvatar().equals(accountEmployee.getAvatarPath())) {
                commonApiService.deleteFile(accountEmployee.getAvatarPath());
            }
            accountEmployee.setAvatarPath(updateProfileEmployeeForm.getAvatar());
        }
        accountRepository.save(accountEmployee);
        return new ApiMessageDto<>("Change profile successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RequestException(ErrorCode.EMPLOYEE_ERROR_NOT_FOUND, "Employee not found"));
        commonApiService.deleteFile(employee.getAccount().getAvatarPath());
        employeeRepository.delete(employee);
        return new ApiMessageDto<>("Delete employee successfully");
    }
}

package com.tech.api.mapper;

import com.tech.api.dto.employee.EmployeeAdminDto;
import com.tech.api.dto.employee.EmployeeDto;
import com.tech.api.form.employee.CreateEmployeeForm;
import com.tech.api.form.employee.UpdateEmployeeForm;
import com.tech.api.storage.model.Account;
import com.tech.api.storage.model.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-05T13:12:04+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Employee fromCreateEmployeeFormToEntity(CreateEmployeeForm createEmployeeForm) {
        if ( createEmployeeForm == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setAccount( createEmployeeFormToAccount( createEmployeeForm ) );
        employee.setNote( createEmployeeForm.getNote() );

        return employee;
    }

    @Override
    public EmployeeDto fromEmployeeEntityToDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setNote( employee.getNote() );
        employeeDto.setId( employee.getId() );
        employeeDto.setDepartment( categoryMapper.fromEntityToAdminDto( employee.getDepartment() ) );
        employeeDto.setJob( categoryMapper.fromEntityToAdminDto( employee.getJob() ) );
        employeeDto.setAccount( accountMapper.fromEntityToAccountDto( employee.getAccount() ) );

        return employeeDto;
    }

    @Override
    public List<EmployeeDto> fromListEmployeeEntityToListDto(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeDto> list = new ArrayList<EmployeeDto>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( fromEmployeeEntityToDto( employee ) );
        }

        return list;
    }

    @Override
    public EmployeeAdminDto fromEmployeeEntityToAdminDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeAdminDto employeeAdminDto = new EmployeeAdminDto();

        employeeAdminDto.setNote( employee.getNote() );
        employeeAdminDto.setId( employee.getId() );
        employeeAdminDto.setDepartment( categoryMapper.fromEntityToAdminDto( employee.getDepartment() ) );
        employeeAdminDto.setJob( categoryMapper.fromEntityToAdminDto( employee.getJob() ) );
        employeeAdminDto.setAccount( accountMapper.fromEntityToAccountAdminDto( employee.getAccount() ) );

        return employeeAdminDto;
    }

    @Override
    public List<EmployeeAdminDto> fromEmployeeEntityListToAdminDtoList(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeAdminDto> list = new ArrayList<EmployeeAdminDto>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( fromEmployeeEntityToAdminDto( employee ) );
        }

        return list;
    }

    @Override
    public void fromUpdateEmployeeFormToEntity(UpdateEmployeeForm updateEmployeeForm, Employee employee) {
        if ( updateEmployeeForm == null ) {
            return;
        }

        if ( employee.getAccount() == null ) {
            employee.setAccount( new Account() );
        }
        updateEmployeeFormToAccount( updateEmployeeForm, employee.getAccount() );
        if ( updateEmployeeForm.getNote() != null ) {
            employee.setNote( updateEmployeeForm.getNote() );
        }
    }

    protected Account createEmployeeFormToAccount(CreateEmployeeForm createEmployeeForm) {
        if ( createEmployeeForm == null ) {
            return null;
        }

        Account account = new Account();

        account.setUsername( createEmployeeForm.getUsername() );
        account.setFullName( createEmployeeForm.getFullName() );
        account.setAvatarPath( createEmployeeForm.getAvatar() );
        account.setEmail( createEmployeeForm.getEmail() );
        account.setPassword( accountMapper.encodePassword( createEmployeeForm.getPassword() ) );
        account.setPhone( createEmployeeForm.getPhone() );
        account.setStatus( createEmployeeForm.getStatus() );

        return account;
    }

    protected void updateEmployeeFormToAccount(UpdateEmployeeForm updateEmployeeForm, Account mappingTarget) {
        if ( updateEmployeeForm == null ) {
            return;
        }

        if ( updateEmployeeForm.getFullName() != null ) {
            mappingTarget.setFullName( updateEmployeeForm.getFullName() );
        }
        if ( updateEmployeeForm.getAvatar() != null ) {
            mappingTarget.setAvatarPath( updateEmployeeForm.getAvatar() );
        }
        if ( updateEmployeeForm.getEmail() != null ) {
            mappingTarget.setEmail( updateEmployeeForm.getEmail() );
        }
        if ( updateEmployeeForm.getPassword() != null ) {
            mappingTarget.setPassword( accountMapper.encodePassword( updateEmployeeForm.getPassword() ) );
        }
        if ( updateEmployeeForm.getPhone() != null ) {
            mappingTarget.setPhone( updateEmployeeForm.getPhone() );
        }
        if ( updateEmployeeForm.getStatus() != null ) {
            mappingTarget.setStatus( updateEmployeeForm.getStatus() );
        }
    }
}

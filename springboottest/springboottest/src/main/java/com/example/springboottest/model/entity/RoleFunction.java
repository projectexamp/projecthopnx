package com.example.springboottest.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_role_function", schema = "small_project_aht", catalog = "")
public class RoleFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Basic
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Basic
    @Column(name = "FUNCTION_ID")
    private Long functionId;


    @Basic
    @Column(name = "IS_ACTIVE")
    private Long isActive;

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getIsActive() {
        return isActive;
    }

    public void setIsActive(Long isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleFunction that = (RoleFunction) o;
        return id == that.id &&
                roleId == that.roleId &&
                isActive == that.isActive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, isActive);
    }
}

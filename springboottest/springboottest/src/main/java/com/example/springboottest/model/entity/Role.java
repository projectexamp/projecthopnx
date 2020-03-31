package com.example.springboottest.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_role", schema = "small_project_aht", catalog = "")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Basic
    @Column(name = "STATUS")
    private Long status;

    @Basic
    @Column(name = "ROLE_NAME")
    private String roleName;

    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    @Basic
    @Column(name = "ROLE_CODE")
    private String roleCode;

    @Basic
    @Column(name = "ROLE_ORDER")
    private Long roleOrder;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Long getRoleOrder() {
        return roleOrder;
    }

    public void setRoleOrder(Long roleOrder) {
        this.roleOrder = roleOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role tblRole = (Role) o;
        return id == tblRole.id &&
                status == tblRole.status &&
                roleName == tblRole.roleName &&
                description == tblRole.description &&
                roleCode == tblRole.roleCode &&
                roleOrder == tblRole.roleOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, roleName, description, roleCode, roleOrder);
    }
}

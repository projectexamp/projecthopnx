package com.example.springboottest.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_function", schema = "small_project_aht", catalog = "")
public class Function {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Basic
    @Column(name = "STATUS")
    private Long status;

    @Basic
    @Column(name = "FUNCTION_ORDER")
    private Long functionOrder;

    @Basic
    @Column(name = "FUNCTION_URL")
    private String functionUrl;

    @Basic
    @Column(name = "FUNCTION_NAME")
    private String functionName;

    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    @Basic
    @Column(name = "FUNCTION_CODE")
    private String functionCode;

    @Basic
    @Column(name = "PARENT_ID")
    private String parentId;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

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

    public Long getFunctionOrder() {
        return functionOrder;
    }

    public void setFunctionOrder(Long functionOrder) {
        this.functionOrder = functionOrder;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }


    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function that = (Function) o;
        return id == that.id &&
                status == that.status &&
                functionOrder == that.functionOrder &&
                Objects.equals(functionUrl, that.functionUrl) &&
                Objects.equals(functionName, that.functionName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(functionCode, that.functionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, functionOrder, functionUrl, functionName, description, functionCode);
    }
}

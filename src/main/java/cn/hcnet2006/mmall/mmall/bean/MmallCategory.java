package cn.hcnet2006.mmall.mmall.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * 类描述：set集合针对String 类型和8大基础数据类型  过滤掉重复数据,
 * 如果存放的是其他类型对象，则需要重写hashCode方法和equals方法,
 * 当hashcode相等时(先执行hashCode方法)，则会去执行equals方法，比较每个属性的值
 * 如果一致的话,则不会存进set，否则加入set集合
 */

public class MmallCategory implements Serializable {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MmallCategory category = (MmallCategory) o;

        //只是比较id,只有属性值一致才会返回true
        return id != null ? id.equals(category.id) : category.id == null;
    }

    /**
     * 重写hashCode方法，返回的hashCode不一样才会再去比较每一个属性的值
     * @return
     */
    @Override
    public int hashCode() {
        //return id.hashCode
        return id != null ? id.hashCode() : 0;
    }
}
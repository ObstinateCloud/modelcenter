package com.sgcc.web.entity;

public class Demo {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户id
     * @return user_id 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 性别
     * @return sex 性别
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 性别
     * @param sex 性别
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 用户名
     * @return username 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @mbg.generated 2019-03-17
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", sex=").append(sex);
        sb.append(", username=").append(username);
        sb.append("]");
        return sb.toString();
    }
}
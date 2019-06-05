package com.sgcc.web.mapper;

import com.sgcc.web.entity.Demo;
import com.sgcc.web.entity.DemoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DemoMapper {
    /**
     *
     * @mbg.generated 2019-03-17
     */
    long countByExample(DemoExample example);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    int deleteByExample(DemoExample example);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    int deleteByPrimaryKey(String userId);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    int insert(Demo record);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    int insertSelective(Demo record);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    List<Demo> selectByExample(DemoExample example);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    Demo selectByPrimaryKey(String userId);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    int updateByExampleSelective(@Param("record") Demo record, @Param("example") DemoExample example);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    int updateByExample(@Param("record") Demo record, @Param("example") DemoExample example);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    int updateByPrimaryKeySelective(Demo record);

    /**
     *
     * @mbg.generated 2019-03-17
     */
    int updateByPrimaryKey(Demo record);
}
package com.zzy.mall.mapper;

import com.zzy.mall.model.SmsFlashPromotion;
import com.zzy.mall.model.SmsFlashPromotionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsFlashPromotionMapper {
    long countByExample(SmsFlashPromotionExample example);

    int deleteByExample(SmsFlashPromotionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsFlashPromotion row);

    int insertSelective(SmsFlashPromotion row);

    List<SmsFlashPromotion> selectByExample(SmsFlashPromotionExample example);

    SmsFlashPromotion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") SmsFlashPromotion row, @Param("example") SmsFlashPromotionExample example);

    int updateByExample(@Param("row") SmsFlashPromotion row, @Param("example") SmsFlashPromotionExample example);

    int updateByPrimaryKeySelective(SmsFlashPromotion row);

    int updateByPrimaryKey(SmsFlashPromotion row);
}
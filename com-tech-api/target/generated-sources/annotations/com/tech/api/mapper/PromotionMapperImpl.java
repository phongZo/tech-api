package com.tech.api.mapper;

import com.tech.api.dto.promotion.PromotionDto;
import com.tech.api.form.promotion.CreatePromotionForm;
import com.tech.api.storage.model.Promotion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-03T19:16:09+0700",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class PromotionMapperImpl implements PromotionMapper {

    @Override
    public Promotion fromCreateFormToEntity(CreatePromotionForm createPromotionForm) {
        if ( createPromotionForm == null ) {
            return null;
        }

        Promotion promotion = new Promotion();

        promotion.setKind( createPromotionForm.getKind() );
        promotion.setDescription( createPromotionForm.getDescription() );
        promotion.setMaxValueForPercent( createPromotionForm.getMaxValueForPercent() );
        promotion.setTitle( createPromotionForm.getTitle() );
        promotion.setValue( createPromotionForm.getValue() );

        return promotion;
    }

    @Override
    public PromotionDto fromEntityToRankDto(Promotion promotion) {
        if ( promotion == null ) {
            return null;
        }

        PromotionDto promotionDto = new PromotionDto();

        promotionDto.setKind( promotion.getKind() );
        promotionDto.setDescription( promotion.getDescription() );
        promotionDto.setMaxValueForPercent( promotion.getMaxValueForPercent() );
        promotionDto.setId( promotion.getId() );
        promotionDto.setTitle( promotion.getTitle() );
        promotionDto.setValue( promotion.getValue() );
        promotionDto.setStatus( promotion.getStatus() );

        return promotionDto;
    }
}

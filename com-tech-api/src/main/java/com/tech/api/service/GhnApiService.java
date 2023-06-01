package com.tech.api.service;

import java.util.List;

import com.tech.api.dto.ApiMessageDto;
import com.tech.api.dto.ghn.DeliveryServiceResponse;
import com.tech.api.form.orders.GetDeliveryFeeForm;
import com.tech.api.form.orders.GetDeliveryServiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GhnApiService {
    @Autowired
    RestService restService;

    public DeliveryServiceResponse getDeliveryService(int fromDistrictId, String fromWardCode, Long toDistrictId, Long shopId, String toWardCode){
        GetDeliveryServiceForm form = new GetDeliveryServiceForm();
        form.setShopId(shopId);
        form.setFromDistrictId(fromDistrictId);
        form.setToDistrictId(toDistrictId);

        String path = "/shiip/public-api/v2/shipping-order/available-services";
        ApiMessageDto<List<DeliveryServiceResponse>> result = restService.POST_FOR_LIST(null,form,path,null,DeliveryServiceResponse.class);
        DeliveryServiceResponse item = result.getData().get(0);
        if(item != null){
            DeliveryServiceResponse fee = getDeliveryFee(fromDistrictId,toDistrictId,shopId,item.getServiceId(),item.getType(),toWardCode);
            DeliveryServiceResponse deliveryTime = calculateExpectedTime(fromDistrictId,fromWardCode,toDistrictId,shopId,item.getServiceId(),toWardCode);
            item.setTotal(fee.getTotal());
            item.setLeadTime(deliveryTime.getLeadTime());
        }
        return item;
    }


    public DeliveryServiceResponse getDeliveryFee(int fromDistrictId, Long toDistrictId, Long shopId, int serviceId, int serviceTypeId, String toWardCode){
        GetDeliveryFeeForm form = new GetDeliveryFeeForm();
        form.setShopId(shopId);
        form.setFromDistrictId(fromDistrictId);
        form.setToDistrictId(toDistrictId);
        form.setServiceId(serviceId);
        form.setServiceType(serviceTypeId);
        form.setToWardCode(toWardCode);

        String path = "/shiip/public-api/v2/shipping-order/fee";
        ApiMessageDto<DeliveryServiceResponse> result = restService.POST(null,form,path,null,DeliveryServiceResponse.class);
        return result.getData();
    }

    public DeliveryServiceResponse calculateExpectedTime(int fromDistrictId, String fromWardCode, Long toDistrictId, Long shopId, int serviceId, String toWardCode){
        GetDeliveryFeeForm form = new GetDeliveryFeeForm();
        form.setShopId(shopId);
        form.setFromDistrictId(fromDistrictId);
        form.setToDistrictId(toDistrictId);
        form.setServiceId(serviceId);
        form.setToWardCode(toWardCode);
        form.setFromWardCode(fromWardCode);

        String path = "/shiip/public-api/v2/shipping-order/leadtime";
        ApiMessageDto<DeliveryServiceResponse> result = restService.POST(null,form,path,null,DeliveryServiceResponse.class);
        return result.getData();
    }
}

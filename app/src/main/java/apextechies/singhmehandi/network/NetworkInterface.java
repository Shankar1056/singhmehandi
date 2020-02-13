package apextechies.singhmehandi.network;


import apextechies.singhmehandi.component.activity.CommonRequest;
import apextechies.singhmehandi.component.activity.CommonRequestWithDate;
import apextechies.singhmehandi.component.activity.login.model.LoginModel;
import apextechies.singhmehandi.component.activity.login.model.LoginModelVerifyModel;
import apextechies.singhmehandi.component.activity.login.model.OtpRequest;
import apextechies.singhmehandi.component.activity.login.model.OtpValidate;
import apextechies.singhmehandi.component.activity.order.model.AuthorizedRetailerList;
import apextechies.singhmehandi.component.activity.order.model.AuthorizedRetailerRequest;
import apextechies.singhmehandi.component.activity.order.model.DescriptionCategoryRequestModel;
import apextechies.singhmehandi.component.activity.order.model.ItemListResponse;
import apextechies.singhmehandi.component.activity.order.model.OrderDeleteRequest;
import apextechies.singhmehandi.component.activity.order.model.OrderDeleteResponse;
import apextechies.singhmehandi.component.activity.order.model.OrderListResponse;
import apextechies.singhmehandi.component.activity.order.model.SaveShopOrder;
import apextechies.singhmehandi.component.activity.order.model.SaveShopOrderResponse;
import apextechies.singhmehandi.component.activity.shop.model.AreaListResponse;
import apextechies.singhmehandi.component.activity.shop.model.DeleteResponse;
import apextechies.singhmehandi.component.activity.shop.model.DeleteShopRequest;
import apextechies.singhmehandi.component.activity.shop.model.DistributorListResponse;
import apextechies.singhmehandi.component.activity.shop.model.RouteListRequest;
import apextechies.singhmehandi.component.activity.shop.model.RouteListResponse;
import apextechies.singhmehandi.component.activity.shop.model.SaveShopDetailsRequest;
import apextechies.singhmehandi.component.activity.shop.model.SaveShopResponse;
import apextechies.singhmehandi.component.activity.shop.model.ShopListResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by anujgupta on 26/12/17.
 */

public interface NetworkInterface {

    @POST(BaseUrl.LOGINVALIDATEAPI)
    Observable<LoginModel> userLogin(@Body OtpRequest request);

    @POST(BaseUrl.LOGINVALIDATEAPI)
    Observable<LoginModelVerifyModel> verifyOtp(@Body OtpValidate request);

    @POST(BaseUrl.AREA_LIST)
    Observable<AreaListResponse> getAreaList(@Body CommonRequest request);

    @POST(BaseUrl.ROUTE_LIST)
    Observable<RouteListResponse> getRouteList(@Body RouteListRequest request);

    @POST(BaseUrl.ROUTE_LIST)
    Observable<RouteListResponse> getRouteList(@Body CommonRequest request);

    @POST(BaseUrl.DISTRIBUT_LIST)
    Observable<DistributorListResponse> getDistributorList(@Body CommonRequest request);

    @POST(BaseUrl.ADDSHOP)
    Observable<SaveShopResponse> addShop(@Body SaveShopDetailsRequest request);

    @POST(BaseUrl.UPDATESHOP)
    Observable<SaveShopResponse> updateShop(@Body SaveShopDetailsRequest request);

  /*  @POST(BaseUrl.RETAILERLIST_SHOP)
    Observable<ShopRetailerList> getRetailerList(@Body CommonRequestWithDate request);*/

    @POST(BaseUrl.AUTHORISED_RETAILERS_LIST)
    Observable<AuthorizedRetailerList> getAuthorisedRetailer(@Body AuthorizedRetailerRequest request);

    @POST(BaseUrl.ITEMLIST)
    Observable<ItemListResponse> getItemList(@Body DescriptionCategoryRequestModel request);

    @POST(BaseUrl.RETAILERLIST_SHOP)
    Observable<ShopListResponse> getShopList(@Body CommonRequestWithDate request);

    @POST(BaseUrl.ORDER_LIST)
    Observable<OrderListResponse> getOrderList(@Body CommonRequestWithDate request);

    @POST(BaseUrl.GETSHOPORDER_VISITSHOP)
    Observable<SaveShopOrderResponse> visitOrGetShopOrder(@Body SaveShopOrder request);

    @POST(BaseUrl.UPDATEORDER)
    Observable<SaveShopOrderResponse> updateOrGetShopOrder(@Body SaveShopOrder request);

    @POST(BaseUrl.DELETE_SHOP)
    Observable<DeleteResponse> deleteShop(@Body DeleteShopRequest request);

    @POST(BaseUrl.DELETE_ORDER)
    Observable<OrderDeleteResponse> deleteOrder(@Body OrderDeleteRequest request);


}

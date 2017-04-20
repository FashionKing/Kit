package utils.app.com.commonutlis.utils;

/*
 * @创建者     master
 * @创建时间   2017/3/8 13:33
 * @描述       消券
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   "066006","066004" 合并为已过期
 */

public class CouponCodeStateUtil {

	/**
	 * case 063012://必要参数为空
	 * case 066023://场景订单为空
	 * case 060101://参数：storeId为空
	 * case 060102://店铺未激活或不存在
	 * case 066002://卡券兑换码错误
	 * case 066001://没有匹配订单
	 * case 066007://卡券商品不属于该商户
	 * case 066003://卡券已兑换，不能重复兑换
	 * case 066004://卡券已失效
	 * case 066005://卡券兑换时间未开始
	 * case 066006://卡券已过期
	 * case 066026://没有找到对应的积分兑换记录
	 * case 066027://积分兑换商户不匹配
	 * case 066028://积分已兑换，不能重复兑换
	 * case 066029://该商户已兑换!
	 * case 066025://兑换券非法!
	 * case 000000://OK
	 */

	//消券错误码集合(未能识别二维码)
	private static final String[] couponsErrorCode_1 = {"063012", "066023", "060101",
			"066026", "066027", "066002", "066001", "999999"};
	//消券错误码集合(已使用)
	private static final String[] couponsErrorCode_2 = {"066003", "066028", "066029"};
	//消券错误码集合(已失效)
	private static final String[] couponsErrorCode_3 = {""};
	//消券错误码集合(已过期)
	private static final String[] couponsErrorCode_4 = {"066006","066004"};
	//消券错误码集合(未开始兑换)
	private static final String[] couponsErrorCode_5 = {"066005"};
	//消券错误码集合(兑换券无效)
	private static final String[] couponsErrorCode_6 = {"066025"};
	//消券错误码集合(店铺未激活)
	private static final String[] couponsErrorCode_7 = {"060102"};
	//消券错误码集合(卡券商品不属于该商户)
	private static final String[] couponsErrorCode_8 = {"066007"};

	/**
	 * 判断二维码是否可用
	 * @param code code
	 * @return 返回信息
	 */
	public static String setInvalidTicketDialogMsg(String code){
		for(String errorCode: couponsErrorCode_1){
			if(errorCode.equals(code)){
				return "未能识别二维码";
			}
		}
		for(String errorCode: couponsErrorCode_2){
			if(errorCode.equals(code)){
				return "已使用";
			}
		}
		for(String errorCode: couponsErrorCode_4){
			if(errorCode.equals(code)){
				return "已过期";
			}
		}
		for(String errorCode: couponsErrorCode_5){
			if(errorCode.equals(code)){
				return "未开始兑换";
			}
		}
		for(String errorCode: couponsErrorCode_6){
			if(errorCode.equals(code)){
				return "兑换券无效";
			}
		}
		for(String errorCode: couponsErrorCode_7){
			if(errorCode.equals(code)){
				return "店铺未激活";
			}
		}
		for(String errorCode: couponsErrorCode_8){
			if(errorCode.equals(code)){
				return "卡券商品不属于该商户";
			}
		}
		return "未能识别二维码";
	}

}

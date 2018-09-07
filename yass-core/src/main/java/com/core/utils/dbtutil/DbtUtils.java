package com.core.utils.dbtutil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DbtUtils {
    /***
     * 获取无效渠道
     * @return
     */
    public static List<String> getInvalidChannelList(){
		List<String> list=new ArrayList<String>();
		list.add("89F32F77BDAD414E849966B95E5D3055");//待定
		list.add("1D120EA942E8409EB79F76F226AD729D");//待定
		list.add("2A4CC24EAB634BB191BE489B80C9FA94");//待定
		list.add("87FD78ABE6E744069F7D9731A0D9D112");//无效-OP3-其它餐饮
		list.add("9D05C91197AD4F4296BCD1B7B9CAD184");//无效-OP31-星级酒店
		list.add("BB6BC78AC12B4EA5BF748EDB546E1AB7");//无效-OP14-中式火锅店
		list.add("6B5B84DEA2AA4A588A6887FC10871837");//无效-OP16-中式烧烤餐厅
		list.add("C7956BE6161E40C8A0D4AE38A39AA0E7");//无效-OP30-其它餐饮
		list.add("A4A0DC5E1C7E4002AA31494B59FB6C84");//无效-TT2-一般商店
		list.add("4F67D360F0D4491DBB260AAA3EA3C6D2");//无效-TT21-一般商店
		list.add("C97C33E038EA4480941E4BFD0439E786");//无效-MT3-便利店/加油站
		list.add("D7134AB0761E46FB9AF1F5A7B6AB6E25");//无效-MT1-特大卖场
		list.add("A4EE3B0F43C44228BA57E0A752B13F48");//无效-MT2-超级卖场
		list.add("938C5B215FB94872A0DF8514E1224165");//无效-MT33-加油站
		list.add("1D5513ACC4F6471D9E2191CAA1A03478");//无效-MT32-独立便利店
		list.add("5BE8330B30BD42EF8FDFA54B3AEB379A");//无效-MT11-特大卖场
		list.add("295672E6547445C8BE9B779305010112");//无效-MT23-连锁超级市场
		list.add("48389248FA8E474B9074E1380951E537");//无效-MT21-大型独立超市
		list.add("68C6685C42FD4D89B3B2E82476379D58");//无效-ET2-娱乐、休闲吧
		list.add("8B6BEA79054B4DF8BE4162906CD918DC");//无效-ET20-娱乐、休闲吧
		list.add("DF5C308E06A84482A4378ED0AE2A35A2");//无效-ET10-量贩KTV

		return list;
	}
}

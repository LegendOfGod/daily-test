package jdk;

/**
 * @author lqb
 * @date 2022/4/27 10:40
 */
public enum DeliSkuInfoEnum {

    /**
     * 枚举
     */
    skuNo("skuNo", "sku"),
    productName("productName", "名称"),
    stockName("stockName", "货号"),
    cost("cost", "目标成本"),
    suggestPrice("suggestPrice", "销售指导价"),
    stock("stock", "库存"),
    directDelivery("directDelivery", "是否直发"),
    deliveryTime("deliveryTime", "货期");

    /**
     * int值
     */
    private final String value;


    /**
     * 描述
     */
    private final String desc;

    DeliSkuInfoEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}

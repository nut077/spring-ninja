package com.github.nut077.springninja.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Data
@ExcludeSuperclassListeners // ไม่เอาความสามารถจาก class แม่ แต่ยังได้ field มาอยู่
@EqualsAndHashCode(callSuper = false)
@DynamicInsert
@DynamicUpdate
@Entity(name = "orders")
//@IdClass(OrderId.class) กรณีที่ใช้ IdClass ต้องเอา field ทั้งหมดใน OrderId มาใส่ไว้ใน class นี้
public class Order extends Common {

    /* use @IdClass
    @Id
    private long id;
    @Id
    private long productId;*/

    @EmbeddedId
    @AttributeOverrides(value = {
            @AttributeOverride(name = "id", column = @Column(name = "o_id")),
            @AttributeOverride(name = "productId", column = @Column(name = "p_id"))
        }
    )
    private OrderId orderId;
    private int quantity;

    @Lob
    private String detail; // CLOB

    @Lob
    @Nationalized
    private String nationalDetail; // NCLOB

    @Lob
    private byte[] photo; // BLOB
}

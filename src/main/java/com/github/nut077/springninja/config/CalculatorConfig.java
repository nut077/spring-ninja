package com.github.nut077.springninja.config;

import com.github.nut077.springninja.component.Calculator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CalculatorConfig {

    // จะใช้ Bean ได้ก็ต่อเมื่อใช้คู่กับ @Configuration
    @Primary // เป็นการระบุว่า method ไหนจะเป็นค่าหลัก ถ้าหากว่ามีหลาย method
    @Qualifier("plus") // เป็นการระบุชื่อ method กรณีที่มีหลาย method แล้วต้องการเรียกใช้ method นี้ โดยการใช้ @Qualifier("เวลาเรียกใช้ ก็ใส่ชื่อที่เราระบุไว้ อย่างเช่น plus")
    @Bean
    Calculator plus() {
        return (num1, num2) -> num1 + num2;
    }

    @Qualifier("minus")
    @Bean
    //@Bean("minus") ใช้เหมือนกับ @Qualifier
    Calculator minus() {
        return (num1, num2) -> num1 - num2;
    }
}

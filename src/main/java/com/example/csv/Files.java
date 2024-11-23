package com.example.csv;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="file_data")
public class Files
{
     @Id
     private String id;
     private String desc1;
     private String desc2;
     private String val1;
     private String val2;
     private String val3;
     private String val4;
     private String val5;
     private String val6;
     private String val7;

     public String getId() {
          return id;
     }

     public String getDesc1() {
          return desc1;
     }

     public String getDesc2() {
          return desc2;
     }

     public String getVal1() {
          return val1;
     }

     public String getVal2() {
          return val2;
     }

     public String getVal3() {
          return val3;
     }

     public String getVal4() {
          return val4;
     }

     public String getVal5() {
          return val5;
     }

     public String getVal6() {
          return val6;
     }

     public String getVal7() {
          return val7;
     }

     public void setId(String id) {
          this.id = id;
     }

     public void setDesc1(String desc1) {
          this.desc1 = desc1;
     }

     public void setDesc2(String desc2) {
          this.desc2 = desc2;
     }

     public void setVal1(String val1) {
          this.val1 = val1;
     }

     public void setVal2(String val2) {
          this.val2 = val2;
     }

     public void setVal3(String val3) {
          this.val3 = val3;
     }

     public void setVal4(String val4) {
          this.val4 = val4;
     }

     public void setVal5(String val5) {
          this.val5 = val5;
     }

     public void setVal6(String val6) {
          this.val6 = val6;
     }

     public void setVal7(String val7) {
          this.val7 = val7;
     }

	
}

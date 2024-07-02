package mvTech.enumDemo.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import javax.persistence.*;

@Table(name = "users")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

//    @Excel(name = "myname",replace = {"好人_1","坏人_2"})
    @Excel(name = "姓名",dict = "dataSourceDict")
    private String name;



}
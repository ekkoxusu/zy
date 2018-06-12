import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ExcelTarget("测试")
public class Demo {
    @Excel(name = "机型")
    public String ceshijixing;
    @Excel(name = "版本")
    public String xitongbanben;
    @Excel(name ="号码")
    public String ceshihaoma;
    @Excel(name ="归属地")
    public String haomaguishudi;
    @Excel(name ="姓名")
    public String testPerson;

    public Demo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Demo demo = (Demo) o;
        return ceshihaoma.equals(demo.ceshihaoma);
    }

    @Override
    public int hashCode() {
        return new Integer(ceshihaoma.replace("*",""));
    }
}

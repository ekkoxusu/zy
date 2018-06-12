import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestPoi {
    //人
    private static String TESTPERSON = "testPerson";
    //归属地
    private static String HAOMAGUISHUDI = "haomaguishudi";
    //机型
    private static String CESHIJIXING = "ceshijixing";
    //手机号
    private static String CESHIHAOMA = "ceshihaoma";
    //系统版本 
    private static String XITONGBANBEN = "xitongbanben";

    public static void main(String[] args) {
        String url = "/Users/su/Desktop/zhangyang.json";
        List<String> lines = FileUtil.readLines(new File(url), "UTF-8");
        Set<Demo> set = new HashSet<>();
        lines.stream().forEach(item -> {
            JSONObject object = JSONObject.parseObject(item);
            Set<Map.Entry<String, Object>> entries = object.entrySet();
            entries.forEach(i ->{
                try {
                    Demo demo = findDemo(JSONObject.parseObject(i.getValue().toString()));
                    if(demo !=null){
                        set.add(demo);
                    }
                }catch (Exception e){
                }
            });
        });
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("test", "测试", "测试"),
                Demo.class, set);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/su/Desktop/test.xls"));
            workbook.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Demo findDemo(JSONObject item){
        Demo demo = new Demo();
        ImmutableList<String> of = ImmutableList.of(TESTPERSON, HAOMAGUISHUDI, CESHIJIXING, CESHIHAOMA, XITONGBANBEN);
        Set<Map.Entry<String, Object>> entries = item.entrySet();
        for (String s : of) {
            for (Map.Entry<String, Object> entry : entries) {
                if(entry.getKey().contains(s)){
                    String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);
                    try {
                        Method method = Demo.class.getMethod(methodName,String.class);
                        method.invoke(demo,entry.getValue());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return IsNullDemo(demo)?demo:null;
    }

    public static Boolean IsNullDemo(Demo demo){
        if(StringUtils.isBlank(demo.getCeshihaoma())
                || (StringUtils.isBlank(demo.getCeshijixing()) || demo.getCeshihaoma().length()!=11)
                || StringUtils.isBlank(demo.getHaomaguishudi())
                || StringUtils.isBlank(demo.getTestPerson())
                || StringUtils.isBlank(demo.getXitongbanben())
                ) return false;
        return true;
    }
}

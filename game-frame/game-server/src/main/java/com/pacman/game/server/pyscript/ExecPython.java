package com.pacman.game.server.pyscript;

/**
 * @author yhc
 * @create 2021-12-18-21:09
 */

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/*enum的这个用法,可以作为变种的安全单例,值得借鉴哦 ^_^ */
@Service
@Component
public class ExecPython {

    public static final Logger logger = LoggerFactory.getLogger(Exception.class);

    //定义 python 解释器
    private static PythonInterpreter inter;

    public ExecPython() {
//        this.inter  = JythonEnvironment.getInstance().getPythonInterpreter();
//        this.inter.execfile("C:\\test.py");
    }

    //设置 python 脚本的路径
    public void setPythonPath (String pythonPath){
        this.inter.execfile(pythonPath);
    }



    public void execute(String scriptFile, Map<String,String> properties)
    {
        inter.exec(scriptFile);
        logger.info("获取解释器");
        try
        {

            PyFunction getNetInfo = (PyFunction) inter.get("getNetInfo", PyFunction.class);
            PyObject netInfo = getNetInfo.__call__();
            System.out.println("anwser = " + netInfo.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.info("Python 脚本文件执行失败");
        }
    }

    //获取 Python 字符串
    public String getString(){
//获取到python 脚本中的接口
        PyFunction func = (PyFunction) inter.get("adder", PyFunction.class);
        PyObject pyobj = func.__call__();
        System.out.println("anwser = " + pyobj.toString());
        return pyobj.toString();
    }
    // 获取当前数组
    public String getArr() {
        PyFunction getArr = (PyFunction) inter.get("getArr", PyFunction.class);
        PyObject pyobjTwo = getArr.__call__();
        pyobjTwo.__len__();
        System.out.println("anwser = " + pyobjTwo.toString()+" len:"+pyobjTwo.__len__());

        //将 PyObject 对象转换成 java  对象
        //Object object = pyobjTwo.__tojava__(List.class);
        //List<String> list = (List<String>) object;

        //将查询到数据转换成一个 JSON 字符串
        String result = pyobjTwo.toString();
        String JsonStr = "{" + result + "}";
        logger.info(JsonStr);
        logger.info("将查询的结果转换成 JSON 字符串：",JsonStr);

        return pyobjTwo.toString();
    }
}

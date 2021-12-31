package com.pacman.game.server.pyscript;

import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

/**
 * @author yhc
 * @create 2021-12-18-21:10
 */

public final class JythonEnvironment
{
    private static JythonEnvironment INSTANCE = new JythonEnvironment();

    /**
     * 私有构造方法
     */
    private JythonEnvironment()
    {
    }

    /**
     * 获取单例
     * @return JythonEnvironment
     */
    public static JythonEnvironment getInstance()
    {
        return INSTANCE;
    }

    /**
     * 获取python系统状态,可根据需要指定classloader/sys.stdin/sys.stdout等
     * @return PySystemState
     */
    private PySystemState getPySystemState()
    {
        PySystemState.initialize();
        final PySystemState py = new PySystemState();
        py.setClassLoader(getClass().getClassLoader());
        return py;
    }

    /**
     * 获取python解释器
     * @return PythonInterpreter
     */
    public PythonInterpreter getPythonInterpreter(){
        PythonInterpreter inter = new PythonInterpreter(null, getPySystemState());
//        PythonInterpreter inter = new PythonInterpreter();
        return inter;
    }


}
 
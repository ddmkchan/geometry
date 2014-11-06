package you.ctrip.biz;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @文件名称： BeanManager.java
 * @功能描述：
 * @作者 ： aiming
 */
@Component
@Lazy(false)
public class BeanManager implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    /**
     * 获取bean
     * 
     * @param <T>
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 如果有多个Bean符合Class, 取出第一个.
     */
    public static <T> T getBean(Class<T> requiredType) {
        checkApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }

    @Override
    public void destroy() throws Exception {
        cleanApplicationContext();
    }

    /**
     * 清除applicationContext静态变量.
     */
    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}

package com.mode.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mode.dao.MediaDao;
import com.mode.dao.ProductMediaDao;
import com.mode.dao.TestDao;
import com.mode.entity.Media;
import com.mode.entity.Product;
import com.mode.entity.ProductMedia;
import com.mode.entity.ProductOptionValue;
import com.mode.entity.ProductPrice;
import com.mode.entity.ProductVariant;
import com.mode.service.product.AmazonProductImportService;

/**
 * Created by zhaoweiwei on 16/7/20.
 */
@RestController
public class AmazonProductImportApi {

    @Autowired
    private AmazonProductImportService amazonProductImportService;

    @Autowired
    private MediaDao mediaDao;

    @Autowired
    private TestDao testDao;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String productImport(@RequestParam("url") String url) {
        //return amazonProductImportService.productImport(url);
        return null;
    }

    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void batchInsert() throws Exception {
        final long now = System.currentTimeMillis();
        List<Media> medias = new ArrayList<>();
        Media media = new Media();
        media.setUrl("www.whatsmode.com/p1/covered");
        media.setHeight(100);
        media.setType("IMAGE");
        media.setWidth(100);
        media.setCovered(true);;
        medias.add(media);

        media = new Media();
        media.setUrl("www.whatsmode.com/p1/m1");
        media.setHeight(100);
        media.setType("IMAGE");
        media.setWidth(100);
        medias.add(media);

        Product product = new Product();
        product.setDescription("p1 description");
        product.setExternalId("AMAZON_" + "B015P94PEW");
        product.setTitle("p1 title");
        product.setMedias(medias);

        List<ProductVariant> productVariants = new ArrayList<>();

        ProductVariant productVariant = new ProductVariant();
        productVariant.setTitle("p1-v1 title");
        productVariant.setDescription("p1-v1 description");
        productVariant.setWeight(1f);
        productVariant.setWeightUnit("POUNDS");

        List<ProductOptionValue> productOptionValues = new ArrayList<>();
        ProductOptionValue productOptionValue = new ProductOptionValue();
        // Color
        productOptionValue.setProductOptionId(1l);
        productOptionValue.setValue("Amethyst");
        productOptionValues.add(productOptionValue);

        productOptionValue = new ProductOptionValue();
        //Size
        productOptionValue.setProductOptionId(2l);
        productOptionValue.setValue("s");
        productOptionValues.add(productOptionValue);

        productVariant.setProductOptionValues(productOptionValues);

        ProductPrice productPrice = new ProductPrice();
        productPrice.setCurrency("USD");
        productPrice.setListPriceCent(10000l);
        productPrice.setSalePriceCent(5000l);

        productVariant.setProductPrice(productPrice);

        productVariants.add(productVariant);

        productVariant = new ProductVariant();
        productVariant.setTitle("p1-v2 title");
        productVariant.setDescription("p1-v2 description");
        productVariant.setWeight(2f);
        productVariant.setWeightUnit("POUNDS");

        productOptionValues = new ArrayList<>();
        productOptionValue = new ProductOptionValue();
        // Color
        productOptionValue.setProductOptionId(1l);
        productOptionValue.setValue("Black");
        productOptionValues.add(productOptionValue);

        productOptionValue = new ProductOptionValue();
        //Size
        productOptionValue.setProductOptionId(2l);
        productOptionValue.setValue("M");
        productOptionValues.add(productOptionValue);

        productVariant.setProductOptionValues(productOptionValues);

        productPrice = new ProductPrice();
        productPrice.setCurrency("USD");
        productPrice.setListPriceCent(20000l);
        productPrice.setSalePriceCent(15000l);

        productVariant.setProductPrice(productPrice);

        productVariants.add(productVariant);

        product.setProductVariants(productVariants);


        amazonProductImportService.productImport(product);
    }


    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public void test() throws Exception {
        testDao.createTest(1);
        int i = 1;
        int j = 0;
        i = i / j;
    }


    //public static void main(String... args) {
//        Long l = new Long(1l);
//        Long l1 = new Long(1l);
//        if (1l == l) {
//            System.out.println("e");
//        } else {
//            System.out.println("ne");
//        }
//
//        if (l1 == l) {
//            System.out.println("e");
//        } else {
//            System.out.println("ne");
//        }




   // }


    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        // 动态代理类：通用指定类加载器，和接口产生一类
        // getProxyClass()返回代理类的 java.lang.Class 对象，并向其提供类加载器和接口数组。
        Class clazzProxy = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
        System.out.println("动态产生的类名为：" + clazzProxy.getName());
        System.out.println("----------获取动态产生的类的构造方法---------");
        Constructor[] constructors = clazzProxy.getConstructors();
        int i = 1;
        for (Constructor constructor : constructors) {
            System.out.println("第" + (i++) + "个构造方法名：" + constructor.getName());
            Class[] parameterClazz = constructor.getParameterTypes();
            System.out.println("第" + (i++) + "个构造方法参数：" + Arrays.asList(parameterClazz));
        }
        System.out.println("----------获取动态产生的类的普通方法---------");
        Method[] methods = clazzProxy.getDeclaredMethods();
        for (int j = 0; j < methods.length; j++) {
            Method method = methods[j];
            System.out.println("第" + (j + 1) + "个普通方法名：" + method.getName());
            Class[] parameterClazz = method.getParameterTypes();
            System.out.println("第" + (j + 1) + "个普通方法参数：" + Arrays.asList(parameterClazz));
        }
        System.out.println("---------获取动态代理对象的构造方法---------");
        // 动态代理产生的对象的构造方法需要一个实现java.lang.reflect.InvocationHandler接口的对象，故不能通过
        // clazzProxy.newInstance();产生一个对象，可以根据构造方法产生一个对象
        // InvocationHandler 是代理实例的调用处理程序 实现的接口。
        Constructor constructor = clazzProxy.getConstructor(InvocationHandler.class);

        // 代理产生的对象
        Collection proxyBuildCollection = (Collection) constructor
                .newInstance(new InvocationHandler() {
                    // 为什么这里选择ArrayList作为目标对象？
                    // 因为这里的constructor是clazzProxy这个动态类的构造方法，clazzProxy是通过Proxy.getProxyClass()方法产生的，
                    // 该方法有两个参数，一个是指定类加载器，一个是指定代理要实现的接口，这个接口我上面指定了Collection
                    // 而ArrayList实现了Collection接口，固可以为该动态类的目标对象
                    ArrayList target = new ArrayList();// 动态类的目标对象

                    public Object invoke(Object proxy, Method method,
                                         Object[] args) throws Throwable {
                        System.out.println("执行目标" + method.getName() + "方法之前："
                                + System.currentTimeMillis());
                        Object result = method.invoke(target, args);// 其实代理对象的方法调用还是目标对象的方法
                        System.out.println("执行目标" + method.getName() + "方法之后："
                                + System.currentTimeMillis());
                        return result;
                    }

                });
        proxyBuildCollection.clear();
        proxyBuildCollection.add("abc");
        proxyBuildCollection.add("dbc");
        System.out.println(proxyBuildCollection.size());
        System.out.println(proxyBuildCollection.getClass().getName());

        /**
         * 动态代理：总结如下：
         * 1，通过Proxy.getProxyClass(classLoader,interface)方法产生一个动态类的class字节码(clazz)
         *    该getProxyClass()方法有两个参数：一个是指定该动态类的类加载器，一个是该动态类的要实现的接口（从这里可以看现JDK的动态代理必须要实现一个接口）
         *
         * 2，通过第一步的获取的clazz对象可以获取它的构造方法constructor，那么就可以通用constructor的newInstance()方法构造出一个动态实体对象
         *    但constructor的newInstance()方法需要指定一个实现了InvocationHandler接口的类handler，在该类中需要一个目标对象A和实现invoke方法
         *    目标对象A要求能对第一步中的接口的实现，因为在invoke方法中将会去调用A中的方法并返回结果。
         *    过程如下：调用动态代理对象ProxyObject的x方法 ————> 进入构造方法传进的handler的invoke方法 ————> invoke方法调用handler中的target对象
         *            的x方法（所以要求target必须要实现构造动态代理类时指定的接口）并返回它的返回值。（其实如果我们代理P类，那么target就可以选中P类，只是要求P必需实现一个接口）
         *
         *    那么上述中x方法有哪些呢？除了从Object继承过来的方法中除toString,hashCode,equals外的方法不交给handler外，其它的方法全部交给handler处理
         *    如上面proxyBuildCollection.getClass().getName()就没有调用handler的getClass方法，而是调用自己的
         *
         * 3，在handler的invoke方法中return method.invoke(target,args)就是将方法交给target去完成。那么在这个方法执行之前，之后，异常时我们都可以做一些操作，
         *    并且可以在执行之前检查方法的参数args，执行之后检查方法的结果
         */
        System.out.println("-------------------下面的写法更简便--------------------");

        // proxyBuildColl是对ArrayList进行代理
        Collection proxyBuildCollection2 = (Collection) Proxy.newProxyInstance(
                Collection.class.getClassLoader(),// 指定类加载器
                new Class[] { Collection.class },// 指定目标对象实现的接口
                // 指定handler
                new InvocationHandler() {
                    ArrayList target = new ArrayList();

                    public Object invoke(Object proxy, Method method,
                                         Object[] args) throws Throwable {
                        System.out.println(method.getName() + "执行之前...");
                        if (null != args) {
                            System.out.println("方法的参数：" + Arrays.asList(args));
                        } else {
                            System.out.println("方法的参数：" + null);
                        }
                        Object result = method.invoke(target, args);
                        System.out.println(method.getName() + "执行之后...");
                        return result;
                    }
                });
        proxyBuildCollection2.add("abc");
        proxyBuildCollection2.size();
        proxyBuildCollection2.clear();
        proxyBuildCollection2.getClass().getName();

        System.out.println("-------------------对JDK动态代理的重构--------------------");
//        Set proxySet = (Set) buildProxy(new HashSet(), new MyAdvice());
//        proxySet.add("abc");
//        proxySet.size();
    }
    /**
     * 构造一个目标对象的代理对象
     *
     * @param target
     *            目标对象（需要实现某个接口）
     * @return
     */
//    public static Object buildProxy(final Object target,final AdviceInter advice) {
//        Object proxyObject = Proxy.newProxyInstance(
//                target.getClass().getClassLoader(),// 指定类加载器
//                target.getClass().getInterfaces(), // 指定目标对象实现的接口
//                // handler
//                new InvocationHandler() {
//
//                    public Object invoke(Object proxy, Method method,
//                                         Object[] args) throws Throwable {
//                        advice.beforeMethod(target, method, args);
//                        Object result = method.invoke(target, args);
//                        advice.afterMethod(target, method, args);
//                        return result;
//                    }
//                });
//        return proxyObject;
//    }



}

package utils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by russellf on 2/24/2016.
 *
 * Based on the Spark VelocityTemplateEngine
 * Added functionality for taking ViewModel Objects and converting them to Maps
 */
public class VelocityTemplateEngine extends TemplateEngine {

    private final VelocityEngine velocityEngine;

    /**
     * Constructor
     */
    public VelocityTemplateEngine() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty(
                "class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine = new org.apache.velocity.app.VelocityEngine(properties);
    }

    /**
     * Constructor
     *
     * @param velocityEngine The velocity engine, must not be null.
     */
    public VelocityTemplateEngine(VelocityEngine velocityEngine) {
        if (velocityEngine == null) {
            throw new IllegalArgumentException("velocityEngine must not be null");
        }
        this.velocityEngine = velocityEngine;
    }

    //TODO: I would like to get view name from model, but spark handle must return a ModelAndView
    //TODO: I will work on figuring out how to implement this
    @Override
    public String render(Object object) {

        String objName = object.getClass().getName();
        if (objName.endsWith("ViewModel")) {
            objName = objName.substring(0, objName.length() - 9);
        } else {
            throw new IllegalArgumentException("argument must end with 'ViewModel'");
        }

        Template template = velocityEngine.getTemplate(objName);

        //TODO: Convert object to map

        if (object instanceof Map) {
            Map<?, ?> modelMap = (Map<?, ?>) object;
            VelocityContext context = new VelocityContext(modelMap);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer.toString();
        } else {
            throw new IllegalArgumentException("modelAndView must be of type java.util.Map");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String render(ModelAndView modelAndView) {
        Template template = velocityEngine.getTemplate(modelAndView.getViewName());
        Object model = modelAndView.getModel();

        try {
            model = introspect(modelAndView.getModel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (model instanceof Map) {
            Map<?, ?> modelMap = (Map<?, ?>) model;
            VelocityContext context = new VelocityContext(modelMap);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer.toString();
        } else {
            throw new IllegalArgumentException("modelAndView must be of type java.util.Map");
        }
    }

    public static Map<String, Object> introspect(Object obj) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null)
                result.put(pd.getName(), reader.invoke(obj));
        }
        return result;
    }

}

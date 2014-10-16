package fr.istic.taa.shared;


import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface RideFactory extends AutoBeanFactory {
  AutoBean<IRide> ride();
}

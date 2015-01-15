package fr.istic.taa.shared;


import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

@AutoBeanFactory.Category({ IRideCategory.class, IUserCategory.class })
public interface Factory extends AutoBeanFactory {
    AutoBean<IRide> ride();
    AutoBean<IRideList> rideList();

    AutoBean<IUser> user();
    AutoBean<IUserList> userList();
}

package lychee.dote.server.storage.accountcreation;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import lychee.dote.server.storage.accountcreation.Profile;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-18T12:52:48")
@StaticMetamodel(LocationStatus.class)
public class LocationStatus_ { 

    public static volatile SingularAttribute<LocationStatus, Profile> profile;
    public static volatile SingularAttribute<LocationStatus, Boolean> onlineStatus;
    public static volatile SingularAttribute<LocationStatus, Long> id;
    public static volatile SingularAttribute<LocationStatus, Timestamp> LocationDateTime;
    public static volatile SingularAttribute<LocationStatus, Float> xCord;
    public static volatile SingularAttribute<LocationStatus, Float> yCord;

}
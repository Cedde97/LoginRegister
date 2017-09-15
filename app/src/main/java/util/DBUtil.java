package util;

import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.Corner;
import model.Zone;
import source.DateiMemoDbSource;
import source.NeighborDbSource;

/**
 * Created by Joshi on 15.09.2017.
 */

public class DBUtil {


    public Zone initOwnZone(DateiMemoDbSource ownDB) throws YMustBeLargerThanZeroException, XMustBeLargerThanZeroException {
        double blx = ownDB.getCornerBottomLeftX();
        double bly = ownDB.getCornerBottomLeftY();

        double brx = ownDB.getCornerBottomRightX();
        double bry = ownDB.getCornerBottomRightY();

        double tlx = ownDB.getCornerTopLeftX();
        double tly = ownDB.getCornerTopLeftY();

        double trx = ownDB.getCornerTopRightX();
        double topry = ownDB.getCornerBottomLeftX();

        Corner bottomLeft = new Corner(blx,bly);
        Corner bottomRight= new Corner(brx,bry);
        Corner topLeft    = new Corner(tlx,tly);
        Corner topRight   = new Corner(trx,topry);

        Zone zone = new Zone(topLeft,topRight,bottomLeft,bottomRight);
        return zone;
    }
}

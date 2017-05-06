package mi1.app.gonzalal.tabatatimer.data;

import java.util.List;

public class TabataDAO {

    public static List<Tabata> selectAll() {

        return Tabata.listAll(Tabata.class);
    }
}
package todo.codepath.piyush6348.com.codepathtodo.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public class DatabseColumns {
    @DataType(DataType.Type.INTEGER)
    @NotNull
    @PrimaryKey
    @AutoIncrement
    public static final String ID = "_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String TITLE = "title";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String DESCRIPTION = "description";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String PRIORITY ="priority";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String TIME = "time";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String DATE = "date";
}

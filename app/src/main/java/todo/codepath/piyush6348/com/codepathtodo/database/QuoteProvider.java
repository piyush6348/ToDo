package todo.codepath.piyush6348.com.codepathtodo.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = QuoteProvider.AUTHORITY, database = DatabseDefinition.class)
public class QuoteProvider {
    public static final String AUTHORITY = "todo.codepath.piyush6348.com.codepathtodo.database.QuoteProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String QUOTES = "quotes";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = DatabseDefinition.QUOTES)
    public static class Quotes {
        @ContentUri(
                path = Path.QUOTES,
                type = "vnd.android.cursor.dir/quote"
        )
        public static final Uri CONTENT_URI = buildUri(Path.QUOTES);

        @InexactContentUri(
                name = "QUOTE_ID",
                path = Path.QUOTES + "/*",
                type = "vnd.android.cursor.item/quote",
                whereColumn = DatabseColumns.TITLE,
                pathSegment = 1
        )
        public static Uri withSymbol(String symbol) {
            return buildUri(Path.QUOTES, symbol);
        }
    }

}

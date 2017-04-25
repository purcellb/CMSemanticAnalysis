/****
 * Used to pinpoint locations
 * inside a source file based on column and row.
 ****/

package parser;

public class SrcLoc {
    private int source_line;
    private int source_col;
    private String source_file;

    public SrcLoc(int l, int c, String f) {
        source_line = l;
        source_col = c;
        source_file = f;
    }

    public SrcLoc(int l, int c) {
        source_line = l;
        source_col = c;
        source_file = null;
    }

    public String toString() {
        if (source_file == null) return (source_line) + ":" + (source_col);
        else return source_file + ":" + (source_line) + ":" + (source_col);
    }
}

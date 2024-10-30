package com.hahsm.common.ioconsole;

import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.adt.List;

public class TableBuilder {
    private static int DEFAULT_COLUMN_WIDTH = 15;

    private int nColumns = -1;
    private List<Integer> columnsWidth;
    private List<Object[]> rows = new ArrayList<>();
    private List<String> headers;

    public void addRow(final Object... cells) {
        assert rows != null;

        rows.add(cells);
    }

    public void setHeader(final String... headers) {
        this.headers = new ArrayList<>(headers);
    }

    public void setColumnsWidth(final Integer... widths) {
        for (int i = 0; i < widths.length; ++i) {
            if (widths[i] < 0) {
                throw new IllegalArgumentException(
                        "expected a positive column width. Column: " + i + ", width: " + widths[i]);
            }
        }
        columnsWidth = new ArrayList<Integer>(widths);
    }

    public void setColumns(final int nColumns) throws IllegalArgumentException {
        if (nColumns < 0) {
            throw new IllegalArgumentException("expected a positive number of columns, not: " + nColumns);
        }
        this.nColumns = nColumns;
    }

    public String getTableString() {
        inferMissing();
        ensureConsistency();

        final String rowFormat = getRowFormat();
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < rows.size(); ++i) {
            builder.append(String.format(rowFormat, rows.get(i)));
        }

        return builder.toString();
    }

    private String getRowFormat() {
        assert columnsWidth != null;

        final StringBuilder formatBuilder = new StringBuilder();
        for (int i = 0; i < columnsWidth.size(); ++i) {
            formatBuilder.append("|%-").append(columnsWidth.get(i) + 2).append("s");
        }
        formatBuilder.append("|\n");
        return formatBuilder.toString();
    }

    private void inferMissing() {
        if (nColumns == -1) {
            nColumns = headers.size();
        }
        if (columnsWidth == null) {
            columnsWidth = new ArrayList<>(nColumns);
            for (int i = 0; i < nColumns; ++i) {
                columnsWidth.add(DEFAULT_COLUMN_WIDTH);
            }
        }
    }

    private void ensureConsistency() throws IllegalStateException {
        assert nColumns >= 0;
        assert headers != null;
        assert columnsWidth != null;
        
        if (headers != null && headers.size() != nColumns) {
            throw new IllegalStateException(
                    "Inconsistent number of columns and headers, column: " + nColumns + ", headers: " + headers.size());
        }
        if (columnsWidth.size() != nColumns) {
            throw new IllegalStateException(
                    "Inconsistent number of columns and columns width, column: " + nColumns + ", column width: " + columnsWidth.size());
        }
    }
}

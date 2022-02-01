SEP = ", "
QUOTE     = "\'"
NEWLINE   = System.getProperty("line.separator")

begin = true

def record(columns, dataRow) {

    if (begin) {
        columns.eachWithIndex { column, idx ->
            OUT.append(column.name()).append(" IN (").append(NEWLINE)
        }
        begin = false
    }
    else {
        OUT.append(",").append(NEWLINE)
    }
    OUT.append("    ")

    columns.eachWithIndex { column, idx ->
        def skipQuote = dataRow.value(column).toString().isNumber() || dataRow.value(column) == null
        def stringValue = FORMATTER.format(dataRow, column)
        OUT.append(skipQuote ? "": QUOTE).append(stringValue.replace(QUOTE, QUOTE + QUOTE))
           .append(skipQuote ? "": QUOTE).append(idx != columns.size() - 1 ? SEP : "")
    }
}

ROWS.each { row -> record(COLUMNS, row) }
OUT.append(")")

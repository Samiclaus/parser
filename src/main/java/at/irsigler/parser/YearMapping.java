package at.irsigler.parser;

public enum YearMapping {


    YEAR_2002(2, "2002"),
    YEAR_2003(3, "2003"),
    YEAR_2004(4, "2004"),
    YEAR_2005(5, "2005"),
    YEAR_2006(6, "2006"),
    YEAR_2007(7, "2007"),
    YEAR_2008(8, "2008"),
    YEAR_2009(9, "2009"),
    YEAR_2010(10, "2010"),
    YEAR_2011(11, "2011"),
    YEAR_2012(12, "2012"),
    YEAR_2013(13, "2013"),
    YEAR_2014(14, "2014"),
    YEAR_2015(15, "2015"),
    YEAR_2016(16, "2016"),
    YEAR_2017(17, "2017"),
    YEAR_2018(18, "2018");

    private int columnIndex;

    private String year;


    YearMapping(int index, String year) {
        this.columnIndex = index;
        this.year = year;
    }

    public static YearMapping lookup(String yearString) {
        for (YearMapping yearMapping : values()) {
            if (yearMapping.year.equals(yearString)) {
                return yearMapping;
            }
        }
        throw new IllegalArgumentException("YearMapping " + yearString + " unknown.");
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}

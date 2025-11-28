public interface CSVConvertible
{
    // hayde method to convert an object to CSV format
    String toCSV();

    // hayde method to create an object from CSV data
    void fromCSV(String csvData);
}
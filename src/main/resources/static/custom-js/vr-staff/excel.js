    (function($) {
  $.saveAs = function(blob, filename) {
    saveAs(blob, filename);
  };
})(jQuery);

  $(document).ready(function() {
    // Export table data as Excel on button click
    $('#exportButton').click(function() {
      // Get table data
      var table = $('#myTable').get(0);
      var data = XLSX.utils.table_to_sheet(table);

      // Create a new workbook and worksheet using SheetJS
      var wb = XLSX.utils.book_new();
      var ws = XLSX.utils.aoa_to_sheet(data);

      // Add the worksheet to the workbook
      XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

      // Export the workbook to an Excel file
      XLSX.writeFile(wb, 'example.xlsx');
    });
  });

  $(document).ready(function() {
  $('#download-btn').click(function() {
    // Get the table element
    var table = document.getElementById('my-table');
    // Convert the table to a worksheet object
    var worksheet = XLSX.utils.table_to_sheet(table);
    // Create a workbook with the worksheet
    var workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1');
    // Convert the workbook to a binary string
    var binaryString = XLSX.write(workbook, { bookType: 'xlsx', type: 'binary' });
    // Save the file
    saveAs(new Blob([s2ab(binaryString)], {type:"application/octet-stream"}), 'my-table.xlsx');
  });
});

// Utility function to convert a string to an ArrayBuffer
function s2ab(s) {
  var buf = new ArrayBuffer(s.length);
  var view = new Uint8Array(buf);
  for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF;
  return buf;
}

const invoiceNum = document.getElementById('invoice-number');
const invoiceNumInput = document.getElementById('invoice-number-input');

const btnGen = document.getElementById('btn-Gen');

function generateInvNum() {
    const f1 = "INV-";
    const currentYear = new Date().getFullYear();
    const currentYearLast2Digit = currentYear.toString().slice(-2);

    const now = new Date();
    const monthIndex = now.getMonth();

    const currentMonth = monthIndex + 1;
    const randomNumber = Math.floor(Math.random() * 90000) + 10000;
    const randomNumber2 = Math.floor(Math.random() * 90) + 10;

    const date = now.getDate();
    let currentDay = 0;
    if (date <= 9) {
        currentDay = "0" + date;
    } else {
        currentDay = date;
    }
    const result = f1 + currentYearLast2Digit + currentDay + currentMonth + "-" + randomNumber + randomNumber2;
    invoiceNum.textContent = result;
    invoiceNumInput.value = result;
    generatePONumber();
}

function getClientInfo() {
    const date = new Date();

    const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

    const formattedDate = `${monthNames[date.getMonth()]} ${padZero(date.getDate())}, ${date.getFullYear()}`;

    function padZero(value) {
        return value < 10 ? `0${value}` : value;
    }
    var clientName = $('#my-client').val();
    $.ajax({
        type: "GET",
        url: "/getClientInfo",
        data: {
            name: clientName
        },
        success: function(data) {
            $('#client-name').text(data.name);
            $('#client-name-input').val(data.name);

            $('#client-tin').text(data.tin);
            $('#client-tin-input').val(data.tin);

            $('#client-address').text(data.address+", "+data.cityAddress);
            $('#client-address-input').val(data.address+", "+data.cityAddress);

            $('#client-conPerson-input').val(data.agent);
            $('#client-conPerson').text(data.agent);

            $('#client-date').text(formattedDate);
            $('#client-date-input').val(formattedDate);
        }
    });
}

function getSupplierInfo(){
    var supplierName = $('#my-supplier').val();
    $.ajax({
        type: "GET",
        url: "/getSupplierInfo",
        data: {
            name: supplierName
        },
        success: function(data) {
            $('#logo-suppName').text(data.name);

            //for input and display
            $('#supp-name-input').val(data.name);
            $('#supp-name').text(data.name);

            $('#supp-addrs-input').val(data.address+", "+data.cityAddress);
            $('#supp-addrs').text(data.address+", "+data.cityAddress);

            $('#supp-tin-input').val(data.tin);
            $('#supp-tin').text("VAT Reg. TIN "+data.tin);
        }
    });
}
function generatePONumber() {
    const poNum = document.getElementById('client-poNumber');
    const poNumInput = document.getElementById('client-poNumber-input');
    const timestamp = new Date().getTime(); // Get the current timestamp in milliseconds
    const randomNum = Math.floor(Math.random() * 10000); // Generate a random number between 0 and 9999
    const poNumber = `PO-${timestamp}-${randomNum}`; // Combine the timestamp and random number to create the PO number

    // Check if the PO number already exists
    const isDuplicate = Math.random() < 0.0001; // 1 in 10,000 chance of duplication
    if (isDuplicate) {
        console.warn(`Duplicate PO number detected: ${poNumber}`);
    }

    poNum.textContent = poNumber;
    poNumInput.value = poNumber;
}

//////////////////// table
const tableBody = document.getElementById('table-body');
const addRowBtn = document.getElementById('add-row-btn');
const deleteRowBtn = document.getElementById('delete-row-btn');
let rowCounter = 0;

// Define a function to update the amount based on the quantity and unit price values
// Add row
addRowBtn.addEventListener('click', () => {
  if (rowCounter < 30) {
    const newRow = document.createElement('tr');
    newRow.innerHTML = `
      <td>
        <input id="qty" type="number" class="compute_qty" onfocus="if (this.value == '0') { this.value = ''; }" onblur="if (this.value == '') { this.value = '0'; }" required>
      </td>
      <td>
        <input id="unit" type="text" required>
      </td>
      <td>
        <input id="article" type="text" required>
      </td>
      <td>
        <input id="unitPrice" type="number" class="compute_unit_price" onfocus="if (this.value == '0.0') { this.value = ''; }" required>
      </td>
      <td>
        <input id="amount" type="text" class="compute_amount" readonly>
      </td>
    `;
    tableBody.appendChild(newRow);
    const inputs = newRow.querySelectorAll('.compute_qty, .compute_unit_price');
    inputs.forEach(input => {
      input.addEventListener('input', () => {
        updateAmount(newRow);
      });
    }); // <-- added closing curly brace here
    rowCounter++;
    computeInvoiceTotals(); // Call updateTotalAmount function here
  } else {
    alert('Maximum row limit reached!');
  }
});


function updateAmount(row) {
  const qty = row.querySelector('.compute_qty').value;
  const unitPrice = row.querySelector('.compute_unit_price').value;
  const amountInput = row.querySelector('.compute_amount');

  if (qty && unitPrice && qty > 0 && unitPrice > 0) {
    const amount = qty * unitPrice;
    amountInput.value = amount.toFixed(2);
  } else {
    amountInput.value = '';
  }
  computeInvoiceTotals();
}

function computeInvoiceTotals() {
  const amountInputs = document.querySelectorAll('.compute_amount');
  let totalSalesVatInc = 0;
  let totalAmountNetOfVat = 0;
  let addVat = 0;
  let grandTotal = 0;

  amountInputs.forEach(input => {
    if (input.value !== '') {
      totalSalesVatInc += parseFloat(input.value);
    }
  });

  totalAmountNetOfVat = totalSalesVatInc / 1.12;
  addVat = totalSalesVatInc - totalAmountNetOfVat;
  grandTotal = totalSalesVatInc;

  const totalSaleVatIncSpan = document.getElementById('totalSale-VatIncl');
  const amountNOVSpan = document.getElementById('amount-NOV');
  const addVATSpan = document.getElementById('add-VAT');
  const totalAmountDueSpan = document.getElementById('totalAmount-due');
  const totalSalesVatIncInput = document.getElementById('totalSalesVatInc-input');
  const amountNetOfVatInput = document.getElementById('amountNetOfVat-input');
  const addVatInput = document.getElementById('addVat-input');
  const grandTotalInput = document.getElementById('grandTotal-input');

  totalSaleVatIncSpan.textContent = '₱ ' + totalSalesVatInc.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2});
  amountNOVSpan.textContent = '₱ ' + totalAmountNetOfVat.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2});
  addVATSpan.textContent = '₱ ' + addVat.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2});
  totalAmountDueSpan.textContent = '₱ ' + grandTotal.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2});

  totalSalesVatIncInput.value = totalSalesVatInc.toFixed(2);
  amountNetOfVatInput.value = totalAmountNetOfVat.toFixed(2);
  addVatInput.value = addVat.toFixed(2);
  grandTotalInput.value = grandTotal.toFixed(2);
}
// Delete last row
deleteRowBtn.addEventListener('click', () => {
  const rows = tableBody.getElementsByTagName('tr');
  if (rows.length > 0) {
    rows[rows.length - 1].remove();
    rowCounter--;
  }else {
       alert('At least one row is required!');
     }
  computeInvoiceTotals();
});

const clientBusStyleSpan = document.getElementById('client-busStyle');
const clientBusStyleInput = document.getElementById('client-busStyle-input');

    clientBusStyleSpan.addEventListener('input', function() {
      clientBusStyleInput.value = clientBusStyleSpan.textContent;
    });

const clientTerms = document.getElementById('client-terms');
const clientTermsInput = document.getElementById('client-terms-input');

    clientTerms.addEventListener('input', function() {
      clientTermsInput.value = clientTerms.textContent;
    });

const cashierName = document.getElementById('cashier-name');
const cashierNameInput = document.getElementById('cashier-name-input');
// Set the initial value of the input field to the text content of the cashierName element
cashierNameInput.value = cashierName.textContent;
    cashierName.addEventListener('input', function() {
      cashierNameInput.value = cashierName.textContent;
    });



function getAllData() {
  const rows = document.querySelectorAll('table tbody tr');
  const data = [];

  rows.forEach(row => {
    const qty = row.querySelector('#qty')?.value;
    const unit = row.querySelector('#unit')?.value;
    const article = row.querySelector('#article')?.value;
    const unitPrice = row.querySelector('#unitPrice')?.value;
    const amount = row.querySelector('#amount')?.value;

    if (qty || unit || article || unitPrice || amount) {
      data.push({ qty, unit, article, unitPrice, amount });
    }
  });
  return data; // add return statement
}

//function submitForm(event) {
//  event.preventDefault(); // prevent form submission
//  const data = getAllData(); // get data from table
//  const form = document.getElementById('myForm');
//  const formData = new FormData(form);
//  formData.append('data', JSON.stringify(data)); // append data to form data
//  const xhr = new XMLHttpRequest();
//  xhr.open('POST', form.action);
//  xhr.send(formData); // send AJAX request
//}

//function getAllData() {
//  const rows = document.querySelectorAll('table tbody tr');
//  const data = [];
//
//  rows.forEach(row => {
//    const qty = row.querySelector('#qty')?.value;
//    const unit = row.querySelector('#unit')?.value;
//    const article = row.querySelector('#article')?.value;
//    const unitPrice = row.querySelector('#unitPrice')?.value;
//    const amount = row.querySelector('#amount')?.value;
//
//    if (qty || unit || article || unitPrice || amount) {
//      data.push({ qty, unit, article, unitPrice, amount });
//    }
//  });
//
//  // Send an AJAX request to the server with the data
//  const xhr = new XMLHttpRequest();
//  xhr.open('POST', '/createInvoice');
//  xhr.setRequestHeader('Content-Type', 'application/json');
//  xhr.onreadystatechange = function() {
//    if (xhr.readyState === XMLHttpRequest.DONE) {
//      if (xhr.status === 200) {
//        console.log(xhr.responseText);
//      } else {
//        console.error('Failed to send data to server');
//      }
//    }
//  };
//  xhr.send(JSON.stringify(data));
//}


generateInvNum();





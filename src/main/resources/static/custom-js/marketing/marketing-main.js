
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
//////////////////////////////
const quantity = document.querySelectorAll('.compute_qty');
const unitPrice = document.querySelectorAll('.compute_unit_price');
const amount = document.querySelectorAll('.compute_amount');

const totalAmountDue = document.getElementById('grandTotal');
const totalAmountDueInput = document.getElementById('grandTotal-input');

// Loop through each row of the table
for (let i = 0; i < quantity.length; i++) {
    // Add an event listener to the quantity and unit price input fields for each row
    quantity[i].addEventListener('input', updateAmount);
    unitPrice[i].addEventListener('input', updateAmount);
}

// Function to update the amount and total amount due
function updateAmount() {
    let rowAmount = 0;


    // Loop through each row of the table
    for (let i = 0; i < quantity.length; i++) {
    const amountInput = document.querySelectorAll('.compute_amount_input');

        // Get the quantity and unit price values for the current row
        const qty = parseFloat(quantity[i].value) || 0;
        const price = parseFloat(unitPrice[i].value) || 0;

        // Calculate the amount for the current row
        const amt = qty * price;

        // Update the amount for the current row
        amount[i].value = amt.toFixed(2);


        // Add the amount for the current row to the total amount due
        rowAmount += amt;
    }
    // Update the total amount due
    totalAmountDue.textContent = "₱ " + rowAmount.toLocaleString();
    totalAmountDueInput.value = rowAmount.toFixed(2);
}

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

    cashierName.addEventListener('input', function() {
      cashierNameInput.value = cashierName.textContent;
    });



generateInvNum();




const invoiceNum = document.getElementById('invoice-number');
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
            $('#client-tin').text(data.tin);
            $('#client-address').text(data.address);
            $('#client-conPerson').text(data.agent);
            $('#client-date').text(formattedDate);
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
            $('#supp-name').text(data.name);
            $('#supp-addrs').text(data.address+", "+data.cityAddress);
            $('#supp-tin').text("VAT Reg. TIN "+data.tin);
        }
    });
}





function generatePONumber() {
    const poNum = document.getElementById('client-poNumber');
    const timestamp = new Date().getTime(); // Get the current timestamp in milliseconds
    const randomNum = Math.floor(Math.random() * 10000); // Generate a random number between 0 and 9999
    const poNumber = `PO-${timestamp}-${randomNum}`; // Combine the timestamp and random number to create the PO number

    // Check if the PO number already exists
    const isDuplicate = Math.random() < 0.0001; // 1 in 10,000 chance of duplication
    if (isDuplicate) {
        console.warn(`Duplicate PO number detected: ${poNumber}`);
    }

    const result = poNum.textContent = poNumber;
    return result;
}

const quantity = document.querySelectorAll('.compute_qty');
const unitPrice = document.querySelectorAll('.compute_unit_price');
const amount = document.querySelectorAll('.compute_amount');
const totalAmountDue = document.getElementById('grandTotal');

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

        // Get the quantity and unit price values for the current row
        const qty = parseFloat(quantity[i].value) || 0;
        const price = parseFloat(unitPrice[i].value) || 0;

        // Calculate the amount for the current row
        const amt = qty * price;

        // Update the amount for the current row
        amount[i].textContent = amt.toLocaleString();

        // Add the amount for the current row to the total amount due
        rowAmount += amt;
    }
    // Update the total amount due
    totalAmountDue.textContent = "₱ " + rowAmount.toLocaleString();
}
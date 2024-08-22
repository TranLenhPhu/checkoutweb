// document.getElementById('orderForm').addEventListener('submit', function(event) {
//     event.preventDefault();
//
//     const orderCode = document.getElementById('orderCode').value;
//     const merchantCode = document.getElementById('merchantCode').value;
//     const hashCode = document.getElementById('hashCode').value;
//
//     fetch(`http://localhost:8080/api/orders/${orderCode}/${merchantCode}/${hashCode}`)
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Invalid request or error fetching order information.');
//             }
//             return response.json();
//         })
//         .then(data => {
//             displayOrderInfo(data);
//         })
//         .catch(error => {
//             document.getElementById('result').innerHTML = `<p id="error">${error.message}</p>`;
//         });
// });
//
// function displayOrderInfo(orderInfo) {
//     const resultDiv = document.getElementById('result');
//     resultDiv.innerHTML = `
//         <h3>Order Details</h3>
//         <p><strong>Order Code:</strong> ${orderInfo.orderCode}</p>
//         <p><strong>Merchant Code:</strong> ${orderInfo.merchantCode}</p>
//         <p><strong>Amount:</strong> ${orderInfo.amount}</p>
//     `;
// }
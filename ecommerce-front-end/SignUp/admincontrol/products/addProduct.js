// Define a function to add a product
function addSizeInput() {
    const sizesContainer = document.getElementById('sizes');

    // Create size input fields
    const sizeInput = document.createElement('div');
    sizeInput.classList.add('size-input');
    sizeInput.innerHTML = `
        <input type="text" name="sizeName" placeholder="Size Name">
        <input type="number" name="sizeQuantity" placeholder="Quantity">
    `;

    // Append the size input fields to the container
    sizesContainer.appendChild(sizeInput);
}


function addProduct() {
    // Get input values
    var title = document.getElementById('title').value;
    var description = document.getElementById('description').value;
    var price = document.getElementById('price').value;
    var discountedPrice = document.getElementById('discountedPrice').value;
    var discountPercent = document.getElementById('discountPercent').value;
    var quantity = document.getElementById('quantity').value;
    var brand = document.getElementById('brand').value;
    var color = document.getElementById('color').value;
    var imageUrl = document.getElementById('imageUrl').value;
    var topLevelCategory = document.getElementById('topLevelCategory').value;
    var secondLevelCategory = document.getElementById('secondLevelCategory').value;
    var thirdLevelCategory = document.getElementById('thirdLevelCategory').value;

    

    // Add sizes to the formData
    formData.size = sizes;

    // Get size values
    var sizes = [];
    var sizeInputs = document.querySelectorAll('.sizeField');
    sizeInputs.forEach(function (sizeInput) {
        var name = sizeInput.querySelector('.sizeName').value;
        var qty = sizeInput.querySelector('.sizeQty').value;
        sizes.push({ name: name, quantity: qty });
    });

    // Construct the product object
    var product = {
        title: title,
        description: description,
        price: price,
        discountedPrice: discountedPrice,
        discountPercent: discountPercent,
        quantity: quantity,
        brand: brand,
        color: color,
        size: sizes,
        imageUrl: imageUrl,
        topLevelCategory: topLevelCategory,
        secondLevelCategory: secondLevelCategory,
        thirdLevelCategory: thirdLevelCategory
    };

    // Log the product object (for testing purposes)
    console.log(product);

    // Here, you can make an HTTP request to your API endpoint (e.g., using fetch) to add the product
    // Replace the URL with your actual API endpoint
    fetch('http://localhost:5454/api/admin/products/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(product),
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
        // Optionally, redirect to a success page or show a success message
    })
    .catch((error) => {
        console.error('Error:', error);
        // Handle the error (e.g., show an error message)
    });
}

// Function to add a new size field
function addSizeField() {
    var sizeContainer = document.getElementById('sizeContainer');
    
    // Create size field elements
    var sizeField = document.createElement('div');
    sizeField.className = 'sizeField';

    var sizeNameInput = document.createElement('input');
    sizeNameInput.type = 'text';
    sizeNameInput.className = 'sizeName';
    sizeNameInput.placeholder = 'Size Name';

    var sizeQtyInput = document.createElement('input');
    sizeQtyInput.type = 'number';
    sizeQtyInput.className = 'sizeQty';
    sizeQtyInput.placeholder = 'Quantity';

    var removeButton = document.createElement('button');
    removeButton.textContent = 'Remove';
    removeButton.addEventListener('click', function () {
        sizeContainer.removeChild(sizeField);
    });

    // Append elements to the size field
    sizeField.appendChild(sizeNameInput);
    sizeField.appendChild(sizeQtyInput);
    sizeField.appendChild(removeButton);

    // Append the size field to the container
    sizeContainer.appendChild(sizeField);
}

// Attach event listener to the "Add Size" button
document.getElementById('addSizeBtn').addEventListener('click', addSizeField);

// Attach event listener to the "Add Product" button
document.getElementById('addProductBtn').addEventListener('click', addProduct);

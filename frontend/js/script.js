// =====================================================
// SERVER REQUEST FUNCTIONS
// =====================================================

/**
 * Sends a GET request to the server
 * GET requests are used to retrieve data
 */
function sendGetRequest() {
    const url = 'https://api.example.com/data'; // Replace with your server URL

    showLoading();

    // Fetch is the modern way to make HTTP requests
    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => {
        // Check if request was successful
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json(); // Parse JSON response
    })
    .then(data => {
        // Success! Display the data
        displayResponse('GET Request Successful!', data, 'success');
    })
    .catch(error => {
        // Handle errors
        displayResponse('GET Request Failed', error.message, 'error');
    });
}

/**
 * Sends a POST request to the server
 * POST requests are used to send data to create new resources
 */
function sendPostRequest() {
    const url = 'https://api.example.com/data'; // Replace with your server URL

    // Data to send to server
    const data = {
        name: 'Sample Data',
        value: 123,
        timestamp: new Date().toISOString()
    };

    showLoading();

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data) // Convert JavaScript object to JSON string
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        displayResponse('POST Request Successful!', data, 'success');
    })
    .catch(error => {
        displayResponse('POST Request Failed', error.message, 'error');
    });
}

/**
 * Sends a DELETE request to the server
 * DELETE requests are used to remove resources
 */
function sendDeleteRequest() {
    const url = 'https://api.example.com/data/123'; // Replace with your server URL

    showLoading();

    fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        displayResponse('DELETE Request Successful!', data, 'success');
    })
    .catch(error => {
        displayResponse('DELETE Request Failed', error.message, 'error');
    });
}

// =====================================================
// HELPER FUNCTIONS
// =====================================================

/**
 * Displays the response from server in the response box
 */
function displayResponse(title, data, type) {
    const responseArea = document.getElementById('response-area');

    // Create formatted output
    let output = `<strong>${title}</strong><br><br>`;

    if (typeof data === 'object') {
        output += '<pre>' + JSON.stringify(data, null, 2) + '</pre>';
    } else {
        output += data;
    }

    responseArea.innerHTML = output;
    responseArea.classList.add('active');

    // Add color based on success/error
    if (type === 'success') {
        responseArea.style.borderLeftColor = '#4facfe';
        responseArea.style.backgroundColor = '#e8f5e9';
    } else {
        responseArea.style.borderLeftColor = '#f5576c';
        responseArea.style.backgroundColor = '#ffebee';
    }
}

/**
 * Shows a loading message while waiting for server response
 */
function showLoading() {
    const responseArea = document.getElementById('response-area');
    responseArea.innerHTML = '<strong>Loading...</strong><br>Waiting for server response...';
    responseArea.classList.add('active');
    responseArea.style.borderLeftColor = '#667eea';
    responseArea.style.backgroundColor = '#f8f9fa';
}

// =====================================================
// PAGE INITIALIZATION
// =====================================================

/**
 * This code runs when the page finishes loading
 */
document.addEventListener('DOMContentLoaded', function() {
    console.log('Page loaded successfully!');

    // You can add initialization code here
    // For example, fetch initial data, set up event listeners, etc.

    // Example: Add smooth scrolling to all links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });
});

// =====================================================
// UTILITY FUNCTIONS
// =====================================================

/**
 * Format a date to a readable string
 */
function formatDate(date) {
    return new Date(date).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

/**
 * Validate if a string is a valid email
 */
function isValidEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

/**
 * Show a simple alert notification
 */
function showNotification(message, type = 'info') {
    alert(message); // Simple version
    // In a real app, you'd create a custom notification component
}
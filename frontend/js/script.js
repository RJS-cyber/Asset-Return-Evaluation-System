// Wait for DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // Get all selection buttons
    const selectionButtons = document.querySelectorAll('.selection-btn');
    const calculateButton = document.getElementById('calculate-btn');
    const yearsInput = document.getElementById('years');
    const amountInput = document.getElementById('amount');
    const resultMessage = document.getElementById('result-message');

    // Track selected buttons
    let selectedAssets = new Set();

    // Add click event to each selection button
    selectionButtons.forEach(button => {
        button.addEventListener('click', function() {
            const asset = this.getAttribute('data-asset');

            // Toggle selection
            if (this.classList.contains('selected')) {
                this.classList.remove('selected');
                selectedAssets.delete(asset);
            } else {
                this.classList.add('selected');
                selectedAssets.add(asset);
            }
        });
    });

    // Handle calculate button click
    calculateButton.addEventListener('click', function() {
        // Clear previous messages
        resultMessage.textContent = '';
        resultMessage.className = 'result-message';

        // Validate inputs
        const years = yearsInput.value;
        const amount = amountInput.value;

        if (!years || years < 1) {
            showMessage('Bitte geben Sie eine gültige Anzahl von Jahren ein (mindestens 1).', 'error');
            return;
        }

        if (!amount || amount < 0) {
            showMessage('Bitte geben Sie einen gültigen Betrag ein (mindestens 0).', 'error');
            return;
        }

        if (selectedAssets.size === 0) {
            showMessage('Bitte wählen Sie mindestens einen Asset-Typ aus.', 'error');
            return;
        }

        // Build request body matching sendData DTO structure
        const requestBody = {
            years: parseInt(years),
            amount: parseFloat(amount),
            assetTypes: Array.from(selectedAssets).sort()
        };

        // Build the API endpoint URL
        // Adjust the base URL according to your backend configuration
        const apiUrl = 'http://localhost:8080/api/calculate';

        // Show loading message
        showMessage('Berechnung läuft...', 'info');

        // Send HTTP POST request with JSON body
        fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // Handle successful response
                console.log('Response:', data);
                showMessage('Berechnung erfolgreich abgeschlossen! Ergebnis in der Konsole.', 'success');

                // You can add more sophisticated result display here
                // For example, create a results section or modal
            })
            .catch(error => {
                // Handle errors
                console.error('Error:', error);
                showMessage(`Fehler bei der Berechnung: ${error.message}`, 'error');
            });
    });

    // Helper function to display messages
    function showMessage(message, type) {
        resultMessage.textContent = message;
        resultMessage.className = `result-message ${type}`;
    }

    // Allow Enter key to trigger calculation
    [yearsInput, amountInput].forEach(input => {
        input.addEventListener('keypress', function(event) {
            if (event.key === 'Enter') {
                calculateButton.click();
            }
        });
    });
});

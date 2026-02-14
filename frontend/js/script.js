document.addEventListener('DOMContentLoaded', function() {
    const selectionButtons = document.querySelectorAll('.selection-btn');
    const calculateButton = document.getElementById('calculate-btn');
    const yearsInput = document.getElementById('years');
    const amountInput = document.getElementById('amount');
    const resultMessage = document.getElementById('result-message');

    let selectedAssets = new Set();

    selectionButtons.forEach(button => {
        button.addEventListener('click', function() {
            const asset = this.getAttribute('data-asset');

            if (this.classList.contains('selected')) {
                this.classList.remove('selected');
                selectedAssets.delete(asset);
            } else {
                this.classList.add('selected');
                selectedAssets.add(asset);
            }
        });
    });

    calculateButton.addEventListener('click', function() {
        resultMessage.textContent = '';
        resultMessage.className = 'result-message';

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

        const requestBody = {
            years: parseInt(years),
            amount: parseFloat(amount),
            assetTypes: Array.from(selectedAssets).sort()
        };

        const apiUrl = 'http://localhost:8080/api/ares/calculate';

        showMessage('Berechnung läuft...', 'info');

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
                console.log('Response:', data);
                showMessage('Berechnung erfolgreich abgeschlossen! Weiterleitung zu den Ergebnissen...', 'success');

                sessionStorage.setItem('calculationResults', JSON.stringify(data));

                setTimeout(() => {
                    window.location.href = 'results.html';
                }, 1000);
            })
            .catch(error => {
                console.error('Error:', error);
                showMessage(`Fehler bei der Berechnung: ${error.message}`, 'error');
            });
    });

    function showMessage(message, type) {
        resultMessage.textContent = message;
        resultMessage.className = `result-message ${type}`;
    }

    [yearsInput, amountInput].forEach(input => {
        input.addEventListener('keypress', function(event) {
            if (event.key === 'Enter') {
                calculateButton.click();
            }
        });
    });
});

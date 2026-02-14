document.addEventListener('DOMContentLoaded', function() {
    const resultsData = sessionStorage.getItem('calculationResults');

    if (!resultsData) {
        window.location.href = 'index.html';
        return;
    }

    try {
        const data = JSON.parse(resultsData);
        displayResults(data);
    } catch (error) {
        console.error('Error parsing results data:', error);
        alert('Fehler beim Laden der Ergebnisse');
        window.location.href = 'index.html';
    }
});

function displayResults(data) {
    const resultsArray = data.resultsArray;
    const maxYears = data.maxYears;
    const maxCapital = data.maxCapital;
    const assetTypes = data.assetTypes;

    createGraph(resultsArray, maxYears, maxCapital, assetTypes);

    createTables(resultsArray, assetTypes);
}

function createGraph(resultsArray, maxYears, maxCapital, assetTypes) {
    const ctx = document.getElementById('resultsChart').getContext('2d');

    const assetColors = {
        'BONDS': '#f3e221',
        'RAW_MATERIALS': '#4121f3',
        'REAL_ESTATES': '#f38321',
        'STOCKS': '#b421f3'
    };

    const datasets = [];

    assetTypes.forEach(assetType => {
        const dataPoints = [];

        for (let yearIndex = 0; yearIndex < resultsArray.length; yearIndex++) {
            const yearResults = resultsArray[yearIndex];

            for (let assetIndex = 0; assetIndex < yearResults.length; assetIndex++) {
                const result = yearResults[assetIndex];
                if (result && result.type === assetType) {
                    dataPoints.push({
                        x: result.year,
                        y: result.capital
                    });
                    break;
                }
            }
        }

        datasets.push({
            label: formatAssetTypeName(assetType),
            data: dataPoints,
            borderColor: assetColors[assetType] || '#000000',
            backgroundColor: assetColors[assetType] || '#000000',
            borderWidth: 3,
            fill: false,
            tension: 0.1,
            pointRadius: 4,
            pointHoverRadius: 6
        });
    });

    new Chart(ctx, {
        type: 'line',
        data: {
            datasets: datasets
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            aspectRatio: 3,
            plugins: {
                title: {
                    display: true,
                    text: 'Kapitalentwicklung über die Jahre',
                    font: {
                        size: 24,
                        weight: 'bold'
                    },
                    padding: 20
                },
                legend: {
                    display: true,
                    position: 'top',
                    labels: {
                        font: {
                            size: 14
                        },
                        padding: 15,
                        usePointStyle: true
                    }
                },
                tooltip: {
                    mode: 'index',
                    intersect: false,
                    callbacks: {
                        label: function(context) {
                            let label = context.dataset.label || '';
                            if (label) {
                                label += ': ';
                            }
                            label += new Intl.NumberFormat('de-DE', {
                                style: 'currency',
                                currency: 'EUR'
                            }).format(context.parsed.y);
                            return label;
                        }
                    }
                }
            },
            scales: {
                x: {
                    type: 'linear',
                    title: {
                        display: true,
                        text: 'Jahre',
                        font: {
                            size: 16,
                            weight: 'bold'
                        }
                    },
                    min: 0,
                    max: maxYears,
                    ticks: {
                        stepSize: 1,
                        font: {
                            size: 12
                        }
                    },
                    grid: {
                        display: true,
                        color: 'rgba(0, 0, 0, 0.1)'
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Endkapital (€)',
                        font: {
                            size: 16,
                            weight: 'bold'
                        }
                    },
                    min: 0,
                    max: maxCapital * 1.1,
                    ticks: {
                        callback: function(value) {
                            return new Intl.NumberFormat('de-DE', {
                                style: 'currency',
                                currency: 'EUR',
                                minimumFractionDigits: 0,
                                maximumFractionDigits: 0
                            }).format(value);
                        },
                        font: {
                            size: 12
                        }
                    },
                    grid: {
                        display: true,
                        color: 'rgba(0, 0, 0, 0.1)'
                    }
                }
            },
            interaction: {
                mode: 'nearest',
                axis: 'x',
                intersect: false
            }
        }
    });
}

function createTables(resultsArray, assetTypes) {
    const tablesContainer = document.getElementById('tables-container');

    const assetColors = {
        'BONDS': '#f3e221',
        'RAW_MATERIALS': '#4121f3',
        'REAL_ESTATES': '#f38321',
        'STOCKS': '#b421f3'
    };

    assetTypes.forEach(assetType => {
        const assetResults = [];

        for (let yearIndex = 0; yearIndex < resultsArray.length; yearIndex++) {
            const yearResults = resultsArray[yearIndex];

            for (let assetIndex = 0; assetIndex < yearResults.length; assetIndex++) {
                const result = yearResults[assetIndex];
                if (result && result.type === assetType) {
                    assetResults.push(result);
                    break;
                }
            }
        }

        const tableWrapper = document.createElement('div');
        tableWrapper.className = 'table-wrapper';

        const tableTitle = document.createElement('h3');
        tableTitle.className = 'table-title';
        tableTitle.textContent = formatAssetTypeName(assetType);
        tableTitle.style.borderBottomColor = assetColors[assetType] || '#000000';
        tableWrapper.appendChild(tableTitle);

        const table = document.createElement('table');
        table.className = 'results-table';

        const thead = document.createElement('thead');
        thead.innerHTML = `
            <tr>
                <th>Jahr</th>
                <th>Kapital (€)</th>
                <th>Entwicklung (€)</th>
            </tr>
        `;
        table.appendChild(thead);

        const tbody = document.createElement('tbody');

        assetResults.forEach((result, index) => {
            const row = document.createElement('tr');

            if (index % 2 === 1) {
                row.classList.add('alternate-row');
            }

            const yearCell = document.createElement('td');
            yearCell.textContent = result.year;
            row.appendChild(yearCell);

            const capitalCell = document.createElement('td');
            capitalCell.textContent = formatCurrency(result.capital);
            capitalCell.classList.add('capital-cell');
            row.appendChild(capitalCell);

            const developmentCell = document.createElement('td');
            developmentCell.textContent = formatCurrency(result.development);
            developmentCell.classList.add('development-cell');

            if (result.development > 0) {
                developmentCell.classList.add('positive');
            } else if (result.development < 0) {
                developmentCell.classList.add('negative');
            }

            row.appendChild(developmentCell);
            tbody.appendChild(row);
        });

        table.appendChild(tbody);
        tableWrapper.appendChild(table);
        tablesContainer.appendChild(tableWrapper);
    });
}

function formatAssetTypeName(assetType) {
    const nameMapping = {
        'BONDS': 'Anleihen',
        'RAW_MATERIALS': 'Rohstoffe',
        'REAL_ESTATES': 'Immobilien',
        'STOCKS': 'Aktien'
    };
    return nameMapping[assetType] || assetType;
}

function formatCurrency(value) {
    return new Intl.NumberFormat('de-DE', {
        style: 'currency',
        currency: 'EUR',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(value);
}

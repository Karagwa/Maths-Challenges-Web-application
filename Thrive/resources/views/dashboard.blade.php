@extends('layouts.app', ['activePage' => 'Analysis', 'title' => 'Thrive Mathematical challenge by Thrive challenges & Fantastic Five', 'navName' => 'Analysis Board', 'activeButton' => 'laravel'])

@section('content')
    <div class="content">
        <h4>A GRAPH SHOWING THE HISTORY RANKINGS OF THE PARTICIPATING SCHOOLS.</h4>
        <div style="width: 800px;">
            <canvas id="myChart"></canvas>
        </div>
        
        <h4>List of Students that didn't complete the challenges</h4>
        <div class="card-body table-full-width table-responsive">
            <table class="table table-hover" id="tb2">
                <thead>
                    <tr>
                        <th>Username</th>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Challenge Number</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

        <h4>Repetition Percentages</h4>
        <div class="card-body table-full-width table-responsive">
            <table class="table table-hover" id="tb3">
                <thead>
                    <tr>
                        <th>Repetition Percentage</th>
                        <th>Challenge Number</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                fetchIncomplete();
                fetchRepetitions();
            });
        </script>
         
    </div>
@endsection

@push('js')
    <script type="text/javascript">
        $(document).ready(function() {
            demo.initDashboardPageCharts();
            demo.showNotification();
        });

        function fetchIncomplete() {
            fetch('/fetch_Incomplete_challenges')
                .then(response => response.text())
                .then(data => {
                    const rows = data.split('\n');
                    const tbody = document.getElementById('tb2').getElementsByTagName('tbody')[0];
                    tbody.innerHTML = '';
                    rows.forEach(row => {
                        const columns = row.split('|');
                        if (columns.length === 4) {
                            const tr = document.createElement('tr');
                            tr.innerHTML = `
                                <td>${columns[0]}</td>
                                <td>${columns[1]}</td>
                                <td>${columns[2]}</td>
                                <td>${columns[3]}</td>
                            `;
                            tbody.appendChild(tr);
                        }
                    });
                })
                .catch(error => console.error('Error fetching data:', error));
        }

        function fetchRepetitions() {
            fetch('/fetch_Repetition_percentages')
                .then(response => response.text())
                .then(data => {
                    const rows = data.split('\n');
                    const tbody = document.getElementById('tb3').getElementsByTagName('tbody')[0];
                    tbody.innerHTML = '';
                    rows.forEach(row => {
                        const columns = row.split('|');
                        if (columns.length === 2) {
                            const tr = document.createElement('tr');
                            tr.innerHTML = `
                                <td>${columns[0]}</td>
                                <td>${columns[1]}</td>
                            `;
                            tbody.appendChild(tr);
                        }
                    });
                })
                .catch(error => console.error('Error fetching data:', error));
        }

        document.addEventListener('DOMContentLoaded', function () {
            fetch('/fetch_rankings')
                .then(response => response.json())
                .then(data => {
                    const labels = data.years;
                    const schoolNames = data.schools;
                    const rankings = data.rankings;

                    const colors = [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 205, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(54, 162, 235, 0.2)'
                    ];

                    const borderColors = [
                        'rgb(255, 99, 132)',
                        'rgb(255, 159, 64)',
                        'rgb(255, 205, 86)',
                        'rgb(75, 192, 192)',
                        'rgb(54, 162, 235)'
                    ];

                    const datasets = schoolNames.map((school, index) => ({
                        label: school,
                        data: rankings[school],
                        backgroundColor: colors[index],
                        borderColor: borderColors[index],
                        borderWidth: 1
                    }));

                    const chartData = {
                        labels: labels,
                        datasets: datasets
                    };

                    const config = {
                        type: 'bar',
                        data: chartData,
                        options: {
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    };

                    var myChart = new Chart(
                        document.getElementById('myChart'),
                        config
                    );
                })
                .catch(error => console.error('Error fetching data:', error));
        });
    </script>
@endpush
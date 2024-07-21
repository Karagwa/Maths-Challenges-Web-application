@extends('layouts.app', ['activePage' => 'Analysis', 'title' => 'Thrive Mathematical challenge by Thrive challenges & Fantastic Five', 'navName' => 'Analysis Board', 'activeButton' => 'laravel'])
<?php 
$years = ['2019', '2020', '2021', '2022', '2023'];
$schools = ['School A', 'School B', 'School C', 'School D', 'School E'];
$rankings = [
    'School A' => [1, 2, 3, 1, 2],
    'School B' => [2, 1, 1, 3, 1],
    'School C' => [3, 3, 2, 2, 4],
    'School D' => [4, 5, 4, 5, 3],
    'School E' => [5, 4, 5, 4, 5]
];
?>
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
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        fetchIncomplete();
                    });
                </script>
         
    </div>
@endsection

@push('js')
    <script type="text/javascript">
        $(document).ready(function() {
            // Javascript method's body can be found in assets/js/demos.js
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
                        if (columns.length === 3) {
                            const tr = document.createElement('tr');
                            tr.innerHTML = `
                                <td>${columns[0]}</td>
                                <td>${columns[1]}</td>
                                <td>${columns[2]}</td>
                            `;
                            tbody.appendChild(tr);
                        }
                    });
                })
                .catch(error => console.error('Error fetching data:', error));
        }
        // PHP data arrays to JavaScript
        const labels = <?php echo json_encode($years); ?>;
        const schoolNames = <?php echo json_encode($schools); ?>;
        const rankings = <?php echo json_encode($rankings); ?>;
        
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
        
        const data = {
            labels: labels,
            datasets: datasets
        };
        
        const config = {
            type: 'bar',
            data: data,
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
    </script>
@endpush
@extends('layouts.app', ['activePage' => 'table', 'title' => 'Thrive Mathematical Challenges by Thrive challenges & Fantastic Five', 'navName' => 'Table List', 'activeButton' => 'laravel'])
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>REGISTRATION</title>
    <style>
        /* General container styles */
        .container-fluid {
            padding: 20px;
            background-color: #f8f9fa;
        }

        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
            margin-bottom: 20px;
            background-color: #ffffff;
            max-width: 800px; /* Limit the max-width of the card */
            margin: 0 auto; /* Center the card horizontally */
        }

        .card-header {
            background-color: #007bff;
            color: #ffffff;
            padding: 15px;
            border-bottom: 1px solid #007bff;
            text-align: center; /* Center the text in the header */
        }

        .card-header h4 {
            margin: 0;
            font-size: 24px;
        }

        .card-header p {
            margin: 0;
            font-size: 14px;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center; /* Center the form elements */
        }

        input[type="number"],
        input[type="text"] {
            width: 100%; /* Full width of the parent container */
            max-width: 400px; /* Optional: Limit the max-width of the input boxes */
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ced4da;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }

        input[type="button"] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px; /* Add some margin to the top */
        }

        input[type="button"]:hover {
            background-color: #0056b3;
        }

        .table-full-width {
            width: 100%;
        }

        .table-responsive {
            width: 100%;
            overflow-x: auto;
        }

        table {
            width: 100%;
            margin-bottom: 1rem;
            color: #212529;
            border-collapse: collapse;
            text-align: center; /* Center text in table cells */
        }

        .table-hover tbody tr:hover {
            background-color: #f5f5f5;
        }

        thead {
            background-color: #343a40;
            color: white;
        }

        th,
        td {
            padding: 15px;
            text-align: center; /* Center text in table cells */
            border-top: 1px solid #dee2e6;
        }

        th {
            border-bottom: 2px solid #dee2e6;
        }

        tbody tr:nth-of-type(even) {
            background-color: #f8f9fa;
        }


    </style>
    
</head>
<body>
@section('content')
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="card strpied-tabled-with-hover">
                        <div class="card-header">
                            <h4 class="card-title">SCHOOLS THAT ARE ENTERING INTO THE COMPETITION</h4>
                            <p class="card-category">Please enter the information of the schools</p>
                        </div>
                        
                        <form method="GET"  name="sample" style="width: 800px;">
                        @csrf    

                            <br>
                            <input type="number" name="id" id="id" autocomplete="off" placeholder="ID"><br>
                            <input type="text" name="name" id="name" autocomplete="name" placeholder="Name"><br>
                            <input type="text" name="district" id="district" autocomplete="address-level2" placeholder="District"><br>
                            <input type="text" name="regno" id="regno" autocomplete="off" placeholder="Registration Number"> <br>
                            <input type="button" name="add" value="Add info" onclick="addSchool();">
 
                            <div class="card-body table-full-width table-responsive">
                                <table class="table table-hover table-striped" id="tb1">
                                    <thead>
                                       
                                        <th>ID</th>
                                        
                                        <th>Name</th>
                                        <th>District</th>
                                        <th>School Registration Number</th>
                                        
                                    </thead>
                                    <tbody>
                          
                                    </tbody>
                                </table>
                            </div>
                        </form>
                        
                    </div>
                </div>
               
            </div>
        </div>
    </div>
@endsection

<script>
    
    document.addEventListener('DOMContentLoaded', function () {
    fetchSchools();
});

function fetchSchools() {
    fetch('/fetch_schools')
        .then(response => response.text())
        .then(data => {
            const rows = data.split('\n');
            const tbody = document.getElementById('tb1').getElementsByTagName('tbody')[0];
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
                        
                        <td><button onclick="removeRow(${columns[0]}, 'school')">Delete</button></td>
                    `;
                    tbody.appendChild(tr);
                }
            });
        })
        .catch(error => console.error('Error fetching data:', error));
}

function addSchool() {
    const formData = new FormData(document.forms.sample);
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

    fetch('/add_school', {
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': csrfToken
        },
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        const [status, message] = data.split('|');
        if (status === 'success') {
            fetchSchools();
        } else {
            alert(message);
        }
    })
    .catch(error => console.error('Fetch schools error:', error));
}

function removeSchool(id) {
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

    fetch(`/schools/${id}`, {
        method: 'DELETE',
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    })
    .then(data => {
        // Handle success or error response
    })
    .catch(error => console.error('Error deleting school:', error));
}


</script>
</body>
</html>

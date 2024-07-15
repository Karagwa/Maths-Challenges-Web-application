@extends('layouts.app', ['activePage' => 'table', 'title' => 'Thrive Mathematical Challenges by Thrive challenges & Fantastic Five', 'navName' => 'Table List', 'activeButton' => 'laravel'])
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>REGISTRATION</title>
    
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
                            ID <input type="number" name="id" id="id" autocomplete="off">
                            Username <input type="text" name="username" id="username" autocomplete="on">
                            Name <input type="text" name="name" id="name" autocomplete="name">
                            District <input type="text" name="district" id="district" autocomplete="address-level2"><br>
                            <br> RegNo <input type="text" name="regno" id="regno" autocomplete="off"> <br> <br>
                            <input type="button" name="add" value="Add info" onclick="addSchool();">
 
                            <div class="card-body table-full-width table-responsive">
                                <table class="table table-hover table-striped" id="tb1">
                                    <thead>
                                       
                                        <th>ID</th>
                                        <th>UserName</th>
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

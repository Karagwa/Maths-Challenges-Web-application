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
                    <div class="card card-plain table-plain-bg">
                        <div class="card-header ">
                            <h4 class="card-title">REPRESENTATIVE INFORMATION</h4>
                            <p class="card-category">Please enter the information of the representative below</p>
                        </div>
                        <form method="POST" name="sample2">
                        @csrf
                            <br>
                            ID <input type="number" name="id" id="id" autocomplete="off">
                            Username <input type="text" name="username" id="username" autocomplete="on">
                            Name <input type="text" name="name2" id="name2" autocomplete="on">
                            Email <input type="email" name="email" id="email" autocomplete="on">
                            Regno <input type="text" name="regno" id="regno" autocomplete="on">
                            Password <input type="password" name="password" id="password">
                            <br><br>
                            <input type="button" name="add" value="Add info" onclick="addRep();">
                        <div class="card-body table-full-width table-responsive">
                            <table class="table table-hover" id="tb2">
                                <thead>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Name of the representative</th>
                                    <th>Email of the representative</th>
                                    <th>Regno</th>
                                    <th>Password</th>
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
    
    fetchReps();
});


function fetchReps() {
    fetch('/fetch_representatives')
        .then(response => response.text())
        .then(data => {
            const rows = data.split('\n');
            const tbody = document.getElementById('tb2').getElementsByTagName('tbody')[0];
            tbody.innerHTML = '';
            rows.forEach(row => {
                const columns = row.split('|');
                if (columns.length === 5) {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                        <td>${columns[0]}</td>
                        <td>${columns[1]}</td>
                        <td>${columns[2]}</td>
                        <td>${columns[3]}</td>
                        <td>${columns[4]}</td>
                        
                        <td><button onclick="removeRow(${columns[0]}, 'representative')">Delete</button></td>
                    `;
                    tbody.appendChild(tr);
                }
            });
        })
        .catch(error => console.error('Error fetching data:', error));
}


function addRep() {
    const formData = new FormData(document.forms.sample2);
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
    
    fetch('/add_representative', {
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
            fetchReps();
        } else {
            alert(message);
        }
    })
    .catch(error => console.error('Error adding representative:', error));
}

function removeRow(id, type) {
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

    fetch(`/representatives/${id}`, {
        method: 'DELETE',
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    })
    .then(response => response.text())
    .then(data => {
        const [status, message] = data.split('|');
        if (status === 'success') {
            fetchReps();
        } else {
            alert(message);
        }
    })
    .catch(error => console.error('Error deleting data:', error));
}

</script>
</body>
</html>


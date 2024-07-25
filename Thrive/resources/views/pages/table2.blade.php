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
        input[type="text"],
        input[type="email"],
        input[type="password"] {
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
                    <div class="card">
                        <div class="card-header">
                            <h4 class="card-title">REPRESENTATIVE INFORMATION</h4>
                            <p class="card-category">Please enter the information of the representative below</p>
                            <p class="card-category"><span> Please refresh after editing and deleting the information </span></p>
                        </div>
                        <form name="sample2">
                            @csrf
                            <br>
                            <input type="hidden" name="id" id="id" autocomplete="off"><br>
                            <input type="text" name="username" id="username" autocomplete="on" placeholder="Username"><br>
                            <input type="text" name="name2" id="name2" autocomplete="on" placeholder="Name"><br>
                            <input type="email" name="email" id="email" autocomplete="on" placeholder="Email"><br>
                            <input type="text" name="regno" id="regno" autocomplete="on" placeholder="Regno"><br>
                            <input type="password" name="password" id="password" placeholder="Password"><br>
                            <input type="button" name="add" value="Add info" onclick="addRep();">
                        </form>
                        <div class="card-body table-full-width table-responsive">
                            <table class="table table-hover table-striped" id="tb2">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Username</th>
                                        <th>Name of the representative</th>
                                        <th>Email of the representative</th>
                                        <th>Regno</th>
                                        <th>Password</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for editing representative details -->
    <div class="modal fade" id="editRepModal" tabindex="-1" aria-labelledby="editRepModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="editRepModalLabel">Edit Representative</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="editRepForm" method="POST" onsubmit="saveRep(event)">
                        @csrf
                        <input type="hidden" name="_method" value="POST">
                        <input type="hidden" name="id" id="editRepId">
                        <div class="form-group">
                            <label for="editRepUsername">Username</label>
                            <input type="text" name="username" id="editRepUsername" class="form-control" placeholder="Enter username">
                        </div>
                        <div class="form-group">
                            <label for="editRepName">Name</label>
                            <input type="text" name="name2" id="editRepName" class="form-control" placeholder="Enter name">
                        </div>
                        <div class="form-group">
                            <label for="editRepEmail">Email</label>
                            <input type="email" name="email" id="editRepEmail" class="form-control" placeholder="Enter email">
                        </div>
                        <div class="form-group">
                            <label for="editRepRegno">Regno</label>
                            <input type="text" name="regno" id="editRepRegno" class="form-control" placeholder="Enter registration number">
                        </div>
                        <div class="form-group">
                            <label for="editRepPassword">Password</label>
                            <input type="password" name="password" id="editRepPassword" class="form-control" placeholder="Enter password">
                        </div>
                        <button type="submit" class="btn btn-success">Update</button>
                    </form>
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
            rows.forEach((row, index) => {
                const columns = row.split('|');
                console.log("Row Columns:", columns); // Debugging
                if (columns.length === 6) { // Update to 6 for the password field
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                        <td>${index + 1}</td>
                        <td>${columns[1]}</td>
                        <td>${columns[2]}</td>
                        <td>${columns[3]}</td>
                        <td>${columns[4]}</td>
                        <td>${columns[5]}</td>
                        
                        <td>
                            <button class="btn btn-info btn-sm" onclick="editRep(event, '${columns[0]}', '${columns[1]}', '${columns[2]}', '${columns[3]}', '${columns[4]}')"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</button>
                            <button class="btn btn-danger btn-sm" onclick="deleteRep('${columns[0]}')"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</button>
                        </td>
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

function editRep(event, id, username, name2, email, regno, password) {
    event.preventDefault(); // Prevent default form submission

    document.getElementById('editRepId').value = id;
    document.getElementById('editRepUsername').value = username;
    document.getElementById('editRepName').value = name2;
    document.getElementById('editRepEmail').value = email;
    document.getElementById('editRepRegno').value = regno;
    document.getElementById('editRepPassword').value = password;

    $('#editRepModal').modal('show');
}

function saveRep(event) {
    event.preventDefault(); // Prevent default form submission

    const form = document.getElementById('editRepForm');
    const formData = new FormData(form);
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
    const id = document.getElementById('editRepId').value;

    fetch(`/representative/${id}`, {
        method: 'POST', // Use PATCH method
        headers: {
            'X-CSRF-TOKEN': csrfToken,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(Object.fromEntries(formData.entries()))
    })
    .then(response => response.text())
    .then(data => {
        const [status, message] = data.split('|');
        if (status === 'success') {
            $('#editRepModal').modal('hide');
            fetchReps();
        } else {
            alert(message);
        }
    })
    .catch(error => console.error('Error updating representative:', error));
}

function deleteRep(id) {
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

    if (confirm('Are you sure you want to delete this representative?')) {
        fetch(`/representative/${id}`, {
            method: 'DELETE',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        })
        .then(response => response.text())
        .then(data => {
            if (data.includes('success')) {
                fetchReps();
            } else {
                alert('Failed to delete representative');
            }
        })
        .catch(error => console.error('Error deleting representative:', error));
    }
}
</script>
</body>
</html>



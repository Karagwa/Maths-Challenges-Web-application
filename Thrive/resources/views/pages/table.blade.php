@extends('layouts.app', ['activePage' => 'table', 'title' => 'Thrive Mathematical Challenges  by Thrive challenges & Fantastic Five', 'navName' => 'Table List', 'activeButton' => 'laravel'])
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>REGISTRATION</title>
    <!-- Your other head elements -->
</head>
<body>
@section('content')
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="card strpied-tabled-with-hover">
                        <div class="card-header ">
                            <h4 class="card-title">SCHOOLS THAT A RE ENTERING INTO THE COMPETION</h4>
                            <p class="card-category">please enter the information of the schools</p>
                        </div>
                        
                        <form method="GET" name="sample" style="width: 800px;">
                            @csrf
                        <br>
                            ID <input type="number" name="id" id="id" autocomplete="off">
                            Name <input type="text" name="name" id="name" autocomplete="name">
                            District <input type="text" name="district" id="district" autocomplete="address-level2"><br>
                           <br> RegNo <input type="text" name="regno" id="regno" autocomplete="off"> <br> <br>
                           <input type="button" name="add" value="Add infor" onclick="addSchool();">
 
                           <div class="card-body table-full-width table-responsive">
                            <table class="table table-hover table-striped" id="tb1">
                                <thead>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>District</th>
                                    <th>School registration number</th>
                                    
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
<script type="">
document.addEventListener('DOMContentLoaded', function () {
    fetchSchools();
});

function fetchSchools() {
    fetch('/schools')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('tb1').getElementsByTagName('tbody')[0];
            tbody.innerHTML = '';
            data.forEach(school => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${school.id}</td>
                    <td>${school.name}</td>
                    <td>${school.district}</td>
                    <td>${school.regno}</td>
                    <td><button onclick="removeRow(${school.id}, 'school')">Delete</button></td>
                `;
                tbody.appendChild(tr);
            });
        });
}



function addSchool() {
    const formData = new FormData(document.forms.sample);
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
    fetch('/schools', {
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').getAttribute('content')
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            fetchSchools();
        } else {
            alert('Failed to add school');
        }
    })
    .catch(error => console.error('Error:', error));
}



function removeRow(id, type) {
    fetch(`/${type === 'school' ? 'schools' : 'representatives'}/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').getAttribute('content')
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            if (type === 'school') {
                fetchSchools();
            } else {
                fetchReps();
            }
        } else {
            alert('Failed to delete');
        }
    });
}
</script>
</body>
</html>

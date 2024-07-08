@extends('layouts.app', ['activePage' => 'table', 'title' => 'Thrive Mathematical Challenges  by Thrive challenges & Fantastic Five', 'navName' => 'Table List', 'activeButton' => 'laravel'])
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
    <div class="col-md-12">
                    <div class="card card-plain table-plain-bg">
                        <div class="card-header ">
                            <h4 class="card-title">REPRESENTATIVE INFORMATION</h4>
                            <p class="card-category">please enter the information of the representative below</p>
                        </div>
                        <form method="POST"  name="sample2">
                        
                        <br>
                            ID <input type="number" name="id" id="id" autocomplete="off">
                            Name <input type="text" name="name2" id="name" autocomplete="on">
                            Email <input type="email" name="email" id="email" autocomplete="on">
                            <br> <br>
                           <input type="button" name="add" value="Add infor" onclick="addRep();">
                        <div class="card-body table-full-width table-responsive">
                            <table class="table table-hover" id="tb2">
                                <thead>
                                    <th>ID</th>
                                    <th>Name of the representative</th>
                                    <th>Email of the representative</th>
                                    
                                    
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
    fetchReps();
});
function fetchReps() {
    fetch('/representatives')
        .then(response => {
            if (!response.ok){
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.getElementById('tb2').getElementsByTagName('tbody')[0];
            tbody.innerHTML = '';
            data.forEach(rep => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${rep.id}</td>
                    <td>${rep.name}</td>
                    <td>${rep.email}</td>
                    <td><button onclick="removeRow(${rep.id}, 'representative')">Delete</button></td>
                `;
                tbody.appendChild(tr);
            });
        });
}
function addRep() {
    const formData = new FormData(document.forms.sample2);
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
    fetch('/representatives', {
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': document.querySelector('meta[name="csrf-token"]').getAttribute('content')
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            fetchReps();
        } else {
            alert('Failed to add representative');
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